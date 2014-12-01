package cn.ac.rcpa.bio.proteomics.results.buildsummary.io;

import java.util.List;

import junit.framework.TestCase;
import cn.ac.rcpa.bio.proteomics.FollowCandidatePeptide;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryPeptide;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryPeptideHit;

public class TestBuildSummaryModifiedPeptideHitWriter extends TestCase {

	private BuildSummaryPeptideHit hit;

	private BuildSummaryModifiedPeptideHitWriter writer;
	@Override
	public void setUp() {
		writer = new BuildSummaryModifiedPeptideHitWriter("STY");

		hit = new BuildSummaryPeptideHit();
		hit.addPeptide(new BuildSummaryPeptide());
		hit.addPeptide(new BuildSummaryPeptide());
		hit.getPeptide(0).setSequence("R.PGPGPDSDSDS*DSDREEQEEEEEDQR.N");
		hit.getPeptide(0).addProteinName("ProteinA");
		hit.getPeptide(0).setXcorr(6.0);
		hit.getPeptide(0).setDeltacn(0.5);
		hit.getPeptide(1).setSequence("R.PGPGPDSDSDSDS*DREEQEEEEEDQR.N");
		hit.getPeptide(1).addProteinName("ProteinB1");
		hit.getPeptide(1).addProteinName("ProteinB2");
		hit.getPeptide(1).setXcorr(6.0);
		hit.getPeptide(1).setDeltacn(0.5);
		hit.getFollowCandidates().add(
				new FollowCandidatePeptide("R.PGPGPDSDS*DSDSDREEQEEEEEDQR.N", 3.3573,
						0.0709));
		hit.getFollowCandidates().add(
				new FollowCandidatePeptide("R.PGPGPDS*DSDSDSDREEQEEEEEDQR.N", 3.2714,
						0.0947));
	}

	public void testGetFollowCandidates() {
		assertEquals(
				"R.PGPGPDSDSDS*DSDREEQEEEEEDQR.N(6.0000,0.5000) ! R.PGPGPDSDSDSDS*DREEQEEEEEDQR.N(6.0000,0.5000) ! R.PGPGPDSDS*DSDSDREEQEEEEEDQR.N(3.3573,0.0709) ! R.PGPGPDS*DSDSDSDREEQEEEEEDQR.N(3.2714,0.0947)",
				writer.getFollowCandidates(hit));
	}

	public void testGetPeptideSequence() {
		assertEquals("PGPGPDSpDSpDSpDSpDREEQEEEEEDQR", writer.getPeptideSequence(hit));
	}

	public void testGetProteinInfo() {
		assertEquals("ProteinA", writer.getProteinInfo(hit));
	}

	public void testGetUniqueModifiedPeptideCount() throws Exception {
		List<BuildSummaryPeptideHit> pephits = new BuildSummaryPeptideHitReader()
				.read("data/TestPhospho2.peptides");
		assertEquals(4, writer.getUniqueModifiedPeptideCount(pephits));
	}

}
