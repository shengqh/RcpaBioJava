package bijnum;

import java.util.Random;

/**
 * BIJ statistical methods inherited from MatLab. Syntax is as close as possible to matlab.
 *
 * Copyright (c) 1999-2003, Michael Abramoff. All rights reserved.
 * @author: Michael Abramoff
 *
 * Small print:
 * This source code, and any derived programs ('the software')
 * are the intellectual property of Michael Abramoff.
 * Michael Abramoff asserts his right as the sole owner of the rights
 * to this software.
 * Commercial licensing of the software is available by contacting the author.
 * THE SOFTWARE IS PROVIDED "AS IS" AND WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS, IMPLIED OR OTHERWISE, INCLUDING WITHOUT LIMITATION, ANY
 * WARRANTY OF MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE.
 */

/* Remove some function related to BIJ package by Sheng QuanHu, 2004-08-19 */

public class BIJStats {
  /**
   * Conversion from standard error to confidence intervals at different significance levels.
   * To make a confidence interval (99%, 2.576; 95%, 1.96; 99.9 3.39).
   */
  public static double CI95 = 1.96f; // 95% significance level.
  public static double CI99 = 2.576f; // 99% significance level.
  public static double CI99_9 = 3.39f; // 99.9%significance level.

  public static int n(double[] v) {
    return v.length;
  }

  public static int n(double[] v, double[] mask) {
    int n = 0;
    for (int j = 0; j < v.length; j++) {
      if (mask[j] != 0) {
        n++;
      }
    }
    return n;
  }

  /*
   * avg(2) computes the avg of the elements of vector v only where the mask value is not 0.
   * @param v a double[] vector.
   * @param mask a double[] with a value of ! 0 for all elements of v that are valid.
   * @return the avg of the masked elements in v.
   */
  public static double avg(double[] v, double[] mask) {
    return (double) sum(v, mask) / n(v, mask);
  }

  /**
   * stdev(2) computes the stddev of the elements of only where the mask value is not 0.
   * See Press, Numerical recipes in C, pp 617 for the computation of variance using the two pass algorithm.
   * @param v a double[] vector.
   * @param mask a double[] with a value of ! 0 for all elements of v that are valid.
   * @return the stddev of the masked elements in v.
   */
  public static double stdev(double[] v, double[] mask) {
    double avg = avg(v, mask);
    double var = 0;
    double eps = 0;
    int n = 0;
    for (int j = 0; j < v.length; j++) {
      if (mask[j] != 0) {
        double d = v[j] - avg;
        eps += d;
        var += d * d;
        n++;
      }
    }
    var = (var - eps * eps / n) / (n - 1);
    double stdev = (double) Math.sqrt(var);
    return stdev;
  }

  /**
   * sem(1) computes the standard error of the mean of the elements of v.
   * Standard error is the standard deviation divided by the square root of the number of elements.
   * See Press, Numerical recipes in C, pp 617 for the computation of variance using the two pass algorithm.
   * @param v a double[] vector.
   * @return the standard error of the mean of all elements in v.
   */
  public static double sem(double[] v) {
    double avg = avg(v);
    double var = 0;
    double eps = 0;
    int n = 0;
    for (int j = 0; j < v.length; j++) {
      double d = v[j] - avg;
      eps += d;
      var += d * d;
      n++;
    }
    var = (var - eps * eps / n) / (n - 1);
    double stdev = Math.sqrt(var);
    double sem = (double) (stdev / Math.sqrt(n));
    return sem;
  }

  /**
   * sem(2) computes the standard error of the mean of the elements of v only where the mask value is not 0.
   * Standard error is the standard deviation divided by the square root of the number of elements.
   * See Press, Numerical recipes in C, pp 617 for the computation of variance using the two pass algorithm.
   * @param v a double[] vector.
   * @param mask a double[] with a value of ! 0 for all elements of v that are valid.
   * @return the stderr of the masked elements in v.
   */
  public static double sem(double[] v, double[] mask) {
    double avg = avg(v, mask);
    double var = 0;
    double eps = 0;
    int n = 0;
    for (int j = 0; j < v.length; j++) {
      if (mask[j] != 0) {
        double d = v[j] - avg;
        eps += d;
        var += d * d;
        n++;
      }
    }
    var = (var - eps * eps / n) / (n - 1);
    double stdev = Math.sqrt(var);
    double sem = (double) (stdev / Math.sqrt(n));
    return sem;
  }

