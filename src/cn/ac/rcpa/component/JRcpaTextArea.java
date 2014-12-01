package cn.ac.rcpa.component;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import cn.ac.rcpa.utils.XMLFile;

public class JRcpaTextArea implements IRcpaComponent {
	private String title;

	protected JLabel lblTitle;

	protected JTextArea txtValue;

	protected JScrollPane pnlValue;

	private boolean required;

	public JRcpaTextArea(String key, String title, String defaultValue,
			boolean required) {
		this.required = required;
		this.title = title;
		lblTitle = new JLabel(title);
		txtValue = new JTextArea(defaultValue);
		pnlValue = new JScrollPane(txtValue);
		pnlValue.setMinimumSize(new Dimension(800, 600));
		pnlValue.setPreferredSize(new Dimension(800, 600));
	}

	public JRcpaTextArea(String key, String title, String defaultValue,
			boolean required, Dimension size) {
		this.required = required;
		this.title = title;
		lblTitle = new JLabel(title);
		txtValue = new JTextArea(defaultValue);
		pnlValue = new JScrollPane(txtValue);
		pnlValue.setMinimumSize(size);
		pnlValue.setPreferredSize(size);
	}

	public String getText() {
		return txtValue.getText() == null ? "" : txtValue.getText().trim();
	}

	public void setText(String value) {
		txtValue.setText(value);
	}

	public int addTo(Container container, int addToRow, int totalColumnCount) {
		container.add(lblTitle, new GridBagConstraints(0, addToRow, 1, 1, 1.0, 0.0,
				GridBagConstraints.NORTHEAST, GridBagConstraints.NONE, new Insets(10,
						10, 0, 10), 0, 0));
		container.add(pnlValue, new GridBagConstraints(1, addToRow,
				totalColumnCount - 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(10, 10, 0, 10), 0, 0));
		return addToRow + 2;
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
	}

	public void saveToFile(XMLFile option) {
	}

	public double getPreferredHeight() {
		return pnlValue.getPreferredSize().getHeight()
				+ lblTitle.getPreferredSize().getHeight() + 20;
	}
}
