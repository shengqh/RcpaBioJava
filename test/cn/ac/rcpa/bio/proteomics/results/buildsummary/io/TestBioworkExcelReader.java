package cn.ac.rcpa.bio.proteomics.results.buildsummary.io;

import java.io.IOException;

import junit.framework.TestCase;
import cn.ac.rcpa.bio.proteomics.IIdentifiedResult;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryProteinGroup;

public class TestBioworkExcelReader extends TestCase {
  private BioworkExcelReader bioworkExcelReader = BioworkExcelReader.getInstance();

  public void testRead() throws IOException {
    String filename = "data/BioworkFormatTest.xls";
    IIdentifiedResult actualReturn = bioworkExcelReader.read(filename);
    assertEquals(3, actualReturn.getProteinGroupCount());
    assertEquals(54, actualReturn.getProteinGroup(0).getPeptideHitCount());
    assertEquals(1,  ((BuildSummaryProteinGroup)actualReturn.getProteinGroup(0)).getNumber());
    assertEquals(25, actualReturn.getProteinGroup(2).getPeptideHitCount());
    assertEquals("EGFA431_5ul_CTI_Biphase_PH55elute_NSI.2752.2757.1.", actualReturn.getProteinGroup(0).getProtein(0).getPeptide(0).getPeakListInfo().getLongFilename());
  }

}
