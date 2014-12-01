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
package cn.ac.rcpa;

public class Constants {
  public static String sqhContact = "Quanhu Sheng (shengqh@gmail.com)";

  public static String getSQHTitle(String title, String version) {
    return formatTitle(title, version, sqhContact);
  }

  public static String formatTitle(String title, String version,
      String authorContact) {
    return title + " - " + version + " - " + authorContact + " - RCPA/COL";
  }
}