  /**
   * Compute the combined standard error of the mean of the two standard errors stderr0 and stderr1 and
   * corresponding averages avg0 and avg1.
   * The method used to calculate the SE of a calculated result involving multiplication and/or division
   * is similar to, but slightly more complicated than that used for addition and subtraction.
   * In this case, the relative standard deviations (or relative standard errors) of the values involved
   * in the calculation must be used rather than the absolute standard errors.
   * The relative SD (or SE) is obtained from the absolute SD (or SE) by dividing the (SE or SD) by the data value itself.
   * Note that these relative quantities are always unitless.
   * @param avg the average of the combination (avg0/avg1)
   * @param avg0, stderr0 average and standard deviation of first component.
   * @param avg1, stderr1 average and standard deviation of second component.
   * @return standard error of the mean for avg.
   */
  public static double sem(double avg, double avg0, double stderr0, double avg1,
                           double stderr1) {
    //double rse0 = stderr0;
    //double rse1 = stderr1;
    ////double rse = Math.sqrt(rse0 * rse0 + rse1 * rse1);
    //return (double) rse;
    double rse0 = stderr0 / avg0;
    double rse1 = stderr1 / avg1;
    double rse = Math.sqrt(rse0 * rse0 + rse1 * rse1);
    // convert to absolute stderr.
    return rse * avg;
  }

  /*
   * avgNoExtremes computes the average of all elements in v, but throws out all extreme values
   * more than nrstddev standard deviations from the average.
   * @param v a double[] vector.
   * @param nrstddev the standard deviation above which values will not be included in average.
   * @return the avg of the non-extreme elements in v.
   * @deprecated
   */
  public static double avgNoExtremes(double[] v, double nrstddev) {
    double avg = avg(v);
    double stddev = stdev(v);
    double cum = 0;
    int n = 0;
    for (int j = 0; j < v.length; j++) {
      double val = v[j];
      if (Math.abs(val - avg) < nrstddev * stddev) {
        cum += (double) val;
        n++;
      }
    }
    cum /= (double) n;
    return cum;
  }

  /*
   * meanT(a) computes the mean values of each column of the <code>transpose</code> of a.
   * It is the same as mean(transpose(a)) but saves a lot of space.
   * @param m a double[][] matrix.
   * @return  a double[] with the mean of all row vectors.
   */
  public static double[] meanColumnT(double[][] m) {
    int iN = m.length;
    int iM = m[0].length;
    double[] v = new double[iM];
    for (int i = 0; i < iM; i++) {
      for (int j = 0; j < iN; j++) {
        v[i] += m[j][i];
      }
    }
    for (int i = 0; i < iM; i++) {
      v[i] /= (double) iN;
    }
    return v;
  }

  /*
   * mean(a) computes the mean values of each column of a.
   * @param m a double[][] matrix.
   * @return a double[] with the mean of all column vectors.
   */
  public static double[] meanColumn(double[][] m) {
    int iN = m.length;
    int iM = m[0].length;
    double[] v = new double[iM];
    for (int j = 0; j < iN; j++) {
      for (int i = 0; i < iM; i++) {
        v[j] += m[j][i];
      }
    }
    for (int j = 0; j < iN; j++) {
      v[j] /= (double) iM;
    }
    return v;
  }

  /*
   * mean(A) computes the means of all the elements of m.
   * @param m a double[][] matrix.
   * @return a double with the mean of all elements.
   */
  public static double mean(double[][] m) {
    int iN = m.length;
    int iM = m[0].length;
    double v = 0;
    for (int j = 0; j < iN; j++) {
      for (int i = 0; i < iM; i++) {
        v += m[j][i];
      }
    }
    for (int j = 0; j < iN; j++) {
      v /= (double) (iM * iN);
    }
    return (double) v;
  }

  /**
   * Compute average of all values in vector v.
   * @param v a vector of double[]
   * @return the average of all elements in v.
   */
  public static double avg(double[] v) {
    return (double) (sum(v) / v.length);
  }

  /**
   * sum(1) computes average of all values in vector v.
   * @param v a vector of double[]
   * @return the average of all elements in v.
   */
  public static double sum(double[] v) {
    double aggr = 0;
    for (int j = 0; j < v.length; j++) {
      aggr += v[j];
    }
    return aggr;
  }

