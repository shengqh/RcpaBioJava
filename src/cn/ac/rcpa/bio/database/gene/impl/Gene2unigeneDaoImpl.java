/*
 * Created on 2006-1-17
 *
 *                    Rcpa development code
 *
 * Author Sheng QuanHu(qhsheng@sibs.ac.cn / shengqh@gmail.com)
 * This code is developed by RCPA Bioinformatic Platform
 * http://www.proteomics.ac.cn/
 *
 */
package cn.ac.rcpa.bio.database.gene.impl;

import java.util.List;

import cn.ac.rcpa.bio.database.gene.Gene2unigene;
import cn.ac.rcpa.bio.database.gene.Gene2unigeneDao;
import cn.ac.rcpa.dao.BaseDaoImpl;

public class Gene2unigeneDaoImpl extends BaseDaoImpl<Gene2unigene> implements
    Gene2unigeneDao {

  public Gene2unigeneDaoImpl() {
    super(Gene2unigene.class);
  }

  public Gene2unigene findByUnigene(String unigene) {
    List result = find("unigene", unigene);
    if (result.size() > 0) {
      return (Gene2unigene) result.get(0);
    } else {
      return null;
    }
  }

  public List<Gene2unigene> findByGene(Integer gene) {
    return find("gene", gene);
  }

}
