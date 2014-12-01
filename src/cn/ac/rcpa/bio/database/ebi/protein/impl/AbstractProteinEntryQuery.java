package cn.ac.rcpa.bio.database.ebi.protein.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import cn.ac.rcpa.bio.database.AbstractDBApplication;
import cn.ac.rcpa.bio.database.ebi.protein.IProteinEntryQuery;
import cn.ac.rcpa.bio.database.ebi.protein.entry.Db_reference;
import cn.ac.rcpa.bio.database.ebi.protein.entry.Free_comment;
import cn.ac.rcpa.bio.database.ebi.protein.entry.ProteinEntry;

abstract public class AbstractProteinEntryQuery
    extends AbstractDBApplication
    implements IProteinEntryQuery {

  public AbstractProteinEntryQuery(Connection connection) {
    super(connection);
  }

  final public Map<String, ProteinEntry> getEntries(String[] identities) throws SQLException {
    Map<String, ProteinEntry> result = new LinkedHashMap<String, ProteinEntry>();
    for (int i = 0; i < identities.length; i++) {
      final ProteinEntry entry = getEntry(identities[i]);
      if (entry != null){
        result.put(identities[i], entry);
      }
    }
    return result;
  }

  abstract public ProteinEntry getEntry(String identity) throws SQLException;

  protected ProteinEntry getProteinEntryByID(String spid) throws SQLException {
    ProteinEntry entry = new ProteinEntry();

    fillSwissProt(entry, spid);
    fillAccession(entry, spid);
    fillDb_references(entry, spid);
    fillFree_comments(entry, spid);

    return entry;
  }

  private void fillSwissProt(ProteinEntry entry, String spid) throws
      SQLException {
    final ResultSet rsSprot = connection.createStatement().executeQuery(
        "select * from swissprot where id=" + spid);
    if (!rsSprot.next()) {
      throw new IllegalStateException("Cannot find " + spid + " table swissprot! Database maybe crashed, contact administrator to fix it!");
    }

    entry.setEntry_name(rsSprot.getString(2));
    entry.setData_class(rsSprot.getString(3));
    entry.setMolecule_type(rsSprot.getString(4));
    entry.setSequence_length(rsSprot.getInt(5));
    entry.setCreate(rsSprot.getString(6));
    entry.setSequence_update(rsSprot.getString(7));
    entry.setAnnotation_update(rsSprot.getString(8));
    entry.setDescription(rsSprot.getString(9));
    entry.setGene_name(rsSprot.getString(10));
    entry.setOrganism_species(rsSprot.getString(11));
    entry.setOrganelle(rsSprot.getString(12));
    entry.setOrganism_classification(rsSprot.getString(13));
    entry.setTaxonomy_id(rsSprot.getString(14));
    entry.setKeyword(rsSprot.getString(15));
    entry.setMw(rsSprot.getInt(16));
    entry.setCrc(rsSprot.getString(17));
    entry.setSequence(rsSprot.getString(18));
  }

  private void fillAccession(ProteinEntry entry, String spid) throws SQLException {
    entry.clearAc_number();

    final ResultSet rsAc = connection.createStatement().executeQuery(
        "select * from accession where swissprot_id=" + spid);
    while (rsAc.next()) {
      entry.addAc_number(rsAc.getString(2));
    }
  }

  private void fillDb_references(ProteinEntry entry, String spid) throws SQLException {
    entry.clearDb_reference();

    final ResultSet rsDr = connection.createStatement()
        .executeQuery("select * from db_references where swissprot_id="
                      + spid);
    while (rsDr.next()) {
      Db_reference dr = new Db_reference();
      dr.setDb(rsDr.getString(2));
      dr.setPrimary_identifier(rsDr.getString(3));
      dr.setSecondary_identifier(rsDr.getString(4));
      dr.setTertiary_identifier(rsDr.getString(5));
      entry.addDb_reference(dr);
    }
  }

  private void fillFree_comments(ProteinEntry entry, String spid) throws SQLException {
    entry.clearFree_comment();

    final ResultSet rsFc = connection.createStatement()
        .executeQuery("select * from free_comments where swissprot_id="
                      + spid);
    while (rsFc.next()) {
      Free_comment fc = new Free_comment();
      fc.setCc_topic(rsFc.getString(2));
      fc.setCc_details(rsFc.getString(3));
      entry.addFree_comment(fc);
    }
  }
}
