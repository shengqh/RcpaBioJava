package cn.ac.rcpa.bio.proteomics.processor;

import java.util.ArrayList;
import java.util.List;

import cn.ac.rcpa.bio.proteomics.IIdentifiedPeptide;
import cn.ac.rcpa.bio.proteomics.IIdentifiedPeptideHit;
import cn.ac.rcpa.bio.proteomics.IIdentifiedProtein;
import cn.ac.rcpa.bio.proteomics.IIdentifiedProteinGroup;
import cn.ac.rcpa.filter.IFilter;
import cn.ac.rcpa.processor.AbstractProcessor;

/**
 * <p>Title: RCPA Package</p>
 *
 * <p>Description:
 * 根据给定的ProteinHitFilter，对蛋白进行过滤。如果有蛋白满足过滤条件，只保留这些蛋白，如果没有蛋白满足条件，保留所有蛋白</p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: RCPA.SIBS.AC.CN</p>
 * @author Sheng Quan-Hu (shengqh@gmail.com)
 * @version 1.0
 */
public class IdentifiedProteinGroupRemainProteinByFilterProcessor<
    E extends IIdentifiedPeptide,
    F extends IIdentifiedPeptideHit<E>,
    G extends IIdentifiedProtein<E>,
    H extends IIdentifiedProteinGroup<E, F, G, H>>
    extends AbstractProcessor<H> {
  private IFilter<? super G> proFilter;

  public IdentifiedProteinGroupRemainProteinByFilterProcessor(
      IFilter<? super G> proFilter) {
    super();

    if (proFilter == null) {
      throw new IllegalArgumentException("argument should not be null!");
    }

    this.proFilter = proFilter;

    processed.add(proFilter.getType());
  }

  private List<G> remainProtein(List<G> prohits) {
    List<G> result = new ArrayList<G> ();
    for (G protein : prohits) {
      if (proFilter.accept(protein)) {
        result.add(protein);
      }
    }
    if (result.isEmpty()) {
      result.addAll(prohits);
    }
    return result;
  }

  public List<String> process(H group) {
    if (group.getProteinCount() >= 2) {
      final List<G> sp = remainProtein(group.getProteins());

      if (sp.size() != group.getProteinCount()) {
        group.clearProteins();
        for (G protein : sp) {
          group.addProtein(protein);
        }
        return processed;
      }
    }

    return unprocessed;
  }
}
