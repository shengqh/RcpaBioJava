package cn.ac.rcpa.bio.annotation.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.biojava.bio.BioException;

import cn.ac.rcpa.bio.annotation.AnnotationFactory;
import cn.ac.rcpa.bio.annotation.GOAAspectType;
import cn.ac.rcpa.bio.annotation.GOAClassificationEntry;
import cn.ac.rcpa.bio.annotation.GOEntry;
import cn.ac.rcpa.bio.annotation.IAnnotationQueryByGO;
import cn.ac.rcpa.bio.annotation.IGOEntry;
import cn.ac.rcpa.bio.database.AccessNumberFastaParser;
import cn.ac.rcpa.bio.database.SequenceDatabaseType;
import cn.ac.rcpa.bio.processor.AbstractFileProcessor;
import cn.ac.rcpa.utils.RcpaFileUtils;

public class GOAnnotationProcessorByGO extends AbstractFileProcessor {
	private DecimalFormat df = new DecimalFormat("##.##");

	private SequenceDatabaseType dbType;

	public GOAnnotationProcessorByGO(SequenceDatabaseType dbType) {
		this.dbType = dbType;
	}

	public List<String> process(String originFile) throws Exception {
		List<String> result = new ArrayList<String>();

		final File fastaFile = new File(originFile);

		IAnnotationQueryByGO query = AnnotationFactory
				.getAnnotationQueryByGO(dbType);

		checkInterrupted();

		Map<String, String> seqs = getSequences(fastaFile, dbType);

		checkInterrupted();

		String[] accessNumbers = seqs.keySet().toArray(new String[0]);

		for (GOAAspectType type : GOAAspectType.GOA_ASPECT_TYPES) {
			checkInterrupted();

			System.out.println("Getting " + type.getRoot().getName()
					+ " annotation from database ...");

			GOAClassificationEntry goa = query.getAnnotation(type.getRoot()
					.getAccession(), type.getDefaultLevel(), accessNumbers);

			GOAClassificationEntry goaUnknown = getUnknown(goa, accessNumbers, type
					.getUnknown());

			System.out.println("Get " + type.getRoot().getName()
					+ " annotation from database succeed.");

			final File statFile = new File(fastaFile.getParentFile(), "STATISTIC/"
					+ RcpaFileUtils.changeExtension(fastaFile.getName(), "go_"
							+ type.getRoot().getName() + ".stat"));

			printGOA(accessNumbers.length, type.getDefaultLevel(), type.getRoot()
					.getName(), goa, goaUnknown, statFile);

			result.add(statFile.getAbsolutePath());

			result.add(printGOAProteins(seqs, goa, goaUnknown, statFile));

			final String treeFile = RcpaFileUtils.changeExtension(statFile
					.getAbsolutePath(), "tree");
			goa.saveToFile(treeFile);

			result.add(treeFile);
		}

		return result;
	}

	private String printGOAProteins(Map<String, String> seqs,
			GOAClassificationEntry goa, GOAClassificationEntry goaUnknown,
			File resultFile) throws IOException {
		final String result = RcpaFileUtils.changeExtension(resultFile
				.getAbsolutePath(), ".details");
		PrintWriter goaDetailWriter = new PrintWriter(new FileWriter(result));
		try {
			printGOADefinition(goaDetailWriter, goa, 0);

			goaDetailWriter.println();

			printGOADetails(goaDetailWriter, goa, seqs);

			printGOADetails(goaDetailWriter, goaUnknown, seqs);
		} finally {
			goaDetailWriter.close();
		}
		return result;
	}

	private void printGOA(int acCount, int level, String classificationName,
			GOAClassificationEntry goa, GOAClassificationEntry goaUnknown,
			File resultFile) throws IOException {
		if (!resultFile.getParentFile().exists()) {
			resultFile.getParentFile().mkdirs();
		}

		PrintWriter goaWriter = new PrintWriter(new FileWriter(resultFile));
		try {
			goaWriter.println(classificationName + " classification");

			printGOAEntry(goaWriter, goa, 0, 0, acCount);

			printGOAEntry(goaWriter, goaUnknown, 0, 0, acCount);

			goaWriter.println();

			for (int maxLevel = 1; maxLevel <= level; maxLevel++) {
				printGOAEntry(goaWriter, goa, 0, maxLevel, acCount);
				goaWriter.println();
			}
		} finally {
			goaWriter.close();
		}
	}

