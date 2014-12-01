package cn.ac.rcpa.bio.database;

import cn.ac.rcpa.bio.database.impl.AccessNumberForwarder;
import cn.ac.rcpa.bio.database.impl.IPIAccessNumberParser;
import cn.ac.rcpa.bio.database.impl.NRAccessNumberParser;
import cn.ac.rcpa.bio.database.impl.SwissProtAccessNumberParser;

public class AccessNumberParserFactory {
  private AccessNumberParserFactory() {
  }

  public static IAccessNumberParser getParser(SequenceDatabaseType dbType){
    if (dbType == SequenceDatabaseType.IPI){
      return IPIAccessNumberParser.getInstance();
    }
    else if (dbType == SequenceDatabaseType.SWISSPROT){
      return SwissProtAccessNumberParser.getInstance();
    }
    else if (dbType == SequenceDatabaseType.NR){
      return NRAccessNumberParser.getInstance();
    }
    else if (dbType == SequenceDatabaseType.OTHER){
      return AccessNumberForwarder.getInstance();
    }

    throw new IllegalArgumentException("Unrecognize database type in AccessNumberParserFactory : " + dbType );
  }
}
