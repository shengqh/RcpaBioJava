package cn.ac.rcpa.bio.proteomics.results.buildsummary.io;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import cn.ac.rcpa.bio.proteomics.IIdentifiedPeptideHit;
import cn.ac.rcpa.bio.proteomics.io.IIdentifiedResultWriter;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryPeptideHit;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryProteinGroup;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryResult;
import cn.ac.rcpa.bio.sequest.SequestFilename;

public class BuildSummaryDistinctResultWriter
    implements IIdentifiedResultWriter<BuildSummaryResult> {
  public BuildSummaryDistinctResultWriter() {
  }

  private BuildSummaryResult getBuildSummaryDistinctResult(BuildSummaryResult bsr) {
    BuildSummaryResult result = (BuildSummaryResult) bsr.clone();

    final HashMap<String, Integer> peptideMap = getScanExistCountMap(result);

    for (int i = result.getProteinGroupCount() - 1; i >= 0; i--) {
      final BuildSummaryProteinGroup group = result.getProteinGroup(i);
      final IIdentifiedPeptideHit[] pephits = group.getPeptideHits().toArray(new
          IIdentifiedPeptideHit[0]);

      for (int k = pephits.length - 1; k >= 0; k--) {
        final String longFilename = pephits[k].getPeakListInfo().getLongFilename();

        if (peptideMap.get(longFilename) > 1) {
          for (int j = 0; j < group.getProteinCount(); j++) {
            group.getProtein(j).removePeptide(k);
          }
        }
      }

      if (group.getPeptideHitCount() == 0) {
        result.removeProteinGroup(i);
        break;
      }
    }

    return result;
  }

  private HashMap<String,
      Integer> getScanExistCountMap(BuildSummaryResult result) {
    final HashMap<String, Integer> peptideMap = new HashMap<String, Integer> ();
    for (int i = result.getProteinGroupCount() - 1; i >= 0; i--) {
      final BuildSummaryProteinGroup group = result.getProteinGroup(i);
      final List<BuildSummaryPeptideHit> peptides = group.getPeptideHits();
      for (BuildSummaryPeptideHit peptide : peptides) {
        final SequestFilename fname = (SequestFilename) peptide.getPeptide(0).
            getPeakListInfo();
        final String longFilename = fname.getLongFilename();
        if (!peptideMap.containsKey(longFilename)) {
          peptideMap.put(longFilename, 0);
        }
        peptideMap.put(longFilename, peptideMap.get(longFilename) + 1);
      }
    }
    return peptideMap;
  }

  /**
   * write
   *
   * @param writer PrintWriter
   * @param identifiedResult IIdentifiedResult
   */
  public void write(PrintWriter writer, BuildSummaryResult identifiedResult) throws
      IOException {
    BuildSummaryResult ir = getBuildSummaryDistinctResult(identifiedResult);

    BuildSummaryResultWriter.getInstance().write(writer, ir);
  }

  /**
   * write
   *
   * @param filename String
   * @param identifiedResult IIdentifiedResult
   */
  public void write(String filename, BuildSummaryResult identifiedResult) throws
      IOException {
    BuildSummaryResult ir = getBuildSummaryDistinctResult(identifiedResult);

    BuildSummaryResultWriter.getInstance().write(filename, ir);
  }
}
