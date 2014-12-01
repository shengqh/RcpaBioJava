package cn.ac.rcpa.bio.annotation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import cn.ac.rcpa.utils.RcpaFileUtils;

public class StatisticRanges {
  private static DecimalFormat df = new DecimalFormat("0.####");

  private final static double[] DEFAULT_PROTEIN_MW_THRESHOLDS = { 10000, 20000,
      30000, 40000, 50000, 60000, 70000, 80000, 90000, 100000, 110000, 120000,
      130000, 140000, 150000, 160000, 170000, 180000, 190000, 200000, 300000,
      400000, 500000, 600000 };

  private StatisticRanges() {
  }

  public static double[] getProteinMWRange() {
    return getRange(new File("config/DistributionProteinMWRange.txt"),
        DEFAULT_PROTEIN_MW_THRESHOLDS);
  }

  public static double[] getPIRange() {
    return getRange(new File("config/DistributionPIRange.txt"), 4, 10, 1);
  }

  public static double[] getPeptideMWRange() {
    return getRange(new File("config/DistributionPeptideMWRange.txt"), 1000,
        5000, 500);
  }

  public static double[] getPrecursorMZRange() {
    return getRange(new File("config/DistributionPrecursorMZRange.txt"), 400,
        2000, 100);
  }

  public static double[] getPrecursorDiffMZRange() {
    return getRange(new File("config/DistributionPrecursorDiffMZRange.txt"),
        -2.9, 2.9, 0.1);
  }

  public static double[] getPrecursorDiffMZPPMRange() {
    return getRange(new File("config/DistributionPrecursorDiffMZPPMRange.txt"),
        -1000, 1000, 50);
  }

  public static double[] getGRAVYRange() {
    return getRange(new File("config/DistributionGRAVYRange.txt"), -1.5, 1.5,
        0.5);
  }

  public static double[] getXCorrRange() {
    return getRange(new File("config/DistributionXCorrRange.txt"), 0.0, 5.0,
        0.05);
  }

  public static double[] getDeltaCnRange() {
    return getRange(new File("config/DistributionDeltaCnRange.txt"), 0.0, 0.5,
        0.01);
  }

  private static double[] getRange(File configFile, double from, double to,
      double step) {
    List<Double> ranges = new ArrayList<Double>();
    for (double value = from; value <= to; value += step) {
      ranges.add(value);
    }

    double[] defaultRange = new double[ranges.size()];
    for (int i = 0; i < defaultRange.length; i++) {
      defaultRange[i] = ranges.get(i);
    }

    return getRange(configFile, defaultRange);
  }

  private static double[] getRange(File configFile, double[] defaultRange) {
    double[] result = defaultRange;

    try {
      if (configFile.exists()) {
        result = getRangeFromFile(configFile);
      } else {
        PrintWriter pw = new PrintWriter(new FileWriter(configFile));
        for (int i = 0; i < result.length; i++) {
          pw.println(df.format(result[i]));
        }
        pw.close();
      }
      return result;
    } catch (FileNotFoundException ex) {
      return defaultRange;
    } catch (IOException ex) {
      return defaultRange;
    }
  }

  private static double[] getRangeFromFile(File rangeFile)
      throws FileNotFoundException, IOException {
    String[] lines = RcpaFileUtils.readFile(rangeFile.getAbsolutePath());
    ArrayList<Double> ranges = new ArrayList<Double>();

    for (int i = 0; i < lines.length; i++) {
      if (lines[i].trim().length() == 0) {
        continue;
      }

      ranges.add(Double.valueOf(lines[i]));
    }

    double[] result = new double[ranges.size()];
    for (int i = 0; i < result.length; i++) {
      result[i] = ranges.get(i);
    }
    return result;
  }
}
