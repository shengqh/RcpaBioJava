/*
 * Created on 2006-2-24
 *
 *                    Rcpa development code
 *
 * Author Sheng QuanHu(qhsheng@sibs.ac.cn / shengqh@gmail.com)
 * This code is developed by RCPA Bioinformatic Platform
 * http://www.proteomics.ac.cn/
 *
 */
package cn.ac.rcpa.component;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cn.ac.rcpa.utils.XMLFile;

public class JRcpaRangeField implements IRcpaComponent {
	private String key;

	private double defaultFrom;

	private double defaultTo;

	protected JLabel lblTitle;

	protected JLabel lblFrom;

	protected JTextField txtFrom;

	protected JLabel lblTo;

	protected JTextField txtTo;

	private JPanel rangePanel;

	private boolean required;

	public JRcpaRangeField(String key, String title, double defaultFrom,
			double defaultTo, boolean required) {
		this.key = key;
		this.defaultFrom = defaultFrom;
		this.defaultTo = defaultTo;
		this.required = required;
		initPanel(title);
	}

	private void initPanel(String title) {
		lblTitle = new JLabel("Input " + title);
		lblFrom = new JLabel("From");
		txtFrom = new JTextField(Double.toString(defaultFrom));
		lblTo = new JLabel("To");
		txtTo = new JTextField(Double.toString(defaultTo));
		rangePanel = new JPanel(new GridBagLayout());
		rangePanel.add(lblFrom, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0,
						10), 0, 0));
		rangePanel.add(txtFrom, new GridBagConstraints(1, 0, 1, 1, 0.5, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0,
						0, 0, 10), 0, 0));
		rangePanel.add(lblTo, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0,
						10), 0, 0));
		rangePanel.add(txtTo, new GridBagConstraints(3, 0, 1, 1, 0.5, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0,
						0, 0, 0), 0, 0));
	}

	public double getFrom() {
		return Double.parseDouble(txtFrom.getText().trim());
	}

	public void setFrom(double from) {
		txtFrom.setText(String.valueOf(from));
	}

	public double getTo() {
		return Double.parseDouble(txtTo.getText().trim());
	}

	public void setTo(double to) {
		txtTo.setText(String.valueOf(to));
	}

	public int addTo(Container container, int addToRow, int totalColumnCount) {
		container.add(lblTitle, new GridBagConstraints(0, addToRow, 1, 1, 0.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(10, 10, 0,
						10), 0, 0));
		container.add(rangePanel, new GridBagConstraints(1, addToRow,
				GridBagConstraints.REMAINDER, 1, 1.0, 1.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(10, 10, 0, 10), 0, 0));
		return addToRow + 1;
	}

	public void validate() throws IllegalAccessException {
		if (!required && 0 == txtFrom.getText().trim().length()
				&& 0 == txtTo.getText().trim().length()) {
			return;
		}

		txtFrom.setText(txtFrom.getText().trim());
		txtTo.setText(txtTo.getText().trim());
		try {
			getFrom();
			getTo();
		} catch (NumberFormatException ex) {
			throw new IllegalAccessException("Input From/To as number first!");
		}
	}

	public int columnCountNeeded() {
		return 2;
	}

	public void loadFromFile(XMLFile option) {
		txtFrom.setText(option.getElementValue(key + "_from", String
				.valueOf(defaultFrom)));
		txtTo.setText(option
				.getElementValue(key + "_to", String.valueOf(defaultTo)));
	}

	public void saveToFile(XMLFile option) {
		option.setElementValue(key + "_from", txtFrom.getText());
		option.setElementValue(key + "_to", txtTo.getText());
	}

	public double getPreferredHeight() {
		return txtFrom.getPreferredSize().getHeight() + 10;
	}

}
