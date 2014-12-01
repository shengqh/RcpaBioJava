/*
 * Created on 2005-5-13
 *
 *                    Rcpa development code
 *
 * Author Sheng QuanHu(qhsheng@sibs.ac.cn / shengqh@gmail.com)
 * This code is developed by RCPA Bioinformatic Platform
 * http://www.proteomics.ac.cn/
 *
 */
package cn.ac.rcpa.bio.utils;

import junit.framework.TestCase;
import cn.ac.rcpa.bio.database.RcpaDBFactory;
import cn.ac.rcpa.bio.database.RcpaDatabaseType;

public class TestDatabaseUtils extends TestCase {
	public void testNothing(){}
	
  public void ttestAppendDatabaseLog() {
    DatabaseUtils.appendDatabaseLog(RcpaDBFactory.getInstance().getConnection(RcpaDatabaseType.TEST),"test");
  }

  public void ttestChineseCharacter() {
    DatabaseUtils.appendDatabaseLog(RcpaDBFactory.getInstance().getConnection(RcpaDatabaseType.TEST),"÷–Œƒ≤‚ ‘");
  }

}
