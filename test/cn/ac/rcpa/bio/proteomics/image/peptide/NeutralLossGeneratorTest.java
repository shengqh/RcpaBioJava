package cn.ac.rcpa.bio.proteomics.image.peptide;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;

public class NeutralLossGeneratorTest extends TestCase {

	private void check(List<INeutralLossType> comb1, double mass, String name) {
		for (INeutralLossType aType : comb1) {
			if (aType.getMass() == mass && aType.getName().equals(name)) {
				return;
			}
		}
		Assert.fail("Cannot find mass = " + mass + " ; name = " + name);
	}

	public void testGetCombination() {
		ArrayList<INeutralLossType> nlTypes = new ArrayList<INeutralLossType>();
		nlTypes.add(new NeutralLossType(10.0, "10", true));
		nlTypes.add(new NeutralLossType(10.0, "10", true));
		nlTypes.add(new NeutralLossType(200.0, "200", true));
		nlTypes.add(new NeutralLossType(3000.0, "3000", true));
		nlTypes.add(new NeutralLossType(40000.0, "40000", true));

		List<INeutralLossType> comb = NeutralLossGenerator
				.getCombinationValues(nlTypes, 1);
		assertEquals(4, comb.size());
		check(comb, 10, "10");
		check(comb, 200, "200");
		check(comb, 3000, "3000");
		check(comb, 40000, "40000");

		comb = NeutralLossGenerator.getCombinationValues(nlTypes, 2);
		assertEquals(7, comb.size());
		check(comb, 20, "10-10");
		check(comb, 210, "10-200");
		check(comb, 3010, "10-3000");
		check(comb, 40010, "10-40000");
		check(comb, 3200, "200-3000");
		check(comb, 40200, "200-40000");
		check(comb, 43000, "3000-40000");

		comb = NeutralLossGenerator.getCombinationValues(nlTypes, 3);
		assertEquals(7, comb.size());

		comb = NeutralLossGenerator.getCombinationValues(nlTypes, 4);
		assertEquals(4, comb.size());

		comb = NeutralLossGenerator.getCombinationValues(nlTypes, 5);
		assertEquals(1, comb.size());
		
		nlTypes.clear();
		nlTypes.add(new NeutralLossType(10.0, "10", false));
		nlTypes.add(new NeutralLossType(10.0, "10", false));
		nlTypes.add(new NeutralLossType(200.0, "200", true));
		nlTypes.add(new NeutralLossType(3000.0, "3000", true));
		nlTypes.add(new NeutralLossType(40000.0, "40000", true));
		
		comb = NeutralLossGenerator.getCombinationValues(nlTypes, 2);
		assertEquals(6, comb.size());
		//no 10-10 type
		check(comb, 210, "10-200");
		check(comb, 3010, "10-3000");
		check(comb, 40010, "10-40000");
		check(comb, 3200, "200-3000");
		check(comb, 40200, "200-40000");
		check(comb, 43000, "3000-40000");
		
	}

}
