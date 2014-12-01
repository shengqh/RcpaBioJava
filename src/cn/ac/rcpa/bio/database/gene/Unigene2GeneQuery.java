/*
 * Created on 2006-1-19
 *
 *                    Rcpa development code
 *
 * Author Sheng QuanHu(qhsheng@sibs.ac.cn / shengqh@gmail.com)
 * This code is developed by RCPA Bioinformatic Platform
 * http://www.proteomics.ac.cn/
 *
 */
package cn.ac.rcpa.bio.database.gene;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.ac.rcpa.bio.database.RcpaDatabaseType;
import cn.ac.rcpa.database.utils.ApplicationContextFactory;

public class Unigene2GeneQuery {
  private Gene2unigeneDao dao;

  public void setDao(Gene2unigeneDao dao) {
    this.dao = dao;
  }

  public Gene2unigeneDao getDao() {
    if (null == dao) {
      dao = (Gene2unigeneDao) ApplicationContextFactory.getContext(
          RcpaDatabaseType.ANNOTATION).getBean("Gene2unigeneDao");
    }
    return dao;
  }

  public Map<String, Integer> getUnigene2geneMap(List<String> unigenes) {
    Map<String, Integer> unigene2geneMap = new HashMap<String, Integer>();
    for (String unigene : unigenes) {
      Gene2unigene gene = getDao().findByUnigene(unigene);
      if (null != gene) {
        unigene2geneMap.put(unigene, gene.getGene());
      }
    }
    return unigene2geneMap;
  }
}
