package cn.ac.rcpa.bio.proteomics.image.peptide;

import java.io.IOException;

import junit.framework.TestCase;
import cn.ac.rcpa.utils.RcpaFileUtils;

public class TestSequestSpectraImageBuilder extends TestCase {
	public void testBuild() throws Exception, IOException{
		IdentifiedPeptideResultImageBuilder builder = new IdentifiedPeptideResultImageBuilder(1,
			1,
			0.3,
			0.3,
			0.01,
			false,
			ImageType.jpg);
		
		String dtaFile = "Z:\\fkx\\Data_Combined\\All_Combined_001\\dtaouts\\temp\\Ur_CA_SAX_Online_071112_02.10309.10309.2.dta";
		String outFile = RcpaFileUtils.changeExtension(dtaFile, "out");

		SequestPeptideResult sr = new SequestPeptideResult();
		
		sr.parseFromFile(dtaFile, outFile);
		
//		PeakList<MatchedPeak> exps = sr.getExperimentalPeakList();
//		
//		Map<IonType, List<MatchedPeak>> ions = sr.getTheoreticalIonSeries();
//		
//		MatchedPeakUtils.match(exps.getPeaks(), ions.get(IonType.B), 0.5);
//		
//		double minIntensity = exps.getMaxIntensity() * 0.05;
//		
//		System.out.println("MaxIntensity = " + exps.getMaxIntensity());
//		System.out.println("MinIntensity = " + minIntensity);
//		
//		for(MatchedPeak p : exps.getPeaks()){
//			if(p.getIonType() != null){
//			System.out.println(p.getMz() + ", " + p.getIntensity() + ", " + p.getIonType() + p.getIonIndex());
//			}
//		}
//
		String imageFilename = RcpaFileUtils.changeExtension(dtaFile, ".jpg");

		builder.drawImage(imageFilename, sr);
	}
}
