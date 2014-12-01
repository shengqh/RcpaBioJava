package cn.ac.rcpa.bio.database.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.ac.rcpa.bio.database.IAccessNumberParser;

public class SwissProtAccessNumberParser implements IAccessNumberParser {
  private static SwissProtAccessNumberParser instance;

  public static SwissProtAccessNumberParser getInstance(){
    if (instance == null){
      instance = new SwissProtAccessNumberParser();
    }
    return instance;
  }

  private SwissProtAccessNumberParser(){
  }

  private Pattern pattern;
  private Pattern getPattern() {
    if (pattern == null) {
      pattern = Pattern.compile("^>{0,1}(\\S+)");
    }
    return pattern;
  }
  /**
   * getAccessNumber
   *
   * @param name String
   * @return String
   */
  public String getValue(String name) {
    final Matcher matcher = getPattern().matcher(name);
    if (matcher.find()) {
      return matcher.group(1);
    }
    throw new RuntimeException(name + " is not a valid SwissProt name!");
  }

  public String getTitle() {
    return "SPAccessNumber";
  }
  
  public String getDescription() {
    return "SwissProt Access Number Parser";
  }
  

}
