package cn.ac.rcpa.bio.proteomics.modification;

import cn.ac.rcpa.filter.IFilter;
import cn.ac.rcpa.utils.Pair;

public class ModificationSiteFilterFactory {
  private ModificationSiteFilterFactory() {
  }

  public static IFilter<Pair<Character, Character>>
      getModifiedSiteFilter() {
    return new IFilter<Pair<Character, Character>> () {
      public boolean accept(Pair<Character, Character> e) {
        return!e.snd.equals(' ');
      }

      public String getType() {
        return "ModifiedSite";
      }
    };
  }

  public static IFilter<Pair<Character, Character>>
      getAmbigiousModifiedSiteFilter() {
    return new IFilter<Pair<Character, Character>> () {
      public boolean accept(Pair<Character, Character> e) {
        return e.snd.equals(SequenceModificationSitePair.AMBIGUOUS_SITE);
      }

      public String getType() {
        return "ModifiedSite";
      }
    };
  }

  public static IFilter<Pair<Character, Character>>
      getPositiveModifiedSiteFilter() {
    return new IFilter<Pair<Character, Character>> () {
      public boolean accept(Pair<Character, Character> e) {
        return!e.snd.equals(' ') &&
            !e.snd.equals(SequenceModificationSitePair.AMBIGUOUS_SITE);
      }

      public String getType() {
        return "ModifiedSite";
      }
    };
  }

  public static IFilter<Pair<Character, Character>>
      getMultipleStateModifiedSiteFilter() {
    return new IFilter<Pair<Character, Character>> () {
      public boolean accept(Pair<Character, Character> e) {
        return e.snd.equals(SequenceModificationSitePair.MULTI_STATE_SITE);
      }

      public String getType() {
        return "ModifiedSite";
      }
    };
  }

}
