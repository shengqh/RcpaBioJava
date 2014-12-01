package cn.ac.rcpa.utils;

import javax.swing.JFileChooser;

public interface IFileArgument {
  String getFileType();
  String getFileDescription();
  JFileChooser getFileChooser();
  boolean isValid(String filename);
}
