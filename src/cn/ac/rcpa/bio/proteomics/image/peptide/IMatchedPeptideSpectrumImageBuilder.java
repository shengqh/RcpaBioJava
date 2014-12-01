/*
 * Created on 2005-11-25
 *
 *                    Rcpa development code
 *
 * Author Sheng QuanHu(qhsheng@sibs.ac.cn / shengqh@gmail.com)
 * This code is developed by RCPA Bioinformatic Platform
 * http://www.proteomics.ac.cn/
 *
 */
package cn.ac.rcpa.bio.proteomics.image.peptide;

import java.awt.Dimension;
import java.io.OutputStream;
import java.util.List;

public interface IMatchedPeptideSpectrumImageBuilder {
	Dimension getDimension();

	void setDimension(Dimension dim);

	IIdentifiedPeptideResult getIdentifiedResult();

	void setIdentifiedResult(IIdentifiedPeptideResult sr);

	void drawPeptide(List<MatchedPeak> peaks, String peptide, int yPosition);

	void drawImage(OutputStream os) throws Exception;

	void addMatcher(IPeakMatcher matcher);

	void removeMatcher(IPeakMatcher matcher);

	void clearMatcher();
	
	void addDrawer(IPeptideDrawer drawer);
	
	void matchPeaks();
}
