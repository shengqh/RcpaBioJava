package cn.ac.rcpa.bio.proteomics.modification;

import java.util.ArrayList;
import java.util.List;

import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryPeptideHit;
import cn.ac.rcpa.bio.proteomics.utils.PeptideUtils;
import cn.ac.rcpa.utils.Pair;

public class ModificationUtils {
	private ModificationUtils() {
	}

	private static boolean isAminoacid(char c) {
		return Character.isLetter(c) && Character.isUpperCase(c);
	}

	private static boolean isModifiedAminoacid(String modifiedAminoacids, char c) {
		return -1 != modifiedAminoacids.indexOf(c);
	}

	public static Pair<String, Integer> getModificationInfo(
			String modifiedAminoacid, BuildSummaryPeptideHit pephit) {
		SequenceModificationSitePair pair = new SequenceModificationSitePair(
				modifiedAminoacid, pephit);

		List<Pair<Character, Character>> chars = new ArrayList<Pair<Character, Character>>(
				pair.getSiteList());
		String matchedSeq = PeptideUtils.getMatchPeptideSequence(pephit.getPeptide(
				0).getSequence());
		int icount = 0;
		for (int i = 0; i < matchedSeq.length(); i++) {
			char c = matchedSeq.charAt(i);
			if (isAminoacid(c)) {
				icount++;
				continue;
			}

			assert (i > 0);
			assert (matchedSeq.charAt(i - 1) == chars.get(icount - 1).fst);

			if (!isModifiedAminoacid(modifiedAminoacid, matchedSeq.charAt(i - 1))) {
				chars.get(icount - 1).snd = c;
			}
		}

		StringBuffer sbModifiedSequence = new StringBuffer();
		for (int i = 0; i < chars.size(); i++) {
			sbModifiedSequence.append(chars.get(i).fst);
			if (chars.get(i).snd != ' ') {
				sbModifiedSequence.append(chars.get(i).snd);
			}
		}

		return new Pair<String, Integer>(sbModifiedSequence.toString(), pair
				.getModifiedCount());
	}
}
