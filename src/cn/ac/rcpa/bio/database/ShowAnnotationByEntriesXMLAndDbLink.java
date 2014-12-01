/*
 * 创建日期 2004-8-25
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package cn.ac.rcpa.bio.database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;

import cn.ac.rcpa.bio.database.ebi.protein.entry.ProteinEntry;
import cn.ac.rcpa.bio.database.ebi.protein.entry.ProteinEntrySet;
import cn.ac.rcpa.bio.database.link.DatabaseLink;
import cn.ac.rcpa.bio.database.link.DatabaseLinkSet;
import cn.ac.rcpa.utils.RcpaStringUtils;

/**
 * @author long
 *
 * 更改所生成类型注释的模板为 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class ShowAnnotationByEntriesXMLAndDbLink {
  public static void showEntriesByXMLAndDatabaseLink(
      ProteinEntrySet entryset, DatabaseLinkSet dblinkset,
      String resultFilename, SequenceDatabaseType dbType) throws IOException {
    final PrintWriter pw = new PrintWriter(new FileWriter(resultFilename));
    final String shortlabel = shortlabelVSdatabasetype(dbType);
    final DatabaseLink[] dblinks = dblinkset.getDatabaseLink();
    String search = new String();
    for (int j = 0; j < dblinkset.getDatabaseLinkCount(); j++) {
      if (dblinks[j].getShortlabel().equals(shortlabel)) {
        search = dblinks[j].getSearch_url();
        break;
      }
    }
    final ProteinEntry[] entries = entryset.getProteinEntry();
    pw.print("<html>\n<head>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">\n"
             + "<title>index</title>\n</head>\n<body>\n");
    for (int i = 0; i < entries.length; i++) {
      final String name = entries[i].getEntry_name();
      pw.print("<p><a href=\"" + name + ".html\" target=\"_blank\">"
               + name + "</a></p>\n");
      final String page = name + ".html";
      File path = new File(resultFilename);
      File onesearch = new File(path.getParent(), page);
      onesearch.createNewFile();
      final PrintWriter annopw = new PrintWriter(
          new FileWriter(onesearch));
      annopw.print("<html>\n<head>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\">\n<title>"
                   + name + "</title>\n</head>\n<body>\n");
      annopw.print("<pre>\n");
      annopw.print("ID " + "<a href=\""
                   + search.replaceAll("\\$\\{ac\\}", name)
                   + "\" target=\"_blank\">" + name + "</a>" + " "
                   + entries[i].getData_class() + "; "
                   + entries[i].getMolecule_type() + "; "
                   + entries[i].getSequence_length() + "AA.");
      annopw.print("AC ");
      for (int k = 0; k < entries[i].getAc_numberCount(); k++) {
        annopw.print(entries[i].getAc_number(k) + ";");
      }
      annopw.print("\n");
      annopw.print("DT " + entries[i].getCreate() + "\n");
      annopw.print("DT " + entries[i].getSequence_update() + "\n");
      if (entries[i].getAnnotation_update() != null) {
        annopw.print("DT " + entries[i].getAnnotation_update() + "\n");
      }
      if (entries[i].getDescription() != null) {
        annopw.print("DE " + entries[i].getDescription() + "\n");
      }
      if (entries[i].getGene_name() != null) {
        annopw.print("GN " + entries[i].getGene_name() + "\n");
      }
      annopw.print("OS " + entries[i].getOrganism_species() + "\n");
      if (entries[i].getOrganelle() != null) {
        annopw.print("OG " + entries[i].getOrganelle() + "\n");
      }
      annopw.print("OC " + entries[i].getOrganism_classification()
                   + "\n");
      annopw.print("OX " + entries[i].getTaxonomy_id() + "\n");
      if (entries[i].getReferenceCount() != 0) {
        for (int k = 0; k < entries[i].getReferenceCount(); k++) {
          annopw.print("RN " + entries[i].getReference(k).getNum() + "\n");
          if (entries[i].getReference(k).getPosition() != null) {
            annopw.print("RP " + entries[i].getReference(k).getPosition() +
                         "\n");
          }
          if (entries[i].getReference(k).getComment() != null) {
            annopw.print("RC " + entries[i].getReference(k).getComment() + "\n");
          }
          annopw.print("RX ");
          if (entries[i].getReference(k).getMedline_num() != 0) {
            annopw.print("MEDLINE=" + entries[i].getReference(k).getMedline_num());
          }
          if (entries[i].getReference(k).getPubmed_num() != 0) {
            annopw.print("PubMed=" + entries[i].getReference(k).getPubmed_num());
          }
          if (entries[i].getReference(k).getDoi_num() != null) {
            annopw.print("DOI=" + entries[i].getReference(k).getDoi_num());
          }
          annopw.print("\n");
          if (entries[i].getReference(k).getAuthor() != null) {
            annopw.print("RA " + entries[i].getReference(k).getAuthor() + "\n");
          }
          if (entries[i].getReference(k).getTitle() != null) {
            annopw.print("RT " + entries[i].getReference(k).getTitle() + "\n");
          }
          if (entries[i].getReference(k).getLocation() != null) {
            annopw.print("RL " + entries[i].getReference(k).getLocation() +
                         "\n");
          }
        }
      }
      if (entries[i].getFree_commentCount() != 0) {
        for (int k = 0; k < entries[i].getFree_commentCount(); k++) {
          annopw.print("CC -!-"
                       + entries[i].getFree_comment(k).getCc_topic()
                       + ": "
                       + entries[i].getFree_comment(k).getCc_details()
                       + ".\n");
        }
      }
      if (entries[i].getDb_referenceCount() != 0) {
        for (int k = 0; k < entries[i].getDb_referenceCount(); k++) {
          annopw.print("DR " + entries[i].getDb_reference(k).getDb() + "; ");
          annopw.print(entries[i].getDb_reference(k).getPrimary_identifier() +
                       "; ");
          if (entries[i].getDb_reference(k).getSecondary_identifier() != null) {
            annopw.print(entries[i].getDb_reference(k).getSecondary_identifier() +
                         "; ");
          }
          if (entries[i].getDb_reference(k).getTertiary_identifier() != null) {
            annopw.print(entries[i].getDb_reference(k).getTertiary_identifier() +
                         ".");
          }
          annopw.print("\n");
        }
      }
      if (entries[i].getKeyword() != null) {
        annopw.print("KW " + entries[i].getKeyword() + "\n");
      }
      if (entries[i].getFeature_tableCount() != 0) {
        for (int k = 0; k < entries[i].getFeature_tableCount(); k++) {
          annopw.print("FT " + entries[i].getFeature_table(k).getKey_name() +
                       " " + entries[i].getFeature_table(k).getSequence_from() +
                       " " + entries[i].getFeature_table(k).getSequence_to());
          if (entries[i].getFeature_table(k).getFt_description() != null) {
            annopw.print(" " + entries[i].getFeature_table(k).getFt_description());
          }
          annopw.print("\n");
        }
      }
      annopw.print("SQ SEQUENCE" + entries[i].getSequence_length() + "AA;");
      if (entries[i].getMw() != 0) {
        annopw.print(" " + entries[i].getMw());
      }
      if (entries[i].getCrc() != null) {
        annopw.print(" " + entries[i].getCrc());
      }
      annopw.print("\n");
      annopw.print("   " + RcpaStringUtils.warpString(entries[i].getSequence(), 60));
      annopw.print("</pre>\n");
      annopw.print("</body>\n</html>");
      annopw.close();
    }
    pw.print("</body>\n</html>");
    pw.close();

  }

  public static String shortlabelVSdatabasetype(SequenceDatabaseType dbtype) {
    if (dbtype.equals(SequenceDatabaseType.IPI)) {
      return "ipi";
    }
    else if (dbtype.equals(SequenceDatabaseType.SWISSPROT)) {
      return "uniprot";
    }
    else {
      return null;
    }
  }

  public static void main(String[] args) throws IOException,
      MarshalException, ValidationException {
    if (args.length != 4) {
      System.err.println(
          "ShowAnnotationByEntriesXMLAndDbLink EntriesXML DatabaseLinkSet ResultFile (IPI | NR | SWISSPROT)");
      return;
    }

    if (!new File(args[0]).exists()) {
      System.err.println("The EntriesXMLFile:" + args[0]
                         + " does not exist!");
      return;
    }

    if (!new File(args[1]).exists()) {
      System.err.println("The DatabaseLinkSetFile:" + args[1]
                         + " does not exist!");
      return;
    }

    File out = new File(args[2]);
    if (out.exists()) {
      out.delete();
    }
    else {
      out.createNewFile();
    }

    final BufferedReader entryread = new BufferedReader(new FileReader(
        args[0]));
    final ProteinEntrySet entryset = ProteinEntrySet.unmarshal(entryread);
    final BufferedReader dblinkread = new BufferedReader(new FileReader(
        args[1]));
    final DatabaseLinkSet dblinkset = DatabaseLinkSet.unmarshal(dblinkread);
    SequenceDatabaseType type = SequenceDatabaseType.valueOf(args[3]);
    showEntriesByXMLAndDatabaseLink(entryset, dblinkset, args[2], type);
  }

}
