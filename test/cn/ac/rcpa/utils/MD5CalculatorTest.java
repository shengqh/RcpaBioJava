/*
 * Created on 2005-12-20
 *
 *                    Rcpa development code
 *
 * Author Sheng QuanHu(qhsheng@sibs.ac.cn / shengqh@gmail.com)
 * This code is developed by RCPA Bioinformatic Platform
 * http://www.proteomics.ac.cn/
 *
 */
package cn.ac.rcpa.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import junit.framework.TestCase;

public class MD5CalculatorTest extends TestCase {

  /*
   * Test method for 'cn.ac.rcpa.utils.MD5Calculator.calculateFileMD5(String)'
   */
  public void testCalculateFileMD5() throws NoSuchAlgorithmException, FileNotFoundException, IOException {
    assertEquals("e6a96602853b20607831eec27dbb6cf0", MD5Calculator.calculateFileMD5("data/TestMD5.bin").toLowerCase());
  }

  /*
   * Test method for 'cn.ac.rcpa.utils.MD5Calculator.calculateStringMD5(String)'
   */
  public void testCalculateStringMD5() throws NoSuchAlgorithmException {
    assertEquals("3e7705498e8be60520841409ebc69bc1", MD5Calculator.calculateStringMD5("test1\n").toLowerCase());

  }

}
