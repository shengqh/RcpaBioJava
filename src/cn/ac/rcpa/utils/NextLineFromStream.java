package cn.ac.rcpa.utils;

import java.io.BufferedReader;
import java.io.IOException;

public class NextLineFromStream implements INextLine {
	private BufferedReader mFile;
	
	public NextLineFromStream(BufferedReader mFile){
		this.mFile = mFile;
	}
	
	public String nextLine() throws IOException {
		return mFile.readLine();
	}
}
