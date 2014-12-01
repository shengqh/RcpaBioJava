package cn.ac.rcpa.bio.database.ebi.protein;

import java.sql.SQLException;
import java.util.Map;

import cn.ac.rcpa.bio.database.ebi.protein.entry.ProteinEntry;

public interface IProteinEntryQuery {
  ProteinEntry getEntry(String identity) throws SQLException;
  Map<String, ProteinEntry> getEntries(String[] identity) throws SQLException;
}
