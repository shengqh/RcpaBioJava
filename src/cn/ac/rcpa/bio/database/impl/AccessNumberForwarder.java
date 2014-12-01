package cn.ac.rcpa.bio.database.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.ac.rcpa.bio.database.IAccessNumberParser;

public class AccessNumberForwarder
    implements IAccessNumberParser {
  private static AccessNumberForwarder instance;

  public static AccessNumberForwarder getInstance() {
    if (instance == null) {
      instance = new AccessNumberForwarder();
    }
    return instance;
  }

  private AccessNumberForwarder() {
  }

  private Pattern pattern;
  private Pattern getPattern() {
    if (pattern == null) {
      pattern = Pattern.compile("(.+?)\\s");
    }
    return pattern;
  }


  public String getValue(String name) {
    final Matcher matcher = getPattern().matcher(name);
    if (matcher.find()) {
      return matcher.group(1);
    }

    return name;
  }

  public String getTitle() {
    return "AccessNumber";
  }

  public String getDescription() {
    return "Access Number Parser";
  }
}
