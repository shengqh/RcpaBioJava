package cn.ac.rcpa.component;

import java.awt.Container;

import cn.ac.rcpa.utils.XMLFile;

public interface IRcpaComponent {
  /**
   * Add current component to Container
   *
   * @param container Container, parent container
   * @param addToRow int, start row of current component
   * @param totalColumnCount int, total column count in container
   * @return int, after add current component, return next row index
   */
  public int addTo(Container container, int addToRow, int totalColumnCount);

  /**
   * Validate current component
   */
  public void validate() throws IllegalAccessException;

  /**
   * Query the column width current component needed. Parent container should
   * set column width based on each component
   *
   * @return int
   */
  public int columnCountNeeded();

  /**
   * Load current component from option file
   *
   * @param option XMLFile
   */
  void loadFromFile(XMLFile option);

  /**
   * Save current component to option file
   *
   * @param option XMLFile
   */
  void saveToFile(XMLFile option);
  
  double getPreferredHeight();
}
