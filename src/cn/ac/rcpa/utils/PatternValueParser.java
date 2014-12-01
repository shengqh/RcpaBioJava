/*
 * Created on 2005-12-5
 *
 *                    Rcpa development code
 *
 * Author Sheng QuanHu(qhsheng@sibs.ac.cn / shengqh@gmail.com)
 * This code is developed by RCPA Bioinformatic Platform
 * http://www.proteomics.ac.cn/
 *
 */
package cn.ac.rcpa.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.ac.rcpa.IParser;

public class PatternValueParser implements IParser<String> {
  private String title;

  private Pattern pattern;

  private String groupSpliter;

  private String errorFormatStr;

  public PatternValueParser(String title, String patternStr,
      String groupSpliter, String errorFormatStr) {
    this.title = title;
    this.pattern = Pattern.compile(patternStr);
    this.groupSpliter = groupSpliter;
    this.errorFormatStr = errorFormatStr;
  }

  public Pattern getPattern() {
    return pattern;
  }

  public String getValue(String name) {
    Matcher matcher = getPattern().matcher(name);
    if (!matcher.find()) {
      throw new IllegalArgumentException(String.format(errorFormatStr, name));
    }
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < matcher.groupCount(); i++) {
      if (i != 0) {
        sb.append(groupSpliter);
      }
      sb.append(matcher.group(i + 1));
    }
    return sb.toString();
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return "Parser by pattern (" + pattern.pattern() + ")";
  }

}
