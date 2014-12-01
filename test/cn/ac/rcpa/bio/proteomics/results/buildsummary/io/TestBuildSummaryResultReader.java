package cn.ac.rcpa.bio.proteomics.results.buildsummary.io;

import java.io.IOException;

import junit.framework.TestCase;
import cn.ac.rcpa.bio.proteomics.IIdentifiedResult;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryPeptide;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryProtein;
import cn.ac.rcpa.bio.sequest.SequestParseException;

/**
 * <p>Title: RCPA Package</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: RCPA.SIBS.AC.CN</p>
 * @author not attributable
 * @version 1.0
 */

public class TestBuildSummaryResultReader
    extends TestCase {
  private BuildSummaryResultReader reader = null;

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    reader = new BuildSummaryResultReader();
  }

  @Override
  protected void tearDown() throws Exception {
    reader = null;
    super.tearDown();
  }

  @SuppressWarnings("unchecked")
  public void testRead() throws Exception {
    String filename = "data/Bound_SF_2.noredundant";
    IIdentifiedResult ir = reader.read(filename);
    BuildSummaryProtein[] prohits = (BuildSummaryProtein[])ir.getProteins().toArray(new BuildSummaryProtein[0]);

    assertEquals("protein group count", 245, ir.getProteinGroupCount());

    assertEquals("protein count", 373, prohits.length);

    BuildSummaryProtein protein1_1 = (BuildSummaryProtein)ir.getProteinGroup(0).getProtein(0);
    assertEquals(1, protein1_1.getGroup());
    assertEquals(protein1_1.getReference(), "IPI:IPI00022434.1|SWISS-PROT:P02768|REFSEQ_NP:NP_000468|ENSEMBL:ENSP00000295897 Tax_Id=9606 Serum albumin precursor",
                 protein1_1.getReference().trim());
    assertEquals("peptide count", 4250, protein1_1.getPeptideCount());

    final BuildSummaryPeptide pephit = (BuildSummaryPeptide)protein1_1.getPeptide(0);
    assertEquals(1372.45,pephit.getTheoreticalSingleChargeMass(), 0.01);
    assertEquals(3.0337, pephit.getXcorr(), 0.01);
    assertEquals(0.3224, pephit.getDeltacn(), 0.01);
    assertEquals(638.9, pephit.getSp(), 0.1);
    assertEquals(1, pephit.getSpRank());
    assertEquals(15, pephit.getMatchCount());
    assertEquals(22, pephit.getTheoreticalCount());
    assertEquals("K.AAFTECCQAADK.A", protein1_1.getPeptide(0).getSequence());

    BuildSummaryProtein protein2_1 = (BuildSummaryProtein)ir.getProteinGroup(1).getProtein(0);
    assertEquals(protein2_1.getReference(),
        "IPI:IPI00022463.1|SWISS-PROT:P02787|REFSEQ_NP:NP_001054|ENSEMBL:ENSP00000264998 Tax_Id=9606 Serotransferrin precursor",
                 protein2_1.getReference().trim());
    assertEquals("peptide count", 419, protein2_1.getPeptideCount());
    assertEquals("unique peptide count", 28,
                 protein2_1.getUniquePeptides().length);

    assertEquals(2, ir.getProteinGroup(69).getProteinCount());
    BuildSummaryProtein protein70_1 = (BuildSummaryProtein)ir.getProteinGroup(69).getProtein(0);
    assertEquals("IPI:IPI00387022.1|SWISS-PROT:P01593 Tax_Id=9606 Ig kappa chain V-I region AG",
                 protein70_1.getReference());
    assertEquals("K.ILIYDASNLETGVPSR.F",
                 protein70_1.getPeptide(0).getSequence());
    BuildSummaryProtein protein70_2 = (BuildSummaryProtein)ir.getProteinGroup(69).getProtein(1);
    assertEquals("IPI:IPI00387106.1|SWISS-PROT:P01613 Tax_Id=9606 Ig kappa chain V-I region Ni",
                 protein70_2.getReference());
    assertEquals("K.LLIYDASNLETGVPSR.F",
                 protein70_2.getPeptide(0).getSequence());
  }

  public void testReadParentChildren() throws IOException,
      SequestParseException {
    IIdentifiedResult bsResult = new BuildSummaryResultReader()
        .read("data/parent_children.proteins");
    assertEquals(2, bsResult.getProteinGroupCount());
    assertEquals(1, bsResult.getProteinGroup(0).getChildrenCount());
    assertEquals(0, bsResult.getProteinGroup(1).getChildrenCount());
    assertEquals(0, bsResult.getProteinGroup(0).getParentCount());
    assertEquals(1, bsResult.getProteinGroup(1).getParentCount());
    assertEquals(bsResult.getProteinGroup(1), bsResult.getProteinGroup(0)
        .getChildren().iterator().next());
    assertEquals(bsResult.getProteinGroup(0), bsResult.getProteinGroup(1)
        .getParents().iterator().next());
  }
}
