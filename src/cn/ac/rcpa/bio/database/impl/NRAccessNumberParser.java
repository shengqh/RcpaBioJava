package cn.ac.rcpa.bio.database.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.ac.rcpa.bio.database.IAccessNumberParser;

public class NRAccessNumberParser
    implements IAccessNumberParser {
  private static NRAccessNumberParser instance;

  public static NRAccessNumberParser getInstance() {
    if (instance == null) {
      instance = new NRAccessNumberParser();
    }
    return instance;
  }

  private NRAccessNumberParser() {
  }

  private Pattern pattern;
  private Pattern getPattern() {
    if (pattern == null) {
      pattern = Pattern.compile("(gi\\|\\d+)\\|");
    }
    return pattern;
  }

  public String getValue(String name) {
    final Matcher matcher = getPattern().matcher(name);
    if (matcher.find()) {
      return matcher.group(1);
    }
    throw new RuntimeException(name + " is not a valid NR name!");
  }

  public String getTitle() {
    return "NRAccessNumber";
  }
  public String getDescription() {
    return "NR Access Number Parser";
  }
  
}
