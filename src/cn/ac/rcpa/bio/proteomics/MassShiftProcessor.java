package cn.ac.rcpa.bio.proteomics;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import bijnum.BIJStats;
import cn.ac.rcpa.bio.processor.IFileProcessor;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryPeptide;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryPeptideHit;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryProteinGroup;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryResult;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.io.BuildSummaryResultReader;
import cn.ac.rcpa.utils.RcpaMathUtils;

public class MassShiftProcessor implements IFileProcessor {

	public List<String> process(String originFile) throws Exception {
		BuildSummaryResult br = new BuildSummaryResultReader().read(originFile);

		List<BuildSummaryProteinGroup> groups = new ArrayList<BuildSummaryProteinGroup>();

		for (BuildSummaryProteinGroup group : br.getProteinGroups()) {
			if (group.getProtein(0).getUniquePeptides().length >= 2) {
				groups.add(group);
			}
		}

		BuildSummaryResult good = new BuildSummaryResult();
		for (int i = 0; i < groups.size() / 2; i++) {
			good.addProteinGroup(groups.get(i));
		}

		List<BuildSummaryPeptideHit> pephits = good.getPeptideHits();
		List<Double> massShifts = new ArrayList<Double>();

		PrintWriter pw = new PrintWriter(originFile + ".massshift");
		try {
			pw.println("Mz\tShift");
			for (BuildSummaryPeptideHit pephit : pephits) {
				BuildSummaryPeptide peptide = pephit.getPeptide(0);
				double mzShift = peptide.getDiffToExperimentMass()
						/ peptide.getCharge();
				double ppmShift = PrecursorTolerance.mz2ppm(pephit.getPeptide(0)
						.getObservedMz(), mzShift);
				massShifts.add(ppmShift);
				pw.println(peptide.getObservedMz() + "\t" + ppmShift);
			}

			double[] massShiftArray = RcpaMathUtils.toDoubleArray(massShifts);
			double avg = BIJStats.avg(massShiftArray);
			double stdev = BIJStats.stdev(massShiftArray);
			pw.println();
			pw.println("Avg  =" + avg);
			pw.println("Stdev=" + stdev);

			Map<Integer, Integer> countmap = new LinkedHashMap<Integer, Integer>();

			int maxSize = 20;
			double factor = 1.0;
			for (int i = 0; i < maxSize; i++) {
				countmap.put(i, 0);
			}

			for (double ms : massShifts) {
				double fms = Math.abs(ms);
				int index = (int) (fms * factor);
				if (index >= maxSize) {
					index = maxSize - 1;
				}

				countmap.put(index, countmap.get(index) + 1);
			}

			pw.println();
			List<Integer> indexList = new ArrayList<Integer>(countmap.keySet());
			for (int i = 0; i < indexList.size(); i++) {
				int index = indexList.get(i);
				double from = index / factor;
				double to = (index + 1.0) / factor;
				if (i == indexList.size() - 1) {
					pw.println(from + "~\t" + countmap.get(index));
				} else {
					pw.println(from + "~" + to + "\t" + countmap.get(index));
				}
			}

		} finally {
			pw.close();
		}

		return null;
	}

	public static void main(String[] args) throws Exception {
		new MassShiftProcessor()
				.process("F:\\sqh\\Project\\yuanchao\\shotgun\\summary\\summary.20ppm.noredundant");
	}
}
