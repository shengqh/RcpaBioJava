package cn.ac.rcpa.utils;

import java.io.File;

public class OpenFileArgument extends AbstractFileArgument {
  public OpenFileArgument(String fileType, String extension) {
    super(fileType, extension);
  }

  public OpenFileArgument(String fileType, String[] extensions) {
    super(fileType, extensions);
  }

  @Override
  public boolean isValid(String filename) {
    return new File(filename).isFile();
  }
}
