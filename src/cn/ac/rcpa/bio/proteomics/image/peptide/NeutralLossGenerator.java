/*
 * Created on 2005-11-26
 *
 *                    Rcpa development code
 *
 * Author Sheng QuanHu(qhsheng@sibs.ac.cn / shengqh@gmail.com)
 * This code is developed by RCPA Bioinformatic Platform
 * http://www.proteomics.ac.cn/
 *
 */
package cn.ac.rcpa.bio.proteomics.image.peptide;

import java.util.ArrayList;
import java.util.List;

public class NeutralLossGenerator {
	private NeutralLossGenerator() {
	}

	public static List<INeutralLossType> getTotalCombinationValues(
			List<INeutralLossType> nlTypes, int maxLevel) {
		List<INeutralLossType> result = new ArrayList<INeutralLossType>();
		for (int i = 1; i <= maxLevel; i++) {
			List<INeutralLossType> subResult = getCombinationValues(nlTypes, i);

			for (INeutralLossType aType : subResult) {
				addToList(result, aType);
			}
		}

		return result;
	}

	private static void addToList(List<INeutralLossType> result,
			INeutralLossType aType) {
		for (INeutralLossType oldType : result) {
			if (oldType.getMass() == aType.getMass()) {
				return;
			}
		}
		result.add(aType);
	}

	public static List<INeutralLossType> getCombinationValues(
			List<INeutralLossType> nlTypes, int numberOfCombination) {
		List<INeutralLossType> result = new ArrayList<INeutralLossType>();

		if (nlTypes.size() >= numberOfCombination) {
			if (numberOfCombination == 1) {
				for (INeutralLossType aType : nlTypes) {
					if (!result.contains(aType)) {
						result.add(aType);
					}
				}
			} else {
				List<INeutralLossType> subTypes = nlTypes.subList(1, nlTypes
						.size());
				INeutralLossType firstType = nlTypes.get(0);

				List<INeutralLossType> subs = getCombinationValues(subTypes,
						numberOfCombination - 1);

				for (INeutralLossType aType : subs) {
					CombinedNeutralLossType curType = new CombinedNeutralLossType(
							aType);
					if (curType.insertNeutralLossType(firstType)) {
						addToList(result, curType);
					}
				}

				List<INeutralLossType> combinedSubTypes = getCombinationValues(
						subTypes, numberOfCombination);
				for (INeutralLossType aType : combinedSubTypes) {
					addToList(result, aType);
				}
			}
		}
		return result;
	}
}
