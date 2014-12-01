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
package cn.ac.rcpa.bio.database.gene;

import java.util.List;

import cn.ac.rcpa.dao.BaseDao;

public interface Gene2goDao extends BaseDao<Gene2go> {
  List<Gene2go> findByGene(int gene_id);

  List<Gene2go> findByTaxonomy(int tax_id);

  List<Gene2go> findByGo(String go_id);
}
