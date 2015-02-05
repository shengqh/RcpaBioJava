/**
 * <p>Title: Aminoacids</p>
 * <p>Description: A class includes all aminoacids information</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: cn.ac.rcpa</p>
 * <p>Create: 2003-12-3 9:43:13
 * @author Sheng Quanhu(shengqh@gmail.com/shengqh@263.net)
 * @version 1.0
 *
 * http://www.expasy.org/tools/pscale/Hphob.Doolittle.html
 * Amino acid scale: Hydropathicity.
 * Author(s): Kyte J., Doolittle R.F.
 * Reference: J. Mol. Biol. 157:105-132(1982).
 * Amino acid scale values:
 * Ala:  1.800
 * Arg: -4.500
 * Asn: -3.500
 * Asp: -3.500
 * Cys:  2.500
 * Gln: -3.500
 * Glu: -3.500
 * Gly: -0.400
 * His: -3.200
 * Ile:  4.500
 * Leu:  3.800
 * Lys: -3.900
 * Met:  1.900
 * Phe:  2.800
 * Pro: -1.600
 * Ser: -0.800
 * Thr: -0.700
 * Trp: -0.900
 * Tyr: -1.300
 * Val:  4.200
 */

package cn.ac.rcpa.bio.aminoacid;

import cn.ac.rcpa.bio.utils.MassCalculator;

public class Aminoacids {
	private final static Aminoacids instance = new Aminoacids();

	private Aminoacid[] totalAminoacids;

	public final static Aminoacids getStableInstance() {
		return instance;
	}

	public Aminoacids() {
		super();

		totalAminoacids = new Aminoacid[128];
		for (int i = 0; i < 128; i++) {
			totalAminoacids[i] = new Aminoacid();
		}

		resetAminoacids();
	}

	/**
	 * @param aSequence
	 *            : pepitide sequence
	 * @return average peptide mass (add extra 18.0 to average residues mass)
	 */
	public double getAveragePeptideMass(String sequence) {
		return 18.0 + getAverageResiduesMass(sequence);
	}

	/**
	 * @param aSequence
	 *            : residues sequence
	 * @return average mass of total residues
	 */
	public double getAverageResiduesMass(String sequence) {
		double result = 0.0;
		for (int i = 0; i < sequence.length(); i++) {
			result += totalAminoacids[sequence.charAt(i)].getAverageMass();
		}
		return result;
	}

	public Aminoacid get(int index) {
		if (index < 0 || index >= 128) {
			throw new IndexOutOfBoundsException("Parameter error : index " + index + " out of range 0-127");
		}

		return totalAminoacids[index];
	}

	public int length() {
		return totalAminoacids.length;
	}

	/**
	 * @param aSequence
	 *            : pepitide sequence
	 * @return getMonoPeptideMass peptide mass (add extra 18.0 to monoisotopic
	 *         residues mass)
	 */
	public double getMonoPeptideMass(String sequence) {
		return 18.0 + getMonoResiduesMass(sequence);
	}

	/**
	 * @param aSequence
	 *            : residues sequence
	 * @return monoisotopic mass of total residues
	 */
	public double getMonoResiduesMass(String sequence) {
		double dRes = 0.0;
		for (int i = 0; i < sequence.length(); i++) {
			dRes += totalAminoacids[sequence.charAt(i)].getMonoMass();
		}
		return dRes;
	}

	public void reset() {
		resetAminoacids();
	}

	/**
	 * The method used in Construction should be set to private method to avoid
	 * surprised error caused by polymorphism.
	 */
	private void resetAminoacids() {
		for (int i = 0; i < 128; i++) {
			totalAminoacids[i].clear();
		}

		initStandard20Aminoacids();

		initAminoacidX();

		initAminoacidB();

		initAminoacidZ();
	}

