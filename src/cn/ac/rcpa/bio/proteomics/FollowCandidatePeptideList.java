package cn.ac.rcpa.bio.proteomics;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FollowCandidatePeptideList
    extends ArrayList<FollowCandidatePeptide> {
  private static Pattern infoPattern;

  private static Pattern getInfoPattern() {
    if (infoPattern == null) {
      infoPattern = Pattern.compile(
          "(\\S\\.\\S+?\\.\\S)\\((\\d+\\.\\d+),(\\d+\\.\\d+)\\)");
    }
    return infoPattern;
  }

  public FollowCandidatePeptideList() {
  }

  public static FollowCandidatePeptideList valueOf(String info) {
    FollowCandidatePeptideList result = new FollowCandidatePeptideList();
    Matcher matcher = getInfoPattern().matcher(info);
    while (matcher.find()) {
      FollowCandidatePeptide pep = new FollowCandidatePeptide(matcher.group(1),
          Double.parseDouble(matcher.group(2)),
          Double.parseDouble(matcher.group(3)));
      result.add(pep);
    }
    return result;
  }

  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer();
    for(int i = 0;i < size();i++){
      if (i == 0){
        sb.append(get(i).toString());
      }
      else {
        sb.append(" ! " + get(i).toString());
      }
    }
    return sb.toString();
  }

}
