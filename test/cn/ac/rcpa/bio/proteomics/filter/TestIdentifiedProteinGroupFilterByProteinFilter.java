/*
 * Created on 2005-5-18
 *
 *                    Rcpa development code
 *
 * Author Sheng QuanHu(qhsheng@sibs.ac.cn / shengqh@gmail.com)
 * This code is developed by RCPA Bioinformatic Platform
 * http://www.proteomics.ac.cn/
 *
 */
package cn.ac.rcpa.bio.proteomics.filter;

import junit.framework.TestCase;
import cn.ac.rcpa.bio.proteomics.IIdentifiedProtein;
import cn.ac.rcpa.bio.proteomics.IIdentifiedProteinGroup;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryProteinGroup;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.io.BuildSummaryResultReader;
import cn.ac.rcpa.filter.IFilter;

public class TestIdentifiedProteinGroupFilterByProteinFilter extends TestCase {

  public void testAccept() {
    BuildSummaryProteinGroup parent = new BuildSummaryProteinGroup();
    BuildSummaryProteinGroup child = new BuildSummaryProteinGroup();
    
    parent.addProtein(BuildSummaryResultReader.parseProtein("$935-1 IPI:IPI00215349.3|TREMBL:Q5RKI0|REFSEQ_XP:XP_341229|ENSEMBL:ENSRNOP00000024012 Tax_Id=10096 Hypothetical protein  2 2   3.14% 66181.29    6.15  IPI:IPI00215349.3|TREMBL:Q5RKI0|REFSEQ_XP:XP_341229|ENSEMBL:ENSRNOP00000024012 Tax_Id=10096 Hypothetical protein"));
    child.addProtein(BuildSummaryResultReader.parseProtein("$1997-1 IPI:IPI00364112.1|REFSEQ_XP:XP_341230 Tax_Id=10116 Similar to WD-repeat protein 1 (Actin interacting protein 1) (AIP1)  1 1   5.52% 19722.13    5.19  IPI:IPI00364112.1|REFSEQ_XP:XP_341230 Tax_Id=10116 Similar to WD-repeat protein 1 (Actin interacting protein 1) (AIP1)"));
    parent.addChild(child);
    
    IFilter<IIdentifiedProtein> proteinFilter = new IdentifiedProteinReferenceFilter("Tax_Id=10116");
    IFilter<IIdentifiedProteinGroup> groupFilter1 = new IdentifiedProteinGroupFilterByProteinFilter(proteinFilter, false);
    assertFalse(groupFilter1.accept(parent));
    assertTrue(groupFilter1.accept(child));
    
    IFilter<IIdentifiedProteinGroup> groupFilter2 = new IdentifiedProteinGroupFilterByProteinFilter(proteinFilter, true);
    assertTrue(groupFilter2.accept(parent));
    assertTrue(groupFilter2.accept(child));
  }

}
