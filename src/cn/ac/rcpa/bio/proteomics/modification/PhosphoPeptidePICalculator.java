package cn.ac.rcpa.bio.proteomics.modification;

import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import cn.ac.rcpa.bio.annotation.StatisticRanges;
import cn.ac.rcpa.bio.processor.IFileProcessor;
import cn.ac.rcpa.bio.proteomics.classification.impl.PhosphoPeptideSequencePIClassification;
import cn.ac.rcpa.bio.proteomics.statistics.impl.OneDimensionStatisticsCalculator;
import cn.ac.rcpa.bio.proteomics.utils.PeptideUtils;
import cn.ac.rcpa.utils.Pair;
import cn.ac.rcpa.utils.RcpaFileUtils;

public class PhosphoPeptidePICalculator implements IFileProcessor {
	public PhosphoPeptidePICalculator() {
	}

	@Override
	public List<String> process(String originFile) throws Exception {
		String[] peptides = RcpaFileUtils.readFile(originFile, true);

		PhosphoPeptideSequencePIClassification pps = new PhosphoPeptideSequencePIClassification(
				StatisticRanges.getPIRange());

		String resultFilename = originFile + ".pi";
		DecimalFormat df4 = new DecimalFormat("0.00");

		List<String> peps = new ArrayList<String>();
		List<String> pep_pis = new ArrayList<String>();
		HashSet<Pair<String, Integer>> seqCountMap = new HashSet<Pair<String, Integer>>();
		for (int i = 0; i < peptides.length; i++) {
			String pureSeq = PeptideUtils.getPurePeptideSequence(peptides[i]);

			int phosphoNumber = pps.getPhosphoNumber(peptides[i]);
			Pair<String, Integer> p = new Pair<String, Integer>(pureSeq,
					phosphoNumber);
			if (seqCountMap.contains(p)) {
				continue;
			}
			seqCountMap.add(p);
			peps.add(peptides[i]);

			double outcome = pps.getPhosphoPI(peptides[i]);
			pep_pis.add(peptides[i] + "\t" + df4.format(outcome));
		}

		OneDimensionStatisticsCalculator<String> calc = new OneDimensionStatisticsCalculator<String>(
				pps);
		calc.process(peps);

		PrintWriter pw = new PrintWriter(resultFilename);
		try {
			calc.output(pw);
			pw.println();
			for (String line : pep_pis) {
				pw.println(line);
			}
		} finally {
			pw.close();
		}

		return Arrays.asList(new String[] { resultFilename });
	}

	public static void main(String[] args) throws Exception {
		if (args.length == 0) {
			System.out.println("Input peptide file as argument!");
			return;
		}

		for (int i = 0; i < args.length; i++) {
			new PhosphoPeptidePICalculator().process(args[i]);
		}
	}
}
