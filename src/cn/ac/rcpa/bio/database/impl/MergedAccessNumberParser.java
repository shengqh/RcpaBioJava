package cn.ac.rcpa.bio.database.impl;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;

import cn.ac.rcpa.bio.database.IAccessNumberParser;

public class MergedAccessNumberParser implements IAccessNumberParser{
  private IAccessNumberParser parser;
  private String token;

  public MergedAccessNumberParser(IAccessNumberParser parser, String token) {
    this.parser = parser;
    this.token = token;
  }

  public String getValue(String name) {
    String[] names = name.split(token);
    ArrayList<String> acs = new ArrayList<String>();
    for(String ac:names){
      acs.add(parser.getValue(ac.trim()));
    }
    return StringUtils.join(acs.iterator(), token);
  }
  
  public String getTitle() {
    return parser.getTitle();
  }

  public String getDescription() {
    return parser.getDescription();
  }
}
