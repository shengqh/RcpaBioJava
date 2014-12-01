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
package cn.ac.rcpa.bio.database.gene.impl;

import java.util.List;

import cn.ac.rcpa.bio.database.gene.Gene2go;
import cn.ac.rcpa.bio.database.gene.Gene2goDao;
import cn.ac.rcpa.dao.BaseDaoImpl;

public class Gene2goDaoImpl extends BaseDaoImpl<Gene2go> implements Gene2goDao {

  public Gene2goDaoImpl() {
    super(Gene2go.class);
  }

  public List<Gene2go> findByGene(int gene_id) {
    return find("gene_id", new Integer(gene_id));
  }

  public List<Gene2go> findByTaxonomy(int tax_id) {
    return find("tax_id", new Integer(tax_id));
  }

  public List<Gene2go> findByGo(String go_id) {
    return find("go_id", go_id);
  }

}
