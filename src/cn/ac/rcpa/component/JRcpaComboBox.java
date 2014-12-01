package cn.ac.rcpa.component;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.ListCellRenderer;

import org.apache.commons.lang.StringUtils;

import cn.ac.rcpa.utils.RcpaCollectionUtils;
import cn.ac.rcpa.utils.XMLFile;

public class JRcpaComboBox<T> implements IRcpaComponent {
	private String key;

	private JLabel lblTitle;

	private JComboBox cbValue;

	private T defaultValue;

	private int defaultIndex;

	private T[] values;

	public JComboBox asComboBox() {
		return cbValue;
	}

	public JRcpaComboBox(String key, String title, T[] values, T defaultValue) {
		this.key = key;
		this.lblTitle = new JLabel("Select " + title);
		this.cbValue = new JComboBox(values);
		this.values = values;
		this.defaultValue = defaultValue;
		this.defaultIndex = RcpaCollectionUtils.getItemIndex(defaultValue, values);
	}

	public void addActionListener(ActionListener listener) {
		this.cbValue.addActionListener(listener);
	}

	public JRcpaComboBox(String key, String title, T[] values, T defaultValue,
			ListCellRenderer aRenderer) {
		this.key = key;
		this.lblTitle = new JLabel("Select " + title);
		this.cbValue = new JComboBox(values);
		this.cbValue.setRenderer(aRenderer);
		this.values = values;
		this.defaultValue = defaultValue;
		this.defaultIndex = RcpaCollectionUtils.getItemIndex(defaultValue, values);
	}

	public void setValues(T[] newValues, T newDefaultValue) {
		this.values = newValues;
		this.defaultValue = newDefaultValue;
		this.cbValue.setModel(new DefaultComboBoxModel(newValues));
		this.cbValue.setSelectedItem(newDefaultValue);
		this.defaultIndex = RcpaCollectionUtils.getItemIndex(defaultValue, values);
	}

	@SuppressWarnings("unchecked")
	public T getSelectedItem() {
		return (T) cbValue.getSelectedItem();
	}

	public void setSelectedItem(T value) {
		cbValue.setSelectedItem(value);
	}

	public int addTo(Container container, int addToRow, int totalColumnWidth) {
		container.add(lblTitle, new GridBagConstraints(0, addToRow, 1, 1, 0.0, 0.0,
				GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(10, 10, 0,
						10), 0, 0));
		container.add(cbValue, new GridBagConstraints(1, addToRow,
				totalColumnWidth - 1, 1, 1.0, 0.0, GridBagConstraints.CENTER,
				GridBagConstraints.HORIZONTAL, new Insets(10, 10, 0, 10), 0, 0));
		return addToRow + 1;
	}

	public void validate() throws IllegalAccessException {
		return;
	}

	public int columnCountNeeded() {
		return 2;
	}

	public void loadFromFile(XMLFile option) {
		String value = option.getElementValue(key, Integer.toString(defaultIndex));

		int index;
		if (StringUtils.isNumeric(value)) {
			index = Integer.parseInt(value);
		} else {
			index = defaultIndex;
		}
		if (index < 0 || index >= values.length) {
			index = defaultIndex;
		}
		setSelectedItem(values[index]);
	}

	public void saveToFile(XMLFile option) {
		option.setElementValue(key, Integer.toString(RcpaCollectionUtils
				.getItemIndex(getSelectedItem(), values)));
	}

	public double getPreferredHeight() {
		return cbValue.getPreferredSize().getHeight() + 10;
	}

}
