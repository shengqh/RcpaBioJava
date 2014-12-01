/*
 * Created on 2005-5-17
 *
 *                    Rcpa development code
 *
 * Author Sheng QuanHu(qhsheng@sibs.ac.cn / shengqh@gmail.com)
 * This code is developed by RCPA Bioinformatic Platform
 * http://www.proteomics.ac.cn/
 *
 */
package cn.ac.rcpa.bio.annotation;

import java.sql.SQLException;
import java.util.Map;

public interface ISubcellularLocationQuery {
  public Map<String, String> getSubcellularLocation(
      String[] acNumbers) throws SQLException;
}
