package cn.ac.rcpa.bio.proteomics.results.buildsummary.viewer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

import cn.ac.rcpa.bio.models.IObjectRemoveEvent;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryProtein;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryProteinGroup;
import cn.ac.rcpa.utils.ShellUtils;

public class BuildSummaryProteinGroupViewer
    extends JSplitPane
    implements IObjectViewer {
  private ArrayList<IObjectRemoveEvent> objectRemovedEvents = new ArrayList<IObjectRemoveEvent>();

  JTextArea alignmentEditor = new JTextArea();
  JScrollPane alignmentPane = new JScrollPane();
  SequestProteinGroupTableModel proteinModel = new
      SequestProteinGroupTableModel();
  JTable proteinTable = new JTable(proteinModel);
  JScrollPane proteinListPane = new JScrollPane();
  JScrollPane txtProteinPane = new JScrollPane();
  JTextArea txtProtein = new JTextArea();
  JPanel proteinPane = new JPanel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JPopupMenu pmProtein = new JPopupMenu();
  JMenuItem mniRemove = new JMenuItem();
  JMenuItem mniAlignment = new JMenuItem();
  JPanel pnlProteins = new JPanel();
  JButton btnAlignment = new JButton();
  JToolBar barAlignmentBotton = new JToolBar();
  BorderLayout borderLayout1 = new BorderLayout();

  public BuildSummaryProteinGroupViewer() {
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void objectRemoved(Object obj) {
    for (int i = 0; i < objectRemovedEvents.size(); i++) {
      ( (IObjectRemoveEvent) objectRemovedEvents.get(i)).objectRemoved(this, obj);
    }
  }

  public void load(Object obj) throws Exception {
    if (! (obj instanceof BuildSummaryProteinGroup)) {
      throw new IllegalArgumentException(
          "Parameter obj must be SequestProteinGroup!");
    }

    BuildSummaryProteinGroup group = (BuildSummaryProteinGroup) obj;
    proteinModel.setGroup(group);
    proteinModel.fireTableDataChanged();
    focusProtein( -1);

    if (proteinModel.getGroup().getProteinCount() <= 1) {
      alignmentEditor.setText(null);
    }
  }

  public void addObjectRemovedEvent(IObjectRemoveEvent e) {
    objectRemovedEvents.add(e);
  }

  public void removeObjectMovedEvent(IObjectRemoveEvent e) {
    objectRemovedEvents.remove(e);
  }

  public static boolean clustalw(String inputFile) {
    String[] command = {
        "extends\\clustalw.exe",
        "\"" + inputFile + "\""};
    return ShellUtils.execute(command, true);
  }

  private void jbInit() throws Exception {
    this.setOrientation(JSplitPane.VERTICAL_SPLIT);
    proteinPane.setLayout(gridBagLayout1);
    proteinListPane.setMinimumSize(new Dimension(24, 200));
    proteinListPane.setPreferredSize(new Dimension(24, 200));
    proteinTable.addMouseListener(new
                                  SequestProteinGroupViewer_proteinTable_mouseAdapter(this));
    txtProteinPane.setMinimumSize(new Dimension(0, 50));
    txtProteinPane.setPreferredSize(new Dimension(0, 50));
    txtProtein.setMinimumSize(new Dimension(0, 50));
    txtProtein.setPreferredSize(new Dimension(0, 50));
    txtProtein.setLineWrap(true);
    mniRemove.setText("Remove");
    mniRemove.addActionListener(new
                                SequestProteinGroupViewer_mniRemove_actionAdapter(this));
    mniAlignment.setText("Alignment");
    mniAlignment.addActionListener(new
        SequestProteinGroupViewer_mniAlignment_actionAdapter(this));
    pnlProteins.setLayout(borderLayout1);
    btnAlignment.setText("Alignment");
    btnAlignment.addActionListener(new SequestProteinGroupViewer_btnAlignment_actionAdapter(this));
    proteinListPane.getViewport().add(proteinTable, null);
    proteinPane.add(proteinListPane,
                    new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
                                           , GridBagConstraints.CENTER,
                                           GridBagConstraints.BOTH,
                                           new Insets(0, 0, 0, 0), 0, 0));

    txtProtein.setEditable(false);
    txtProteinPane.getViewport().add(txtProtein);
    proteinPane.add(txtProteinPane, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
        new Insets(0, 0, 0, 0), 0, 0));
    this.add(proteinPane, JSplitPane.TOP);
    alignmentEditor.setFont(new java.awt.Font("Courier New", 0, 11));
    alignmentEditor.setEditable(false);
    alignmentPane.getViewport().add(alignmentEditor, null);

    barAlignmentBotton.add(btnAlignment);
    pnlProteins.add(barAlignmentBotton, BorderLayout.NORTH);
    pnlProteins.add(alignmentPane, BorderLayout.CENTER);
    this.add(pnlProteins, JSplitPane.BOTTOM);

    pmProtein.add(mniRemove);
    pmProtein.addSeparator();
    pmProtein.add(mniAlignment);
    proteinTable.addMouseListener(new PopupListener(this));

    TableColumn column = null;
    for (int i = 0; i < SequestProteinGroupTableModel.COLUMN_NAMES.length; i++) {
      column = proteinTable.getColumnModel().getColumn(i);
      column.setPreferredWidth(SequestProteinGroupTableModel.COLUMN_WIDTHS[i]);
    }
  }

  public BuildSummaryProteinGroup getGroup() {
    return proteinModel.getGroup();
  }

  void focusProtein(int index) {
    if (index == -1) {
      txtProtein.setText(null);
    }
    else {
      txtProtein.setText(getGroup().getProtein(index).getReference());
    }
  }

  void proteinTable_mouseClicked(MouseEvent e) {
    focusProtein(proteinTable.rowAtPoint(e.getPoint()));
  }

  void mniRemove_actionPerformed(ActionEvent e) {
    /*    if (proteinTable.getSelectedRowCount() == 0) {
          return;
        }

     if (proteinTable.getSelectedRowCount() == getGroup().getProteinCount()) {
          if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this,
              "Are you sure to delete current group?",
              "Confirm",
              JOptionPane.YES_NO_OPTION)) {
            objectRemoved(getGroup());
          }
          return;
        }
     */

    if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this,
        "Are you sure to delete selected proteins?",
        "Confirm",
        JOptionPane.YES_NO_OPTION)) {
      int[] removeRows = proteinTable.getSelectedRows();
      for (int i = removeRows.length - 1; i >= 0; i--) {
        BuildSummaryProtein proHit = (BuildSummaryProtein)getGroup().getProtein(removeRows[i]);
        getGroup().removeProtein(proHit);
        objectRemoved(proHit);
      }
      proteinModel.fireTableDataChanged();
    }
  }

  void mniAlignment_actionPerformed(ActionEvent e) {
    alignment();
  }

  private void alignment() {
    BuildSummaryProteinGroup group = proteinModel.getGroup();
    if (group.getProteinCount() > 1 && group.getProtein(0).getSequence() != null &&
        group.getProtein(0).getSequence().length() > 0) {
      try {
        File dir = new File("temp");
        dir.mkdirs();

        String fastafile = dir.getAbsolutePath() + File.separator + "align.fas";
        String resultfile = dir.getAbsolutePath() + File.separator +
            "align.aln";
        PrintWriter pw = new PrintWriter(new FileWriter(fastafile));
        for (int i = 0; i < group.getProteinCount(); i++) {
          pw.println(">" + group.getProtein(i).getProteinName());
          pw.println(group.getProtein(i).getSequence());
        }
        pw.close();

        if (clustalw(fastafile)) {
          BufferedReader br = new BufferedReader(new FileReader(resultfile));
          StringBuffer sb = new StringBuffer();
          String line;
          while ( (line = br.readLine()) != null) {
            sb.append(line);
            sb.append("\n");
          }
          br.close();
          alignmentEditor.setText(sb.toString());
        }
      }
      catch (Exception ex) {
        JOptionPane.showMessageDialog(this, ex.getMessage(), "Error",
                                      JOptionPane.ERROR_MESSAGE);
      }
    }
    else{
      alignmentEditor.setText(null);
    }
  }

  void btnAlignment_actionPerformed(ActionEvent e) {
    alignment();
  }
}

