package cn.ac.rcpa.bio.database.ebi.application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import cn.ac.rcpa.bio.database.AbstractDBApplication;
import cn.ac.rcpa.bio.database.ebi.protein.entry.ProteinEntry;

/*
 * 创建日期 2004-5-28
 * @author Li Long, Sheng QuanHu
 */
public class ProteinEntryToDB extends AbstractDBApplication{
  public ProteinEntryToDB(Connection connection){
    super(connection);
  }

//    private int currSwissProtID = 1;

  public void createTable() throws SQLException {
    final Statement stmt = connection.createStatement();

    stmt
        .execute("CREATE TABLE SWISSPROT (	ID Integer PRIMARY KEY ,	ENTRY_NAME Varchar (100) ASCII,	DATA_CLASS Varchar (100) ASCII, MOLECULE_TYPE Varchar (100) ASCII,	SEQUENCE_LENGTH Integer, CREATE Varchar (100) ASCII, SEQUENCE_UPDATE  Varchar (100) ASCII,	ANNOTATION_UPDATE Varchar (100) ASCII,	DESCRIPTION Long ASCII,	GENE_NAME Long ASCII,	ORGANISM_SPECIE  Long ASCII, ORGANELLE  Long ASCII,	ORGANISM_CLASSIFICATION Long ASCII,	TAXONOMY_ID   Varchar (200) ASCII,	KEYWORD  Long ASCII,	MW  Integer,	CRC Long ASCII,	SEQUENCE   Long ASCII)");
    stmt
        .execute("CREATE TABLE ACCESSION	(id	integer	PRIMARY KEY , AC_NUMBER		varchar(500) ,	swissprot_id	integer NOT NULL	REFERENCES SWISSPROT(ID) ON DELETE RESTRICT )");
    stmt
        .execute("CREATE TABLE REFERENCE	(id	integer	PRIMARY KEY ,	NUMBER	integer ,	POSITION	long ,	COMMENT	long ,	MEDLINE_NUM	integer ,	PUBMED_NUM	integer ,	AUTHOR	long ,	TITLE		long ,	LOCATION	long ,	swissprot_id	integer NOT NULL	 REFERENCES SWISSPROT(ID) ON DELETE RESTRICT ,	UNIQUE (NUMBER, swissprot_id))");
    stmt
        .execute("CREATE TABLE FREE_COMMENTS	(id	integer	PRIMARY KEY ,	CC_TOPIC	varchar(100)	 ,	CC_DETAILS	long ,	swissprot_id	integer NOT NULL	 REFERENCES SWISSPROT(ID) ON DELETE RESTRICT)");
    stmt
        .execute("CREATE TABLE DB_REFERENCES	(id	integer	PRIMARY KEY ,	DB	varchar(100)	 ,	PRIMARY_IDENTIFIER	varchar(200) ,	SECONDARY_IDENTIFIER	varchar(200) ,	TERTIARY_IDENTIFIER	varchar(200) ,	swissprot_id	integer NOT NULL	 REFERENCES SWISSPROT(ID) ON DELETE RESTRICT)");
    stmt
        .execute("CREATE TABLE FEATURE_TABLE	(id	integer	PRIMARY KEY ,	KEY_NAME	varchar(100)	 ,	SEQUENCE_FROM		integer ,	SEQUENCE_TO	integer ,	FT_DESCRIPTION	long ,	swissprot_id	integer NOT NULL	 REFERENCES SWISSPROT(ID) ON DELETE RESTRICT)");

    stmt.execute("CREATE SEQUENCE SWISSPROT_ID CACHE 100");
    stmt.execute("CREATE SEQUENCE ACCESSION_ID CACHE 100");
    stmt.execute("CREATE SEQUENCE REFERENCE_ID CACHE 100");
    stmt.execute("CREATE SEQUENCE FREE_COMMENTS_ID CACHE 100");
    stmt.execute("CREATE SEQUENCE DB_REFERENCES_ID CACHE 100");
    stmt.execute("CREATE SEQUENCE FEATURE_TABLE_ID CACHE 100");
    /*
     * stmt.execute("GRANT SELECT ON SWISSPROT TO ANNOTATION");
     * stmt.execute("GRANT SELECT ON ACCESSION TO ANNOTATION");
     * stmt.execute("GRANT SELECT ON REFERENCE TO ANNOTATION");
     * stmt.execute("GRANT SELECT ON FREE_COMMENTS TO ANNOTATION");
     * stmt.execute("GRANT SELECT ON DB_REFERENCES TO ANNOTATION");
     * stmt.execute("GRANT SELECT ON FEATURE_TABLE TO ANNOTATION");
     */
    stmt.close();
  }

