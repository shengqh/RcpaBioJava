package cn.ac.rcpa.bio.proteomics.processor;

import java.util.List;

import cn.ac.rcpa.bio.proteomics.IIdentifiedProteinGroup;
import cn.ac.rcpa.bio.proteomics.IIdentifiedResult;
import cn.ac.rcpa.filter.IFilter;
import cn.ac.rcpa.processor.AbstractProcessor;

public class IdentifiedResultGroupFilterProcessor
    extends AbstractProcessor<IIdentifiedResult> {
  private IFilter<IIdentifiedProteinGroup> groupFilter;

  public IdentifiedResultGroupFilterProcessor(IFilter<IIdentifiedProteinGroup>
                                              groupFilter) {
    super();

    if (groupFilter == null) {
      throw new IllegalArgumentException("argument should not be null!");
    }

    this.groupFilter = groupFilter;

    processed.add(groupFilter.getType());
  }

  public List<String> process(IIdentifiedResult e) {
    boolean result = false;

    for (int i = e.getProteinGroupCount() - 1; i >= 0; i--) {
      if (!groupFilter.accept(e.getProteinGroup(i))) {
        e.removeProteinGroup(i);
        result = true;
      }
    }

    return result ? processed : unprocessed;
  }
}
