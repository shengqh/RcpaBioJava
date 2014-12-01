/*
 * Created on 2005-5-18
 *
 *                    Rcpa development code
 *
 * Author Sheng QuanHu(qhsheng@sibs.ac.cn / shengqh@gmail.com)
 * This code is developed by RCPA Bioinformatic Platform
 * http://www.proteomics.ac.cn/
 *
 */
package cn.ac.rcpa.bio.proteomics.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.biojava.bio.seq.Sequence;
import org.biojava.bio.seq.SequenceIterator;

import cn.ac.rcpa.bio.proteomics.IIdentifiedProtein;
import cn.ac.rcpa.bio.proteomics.IIdentifiedResult;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryPeptideHit;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryResult;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.io.BuildSummaryPeptideHitReader;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.io.BuildSummaryResultReader;
import cn.ac.rcpa.bio.utils.SequenceUtils;

public class TestIdentifiedResultUtils extends TestCase {
	@SuppressWarnings("unchecked")
	private void checkSequence(IIdentifiedResult ir, String fastaFilename)
			throws Exception {
		List<? extends IIdentifiedProtein> proteins = ir.getProteins();

		SequenceIterator seqI = SequenceUtils.readFastaProtein(new BufferedReader(
				new FileReader(fastaFilename)));

		while (seqI.hasNext()) {
			Sequence seq = null;
			try {
				seq = seqI.nextSequence();
			} catch (Exception ex) {
				throw new IllegalStateException(ex.getMessage());
			}

			for (IIdentifiedProtein protein : proteins) {
				if (seq.getName().startsWith(protein.getProteinName())) {
					assertEquals(protein.getSequence(), seq.seqString());
					proteins.remove(protein);
					break;
				}
			}
		}
		assertEquals(0, proteins.size());
	}

	public void testFillSequenceByName() throws Exception {
		BuildSummaryResult irByName = new BuildSummaryResultReader()
				.readOnly("data/parent_children.proteins");
		IdentifiedResultUtils.fillSequenceByName(irByName.getProteins(),
				"data/parent_children.proteins.fasta");

		checkSequence(irByName, "data/parent_children.proteins.fasta");
	}

	public void testGet_PureSequence_PeptideHit_Map() throws Exception {
		List<BuildSummaryPeptideHit> pephits = new BuildSummaryPeptideHitReader()
				.read("data/peptideUtilsTest.peptides");
		Map<String, List<BuildSummaryPeptideHit>> map = IdentifiedResultUtils
				.get_PureSequence_PeptideHit_Map(pephits);
		assertEquals(4, map.size());
	}
}