  public void createIndex() throws SQLException {
    final Statement stmt = connection.createStatement();
    stmt.execute("create index SWISSPROT_ENTRY_NAME on SWISSPROT(ENTRY_NAME)");
    stmt.execute("create index ACCESSION_ACNUMBER on ACCESSION(AC_NUMBER)");
    stmt.execute("create index ACCESSION_SPID on ACCESSION(SWISSPROT_ID)");
    stmt.execute("create index REFERENCE_SPID on REFERENCE(SWISSPROT_ID)");
    stmt
        .execute("create index FREE_COMMENTS_SPID on FREE_COMMENTS(SWISSPROT_ID)");
    stmt
        .execute("create index DB_REFERENCES_SPID on DB_REFERENCES(SWISSPROT_ID)");
    stmt
        .execute("create index DB_REFERENCES_DB on DB_REFERENCES(DB)");
    stmt
        .execute("create index FEATURE_TABLE_SPID on FEATURE_TABLE(SWISSPROT_ID)");
  }

  private void dropIndex(String indexName) {
    try {
      connection.createStatement().execute("drop index " + indexName);
    } catch (SQLException ex) {
    }
  }

  private void dropTable(String tableName) {
    try {
      connection.createStatement().execute("drop table " + tableName);
    } catch (SQLException ex) {
    }
  }

  private void dropSequence(String sequenceName) {
    try {
      connection.createStatement().execute("drop SEQUENCE " + sequenceName);
    } catch (SQLException ex) {
    }
  }

  public void dropIndex() {
    dropIndex("SWISSPROT_ENTRY_NAME");
    dropIndex("ACCESSION_ACNUMBER");
    dropIndex("ACCESSION_SPID");
    dropIndex("FREE_COMMENTS_SPID");
    dropIndex("DB_REFERENCES_SPID");
    dropIndex("DB_REFERENCES_DB");
    dropIndex("REFERENCE_SPID");
    dropIndex("FEATURE_TABLE_SPID");
  }

  public void dropTable() {
    dropTable("REFERENCE");
    dropTable("FEATURE_TABLE");
    dropTable("ACCESSION");
    dropTable("FREE_COMMENTS");
    dropTable("DB_REFERENCES");
    dropTable("SWISSPROT");

    dropSequence("SWISSPROT_ID");
    dropSequence("ACCESSION_ID");
    dropSequence("REFERENCE_ID");
    dropSequence("FREE_COMMENTS_ID");
    dropSequence("DB_REFERENCES_ID");
    dropSequence("FEATURE_TABLE_ID");
  }

  public void storeToDB(ProteinEntry entry) throws SQLException {
    storeToSwissProtTable(entry);
    storeToAccessionTable(entry);
    storeToFreeCommentsTable(entry);
    storeToDBReferencesTable(entry);
    storeToReferencesTable(entry);
    storeToFeatureTableTable(entry);
  }

  /**
   * @param entry
   * @throws SQLException
   */
  private void storeToFeatureTableTable(ProteinEntry entry) throws SQLException {
    final PreparedStatement dr = connection
        .prepareStatement("insert into FEATURE_TABLE VALUES (FEATURE_TABLE_ID.nextval,?,?,?,?,SWISSPROT_id.currval)");
    for (int i = 0; i < entry.getFeature_tableCount(); i++) {
      dr.setString(1, entry.getFeature_table(i).getKey_name());
      dr.setInt(2, entry.getFeature_table(i).getSequence_from());
      dr.setInt(3, entry.getFeature_table(i).getSequence_to());
      dr.setString(4, entry.getFeature_table(i).getFt_description());
      dr.executeUpdate();
    }
    dr.close();
  }

