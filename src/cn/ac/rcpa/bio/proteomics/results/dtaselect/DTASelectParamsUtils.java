package cn.ac.rcpa.bio.proteomics.results.dtaselect;

import cn.ac.rcpa.bio.proteomics.results.dtaselect.types.PurgeDuplicatePeptideType;

public class DTASelectParamsUtils {
  private DTASelectParamsUtils() {
  }

  public static String getParamString(DTASelectParams params){
    StringBuffer sb = new StringBuffer();
    sb.append(" -1 " + params.getXcorrCharge1() +
             " -2 " + params.getXcorrCharge2() +
             " -3 " + params.getXcorrCharge3() +
             " -d " + params.getDeltacn());

    if (params.getMaxSpRank() != 1000) {
     sb.append(" -s " + params.getMaxSpRank());
    }

    if (params.getMinSpScore() > 0) {
     sb.append(" -S " + params.getMinSpScore());
    }

    if (params.getMinPeptideCount() != 10) {
      sb.append(" -r " + params.getMinPeptideCount());
    }

    if (params.getMinUniquePeptideCount() != 2) {
      sb.append(" -p " + params.getMinUniquePeptideCount());
    }

    if (params.getPurgeDuplicatePeptide() == PurgeDuplicatePeptideType.FALSE){
      sb.append(" -t 0");
    }
    else if(params.getPurgeDuplicatePeptide() == PurgeDuplicatePeptideType.INTENSITY) {
      sb.append(" -t 1");
    }

    if( params.getRemoveSubsetProteins()){
      sb.append(" -o");
    }

    return sb.toString();
  }
}
