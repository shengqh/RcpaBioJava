package cn.ac.rcpa.bio.proteomics;


import java.util.NoSuchElementException;

import org.biojava.bio.BioException;


/**
 * <p>Title: RCPA Package</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: RCPA.SIBS.AC.CN</p>
 *
 * @author Sheng Quan-Hu
 * @version 1.0
 */
public interface IPeakListIterator<T extends Peak> {
	String getFormatName();
  boolean hasNext();
  PeakList<T> next() throws NoSuchElementException, BioException;
}
