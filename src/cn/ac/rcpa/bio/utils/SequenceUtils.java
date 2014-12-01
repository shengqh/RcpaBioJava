package cn.ac.rcpa.bio.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.biojava.bio.BioError;
import org.biojava.bio.BioException;
import org.biojava.bio.seq.DNATools;
import org.biojava.bio.seq.ProteinTools;
import org.biojava.bio.seq.RNATools;
import org.biojava.bio.seq.Sequence;
import org.biojava.bio.seq.SequenceIterator;
import org.biojava.bio.seq.db.HashSequenceDB;
import org.biojava.bio.seq.db.IDMaker;
import org.biojava.bio.seq.db.SequenceDB;
import org.biojava.bio.seq.io.SequenceBuilderFactory;
import org.biojava.bio.seq.io.SmartSequenceBuilder;
import org.biojava.bio.seq.io.StreamReader;
import org.biojava.bio.seq.io.StreamWriter;
import org.biojava.bio.seq.io.SymbolTokenization;
import org.biojava.bio.symbol.Alphabet;
import org.biojava.bio.symbol.IllegalSymbolException;
import org.biojava.utils.AssertionFailure;
import org.biojava.utils.ChangeVetoException;
import org.biojavax.bio.seq.SimpleRichSequence;

import cn.ac.rcpa.bio.database.AccessNumberParserFactory;
import cn.ac.rcpa.bio.database.IAccessNumberParser;
import cn.ac.rcpa.bio.database.SequenceDatabaseType;
import cn.ac.rcpa.bio.seq.FastaDescriptionLineParser;
import cn.ac.rcpa.bio.seq.FastaFormat;
import cn.ac.rcpa.filter.FilterNothing;
import cn.ac.rcpa.filter.IFilter;

/**
 * <p>
 * Title: RCPA Package
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * 
 * <p>
 * Company: RCPA.SIBS.AC.CN
 * </p>
 * 
 * @author Sheng Quan-Hu
 * @version 1.0
 */
final public class SequenceUtils {
	private final static Pattern refPattern = Pattern
			.compile("\\s*\\S*\\s+(?:Tax_Id=\\d+)?\\s*(.*)$");

	private final static Pattern namePattern = Pattern.compile("\\s*(\\S*)");

	private SequenceUtils() {
	}

	public static List<Sequence> readFastaProteins(String fastaFile)
			throws IOException, BioException {
		return readFastaProteins(new File(fastaFile));
	}

	public static List<Sequence> readFastaProteins(File fastaFile)
			throws IOException, BioException {
		return readFastaProteins(fastaFile, new FilterNothing<Sequence>());
	}

	public static List<Sequence> readFastaProteins(File fastaFile,
			IFilter<Sequence> seqFilter) throws IOException, BioException {
		System.out.println("Reading protein sequences from "
				+ fastaFile.getName() + " ...");
		SequenceIterator seqi = readFastaProtein(new BufferedReader(
				new FileReader(fastaFile)));
		ArrayList<Sequence> result = new ArrayList<Sequence>();
		while (seqi.hasNext()) {
			Sequence seq = seqi.nextSequence();
			if (seqFilter.accept(seq)) {
				result.add(seq);
			}
		}
		System.out.println("Reading protein sequences from "
				+ fastaFile.getName() + " finished.");
		return result;
	}

	public static void writeFasta(File outputFile,
			Collection<Sequence> sequences) throws IOException {
		FileOutputStream os = new FileOutputStream(outputFile);
		try {
			for (Sequence sequence : sequences) {
				writeFasta(os, sequence);
			}
		} finally {
			os.close();
		}
	}

	public static List<String> getAccessNumbers(File fastaFile,
			SequenceDatabaseType dbType) throws FileNotFoundException,
			BioException, NoSuchElementException {
		final IAccessNumberParser parser = AccessNumberParserFactory
				.getParser(dbType);
		SequenceIterator seqi = readFastaProtein(new BufferedReader(
				new FileReader(fastaFile)));
		ArrayList<String> result = new ArrayList<String>();
		while (seqi.hasNext()) {
			final Sequence seq = seqi.nextSequence();
			result.add(parser.getValue((String) seq.getAnnotation()
					.getProperty("description_line")));
		}
		return result;
	}

