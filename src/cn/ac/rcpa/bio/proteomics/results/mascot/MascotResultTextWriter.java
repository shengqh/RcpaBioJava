package cn.ac.rcpa.bio.proteomics.results.mascot;

import java.io.PrintWriter;
import java.text.DecimalFormat;

public class MascotResultTextWriter {
	public void write(String resultFilename, MascotResult mr) throws Exception {
		DecimalFormat df = new DecimalFormat("0.######");
		DecimalFormat df4 = new DecimalFormat("0.##E0");
		PrintWriter pw = new PrintWriter(resultFilename);
		try {
			int groupIndex = 0;
			pw.println("\tName\tMass\tTotalScore\tPepCount\tDescription");
			pw
					.println("\tFileScan\tQuery\tObs\tMr(exp)\tMr(cal)\tDelta\tMiss\tScore\tPValue\tRank\tPeptide\tModification");
			for (MascotProteinGroup group : mr) {
				groupIndex++;

				int proteinIndex = 0;
				for (MascotProtein mp : group) {
					proteinIndex++;
					pw.println("$" + groupIndex + "-" + proteinIndex + "\t"
							+ mp.getProteinName() + "\t" + mp.getMW() + "\t" + mp.getScore()
							+ "\t" + mp.getPeptideCount() + "\t" + mp.getReference());

					if (proteinIndex == group.size()) {
						for (MascotPeptide mpep : mp.getPeptides()) {
							pw.println("\t"
									+ mpep.getPeakListInfo().getLongFilename()
									+ "dta\t"
									+ mpep.getQuery()
									+ "\t"
									+ mpep.getObservedMz()
									+ "\t"
									+ df.format((mpep.getExperimentalSingleChargeMass() - mpep
											.getPrecursorHydrogenMass()))
									+ "\t"
									+ df.format((mpep.getTheoreticalSingleChargeMass() - mpep
											.getPrecursorHydrogenMass())) + "\t"
									+ df.format(-mpep.getDiffToExperimentMass()) + "\t"
									+ mpep.getMissCleavage() + "\t" + mpep.getScore() + "\t"
									+ df4.format(mpep.getPValue()) + "\t" + mpep.getRank() + "\t"
									+ mpep.getSequence() + "\t" + mpep.getModification());
						}
					}
				}
			}
		} finally {
			pw.close();
		}
	}
}
