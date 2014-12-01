package cn.ac.rcpa.bio.processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

import cn.ac.rcpa.models.AbstractInterruptable;
import cn.ac.rcpa.models.IInterruptable;

public class SerialFileProcessor
    extends AbstractInterruptable implements IFileProcessor {
  private IFileProcessor currentProcessor;

  private List<IFileProcessor> processors;
  public SerialFileProcessor(List<IFileProcessor> processors) {
    this.processors = new ArrayList<IFileProcessor> (processors);
  }

  public SerialFileProcessor(IFileProcessor[] processors) {
    this.processors = new ArrayList<IFileProcessor> (Arrays.asList(processors));
  }

  public List<String> process(String originFile) throws Exception {
    interrupted = false;

    LinkedHashSet<String> result = new LinkedHashSet<String> ();
    for (IFileProcessor processor : processors) {
      currentProcessor = processor;
      if (interrupted) {
        break;
      }
      result.addAll(currentProcessor.process(originFile));
    }
    currentProcessor = null;
    return new ArrayList<String> (result);
  }

  @Override
  public void interrupt() {
    super.interrupt();
    if ( (currentProcessor != null)
        && (currentProcessor instanceof IInterruptable)) {
      ( (IInterruptable) currentProcessor).interrupt();
    }
  }
}
