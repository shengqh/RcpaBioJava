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
package cn.ac.rcpa.component;

public class JRcpaObjectCheckBox<T> extends JRcpaCheckBox {
  private T obj;

  public JRcpaObjectCheckBox(String key, String description, int column, T obj) {
    super(key, description, column);
    this.obj = obj;
  }

  public JRcpaObjectCheckBox(String key, String description, T obj) {
    super(key, description);
    this.obj = obj;
  }

  public T getObject() {
    return obj;
  }

}
