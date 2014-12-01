package cn.ac.rcpa.bio.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.support.ArgumentConvertingMethodInvoker;

import cn.ac.rcpa.bio.aminoacid.Aminoacids;
import cn.ac.rcpa.bio.proteomics.IonType;
import cn.ac.rcpa.bio.proteomics.Peak;
import cn.ac.rcpa.bio.proteomics.SequenceValidateException;

public class MassCalculator {
	/**
	 * Constant value of Carbon monoisotopic mass
	 */
	public static final double Cmono = 12.00000;

	/**
	 * Constant value of Hydrogen monoisotopic mass
	 */
	public static final double Hmono = 1.0078250;

	/**
	 * Constant value of Nitrogen monoisotopic mass
	 */
	public static final double Nmono = 14.0030740;

	/**
	 * Constant value of Oxygen monoisotopic mass
	 */
	public static final double Omono = 15.9949146;

	/**
	 * Constant value of Carbon average mass
	 */
	public static final double Cavg = 12.011;

	/**
	 * Constant value of Hydrogen average mass
	 */
	public static final double Havg = 1.00794;

	/**
	 * Constant value of Nitrogen average mass
	 */
	public static final double Navg = 14.00674;

	/**
	 * Constant value of Oxygen average mass
	 */
	public static final double Oavg = 15.9994;

	private double Cmass;

	private double Hmass;

	private double Omass;

	private double Nmass;

	private double staticAminoacidMass[] = new double[128];

	private double dynamicModificationMass[] = new double[128];

	private boolean[] dynamicAminoacid = new boolean[128];

	private int dynamicAminoacidCount = 0;

	private boolean monoIsotopic;

	private double cTermMass;

	private double nTermMass;

	public void setCTermMass(double cTermMass) {
		this.cTermMass = cTermMass;
	}

	public void setNTermMass(double nTermMass) {
		this.nTermMass = nTermMass;
	}

	public double getCTermMass() {
		return cTermMass;
	}

	public double getNTermMass() {
		return nTermMass;
	}

	private void resetAminoacids() {
		for (int i = 0; i < 128; i++) {
			dynamicModificationMass[i] = 0.0;

			if (Aminoacids.getStableInstance().get(i).isVisible()) {
				if (monoIsotopic) {
					staticAminoacidMass[i] = Aminoacids.getStableInstance()
							.get(i).getMonoMass();
				} else {
					staticAminoacidMass[i] = Aminoacids.getStableInstance()
							.get(i).getAverageMass();
				}
			} else {
				staticAminoacidMass[i] = 0.0;
			}
		}
	}

	private void resetCHON() {
		if (monoIsotopic) {
			Cmass = Cmono;
			Hmass = Hmono;
			Omass = Omono;
			Nmass = Nmono;
		} else {
			Cmass = Cavg;
			Hmass = Havg;
			Omass = Oavg;
			Nmass = Navg;
		}
	}

	private void resetTermMass() {
		this.cTermMass = Hmass + Omass;
		this.nTermMass = Hmass;
	}

	public MassCalculator(boolean monoIsotopic) {
		this.monoIsotopic = monoIsotopic;
		resetCHON();
		resetTermMass();
		resetAminoacids();
	}

	public MassCalculator(boolean monoIsotopic, double cTermMass,
			double nTermMass) {
		this.monoIsotopic = monoIsotopic;

		resetCHON();

		this.cTermMass = cTermMass;
		this.nTermMass = nTermMass;

		resetAminoacids();
	}

	private double getTermMass() {
		return cTermMass + nTermMass;
	}

	public void resetMass() {
		resetTermMass();
		resetAminoacids();
	}

	public void addStaticModification(char aminoacid, double actualMass) {
		staticAminoacidMass[aminoacid] = actualMass;
	}

	public void addStaticModifications(Map<Character, Double> map) {
		for (Character ch : map.keySet()) {
			addStaticModification(ch, map.get(ch));
		}
	}

	public void removeStaticModification(char aminoacid) {
		if (monoIsotopic) {
			staticAminoacidMass[aminoacid] = Aminoacids.getStableInstance()
					.get(aminoacid).getMonoMass();
		} else {
			staticAminoacidMass[aminoacid] = Aminoacids.getStableInstance()
					.get(aminoacid).getAverageMass();
		}
	}

	public double getMass(String proteinSeq) {
		validateSequence(proteinSeq);

		double result = getTermMass();
		for (int i = 0; i < proteinSeq.length(); i++) {
			result += staticAminoacidMass[proteinSeq.charAt(i)];
		}

		result += getDynamicModificationMass(proteinSeq);
		return result;
	}

