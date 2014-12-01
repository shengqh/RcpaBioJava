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

import cn.ac.rcpa.bio.proteomics.IonType;
import cn.ac.rcpa.bio.proteomics.utils.PeptideUtils;

public class BSeriesPeptideDrawer extends AbstractIonSeriesPeptideDrawer {
	public BSeriesPeptideDrawer(IonType ionType) {
		super(ionType);
	}

	@Override
	protected String getMatchPeptide(IIdentifiedPeptideResult sr) {
		return PeptideUtils.getMatchPeptideSequence(sr.getPeptide());
	}

}
