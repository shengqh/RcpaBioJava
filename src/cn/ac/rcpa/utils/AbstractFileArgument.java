package cn.ac.rcpa.utils;

import javax.swing.JFileChooser;

public abstract class AbstractFileArgument
    implements IFileArgument {
  private String fileDescription;
  private String fileType;
  private JFileChooser fileChooser;

  public AbstractFileArgument(String fileType, String extension) {
    this.fileDescription = fileType + " File";
    this.fileType = fileType;
    fileChooser = new JFileChooser();
    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    fileChooser.setFileFilter(new SpecialSwingFileFilter(extension,
        fileType + " File", false));
  }

  public AbstractFileArgument(String fileType, String[] extensions) {
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

  public abstract boolean isValid(String filename);
}
