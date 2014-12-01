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
package cn.ac.rcpa.bio.annotation;

import java.io.File;

import junit.framework.TestCase;

public class GOEntryTest extends TestCase {

	public void testNothing(){}
  /*
   * Test method for 'cn.ac.rcpa.bio.annotation.GOEntry.loadFromFile(String)'
   */
  public void ttestLoadFromFile() {
    GOEntry goe = GOAAspectType.CELLULAR_COMPONENT.getRoot();
    goe.saveToFile("c:\\temp1.xml");
    GOAClassificationEntry goe2 = new GOAClassificationEntry();
    goe2.loadFromFile("c:\\temp1.xml");
    goe2.saveToFile("c:\\temp2.xml");
    new File("c:\\temp1.xml").delete();
    new File("c:\\temp2.xml").delete();
    
    goe2.loadFromFile("data/STATISTIC/Bound_SF_2.noredundant.go_cellular_component.tree");
  }

}
