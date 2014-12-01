package cn.ac.rcpa;

public class ParserForwarder<T> implements IParser<T> {
	private String title;

	private String description;

	public ParserForwarder(String title, String description) {
		this.title = title;
		this.description = description;
	}

	public String getValue(T name) {
		return name.toString();
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}
}
