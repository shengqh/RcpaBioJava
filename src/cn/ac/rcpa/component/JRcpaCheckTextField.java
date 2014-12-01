package cn.ac.rcpa.component;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import cn.ac.rcpa.utils.XMLFile;

public class JRcpaCheckTextField implements IRcpaComponent {
	private String key;

	private String valueTitle;

	private boolean defaultSelected;

	private String defaultValue;

	private JLabel lblTitle;

	private JCheckBox cbSelected;

	protected JTextField txtValue;

	public JRcpaCheckTextField(String key, String selectingTitle,
			boolean defaultSelected, String valueTitle, String defaultValue) {
		this.key = key;
		this.valueTitle = valueTitle;
		this.defaultSelected = defaultSelected;
		this.defaultValue = defaultValue;
		this.lblTitle = new JLabel("Input " + valueTitle);
		cbSelected = new JCheckBox(selectingTitle);
		txtValue = new JTextField(defaultValue);
		cbSelected.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtValue.setEnabled(cbSelected.isSelected());
			}
		});
		setSelected(defaultSelected);
	}

	public boolean isSelected() {
		return cbSelected.isSelected();
	}

	public void setSelected(boolean value) {
		cbSelected.setSelected(value);
		txtValue.setEnabled(cbSelected.isSelected());
	}

	public String getText() {
		return txtValue.getText() == null ? "" : txtValue.getText().trim();
	}

	public void setText(String value) {
		txtValue.setText(value);
	}

	public int addTo(Container container, int addToRow, int totalColumnCount) {
		container.add(cbSelected, new GridBagConstraints(0, addToRow, 1, 1, 0.0,
				0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(10,
						10, 0, 10), 0, 0));
		container.add(lblTitle, new GridBagConstraints(1, addToRow, 1, 1, 0.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(10, 10, 0,
						10), 0, 0));
		container.add(txtValue, new GridBagConstraints(2, addToRow,
				totalColumnCount - 2, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(10, 10, 0, 10), 0, 0));
		return addToRow + 1;
	}

	public void validate() throws IllegalAccessException {
		if (cbSelected.isSelected() && getText().length() == 0) {
			throw new IllegalAccessException("Input " + valueTitle + " first");
		}
	}

	public int columnCountNeeded() {
		return 3;
	}

	public void loadFromFile(XMLFile option) {
		setSelected(option.getElementValue(key + "_selected",
				Boolean.toString(defaultSelected)).equals(Boolean.TRUE.toString()));
		setText(option.getElementValue(key + "_value", defaultValue));
	}

	public void saveToFile(XMLFile option) {
		option.setElementValue(key + "_selected", Boolean.toString(isSelected()));
		option.setElementValue(key + "_value", getText());
	}

	public double getPreferredHeight() {
		return txtValue.getPreferredSize().getHeight() + 10;
	}
}
