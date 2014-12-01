/*
 * Created on 2006-2-16
 *
 *                    Rcpa development code
 *
 * Author Sheng QuanHu(qhsheng@sibs.ac.cn / shengqh@gmail.com)
 * This code is developed by RCPA Bioinformatic Platform
 * http://www.proteomics.ac.cn/
 *
 */
package cn.ac.rcpa.bio.proteomics.results.buildsummary.io;

import java.io.IOException;
import java.util.List;

import junit.framework.TestCase;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryProtein;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryResult;
import cn.ac.rcpa.bio.sequest.SequestParseException;

public class TestBuildSummaryProteinReader extends TestCase {
  public void testProteinReader() throws SequestParseException, IllegalStateException, IOException{
    BuildSummaryResult result = new BuildSummaryResultReader().readOnly("data/TestReadProtein.txt");
    List<BuildSummaryProtein> proteins = result.getProteins();
     assertEquals(3, proteins.size());
    assertEquals(1, proteins.get(2).getPeptideCount());
    assertEquals(1, proteins.get(1).getPeptideCount());
    assertEquals(2, proteins.get(0).getPeptideCount());
  }

}
