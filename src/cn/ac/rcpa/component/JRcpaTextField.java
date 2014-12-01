package cn.ac.rcpa.component;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JTextField;

import cn.ac.rcpa.utils.XMLFile;

public class JRcpaTextField implements IRcpaComponent {
	private String key;

	protected String title;

	private String defaultValue;

	protected JLabel lblTitle;

	protected JTextField txtValue;

	protected boolean required;

	public JRcpaTextField(String key, String title, String defaultValue,
			boolean required) {
		this.required = required;
		this.key = key;
		this.title = title;
		this.defaultValue = defaultValue;
		lblTitle = new JLabel("Input " + title);
		txtValue = new JTextField(defaultValue);
	}

	public String getText() {
		return txtValue.getText() == null ? "" : txtValue.getText().trim();
	}

	public void setText(String value) {
		txtValue.setText(value);
	}

	public int addTo(Container container, int addToRow, int totalColumnCount) {
		container.add(lblTitle, new GridBagConstraints(0, addToRow, 1, 1, 0.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(10, 10, 0,
						10), 0, 0));
		container.add(txtValue, new GridBagConstraints(1, addToRow,
				totalColumnCount - 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(10, 10, 0, 10), 0, 0));
		return addToRow + 1;
	}

	public void validate() throws IllegalAccessException {
		if (!required && getText().length() == 0) {
			return;
		}

		if (getText().length() == 0) {
			throw new IllegalAccessException("Input " + title + " first");
		}
	}

	public int columnCountNeeded() {
		return 2;
	}

	public void loadFromFile(XMLFile option) {
		setText(option.getElementValue(key, defaultValue));
	}

	public void saveToFile(XMLFile option) {
		option.setElementValue(key, getText());
	}

	public double getPreferredHeight() {
		return txtValue.getPreferredSize().getHeight() + 10;
	}
}
