package cn.ac.rcpa.bio.proteomics.results.buildsummary.viewer;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

import cn.ac.rcpa.bio.models.IObjectRemoveEvent;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryPeptide;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryProtein;

public class BuildSummaryProteinViewer
    extends JSplitPane
    implements IObjectViewer {
  private ArrayList<IObjectRemoveEvent> objectRemovedEvents = new ArrayList<IObjectRemoveEvent>();

  JTextArea alignmentEditor = new JTextArea();
  JScrollPane alignmentPane = new JScrollPane();
  SequestProteinHitTableModel proteinModel = new
      SequestProteinHitTableModel();
  JTable proteinTable = new JTable(proteinModel);
  JScrollPane proteinListPane = new JScrollPane();
  JScrollPane txtProteinPane = new JScrollPane();
  JTextArea txtProtein = new JTextArea();
  JPanel proteinPane = new JPanel();
  GridBagLayout gridBagLayout1 = new GridBagLayout();

  public BuildSummaryProteinViewer() {
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void load(Object obj) throws Exception {
    if (! (obj instanceof BuildSummaryProtein)) {
      throw new IllegalArgumentException(
          "Parameter obj must be SequestProteinHit!");
    }

    BuildSummaryProtein proteinHit = (BuildSummaryProtein) obj;
    proteinModel.setGroup(proteinHit);
    proteinModel.fireTableDataChanged();
    focusProtein(-1);

    if (proteinHit.getPeptideCount() <= 1) {
      alignmentEditor.setText(null);
    }
    else {
      if (proteinHit.getPeptide(0).getSequence() != null &&
          proteinHit.getPeptide(0).getSequence().length() > 0) {
        File dir = new File("temp");
        dir.mkdirs();

        String fastafile = dir.getAbsolutePath() + File.separator + "align.fas";
        String resultfile = dir.getAbsolutePath() + File.separator +
            "align.aln";
        PrintWriter pw = new PrintWriter(new FileWriter(fastafile));
        for (int i = 0; i < proteinHit.getPeptideCount(); i++) {
          pw.println(">" + (i + 1));
          pw.println(proteinHit.getPeptide(i).getSequence());
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
    }
  }

  public void addObjectRemovedEvent(IObjectRemoveEvent e){
    objectRemovedEvents.add(e);
  }

  public void removeObjectMovedEvent(IObjectRemoveEvent e){
    objectRemovedEvents.remove(e);
  }

  public static boolean clustalw(String inputFile) {
    String[] command = {
        "extends\\clustalw.exe",
        "\"" + inputFile + "\""};
    return exec(command);
  }

  public static boolean exec(String[] command) {
    Runtime rt = Runtime.getRuntime();
    try {
      Process child = rt.exec(command);
      BufferedReader input =
          new BufferedReader
          (new InputStreamReader(child.getInputStream()));
      String line;
      while ( (line = input.readLine()) != null) {
        System.out.println(line);
      }
      input.close();
      return true;
    }
    catch (IOException e) {
      System.err.println("IOException starting process!");
      return false;
    }
  }

  private void jbInit() throws Exception {
    this.setOrientation(JSplitPane.VERTICAL_SPLIT);
    proteinPane.setLayout(gridBagLayout1);
    proteinListPane.setMinimumSize(new Dimension(24, 200));
    proteinListPane.setPreferredSize(new Dimension(24, 200));
    proteinTable.addMouseListener(new SequestProteinHitViewer_proteinTable_mouseAdapter(this));
    txtProteinPane.setMinimumSize(new Dimension(0, 50));
    txtProteinPane.setPreferredSize(new Dimension(0, 50));
    txtProtein.setMinimumSize(new Dimension(0, 50));
    txtProtein.setPreferredSize(new Dimension(0, 50));
    txtProtein.setLineWrap(true);
    proteinListPane.getViewport().add(proteinTable, null);
    proteinPane.add(proteinListPane,   new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

    txtProtein.setEditable(false);
    txtProteinPane.getViewport().add(txtProtein);
    proteinPane.add(txtProteinPane,   new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    this.add(proteinPane, JSplitPane.TOP);
    alignmentEditor.setFont(new java.awt.Font("Courier New", 0, 11));
    alignmentEditor.setEditable(false);
    alignmentPane.getViewport().add(alignmentEditor, null);
    this.add(alignmentPane, JSplitPane.BOTTOM);

    TableColumn column = null;
    for (int i = 0; i < SequestProteinHitTableModel.COLUMN_NAMES.length; i++) {
      column = proteinTable.getColumnModel().getColumn(i);
      column.setPreferredWidth(SequestProteinHitTableModel.COLUMN_WIDTHS[i]);
    }
  }

  public BuildSummaryProtein getProteinHit(){
    return proteinModel.getGroup();
  }

  void focusProtein(int index){
    if (index == -1){
      txtProtein.setText(null);
    }
    else{
      txtProtein.setText(getProteinHit().getPeptide(index).getSequence());
    }
  }

  void proteinTable_mouseClicked(MouseEvent e) {
    focusProtein( proteinTable.rowAtPoint(e.getPoint()));
  }
}

class SequestProteinHitTableModel
    extends AbstractTableModel {
  public static final String[] COLUMN_NAMES = {
      "Unique?","Filename", "Sequence", "MH+", "XCorr", "DelteCN"};
  public static final int[] COLUMN_WIDTHS = {
      20, 100, 200, 40, 40, 40};
  private BuildSummaryProtein proteinHit = null;
  public SequestProteinHitTableModel() {
  }

  public void setGroup(BuildSummaryProtein proteinHit) {
    this.proteinHit = proteinHit;
  }

  public int getColumnCount() {
    return COLUMN_NAMES.length;
  }

  @Override
  public String getColumnName(int col) {
    return COLUMN_NAMES[col];
  }

  @Override
  public Class<?> getColumnClass(int columnIndex) {
    switch (columnIndex) {
      case 0:
        return Boolean.class;
      case 3:
      case 4:
      case 5:
        return Double.class;
      default:
        return Object.class;
    }
  }

  public int getRowCount() {
    if (proteinHit == null) {
      return 0;
    }
    else {
      return proteinHit.getPeptideCount();
    }
  }

  public Object getValueAt(int rowIndex, int columnIndex) {
    if (proteinHit == null) {
      return null;
    }

    BuildSummaryPeptide peptide = (BuildSummaryPeptide) proteinHit.getPeptide(rowIndex);
    switch (columnIndex) {
      case 0:
        if (peptide.getProteinNameCount() > 1){
          return Boolean.FALSE;
        }
        else{
          return Boolean.TRUE;
        }
      case 1:
        return peptide.getPeakListInfo();
      case 2:
        return peptide.getSequence();
      case 3:
        return new Double(peptide.getTheoreticalSingleChargeMass());
      case 4:
        return new Double(peptide.getXcorr());
      case 5:
        return new Double(peptide.getDeltacn());
      default:
        return "";
    }
  }

  @Override
  public boolean isCellEditable(int row, int col) {
    return false;
  }

  public BuildSummaryProtein getGroup(){
    return proteinHit;
  }
};

class SequestProteinHitViewer_proteinTable_mouseAdapter extends java.awt.event.MouseAdapter {
  BuildSummaryProteinViewer adaptee;

  SequestProteinHitViewer_proteinTable_mouseAdapter(BuildSummaryProteinViewer adaptee) {
    this.adaptee = adaptee;
  }
  @Override
  public void mouseClicked(MouseEvent e) {
    adaptee.proteinTable_mouseClicked(e);
  }
}

