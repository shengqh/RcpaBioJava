package cn.ac.rcpa.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StreamGobbler extends Thread {
	private InputStream is;

	private String type;

	private boolean show;

	public StreamGobbler(InputStream is, String type, boolean show) {
		this.is = is;
		this.type = type;
		this.show = show;
	}

	@Override
	public void run() {
		try {
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null) {
				if (show) {
					System.out.println(type + ">" + line);
					System.out.flush();
				}
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
