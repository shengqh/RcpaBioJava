/*
 * Created on 2005-1-18
 *
 *                    Rcpa development code
 *
 * Author Sheng QuanHu(qhsheng@sibs.ac.cn / shengqh@gmail.com)
 * This code is developed by RCPA Bioinformatic Platform
 * http://www.proteomics.ac.cn/
 *
 */
package cn.ac.rcpa.bio.proteomics.statistics.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.HashMap;

import cn.ac.rcpa.bio.proteomics.classification.IClassification;

/**
 * @author sqh
 * 
 */
final public class OneDimensionStatisticsCalculator<E> extends
		AbstractStatisticsCalculator<E> {
	private IClassification<E> classification;

	private HashMap<String, Integer> counts;

	public OneDimensionStatisticsCalculator(IClassification<E> classification) {
		super();

		this.classification = classification;
		this.counts = new HashMap<String, Integer>();
	}

	public void clear() {
		counts.clear();
	}

	public void output(PrintWriter writer) throws IOException {
		writer.println(classification.getPrinciple() + " distribution");
		String[] classificationValues = classification.getClassifications();

		int itotalcount = 0;
		for (Integer count : counts.values()) {
			itotalcount += count;
		}

		DecimalFormat df = new DecimalFormat("#.##");
		for (int i = 0; i < classificationValues.length; i++) {
			int currentCount = 0;
			if (counts.containsKey(classificationValues[i])) {
				currentCount = counts.get(classificationValues[i]);
			}
			writer.println(classificationValues[i] + "\t" + currentCount + "\t"
					+ df.format((double) currentCount * 100 / itotalcount) + "%");
		}
	}

	public String getPrinciple() {
		return classification.getPrinciple();
	}

	@Override
	protected void doProcess(E obj) {
		String value = classification.getClassification(obj);
		if (counts.containsKey(value)) {
			counts.put(value, counts.get(value) + 1);
		} else {
			counts.put(value, 1);
		}
	}

}
