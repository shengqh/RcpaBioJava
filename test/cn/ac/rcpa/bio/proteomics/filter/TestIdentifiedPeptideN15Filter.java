package cn.ac.rcpa.bio.proteomics.filter;

import junit.framework.TestCase;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryPeptide;

public class TestIdentifiedPeptideN15Filter extends TestCase {

	public void testAccept() throws Exception {
		IdentifiedPeptideN15Filter filter = new IdentifiedPeptideN15Filter(
				"data/sequest.n15.params");
		BuildSummaryPeptide e = new BuildSummaryPeptide();

		e.setTheoreticalSingleChargeMass(1976.94945);
		e.setSequence("K.LTSIHEEGGVPHADDMIR.H");
		assertFalse(filter.accept(e));
		    
		e.setTheoreticalSingleChargeMass(2001.87475);
		assertTrue(filter.accept(e));
		    
		e.setTheoreticalSingleChargeMass(1992.94437);
		e.setSequence("K.LTSIHEEGGVPHADDM#IR.H");
		assertFalse(filter.accept(e));

		e.setTheoreticalSingleChargeMass(2017.86967);
		e.setSequence("K.LTSIHEEGGVPHADDM#IR.H");
		assertTrue(filter.accept(e));
	}

	public void testAcceptMultipleModification() throws Exception {
		IdentifiedPeptideN15Filter filter = new IdentifiedPeptideN15Filter(
				"data/Lep_37_15N_methyl_KR.sequest.params");
		
		BuildSummaryPeptide e = new BuildSummaryPeptide();

		e.setTheoreticalSingleChargeMass(1828.73554);
		e.setSequence("K.LDDDDDGDDTYK@EER.H");
		assertFalse(filter.accept(e));

		e.setTheoreticalSingleChargeMass(1833.66309);
		e.setSequence("K.LDDDDDGDDTYK#EER.H");
		assertTrue(filter.accept(e));

		e.setTheoreticalSingleChargeMass(1847.67874);
		e.setSequence("K.LDDDDDGDDTYK@EER.H");
		assertTrue(filter.accept(e));

		e.setTheoreticalSingleChargeMass(1861.69439);
		e.setSequence("K.LDDDDDGDDTYK^EER.H");
		assertTrue(filter.accept(e));

	}

}
