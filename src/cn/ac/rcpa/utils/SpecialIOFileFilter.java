package cn.ac.rcpa.utils;

import java.io.File;

public class SpecialIOFileFilter implements java.io.FileFilter {
  private String specialExtension;

  private String lowerSpecialExtension;

  private boolean fileOnly;

  public SpecialIOFileFilter(String specialExtension,
      boolean fileOnly) {
    if (specialExtension == null || specialExtension.trim().length() == 0) {
      throw new IllegalArgumentException("spacialExtension cannot be empty!");
    }

    if (specialExtension.startsWith(".")) {
      this.specialExtension = specialExtension;
    } else {
      this.specialExtension = "." + specialExtension;
    }
    this.lowerSpecialExtension = this.specialExtension.toLowerCase();
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

    return pathname.getName().toLowerCase().endsWith(lowerSpecialExtension);
  }
}