	private void initStandard20Aminoacids() {
		totalAminoacids['G'].reset('G', "Gly", 57.02147, 57.05, -0.400, "Glycine C2H3NO", true);
		totalAminoacids['A'].reset('A', "Ala", 71.03712, 71.08, 1.8, "Alanine C3H5NO", true);
		totalAminoacids['S'].reset('S', "Ser", 87.03203, 87.08, -0.8, "Serine C3H5NO2", true);
		totalAminoacids['P'].reset('P', "Pro", 97.05277, 97.12, -1.6, "Proline C5H7NO", true);
		totalAminoacids['V'].reset('V', "Val", 99.06842, 99.13, 4.2, "Valine C5H9NO", true);
		totalAminoacids['T'].reset('T', "Thr", 101.04768, 101.10, -0.7, "Threonine C4H7NO2", true);
		totalAminoacids['C'].reset('C', "Cys", 103.00919, 103.14, 2.5, "Cysteine C3H5NOS", true);
		totalAminoacids['I'].reset('I', "Ile", 113.08407, 113.16, 4.5, "Isoleucine C6H11NO", true);
		totalAminoacids['L'].reset('L', "Leu", 113.08407, 113.16, 3.8, "Leucine C6H11NO", true);
		totalAminoacids['N'].reset('N', "Asn", 114.04293, 114.10, -3.5, "Asparagine C4H6N2O2", true);
		totalAminoacids['D'].reset('D', "Asp", 115.02695, 115.09, -3.5, "Aspartic acid C4H5NO3", true);
		totalAminoacids['Q'].reset('Q', "Gln", 128.05858, 128.13, -3.5, "Glutamine C5H8N2O2", true);
		totalAminoacids['K'].reset('K', "Lys", 128.09497, 128.17, -3.9, "Lysine C6H12N2O", true);
		totalAminoacids['E'].reset('E', "Glu", 129.04260, 129.12, -3.5, "Glutamic acid C5H7NO3", true);
		totalAminoacids['M'].reset('M', "Met", 131.04049, 131.19, 1.9, "Methionine C5H9NOS", true);
		totalAminoacids['H'].reset('H', "His", 137.05891, 137.14, -3.2, "Histidine C6H7N3O", true);
		totalAminoacids['F'].reset('F', "Phe", 147.06842, 147.18, 2.8, "Phenylalanine C9H9NO", true);
		totalAminoacids['R'].reset('R', "Arg", 156.10112, 156.19, -4.5, "Arginine C6H12N4O", true);
		totalAminoacids['Y'].reset('Y', "Tyr", 163.06333, 163.18, -1.3, "Tyrosine C9H9NO2", true);
		totalAminoacids['W'].reset('W', "Trp", 186.07932, 186.21, -0.9, "Tryptophan C11H10N2O", true);
	}

	private void initAminoacidZ() {
		totalAminoacids['Z'].reset('Z', "Glx", (totalAminoacids['Q'].getMonoMass() + totalAminoacids['E'].getMonoMass()) / 2,
				(totalAminoacids['Q'].getAverageMass() + totalAminoacids['E'].getAverageMass()) / 2, (totalAminoacids['Q'].getHydropathicity() + totalAminoacids['E'].getHydropathicity()) / 2,
				"Glutamine or Glutamic acid", true);
	}

	private void initAminoacidB() {
		totalAminoacids['B'].reset('B', "Asx", (totalAminoacids['D'].getMonoMass() + totalAminoacids['N'].getMonoMass()) / 2,
				(totalAminoacids['D'].getAverageMass() + totalAminoacids['N'].getAverageMass()) / 2, (totalAminoacids['D'].getHydropathicity() + totalAminoacids['N'].getHydropathicity()) / 2,
				"Aspartic acid or Asparagine", true);
	}

	private void initAminoacidX() {
		double totalMonoMass = 0.0, totalAverageMass = 0.0, totalAverageHydropathicity = 0.0;
		int icount = 0;
		for (int i = 0; i < 128; i++) {
			if (totalAminoacids[i].isVisible()) {
				icount++;
				totalMonoMass += totalAminoacids[i].getMonoMass();
				totalAverageMass += totalAminoacids[i].getAverageMass();
				totalAverageHydropathicity += totalAminoacids[i].getHydropathicity();
			}
		}
		final double averageMonoMass = totalMonoMass / icount;
		final double averageAverageMass = totalAverageMass / icount;
		final double averageHydrophthicity = totalAverageHydropathicity / icount;
		totalAminoacids['X'].reset('X', "Xaa", averageMonoMass, averageAverageMass, averageHydrophthicity, "Any amino acid", true);
	}

	/**
	 * @return all visible amino acids
	 */
	@Override
	public String toString() {
		StringBuffer res = new StringBuffer();
		for (int i = 0; i < 128; i++) {
			if (totalAminoacids[i].isVisible()) {
				res.append(totalAminoacids[i].toString()).append("\n");
			}
		}
		return res.toString();
	}

	public void initTerminal() {
		MassCalculator mcMono = new MassCalculator(true);
		totalAminoacids['['].setMonoMass(mcMono.getNTermMass());
		totalAminoacids[']'].setMonoMass(mcMono.getCTermMass());

		MassCalculator mcAve = new MassCalculator(true);
		totalAminoacids['['].setAverageMass(mcAve.getNTermMass());
		totalAminoacids[']'].setAverageMass(mcAve.getCTermMass());
	}
}
