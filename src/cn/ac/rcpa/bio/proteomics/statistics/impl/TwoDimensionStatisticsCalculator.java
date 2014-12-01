/*
 * Created on 2005-1-18
 *
 *                    Rcpa development code
 *
 * Author Sheng QuanHu (qhsheng@sibs.ac.cn / shengqh@gmail.com)
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
public class TwoDimensionStatisticsCalculator<E> extends
		AbstractStatisticsCalculator<E> {
	private IClassification<E> cf1;

	private IClassification<E> cf2;

	private HashMap<String, HashMap<String, Integer>> counts;

	private int outputIndividual;

	/**
	 * outputIndividual: 0 -- none 1 -- first 2 -- second 3 -- both
	 * 
	 * @param cf1
	 *          IClassification
	 * @param cf2
	 *          IClassification
	 * @param outputIndividual
	 *          int
	 */
	public TwoDimensionStatisticsCalculator(IClassification<E> cf1,
			IClassification<E> cf2, int outputIndividual) {
		super();

		this.cf1 = cf1;
		this.cf2 = cf2;
		this.counts = new HashMap<String, HashMap<String, Integer>>();
		this.outputIndividual = outputIndividual;
	}

	final public void clear() {
		counts.clear();
	}

	final public void output(PrintWriter writer) throws IOException {
		outputBothClassificationResults(writer);

		if (outputIndividual > 0) {
			int itotalcount = 0;

			for (HashMap<String, Integer> map : counts.values()) {
				for (Integer count : map.values()) {
					itotalcount += count;
				}
			}

			if (outputIndividual == 1 || outputIndividual == 3) {
				outputFirstClassificationResults(writer, itotalcount);
			}
			if (outputIndividual == 2 || outputIndividual == 3) {
				outputSecondClassificationResults(writer, itotalcount);
			}
		}
	}

	/**
	 * @param writer
	 * @throws IOException
	 */
	private void outputBothClassificationResults(PrintWriter writer)
			throws IOException {
		writer.println(cf1.getPrinciple() + " / " + cf2.getPrinciple()
				+ " distribution");
		String[] cf1Values = cf1.getClassifications();
		String[] cf2Values = cf2.getClassifications();
		for (int i = 0; i < cf2Values.length; i++) {
			writer.print("\t" + cf2Values[i]);
		}
		writer.println();
		for (int i = 0; i < cf1Values.length; i++) {
			writer.print(cf1Values[i]);

			HashMap<String, Integer> found;
			if (counts.containsKey(cf1Values[i])) {
				found = counts.get(cf1Values[i]);
			} else {
				found = new HashMap<String, Integer>();
			}

			for (int j = 0; j < cf2Values.length; j++) {
				if (found.containsKey(cf2Values[j])) {
					writer.print("\t" + found.get(cf2Values[j]));
				} else {
					writer.print("\t0");
				}
			}

			writer.println();
		}
		writer.println();
	}

	private void outputFirstClassificationResults(PrintWriter writer,
			int itotalcount) throws IOException {
		writer.println(cf1.getPrinciple() + " distribution");
		String[] cf1Values = cf1.getClassifications();

		DecimalFormat df = new DecimalFormat("#.##");
		for (int i = 0; i < cf1Values.length; i++) {
			writer.print(cf1Values[i]);

			HashMap<String, Integer> found;
			if (counts.containsKey(cf1Values[i])) {
				found = counts.get(cf1Values[i]);
			} else {
				found = new HashMap<String, Integer>();
			}

			int currentCount = 0;
			for (Integer eachCount : found.values()) {
				currentCount += eachCount;
			}
			writer.println("\t" + currentCount + "\t"
					+ df.format((double) currentCount * 100 / itotalcount) + "%");
		}
		writer.println();
	}

	private void outputSecondClassificationResults(PrintWriter writer,
			int itotalcount) throws IOException {
		writer.println(cf2.getPrinciple() + " distribution");
		String[] cf2Values = cf2.getClassifications();

		DecimalFormat df = new DecimalFormat("#.##");
		for (int i = 0; i < cf2Values.length; i++) {
			writer.print(cf2Values[i]);

			int currentCount = 0;
			for (HashMap<String, Integer> map : counts.values()) {
				if (map.containsKey(cf2Values[i])) {
					currentCount += map.get(cf2Values[i]);
				}
			}
			writer.println("\t" + currentCount + "\t"
					+ df.format((double) currentCount * 100 / itotalcount) + "%");
		}
		writer.println();
	}

	public String getPrinciple() {
		return cf1.getPrinciple() + " / " + cf2.getPrinciple();
	}

	@Override
	protected void doProcess(E obj) {
		String key1 = cf1.getClassification(obj);
		String key2 = cf2.getClassification(obj);
		HashMap<String, Integer> found;
		if (counts.containsKey(key1)) {
			found = counts.get(key1);
		} else {
			found = new HashMap<String, Integer>();
			counts.put(key1, found);
		}

		if (found.containsKey(key2)) {
			found.put(key2, found.get(key2) + 1);
		} else {
			found.put(key2, 1);
		}
	}

}
