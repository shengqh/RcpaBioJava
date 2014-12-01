package cn.ac.rcpa.bio.glycoproteomics;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.ac.rcpa.bio.proteomics.IsotopicType;
import cn.ac.rcpa.bio.sequest.SequestFilename;

public class SequestOutGenerator {
	private static String getIsotopicTypeString(IsotopicType iType){
		if(iType == IsotopicType.Average){
			return "AVG";
		}
		
		return "MONO";
	}
	
	public static List<String> generate(SequestFilename sf, double precursorMH,
			double precursorTolerance, IsotopicType precursorIsotopicType,
			IsotopicType fragmentationIsotopicType,
			Map<Character, Double> staticModifications,
			Map<String, Double> dynamicModifications) {
		List<String> result = new ArrayList<String>();

		DecimalFormat df = new DecimalFormat("0.0000");
		result.add(" " + sf.getLongFilename());
		result.add(" (M+H)+ mass = " + df.format(precursorMH) + " ~ " + df.format(precursorTolerance) + " (+1), fragment tol = 0.0000 , " + 
				getIsotopicTypeString(precursorIsotopicType) + "/" + getIsotopicTypeString(fragmentationIsotopicType));

		StringBuilder sb = new StringBuilder();
		
		for(String dMod:dynamicModifications.keySet()){
			double mod = dynamicModifications.get(dMod);
			String sign = mod > 0 ? "+" : "";
			sb.append(" (" + dMod + " " + sign + df.format(mod) + ")");
		}
		
		for(Character sMod:staticModifications.keySet()){
			double mod = staticModifications.get(sMod);
			sb.append(" " + sMod + "=" + df.format(mod));
		}
		sb.append(" Enzyme:Trypsin (2)");
		
		result.add(sb.toString());
		
		return result;
	}
}