  /**
   * sum(2) computes the sum of the elements of vector v only where the mask value is not 0.
   * @param v a double[] vector.
   * @param mask a double[] with a value of ! 0 for all elements of v that are valid.
   * @return the avg of the masked elements in v.
   */
  public static double sum(double[] v, double[] mask) {
    double agr = 0;
    for (int j = 0; j < v.length; j++) {
      if (mask[j] != 0) {
        agr += v[j];
      }
    }
    return agr;
  }

  /**
   * Compute variance of all elements in vector v.
   * @param v a vector of double[]
   */
  public static double var(double[] v) {
    double avg = avg(v);
    double var = 0;
    double eps = 0;
    int n = 0;
    for (int j = 0; j < v.length; j++) {
      double d = v[j] - avg;
      eps += d;
      var += d * d;
      n++;
    }
    var = (var - eps * eps / n) / (n - 1);
    double stdev = (double) Math.sqrt(var);
    return stdev;
  }

  /**
   * Make a new vector of v that has zero mean and unit variance.
   * @param v a double[] vector
   * @return a vector with proprtionally the same elements as v but with zero mean and unit variance.
   */
  public static double[] unitvar(double[] v) {
    double[] n = zeromean(v);
    // Make unit variance.
    double var = var(n);
    for (int j = 0; j < v.length; j++) {
      n[j] /= Math.sqrt(var);
    }
    return n;
  }

  /**
   * Zero the mean of all elements of v.
   * @param v a vector.
   * @return a vector with zero mean and same variance as v.
   */
  public static double[] zeromean(double[] v) {
    double[] n = new double[v.length];
    double avg = sum(v) / v.length;
    // Make zero mean.
    for (int j = 0; j < v.length; j++) {
      n[j] = (v[j] - avg);
    }
    return n;
  }

  /**
   * Compute stdev (SQRT(var)) of all values in vector v.
   * @param v a vector of double[]
   */
  public static double stdev(double[] v) {
    double stdev = (double) Math.sqrt(var(v));
    return stdev;
  }

  public static double thresholdFraction(double[] v, double fraction) {
    return thresholdFraction(v, (double) fraction);
  }

  /**
   * Compute a histogram with n bins for the vector v, each bin separated by d.
   * @param v a double[] vector
   * @param min the lowest value for the first bin.
   * @param d the difference in value between each bin
   * @param n the number of bins.
   * @return a double[] with the number of occurences for each bin value.
   */
  public static int[] histogram(double[] v, double min, double d, int n) {
    int[] Pv = new int[n];
    for (int i = 0; i < v.length; i++) {
      // Scale the pixel value and compute bin index.
      int ix = (int)Math.round( (v[i] - min) * d);
      if (ix >= 0 && ix < Pv.length) {
        Pv[ix]++;
      }
    }
    return Pv;
  }

  /**
   * Compute the lowest bin into which the highest p percent of occurrences falls.
   * Used to compute things as "give me the occurence above which 10% of the histogram falls."
   * @param histogram an int[] with the ouccrence counts for each bin
   * @param p the fraction of histogram values desired.
   * @return an int, the index into the lowest bin that still falls among p.
   */
  public static int binIndex(int[] histogram, double fraction) {
    // First compute total number of occurences in histogram.
    int total = 0;
    for (int i = 0; i < histogram.length; i++) {
      total += histogram[i];
    }
    int aggr = 0;
    int bin = histogram.length - 1;
    while ( (fraction * total) - (double) aggr >= 0) {
      //IJ.write("total "+total+" fraction="+(fraction*total)+" aggr="+aggr);
      aggr += histogram[bin--];
    }
    return bin;
  }

  /**
   * Compute the correlation of a vector with another vector b.
   * The mean of a and b is subtracted before further processing.
   * a and b therefore do not have to be zero-mean.
   * @param a a double[] vector.
   * @param b a double[] vector of same length.
   * @return r, the correlation coefficient.
   */
  public static double correl(double[] a, double[] b) {
    int iN = Math.min(a.length, b.length);
    double avga = avg(a);
    double avgb = avg(b);
    double agr = 0;
    double vara = 0;
    double varb = 0;
    for (int i = 0; i < iN; i++) {
      agr += (a[i] - avga) * (b[i] - avgb);
      vara += Math.pow( (a[i] - avga), 2);
      varb += Math.pow( (b[i] - avgb), 2);
    }
    double r;
    if (vara == 0 || varb == 0) {
      r = 0;
    }
    else {
      r = (agr / Math.sqrt(vara * varb));
    }
    if (Double.isNaN(r)) {
      System.out.println("NAN->" + agr + " " + vara + " " + varb);
    }
    return (double) r;
  }

