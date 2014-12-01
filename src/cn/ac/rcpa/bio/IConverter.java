package cn.ac.rcpa.bio;

import java.util.List;

public interface IConverter {
  /**
   * Read an origin file and write to another format file
   *
   * @param originFile : origin file name
   * @return List<String> : result file names
   */
  List<String> convert(String originFile) throws Exception;
}
