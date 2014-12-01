package cn.ac.rcpa.bio.proteomics;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class AbstractPeptideHitList extends ArrayList<IIdentifiedPeptideHit> {
  public AbstractPeptideHitList(List<IIdentifiedPeptideHit> source) {
    super(source);
  }

  public AbstractPeptideHitList() {
    super();
  }

  public abstract Set<String> getUniquePeptide();
}
