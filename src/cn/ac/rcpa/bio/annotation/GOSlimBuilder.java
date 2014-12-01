package cn.ac.rcpa.bio.annotation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

import org.jdom.JDOMException;

import cn.ac.rcpa.utils.RcpaFileUtils;

/**
 * <p>
 * Title: RCPA Package
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: RCPA.SIBS.AC.CN
 * </p>
 * 
 * @author Sheng Quan-Hu (shengqh@gmail.com)
 * @version 1.1
 */

public class GOSlimBuilder {
	public GOSlimBuilder() {
	}

	public static void main(String[] args) throws Exception {
		// File totalFile = new
		// File("d:\\database\\go\\go_200503-termdb.rdf-xml");
		// File mappingFile = new File("d:\\database\\go\\goslim_goa_rcpa.obo");
		File totalFile = new File("");
		File mappingFile = new File("");

		if (args.length >= 2) {
			totalFile = new File(args[0]);
			mappingFile = new File(args[1]);
		}

		if (!totalFile.exists() || !mappingFile.exists()) {
			printPrompt();
			return;
		}

		build(totalFile.getAbsolutePath(), mappingFile.getAbsolutePath());
	}

	public static String build(String totalFile, String mappingFile)
			throws Exception {
		GOEntryMap totalMap = getTotalGOEntry(totalFile);
		GOEntryMap mappingMap = getMappingGOEntry(mappingFile);

		String resultFile = RcpaFileUtils.changeExtension(mappingFile, "slim");

		PrintWriter pw = new PrintWriter(new FileWriter(resultFile));
		PrintWriter pwTemp = new PrintWriter(
				new FileWriter(resultFile + ".tmp"));
		String[] accessions = totalMap.getAccessions();
		String[] mappingAccessions = mappingMap.getAccessions();
		for (int i = 0; i < accessions.length; i++) {
			ArrayList<String> parents = new ArrayList<String>();

			// 如果是mappingAccession中一个，父类就是自己
			for (int j = 0; j < mappingAccessions.length; j++) {
				if (accessions[i].equals(mappingAccessions[j])) {
					parents.add(accessions[i]);
					break;
				}
			}

			// 否则，在mappingAccession中找自己的父类
			if (parents.size() == 0) {
				for (int j = 0; j < mappingAccessions.length; j++) {
					if (totalMap.isParent(accessions[i], mappingAccessions[j])) {
						parents.add(mappingAccessions[j]);
					}
				}
			}

			String[] parent_accessions = (String[]) parents
					.toArray(new String[0]);
			ArrayList<String> noredundant_parents = new ArrayList<String>();

			for (int j = 0; j < parent_accessions.length; j++) {
				boolean bParent = false;
				for (int k = 0; k < parent_accessions.length; k++) {
					if (j == k) {
						continue;
					}

					if (mappingMap.isParent(parent_accessions[k],
							parent_accessions[j])) {
						bParent = true;
						break;
					}
				}

				if (!bParent) {
					noredundant_parents.add(parent_accessions[j]);
				}
			}

			final String tmpInfo = accessions[i] + "\t"
					+ totalMap.getEntry(accessions[i]).getName() + "\t"
					+ noredundant_parents + "\t" + parents;
			pwTemp.println(tmpInfo);
			System.out.println(tmpInfo);
			for (Iterator iter = noredundant_parents.iterator(); iter.hasNext();) {
				String parent = (String) iter.next();
				pw.println(accessions[i] + "\t" + parent + "\t"
						+ mappingMap.getEntry(parent).getNamespace() + "\t"
						+ mappingMap.getEntry(parent).getName());
			}
		}
		pw.close();
		pwTemp.close();

		return resultFile;
	}

	/**
	 * printPrompt
	 */
	private static void printPrompt() {
		System.err
				.println("GOSlimGenerator go_XXXXXX_termdb.xml goslim_XXX.obo");
	}

	private static GOEntryMap getMappingGOEntry(String filename)
			throws FileNotFoundException, IOException {
		System.out.print("Getting mapping entries from " + filename + " ... ");
		GOEntryMap goMap = GOEntryMap.getGOEntryMapFromOBOFile(filename);
		System.out.println("succeed.");
		return goMap;
	}

	private static GOEntryMap getTotalGOEntry(String filename) throws Exception {
		System.out.print("Getting total entries from " + filename + " ... ");
		GOEntryMap goMap = GOEntryMap.getGOEntryMapFromXMLFile(filename);
		System.out.println("succeed.");
		return goMap;
	}
}
