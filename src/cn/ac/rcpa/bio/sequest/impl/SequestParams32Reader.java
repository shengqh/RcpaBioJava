package cn.ac.rcpa.bio.sequest.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.ac.rcpa.bio.sequest.EnzymeLimitType;
import cn.ac.rcpa.bio.sequest.SequestEnzyme;
import cn.ac.rcpa.bio.sequest.SequestParams;

public class SequestParams32Reader extends AbstractSequestParamsReader {
	@Override
	protected void parseOptionFromMap(SequestParams result,
			HashMap<String, String> map) {
		super.parseOptionFromMap(result, map);

		if (!map.containsKey("enzyme_info")) {
			throw new IllegalArgumentException(
					"There is no enzyme_info in file! It maybe is old version.");
		}

		final String ENZYME_PATTERN = "(\\S+)\\s+(\\d)\\s+(\\d)\\s+(\\S+)\\s+(\\S+)";
		final Pattern pattern = Pattern.compile(ENZYME_PATTERN);

		String line = map.get("enzyme_info").trim();
		result.clearEnzyme();
		Matcher match = pattern.matcher(line);
		if (!match.find()) {
			throw new IllegalArgumentException(
					"Cannot parse enzyme from enzyme_info line : " + line);
		}

		SequestEnzyme enzyme = new SequestEnzyme(match.group(1), Integer
				.parseInt(match.group(3)), match.group(4), match.group(5));
		result.addEnzyme(enzyme);
		result.setEnzyme_number(0);

		int enzymeLimitTypeOrder = Integer.parseInt(match.group(2));
		switch (enzymeLimitTypeOrder) {
		case 2:
			result.setEnzymeLimitType(EnzymeLimitType.Partially);
			break;
		case 3:
			result.setEnzymeLimitType(EnzymeLimitType.NTerminal);
			break;
		case 4:
			result.setEnzymeLimitType(EnzymeLimitType.CTerminal);
			break;
		default:
			result.setEnzymeLimitType(EnzymeLimitType.Full);
			break;
		}

		result.setMax_num_differential_per_peptide(Integer.parseInt(map
				.get("max_num_differential_per_peptide")));
	}

	@Override
	protected void readEnzymeDefinition(BufferedReader br, SequestParams result)
			throws IOException {
	}
}
