package cn.ac.rcpa.tools.io;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import cn.ac.rcpa.utils.GUIUtils;

public class GetFirstNLinesFromHugeFileUI
    extends JFrame {
  JTextField txtSourceFile = new JTextField();
  JButton btnBrowse = new JButton();
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JTextField txtLineCount = new JTextField();
  JButton btnStart = new JButton();
  JButton btnClose = new JButton();
  GridBagLayout gridBagLayout1 = new GridBagLayout();

  JFileChooser chooser = new JFileChooser();
  public GetFirstNLinesFromHugeFileUI() {
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    this.getContentPane().setLayout(gridBagLayout1);
    btnBrowse.setText("Browse...");
    btnBrowse.addActionListener(new
                                GetFirstNLinesFromHugeFileUI_btnBrowse_actionAdapter(this));
    jLabel1.setText("Source file");
    jLabel2.setText("Line count");
    btnStart.setText("Start");
    btnStart.addActionListener(new
                               GetFirstNLinesFromHugeFileUI_btnStart_actionAdapter(this));
    btnClose.setText("Close");
    btnClose.addActionListener(new
                               GetFirstNLinesFromHugeFileUI_btnClose_actionAdapter(this));
    txtSourceFile.setText("");
    txtLineCount.setText("");
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.setTitle("Get First N Lines From Huge File");
    this.getContentPane().add(jLabel1,
                              new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.NONE,
        new Insets(10, 10, 10, 10), 0, 0));
    this.getContentPane().add(txtSourceFile,
                              new GridBagConstraints(1, 0, 2, 1, 1.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
        new Insets(10, 10, 10, 10), 0, 0));
    this.getContentPane().add(jLabel2,
                              new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.NONE,
        new Insets(10, 10, 10, 10), 0, 0));
    this.getContentPane().add(txtLineCount,
                              new GridBagConstraints(1, 1, 1, 1, 1.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
        new Insets(10, 10, 10, 10), 0, 0));
    this.getContentPane().add(btnClose,
                              new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
        new Insets(10, 10, 10, 10), 0, 0));
    this.getContentPane().add(btnStart,
                              new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
        new Insets(10, 10, 10, 10), 0, 0));
    this.getContentPane().add(btnBrowse,
                              new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
        new Insets(10, 10, 10, 10), 0, 0));
  }

  void btnClose_actionPerformed(ActionEvent e) {
    dispose();
  }

  void btnBrowse_actionPerformed(ActionEvent e) {
    if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
      txtSourceFile.setText(chooser.getSelectedFile().getAbsolutePath());
    }
  }

  void btnStart_actionPerformed(ActionEvent e) {
    try {
      final File sourceFile = getSourceFile();

      final int lineCount = getLineCount();

      final File resultFile = new File(sourceFile.getAbsolutePath() + "." + lineCount);

      GetFirstNLinesFromHugeFile.getFirstNLine(
          sourceFile,
          resultFile,
          lineCount);

      JOptionPane.showMessageDialog(this, "Result file : " + resultFile.getAbsolutePath(), "Congratulation",
                                    JOptionPane.INFORMATION_MESSAGE);
    }
    catch (Exception ex) {
      JOptionPane.showMessageDialog(this, ex.getMessage(), "Error",
                                    JOptionPane.ERROR_MESSAGE);
    }
  }

  private int getLineCount() {
    try {
      int lineCount = Integer.parseInt(txtLineCount.getText());
      if (lineCount < 0) {
        throw new NumberFormatException("Line count must be large than zero,");
      }
      return lineCount;
    }
    catch (NumberFormatException ex) {
      throw new RuntimeException(ex.getMessage() + "\nInput line count first");
    }
  }

  private File getSourceFile() throws FileNotFoundException {
    File result = new File(txtSourceFile.getText());
    if (!result.isFile()) {
      throw new FileNotFoundException("Select or input source file name first");
    }
    return result;
  }

  public static void main(String[] args) {
    JFrame ui = new GetFirstNLinesFromHugeFileUI();
    ui.setSize(600,150);
    GUIUtils.setFrameDesktopCentre(ui);
    ui.setVisible(true);
  }
}

class GetFirstNLinesFromHugeFileUI_btnClose_actionAdapter
    implements java.awt.event.ActionListener {
  GetFirstNLinesFromHugeFileUI adaptee;

  GetFirstNLinesFromHugeFileUI_btnClose_actionAdapter(
      GetFirstNLinesFromHugeFileUI adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.btnClose_actionPerformed(e);
  }
}

class GetFirstNLinesFromHugeFileUI_btnBrowse_actionAdapter
    implements java.awt.event.ActionListener {
  GetFirstNLinesFromHugeFileUI adaptee;

  GetFirstNLinesFromHugeFileUI_btnBrowse_actionAdapter(
      GetFirstNLinesFromHugeFileUI adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.btnBrowse_actionPerformed(e);
  }
}

class GetFirstNLinesFromHugeFileUI_btnStart_actionAdapter
    implements java.awt.event.ActionListener {
  GetFirstNLinesFromHugeFileUI adaptee;

  GetFirstNLinesFromHugeFileUI_btnStart_actionAdapter(
      GetFirstNLinesFromHugeFileUI adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.btnStart_actionPerformed(e);
  }
}
