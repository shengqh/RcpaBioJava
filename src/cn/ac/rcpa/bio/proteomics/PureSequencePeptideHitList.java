package cn.ac.rcpa.bio.proteomics;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.ac.rcpa.bio.proteomics.utils.PeptideUtils;

public class PureSequencePeptideHitList
    extends AbstractPeptideHitList {
  public PureSequencePeptideHitList(List<IIdentifiedPeptideHit> source) {
    super(source);
  }

  public PureSequencePeptideHitList() {
    super();
  }

  @Override
  public Set<String> getUniquePeptide() {
    HashSet<String> result = new HashSet<String> ();
    for (IIdentifiedPeptideHit hit : this) {
      result.add(PeptideUtils.getPurePeptideSequence(hit.getPeptide(0).
          getSequence()));
    }

    return result;
  }
}
