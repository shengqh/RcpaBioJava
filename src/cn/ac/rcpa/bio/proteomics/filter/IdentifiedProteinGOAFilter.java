package cn.ac.rcpa.bio.proteomics.filter;

import cn.ac.rcpa.bio.annotation.FunctionClassificationEntry;
import cn.ac.rcpa.bio.proteomics.IIdentifiedProtein;
import cn.ac.rcpa.filter.IFilter;

/**
 * <p>Title: RCPA Package</p>
 *
 * <p>Description: Filter protein hit by GO Annotation information</p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: RCPA.SIBS.AC.CN</p>
 * @author Sheng Quan-Hu (shengqh@gmail.com)
 * @version 1.0
 */
public class IdentifiedProteinGOAFilter
    implements IFilter<IIdentifiedProtein> {
  private static IdentifiedProteinGOAFilter instance;

  public static IdentifiedProteinGOAFilter getInstance() {
    if (instance == null) {
      instance = new IdentifiedProteinGOAFilter();
    }
    return instance;
  }

  private IdentifiedProteinGOAFilter() {
  }

  public boolean accept(IIdentifiedProtein prohit) {
    if (prohit.getAnnotation().containsKey("GOA")) {
      final FunctionClassificationEntry[] goa = (FunctionClassificationEntry[])
          prohit.getAnnotation().get("GOA");
      return goa.length > 0;
    }
    return false;
  }

  @Override
  public String toString(){
    return getType();
  }

  public String getType() {
    return "GOA";
  }
}
