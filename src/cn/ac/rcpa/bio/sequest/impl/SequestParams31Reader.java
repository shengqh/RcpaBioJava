package cn.ac.rcpa.bio.sequest.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.ac.rcpa.bio.sequest.SequestEnzyme;
import cn.ac.rcpa.bio.sequest.SequestParams;

public class SequestParams31Reader extends AbstractSequestParamsReader {

	@Override
	protected void parseOptionFromMap(SequestParams result,
			HashMap<String, String> map) {
		super.parseOptionFromMap(result, map);
		result.setEnzyme_number(Integer.parseInt(map.get("enzyme_number")));
		result.setMax_num_differential_AA_per_mod(Integer.parseInt(map
				.get("max_num_differential_AA_per_mod")));
	}

	@Override
	protected void readEnzymeDefinition(BufferedReader br, SequestParams result)
			throws IOException {
		final String ENZYME_PATTERN = "\\S*\\s*(\\S*)\\s*(\\d)\\s*(\\S*)\\s*(\\S*)\\s*";
		final Pattern pattern = Pattern.compile(ENZYME_PATTERN);

		String line;
		result.clearEnzyme();
		while ((line = br.readLine()) != null) {
			Matcher match = pattern.matcher(line);
			if (match.find()) {
				SequestEnzyme enzyme = new SequestEnzyme(match.group(1), Integer
						.parseInt(match.group(2)), match.group(3), match.group(4));
				result.addEnzyme(enzyme);
			}
		}
	}
}
