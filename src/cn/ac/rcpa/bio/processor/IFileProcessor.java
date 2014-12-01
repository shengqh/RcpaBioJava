package cn.ac.rcpa.bio.processor;

import java.util.List;

public interface IFileProcessor {
  /**
   * Read an origin file and write to another format file
   *
   * @param originFile origin file
   * @return List<String> result files
   * @throws Exception
   */
  List<String> process(String originFile) throws Exception;
}
