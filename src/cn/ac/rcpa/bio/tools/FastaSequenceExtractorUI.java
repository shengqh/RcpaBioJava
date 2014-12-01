package cn.ac.rcpa.bio.tools;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.biojava.bio.seq.Sequence;

import cn.ac.rcpa.bio.utils.SequenceUtils;
import cn.ac.rcpa.utils.GUIUtils;
import cn.ac.rcpa.utils.SpecialSwingFileFilter;

/**
 * <p>Title: RCPA Package</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: RCPA.SIBS.AC.CN</p>
 *
 * @author Sheng Quan-Hu
 * @version 1.0
 */
public class FastaSequenceExtractorUI
    extends JFrame {
  final String version = "1.0.0";

  java.util.List<Sequence> originSequences = new ArrayList<Sequence> ();
  java.util.Set<Sequence> selectedSequences = new HashSet<Sequence> ();

  DefaultListModel originModel = new DefaultListModel(); ;
  DefaultListModel selectedModel = new DefaultListModel();

  JList lstOrigin = new JList(originModel);
  JList lstExtractor = new JList(selectedModel);

  JScrollPane pnlOrigin = new JScrollPane(lstOrigin);
  JScrollPane pnlExtractor = new JScrollPane(lstExtractor);

  JFileChooser filechooser = new JFileChooser();

  JPanel pnlMove = new JPanel();
  JButton btnMoveToExtract = new JButton();
  JButton btnMoveAllToExtract = new JButton();
  JButton btnRemoveFromExtract = new JButton();
  JButton btnRemoveAllFromExtract = new JButton();
  JPanel pnlButton = new JPanel();
  JButton btnExtract = new JButton();
  JButton btnClose = new JButton();
  JButton btnLoad = new JButton();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JPanel jPanel1 = new JPanel();
  GridBagLayout gridBagLayout2 = new GridBagLayout();

  public FastaSequenceExtractorUI() {
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public static void main(String[] args) {
    FastaSequenceExtractorUI frame = new FastaSequenceExtractorUI();
    frame.setSize(800, 420);
    GUIUtils.setFrameDesktopCentre(frame);
    frame.setVisible(true);
  }

  private void jbInit() throws Exception {
    this.getContentPane().setLayout(gridBagLayout1);

    lstOrigin.setCellRenderer(new SequenceRenderer());
    lstExtractor.setCellRenderer(new SequenceRenderer());

    filechooser.setFileFilter(new SpecialSwingFileFilter("fasta", "Fasta file", false));

    btnMoveToExtract.setPreferredSize(new Dimension(47, 25));
    btnMoveToExtract.setText(">");
    btnMoveToExtract.addActionListener(new
                                       FastaSequenceExtractorUI_btnMoveToExtract_actionAdapter(this));
    btnLoad.setPreferredSize(new Dimension(80, 25));
    btnLoad.setText("Load");
    btnLoad.addActionListener(new
                              FastaSequenceExtractorUI_jButton1_actionAdapter(this));
    btnMoveAllToExtract.setText(">>");
    btnMoveAllToExtract.addActionListener(new
                                          FastaSequenceExtractorUI_btnMoveAllToExtract_actionAdapter(this));
    btnRemoveFromExtract.setPreferredSize(new Dimension(47, 25));
    btnRemoveFromExtract.setText("<");
    btnRemoveFromExtract.addActionListener(new
                                           FastaSequenceExtractorUI_btnRemoveFromExtract_actionAdapter(this));
    btnRemoveAllFromExtract.setText("<<");
    btnRemoveAllFromExtract.addActionListener(new
                                              FastaSequenceExtractorUI_btnRemoveAllFromExtract_actionAdapter(this));
    btnExtract.setPreferredSize(new Dimension(80, 25));
    btnExtract.setText("Extract");
    btnExtract.addActionListener(new
                                 FastaSequenceExtractorUI_btnExtract_actionAdapter(this));
    btnClose.setMaximumSize(new Dimension(70, 25));
    btnClose.setMinimumSize(new Dimension(70, 25));
    btnClose.setPreferredSize(new Dimension(80, 25));
    btnClose.setText("Close");
    btnClose.addActionListener(new
                               FastaSequenceExtractorUI_btnClose_actionAdapter(this));
    jPanel1.setLayout(gridBagLayout2);
    pnlMove.add(btnMoveToExtract);
    pnlMove.add(btnMoveAllToExtract);
    pnlMove.add(btnRemoveFromExtract);
    pnlMove.add(btnRemoveAllFromExtract);
    pnlButton.add(btnLoad);
    pnlButton.add(btnExtract);
    pnlButton.add(btnClose);
    this.getContentPane().add(jPanel1,
                              new GridBagConstraints(0, 0, 3, 1, 1.0, 1.0
        , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
        new Insets(10, 10, 10, 10), 0, 0));
    jPanel1.add(pnlOrigin, new GridBagConstraints(0, 0, 1, 1, 0.5, 1.0
                                                  , GridBagConstraints.CENTER,
                                                  GridBagConstraints.BOTH,
                                                  new Insets(0, 0, 0, 0), 0, 0));
    jPanel1.add(pnlExtractor, new GridBagConstraints(2, 0, 1, 1, 0.5, 1.0
        , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
        new Insets(0, 0, 0, 0), 0, 0));
    jPanel1.add(pnlMove, new GridBagConstraints(1, 0, 1, 1, 0.0, 1.0
                                                , GridBagConstraints.CENTER,
                                                GridBagConstraints.VERTICAL,
                                                new Insets(0, 0, 0, 0), -141, 0));
    this.getContentPane().add(pnlButton,
                              new GridBagConstraints(0, 1, 2, 1, 1.0, 0.0
        , GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
        new Insets(10, 10, 10, 10), 0, 0));
  }

  public void btnClose_actionPerformed(ActionEvent e) {
    dispose();
  }

  public void btnLoad_actionPerformed(ActionEvent e) {
    try {
//      originSequences.clear();
//      originSequences.addAll(FastaSequenceExtractor.readProtein(
//          new File("F:\\Science\\Data\\HPPP\\six_high_abundant_protein.fasta")));

      filechooser.setDialogTitle("Select fasta file:");
      if (filechooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
        originSequences.clear();
        originSequences.addAll(SequenceUtils.readFastaProteins(filechooser.
            getSelectedFile()));
        refreshOriginSequenceList();
      }
    }
    catch (Exception ex) {
      JOptionPane.showMessageDialog(this,
                                    "Load Fasta File Error : " + ex.getMessage(),
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
    }
  }

  public void btnMoveToExtract_actionPerformed(ActionEvent e) {
    Object[] sequences = lstOrigin.getSelectedValues();
    for (int i = 0; i < sequences.length; i++) {
      selectedSequences.add( (Sequence) sequences[i]);
    }

    refreshSelectedSequenceList();
  }

  private void setElement(DefaultListModel model, Collection<Sequence> collection) {
    model.clear();

    java.util.List<Sequence> sequences = new ArrayList<Sequence> (collection);
    Collections.sort(sequences, new Comparator<Sequence>() {
      public int compare(Sequence seq1, Sequence seq2) {
        return seq1.getName().compareTo(seq2.getName());
      }

      @Override
      public boolean equals(Object obj) {
        return false;
      }
    });

    for (Iterator iter = sequences.iterator(); iter.hasNext(); ) {
      Sequence seq = (Sequence) iter.next();
      model.addElement(seq);
    }
  }

  private void refreshSelectedSequenceList() {
    setElement(selectedModel, selectedSequences);
  }

  private void refreshOriginSequenceList() {
    setElement(originModel, originSequences);
  }

  public void btnMoveAllToExtract_actionPerformed(ActionEvent e) {
    selectedSequences.clear();
    selectedSequences.addAll(originSequences);
    refreshSelectedSequenceList();
  }

  public void btnRemoveFromExtract_actionPerformed(ActionEvent e) {
    Object[] sequences = lstExtractor.getSelectedValues();
    for (int i = 0; i < sequences.length; i++) {
      selectedSequences.remove(sequences[i]);
    }

    refreshSelectedSequenceList();
  }

  public void btnRemoveAllFromExtract_actionPerformed(ActionEvent e) {
    selectedSequences.clear();
    refreshSelectedSequenceList();
  }

  public void btnExtract_actionPerformed(ActionEvent e) {
    if (selectedSequences.isEmpty()) {
      return;
    }

    filechooser.setDialogTitle("Save fasta file to");
    if (filechooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
      try {
        SequenceUtils.writeFasta(filechooser.getSelectedFile(),
                                     selectedSequences);
      }
      catch (Exception ex) {
        JOptionPane.showMessageDialog(this,
                                      "Save fasta file error : " +
                                      ex.getMessage(),
                                      "Error",
                                      JOptionPane.ERROR_MESSAGE);
      }
    }
  }
}

class FastaSequenceExtractorUI_btnExtract_actionAdapter
    implements ActionListener {
  private FastaSequenceExtractorUI adaptee;
  FastaSequenceExtractorUI_btnExtract_actionAdapter(FastaSequenceExtractorUI
      adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.btnExtract_actionPerformed(e);
  }
}

class FastaSequenceExtractorUI_btnClose_actionAdapter
    implements ActionListener {
  private FastaSequenceExtractorUI adaptee;
  FastaSequenceExtractorUI_btnClose_actionAdapter(FastaSequenceExtractorUI
                                                  adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.btnClose_actionPerformed(e);
  }
}

class FastaSequenceExtractorUI_jButton1_actionAdapter
    implements ActionListener {
  private FastaSequenceExtractorUI adaptee;
  FastaSequenceExtractorUI_jButton1_actionAdapter(FastaSequenceExtractorUI
                                                  adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {

    adaptee.btnLoad_actionPerformed(e);
  }
}

class FastaSequenceExtractorUI_btnMoveToExtract_actionAdapter
    implements ActionListener {
  private FastaSequenceExtractorUI adaptee;
  FastaSequenceExtractorUI_btnMoveToExtract_actionAdapter(
      FastaSequenceExtractorUI adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.btnMoveToExtract_actionPerformed(e);
  }
}

class FastaSequenceExtractorUI_btnMoveAllToExtract_actionAdapter
    implements ActionListener {
  private FastaSequenceExtractorUI adaptee;
  FastaSequenceExtractorUI_btnMoveAllToExtract_actionAdapter(
      FastaSequenceExtractorUI adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {

    adaptee.btnMoveAllToExtract_actionPerformed(e);
  }
}

class FastaSequenceExtractorUI_btnRemoveFromExtract_actionAdapter
    implements ActionListener {
  private FastaSequenceExtractorUI adaptee;
  FastaSequenceExtractorUI_btnRemoveFromExtract_actionAdapter(
      FastaSequenceExtractorUI adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.btnRemoveFromExtract_actionPerformed(e);
  }
}

class FastaSequenceExtractorUI_btnRemoveAllFromExtract_actionAdapter
    implements ActionListener {
  private FastaSequenceExtractorUI adaptee;
  FastaSequenceExtractorUI_btnRemoveAllFromExtract_actionAdapter(
      FastaSequenceExtractorUI adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.btnRemoveAllFromExtract_actionPerformed(e);
  }
}
