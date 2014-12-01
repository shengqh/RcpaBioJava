package cn.ac.rcpa.bio.database.ebi.application;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import cn.ac.rcpa.bio.database.RcpaDBFactory;
import cn.ac.rcpa.bio.database.RcpaDatabaseType;
import cn.ac.rcpa.utils.GUIUtils;

public class StoreProteinEntryFileToDBUI extends JFrame {
  JScrollPane pnlFiles = new JScrollPane();

  DefaultListModel listModel = new DefaultListModel();

  JList lstFiles = new JList(listModel);

  JButton btnAdd = new JButton();

  JButton btnDelete = new JButton();

  JButton btnClear = new JButton();

  JPanel jPanel1 = new JPanel();

  JButton btnStart = new JButton();

  JButton btnClose = new JButton();

  GridBagLayout gridBagLayout1 = new GridBagLayout();

  JFileChooser chooser = new JFileChooser();

  public StoreProteinEntryFileToDBUI() {
    try {
      jbInit();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    this.getContentPane().setLayout(gridBagLayout1);
    btnAdd.setText("Add");
    btnAdd
        .addActionListener(new StoreProteinEntryFileToDBUI_btnAdd_actionAdapter(
            this));
    btnDelete.setText("Delete");
    btnDelete
        .addActionListener(new StoreProteinEntryFileToDBUI_btnDelete_actionAdapter(
            this));
    btnClear.setText("Clear");
    btnClear
        .addActionListener(new StoreProteinEntryFileToDBUI_btnClear_actionAdapter(
            this));
    btnStart.setMargin(new Insets(2, 14, 2, 14));
    btnStart.setText("Start");
    btnStart
        .addActionListener(new StoreProteinEntryFileToDBUI_btnStart_actionAdapter(
            this));
    btnClose.setText("Close");
    btnClose
        .addActionListener(new StoreProteinEntryFileToDBUI_btnClose_actionAdapter(
            this));
    this
        .setTitle("Store Protein Entry From File To Database 1.0 (Li Long, Sheng QuanHu)");
    pnlFiles.getViewport().add(lstFiles, null);
    this.getContentPane().add(
        pnlFiles,
        new GridBagConstraints(0, 0, 1, 4, 1.0, 1.0, GridBagConstraints.CENTER,
            GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 0, 0));
    this.getContentPane().add(
        btnAdd,
        new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
            GridBagConstraints.BOTH, new Insets(10, 10, 0, 10), 0, 0));
    this.getContentPane().add(
        btnDelete,
        new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
            GridBagConstraints.BOTH, new Insets(10, 10, 0, 10), 0, 0));
    this.getContentPane().add(
        btnClear,
        new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
            GridBagConstraints.BOTH, new Insets(10, 10, 0, 10), 0, 0));
    this.getContentPane().add(
        jPanel1,
        new GridBagConstraints(0, 4, 2, 1, 1.0, 0.0, GridBagConstraints.CENTER,
            GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 0, 0));
    jPanel1.add(btnStart, null);
    jPanel1.add(btnClose, null);
    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    chooser.setMultiSelectionEnabled(true);
  }

  void btnClose_actionPerformed(ActionEvent e) {
    dispose();
  }

  void btnAdd_actionPerformed(ActionEvent e) {
    if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
      addFileToList(chooser.getSelectedFiles());
    }
  }

  /**
   * addFileToList
   *
   * @param files
   *          File[]
   */
  private void addFileToList(File[] files) {
    for (int i = 0; i < files.length; i++) {
      if (!isFileIncluded(files[i])) {
        listModel.addElement(files[i].getAbsolutePath());
      }
    }
  }

  private boolean isFileIncluded(File file) {
    for (int i = 0; i < listModel.getSize(); i++) {
      String existsFile = (String) listModel.elementAt(i);
      if (existsFile.equals(file.getAbsolutePath())) {
        return true;
      }
    }

    return false;
  }

  public static void main(String[] args) {
    StoreProteinEntryFileToDBUI frame = new StoreProteinEntryFileToDBUI();
    frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    frame.setSize(600, 480);
    GUIUtils.setFrameDesktopCentre(frame);
    frame.setVisible(true);
  }

  void btnDelete_actionPerformed(ActionEvent e) {
    final int[] selectedIndecies = lstFiles.getSelectedIndices();
    for (int i = selectedIndecies.length - 1; i >= 0; i--) {
      listModel.remove(selectedIndecies[i]);
    }
  }

  void btnClear_actionPerformed(ActionEvent e) {
    listModel.clear();
  }

  void btnStart_actionPerformed(ActionEvent e) {
    final ArrayList<String> filenames = new ArrayList<String>();
    for (int i = 0; i < listModel.getSize(); i++) {
      filenames.add(listModel.get(i).toString());
    }
    try {
      new StoreProteinEntryFileToDB(RcpaDBFactory.getInstance()
          .getConnection(RcpaDatabaseType.ANNOTATION))
          .initilizeDatabaseByFiles((String[]) filenames.toArray(new String[0]));
    } catch (Exception ex) {
      JOptionPane.showMessageDialog(this, ex.getMessage(), "Error",
          JOptionPane.ERROR_MESSAGE);
    }
  }
}

class StoreProteinEntryFileToDBUI_btnClose_actionAdapter implements
    java.awt.event.ActionListener {
  StoreProteinEntryFileToDBUI adaptee;

  StoreProteinEntryFileToDBUI_btnClose_actionAdapter(
      StoreProteinEntryFileToDBUI adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.btnClose_actionPerformed(e);
  }
}

class StoreProteinEntryFileToDBUI_btnAdd_actionAdapter implements
    java.awt.event.ActionListener {
  StoreProteinEntryFileToDBUI adaptee;

  StoreProteinEntryFileToDBUI_btnAdd_actionAdapter(
      StoreProteinEntryFileToDBUI adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.btnAdd_actionPerformed(e);
  }
}

class StoreProteinEntryFileToDBUI_btnDelete_actionAdapter implements
    java.awt.event.ActionListener {
  StoreProteinEntryFileToDBUI adaptee;

  StoreProteinEntryFileToDBUI_btnDelete_actionAdapter(
      StoreProteinEntryFileToDBUI adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.btnDelete_actionPerformed(e);
  }
}

class StoreProteinEntryFileToDBUI_btnClear_actionAdapter implements
    java.awt.event.ActionListener {
  StoreProteinEntryFileToDBUI adaptee;

  StoreProteinEntryFileToDBUI_btnClear_actionAdapter(
      StoreProteinEntryFileToDBUI adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.btnClear_actionPerformed(e);
  }
}

class StoreProteinEntryFileToDBUI_btnStart_actionAdapter implements
    java.awt.event.ActionListener {
  StoreProteinEntryFileToDBUI adaptee;

  StoreProteinEntryFileToDBUI_btnStart_actionAdapter(
      StoreProteinEntryFileToDBUI adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.btnStart_actionPerformed(e);
  }
}