class SequestProteinGroupTableModel
    extends AbstractTableModel {
  public static final String[] COLUMN_NAMES = {
      "Group", "Name", "Reference", "MW", "PI", "Coverage"};
  public static final int[] COLUMN_WIDTHS = {
      10, 100, 400, 40, 20, 20};
  private BuildSummaryProteinGroup group = null;
  public SequestProteinGroupTableModel() {
  }

  public void setGroup(BuildSummaryProteinGroup group) {
    this.group = group;
  }

  public int getColumnCount() {
    return COLUMN_NAMES.length;
  }

  @Override
  public String getColumnName(int col) {
    return COLUMN_NAMES[col];
  }

  public int getRowCount() {
    if (group == null) {
      return 0;
    }
    else {
      return group.getProteinCount();
    }
  }

  public Object getValueAt(int rowIndex, int columnIndex) {
    if (group == null) {
      return null;
    }

    final BuildSummaryProtein protein = (BuildSummaryProtein)group.getProtein(rowIndex);
    switch (columnIndex) {
      case 0:
        return Integer.toString(protein.getGroup());
      case 1:
        return protein.getProteinName();
      case 2: {
        int ipos = protein.getReference().indexOf(' ');
        if (ipos == -1){
          ipos = protein.getReference().indexOf('\t');
        }
        if (ipos == -1){
          return protein.getReference();
        }
        else{
          return protein.getReference().substring(ipos);
        }
      }
      case 3:
        return Double.toString(protein.getMW());
      case 4:
        return Double.toString(protein.getPI());
      case 5:
        return Double.toString(protein.getCoverage());
      default:
        return "";
    }
  }

  @Override
  public boolean isCellEditable(int row, int col) {
    return false;
  }

  public BuildSummaryProteinGroup getGroup() {
    return group;
  }
};

