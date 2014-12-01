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
public abstract class AbstractPeakListIterator
    implements IPeakListIterator<Peak> {
  protected boolean morePeakListAvailable = true;

  public AbstractPeakListIterator() {
  }

  public boolean hasNext() {
    return morePeakListAvailable;
  }

  public PeakList<Peak> next() throws NoSuchElementException, BioException {
    if (!morePeakListAvailable) {
      throw new NoSuchElementException("Stream is empty");
    }

    try {
      PeakList<Peak> result = new PeakList<Peak>();
      morePeakListAvailable = doReadNextPeakList(result);
      return result;
    }
    catch (Exception e) {
      throw new BioException("Could not read peak list", e);
    }
  }

  abstract protected boolean doReadNextPeakList(PeakList<Peak> peakList) throws
      Exception;
}