  /**
   * Compute the erf of x.
   * @param x the argument
   * @return the erf of x.
   */
  public static double erf(double x) {
    double a1 = .0705230784;
    double a2 = .0422820123;
    double a3 = .0092705272;
    double a4 = .0001520143;
    double a5 = .0002765672;
    double a6 = .0000430638;
    double xs, xc;

    xs = x * x;
    xc = xs * x;
    return (1.0 -
            1.0 /
            Math.pow(1 + a1 * x + a2 * xs + a3 * xc + a4 * xs * xs +
                     a5 * xc * xs + a6 * xc * xc, 16));
  }

  /**
   * Compute Mean Square Error (or residual) of vectors a and b.
   * a and b are normalized to zero mean and unit variance.
   * @param a a double[] vector
   * @param b a double[] vector.
   * @return a double that is the Mean Square Error of a and b.
   */
  public static double mse(double[] a, double[] b) throws IllegalArgumentException {
    if (a.length != b.length) {
      throw new IllegalArgumentException("mse: vectors do not match");
    }
    double[] auv = unitvar(a);
    double[] buv = unitvar(b);
    // Mean is zero, variance is 1.
    int iN = a.length;
    //IJ.write("mse: avg="+avg(auv)+", "+avg(buv)+" var="+var(auv)+", "+var(buv));
    double agr = 0;
    for (int i = 0; i < iN; i++) {
      agr += Math.pow(auv[i] - buv[i], 2);
    }
    double mse = agr / iN;
    return mse;
  }

  /**
   * Compute Root Mean Square Error of vectors a and b.
   * a and b are normalized to zero mean and unit variance.
   * @param a a double[] vector
   * @param b a double[] vector.
   * @return a double that is the Root Mean Square Error of a and b.
   */
  public static double rmse(double[] a, double[] b) throws
      IllegalArgumentException {
    return (double) Math.sqrt(mse(a, b));
  }

  /**
   * Randomly sample a fraction of the elements of vector v.
   * @param v a vector
   * @param fraction the fraction of elements from v to be included.
   * @return a double[] vector with all the sampled elements.
   */
  public static double[] randomFraction(double[] v, double fraction) {
    // Take at least one sample.
    int length = 1 + (int) Math.round(v.length * fraction);
    double[] r = new double[length];
    Random random = new Random();
    for (int i = 0; i < r.length; i++) {
      r[i] = v[ (int) (v.length * random.nextDouble())];
    }
    return r;
  }

  /**
   * Compute the sensitivities of a test of which the result is in exp and the ground truth in truth,
   * for all classes n that occur in truth.
   * @param exp  an int[] vector of test results, where 0 <= exp[n] < n.
   * @param truth an int[] vector of ground truth, where 0 <= truth[n] < n.
   * @param n the number of classes to determine the sensitivity for.
   * @return a double[] vector with the sensitivities for all classes in truth.
   */
  public static double[] sensitivities(double[] exp, double[] truth, int n) throws
      IllegalArgumentException {
    double[] s = new double[n - 1];
    for (int c = 0; c < s.length; c++) {
      s[c] = sensitivity(exp, truth, c);
    }
    return s;
  }

  /**
   * Compute the specificities of a test of which the result is in exp and the ground truth in truth,
   * for all classes n that occur in truth.
   * @param exp  an int[] vector of test results, where 0 <= exp[n] < n.
   * @param truth an int[] vector of ground truth, where 0 <= truth[n] < n.
   * @param n the number of classes to determine the sensitivity for.
   * @return a double[] vector with the specificities for all classes in truth.
   */
  public static double[] specificities(double[] exp, double[] truth, int n) throws
      IllegalArgumentException {
    double[] s = new double[n - 1];
    for (int c = 0; c < s.length; c++) {
      s[c] = specificity(exp, truth, c);
    }
    return s;
  }

