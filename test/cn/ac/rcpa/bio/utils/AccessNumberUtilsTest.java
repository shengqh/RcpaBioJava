/*
 * Created on 2006-2-28
 *
 *                    Rcpa development code
 *
 * Author Sheng QuanHu(qhsheng@sibs.ac.cn / shengqh@gmail.com)
 * This code is developed by RCPA Bioinformatic Platform
 * http://www.proteomics.ac.cn/
 *
 */
package cn.ac.rcpa.bio.utils;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

public class AccessNumberUtilsTest extends TestCase {

  /*
   * Test method for 'cn.ac.rcpa.bio.utils.AccessNumberUtils.parseAccessNumber(String)'
   */
  public void testParseAccessNumber() {
    List<String> expect = Arrays.asList(new String[]{"AAAA"});
    List<String> actual = AccessNumberUtils.parseAccessNumber("AAAA");
    assertEquals(expect, actual);
    
    expect = Arrays.asList(new String[]{"AAAA"});
    actual = AccessNumberUtils.parseAccessNumber("AAAA,");
    assertEquals(expect, actual);
    
    expect = Arrays.asList(new String[]{"AAAA","BBBB"});
    actual = AccessNumberUtils.parseAccessNumber("AAAA BBBB");
    assertEquals(expect, actual);
    
    expect = Arrays.asList(new String[]{"AAAA","BBBB"});
    actual = AccessNumberUtils.parseAccessNumber("AAAA, BBBB,");
    assertEquals(expect, actual);
    
    actual = AccessNumberUtils.parseAccessNumber("AAAA,\tBBBB, ");
    assertEquals(expect, actual);
  }

}