	private GOAClassificationEntry getUnknown(GOAClassificationEntry goa,
			String[] accessNumbers, GOEntry unknown) {
		GOAClassificationEntry result = new GOAClassificationEntry();
		result.setAccession(unknown.getAccession());
		result.setName(unknown.getName());

		HashSet<String> total = new HashSet<String>(Arrays.asList(accessNumbers));

		total.removeAll(goa.getProteins());

		for (IGOEntry child : goa.getChildren()) {
			GOAClassificationEntry goaEntry = (GOAClassificationEntry) child;
			if (goaEntry.getAccession().equals(unknown.getAccession())) {
				result = goaEntry;
				goa.getChildren().remove(goaEntry);
				goa.getProteins().removeAll(goaEntry.getProteins());
				break;
			}
		}

		for (String ac : total) {
			result.getProteins().add(ac);
		}

		return result;
	}

	private void printGOADetails(PrintWriter pw, GOAClassificationEntry goa,
			Map<String, String> seqs) {
		if (goa.getProteins().size() > 0) {
			pw.println(goa.getAccession() + "\t" + goa.getName() + "\t"
					+ goa.getProteins().size());

			ArrayList<String> proteins = new ArrayList<String>();
			for (String protein : goa.getProteins()) {
				proteins.add(seqs.get(protein) + "_#_#_#" + protein);
			}
			Collections.sort(proteins);

			for (String protein : proteins) {
				String[] temp = protein.split("_#_#_#");
				if (temp.length == 1) {
					pw.println("\t" + temp[0]);
				} else {
					pw.println("\t" + temp[1] + "\t" + temp[0]);
				}
			}
			pw.println();

			for (IGOEntry childGO : goa.getChildren()) {
				printGOADetails(pw, (GOAClassificationEntry) childGO, seqs);
			}
		}
	}

	private void printGOADefinition(PrintWriter goaWriter,
			GOAClassificationEntry goa, int curLevel) {
		if (goa.getProteins().size() > 0) {
			for (int i = 0; i < curLevel; i++) {
				goaWriter.print("\t");
			}

			goaWriter.println(goa.getAccession() + "\t" + goa.getName() + "\t"
					+ goa.getDefinition());

			curLevel++;
			for (IGOEntry childGO : goa.getChildren()) {
				printGOADefinition(goaWriter, (GOAClassificationEntry) childGO,
						curLevel);
			}
		}
	}

	private void printGOAEntry(PrintWriter goaWriter, GOAClassificationEntry goa,
			int curLevel, int maxLevel, int totalCount) {
		if (goa.getProteins().size() > 0) {
			for (int i = 0; i < curLevel; i++) {
				goaWriter.print("\t");
			}

			String percent = df.format((double) goa.getProteins().size() * 100.0
					/ totalCount)
					+ "%";

			goaWriter.println(goa.getName() + "\t" + goa.getProteins().size() + "\t"
					+ percent);

			curLevel++;
			if (curLevel <= maxLevel) {
				goa.sortChildren();

				for (IGOEntry childGO : goa.getChildren()) {
					printGOAEntry(goaWriter, (GOAClassificationEntry) childGO, curLevel,
							maxLevel, goa.getProteins().size());
				}
			}
		}
	}

	private Map<String, String> getSequences(File fastaFile,
			SequenceDatabaseType dbType) throws NoSuchElementException, BioException,
			FileNotFoundException {
		System.out.print("Getting access numbers from fasta file ...");

		Map<String, String> result = AccessNumberFastaParser.getNameAndDescription(
				fastaFile.getAbsolutePath(), dbType);

		System.out.println(" succeed, including " + result.size() + " proteins.");

		return result;
	}

	public static void main(String[] args) throws Exception {
		String target = "F:\\Science\\Data\\HIPP\\summary\\hippocampi_2d_all\\hippocampi_2d_all.noredundant.unduplicated.fasta";
		// File target = new File("data/HIPP.noredundant.fasta");
		new GOAnnotationProcessorByGO(SequenceDatabaseType.IPI).process(target);
	}
}
