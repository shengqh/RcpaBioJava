package cn.ac.rcpa.utils;

import java.io.File;
import java.util.regex.Pattern;

public class SpecialIOFileFilterByPattern
    implements java.io.FileFilter {
  private Pattern specialPattern;

  private boolean fileOnly;

  public SpecialIOFileFilterByPattern(String patternStr,
                                      boolean fileOnly) {
    this.specialPattern = Pattern.compile(patternStr);
    this.fileOnly = fileOnly;
  }

  /**
   * accept
   *
   * @param pathname
   *          File
   * @return boolean
   */
  public boolean accept(File pathname) {
    if (pathname.isDirectory() && fileOnly) {
      return false;
    }

    return specialPattern.matcher( pathname.getName()).find();
  }
}
