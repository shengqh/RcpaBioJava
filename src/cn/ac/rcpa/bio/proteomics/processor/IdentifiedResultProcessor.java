package cn.ac.rcpa.bio.proteomics.processor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.ac.rcpa.bio.proteomics.IIdentifiedProteinGroup;
import cn.ac.rcpa.bio.proteomics.IIdentifiedResult;
import cn.ac.rcpa.processor.IProcessor;

public class IdentifiedResultProcessor
    implements IProcessor<IIdentifiedResult> {
  private IProcessor<IIdentifiedProteinGroup> groupProcessor;

  public IdentifiedResultProcessor(IProcessor<IIdentifiedProteinGroup>
                                   processor) {
    this.groupProcessor = processor;
  }

  /**
   * process
   *
   * @param sequestResult SequestResult
   */
  public List<String> process(IIdentifiedResult sequestResult) {
    Map<List<String>, Integer> counts = new HashMap<List<String>, Integer>();
    Map<Set<String>, List<String>> processedMap = new HashMap<Set<String>, List<String>>();

    for (int i = 0; i < sequestResult.getProteinGroupCount(); i++) {
      final List<String> processed = groupProcessor.process(sequestResult.getProteinGroup(i));
      final Set<String> curProcessedMap = new HashSet<String>(processed);

      if (!processedMap.containsKey(curProcessedMap)){
        processedMap.put(curProcessedMap, processed);
        counts.put(processed, 0);
      }

      final List<String> curProcessed = processedMap.get(curProcessedMap);
      counts.put(curProcessed, counts.get(curProcessed) + 1);
    }

    List<String> result = new ArrayList<String>();

    for(List<String> processed:counts.keySet()){
      result.add(processed + "\t" + counts.get(processed));
    }

    Collections.sort(result);

    return result;
  }

}
