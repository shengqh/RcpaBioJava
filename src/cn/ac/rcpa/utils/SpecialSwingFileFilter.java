package cn.ac.rcpa.utils;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class SpecialSwingFileFilter
    extends FileFilter {
  private String[] specialExtensions;
  private String description;
  private boolean fileOnly;

  private void init(String[] specialExtensions, String description,
                    boolean fileOnly) {
    this.specialExtensions = new String[specialExtensions.length];
    for (int i = 0; i < specialExtensions.length; i++) {
      this.specialExtensions[i] = ("." + specialExtensions[i]).toLowerCase();
    }
    this.description = description;
    this.fileOnly = fileOnly;
  }

  public SpecialSwingFileFilter(String specialExtension, String description,
                                boolean fileOnly) {
    if (specialExtension == null || specialExtension.trim().length() == 0) {
      throw new IllegalArgumentException("spacialExtension cannot be empty!");
    }

    init(new String[] {specialExtension}, description, fileOnly);
  }

  public SpecialSwingFileFilter(String[] specialExtensions, String description,
                                boolean fileOnly) {
    if (specialExtensions == null || specialExtensions.length == 0) {
      throw new IllegalArgumentException("spacialExtension cannot be empty!");
    }

    init(specialExtensions, description, fileOnly);
  }

  @Override
  public boolean accept(File f) {
    if (f.isDirectory()) {
      return!fileOnly;
    }

    final String filename = f.getName().toLowerCase();
    for (String specialExtension : specialExtensions) {
      if (filename.endsWith(specialExtension)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public String getDescription() {
    StringBuffer result = new StringBuffer(description + " (");
    for (int i = 0; i < specialExtensions.length; i++) {
      if (i != 0) {
        result.append("; ");
      }
      result.append("*" + specialExtensions[i]);
    }
    result.append(")");
    return result.toString();
  }
}
