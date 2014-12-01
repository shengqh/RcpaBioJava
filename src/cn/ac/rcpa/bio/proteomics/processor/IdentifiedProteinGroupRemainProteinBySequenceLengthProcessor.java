package cn.ac.rcpa.bio.proteomics.processor;

import java.util.List;

import cn.ac.rcpa.bio.proteomics.IIdentifiedProtein;
import cn.ac.rcpa.bio.proteomics.IIdentifiedProteinGroup;
import cn.ac.rcpa.processor.AbstractProcessor;

/**
 * <p>Title: RCPA Package</p>
 *
 * <p>Description: Keep max/min sequence length protein hit</p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: RCPA.SIBS.AC.CN</p>
 * @author Sheng Quan-Hu (shengqh@gmail.com)
 * @version 1.0
 */
public class IdentifiedProteinGroupRemainProteinBySequenceLengthProcessor
    <E extends IIdentifiedProteinGroup>
    extends AbstractProcessor<E> {
  private boolean keepMaximum;

  public IdentifiedProteinGroupRemainProteinBySequenceLengthProcessor(boolean
      keepMaximum) {
    super();

    this.keepMaximum = keepMaximum;

    processed.add(keepMaximum ? "MaxSequenceLength" : "MinSequenceLength");
  }

  @SuppressWarnings("unchecked")
  private void checkSequenceExist(IIdentifiedProteinGroup group) {
    List<IIdentifiedProtein> prohits = group.getProteins();
    for ( IIdentifiedProtein protein : prohits) {
      if (protein.getSequence() == null) {
        throw new IllegalStateException(
            "Error : Protein sequence should be assigned first : " + protein.getProteinName());
      }
    }
  }

  @SuppressWarnings("unchecked")
  private boolean matchCondition(IIdentifiedProtein prohit, int length) {
    if (keepMaximum) {
      return prohit.getSequence().length() > length;
    }
    else {
      return prohit.getSequence().length() < length;
    }
  }

  /**
   * process
   *
   * @param group SequestProteinGroup
   */
  @SuppressWarnings("unchecked")
  public List<String> process(E group) {
    checkSequenceExist(group);

    if (group.getProteinCount() > 1) {
      List<IIdentifiedProtein> proteins = group.getProteins();
      IIdentifiedProtein keepProtein = null;
      int length = 0;

      for (IIdentifiedProtein protein : proteins) {
        if (keepProtein == null) {
          keepProtein = protein;
          length = keepProtein.getSequence().length();
        }
        else if (matchCondition(protein, length)) {
          keepProtein = protein;
          length = protein.getSequence().length();
        }
      }

      group.clearProteins();
      group.addProtein(keepProtein);

      return processed;
    }

    return unprocessed;
  }
}
