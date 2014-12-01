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

import cn.ac.rcpa.bio.annotation.GOAClassificationEntry;
import cn.ac.rcpa.bio.database.AbstractDBApplication;
import cn.ac.rcpa.bio.database.RcpaDBFactory;
import cn.ac.rcpa.bio.database.RcpaDatabaseType;

public class GOAnnotationQueryTreeBuilder extends AbstractDBApplication {

  public GOAnnotationQueryTreeBuilder() {
    super(RcpaDBFactory.getInstance()
        .getConnection(RcpaDatabaseType.ANNOTATION));
  }

  public GOAnnotationQueryTreeBuilder(Connection connection) {
    super(connection);
  }

  public GOAClassificationEntry getGOAEntryTree(String rootGO, int level) {
    GOAClassificationEntry result = new GOAClassificationEntry();

    System.out.println("Fill gotree start : " + new java.util.Date());
    new GOQuery(connection).fillGOEntry(result, rootGO, level);
    System.out.println("Fill gotree end   : " + new java.util.Date()
        + " GOEntryCount=" + result.getGOEntryMap().size());

    return result;
  }

}
