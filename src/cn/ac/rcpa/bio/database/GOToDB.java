package cn.ac.rcpa.bio.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.jdom.JDOMException;

import cn.ac.rcpa.bio.annotation.GOEntryMap;
import cn.ac.rcpa.bio.annotation.IGOEntry;
import cn.ac.rcpa.bio.utils.DatabaseUtils;

public class GOToDB extends AbstractDBApplication {
	public GOToDB(Connection connection) {
		super(connection);
	}

	public static void main(String[] args) throws Exception {
		File goFile = new File("");

		if (args.length > 0) {
			goFile = new File(args[0]);
		}

		if (!goFile.exists()) {
			printPrompt();
			return;
		}

		new GOToDB(RcpaDBFactory.getInstance().getConnection(
				RcpaDatabaseType.ANNOTATION)).store(goFile.getAbsolutePath());
	}

	public void store(String goFile) throws Exception {
		GOEntryMap totalMap = getTotalGOEntry(goFile);

		createTable();
		try {
			addFileToDB(totalMap);
			DatabaseUtils.appendDatabaseLog(connection, goFile);
		} finally {
			createIndex();
		}
	}

	private static void printPrompt() {
		System.err.println("GOToDB go_YYYYMM-termdb.rdf-xml");
	}

	private void addFileToDB(GOEntryMap totalMap) throws SQLException,
			FileNotFoundException, IOException, JDOMException {

		System.out.println("Importing GO data ...");
		PreparedStatement ps = connection
				.prepareStatement("INSERT INTO GO VALUES(?,?,?)");
		String[] accessions = totalMap.getAccessions();
		int icount = 0;
		for (String accession : accessions) {
			if (!accession.startsWith("GO:")) {
				continue;
			}

			icount++;
			if (icount % 1000 == 0) {
				System.out.println(icount);
			}

			IGOEntry entry = totalMap.getEntry(accession);

			ps.setString(1, entry.getAccession());
			ps.setString(2, entry.getName());
			ps.setString(3, entry.getDefinition());

			ps.execute();
		}

		System.out.println("Import GO data succeed.");
	}

	private void dropTable() {
		try {
			Statement sm = connection.createStatement();
			sm.execute("DROP TABLE GO");
		} catch (Exception ex) {
		}
	}

	private void dropIndex() {
		try {
			Statement sm = connection.createStatement();
			sm.execute("DROP INDEX GO_ACCESSION");
			sm.execute("DROP INDEX GO_NAME");
		} catch (Exception ex) {
		}
	}

	private void createIndex() throws SQLException {
		Statement sm = connection.createStatement();
		sm.execute("CREATE INDEX GO_ACCESSION ON GO(\"ACCESSION\" ASC)");
		sm.execute("CREATE INDEX GO_NAME ON GO(\"NAME\" ASC)");
	}

	private void createTable() throws SQLException {
		dropTable();

		dropIndex();

		System.out.print("Creating GO database ... ");

		String createSQL = "CREATE TABLE GO" + "\n" + "("
				+ "\"ACCESSION\" Varchar(50) ASCII,"
				+ "\"NAME\" Varchar (200) ASCII,"
				+ "\"DEFINITION\" LONG ASCII )";

		connection.createStatement().execute(createSQL);

		System.out.println("succeed.");
	}

	private static GOEntryMap getTotalGOEntry(String filename) throws Exception {
		System.out.print("Getting total entries from " + filename + " ... ");
		GOEntryMap goMap = GOEntryMap.getGOEntryMapFromXMLFile(filename);
		System.out.println("finished.");
		return goMap;
	}

}
