/*
 * Created on 2003-12-3
 */
package cn.ac.rcpa.utils;

import java.util.logging.Logger;

/**
 * @author Sheng Quanhu (shengqh@gmail.com/shengqh@263.net)
 */
public class DebugLog {

  public static void info(String info) {
    logger.info(info);
  }

  public static void warning(String warning) {
    logger.warning(warning);
  }

  private static Logger logger = Logger.getAnonymousLogger();
}
