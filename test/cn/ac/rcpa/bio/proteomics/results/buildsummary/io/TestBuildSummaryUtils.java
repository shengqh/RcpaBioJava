/*
 * Created on 2005-11-7
 *
 *                    Rcpa development code
 *
 * Author Sheng QuanHu(qhsheng@sibs.ac.cn / shengqh@gmail.com)
 * This code is developed by RCPA Bioinformatic Platform
 * http://www.proteomics.ac.cn/
 *
 */
package cn.ac.rcpa.bio.proteomics.results.buildsummary.io;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.TestCase;

public class TestBuildSummaryUtils extends TestCase {
  private void checkValue(Pattern pattern, String[] expect, String line) {
    Matcher matcher = pattern.matcher(line);
    assertTrue(matcher.find());
    for (int i = 1; i <= expect.length; i++) {
      assertEquals(expect[i - 1], matcher.group(i));
    }
  }

  public void testGetExperimentalPattern() {
    checkValue(BuildSummaryUtils.getExperimentalPattern(),
        new String[] { "SAX_35" },
        "\tSAX_35,5321 - 5324\tK.AGFAGDDAPR.A\t977.013");
    checkValue(BuildSummaryUtils.getExperimentalPattern(),
            new String[] { "SAX_35" },
            "\t\"SAX_35,5321 - 5324\"\tK.AGFAGDDAPR.A\t977.013");
    checkValue(BuildSummaryUtils.getExperimentalPattern(),
        new String[] { "SAX_35" },
        "\tSAX_35,10927\tR.GVVDSEDIPLNLSR.E ! R.GVVDSEDLPLNISR.E\t1514.66");
    checkValue(BuildSummaryUtils.getExperimentalPattern(),
        new String[] { "SAX_35" }, "\tSAX_35,7039\tR.GYSFTTTAER.E\t1133.19");
    checkValue(
        BuildSummaryUtils.getExperimentalPattern(),
        new String[] { "JWH_SAX_35_050906" },
        "\tJWH_SAX_35_050906,18762\t-.M#ITIPLT*NEVVSMLSQNK.S\t2115.38\t-0.14979\t2\t1\t3.0568\t0.1351\t401.1\t2\t18|51\tIPI:IPI00110296.1|TREMBL:Q9EPS6|REFSEQ_NP:NP_109662|ENSEMBL:ENSMUSP00000079674\t5.75\t-.M#ITIPLTNEVVS*MLSQNK.S(2.7855,0.0888)");
    checkValue(
        BuildSummaryUtils.getExperimentalPattern(),
        new String[] { "JWH_SAX_35_050906" },
        "\tJWH_SAX_35_050906,18762\t-.M#ITIPLT*NEVVSMLSQNK.S\t2115.38\t-0.14979\t2\t1\t3.0568\t0.1351\t401.1\t2\t18|51\tIPI:IPI00110296.1|TREMBL:Q9EPS6|REFSEQ_NP:NP_109662|ENSEMBL:ENSMUSP00000079674\t5.75");
  }

  public void testGetExperimentalPeptidePattern() {
    checkValue(BuildSummaryUtils.getExperimentalPeptidePattern(), new String[] {
        "SAX_35", "K.AGFAGDDAPR.A" },
        "\tSAX_35,5321 - 5324\tK.AGFAGDDAPR.A\t977.013");
    checkValue(BuildSummaryUtils.getExperimentalPeptidePattern(), new String[] {
        "SAX_35", "K.AGFAGDDAPR.A" },
        "\t\"SAX_35,5321 - 5324\"\tK.AGFAGDDAPR.A\t977.013");
    checkValue(BuildSummaryUtils.getExperimentalPeptidePattern(), new String[] {
        "SAX_35", "R.GVVDSEDIPLNLSR.E ! R.GVVDSEDLPLNISR.E" },
        "\tSAX_35,10927\tR.GVVDSEDIPLNLSR.E ! R.GVVDSEDLPLNISR.E\t1514.66");
    checkValue(BuildSummaryUtils.getExperimentalPeptidePattern(), new String[] {
        "SAX_35", "R.GYSFTTTAER.E" }, "\tSAX_35,7039\tR.GYSFTTTAER.E\t1133.19");
    checkValue(
        BuildSummaryUtils.getExperimentalPeptidePattern(),
        new String[] { "JWH_SAX_35_050906", "-.M#ITIPLT*NEVVSMLSQNK.S" },
        "\tJWH_SAX_35_050906,18762\t-.M#ITIPLT*NEVVSMLSQNK.S\t2115.38\t-0.14979\t2\t1\t3.0568\t0.1351\t401.1\t2\t18|51\tIPI:IPI00110296.1|TREMBL:Q9EPS6|REFSEQ_NP:NP_109662|ENSEMBL:ENSMUSP00000079674\t5.75\t-.M#ITIPLTNEVVS*MLSQNK.S(2.7855,0.0888)");
    checkValue(
        BuildSummaryUtils.getExperimentalPeptidePattern(),
        new String[] { "JWH_SAX_35_050906" , "-.M#ITIPLT*NEVVSMLSQNK.S"},
        "\tJWH_SAX_35_050906,18762\t-.M#ITIPLT*NEVVSMLSQNK.S\t2115.38\t-0.14979\t2\t1\t3.0568\t0.1351\t401.1\t2\t18|51\tIPI:IPI00110296.1|TREMBL:Q9EPS6|REFSEQ_NP:NP_109662|ENSEMBL:ENSMUSP00000079674\t5.75");
  }
}
