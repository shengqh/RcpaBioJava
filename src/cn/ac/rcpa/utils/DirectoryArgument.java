package cn.ac.rcpa.utils;

import java.io.File;

import javax.swing.JFileChooser;

public class DirectoryArgument
    implements IFileArgument {
  private String fileDescription;
  private String fileType;
  private JFileChooser fileChooser;

  public DirectoryArgument(String directoryType) {
    this.fileDescription = directoryType + " Directory";
    this.fileType = directoryType;
    fileChooser = new JFileChooser();
    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
  }

  public String getFileType() {
    return fileType;
  }

  public JFileChooser getFileChooser(){
    return fileChooser;
  }

  public String getFileDescription(){
    return fileDescription;
  }

  public boolean isValid(String filename) {
    return new File(filename).isDirectory();
  }
}
