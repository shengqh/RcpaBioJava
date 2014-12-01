package cn.ac.rcpa.utils;

import java.io.File;

public class ShellUtils {
	private ShellUtils() {
	}

	public static boolean execute(String[] cmdarray, boolean showOutput) {
		return execute(cmdarray, null, showOutput);
	}

	public static boolean execute(String[] cmdarray, File dir, boolean showOutput) {
		try {
			final Process child = Runtime.getRuntime().exec(cmdarray, null, dir);

			return waitProcess(child, showOutput);
		} catch (Throwable t) {
			t.printStackTrace();
			return false;
		}
	}

	private static boolean waitProcess(Process child, boolean showOutput)
			throws InterruptedException {
		// any error message?
		StreamGobbler errorGobbler = new StreamGobbler(child.getErrorStream(),
				"ERROR", showOutput);

		// any output?
		StreamGobbler outputGobbler = new StreamGobbler(child.getInputStream(),
				"OUTPUT", showOutput);

		// kick them off
		errorGobbler.start();
		outputGobbler.start();

		int exitVal = child.waitFor();
		if (showOutput) {
			System.out.println("ExitValue: " + exitVal);
		}

		return exitVal == 0;
	}

	public static boolean execute(String command, boolean showOutput) {
		return execute(command, null, showOutput);
	}

	public static boolean execute(String command, File dir, boolean showOutput) {
		try {
			final Process child = Runtime.getRuntime().exec(command, null, dir);

			return waitProcess(child, showOutput);
		} catch (Throwable t) {
			t.printStackTrace();
			return false;
		}
	}
}
