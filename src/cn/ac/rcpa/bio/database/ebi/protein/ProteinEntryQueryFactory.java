package cn.ac.rcpa.bio.database.ebi.protein;

import java.sql.SQLException;

import cn.ac.rcpa.bio.database.SequenceDatabaseType;
import cn.ac.rcpa.bio.database.RcpaDBFactory;
import cn.ac.rcpa.bio.database.RcpaDatabaseType;
import cn.ac.rcpa.bio.database.ebi.protein.impl.IPIProteinEntryQuery;
import cn.ac.rcpa.bio.database.ebi.protein.impl.SwissProtProteinEntryQuery;

public class ProteinEntryQueryFactory {
  private ProteinEntryQueryFactory() {
  }

  public static IProteinEntryQuery create(SequenceDatabaseType dbType) throws ClassNotFoundException, SQLException{
    if (dbType == SequenceDatabaseType.IPI){
      return new IPIProteinEntryQuery(RcpaDBFactory.getInstance().getConnection(RcpaDatabaseType.ANNOTATION));
    }

    if (dbType == SequenceDatabaseType.SWISSPROT){
      return new SwissProtProteinEntryQuery(RcpaDBFactory.getInstance().getConnection(RcpaDatabaseType.ANNOTATION));
    }

    throw new IllegalArgumentException("Cannot crete ProteinEntryQuery for database " + dbType);
  }
}
