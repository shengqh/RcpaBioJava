/*
 * Created on 2006-1-18
 *
 *                    Rcpa development code
 *
 * Author Sheng QuanHu(qhsheng@sibs.ac.cn / shengqh@gmail.com)
 * This code is developed by RCPA Bioinformatic Platform
 * http://www.proteomics.ac.cn/
 *
 */
package cn.ac.rcpa.bio.annotation.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import cn.ac.rcpa.bio.annotation.GOAClassificationEntry;
import cn.ac.rcpa.bio.annotation.IAnnotationQueryByGO;
import cn.ac.rcpa.bio.annotation.IGOEntry;
import cn.ac.rcpa.bio.database.AbstractDBApplication;
import cn.ac.rcpa.bio.database.RcpaDBFactory;
import cn.ac.rcpa.bio.database.RcpaDatabaseType;
import cn.ac.rcpa.bio.database.gene.Unigene2GeneQuery;

public class UnigeneGoAnnotationQuery extends AbstractDBApplication implements
    IAnnotationQueryByGO {
  public UnigeneGoAnnotationQuery(Connection connection) {
    super(connection);
  }

  public UnigeneGoAnnotationQuery() {
    super(RcpaDBFactory.getInstance()
        .getConnection(RcpaDatabaseType.ANNOTATION));
  }

  public GOAClassificationEntry getAnnotation(String rootGO, int level,
      String[] acNumbers) throws SQLException {
    validateConnection();

    GOAClassificationEntry result = new GOAnnotationQueryTreeBuilder(connection)
        .getGOAEntryTree(rootGO, level);

    fillAnnotation(result, acNumbers);

    result.removeEmptyGOEntry();

    result.fillGOAOther();

    return result;
  }

  public void fillAnnotation(GOAClassificationEntry rootEntry,
      String[] acNumbers) throws SQLException {
    Map<String, Integer> unigene2geneMap = new Unigene2GeneQuery()
        .getUnigene2geneMap(Arrays.asList(acNumbers));

    Map<String, String> gene2unigeneMap = getGene2unigeneMap(unigene2geneMap);

    new GeneGoAnnotationQuery(connection).fillAnnotation(rootEntry,
        gene2unigeneMap.keySet().toArray(new String[0]));

    replaceGene2unigene(rootEntry, gene2unigeneMap);
  }

  private void replaceGene2unigene(GOAClassificationEntry rootEntry,
      Map<String, String> gene2unigeneMap) {
    if (rootEntry.getProteins().size() > 0) {
      String firstGene = rootEntry.getProteins().iterator().next();
      if (hasBeenReplaced(gene2unigeneMap, firstGene)) {
        return;
      }

      HashSet<String> genes = new HashSet<String>(rootEntry.getProteins());

      rootEntry.getProteins().clear();
      for (String gene : genes) {
        rootEntry.getProteins().add(gene2unigeneMap.get(gene));
      }

      for (IGOEntry subEntry : rootEntry.getChildren()) {
        replaceGene2unigene((GOAClassificationEntry) subEntry, gene2unigeneMap);
      }
    }
  }

  private boolean hasBeenReplaced(Map<String, String> gene2unigeneMap,
      String firstGene) {
    return !gene2unigeneMap.containsKey(firstGene);
  }

  private Map<String, String> getGene2unigeneMap(
      Map<String, Integer> unigene2geneMap) {
    Map<String, String> result = new HashMap<String, String>();

    for (String unigene : unigene2geneMap.keySet()) {
      String gene = unigene2geneMap.get(unigene).toString();
      if (result.containsKey(gene)) {
        result.put(gene, result.get(gene) + "/" + unigene);
      } else {
        result.put(gene, unigene);
      }
    }

    return result;
  }
}