class SequestProteinGroupViewer_proteinTable_mouseAdapter
    extends java.awt.event.MouseAdapter {
  BuildSummaryProteinGroupViewer adaptee;

  SequestProteinGroupViewer_proteinTable_mouseAdapter(BuildSummaryProteinGroupViewer
      adaptee) {
    this.adaptee = adaptee;
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    adaptee.proteinTable_mouseClicked(e);
  }
}

class SequestProteinGroupViewer_mniRemove_actionAdapter
    implements java.awt.event.ActionListener {
  BuildSummaryProteinGroupViewer adaptee;

  SequestProteinGroupViewer_mniRemove_actionAdapter(BuildSummaryProteinGroupViewer
      adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.mniRemove_actionPerformed(e);
  }
}

class PopupListener
    extends MouseAdapter {
  BuildSummaryProteinGroupViewer viewer;

  PopupListener(BuildSummaryProteinGroupViewer viewer) {
    this.viewer = viewer;
  }

  @Override
  public void mousePressed(MouseEvent e) {
    maybeShowPopup(e);
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    maybeShowPopup(e);
  }

  private void maybeShowPopup(MouseEvent e) {
    if (e.isPopupTrigger()) {
      if (e.getComponent() instanceof JTable) {
        viewer.mniRemove.setEnabled(viewer.proteinTable.getSelectedRowCount() != 0 &&
            viewer.proteinTable.getSelectedRowCount() != viewer.proteinTable.getRowCount());
      }
      viewer.pmProtein.show(e.getComponent(),
                 e.getX(), e.getY());
    }
  }
}

class SequestProteinGroupViewer_mniAlignment_actionAdapter
    implements java.awt.event.ActionListener {
  BuildSummaryProteinGroupViewer adaptee;

  SequestProteinGroupViewer_mniAlignment_actionAdapter(
      BuildSummaryProteinGroupViewer adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.mniAlignment_actionPerformed(e);
  }
}

class SequestProteinGroupViewer_btnAlignment_actionAdapter implements java.awt.event.ActionListener {
  BuildSummaryProteinGroupViewer adaptee;

  SequestProteinGroupViewer_btnAlignment_actionAdapter(BuildSummaryProteinGroupViewer adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.btnAlignment_actionPerformed(e);
  }
}
