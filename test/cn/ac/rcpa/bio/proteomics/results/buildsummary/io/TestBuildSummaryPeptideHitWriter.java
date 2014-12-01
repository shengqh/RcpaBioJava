package cn.ac.rcpa.bio.proteomics.results.buildsummary.io;

import java.io.File;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.io.FileUtils;

import cn.ac.rcpa.bio.proteomics.FollowCandidatePeptide;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryPeptide;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryPeptideHit;

public class TestBuildSummaryPeptideHitWriter extends TestCase {
	private void doTest(String oldFilename) throws Exception {
		String newFilename = oldFilename + ".tmp";
		List<BuildSummaryPeptideHit> pephits = new BuildSummaryPeptideHitReader()
				.read(oldFilename);

		BuildSummaryPeptideHitWriter pephitWriter = new BuildSummaryPeptideHitWriter();

		pephitWriter.write(newFilename, pephits);
		assertTrue(FileUtils.contentEquals(new File(oldFilename), new File(
				newFilename)));
		new File(newFilename).delete();
	}

	public void testWrite() throws Exception {
		doTest("data/HIPP2.peptides");
	}

	private BuildSummaryPeptideHit hit;

	private BuildSummaryPeptideHitWriter writer;

	@Override
	public void setUp() {
		writer = new BuildSummaryPeptideHitWriter();

		hit = new BuildSummaryPeptideHit();
		hit.addPeptide(new BuildSummaryPeptide());
		hit.addPeptide(new BuildSummaryPeptide());
		hit.getPeptide(0).setSequence("A.AAAAAA.A");
		hit.getPeptide(0).addProteinName("ProteinA");
		hit.getPeptide(1).setSequence("B.BBBBBB.B");
		hit.getPeptide(1).addProteinName("ProteinB1");
		hit.getPeptide(1).addProteinName("ProteinB2");
		hit.getFollowCandidates().add(
				new FollowCandidatePeptide("R.PGPGPDSDS*DSDSDREEQEEEEEDQR.N", 3.3573,
						0.0709));
		hit.getFollowCandidates().add(
				new FollowCandidatePeptide("R.PGPGPDS*DSDSDSDREEQEEEEEDQR.N", 3.2714,
						0.0947));
	}

	public void testGetFollowCandidates() {
		assertEquals(
				"R.PGPGPDSDS*DSDSDREEQEEEEEDQR.N(3.3573,0.0709) ! R.PGPGPDS*DSDSDSDREEQEEEEEDQR.N(3.2714,0.0947)",
				writer.getFollowCandidates(hit));
	}

	public void testGetPeptideSequence() {
		assertEquals("A.AAAAAA.A ! B.BBBBBB.B", writer.getPeptideSequence(hit));
	}

	public void testGetProteinInfo() {
		assertEquals("ProteinA ! ProteinB1/ProteinB2", writer.getProteinInfo(hit));
	}

	public void testGetUniquePeptideCount() throws Exception {
		List<BuildSummaryPeptideHit> pephits = new BuildSummaryPeptideHitReader()
				.read("data/TestPhospho2.peptides");
		assertEquals(1, writer.getUniquePeptideCount(pephits));
	}
}