  /**
   * @param entry
   */
  private void storeToReferencesTable(ProteinEntry entry) throws SQLException {
    final PreparedStatement dr = connection
        .prepareStatement("insert into REFERENCE VALUES (REFERENCE_ID.nextval,?,?,?,?,?,?,?,?,SWISSPROT_id.currval)");
    for (int i = 0; i < entry.getReferenceCount(); i++) {
      dr.setInt(1, i + 1);
      dr.setString(2, entry.getReference(i).getPosition());
      dr.setString(3, entry.getReference(i).getComment());
      dr.setInt(4, entry.getReference(i).getMedline_num());
      dr.setInt(5, entry.getReference(i).getPubmed_num());
      dr.setString(6, entry.getReference(i).getAuthor());
      dr.setString(7, entry.getReference(i).getTitle());
      dr.setString(8, entry.getReference(i).getLocation());
      dr.executeUpdate();
    }
    dr.close();
  }

  private void storeToDBReferencesTable(ProteinEntry entry) throws SQLException {
    final PreparedStatement dr = connection
        .prepareStatement("insert into DB_REFERENCES VALUES (DB_REFERENCES_ID.nextval,?,?,?,?,SWISSPROT_id.currval)");
    for (int i = 0; i < entry.getDb_referenceCount(); i++) {
      dr.setString(1, entry.getDb_reference(i).getDb());
      dr.setString(2, entry.getDb_reference(i).getPrimary_identifier());
      dr.setString(3, entry.getDb_reference(i).getSecondary_identifier());
      dr.setString(4, entry.getDb_reference(i).getTertiary_identifier());
      dr.executeUpdate();
    }
    dr.close();
  }

  private void storeToFreeCommentsTable(ProteinEntry entry) throws SQLException {
    final PreparedStatement cc = connection
        .prepareStatement("insert into FREE_COMMENTS VALUES (FREE_COMMENTS_ID.nextval,?,?,SWISSPROT_id.currval)");
    for (int i = 0; i < entry.getFree_commentCount(); i++) {
      cc.setString(1, entry.getFree_comment(i).getCc_topic());
      cc.setString(2, entry.getFree_comment(i).getCc_details());
      cc.executeUpdate();
    }
    cc.close();
  }

  private void storeToAccessionTable(ProteinEntry entry) throws SQLException {
    final PreparedStatement acnum = connection
        .prepareStatement("insert into ACCESSION VALUES (ACCESSION_ID.nextval,?,SWISSPROT_id.currval)");
    for (int i = 0; i < entry.getAc_numberCount(); i++) {
      acnum.setString(1, entry.getAc_number(i));
      acnum.executeUpdate();
    }
    acnum.close();
  }

  private void storeToSwissProtTable(ProteinEntry entry) throws SQLException {
    final PreparedStatement sprot = connection
        .prepareStatement("insert into SWISSPROT VALUES (SWISSPROT_ID.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
    sprot.setString(1, entry.getEntry_name());
    sprot.setString(2, entry.getData_class());
    sprot.setString(3, entry.getMolecule_type());
    sprot.setInt(4, entry.getSequence_length());
    sprot.setString(5, entry.getCreate());
    sprot.setString(6, entry.getSequence_update());
    sprot.setString(7, entry.getAnnotation_update());
    sprot.setString(8, entry.getDescription());
    sprot.setString(9, entry.getGene_name());
    sprot.setString(10, entry.getOrganism_species());
    sprot.setString(11, entry.getOrganelle());
    sprot.setString(12, entry.getOrganism_classification());
    sprot.setString(13, entry.getTaxonomy_id());
    sprot.setString(14, entry.getKeyword());
    sprot.setInt(15, entry.getMw());
    sprot.setString(16, entry.getCrc());
    sprot.setString(17, entry.getSequence());
    sprot.executeUpdate();
    sprot.close();
  }
}
