package cn.ac.rcpa.bio.database.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.ac.rcpa.bio.database.IAccessNumberParser;

public class SwissProtIdentityParser
    implements IAccessNumberParser {
  private static SwissProtIdentityParser instance;

  public static SwissProtIdentityParser getInstance(){
    if (instance == null){
      instance = new SwissProtIdentityParser();
    }
    return instance;
  }

  private SwissProtIdentityParser(){
  }

  private Pattern pattern;
  private Pattern getPattern() {
    if (pattern == null) {
      pattern = Pattern.compile("\\((\\w+)\\)");
    }
    return pattern;
  }
  /**
   * getAccessNumber
   *
   * @param reference String
   * @return String
   */
  public String getValue(String reference) {
    final Matcher matcher = getPattern().matcher(reference);
    if (matcher.find()) {
      return matcher.group(1);
    }
    throw new RuntimeException(reference + " is not a valid SwissProt reference!");
  }

  public String getTitle() {
    return "SPIdentity";
  }
  
  public String getDescription() {
    return "SwissProt Identity Parser";
  }
  
  
}
