package cn.ac.rcpa.utils;

import java.io.IOException;
import java.util.List;

public class NextLineFromStrings implements INextLine {
	private int curIndex;

	private String[] lines;

	public NextLineFromStrings(String[] lines) {
		this.curIndex = 0;
		this.lines = lines;
	}

	public NextLineFromStrings(List<String> lines) {
		this.curIndex = 0;
		this.lines = lines.toArray(new String[0]);
	}

	public String nextLine() throws IOException {
		if (curIndex < lines.length) {
			return lines[curIndex++];
		}
		return null;
	}
}
