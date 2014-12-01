package cn.ac.rcpa.bio.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.biojava.bio.BioException;
import org.biojava.bio.proteomics.Protease;
import org.biojava.bio.proteomics.ProteaseManager;

import cn.ac.rcpa.utils.RcpaFileUtils;
import cn.ac.rcpa.utils.RcpaStringUtils;

/**
 * <p>
 * Title: Theoretical Digestor
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * 
 * <p>
 * Company: RCPA.SIBS.AC.CN
 * </p>
 * 
 * @author Sheng Quan-Hu (shengqh@gmail.com)
 * @version 1.0
 */
public class RcpaProteaseFactory {
	private static RcpaProteaseFactory instance;

	public static RcpaProteaseFactory getInstance() {
		if (instance == null) {
			instance = new RcpaProteaseFactory();
		}
		return instance;
	}

	private RcpaProteaseFactory() {
		initialize();
	}

	private static File proteaseFile = new File("config/protease.list");

	private void initialize() {
		if (proteaseFile.exists()) {
			try {
				loadProteaseListFromFile(proteaseFile);
			} catch (IOException ex) {
				throw new IllegalStateException(ex.getMessage());
			}
		} else {

		}
	}

	public Protease[] getProteaseList() {
		ArrayList<Protease> resultList = new ArrayList<Protease>();

		Set proteaseSet = ProteaseManager.getAllProteases();
		for (Object obj : proteaseSet) {
			Protease protease = (Protease) obj;
			resultList.add(protease);
		}

		Collections.sort(resultList, new Comparator<Protease>() {
			public int compare(Protease o1, Protease o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});

		return resultList.toArray(new Protease[0]);
	}

	public String[] getProteaseNameList() {
		Protease[] proteases = getProteaseList();

		ArrayList<String> result = new ArrayList<String>();
		for (Protease protease : proteases) {
			result.add(protease.getName());
		}

		return result.toArray(new String[0]);
	}

	public Protease getProteaseByName(String name) throws BioException {
		return ProteaseManager.getProteaseByName(name);
	}

	public Protease createProtease(String name, boolean endo, String cleaveage,
			String uncleaveage) throws BioException {
		String finalName = name;
		int i = 1;
		while (ProteaseManager.registered(finalName)) {
			Protease p = ProteaseManager.getProteaseByName(name);
			if (RcpaStringUtils.charEquals(p.getCleaveageResidues().seqString(),
					cleaveage)
					&& RcpaStringUtils.charEquals(p.getNotCleaveResidues().seqString(),
							uncleaveage) && p.isEndoProtease() == endo) {
				return p;
			}
			finalName = name + "_" + i;
			i++;
		}
		return ProteaseManager.createProtease(cleaveage, endo, uncleaveage,
				finalName);
	}

	public Protease[] loadProteaseListFromFile(File proteaseFile)
			throws IOException {
		String[] lines = RcpaFileUtils.readFile(proteaseFile.getAbsolutePath());
		List<Protease> result = new ArrayList<Protease>();
		for (int i = 0; i < lines.length; i++) {
			Protease curProtease = parse(lines[i]);
			if (curProtease != null) {
				result.add(curProtease);
			}
		}
		return (Protease[]) result.toArray(new Protease[0]);
	}

	public Protease parse(String line) {
		Protease result = null;
		String[] infos = line.split("\t+");
		if (infos.length == 5) {
			try {
				if (infos[3].equals("-")) {
					infos[3] = "";
				}
				if (infos[4].equals("-")) {
					infos[4] = "";
				}
				if (infos[3].length() == 0 && infos[4].length() == 0) {
					return result;
				}

				result = createProtease(infos[1], infos[2].equals("1"), infos[3],
						infos[4]);
			} catch (BioException ex) {
				ex.printStackTrace();
				throw new IllegalStateException(line + " is not a valid protease!");
			}
		}

		return result;
	}

	public void saveProteaseListToFile(File proteaseFile, Protease[] proteases)
			throws IOException {
		PrintStream ps = new PrintStream(new FileOutputStream(proteaseFile));
		try {
			for (int i = 0; i < proteases.length; i++) {
				ps.println(i
						+ ".\t"
						+ proteases[i].getName()
						+ "\t"
						+ (proteases[i].isEndoProtease() ? 1 : 0)
						+ "\t"
						+ (proteases[i].getCleaveageResidues().length() == 0 ? "-"
								: proteases[i].getCleaveageResidues().seqString())
						+ "\t"
						+ (proteases[i].getNotCleaveResidues().length() == 0 ? "-"
								: proteases[i].getNotCleaveResidues().seqString()));
			}
		} finally {
			ps.close();
		}
	}
}