  /**
   * Compute the accuracy of a test of which the result is in exp and the ground truth in truth,
   * where test results in multiple classifications.
   * @param exp  an int[] vector of test results, where 0 <= exp[n] < n.
   * @param truth an int[] vector of ground truth, where 0 <= truth[n] < n.
   * @param n the number of classes to determine the sensitivity for.
   * @return the combined accuracy.
   */
  public static double accuracyMultipleClasses(double[] exp, double[] truth, int n) throws
      IllegalArgumentException {
    if (exp.length != truth.length) {
      throw new IllegalArgumentException("accuracy: " + exp.length + "!=" +
                                         truth.length);
    }
    int nom = 0;
    int denom = 0;
    for (int i = 0; i < exp.length; i++) {
      denom++;
      if (truth[i] == exp[i]) {
        nom++;
      }
    }
    //System.out.println("accuracy = "+nom+"/"+denom);
    /*int [] votesT = new int[n];
                     int [] votesE = new int[n];
                     int [] votesC = new int[n];
                     for (int i = 0; i < exp.length; i++)
                     {
            votesE[(int) exp[i]]++;
            votesT[(int) truth[i]]++;
            votesC[(int) truth[i]] += (truth[i] == exp[i]) ? 1 : 0;
                     }
                     for (int c = 0; c < n; c++)
                     {
            System.out.print(""+c+": "+votesT[c]+"/"+votesE[c]+" (correct="+votesC[c]+"); ");
                     }
                     System.out.println();*/
    return (double) nom / (double) denom;
  }

  /**
   * Compute sensitivity of a test of which the result is in exp and the ground truth in truth,
   * for class c. Only those elements in truth will be considered
   * sensitivity = true pos / (true pos + false neg)
   * @param exp  an int[] vector of test results, where 0 <= exp[n] <= c.
   * @param truth an int[] vector of ground truth, where 0 <= truth[n] <= c.
   * @param c the class to determine sensitivity for.
   * @return the sensitivity.
   */
  public static double sensitivity(double[] exp, double[] truth, int c) throws
      IllegalArgumentException {
    if (exp.length != truth.length) {
      throw new IllegalArgumentException("sensitivity");
    }
    int nom = 0;
    int denom = 0;
    for (int i = 0; i < exp.length; i++) {
      if (truth[i] >= 0 && truth[i] == c) {
        denom++;
        if (exp[i] == c) {
          nom++;
        }
      }
    }
    return (double) nom / (double) denom;
  }

  /**
   * Compute specificity of a test of which the result is in exp and the ground truth in truth,
   * for class c.
   * specificity = true neg / (true neg + false pos)
   * @param exp  an int[] vector of test results, where 0 <= exp[n] <= c.
   * @param truth an int[] vector of ground truth, where 0 <= truth[n] <= c.
   * @param c the class to determine specificity for.
   * @return the specificity.
   */
  public static double specificity(double[] exp, double[] truth, int c) throws
      IllegalArgumentException {
    if (exp.length != truth.length) {
      throw new IllegalArgumentException("specificity");
    }
    int nom = 0;
    int denom = 0;
    for (int i = 0; i < exp.length; i++) {
      if (truth[i] != c) {
        denom++;
        if (exp[i] != c) {
          nom++;
        }
      }
    }
    return (double) nom / (double) denom;
  }

  /**
   * Compute accuracy of a test for which the result is in exp and the ground truth in truth,
   * for class c.
   * accuracy = true pos + true neg / (true neg + false pos + true pos + false neg)
   * @param exp  an int[] vector of test results, where 0 <= exp[n] <= c.
   * @param truth an int[] vector of ground truth, where 0 <= truth[n] <= c.
   * @param c the class to determine accuracy for.
   * @return the accuracy.
   */
  public static double accuracy(double[] exp, double[] truth, int c) throws
      IllegalArgumentException {
    if (exp.length != truth.length) {
      throw new IllegalArgumentException("accuracy: " + exp.length + "!=" +
                                         truth.length);
    }
    int nom = 0;
    int denom = 0;
    for (int i = 0; i < exp.length; i++) {
      denom++;
      if (truth[i] == c) {
        if (exp[i] == c) {
          nom++;
        }
      }
      else if (truth[i] != c) {
        if (exp[i] != c) {
          nom++;
        }
      }
    }
    //System.out.println("accuracy = "+nom+"/"+denom);
    return (double) nom / (double) denom;
  }
}