	public static List<Sequence> getSequencesByAccessNumber(
			List<Sequence> sequences, String[] accessNumbers)
			throws FileNotFoundException, BioException, NoSuchElementException {
		List<Sequence> result = new ArrayList<Sequence>();

		for (Iterator iter = sequences.iterator(); iter.hasNext();) {
			Sequence seq = (Sequence) iter.next();
			for (int i = 0; i < accessNumbers.length; i++) {
				if (seq.getName().indexOf(accessNumbers[i]) != -1) {
					result.add(seq);
					break;
				}
			}
		}

		return result;
	}

	public static String getProteinName(String descriptionLine) {
		Matcher matcher = namePattern.matcher(descriptionLine);
		String result;
		if (matcher.find()) {
			result = matcher.group(1);
		} else {
			result = descriptionLine;
		}

		return result;
	}

	public static String getProteinDescriptionLine(Sequence seq) {
		if (seq instanceof SimpleRichSequence) {
			return seq.getName() + " "
					+ ((SimpleRichSequence) seq).getDescription();
		} else {
			if (seq.getAnnotation().containsProperty(
					FastaFormat.PROPERTY_DESCRIPTIONLINE)) {
				return (String) seq.getAnnotation().getProperty(
						FastaFormat.PROPERTY_DESCRIPTIONLINE);
			} else {
				return seq.getName();
			}
		}
	}

	public static String getProteinReference(String descriptionLine) {
		Matcher matcher = refPattern.matcher(descriptionLine);
		String result;
		if (matcher.find()) {
			result = matcher.group(1);
		} else {
			result = "";
		}

		return result;
	}

	public static String getProteinReference(Sequence seq) {
		if (seq instanceof SimpleRichSequence) {
			return ((SimpleRichSequence) seq).getDescription();
		} else {
			if (seq.getAnnotation().containsProperty(
					FastaFormat.PROPERTY_DESCRIPTIONLINE)) {
				return getProteinReference((String) seq.getAnnotation()
						.getProperty(FastaFormat.PROPERTY_DESCRIPTIONLINE));
			} else {
				return "";
			}
		}
	}

	public static boolean isModifiedChar(char aChar) {
		return aChar < 'A' || aChar > 'Z';
	}

	public static Sequence getReversedSeq(Sequence seq, int iCount)
			throws IllegalSymbolException {
		return getReversedSeq(seq, iCount, 0);

	}

	public static Sequence getReversedSeq(Sequence seq, int iCount,
			int leftPadNumber) throws IllegalSymbolException {
		String sequence = StringUtils.reverse(seq.seqString());
		String name = "REVERSED_"
				+ StringUtils.leftPad(Integer.toString(iCount), leftPadNumber,
						'0');

		return ProteinTools.createProteinSequence(sequence, name);
	}

	private static String aminoacids = "GASPVTCILNDQKEMHFRYW";

	public static LinkedHashMap<Character, Double> getAminoacidComposition(
			String sequence) {
		return getAminoacidComposition(sequence, aminoacids);
	}

	public static LinkedHashMap<Character, Double> getAminoacidComposition(
			String sequence, String aas) {
		LinkedHashMap<Character, Double> result = new LinkedHashMap<Character, Double>();
		for (int i = 0; i < aas.length(); i++) {
			result.put(aas.charAt(i), 0.0);
		}

		int totalCount = 0;
		for (int i = 0; i < sequence.length(); i++) {
			char aa = sequence.charAt(i);
			if (aas.indexOf(aa) != -1) {
				totalCount++;
				result.put(aa, result.get(aa) + 1.0);
			}
		}

		if (totalCount != 0) {
			for (Character c : result.keySet()) {
				result.put(c, result.get(c) / totalCount);
			}
		}

		return result;
	}

	public static Map<String, String> readProteinNameDescriptionMap(
			String proteinFastaFile, SequenceDatabaseType dType)
			throws IOException, NoSuchElementException, BioException {
		HashMap<String, String> result = new HashMap<String, String>();
		SequenceIterator seqi = readFastaProtein(new BufferedReader(
				new FileReader(proteinFastaFile)));
		IAccessNumberParser parser = AccessNumberParserFactory.getParser(dType);

		while (seqi.hasNext()) {
			Sequence seq = seqi.nextSequence();
			if (seq.getAnnotation().containsProperty("description")) {
				String description = (String) seq.getAnnotation().getProperty(
						"description");
				result.put(parser.getValue(seq.getName()), description.trim());
			} else {
				result.put(parser.getValue(seq.getName()), "");
			}
		}
		return result;
	}

