package cn.ac.rcpa.bio.proteomics.results.buildsummary.io;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

import org.apache.commons.io.FileUtils;

import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryResult;
import cn.ac.rcpa.bio.sequest.SequestParseException;

public class TestBuildSummaryResultWriter extends TestCase {
  private void doTest(String oldFilename)throws IOException, SequestParseException{
    String newFilename = oldFilename + ".tmp";
    BuildSummaryResult result = new BuildSummaryResultReader().read(
        oldFilename);
    BuildSummaryResultWriter.getInstance().write(newFilename, result);
    assertTrue(FileUtils.contentEquals(new File(oldFilename),
                                      new File(newFilename)));
    new File(newFilename).delete();
    new File(newFilename + ".fasta").delete();
  }

  public void testWrite() throws IOException, SequestParseException {
    doTest("data/Bound_SF_2.noredundant");
  }

  public void testWriteFollowCandidates() throws IOException, SequestParseException {
    doTest("data/TestFollowCandidates.noredundant");
  }
}
