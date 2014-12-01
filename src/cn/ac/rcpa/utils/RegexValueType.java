package cn.ac.rcpa.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexValueType {
  private RegexValueType() {
  }

  public static final String ANY = "\\.*";
  public static final String BLANK = "\\s*";

  public static final String INT = "[+-]?\\s*\\d+";
  public static final String DOUBLE = "[+-]?\\s*\\d+\\.?\\d*";
  public static final String UNBLANK = "\\S+";

  public static final String GET_INT = BLANK + "(" + INT + ")";
  public static final String GET_DOUBLE = BLANK + "(" + DOUBLE + ")";
  public static final String GET_UNBLANK = BLANK + "(" + UNBLANK + ")";

  public static void main(String[] args) {
    String sLine = "  1.   1 / 19      15861 1786.8390  0.0000  0.8720   114.7  10/64  gi|5541865|emb|CAB51072.1|       +3   -.EARGNSSETSHSVPEAK.-";
    String sLine2 = "  2.   2 / 12      16808 1788.8621  0.0587  0.8209   124.2   9/56  gi|21757312|dbj|BAC05086.1|           -.GNYDEGFGHKQHKDR.-";

    String sPattern = RegexValueType.ANY
        + RegexValueType.GET_INT
        + RegexValueType.BLANK
        + "/"
        + RegexValueType.GET_INT
        + RegexValueType.GET_INT
        + RegexValueType.GET_DOUBLE
        + RegexValueType.GET_DOUBLE
        + RegexValueType.GET_DOUBLE
        + RegexValueType.GET_DOUBLE
        + RegexValueType.GET_INT
        + RegexValueType.BLANK
        + "/"
        + RegexValueType.GET_INT
        + RegexValueType.GET_UNBLANK
        + RegexValueType.BLANK
        + "\\+?(\\d*)?"
        + RegexValueType.GET_UNBLANK;

    System.out.println(sPattern);
    Pattern pattern = Pattern.compile(sPattern);
    Matcher matcher = pattern.matcher(sLine);
    if (matcher.find()) {
      for (int i = 0; i <= matcher.groupCount(); i++) {
        String sGroup = matcher.group(i);
        System.out.println("Group " + i + " " + sGroup);
      }
    }
    matcher = pattern.matcher(sLine2);
    if (matcher.find()) {
      for (int i = 0; i <= matcher.groupCount(); i++) {
        String sGroup = matcher.group(i);
        System.out.println("Group " + i + " " + sGroup);
      }
    }
  }
}
