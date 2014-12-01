package cn.ac.rcpa.bio.tools;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jdom.JDOMException;

import cn.ac.rcpa.Constants;
import cn.ac.rcpa.bio.database.SequenceDatabaseType;
import cn.ac.rcpa.bio.proteomics.IIdentifiedProteinGroup;
import cn.ac.rcpa.bio.proteomics.IIdentifiedResult;
import cn.ac.rcpa.bio.proteomics.IdentifiedResultIOFactory;
import cn.ac.rcpa.bio.proteomics.annotator.IIdentifiedResultAnnotator;
import cn.ac.rcpa.bio.proteomics.annotator.impl.IdentifiedResultGOAnnotator;
import cn.ac.rcpa.bio.proteomics.processor.IdentifiedProteinGroupProcessorFactory;
import cn.ac.rcpa.bio.proteomics.processor.IdentifiedResultProcessor;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryResult;
import cn.ac.rcpa.processor.IProcessor;
import cn.ac.rcpa.utils.GUIUtils;
import cn.ac.rcpa.utils.RcpaFileUtils;
import cn.ac.rcpa.utils.SpecialSwingFileFilter;
import cn.ac.rcpa.utils.XMLFile;

/**
 * <p>
 * Title: RCPA Package
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: RCPA.SIBS.AC.CN
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */

public class SequestProteinGroupSimplifierUI extends JFrame {
	public static final String version = "1.0.0";

	private static final String title = "Sequest Protein Group Simplifier";

	private String optionFileName = RcpaFileUtils.getConfigFile(this.getClass());

	final String[] proteinSequences = { "Longest sequence length",
			"Shortest sequence length" };

	JComboBox cbxDatabaseType = new JComboBox(SequenceDatabaseType.values());

	JLabel labelDatabaseType = new JLabel();

	JPanel pnlButton = new JPanel();

	JButton btnGo = new JButton();

	JButton btnClose = new JButton();

	JButton btnSequestResultFile = new JButton();

	JTextField txtSequestResultFile = new JTextField();

	JCheckBox cbFilterByGOAFirst = new JCheckBox();

	GridBagLayout gridBagLayout1 = new GridBagLayout();

