package cn.ac.rcpa.utils;

import java.io.File;

import javax.swing.JFileChooser;

public class FileArgument implements IFileArgument {
  private String fileDescription;
  private String fileType;
  private JFileChooser fileChooser;

  public FileArgument(String fileType, String extension) {
    this.fileDescription = fileType + " File";
    this.fileType = fileType;
    fileChooser = new JFileChooser();
    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    fileChooser.setFileFilter(new SpecialSwingFileFilter(extension,
        fileType + " File", false));
  }

  public FileArgument(String fileType, String[] extensions) {
    this.fileDescription = fileType + " File";
    this.fileType = fileType;
    fileChooser = new JFileChooser();
    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    fileChooser.setFileFilter(new SpecialSwingFileFilter(extensions,
        fileType + " File", false));
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
    return new File(filename).isFile();
  }
}
