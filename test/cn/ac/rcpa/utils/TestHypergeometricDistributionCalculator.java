package cn.ac.rcpa.utils;

import junit.framework.TestCase;

public class TestHypergeometricDistributionCalculator extends TestCase {
  private HypergeometricDistributionCalculator calc = HypergeometricDistributionCalculator.getInstance();

  private void doTestHypergeometric(double expect, int i, int N, int n, int m, double gap){
    assertEquals(expect, calc.hypergeometric(i, N, n, m), gap);
  }

  private void doTestOverHypergeometric(double expect, int i, int N, int n, int m, double gap){
    assertEquals(expect, calc.pValueOfOverRepresentedByHypergeometric(i, N, n, m), gap);
  }

  private void doTestLessHypergeometric(double expect, int i, int N, int n, int m, double gap){
    assertEquals(expect, calc.pValueOfLessRepresentedByHypergeometric(i, N, n, m), gap);
  }

  public void testHypergeometric() {
    doTestHypergeometric(0.3048, 0, 6, 6, 36, 0.0001);
    doTestHypergeometric(0.4390, 1, 6, 6, 36, 0.0001);
    doTestHypergeometric(0.2110, 2, 6, 6, 36, 0.0001);
    doTestHypergeometric(0.04169, 3, 6, 6, 36, 0.00001);
    doTestHypergeometric(0.003350, 4, 6, 6, 36, 0.000001);
    doTestHypergeometric(9.241E-5, 5, 6, 6, 36, 0.001E-5);
    doTestHypergeometric(5.134E-7, 6, 6, 6, 36, 0.001E-7);
  }

  public void testOverRepresentHypergeometric() {
    double expect = 1.0;
    doTestOverHypergeometric(expect, 0, 6, 6, 36, 0.0001);
    expect -= calc.hypergeometric(0, 6, 6, 36);
    doTestOverHypergeometric(expect, 1, 6, 6, 36, 0.0001);
    expect -= calc.hypergeometric(1, 6, 6, 36);
    doTestOverHypergeometric(expect, 2, 6, 6, 36, 0.0001);
    expect -= calc.hypergeometric(2, 6, 6, 36);
    doTestOverHypergeometric(expect, 3, 6, 6, 36, 0.00001);
    expect -= calc.hypergeometric(3, 6, 6, 36);
    doTestOverHypergeometric(expect, 4, 6, 6, 36, 0.000001);
    expect -= calc.hypergeometric(4, 6, 6, 36);
    doTestOverHypergeometric(expect, 5, 6, 6, 36, 0.001E-5);
    expect -= calc.hypergeometric(5, 6, 6, 36);
    doTestOverHypergeometric(expect, 6, 6, 6, 36, 0.001E-7);

//    System.out.println(calc.hypergeometric(1228, 3144, 5258, 10989));
  }

  public void testLessRepresentHypergeometric() {
    double expect = calc.hypergeometric(0, 6, 6, 36);
    doTestLessHypergeometric(expect, 0, 6, 6, 36, 0.0001);
    expect += calc.hypergeometric(1, 6, 6, 36);
    doTestLessHypergeometric(expect, 1, 6, 6, 36, 0.0001);
    expect += calc.hypergeometric(2, 6, 6, 36);
    doTestLessHypergeometric(expect, 2, 6, 6, 36, 0.0001);
    expect += calc.hypergeometric(3, 6, 6, 36);
    doTestLessHypergeometric(expect, 3, 6, 6, 36, 0.00001);
    expect += calc.hypergeometric(4, 6, 6, 36);
    doTestLessHypergeometric(expect, 4, 6, 6, 36, 0.000001);
    expect += calc.hypergeometric(5, 6, 6, 36);
    doTestLessHypergeometric(expect, 5, 6, 6, 36, 0.001E-5);
    expect += calc.hypergeometric(6, 6, 6, 36);
    doTestLessHypergeometric(expect, 6, 6, 6, 36, 0.001E-7);
  }

}
