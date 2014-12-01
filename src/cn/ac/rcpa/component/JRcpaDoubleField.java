package cn.ac.rcpa.component;

public class JRcpaDoubleField extends JRcpaTextField {

	public JRcpaDoubleField(String key, String title, double defaultValue,
			boolean required) {
		super(key, title, Double.toString(defaultValue), required);
	}

	public void validate() throws IllegalAccessException {
		super.validate();

		String value = getText();
		if (value.length() == 0) {
			return;
		}

		try {
			Double.parseDouble(value);
		} catch (Exception ex) {
			throw new IllegalAccessException("Input " + super.title
					+ " (double type value such like 0.01) first");
		}
	}

	public double getValue() {
		return Double.parseDouble(getText());
	}
}
