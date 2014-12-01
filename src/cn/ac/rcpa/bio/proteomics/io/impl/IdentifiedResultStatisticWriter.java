package cn.ac.rcpa.bio.proteomics.io.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

import cn.ac.rcpa.bio.proteomics.IIdentifiedResult;
import cn.ac.rcpa.bio.proteomics.io.IIdentifiedResultWriter;

public class IdentifiedResultStatisticWriter implements IIdentifiedResultWriter {
	private static IdentifiedResultStatisticWriter instance;

	public static IdentifiedResultStatisticWriter getInstance() {
		if (instance == null) {
			instance = new IdentifiedResultStatisticWriter();
		}
		return instance;
	}

	private IdentifiedResultStatisticWriter() {
	}

	class StatisticInfo {
		public int groupCount;

		public int proteinCount;

		public StatisticInfo() {
			groupCount = 0;
			proteinCount = 0;
		}
	}

	/**
	 * write
	 * 
	 * @param writer
	 *          PrintWriter
	 * @param identifiedResult
	 *          IIdentifiedResult
	 */
	public void write(PrintWriter writer, IIdentifiedResult identifiedResult)
			throws IOException {
		writer.println("----- summary -----");

		final int proteinCount = identifiedResult.getProteins().size();
		final int groupCount = identifiedResult.getProteinGroupCount();

		writer.println("Total protein       : " + proteinCount);
		writer.println("Total protein group : " + groupCount);
		writer
				.println("UniPepCount\tProteinGroupCount\tPercent\tProteinCount\tPercent");

		HashMap<Integer, StatisticInfo> map = new HashMap<Integer, StatisticInfo>();
		for (int i = 0; i < identifiedResult.getProteinGroupCount(); i++) {
			Integer uniPepCount = identifiedResult.getProteinGroup(i).getProtein(0)
					.getUniquePeptides().length;
			if (!map.containsKey(uniPepCount)) {
				map.put(uniPepCount, new StatisticInfo());
			}

			StatisticInfo si = (StatisticInfo) map.get(uniPepCount);
			si.groupCount++;
			si.proteinCount += identifiedResult.getProteinGroup(i).getProteinCount();
		}

		ArrayList<Integer> uniPepCounts = new ArrayList<Integer>(map.keySet());
		Collections.sort(uniPepCounts);

		DecimalFormat df = new DecimalFormat("##.##");
		for (Iterator iter = uniPepCounts.iterator(); iter.hasNext();) {
			Integer item = (Integer) iter.next();
			StatisticInfo si = (StatisticInfo) map.get(item);

			writer.println(item.intValue() + "\t" + si.groupCount + "\t"
					+ df.format((double) si.groupCount * 100 / (double) groupCount)
					+ "%\t" + si.proteinCount + "\t"
					+ df.format((double) si.proteinCount * 100 / (double) proteinCount)
					+ "%");
		}
	}

	/**
	 * write
	 * 
	 * @param filename
	 *          String
	 * @param identifiedResult
	 *          IIdentifiedResult
	 */
	public void write(String filename, IIdentifiedResult identifiedResult)
			throws IOException {
		PrintWriter fw = new PrintWriter(filename);
		try {
			write(fw, identifiedResult);
		} finally {
			fw.close();
		}
	}
}
