package cn.ac.rcpa.component;

public class JRcpaIntegerField extends JRcpaTextField {

	public JRcpaIntegerField(String key, String title, int defaultValue,
			boolean required) {
		super(key, title, Integer.toString(defaultValue), required);
	}

	public void validate() throws IllegalAccessException {
		super.validate();

		String value = getText();
		if (value.length() == 0) {
			return;
		}

		try {
			Integer.parseInt(value);
		} catch (Exception ex) {
			throw new IllegalAccessException("Input " + super.title
					+ " (int type value such like 1) first");
		}
	}

	public int getValue() {
		return Integer.parseInt(getText());
	}
}
