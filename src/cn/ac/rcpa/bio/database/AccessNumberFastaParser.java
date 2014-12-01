package cn.ac.rcpa.bio.database;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.biojava.bio.BioException;
import org.biojava.bio.seq.Sequence;
import org.biojava.bio.seq.SequenceIterator;

import cn.ac.rcpa.bio.utils.SequenceUtils;

/**
 * Get protein access number from fasta file
 * 
 * @author Li Long, Sheng Quan-Hu
 */
public class AccessNumberFastaParser {
	public static String[] getNames(String fastaFilename, SequenceDatabaseType dbType)
			throws FileNotFoundException, BioException, NoSuchElementException {
		ArrayList<String> result = new ArrayList<String>();
		SequenceIterator seqi = SequenceUtils.readFastaProtein(new BufferedReader(
				new FileReader(fastaFilename)));
		IAccessNumberParser parser = AccessNumberParserFactory.getParser(dbType);

		while (seqi.hasNext()) {
			Sequence seq = seqi.nextSequence();
			result.add(parser.getValue(seq.getName()));
		}

		return (String[]) result.toArray(new String[0]);
	}

	public static Map<String, String> getNameAndDescription(String fastaFilename,
			SequenceDatabaseType dbType) throws FileNotFoundException, BioException,
			NoSuchElementException {
		HashMap<String, String> result = new HashMap<String, String>();
		SequenceIterator seqi = SequenceUtils.readFastaProtein(new BufferedReader(
				new FileReader(fastaFilename)));
		IAccessNumberParser parser = AccessNumberParserFactory.getParser(dbType);

		while (seqi.hasNext()) {
			Sequence seq = seqi.nextSequence();
			String description = ((String) seq.getAnnotation().getProperty(
					"description_line")).trim();
			result.put(parser.getValue(description), description);
		}

		return result;
	}
}
