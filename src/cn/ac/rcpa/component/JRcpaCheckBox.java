/*
 * Created on 2005-11-30
 *
 *                    Rcpa development code
 *
 * Author Sheng QuanHu(qhsheng@sibs.ac.cn / shengqh@gmail.com)
 * This code is developed by RCPA Bioinformatic Platform
 * http://www.proteomics.ac.cn/
 *
 */
package cn.ac.rcpa.component;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JCheckBox;

import cn.ac.rcpa.utils.XMLFile;

public class JRcpaCheckBox implements IRcpaComponent {
  private JCheckBox cb;

  private String key;
  
  private int column;

  public JRcpaCheckBox(String key, String description, int column) {
    this.key = key;
    this.cb = new JCheckBox(description);
    this.column = column;
  }

  public JRcpaCheckBox(String key, String description) {
    this.key = key;
    this.cb = new JCheckBox(description);
    this.column = 0;
  }

  public int addTo(Container container, int addToRow, int totalColumnCount) {
  	int gridwidth = 1;
  	int gridheight = 1;
  	if(column == totalColumnCount - 1){
  		gridwidth = GridBagConstraints.REMAINDER;
  	}
    container.add(cb, new GridBagConstraints(column, addToRow, gridwidth, gridheight,
        1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
        new Insets(10, 10, 0, 10), 0, 0));
    return addToRow + 1;
  }

  public void validate() throws IllegalAccessException {
  }

  public int columnCountNeeded() {
    return column + 1;
  }

  public boolean isSelected() {
    return cb.isSelected();
  }

  public void setSelected(boolean selected) {
    cb.setSelected(selected);
  }

  public void loadFromFile(XMLFile option) {
    setSelected(option.getElementValue(key, Boolean.FALSE.toString()).equals(
        Boolean.TRUE.toString()));
  }

  public void saveToFile(XMLFile option) {
    option.setElementValue(key, Boolean.toString(isSelected()));
  }

  public double getPreferredHeight() {
    return cb.getPreferredSize().getHeight() + 10;
  }

}
