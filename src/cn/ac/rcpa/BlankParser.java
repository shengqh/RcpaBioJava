/*
 * Created on 2005-12-25
 *
 *                    Rcpa development code
 *
 * Author Sheng QuanHu(qhsheng@sibs.ac.cn / shengqh@gmail.com)
 * This code is developed by RCPA Bioinformatic Platform
 * http://www.proteomics.ac.cn/
 *
 */
package cn.ac.rcpa;

public class BlankParser<T> implements IParser<T>{

  public String getTitle() {
    return "";
  }

  public String getValue(T obj) {
    return "";
  }

  public String getDescription() {
    return "Blank Column";
  }

}
