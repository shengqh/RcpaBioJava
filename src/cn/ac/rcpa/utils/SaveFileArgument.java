package cn.ac.rcpa.utils;

import java.io.File;

public class SaveFileArgument
    extends AbstractFileArgument {
  public SaveFileArgument(String fileType, String extension) {
    super(fileType, extension);
  }

  public SaveFileArgument(String fileType, String[] extensions) {
    super(fileType, extensions);
  }

  @Override
  public boolean isValid(String filename) {
    return (filename != null) && (filename.trim().length() != 0) &&
        ! (new File(filename.trim()).isDirectory());
  }
}
