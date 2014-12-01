package cn.ac.rcpa.bio.database.ebi.application;

import java.util.ArrayList;
import java.util.HashMap;

import cn.ac.rcpa.bio.database.ebi.protein.entry.ProteinEntry;

/*
 * 创建日期 2004-6-28
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */

/**
 * @author long
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class ProteinAnnotation {
  public static final String[] cctopicArray = {
      "DISEASE",
      "DOMAIN",
      "ENZYME REGULATION",
      "FUNCTION",
      "MASS SPECTROMETRY",
      "PATHWAY",
      "PTM",
      "SIMILARITY",
      "SUBCELLULAR LOCATION",
      "TISSUE SPECIFICITY"
  };

  public static HashMap[] getFreeCommentsAnnotation(ProteinEntry[] entry) {
    //String[][] cc = new  String[entry.length][11];
    ArrayList<HashMap> ccArray = new ArrayList<HashMap>();

    for (int i = 0; i < entry.length; i++) {
      HashMap<String, String> cc = new HashMap<String, String>();
      cc.put("ID", entry[i].getEntry_name());
      for (int j = 0; j < entry[i].getFree_comment().length; j++) {
        if (containValue(cctopicArray, entry[i].getFree_comment(j).getCc_topic())) {
          cc.put(entry[i].getFree_comment(j).getCc_topic(),
                 entry[i].getFree_comment(j).getCc_details());
        }
      }
      ccArray.add(i, cc);
    }
    return (HashMap[]) ccArray.toArray(new HashMap[0]);
  }

  public static boolean containValue(String[] array, String value) {
    for (int i = 0; i < array.length; i++) {
      if (value.equals(array[i])) {
        return true;
      }
    }
    return false;
  }
}
