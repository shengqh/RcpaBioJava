package cn.ac.rcpa.bio.database.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.ac.rcpa.bio.database.IAccessNumberParser;

public class IPIAccessNumberParser
    implements IAccessNumberParser {
  private static IPIAccessNumberParser instance;

  public static IPIAccessNumberParser getInstance() {
    if (instance == null) {
      instance = new IPIAccessNumberParser();
    }
    return instance;
  }

  private IPIAccessNumberParser() {
  }

  private Pattern pattern;
  private Pattern getPattern() {
    if (pattern == null) {
      pattern = Pattern.compile("IPI:(IPI\\d+).?");
    }
    return pattern;
  }

  public String getValue(String name) {
    final Matcher matcher = getPattern().matcher(name);
    if (matcher.find()) {
      return matcher.group(1);
    }
    throw new RuntimeException(name + " is not a valid IPI name!");
  }
  
  public String getTitle() {
    return "IPIAccessNumber";
  }

  public String getDescription() {
    return "IPI Access Number Parser";
  }
  
}
