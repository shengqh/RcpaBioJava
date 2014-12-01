package cn.ac.rcpa.bio.sequest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import cn.ac.rcpa.bio.sequest.impl.SequestParams31Reader;
import cn.ac.rcpa.bio.sequest.impl.SequestParams32Reader;

public class SequestParamsIOFactory {
	public static SequestParams loadFromFile(String filename) throws IOException {
		File file = new File(filename);
		if (!file.exists() || !file.isFile()) {
			throw new IllegalArgumentException("File " + filename + " not exists!");
		}

		ISequestParamsReader reader = guessSequestParamsReader(filename);
		return reader.loadFromFile(filename);
	}

	private static ISequestParamsReader guessSequestParamsReader(String filename)
			throws FileNotFoundException, IOException {
		BufferedReader br = new BufferedReader(new FileReader(filename));
		try {
			String line;
			while ((line = br.readLine()) != null) {
				if (line.startsWith("enzyme_info")) {
					return new SequestParams32Reader();
				}
			}
			return new SequestParams31Reader();
		} finally {
			br.close();
		}
	}
}
