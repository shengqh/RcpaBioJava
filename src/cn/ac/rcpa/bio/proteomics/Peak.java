package cn.ac.rcpa.bio.proteomics;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * <p>
 * Title: RCPA Package
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * 
 * <p>
 * Company: RCPA.SIBS.AC.CN
 * </p>
 * 
 * @author Sheng Quan-Hu
 * @version 1.0
 */
public class Peak implements Comparable<Peak> {
	private static DecimalFormat df = new DecimalFormat("0.0000");

	private double mz;

	private double intensity;
	
	private int charge;

	public int getCharge() {
		return charge;
	}

	public void setCharge(int charge) {
		this.charge = charge;
	}

	private List<String> annotations;

	public List<String> getAnnotations() {
		if (null == annotations) {
			annotations = new ArrayList<String>();
		}
		return annotations;
	}

	public void setAnnotations(List<String> annotations) {
		this.annotations = annotations;
	}

	public Peak() {
	}

	public Peak(double mz, double intensity) {
		this.mz = mz;
		this.intensity = intensity;
	}

	public Peak(double mz, double intensity, int charge) {
		this.mz = mz;
		this.intensity = intensity;
		this.charge = charge;
	}

	public double getMz() {
		return mz;
	}

	public void setIntensity(double intensity) {
		this.intensity = intensity;
	}

	public void setMz(double mz) {
		this.mz = mz;
	}

	public double getIntensity() {
		return intensity;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(df.format(mz));
		sb.append("\t");
		sb.append(df.format(intensity));
		sb.append("\t");
		if (null != annotations) {
			for (String annotation : annotations) {
				sb.append(annotation + " ");
			}
		}
		return sb.toString().trim();
	}

	/**
	 * @see java.lang.Comparable#compareTo(Object)
	 */
	public int compareTo(Peak object) {
		return new CompareToBuilder().append(this.mz, object.mz).toComparison();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(1016149365, 627207757).appendSuper(
				super.hashCode()).append(this.mz).toHashCode();
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Peak)) {
			return false;
		}
		Peak rhs = (Peak) object;
		return new EqualsBuilder().append(this.mz, rhs.mz).isEquals();
	}

}
