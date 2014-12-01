/*
 * Created on 2005-1-18
 *
 *                    Rcpa development code
 *
 * Author sqh
 * This code is developed by RCPA Bioinformatic Platform
 * http://www.proteomics.ac.cn/
 *
 */
package cn.ac.rcpa.bio.utils;

import org.biojava.bio.proteomics.Protease;

/**
 * @author sqh
 * 
 */
public class MissedCleavagesCalculator {
  private String cleaveageResidues;

  private String notCleaveResidues;

  private boolean isEndoProtease;

  public MissedCleavagesCalculator(Protease protease) {
    super();

    cleaveageResidues = protease.getCleaveageResidues().seqString();
    notCleaveResidues = protease.getNotCleaveResidues().seqString();
    isEndoProtease = protease.isEndoProtease();
  }

  public int getCount(String pureSequence) {
    int result = 0;

    for (int j = 0; j < pureSequence.length(); j++) {
      char aa = pureSequence.charAt(j);

      if (cleaveageResidues.indexOf(aa) != -1) {
        if (isEndoProtease) {
          boolean cleave = false;
          if (j < pureSequence.length() - 1){
            cleave = true;
            char nextAA = pureSequence.charAt(j+1);
            if (notCleaveResidues.indexOf(nextAA) != -1) {
              cleave = false;
            }
          }

          if (cleave) {
            result++;
          }
        } else {
          boolean cleave = false;
          if (j > 0) {
            cleave = true;
            char priorAA = pureSequence.charAt(j-1);
            if (notCleaveResidues.indexOf(priorAA) != -1) {
              cleave = false;
            }
          }

          if (cleave) {
            result++;
          }
        }
      }
    }
    
    return result;
  }

}
