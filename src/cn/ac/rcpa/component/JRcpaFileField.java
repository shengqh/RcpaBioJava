package cn.ac.rcpa.component;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;

import cn.ac.rcpa.utils.IFileArgument;
import cn.ac.rcpa.utils.SaveFileArgument;
import cn.ac.rcpa.utils.XMLFile;

public class JRcpaFileField implements ActionListener, IRcpaComponent {
	private JButton btnFile;

	private JTextField txtValue;

	private IFileArgument fileArgument;

	private String key;

	private boolean required;

	private String getButtonText() {
		if (fileArgument instanceof SaveFileArgument) {
			return "Save " + fileArgument.getFileDescription() + "...";
		} else {
			return "Select " + fileArgument.getFileDescription() + "...";
		}
	}

	public JRcpaFileField(String key, IFileArgument fileArgument, boolean required) {
		this.key = key;
		this.fileArgument = fileArgument;
		this.required = required;
		this.txtValue = new JTextField("");
		this.btnFile = new JButton(getButtonText());
		this.btnFile.addActionListener(this);
	}

	public void setFileArgument(IFileArgument fileArgument) {
		this.fileArgument = fileArgument;
		btnFile.setText(getButtonText());
		btnFile.updateUI();
	}

	public String getFilename() {
		return txtValue.getText() == null ? "" : txtValue.getText().trim();
	}

	public void setFilename(String value) {
		txtValue.setText(value);
	}

	public void actionPerformed(ActionEvent e) {
		JFileChooser chooser = fileArgument.getFileChooser();
		if (getFilename().length() != 0) {
			chooser.setSelectedFile(new File(getFilename()));
		}
		if (chooser.showOpenDialog(txtValue) == JFileChooser.APPROVE_OPTION) {
			setFilename(chooser.getSelectedFile().getAbsolutePath());
		}
	}

	public int addTo(Container container, int addToRow, int totalColumnCount) {
		container.add(btnFile, new GridBagConstraints(0, addToRow, 1, 1, 0.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(10,
						10, 0, 10), 0, 0));
		container.add(txtValue, new GridBagConstraints(1, addToRow,
				totalColumnCount - 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(10, 10, 0, 10), 0, 0));
		return addToRow + 1;
	}

	public void validate() throws IllegalAccessException {
		if (!required && getFilename().length() == 0) {
			return;
		}

		if (!fileArgument.isValid(getFilename())) {
			throw new IllegalAccessException("Input "
					+ fileArgument.getFileDescription() + " first");
		}
	}

	public int columnCountNeeded() {
		return 2;
	}

	public void loadFromFile(XMLFile option) {
		setFilename(option.getElementValue(key, ""));
	}

	public void saveToFile(XMLFile option) {
		option.setElementValue(key, getFilename());
	}

	public double getPreferredHeight() {
		return btnFile.getPreferredSize().getHeight() + 10;
	}
}