	public SequestProteinGroupSimplifierUI() {
		try {
			jbInit();
			loadOption();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void loadOption() {
		try {
			XMLFile option = new XMLFile(optionFileName);
			txtSequestResultFile.setText(option.getElementValue("SequestResultFile",
					""));
			try {
				cbFilterByGOAFirst.setSelected(Boolean.valueOf(option.getElementValue(
						"FilterByGOA", "TRUE")));
			} catch (Exception ex) {
				cbFilterByGOAFirst.setSelected(true);
			}

			SequenceDatabaseType dbType;
			try {
				dbType = SequenceDatabaseType.valueOf(option.getElementValue("DatabaseType",
						"IPI"));
			} catch (IllegalArgumentException ex) {
				dbType = SequenceDatabaseType.IPI;
			}
			cbxDatabaseType.setSelectedItem(dbType);
		} catch (Exception ex) {
			throw new RuntimeException("Load option error : " + ex.getMessage());
		}
	}

	private void saveOption() {
		try {
			XMLFile option = new XMLFile(optionFileName);
			option.setElementValue("SequestResultFile", txtSequestResultFile
					.getText());
			option.setElementValue("FilterByGOA", Boolean.toString(cbFilterByGOAFirst
					.isSelected()));
			option.setElementValue("DatabaseType", cbxDatabaseType.getSelectedItem()
					.toString());
			option.saveToFile();
		} catch (Exception ex) {
			throw new RuntimeException("Save option error : " + ex.getMessage());
		}
	}

	void jbInit() throws Exception {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		btnClose.setMaximumSize(new Dimension(80, 25));
		btnClose.setMinimumSize(new Dimension(80, 25));
		btnClose.setPreferredSize(new Dimension(80, 25));
		btnClose.setRolloverEnabled(false);
		btnClose.setText("Close");
		btnClose
				.addActionListener(new SequestProteinGroupSimplifierUI_btnClose_actionAdapter(
						this));

		btnGo.setMaximumSize(new Dimension(80, 25));
		btnGo.setMinimumSize(new Dimension(80, 25));
		btnGo.setPreferredSize(new Dimension(80, 25));
		btnGo.setRolloverEnabled(false);
		btnGo.setText("Go");
		btnGo
				.addActionListener(new SequestProteinGroupSimplifierUI_btnGo_actionAdapter(
						this));

		this.setTitle(Constants.getSQHTitle(title, version));
		this.getContentPane().setLayout(gridBagLayout1);

		btnSequestResultFile
				.setText("Select BuildSummary non-redundant result file");
		btnSequestResultFile
				.addActionListener(new SequestProteinGroupSimplifierUI_btnSequestResultFile_actionAdapter(
						this));
		txtSequestResultFile.setText("");

		labelDatabaseType.setText("Database type : ");
		cbFilterByGOAFirst.setSelected(true);
		cbFilterByGOAFirst.setText("Keep the entries having GOA first");
		pnlButton.add(btnGo, null);
		pnlButton.add(btnClose, null);
		this.getContentPane().add(
				pnlButton,
				new GridBagConstraints(0, 4, 2, 1, 1.0, 1.0, GridBagConstraints.CENTER,
						GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 0, 0));
		this.getContentPane().add(
				cbxDatabaseType,
				new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
						GridBagConstraints.BOTH, new Insets(10, 10, 0, 10), 0, 0));
		this.getContentPane().add(
				labelDatabaseType,
				new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
						GridBagConstraints.NONE, new Insets(10, 10, 0, 10), 0, 0));
		this.getContentPane().add(
				cbFilterByGOAFirst,
				new GridBagConstraints(0, 2, 2, 1, 0.0, 0.0, GridBagConstraints.WEST,
						GridBagConstraints.NONE, new Insets(10, 10, 0, 10), 0, 0));
		this.getContentPane().add(
				txtSequestResultFile,
				new GridBagConstraints(0, 1, 2, 1, 1.0, 0.0, GridBagConstraints.CENTER,
						GridBagConstraints.BOTH, new Insets(10, 10, 0, 10), 0, 0));
		this.getContentPane().add(
				btnSequestResultFile,
				new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0, GridBagConstraints.WEST,
						GridBagConstraints.NONE, new Insets(10, 10, 0, 10), 0, 0));
	}

	public static void main(String[] args) {
		SequestProteinGroupSimplifierUI frame = new SequestProteinGroupSimplifierUI();
		frame.setSize(600, 250);
		GUIUtils.setFrameDesktopCentre(frame);
		frame.setVisible(true);
	}

	void btnClose_actionPerformed(ActionEvent e) {
		dispose();
	}

	void btnSequestResultFile_actionPerformed(ActionEvent e) {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setFileFilter(new SpecialSwingFileFilter("noredundant",
				"Non-redundant file", false));
		if (txtSequestResultFile.getText() != null
				&& txtSequestResultFile.getText().trim().length() != 0) {
			chooser.setCurrentDirectory(new File(txtSequestResultFile.getText()
					.trim()).getParentFile());
		}
		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			txtSequestResultFile.setText(chooser.getSelectedFile().getAbsolutePath());
		}
	}

	@SuppressWarnings("unchecked")
	void btnGo_actionPerformed(ActionEvent e) {
		final File resultFile = new File(txtSequestResultFile.getText().trim());
		if (!resultFile.exists()) {
			JOptionPane.showMessageDialog(this,
					"Error : select BuildSummary non-redundant result file first!",
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		saveOption();

		try {
			final boolean filterByGOA = cbFilterByGOAFirst.isSelected();

			IIdentifiedResultAnnotator annotator = null;
			if (filterByGOA) {
				annotator = new IdentifiedResultGOAnnotator(
						(SequenceDatabaseType) cbxDatabaseType.getSelectedItem());
			}

			BuildSummaryResult sr = IdentifiedResultIOFactory.readBuildSummaryResult(
					resultFile.getAbsolutePath(), annotator);

			IProcessor<IIdentifiedProteinGroup> groupProcessor = IdentifiedProteinGroupProcessorFactory
					.createUnduplicatedProteinProcessor(filterByGOA, false);

			IProcessor<IIdentifiedResult> resultProcessor = new IdentifiedResultProcessor(
					groupProcessor);

			List<String> processed = resultProcessor.process(sr);

			IdentifiedResultIOFactory.writeBuildSummaryResult(resultFile
					.getAbsolutePath()
					+ ".unduplicated", sr);

			RcpaFileUtils.writeFile(resultFile.getAbsolutePath()
					+ ".unduplicated.processed", processed.toArray(new String[0]));

			JOptionPane.showMessageDialog(this, "Finished!", "Congratulation",
					JOptionPane.PLAIN_MESSAGE);
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error : " + ex.getMessage(),
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}

class SequestProteinGroupSimplifierUI_btnGo_actionAdapter implements
		java.awt.event.ActionListener {
	SequestProteinGroupSimplifierUI adaptee;

	SequestProteinGroupSimplifierUI_btnGo_actionAdapter(
			SequestProteinGroupSimplifierUI adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.btnGo_actionPerformed(e);
	}
}

class SequestProteinGroupSimplifierUI_btnClose_actionAdapter implements
		java.awt.event.ActionListener {
	SequestProteinGroupSimplifierUI adaptee;

	SequestProteinGroupSimplifierUI_btnClose_actionAdapter(
			SequestProteinGroupSimplifierUI adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.btnClose_actionPerformed(e);
	}
}

class SequestProteinGroupSimplifierUI_btnSequestResultFile_actionAdapter
		implements java.awt.event.ActionListener {
	SequestProteinGroupSimplifierUI adaptee;

	SequestProteinGroupSimplifierUI_btnSequestResultFile_actionAdapter(
			SequestProteinGroupSimplifierUI adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.btnSequestResultFile_actionPerformed(e);
	}
}
