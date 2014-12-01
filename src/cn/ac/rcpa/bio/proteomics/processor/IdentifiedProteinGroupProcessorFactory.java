package cn.ac.rcpa.bio.proteomics.processor;

import java.util.ArrayList;

import cn.ac.rcpa.bio.proteomics.IIdentifiedProteinGroup;
import cn.ac.rcpa.bio.proteomics.filter.IdentifiedProteinGOAFilter;
import cn.ac.rcpa.bio.proteomics.filter.IdentifiedProteinNameFilter;
import cn.ac.rcpa.processor.IProcessor;
import cn.ac.rcpa.processor.ProcessorComposite;

final public class IdentifiedProteinGroupProcessorFactory {
  private IdentifiedProteinGroupProcessorFactory() {
  }

  public static<E extends IIdentifiedProteinGroup> IProcessor<E>
      create(IProcessor<E>[] processors) {
    return new ProcessorComposite<E> (processors);
  }

  public static<E extends IIdentifiedProteinGroup> IProcessor<E>
      createUnduplicatedProteinProcessor(
          boolean filterByGOA,
          String[] dbNames,
          boolean keepMaxSequence) {
    ArrayList<IProcessor<E>> processors = new ArrayList<IProcessor<E>> ();

    if (filterByGOA) {
      processors.add(new IdentifiedProteinGroupRemainProteinByFilterProcessor(
          IdentifiedProteinGOAFilter.getInstance()));
    }

    for (int i = 0; i < dbNames.length; i++) {
      processors.add(new IdentifiedProteinGroupRemainProteinByFilterProcessor(
          new IdentifiedProteinNameFilter(dbNames[i])));
    }

    processors.add(new
                   IdentifiedProteinGroupRemainProteinBySequenceLengthProcessor(
                       keepMaxSequence));

    return new ProcessorComposite<E> (processors);
  }

  public static<E extends IIdentifiedProteinGroup> IProcessor<E>
      createUnduplicatedProteinProcessor(
          boolean filterByGOA,
          boolean keepMaxSequence) {
    final String[] dbNames = {
        "SWISS-PROT", "|sp|", "REFSEQ_NP", "|ref|NP"};
    return createUnduplicatedProteinProcessor(filterByGOA, dbNames,
                                              keepMaxSequence);
  }

  public static<E extends IIdentifiedProteinGroup> IProcessor<E>
      createUnduplicatedProteinProcessor() {
    final String[] dbNames = {
        "SWISS-PROT", "|sp|", "REFSEQ_NP", "|ref|NP"};
    return createUnduplicatedProteinProcessor(true, dbNames, false);
  }
}