	public double getMassSafe(String proteinSeq) {
		validateSequenceEmpty(proteinSeq);
		validateSequenceFirstChar(proteinSeq);

		double result = getTermMass();
		for (int i = 0; i < proteinSeq.length(); i++) {
			result += staticAminoacidMass[proteinSeq.charAt(i)];
		}

		result += getDynamicModificationMass(proteinSeq);
		return result;
	}

	private double getDynamicModificationMass(String proteinSeq) {
		if (!isDynamicModificationExists()) {
			return 0.0;
		}

		double result = 0.0;
		for (int i = 0; i < proteinSeq.length(); i++) {
			if (isDynamicModificationChar(proteinSeq, i)) {
				result += getDynamicModificationCharMass(proteinSeq, i);
			}
		}
		return result;
	}

	/**
	 * Add dynamic modification for character markChar. markChar can be
	 * aminoacid or special character such like '*', '#' and so on
	 * 
	 * @param markChar
	 * @param addMass
	 */
	public void addDynamicModification(char markChar, double addMass) {
		if (addMass == 0.0) {
			removeDynamicModification(markChar);
		} else {
			if (!dynamicAminoacid[markChar]) {
				dynamicAminoacid[markChar] = true;
				dynamicAminoacidCount++;
			}
			dynamicModificationMass[markChar] = addMass;
		}
	}

	public void addDynamicModifications(Map<Character, Double> map) {
		for (Character ch : map.keySet()) {
			addDynamicModification(ch, map.get(ch));
		}
	}

	public void removeDynamicModification(char markChar) {
		if (dynamicModificationMass[markChar] != 0.0) {
			dynamicModificationMass[markChar] = 0.0;
			dynamicAminoacid[markChar] = false;
			dynamicAminoacidCount--;
		}
	}

	public int getAminoacidCount(String peptideSeq) {
		if (!isDynamicModificationExists()) {
			return peptideSeq.length();
		}

		int result = 0;
		for (int i = 0; i < peptideSeq.length(); i++) {
			if (!isDynamicModificationChar(peptideSeq, i)) {
				result++;
			}
		}

		return result;
	}

	public void validateSequence(String sequence) {
		validateSequenceEmpty(sequence);
		validateSequenceFirstChar(sequence);

		for (int i = 1; i < sequence.length(); i++) {
			if (Character.isLetter(sequence.charAt(i))) {
				if (staticAminoacidMass[sequence.charAt(i)] == 0.0) {
					throw new SequenceValidateException(
							"Parameter sequence is invalid : unrecognize aminoacid '"
									+ sequence.charAt(i) + "' in " + sequence);
				}
			} else if (!isDynamicModificationChar(sequence, i)) {
				throw new SequenceValidateException(
						"Parameter sequence is invalid : unrecognize aminoacid '"
								+ sequence.charAt(i) + "' in " + sequence);
			}
		}
	}

	private void validateSequenceFirstChar(String sequence) {
		char c = sequence.charAt(0);

		if (isDynamicModification(c)) {
			return;
		}

		if (!Character.isLetter(c) || !isAminoacidValid(sequence.charAt(0))) {
			throw new SequenceValidateException(
					"Parameter sequence is invalid : first char '"
							+ sequence.charAt(0)
							+ "' is not a valid amino acid");
		}
	}

	private boolean isAminoacidValid(char aminoacid) {
		return staticAminoacidMass[aminoacid] != 0.0;
	}

	private void validateSequenceEmpty(String sequence) {
		if (sequence == null || sequence.length() == 0) {
			throw new SequenceValidateException(
					"Parameter sequence cannot be empty!");
		}
	}

	protected boolean isDynamicModificationExists() {
		return dynamicAminoacidCount != 0;
	}

	protected boolean isDynamicModificationExists(String sequence) {
		if (isDynamicModificationExists()) {
			return true;
		}

		for (int i = 0; i < sequence.length(); i++) {
			if (isDynamicModificationChar(sequence, i)) {
				return true;
			}
		}

		return false;
	}

	private boolean isDynamicModification(char c) {
		// If the character is letter, it's not dynamic modification
		if (Character.isLetter(c)) {
			return false;
		}

		// This character has been assigned dynamic modification mass
		return dynamicAminoacid[c];
	}

