package cn.ac.rcpa.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***********************************************************************
 *
 * File        : Distributions.cxx
 * Author      : Ihab A.B. Awad
 * Date Begun  : October 08 2004
 *
 * $Id: HypergeometricDistributionCalculator.java,v 1.1 2006/06/30 14:03:28 sheng Exp $
 *
 * License information (the MIT license)
 *
 * Copyright (c) 2004 Ihab A.B. Awad; Stanford University

 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT.  IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 *
 **********************************************************************/

public class HypergeometricDistributionCalculator {
  private int maxPopulationSize = 10000;

  private List<Double> logFactorialCache;

  private Map<Pair<Integer, Integer>,
      Double> logNCrCache = new HashMap<Pair<Integer, Integer>, Double> ();

  private static HypergeometricDistributionCalculator instance;

  public static HypergeometricDistributionCalculator getInstance() {
    if (instance == null) {
      instance = new HypergeometricDistributionCalculator();
    }
    return instance;
  }

  private HypergeometricDistributionCalculator() {
    buildLogFactorialCache();
  }

  /*********************************************************************
   * Populate the cache of log(n!) values, _logNFactorialCache.
   * The maximum factorial that will ever have to be calculated is for
   * _maxPopulationSize, so we wil cache that many.
   * Since :
   * n! = n * (n-1) * (n-2) ... * 1
   * Then :
   * log(n!) = log(n * (n-1) * (n-2) ... * 1)
   *         = log(n) + log(n-1) + log(n-2) ... + log(1)
   */
  private void buildLogFactorialCache() {
    logFactorialCache = new ArrayList<Double> (maxPopulationSize + 1);

    logFactorialCache.add(0.0);
    logFactorialCache.add(0.0);

    for (int i = 2; i < maxPopulationSize + 1; i++) {
      logFactorialCache.add(logFactorialCache.get(i - 1) + Math.log(i));
    }
  }

  /*********************************************************************
   * Return the value of log(n!) from the cache.
   * If n > maxPopulationSize, rebuild logFactorialCache
   *
   * @param n int
   * @return double
   */
  public double logFactorial(int n) {
    if (n < 0){
      throw new IllegalArgumentException("Error argument : " + n + ", argument of logFactorial should not be negative");
    }

    if (n > maxPopulationSize) {
      maxPopulationSize = n * 2;
      buildLogFactorialCache();
    }

    return logFactorialCache.get(n);
  }

  /*********************************************************************
   *
   * Compute and return the value of log(n choose r). This method is
   * used by logNCr(int, int) to populate the cache if the specified
   * pair of (n, r) has not already been seen.
   *
   */
  private double computeLogNCr(int n, int r) {
    return logFactorial(n) - (logFactorial(r) + logFactorial(n - r));
  }

  /*********************************************************************
   *
   * This method returns the log of n choose r.  This means that it
   * can do the calculation in log space itself.
   *
   *             n!
   *   nCr =  ---------
   *          r! (n-r)!
   *
   * which means:
   *
   *   log(nCr) = log(n!) - (log(r!) + log((n-r)!))
   *
   * If the value of N as supplied here is greater than the
   * constructor parameter maxPopulationSize, the behavior of this
   * method is undefined.
   *
   */
  public double logNCr(int n, int r) {
    if (n < 0 || r < 0 || n < r){
      throw new IllegalArgumentException("Error argument : n=" + n + ",r=" + r+", argument of logNCr should be : n >= r >= 0");
    }
    Pair<Integer, Integer> key = new Pair<Integer, Integer> (n, r);
    if (logNCrCache.containsKey(key)) {
      return logNCrCache.get(key);
    }
    double value = computeLogNCr(n, r);
    logNCrCache.put(key, value);
    return value;
  }

  /*********************************************************************
   *
   * This method returns the hypergeometric probability value for
   * sampling without replacement.  The calculation is the probability
   * of picking x positives from a sample of n, given that there are M
   * positives in a population of N.
   *
   * The value is calculated as:
   *
   *         (M choose x) (N-M choose n-x)
   *   P =   -----------------------------
   *                  N choose n
   *
   * where generically n choose r is number of permutations by which r
   * things can be chosen from a population of n (see logNCr(int, int))
   *
   * However, given that these n choose r values may be extremely high
   * (as they are are calculated using factorials) it is safer to do
   * this instead in log space, as we are far less likely to have an
   * overflow.
   *
   * thus :
   *
   *   log(P) = log(M choose x) + log(N-M choose n-x) - log (N choose n);
   *
   * this means we can now calculate log(n choose r) for our
   * hypergeometric calculation.
   *
   * If the value of N as supplied here is greater than the
   * constructor parameter maxPopulationSize, the behavior of this
   * method is undefined.
   *
   */
  public double hypergeometric(int x,
                               int n,
                               int M,
                               int N) {
    if (x < 0 || n < 0 || M < 0 || N < 0 || n < x || N < M || M < x || N < n){
      throw new IllegalArgumentException("Error argument : x=" + x + ",n=" + n + ",M=" + M + ",N=" + N + ", argument of hypergeometric should be : n >= x >= 0, N >= M >= 0, N >= n, M >= x ");
    }
    double z =
        logNCr(M, x) +
        logNCr(N - M, n - x) -
        logNCr(N, n);
    return Math.exp(z);
  }

  /*********************************************************************
   *
   * This method calculates the pvalue of of observing x or more
   * positives from a sample of n, given that there are M positives in
   * a population of N.
   *
   * If the value of N as supplied here is greater than the
   * constructor parameter maxPopulationSize, the behavior of this
   * method is undefined.
   *
   */
  public double pValueOfOverRepresentedByHypergeometric(int x,
      int n,
      int M,
      int N) {
    if (x < 0 || n < 0 || M < 0 || N < 0 || n < x || N < M || M < x || N < n){
      throw new IllegalArgumentException("Error argument : x=" + x + ",n=" + n + ",M=" + M + ",N=" + N + ", argument of pValueOfOverRepresentedByHypergeometric should be : n >= x >= 0, N >= M >= 0, N >= n, M >= x ");
    }

    int min = (M < n) ? M : n;

    double result = 0;

    for (int i = x; i <= min; i++) {
      result += hypergeometric(i, n, M, N);
    }

    return result;
  }

  /*********************************************************************
   *
   * This method calculates the pvalue of of observing x or more
   * positives from a sample of n, given that there are M positives in
   * a population of N.
   *
   * If the value of N as supplied here is greater than the
   * constructor parameter maxPopulationSize, the behavior of this
   * method is undefined.
   *
   */
  public double pValueOfLessRepresentedByHypergeometric(int x,
      int n,
      int M,
      int N) {
    if (x < 0 || n < 0 || M < 0 || N < 0 || n < x || N < M || M < x || N < n){
      throw new IllegalArgumentException("Error argument : x=" + x + ",n=" + n + ",M=" + M + ",N=" + N + ", argument of pValueOfLessRepresentedByHypergeometric should be : n >= x >= 0, N >= M >= 0, N >= n, M >= x ");
    }

    int max = (0 > n - (N - M)) ? 0 : n - (N - M);

    double result = 0;

    for (int i = max; i <= x; i++) {
      result += hypergeometric(i, n, M, N);
    }

    return result;
  }

}
