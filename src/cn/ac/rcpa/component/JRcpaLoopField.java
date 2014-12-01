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

public abstract class JRcpaLoopField<T extends Number> implements
		IRcpaComponent {

	public static JRcpaLoopField<Double> createDouble(String key, String title,
			Double defaultFrom, Double defaultTo, Double defaultStep, boolean required) {
		return new JRcpaLoopField<Double>(key, title, defaultFrom, defaultTo,
				defaultStep, required) {
			@Override
			public Double getFrom() {
				return Double.parseDouble(txtFrom.getText().trim());
			}

			@Override
			public Double getTo() {
				return Double.parseDouble(txtTo.getText().trim());
			}

			@Override
			public Double getStep() {
				return Double.parseDouble(txtStep.getText().trim());
			}
		};
	}

	public static JRcpaLoopField<Integer> createInteger(String key, String title,
			Integer defaultFrom, Integer defaultTo, Integer defaultStep,
			boolean required) {
		return new JRcpaLoopField<Integer>(key, title, defaultFrom, defaultTo,
				defaultStep, required) {
			@Override
			public Integer getFrom() {
				return Integer.parseInt(txtFrom.getText().trim());
			}

			@Override
			public Integer getTo() {
				return Integer.parseInt(txtTo.getText().trim());
			}

			@Override
			public Integer getStep() {
				return Integer.parseInt(txtStep.getText().trim());
			}
		};
	}

	private String key;

	private T defaultFrom;

	private T defaultTo;

	private T defaultStep;

	protected JLabel lblTitle;

	protected JLabel lblFrom;

	protected JTextField txtFrom;

	protected JLabel lblTo;

	protected JTextField txtTo;

	protected JLabel lblStep;

	protected JTextField txtStep;

	private JPanel rangePanel;

	private boolean required;

	private JRcpaLoopField(String key, String title, T defaultFrom, T defaultTo,
			T defaultStep, boolean required) {
		this.key = key;
		this.defaultFrom = defaultFrom;
		this.defaultTo = defaultTo;
		this.defaultStep = defaultStep;
		this.required = required;
		initPanel(title);
	}

	private void initPanel(String title) {
		lblTitle = new JLabel("Input " + title);
		lblFrom = new JLabel("From");
		txtFrom = new JTextField(String.valueOf(defaultFrom));
		lblTo = new JLabel("To");
		txtTo = new JTextField(String.valueOf(defaultTo));
		lblStep = new JLabel("Step");
		txtStep = new JTextField(String.valueOf(defaultStep));
		rangePanel = new JPanel(new GridBagLayout());
		rangePanel.add(lblFrom, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0,
						10), 0, 0));
		rangePanel.add(txtFrom, new GridBagConstraints(1, 0, 1, 1, 0.33, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0,
						0, 0, 10), 0, 0));
		rangePanel.add(lblTo, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0,
						10), 0, 0));
		rangePanel.add(txtTo, new GridBagConstraints(3, 0, 1, 1, 0.33, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0,
						0, 0, 0), 0, 0));
		rangePanel.add(lblStep, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0,
						10), 0, 0));
		rangePanel.add(txtStep, new GridBagConstraints(5, 0, 1, 1, 0.33, 0.0,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0,
						0, 0, 0), 0, 0));
	}

	abstract public T getFrom();

	public void setFrom(T value) {
		txtFrom.setText(String.valueOf(value));
	}

	abstract public T getTo();

	public void setTo(T value) {
		txtTo.setText(String.valueOf(value));
	}

	abstract public T getStep();

	public void setStep(T value) {
		txtStep.setText(String.valueOf(value));
	}

	public int addTo(Container container, int addToRow, int totalColumnCount) {
		container.add(lblTitle, new GridBagConstraints(0, addToRow, 1, 1, 0.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(10, 10, 0,
						10), 0, 0));
		container.add(rangePanel, new GridBagConstraints(1, addToRow,
				GridBagConstraints.REMAINDER, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(10, 10, 0, 10), 0, 0));
		return addToRow + 1;
	}

	public void validate() throws IllegalAccessException {
		if (!required && 0 == txtFrom.getText().trim().length()
				&& 0 == txtTo.getText().trim().length()
				&& 0 == txtStep.getText().trim().length()) {
			return;
		}

		txtFrom.setText(txtFrom.getText().trim());
		txtTo.setText(txtTo.getText().trim());
		txtStep.setText(txtStep.getText().trim());
		try {
			getFrom();
			getTo();
			getStep();
		} catch (NumberFormatException ex) {
			throw new IllegalAccessException("Input From/To/Step as number first!");
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
		txtStep.setText(option.getElementValue(key + "_step", String
				.valueOf(defaultStep)));
	}

	public void saveToFile(XMLFile option) {
		option.setElementValue(key + "_from", txtFrom.getText());
		option.setElementValue(key + "_to", txtTo.getText());
		option.setElementValue(key + "_step", txtStep.getText());
	}

	public double getPreferredHeight() {
		return txtFrom.getPreferredSize().getHeight() + 10;
	}

}
