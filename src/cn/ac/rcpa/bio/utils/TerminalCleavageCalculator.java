package cn.ac.rcpa.bio.utils;

import org.biojava.bio.proteomics.Protease;

public class TerminalCleavageCalculator {
	private String cleaveageResidues;

	private String notCleaveResidues;

	private boolean isEndoProtease;

	public TerminalCleavageCalculator(Protease protease) {
		super();

		cleaveageResidues = protease.getCleaveageResidues().seqString();
		notCleaveResidues = protease.getNotCleaveResidues().seqString();
		isEndoProtease = protease.isEndoProtease();
	}

	public boolean isCleavage(char first, char second) {
		if ('-' == first || '-' == second) {
			return true;
		}

		if (isEndoProtease) {
			return -1 != cleaveageResidues.indexOf(first)
					&& -1 == notCleaveResidues.indexOf(second);
		} else {
			return -1 != cleaveageResidues.indexOf(second)
					&& -1 == notCleaveResidues.indexOf(first);
		}
	}

	public boolean isNtermCleavage(String sequence){
		checkSequence(sequence);
		char nterm = sequence.charAt(0);
		char firstchar = sequence.charAt(2);

		return isCleavage(nterm, firstchar);
	}
	
	public boolean isCtermCleavage(String sequence){
		checkSequence(sequence);
		char cterm = sequence.charAt(sequence.length() - 1);
		char lastchar = sequence.charAt(sequence.length() - 3);

		return isCleavage(lastchar, cterm);
	}
	
	public int getCount(String sequence) {
		int result = 0;

		if (isNtermCleavage(sequence)) {
			result++;
		}

		if (isCtermCleavage(sequence)) {
			result++;
		}

		return result;
	}

	private void checkSequence(String sequence) {
		if (sequence.length() < 5 || sequence.charAt(1) != '.'
				|| sequence.charAt(sequence.length() - 2) != '.') {
			throw new IllegalArgumentException(
					"Not a valid sequence. (should like C.NPGLAEIIAER.I) : " + sequence);
		}
	}
}
