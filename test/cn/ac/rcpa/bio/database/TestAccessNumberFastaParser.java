package cn.ac.rcpa.bio.database;

import java.util.Arrays;

import junit.framework.TestCase;

/**
 * <p>Title: RCPA Package</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: RCPA.SIBS.AC.CN</p>
 * @author Sheng Quan-Hu (shengqh@gmail.com)
 * @version 1.0
 */

public class TestAccessNumberFastaParser extends TestCase {
  public void testGetNames() throws Exception {
    String[] expectedReturn = {
      "IPI00022434",
      "IPI00022463",
      "IPI00385332"
    };
    String[] actualReturn = AccessNumberFastaParser.getNames("data/TestAccessNumberFastaParser.fasta", SequenceDatabaseType.IPI);

    assertTrue(Arrays.equals(expectedReturn, actualReturn));
  }

}