	public static Map<String, String> readProteinNameSequenceMap(
			String proteinFastaFile, SequenceDatabaseType dType)
			throws IOException, NoSuchElementException, BioException {
		HashMap<String, String> result = new HashMap<String, String>();
		SequenceIterator seqi = readFastaProtein(new BufferedReader(
				new FileReader(proteinFastaFile)));
		IAccessNumberParser parser = AccessNumberParserFactory.getParser(dType);

		while (seqi.hasNext()) {
			Sequence seq = seqi.nextSequence();
			result.put(parser.getValue(seq.getName()), seq.seqString());
		}
		return result;
	}

	/**
	 * Copy following part from biojava package. New version biojavax made the
	 * fastaformat too complex to read and write. I just like the simplest
	 * version.
	 * 
	 * @author Quanhu Sheng
	 */

	private static SequenceBuilderFactory _fastaBuilderFactory;

	/**
	 * Get a default SequenceBuilderFactory for handling FASTA files.
	 * 
	 * @return a <code>SmartSequenceBuilder.FACTORY</code>
	 */
	public static SequenceBuilderFactory getFastaBuilderFactory() {
		if (_fastaBuilderFactory == null) {
			_fastaBuilderFactory = new FastaDescriptionLineParser.Factory(
					SmartSequenceBuilder.FACTORY);
		}
		return _fastaBuilderFactory;
	}

	/**
	 * Read a fasta file.
	 * 
	 * @param br
	 *            the BufferedReader to read data from
	 * @param sTok
	 *            a SymbolTokenization that understands the sequences
	 * @return a SequenceIterator over each sequence in the fasta file
	 */
	public static SequenceIterator readFasta(BufferedReader br,
			SymbolTokenization sTok) {
		return new StreamReader(br, new FastaFormat(), sTok,
				getFastaBuilderFactory());
	}

	/**
	 * Read a fasta file using a custom type of SymbolList. For example, use
	 * SmartSequenceBuilder.FACTORY to emulate readFasta(BufferedReader,
	 * SymbolTokenization) and SmartSequenceBuilder.BIT_PACKED to force all
	 * symbols to be encoded using bit-packing.
	 * 
	 * @param br
	 *            the BufferedReader to read data from
	 * @param sTok
	 *            a SymbolTokenization that understands the sequences
	 * @param seqFactory
	 *            a factory used to build a SymbolList
	 * @return a <CODE>SequenceIterator</CODE> that iterates over each
	 *         <CODE>Sequence</CODE> in the file
	 */
	public static SequenceIterator readFasta(BufferedReader br,
			SymbolTokenization sTok, SequenceBuilderFactory seqFactory) {
		return new StreamReader(br, new FastaFormat(), sTok,
				new FastaDescriptionLineParser.Factory(seqFactory));
	}

	/**
	 * Iterate over the sequences in an FASTA-format stream of DNA sequences.
	 * 
	 * @param br
	 *            the BufferedReader to read data from
	 * @return a <CODE>SequenceIterator</CODE> that iterates over each
	 *         <CODE>Sequence</CODE> in the file
	 */
	public static SequenceIterator readFastaDNA(BufferedReader br) {
		return new StreamReader(br, new FastaFormat(), getDNAParser(),
				getFastaBuilderFactory());
	}

	/**
	 * Iterate over the sequences in an FASTA-format stream of RNA sequences.
	 * 
	 * @param br
	 *            the BufferedReader to read data from
	 * @return a <CODE>SequenceIterator</CODE> that iterates over each
	 *         <CODE>Sequence</CODE> in the file
	 */
	public static SequenceIterator readFastaRNA(BufferedReader br) {
		return new StreamReader(br, new FastaFormat(), getRNAParser(),
				getFastaBuilderFactory());
	}

	/**
	 * Iterate over the sequences in an FASTA-format stream of Protein
	 * sequences.
	 * 
	 * @param br
	 *            the BufferedReader to read data from
	 * @return a <CODE>SequenceIterator</CODE> that iterates over each
	 *         <CODE>Sequence</CODE> in the file
	 */
	public static SequenceIterator readFastaProtein(BufferedReader br) {
		return new StreamReader(br, new FastaFormat(), getProteinParser(),
				getFastaBuilderFactory());
	}

