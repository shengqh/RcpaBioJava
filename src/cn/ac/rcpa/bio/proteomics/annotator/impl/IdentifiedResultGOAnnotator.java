package cn.ac.rcpa.bio.proteomics.annotator.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.ac.rcpa.bio.annotation.AnnotationFactory;
import cn.ac.rcpa.bio.annotation.FunctionClassificationEntryMap;
import cn.ac.rcpa.bio.annotation.IAnnotationQuery;
import cn.ac.rcpa.bio.database.AccessNumberParserFactory;
import cn.ac.rcpa.bio.database.SequenceDatabaseType;
import cn.ac.rcpa.bio.database.IAccessNumberParser;
import cn.ac.rcpa.bio.proteomics.IIdentifiedProtein;
import cn.ac.rcpa.bio.proteomics.IIdentifiedResult;
import cn.ac.rcpa.bio.proteomics.annotator.IIdentifiedResultAnnotator;

public class IdentifiedResultGOAnnotator
    implements IIdentifiedResultAnnotator {
  private IAccessNumberParser parser;
  private IAnnotationQuery query;

  public IdentifiedResultGOAnnotator(SequenceDatabaseType dbType) throws ClassNotFoundException, SQLException {
    this.parser = AccessNumberParserFactory.getParser(dbType);
    this.query = AnnotationFactory.getAnnotationQuery(dbType);
  }

  @SuppressWarnings("unchecked")
  public void addAnnotation(IIdentifiedResult sequestResult) {
    System.out.println("Filling GOA information...");

    final List prohits = sequestResult.getProteins();

    final FunctionClassificationEntryMap annotation = query.getAnnotation(getAccessNumbers(prohits));

    for (Iterator iter = prohits.iterator(); iter.hasNext(); ) {
      IIdentifiedProtein protein = (IIdentifiedProtein)iter.next();
      String ac = parser.getValue(protein.getProteinName());
      if (annotation.containsKey(ac)) {
        protein.getAnnotation().put("GOA", annotation.get(ac));
      }
    }

    System.out.println("Filling GOA information finished");
  }

  private String[] getAccessNumbers(List prohits) {
    List<String> result = new ArrayList<String>();

    for (Iterator iter = prohits.iterator(); iter.hasNext(); ) {
      IIdentifiedProtein protein = (IIdentifiedProtein)iter.next();
      String ac = parser.getValue(protein.getProteinName());
      if (ac == null) {
        throw new IllegalArgumentException("Parser " +
                                           parser.getClass().getName() +
                                           " cannot parse current protein name : " +
                                           protein.getProteinName());
      }
      result.add(ac);
    }
    return (String[]) result.toArray(new String[0]);
  }

}
