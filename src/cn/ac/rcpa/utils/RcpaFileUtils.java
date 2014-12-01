package cn.ac.rcpa.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.ClassUtils;

/**
 * <p>
 * Title: RCPA Package
 * </p>
 * 
 * <p>
 * Description: FileUtils
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * 
 * <p>
 * Company: RCPA.SIBS.AC.CN
 * </p>
 * 
 * @author Sheng Quan-Hu
 * @version 1.0
 */
public class RcpaFileUtils {
	private RcpaFileUtils() {
	}

	/**
	 * Get filename changed extension
	 * 
	 * @param oldFilename
	 *          String
	 * @param newExtension
	 *          String
	 * @return String filename with new extension
	 */
	public static String changeExtension(String oldFilename, String newExtension) {
		if (oldFilename == null || newExtension == null) {
			throw new IllegalArgumentException(
					"oldFilename and newExtension should not be null!");
		}

		String result = FilenameUtils.removeExtension(oldFilename);

		if (newExtension.length() != 0) {
			if (newExtension.startsWith(".")) {
				result = result + newExtension;
			} else {
				result = result + "." + newExtension;
			}
		}

		return result;
	}

	/**
	 * Create temp file name based on current datetime
	 * 
	 * @return String format as "yyyyMMddHHmmss"
	 */
	public static String getTempFileName() {
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		return df.format(new Date());
	}

	/**
	 * 取得给定目录下的子目录
	 * 
	 * @param directory
	 *          File
	 * @return File[]
	 */
	public static File[] getSubDirectories(File directory) {
		return directory.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				return pathname.isDirectory();
			}
		});
	}

	/**
	 * Read file to array of String.
	 * 
	 * @param filename
	 *          String
	 * @param skipEmptyLine
	 *          boolean
	 * @throws IOException
	 * @return String[]
	 */
	public static String[] readFile(String filename, boolean skipEmptyLine)
			throws IOException {
		final List<String> result = new ArrayList<String>();

		BufferedReader br = new BufferedReader(new FileReader(filename));
		try {
			String line;
			while ((line = br.readLine()) != null) {
				if (skipEmptyLine && line.trim().length() == 0) {
					continue;
				}
				result.add(line);
			}
		} finally {
			br.close();
		}
		return (String[]) result.toArray(new String[0]);
	}

	public static String readFileWithoutLineBreak(String filename,
			boolean skipEmptyLine) throws IOException {
		StringBuilder sb = new StringBuilder();

		BufferedReader br = new BufferedReader(new FileReader(filename));
		try {
			String line;
			while ((line = br.readLine()) != null) {
				if (skipEmptyLine && line.trim().length() == 0) {
					continue;
				}
				sb.append(line);
			}
		} finally {
			br.close();
		}
		return sb.toString();
	}

	/**
	 * Read file to array of String.
	 * 
	 * @param filename
	 *          String
	 * @throws IOException
	 * @return String[]
	 */
	public static String[] readFile(String filename) throws IOException {
		return readFile(filename, false);
	}

	public static void writeFile(String filename, String[] lines)
			throws IOException {
		PrintWriter pw = new PrintWriter(new FileWriter(new File(filename)));
		try {
			for (String line : lines) {
				pw.println(line);
			}
		} finally {
			pw.close();
		}
	}

	@SuppressWarnings("unchecked")
	public static String getConfigFile(Class mainObj) {
		return getConfigFile(ClassUtils.getShortClassName(mainObj));
	}

	public static String getConfigFile(String mainObj) {
		File configDir = new File("config");
		if (!configDir.exists()) {
			configDir.mkdirs();
		}

		return configDir.getAbsolutePath() + "/"
				+ mainObj + ".conf";
	}

	public static String[] getFilenames(File[] files) {
		ArrayList<String> result = new ArrayList<String>();
		for (File afile : files) {
			result.add(afile.getAbsolutePath());
		}
		return result.toArray(new String[0]);
	}

	public static void mergeFiles(String[] sourceFiles, String targetFile)
			throws IOException {
		PrintWriter pw = new PrintWriter(targetFile);
		try {
			for (String sourceFile : sourceFiles) {
				BufferedReader br = new BufferedReader(new FileReader(sourceFile));
				try {
					String line;
					while ((line = br.readLine()) != null) {
						pw.println(line);
					}
				} finally {
					br.close();
				}
			}

		} finally {
			pw.close();
		}
	}

}
