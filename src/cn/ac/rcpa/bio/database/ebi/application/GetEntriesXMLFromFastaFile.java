/*
 * 创建日期 2004-8-20
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package cn.ac.rcpa.bio.database.ebi.application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.biojava.bio.BioException;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;

import cn.ac.rcpa.bio.database.AccessNumberFastaParser;
import cn.ac.rcpa.bio.database.SequenceDatabaseType;
import cn.ac.rcpa.bio.database.ebi.protein.ProteinEntryQueryFactory;
import cn.ac.rcpa.bio.database.ebi.protein.entry.ProteinEntry;
import cn.ac.rcpa.bio.database.ebi.protein.entry.ProteinEntrySet;

/**
 * @author long
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class GetEntriesXMLFromFastaFile {
  private static GetEntriesXMLFromFastaFile instance;

  public static GetEntriesXMLFromFastaFile getInstance() {
    if (instance == null) {
      instance = new GetEntriesXMLFromFastaFile();
    }
    return instance;
  }

  private GetEntriesXMLFromFastaFile() {
  }

  public String getEntriesXMLFromFasta(String fastaFilename,
                                            SequenceDatabaseType dbType) throws
      BioException, SQLException, IOException,
      ValidationException, MarshalException, ClassNotFoundException {
    final String result = fastaFilename + ".xml";

    final String[] identities = AccessNumberFastaParser.getNames(fastaFilename,
        dbType);

    final Map<String,
        ProteinEntry> entries = ProteinEntryQueryFactory.create(dbType).
        getEntries(identities);

    final ProteinEntrySet entryset = new ProteinEntrySet();
    for (ProteinEntry entry : entries.values()) {
      entryset.addProteinEntry(entry);
    }
    entryset.marshal(new FileWriter(result));

    Set<String> identitySet = new HashSet<String>(Arrays.asList(identities));
    identitySet.removeAll(entries.keySet());

    final File missFilename = new File(fastaFilename + ".miss");
    if (identitySet.size() > 0) {
      final PrintWriter pw = new PrintWriter(new FileWriter(missFilename.getAbsoluteFile()));
      try {
        for (String identity : identitySet) {
          pw.println(identity);
        }
      }
      finally {
        pw.close();
      }
    }
    else if (missFilename.exists()){
      missFilename.delete();
    }

    return result;
  }

  public static void main(String[] args) throws Exception {
    String fastaFilename = "data/testGetEntriesXMLFromFasta.fasta";
    SequenceDatabaseType dbType = SequenceDatabaseType.IPI;
    GetEntriesXMLFromFastaFile.getInstance().getEntriesXMLFromFasta(fastaFilename, dbType);
  }
}