	protected boolean isDynamicModificationChar(String sequence, int index) {
		// check index range
		if (index < 0 || index >= sequence.length()) {
			return false;
		}

		// If the character is letter, it's not dynamic modification
		if (Character.isLetter(sequence.charAt(index))) {
			return false;
		}

		// This character has been assigned dynamic modification mass
		if (dynamicAminoacid[sequence.charAt(index)]) {
			return true;
		}

		// The amino acid before this character has been assigned dynamic
		// modification mass
		if (dynamicAminoacid[sequence.charAt(index - 1)]) {
			return true;
		}

		// otherwise, it's not dynamic modification
		return false;
	}

	protected double getDynamicModificationCharMass(String sequence, int index) {
		if (dynamicAminoacid[sequence.charAt(index)]) {
			return dynamicModificationMass[sequence.charAt(index)];
		}

		if (dynamicAminoacid[sequence.charAt(index - 1)]) {
			return dynamicModificationMass[sequence.charAt(index - 1)];
		}

		throw new IllegalArgumentException("Character "
				+ sequence.charAt(index)
				+ " is not a dynamic modification character.");
	}

	public double[] getBSeries(String peptideSeq) {
		validateSequence(peptideSeq);

		if (isDynamicModificationExists(peptideSeq)) {
			return getDynamicModifiedBSeries(peptideSeq,
					getAminoacidCount(peptideSeq) - 1);
		}

		return getUndynamicModifiedBSeries(peptideSeq,
				getAminoacidCount(peptideSeq) - 1);
	}

	public double[] getFullBSeries(String peptideSeq) {
		validateSequence(peptideSeq);

		if (isDynamicModificationExists(peptideSeq)) {
			return getDynamicModifiedBSeries(peptideSeq,
					getAminoacidCount(peptideSeq));
		}
		return getUndynamicModifiedBSeries(peptideSeq, peptideSeq.length());
	}

	private double[] getUndynamicModifiedBSeries(String peptideSeq, int length) {
		double[] result = new double[length];

		double mass = nTermMass;
		for (int i = 0; i < peptideSeq.length() && i < length; i++) {
			mass += staticAminoacidMass[peptideSeq.charAt(i)];
			result[i] = mass;
			if (i >= length) {
				break;
			}
		}
		return result;
	}

	private double[] getDynamicModifiedBSeries(String peptideSeq, int length) {
		double[] result = new double[length];

		double mass = nTermMass;
		int startIndex = 0;
		if(isDynamicModification(peptideSeq.charAt(0))){
			mass += getDynamicModificationCharMass(peptideSeq, 0);
			startIndex = 1;
		}
		
		for (int i = startIndex, j = 0; i < peptideSeq.length();) {
			mass += staticAminoacidMass[peptideSeq.charAt(i++)];
			if (isDynamicModificationChar(peptideSeq, i)) {
				mass += getDynamicModificationCharMass(peptideSeq, i);
				i++;
			}
			result[j++] = mass;
			if (j >= length) {
				break;
			}
		}
		return result;
	}

	public double[] getYSeries(String peptideSeq) {
		validateSequence(peptideSeq);

		if (isDynamicModificationExists()) {
			return getDynamicModifiedYSeries(peptideSeq,
					getAminoacidCount(peptideSeq) - 1);
		}

		return getUndynamicModifiedYSeries(peptideSeq,
				getAminoacidCount(peptideSeq) - 1);
	}

	public double[] getFullYSeries(String peptideSeq) {
		validateSequence(peptideSeq);

		if (isDynamicModificationExists(peptideSeq)) {
			return getDynamicModifiedYSeries(peptideSeq,
					getAminoacidCount(peptideSeq));
		}

		return getUndynamicModifiedYSeries(peptideSeq, peptideSeq.length());
	}

	private double[] getUndynamicModifiedYSeries(String peptideSeq, int length) {
		double[] result = new double[length];

		double mass = cTermMass + 2 * Hmass;

		for (int i = peptideSeq.length() - 1, j = 0; i >= 0; i--) {
			mass += staticAminoacidMass[peptideSeq.charAt(i)];
			result[j++] = mass;
			if (j >= length) {
				break;
			}
		}
		return result;
	}

	private double[] getDynamicModifiedYSeries(String peptideSeq, int length) {
		double[] result = new double[length];

		double mass = cTermMass + 2 * Hmass;

		for (int i = peptideSeq.length() - 1, j = 0; i >= 0; i--) {
			if (isDynamicModificationChar(peptideSeq, i)) {
				mass += getDynamicModificationCharMass(peptideSeq, i);
			} else {
				mass += staticAminoacidMass[peptideSeq.charAt(i)];
				result[j++] = mass;
				if (j >= length) {
					break;
				}
			}
		}
		return result;
	}