	/**
	 * Create a sequence database from a fasta file provided as an input stream.
	 * Note this somewhat duplicates functionality in the readFastaDNA and
	 * readFastaProtein methods but uses a stream rather than a reader and
	 * returns a SequenceDB rather than a SequenceIterator. If the returned DB
	 * is likely to be large then the above mentioned methods should be used.
	 * 
	 * @return a <code>SequenceDB</code> containing all the
	 *         <code>Sequences</code> in the file.
	 * @since 1.2
	 * @param seqFile
	 *            The file containg the fasta formatted sequences
	 * @param alpha
	 *            The <code>Alphabet</code> of the sequence, ie DNA, RNA etc
	 * @throws BioException
	 *             if problems occur during reading of the stream.
	 */
	public static SequenceDB readFasta(InputStream seqFile, Alphabet alpha)
			throws BioException {
		HashSequenceDB db = new HashSequenceDB(IDMaker.byName);
		SequenceBuilderFactory sbFact = new FastaDescriptionLineParser.Factory(
				SmartSequenceBuilder.FACTORY);
		FastaFormat fFormat = new FastaFormat();
		for (SequenceIterator seqI = new StreamReader(seqFile, fFormat, alpha
				.getTokenization("token"), sbFact); seqI.hasNext();) {
			Sequence seq = seqI.nextSequence();
			try {
				db.addSequence(seq);
			} catch (ChangeVetoException cve) {
				throw new AssertionFailure(
						"Could not successfully add sequence " + seq.getName()
								+ " to sequence database", cve);
			}
		}
		return db;
	}

	/**
	 * Write a sequenceDB to an output stream in fasta format.
	 * 
	 * @since 1.2
	 * @param os
	 *            the stream to write the fasta formatted data to.
	 * @param db
	 *            the database of <code>Sequence</code>s to write
	 * @throws IOException
	 *             if there was an error while writing.
	 */
	public static void writeFasta(OutputStream os, SequenceDB db)
			throws IOException {
		StreamWriter sw = new StreamWriter(os, new FastaFormat());
		sw.writeStream(db.sequenceIterator());
	}

	/**
	 * Writes sequences from a SequenceIterator to an OutputStream in Fasta
	 * Format. This makes for a useful format filter where a StreamReader can be
	 * sent to the StreamWriter after formatting.
	 * 
	 * @since 1.2
	 * @param os
	 *            The stream to write fasta formatted data to
	 * @param in
	 *            The source of input <code>Sequences</code>
	 * @throws IOException
	 *             if there was an error while writing.
	 */
	public static void writeFasta(OutputStream os, SequenceIterator in)
			throws IOException {
		StreamWriter sw = new StreamWriter(os, new FastaFormat());
		sw.writeStream(in);
	}

	/**
	 * Writes a single Sequence to an OutputStream in Fasta format.
	 * 
	 * @param os
	 *            the OutputStream.
	 * @param seq
	 *            the Sequence.
	 * @throws IOException
	 *             if there was an error while writing.
	 */
	public static void writeFasta(OutputStream os, Sequence seq)
			throws IOException {
		writeFasta(os, new SingleSeqIterator(seq));
	}

	private static SymbolTokenization getDNAParser() {
		try {
			return DNATools.getDNA().getTokenization("token");
		} catch (BioException ex) {
			throw new BioError("Assertion failing:"
					+ " Couldn't get DNA token parser", ex);
		}
	}

	private static SymbolTokenization getRNAParser() {
		try {
			return RNATools.getRNA().getTokenization("token");
		} catch (BioException ex) {
			throw new BioError("Assertion failing:"
					+ " Couldn't get RNA token parser", ex);
		}
	}

	private static SymbolTokenization getProteinParser() {
		try {
			return ProteinTools.getTAlphabet().getTokenization("token");
		} catch (BioException ex) {
			throw new BioError("Assertion failing:"
					+ " Couldn't get PROTEIN token parser", ex);
		}
	}

	private static final class SingleSeqIterator implements SequenceIterator {
		private Sequence seq;

		SingleSeqIterator(Sequence seq) {
			this.seq = seq;
		}

		public boolean hasNext() {
			return seq != null;
		}

		public Sequence nextSequence() {
			Sequence seq = this.seq;
			this.seq = null;
			return seq;
		}
	}
}
