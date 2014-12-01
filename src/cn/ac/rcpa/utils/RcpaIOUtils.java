package cn.ac.rcpa.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class RcpaIOUtils {
	private RcpaIOUtils() {
	}

	public static <V extends Object> void write(PrintWriter pw,
			List<Pair<V, Integer>> counts) throws IOException {
		for (int i = 0; i < counts.size(); i++) {
			if (i == 0) {
				pw.print("<=" + counts.get(i).fst);
			} else if (i == counts.size() - 1) {
				pw.print(">" + counts.get(i - 1).fst);
			} else {
				pw.print(counts.get(i - 1).fst + "--" + counts.get(i).fst);
			}

			pw.println("\t" + counts.get(i).snd);
		}
	}

	public static void write(PrintWriter pw, double[] range1, double[] range2,
			int[][] counts) throws IOException {
		for (int i = 0; i < range2.length; i++) {
			if (i == 0) {
				pw.print("\t<=" + range2[i]);
			} else if (i == range2.length - 1) {
				pw.print("\t>" + range2[i - 1]);
			} else {
				pw.print("\t" + range2[i - 1] + "--" + range2[i]);
			}
		}
		pw.println();

		for (int i = 0; i < range1.length; i++) {
			if (i == 0) {
				pw.print("<=" + range1[i]);
			} else if (i == range1.length - 1) {
				pw.print(">" + range1[i - 1]);
			} else {
				pw.print("" + range1[i - 1] + "--" + range1[i]);
			}

			for (int j = 0; j < range2.length; j++) {
				pw.print("\t" + counts[i][j]);
			}

			pw.println();
		}
	}
}
