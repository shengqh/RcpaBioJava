package cn.ac.rcpa.bio.proteomics.results.buildsummary.filter;

import java.util.ArrayList;

import cn.ac.rcpa.bio.proteomics.IIdentifiedPeptide;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.filter.impl.PeptideDeltaCnFilter;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.filter.impl.PeptideSpRankFilter;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.filter.impl.PeptideXCorrFilter;
import cn.ac.rcpa.filter.AndFilter;
import cn.ac.rcpa.filter.IFilter;

final public class BuildSummaryPeptideFilterFactory {

  public static IFilter<IIdentifiedPeptide> createXCorrFilter(double[] xcorr) {
    return new PeptideXCorrFilter(xcorr);
  }

  public static IFilter<IIdentifiedPeptide> createDeltaCnFilter(double deltacn) {
    return new PeptideDeltaCnFilter(deltacn);
  }

  public static IFilter<IIdentifiedPeptide> createSpRankFilter(int spRank) {
    return new PeptideSpRankFilter(spRank);
  }

  public static IFilter<IIdentifiedPeptide> createFilter(double[] xcorr, double deltacn) {
    ArrayList<IFilter<IIdentifiedPeptide>> filters = new ArrayList<IFilter<IIdentifiedPeptide>>();

    filters.add(createXCorrFilter(xcorr));
    filters.add(createDeltaCnFilter(deltacn));

    return new AndFilter<IIdentifiedPeptide>(filters);
  }

  public static IFilter<IIdentifiedPeptide> createFilter(double[] xcorr, double deltacn, int spRank) {
    ArrayList<IFilter<IIdentifiedPeptide>> filters = new ArrayList<IFilter<IIdentifiedPeptide>>();

    filters.add(createXCorrFilter(xcorr));
    filters.add(createDeltaCnFilter(deltacn));
    filters.add(createSpRankFilter(spRank));

    return new AndFilter<IIdentifiedPeptide>(filters);
  }
}
