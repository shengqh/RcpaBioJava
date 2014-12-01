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
package cn.ac.rcpa.bio.database.gene;

import java.util.List;

import cn.ac.rcpa.dao.BaseDao;

public interface Gene2unigeneDao extends BaseDao<Gene2unigene> {
  List<Gene2unigene> findByGene(final Integer gene);

  Gene2unigene findByUnigene(final String unigene);
}
