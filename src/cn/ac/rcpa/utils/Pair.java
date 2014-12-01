package cn.ac.rcpa.utils;

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
public class Pair<K, V> implements Comparable<Pair<K, V>> {
	public K fst;

	public V snd;

	public Pair() {
		super();
		this.fst = null;
		this.snd = null;
	}

	public Pair(K fst, V snd) {
		super();
		this.fst = fst;
		this.snd = snd;
	}

	public Pair(Pair<K, V> source) {
		super();
		this.fst = source.fst;
		this.snd = source.snd;
	}

	private String getIdentity() {
		return "[" + fst.toString() + " , " + snd.toString() + "]";
	}

	@Override
	public String toString() {
		return getIdentity();
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Pair)) {
			return false;
		}
		Pair rhs = (Pair) object;
		return new EqualsBuilder().append(this.snd, rhs.snd).append(this.fst,
				rhs.fst).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder(126188503, -488723585).append(this.snd).append(
				this.fst).toHashCode();
	}

	/**
	 * @see java.lang.Comparable#compareTo(Object)
	 */
	public int compareTo(Pair<K, V> myClass) {
		return new CompareToBuilder().append(this.fst, myClass.fst).append(
				this.snd, myClass.snd).toComparison();
	}
}
