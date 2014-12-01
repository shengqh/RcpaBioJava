package cn.ac.rcpa.processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ProcessorComposite<E>
    implements IProcessor<E> {
  private Collection<IProcessor<E>> processors;

  public ProcessorComposite(IProcessor<E>[] processors) {
    this.processors = Arrays.asList(processors);
    if (processors == null || processors.length == 0) {
      throw new IllegalArgumentException(
          "Parameter processors of ProcessorComposite should not be empty!");
    }
  }

  public ProcessorComposite(Collection<IProcessor<E>> processors) {
    this.processors = processors;
    if (processors == null || processors.size() == 0) {
      throw new IllegalArgumentException(
          "Parameter processors of ProcessorComposite should not be empty!");
    }
  }

  public List<String> process(E e) {
    List<String> result = new ArrayList<String>();
    for (IProcessor<E> filter : processors) {
      result.addAll(filter.process(e));
    }

    return result;
  }
}
