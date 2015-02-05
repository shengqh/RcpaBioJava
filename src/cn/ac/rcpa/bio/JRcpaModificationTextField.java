package cn.ac.rcpa.bio;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.ac.rcpa.bio.aminoacid.Aminoacids;
import cn.ac.rcpa.bio.proteomics.IsotopicType;
import cn.ac.rcpa.component.JRcpaTextField;

public class JRcpaModificationTextField extends JRcpaTextField {
	public JRcpaModificationTextField(String key, String title,
			String defaultValue, boolean required) {
		super(key, title, defaultValue, required);
	}

	public JRcpaModificationTextField(boolean required) {
		super("Modified", "Modified Aminoacid (input: C +15.99 Y -17.99)", "",
				required);
	}

	public Map<Character, Double> getModificationMap(IsotopicType iType) {
		Map<Character, Double> result = new HashMap<Character, Double>();

		Pattern pattern = Pattern.compile("(\\S)\\s+([+-0123456789.]+)");
		Aminoacids aas = new Aminoacids();
		aas.initTerminal();
		
		Matcher match = pattern.matcher(this.getText());
		while (match.find()) {
			char aa = match.group(1).charAt(0);

			double value = Double.parseDouble(match.group(2));
			if (iType == IsotopicType.Monoisotopic) {
				double staticValue = aas.get(aa).getMonoMass() + value;
				result.put(aa, staticValue);
			} else {
				double staticValue = aas.get(aa).getAverageMass() + value;
				result.put(aa, staticValue);
			}
		}

		return result;
	}

}
