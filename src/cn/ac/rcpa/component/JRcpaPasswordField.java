package cn.ac.rcpa.component;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPasswordField;

import cn.ac.rcpa.utils.XMLFile;

public class JRcpaPasswordField implements IRcpaComponent {
	private String title;

	protected JLabel lblTitle;

	protected JPasswordField txtValue;

	private boolean required;

	public JRcpaPasswordField(String title, String value, boolean required) {
		this.required = required;
		this.title = title;
		lblTitle = new JLabel("Input " + title);
		txtValue = new JPasswordField(value);
	}

	public String getPassword() {
		return txtValue.getPassword() == null ? "" : String.copyValueOf(txtValue
				.getPassword());
	}

	public void setPassword(String password) {
		txtValue.setText(password);
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
		if (!required && getPassword().length() == 0) {
			return;
		}

		if (getPassword().length() == 0) {
			throw new IllegalAccessException("Input " + title + " first");
		}
	}

	public int columnCountNeeded() {
		return 2;
	}

	public void loadFromFile(XMLFile option) {
		// don't load password from file
	}

	public void saveToFile(XMLFile option) {
		// dn't save password to file
	}

	public double getPreferredHeight() {
		return txtValue.getPreferredSize().getHeight() + 10;
	}
}
