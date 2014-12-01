package cn.ac.rcpa.bio.sequest;

import java.util.HashMap;

import junit.framework.TestCase;

public class SequestDynamicModificationTest extends TestCase {
	public void testGetModificationMap() {
		SequestDynamicModification sdm = new SequestDynamicModification();
		sdm.getModifications()[0].setMarkChar('*');
		sdm.getModifications()[0].setAddMass(18.0);
		sdm.getModifications()[1].setMarkChar('#');
		sdm.getModifications()[1].setAddMass(-18.0);

		HashMap<Character, Double> expect = new HashMap<Character, Double>();
		expect.put('*', 18.0);
		expect.put('#', -18.0);

		assertEquals(expect, sdm.getModificationMap());
	}

}