	public <T extends Peak> Map<IonType, List<T>> getSeries(String peptideSeq,
			IonType[] ionTypes, IAllocator<T> allocator) {
		validateSequence(peptideSeq);

		double[] bIons = getBSeries(peptideSeq);
		double[] yIons = getYSeries(peptideSeq);

		double h2o = getCHON(0, 2, 1, 0);
		double nh3 = getCHON(0, 3, 0, 1);
		double co = getCHON(1, 0, 1, 0);

		Map<IonType, List<T>> result = new LinkedHashMap<IonType, List<T>>();

		for (IonType ionType : ionTypes) {
			if (ionType == IonType.A) {
				double[] ions = arrayToArray(bIons, -co);
				result.put(ionType, arrayToList(ions, ionType, 1, allocator));
			} else if (ionType == IonType.X) {
				double[] ions = arrayToArray(yIons, co);
				result.put(ionType, arrayToList(ions, ionType, 1, allocator));
			} else if (ionType == IonType.B) {
				result.put(ionType, arrayToList(bIons, ionType, 1, allocator));
			} else if (ionType == IonType.Y) {
				result.put(ionType, arrayToList(yIons, ionType, 1, allocator));
			} else if (ionType == IonType.C) {
				double[] ions = arrayToArray(bIons, nh3);
				result.put(ionType, arrayToList(ions, ionType, 1, allocator));
			} else if (ionType == IonType.Z) {
				double[] ions = arrayToArray(yIons, -nh3);
				result.put(ionType, arrayToList(ions, ionType, 1, allocator));
			} else if (ionType == IonType.B2) {
				double[] ions = arrayToCharge2(bIons);
				result.put(ionType, arrayToList(ions, ionType, 2, allocator));
			} else if (ionType == IonType.Y2) {
				double[] ions = arrayToCharge2(yIons);
				result.put(ionType, arrayToList(ions, ionType, 2, allocator));
			} else if (ionType == IonType.B_H2O) {
				double[] ions = arrayToArray(bIons, -h2o);
				result.put(ionType, arrayToList(ions, ionType, 1, allocator));
			} else if (ionType == IonType.Y_H2O) {
				double[] ions = arrayToArray(yIons, -h2o);
				result.put(ionType, arrayToList(ions, ionType, 1, allocator));
			} else if (ionType == IonType.B_NH3) {
				double[] ions = arrayToArray(bIons, -nh3);
				result.put(ionType, arrayToList(ions, ionType, 1, allocator));
			} else if (ionType == IonType.Y_NH3) {
				double[] ions = arrayToArray(yIons, -nh3);
				result.put(ionType, arrayToList(ions, ionType, 1, allocator));
			} else if (ionType == IonType.B_H2O_NH3) {
				double[] ions = arrayToArray(bIons, -nh3 - h2o);
				result.put(ionType, arrayToList(ions, ionType, 1, allocator));
			} else if (ionType == IonType.Y_H2O_NH3) {
				double[] ions = arrayToArray(yIons, -nh3 - h2o);
				result.put(ionType, arrayToList(ions, ionType, 1, allocator));
			} else {
				throw new IllegalArgumentException(
						"Don't know how to get fragmentation of "
								+ ionType.toString());
			}
		}
		return result;
	}

	private double[] arrayToArray(double[] ions, double addMass) {
		double[] result = new double[ions.length];
		for (int i = 0; i < ions.length; i++) {
			result[i] = ions[i] + addMass;
		}
		return result;
	}

	private double[] arrayToCharge2(double[] ions) {
		double[] result = new double[ions.length];
		for (int i = 0; i < ions.length; i++) {
			result[i] = (ions[i] + Hmass) / 2;
		}
		return result;
	}

	private static <T extends Peak> List<T> arrayToList(double[] ions,
			IonType ionType, int charge, IAllocator<T> allocator) {
		List<T> result = new ArrayList<T>();
		for (int i = 0; i < ions.length; i++) {
			T peak = allocator.allocate();
			peak.setMz(ions[i]);
			peak.setIntensity(0.0);
			peak.setCharge(charge);
			peak.getAnnotations().add(ionType.toString() + "(" + (i + 1) + ")");
			result.add(peak);
		}
		return result;
	}

	private double getCHON(int c, int h, int o, int n) {
		return Cmass * c + Hmass * h + Omass * o + Nmass * n;
	}
}
