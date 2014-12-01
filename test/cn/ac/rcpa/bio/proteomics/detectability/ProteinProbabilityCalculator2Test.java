package cn.ac.rcpa.bio.proteomics.detectability;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.TestCase;
import cn.ac.rcpa.utils.RcpaFileUtils;

public class ProteinProbabilityCalculator2Test extends TestCase {

	private void add(ProteinDetectabilityEntry pde, String pep,
			double detectability, int detected) {
		DetectabilityEntry de = new DetectabilityEntry();
		de.setDetectability(detectability);
		de.setScore(0.01);
		de.setPeptide(pep);
		if (1 == detected) {
			de.getExperimentals().add("TEST");
		}
		pde.getPeptideMap().put(pep, de);
	}

	private void add(ProteinDetectabilityEntry pde, String line) {
		Pattern p = Pattern.compile("(\\S+)\\s([\\d.]+)\\s([01])");
		Matcher matcher = p.matcher(line);
		matcher.find();
		String pep = matcher.group(1);
		double detectability = Double.parseDouble(matcher.group(2));
		int detected = Integer.parseInt(matcher.group(3));
		add(pde, pep, detectability, detected);
	}

	/*
	 * Test method for
	 * 'cn.ac.rcpa.bio.proteomics.detectability.ProteinProbabilityCalculator2.getProbability(ProteinDetectabilityEntry)'
	 */
	public void ttestGetProbability() {
		ProteinDetectabilityEntry pde = new ProteinDetectabilityEntry();
		add(pde, "PEP001", 0.877058, 0);
		add(pde, "PEP002", 0.828954, 0);
		add(pde, "PEP003", 0.764498, 0);
		add(pde, "PEP004", 0.746006, 0);
		add(pde, "PEP005", 0.744255, 1);

		add(pde, "PEP006", 0.729749, 1);
		add(pde, "PEP007", 0.724341, 0);
		add(pde, "PEP008", 0.692173, 1);

		add(pde, "PEP009", 0.67464, 0);
		add(pde, "PEP010", 0.671358, 1);
		add(pde, "PEP011", 0.629452, 0);
		add(pde, "PEP012", 0.617506, 0);
		add(pde, "PEP013", 0.602276, 0);
		add(pde, "PEP014", 0.585572, 0);
		add(pde, "PEP015", 0.575536, 1);
		add(pde, "PEP016", 0.573541, 0);
		add(pde, "PEP017", 0.56454, 0);
		add(pde, "PEP018", 0.559748, 0);
		add(pde, "PEP019", 0.529426, 0);
		add(pde, "PEP020", 0.506383, 0);
		add(pde, "PEP021", 0.484113, 1);
		add(pde, "PEP022", 0.481208, 0);
		add(pde, "PEP023", 0.478948, 1);

		System.out.println("Calc1 = "
				+ new ProteinProbabilityCalculator1().getProbability(pde));
		System.out.println("Calc2 = "
				+ new ProteinProbabilityCalculator2().getProbability(pde));
	}

