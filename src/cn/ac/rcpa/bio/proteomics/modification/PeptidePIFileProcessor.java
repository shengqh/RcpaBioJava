package cn.ac.rcpa.bio.proteomics.modification;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import picalculator.pIcalculator;
import cn.ac.rcpa.bio.processor.IFileProcessor;
import cn.ac.rcpa.bio.proteomics.utils.PeptideUtils;
import cn.ac.rcpa.utils.RcpaFileUtils;

public class PeptidePIFileProcessor implements IFileProcessor {
	private boolean auto = true;
	private String AApI = "expasy";
	private String pKaSet = "scansite";
	private boolean methylated = false;
	private int NRpep = 1;

	public PeptidePIFileProcessor() {
	}

	@Override
	public List<String> process(String originFile) throws Exception {
		pIcalculator pc = new pIcalculator();
		pc.setAApI(AApI);
		pc.setpKaSet(pKaSet);
		pc.setMethylatedPeptides(methylated);

		String resultFilename = RcpaFileUtils.changeExtension(originFile,
				".pi.peptides");
		DecimalFormat df4 = new DecimalFormat("0.00");

		BufferedReader br = new BufferedReader(new FileReader(originFile));
		try {
			// read header
			String line = br.readLine();
			if (line.trim().length() == 0) {
				throw new Exception("Cannot find header information from file "
						+ originFile);
			}

			// parse header by tab
			HashMap<String, Integer> headerMap = new HashMap<String, Integer>();
			String[] headers = line.split("\t");
			for (int i = 0; i < headers.length; i++) {
				headerMap.put(headers[i], i);
			}

			// get pi index
			int piIndex = 0;
			if (headerMap.containsKey("PI")) {
				piIndex = headerMap.get("PI");
			} else {
				throw new Exception("Cannot find pi column in peptides file!");
			}

			// get modification index (mascot only)
			boolean bMascot = headerMap.containsKey("Modification");
			int modIndex = 0;
			if (bMascot) {
				modIndex = headerMap.get("Modification");
			}

			PrintWriter pw = new PrintWriter(resultFilename);
			try {
				// write header
				pw.println(line);

				HashMap<String, Double> piMap = new HashMap<String, Double>();

				while ((line = br.readLine()) != null) {
					if (line.trim().length() == 0) {
						break;
					}

					String[] parts = line.split("\t");

					String pepti = PeptideUtils
							.getMatchPeptideSequence(parts[2]);

					double pi;
					if (piMap.containsKey(pepti)) {
						pi = piMap.get(pepti);
					} else {
						boolean bModified = true;
						if (bMascot) {
							bModified = (parts.length > modIndex)
									&& parts[modIndex].contains("Phospho");
						}

						String modPeptide;
						if (bModified) {
							SequenceModificationSitePair pair = new SequenceModificationSitePair(
									"STY", pepti, 'p');
							modPeptide = pair.getModifiedSequence();
						} else {
							modPeptide = PeptideUtils
									.getPurePeptideSequence(pepti);
						}

						if (auto) {
							pc.setPhosfoNumber(pc.getPhosphoNumber(modPeptide));
						} else {
							pc.setPhosfoNumber(NRpep);
						}
						pi = pc.calculate(modPeptide);
						piMap.put(pepti, pi);
					}

					parts[piIndex] = df4.format(pi);
					pw.println(StringUtils.join(parts, '\t'));
				}
			} finally {
				pw.close();
			}
		} finally {
			br.close();
		}

		return Arrays.asList(new String[] { resultFilename });
	}

	public static void main(String[] args) throws Exception {
		// new PeptidePIFileProcessor()
		// .process("E:\\sqh\\ScienceTools\\JavaApplications\\1.peptides");

		if (args.length == 0) {
			System.out.println("Input peptide file as argument!");
			return;
		}

		for (int i = 0; i < args.length; i++) {
			new PeptidePIFileProcessor().process(args[i]);
		}
	}
}
