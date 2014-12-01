package cn.ac.rcpa.bio.annotation.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import org.biojava.bio.BioException;
import org.biojava.bio.seq.Sequence;
import org.biojava.bio.seq.SequenceIterator;

import cn.ac.rcpa.bio.processor.AbstractFileProcessor;
import cn.ac.rcpa.bio.utils.SequenceUtils;

abstract public class AbstractValueRangeAnnotationProcessor extends
		AbstractFileProcessor {
	protected double[] thresholds;

	public final double[] getThresholds() {
		return thresholds;
	}

	public AbstractValueRangeAnnotationProcessor(double[] thresholds) {
		if (thresholds == null || thresholds.length == 0) {
			throw new IllegalArgumentException(
					"Thresholds should not be null or empty!");
		}

		this.thresholds = new double[thresholds.length];
		for (int i = 0; i < thresholds.length; i++) {
			this.thresholds[i] = thresholds[i];
		}
	}

	private AbstractValueRangeAnnotationProcessor() {
	}

	public List<String> process(String originFile) throws Exception {
		final File fastaFile = new File(originFile);

		final File resultFile = getResultFile(fastaFile);

		writeCountToFile(resultFile, getCountFromFile(fastaFile));

		return Arrays.asList(new String[] { resultFile.getAbsolutePath() });
	}

	private void writeCountToFile(File resultFile, int[] count)
			throws IOException {
		if (!resultFile.getParentFile().exists()) {
			resultFile.getParentFile().mkdirs();
		}

		PrintWriter pw = new PrintWriter(new FileWriter(resultFile));
		pw.println("<" + thresholds[0] + "\t" + count[0]);
		for (int i = 1; i < thresholds.length; i++) {
			pw.println(thresholds[i - 1] + "~" + thresholds[i] + "\t" + count[i]);
		}
		pw.println(">" + thresholds[thresholds.length - 1] + "\t"
				+ count[thresholds.length]);
		pw.close();
	}

	private int[] getCountFromFile(File fastaFile) throws IOException,
			BioException, InterruptedException {
		int[] result = new int[thresholds.length + 1];
		Arrays.fill(result, 0);

		SequenceIterator seqi = SequenceUtils.readFastaProtein(new BufferedReader(
				new FileReader(fastaFile)));

		while (seqi.hasNext()) {
			checkInterrupted();

			Sequence seq = seqi.nextSequence();

			increaseCount(result, getValue(seq));
		}

		return result;
	}

	private void increaseCount(int[] count, double value) {
		for (int i = 0; i < thresholds.length; i++) {
			if (value < thresholds[i]) {
				count[i]++;
				return;
			}
		}

		count[count.length - 1]++;
	}

	/**
	 * 从一个FastaSequence对象中取出一个相关值，例如mw，pi，疏水性等等，用于统计
	 * 
	 * @param fs
	 *          FastaSequence
	 * @return double
	 */
	protected abstract double getValue(Sequence fs);

	/**
	 * 根据原始文件得到结果文件名
	 * 
	 * @param originFile
	 *          File
	 * @return File
	 */
	protected abstract File getResultFile(File originFile);
}