	public void ttestGetProbability2() {
		ProteinDetectabilityEntry pde1 = new ProteinDetectabilityEntry();
		pde1.setName("REVERSED_18675");
		add(pde1, "AGEDTTM", 0.800564, 0);
		add(pde1, "STIDEGQEAVTATPSEENNK", 0.790362, 0);
		add(pde1, "DGIMPTEEGNGVR", 0.762819, 0);
		add(pde1, "GAEGFGYRPGLYLICQEER", 0.747513, 0);
		add(pde1, "GELHIEVTAGENPNSYGEGK", 0.679032, 0);
		add(pde1, "IIGGDEFLDEGK", 0.638994, 0);
		add(pde1, "MTAVGIDWAK", 0.627575, 0);
		add(pde1, "DIGIPIDHDEGEGVTFAVDR", 0.614156, 0);
		add(pde1, "LYCMALNLFAALLFSESAK", 0.59347, 0);
		add(pde1, "EEEMAQSDTGK", 0.566998, 0);
		add(pde1, "ENTVGESTK", 0.565706, 0);
		add(pde1, "ESLGYEMELWSVIK", 0.561083, 0);
		add(pde1, "ASEFENMLLQAEGR", 0.553963, 0);
		add(pde1, "GYQIVAQMYK", 0.549109, 0);
		add(pde1, "GLSFVFPENR", 0.4898, 0);
		add(pde1, "LTVEYILEANPEIGFKPK", 0.468792, 0);
		add(pde1, "FDLLEIEFFLTANSPIKPLSGASGYAYEPK", 0.450765, 0);
		add(pde1, "FMNAYIR", 0.431167, 0);
		add(pde1, "CLLHCIEGK", 0.43103, 0);
		add(pde1, "ENASDLGLAK", 0.42556, 0);
		add(pde1, "FYVTGK", 0.307324, 0);
		add(pde1, "VIAAQELK", 0.301487, 0);
		add(pde1, "QCMSIQLR", 0.248269, 0);
		add(pde1, "NQPNVELVK", 0.240786, 1);

		System.out.println(pde1.getName() + " -- "
				+ new ProteinProbabilityCalculator2().getProbability(pde1));

		pde1 = new ProteinDetectabilityEntry();
		pde1.setName("REVERSED_18675");
		add(pde1, "AGEDTTM", 0.800564, 0);
		add(pde1, "STIDEGQEAVTATPSEENNK", 0.790362, 0);
		add(pde1, "DGIMPTEEGNGVR", 0.762819, 0);
		add(pde1, "GAEGFGYRPGLYLICQEER", 0.747513, 0);
		add(pde1, "GELHIEVTAGENPNSYGEGK", 0.679032, 0);
		add(pde1, "IIGGDEFLDEGK", 0.638994, 0);
		add(pde1, "MTAVGIDWAK", 0.627575, 0);
		add(pde1, "DIGIPIDHDEGEGVTFAVDR", 0.614156, 0);
		add(pde1, "LYCMALNLFAALLFSESAK", 0.59347, 0);
		add(pde1, "EEEMAQSDTGK", 0.566998, 0);
		add(pde1, "ENTVGESTK", 0.565706, 0);
		add(pde1, "ESLGYEMELWSVIK", 0.561083, 0);
		add(pde1, "ASEFENMLLQAEGR", 0.553963, 0);
		add(pde1, "GYQIVAQMYK", 0.549109, 0);
		add(pde1, "NQPNVELVK", 0.240786, 1);

		System.out.println(pde1.getName() + " -- "
				+ new ProteinProbabilityCalculator2().getProbability(pde1));

		ProteinDetectabilityEntry pde2 = new ProteinDetectabilityEntry();
		pde2.setName("IPI00028413");

		add(pde2, "FTVSVNVAAGSK	0.832511	0");
		add(pde2, "TAFITNFTLTIDGVTYPGNVK	0.822801	0");
		add(pde2, "LIQDAVTGLTVNGQITGDK	0.796206	0");
		add(pde2, "NVAFVIDISGSMAGR	0.77306	0");
		add(pde2, "VTFELTYEELLK	0.747169	0");
		add(pde2, "DDALCLNIDEAPGTVLR	0.735838	0");
		add(pde2, "NMVVSFGDGVTFVVVLHQVWK	0.734861	0");
		add(pde2, "YHFVTPLTSMVVTKPEDNEDER	0.711847	0");
		add(pde2, "SLPEGVANGIEVYSTK	0.704086	0");
		add(pde2, "SCPTCTDSLLNGDFTITYDVNR	0.703746	0");
		add(pde2, "VSDIRPGSDPTKPDATLVVK	0.693567	0");
		add(pde2, "AVPSTFSWLDTVTVTQDGLSMMINR	0.686838	0");
		add(pde2, "MAFAWWPCLILALLSSLAASGFPR	0.685911	0");
		add(pde2, "GHGATNDLTFTEEVDMK	0.684136	0");
		add(pde2, "GMTNINDGLLR	0.67402	0");
		add(pde2, "EHLVQATPENLQEAR	0.665181	1");

		System.out.println(pde2.getName() + " -- Calc2 -- "
				+ new ProteinProbabilityCalculator2().getProbability(pde2));
	}

	public void ttestGetProbability3() {
		ProteinDetectabilityEntry pde = new ProteinDetectabilityEntry();
		add(pde, "PEP001", 0.9, 0);
		add(pde, "PEP002", 0.8, 0);
		add(pde, "PEP003", 0.7, 0);
		add(pde, "PEP004", 0.6, 1);
		add(pde, "PEP005", 0.5, 0);
		add(pde, "PEP006", 0.4, 0);
		add(pde, "PEP007", 0.3, 0);
		add(pde, "PEP008", 0.2, 1);

		System.out.println("Calc1 = "
				+ new ProteinProbabilityCalculator1().getProbability(pde));
		System.out.println("Calc2 = "
				+ new ProteinProbabilityCalculator2().getProbability(pde));

		pde = new ProteinDetectabilityEntry();
		add(pde, "PEP001", 0.9, 0);
		add(pde, "PEP004", 0.6, 1);
		add(pde, "PEP005", 0.5, 0);
		add(pde, "PEP008", 0.2, 1);

		System.out.println("Calc1 = "
				+ new ProteinProbabilityCalculator1().getProbability(pde));
		System.out.println("Calc2 = "
				+ new ProteinProbabilityCalculator2().getProbability(pde));
	}

	public void testGetProbabilityFromFile() throws IOException {
		String filename = "data/proteinDetectability.txt";
		String[] lines = RcpaFileUtils.readFile(filename, true);

		Map<String, ProteinDetectabilityEntry> proteinMap = new LinkedHashMap<String, ProteinDetectabilityEntry>();
		for (String line : lines) {
			String[] parts = line.split("\\t");
			if (5 == parts.length) {
				if (!proteinMap.containsKey(parts[0])) {
					ProteinDetectabilityEntry pde = new ProteinDetectabilityEntry();
					pde.setName(parts[0]);
					proteinMap.put(parts[0], pde);
				}
				add(proteinMap.get(parts[0]), parts[1], Double.parseDouble(parts[2]),
						Integer.parseInt(parts[3]));
			}
		}

		for (String proteinName : proteinMap.keySet()) {
			ProteinDetectabilityEntry pde = proteinMap.get(proteinName);
			System.out.println(proteinName);
			System.out.println("  Calc1 = "
					+ new ProteinProbabilityCalculator1().getProbability(pde));
			System.out.println("  Calc2 = "
					+ new ProteinProbabilityCalculator2().getProbability(pde));
		}
	}
}
