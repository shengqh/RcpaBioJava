package cn.ac.rcpa.bio.proteomics.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.ac.rcpa.bio.sequest.SequestFilename;
import cn.ac.rcpa.bio.sequest.SequestParseException;

public class SlimIdentifiedPeptideReader {
	private String[] scoreNames;

	public SlimIdentifiedPeptideReader(String[] scoreNames) {
		this.scoreNames = scoreNames;
	}

	public List<SlimIdentifiedPeptide> read(String fileName)
			throws IOException, SequestParseException {
		List<SlimIdentifiedPeptide> result = new ArrayList<SlimIdentifiedPeptide>();

		BufferedReader sr = new BufferedReader(new FileReader(fileName));
		try {
			String line = sr.readLine().trim();
			String[] names = line.split("\t");
			Map<String, Integer> nameMap = new HashMap<String, Integer>();
			for (int i = 0; i < names.length; i++) {
				nameMap.put(names[i], i);
			}

			int chargeIndex = nameMap.get("Charge");

			int[] scoreIndeies = new int[scoreNames.length];
			for (int i = 0; i < scoreNames.length; i++) {
				if (nameMap.containsKey(scoreNames[i])) {
					scoreIndeies[i] = nameMap.get(scoreNames[i]);
				} else {
					scoreIndeies[i] = -1;
				}
			}

			int modificationIndex = -1;
			if (nameMap.containsKey("Modification")) {
				modificationIndex = nameMap.get("Modification");
			}

			while ((line = sr.readLine()) != null) {
				line = line.trim();

				if (line.length() == 0) {
					break;
				}

				String[] parts = line.split("\t");
				SequestFilename sf = SequestFilename
						.parseShortFilename(parts[0]);
				sf.setCharge(Integer.parseInt(parts[chargeIndex]));

				String sequence = parts[1].split(" ! ")[0];

				SlimIdentifiedPeptide peptide = new SlimIdentifiedPeptide(
						sequence, sf);

				for (int i = 0; i < scoreIndeies.length; i++) {
					if (scoreIndeies[i] != -1) {
						peptide.getScoreMap().put(scoreNames[i],
								parts[scoreIndeies[i]]);
					}
				}
				
				if(modificationIndex >= 0){
					peptide.setModification(parts[modificationIndex]);
				}

				result.add(peptide);
			}
		} finally {
			sr.close();
		}
		return result;
	}
}
