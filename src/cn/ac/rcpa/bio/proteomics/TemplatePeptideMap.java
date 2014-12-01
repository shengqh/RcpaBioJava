package cn.ac.rcpa.bio.proteomics;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.ac.rcpa.IParser;
import cn.ac.rcpa.ParserForwarder;
import cn.ac.rcpa.filter.FilterNothing;
import cn.ac.rcpa.filter.IFilter;

public class TemplatePeptideMap<T>
    extends LinkedHashMap<T, DistributionResultMap> {

  public TemplatePeptideMap() {
    super();
  }

  public void increase(T obj, String peptide, String experimental) {
    increase(obj, peptide, experimental, 1);
  }

  public void increase(T obj, String peptide, String experimental,
                       int count) {
    if (!this.containsKey(obj)) {
      this.put(obj, new DistributionResultMap());
    }

    this.get(obj).increase(peptide, experimental, count);
  }

  public List<String> getClassifiedNames() {
    if (size() == 0) {
      return new ArrayList<String> ();
    }

    Set<String> resultSet = new HashSet<String> ();
    for (DistributionResultMap value : this.values()) {
      resultSet.addAll(value.getClassifiedNames());
    }

    List<String> result = new ArrayList<String> (resultSet);
    Collections.sort(result);
    return result;
  }

  public CountMap<T> getTotalCountMap() {
    final CountMap<T> result = new CountMap<T> ();
    for (T key : this.keySet()) {
      result.put(key, this.get(key).getTotalCount());
    }
    return result;
  }

  public List<T> getSortedKeys() {
    return getTotalCountMap().getKeyListByDecendingCount();
  }

  public void classify(Map<String, String> experimantalClassfiedNameMap) {
    for (DistributionResultMap value : this.values()) {
      value.classify(experimantalClassfiedNameMap);
    }
  }

  public void write(PrintWriter writer) throws IOException {
    write(writer, new ParserForwarder<T>("",""));
  }

  public void write(PrintWriter writer, IParser<T> parser) throws
      IOException {
    write(writer, parser, new FilterNothing<DistributionResultMap>(), false);
  }

  public void write(PrintWriter writer, IFilter<DistributionResultMap> filter) throws
      IOException {
    write(writer, new ParserForwarder<T>("",""), filter, false);
  }

  public void writeUnique(PrintWriter writer) throws IOException {
    write(writer, new ParserForwarder<T>("",""), new FilterNothing<DistributionResultMap>(), true);
  }

  public void writeUnique(PrintWriter writer, IParser<T> parser) throws
      IOException {
    write(writer, parser, new FilterNothing<DistributionResultMap>(), true);
  }

  public void writeUnique(PrintWriter writer, IFilter<DistributionResultMap> filter) throws
      IOException {
    write(writer, new ParserForwarder<T>("",""), filter, true);
  }

  public void write(PrintWriter writer, IParser<T> parser,
                    IFilter<DistributionResultMap> filter,
                    boolean uniquePeptideCount) throws
      IOException {
    writer.print("Object");
    if (parser != null){
      writer.print("\tReference");
    }

    final List<String> classifiedNames = getClassifiedNames();
    for (String name : classifiedNames) {
      writer.print("\t" + name);
    }
    writer.println("\tTotal");

//    final List<String> keys = getSortedKeys();
//    for (String key : keys) {
    for (T key : keySet()) {
      final DistributionResultMap pepMap = this.get(key);
      if (!filter.accept(pepMap)) {
        continue;
      }

      if (parser != null){
        writer.print(parser.getValue(key));
        writer.print("\t" + key);
      }
      else {
        writer.print(key.toString());
      }

      for (String classifiedName : classifiedNames) {
        writer.print("\t" + (uniquePeptideCount ?
                             pepMap.getExperimentalUniqueCount(classifiedName) :
                             pepMap.getExperimentalCount(classifiedName)));
      }
      writer.println("\t" + (uniquePeptideCount ?
                           pepMap.getTotalUniqueCount() :
                           pepMap.getTotalCount()));
    }
  }

}
