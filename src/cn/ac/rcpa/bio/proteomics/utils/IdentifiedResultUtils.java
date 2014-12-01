package cn.ac.rcpa.bio.proteomics.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.biojava.bio.seq.Sequence;
import org.biojava.bio.seq.SequenceIterator;

import cn.ac.rcpa.bio.proteomics.IIdentifiedPeptideHit;
import cn.ac.rcpa.bio.proteomics.IIdentifiedProtein;
import cn.ac.rcpa.bio.utils.SequenceUtils;

public class IdentifiedResultUtils {
	private IdentifiedResultUtils() {
	}

	public static void fillSequenceByName(
			List<? extends IIdentifiedProtein> prohits, String fastaFilename)
			throws FileNotFoundException {
		Map<String, IIdentifiedProtein> nameProteinMap = new HashMap<String, IIdentifiedProtein>();
		for (IIdentifiedProtein protein : prohits) {
			nameProteinMap.put(protein.getProteinName(), protein);
		}

		SequenceIterator seqI = SequenceUtils.readFastaProtein(new BufferedReader(
				new FileReader(fastaFilename)));
		while (seqI.hasNext()) {
			Sequence seq = null;
			try {
				seq = seqI.nextSequence();
			} catch (Exception ex) {
				throw new IllegalStateException(ex.getMessage());
			}

			if (nameProteinMap.containsKey(seq.getName())) {
				nameProteinMap.get(seq.getName()).setSequence(seq.seqString());
			}
		}

		for (IIdentifiedProtein protein : prohits) {
			if (protein.getSequence() == null) {
				throw new IllegalArgumentException("There is no sequence of protein "
						+ protein.getProteinName());
			}
		}
	}

	public static <E extends IIdentifiedPeptideHit> Map<String, List<E>> get_PureSequence_PeptideHit_Map(
			List<E> pephits) {
		final HashMap<String, List<E>> result = new HashMap<String, List<E>>();

		for (E pephit : pephits) {
			final String pureSeq = PeptideUtils.getPurePeptideSequence(pephit
					.getPeptide(0).getSequence());

			if (!result.containsKey(pureSeq)) {
				result.put(pureSeq, new ArrayList<E>());
			}
			result.get(pureSeq).add(pephit);
		}
		return result;
	}

}
