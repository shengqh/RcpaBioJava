package cn.ac.rcpa.bio.database.ebi.protein;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.biojava.bio.Annotation;
import org.biojava.bio.BioException;
import org.biojava.bio.seq.Feature;
import org.biojava.bio.seq.Sequence;
import org.biojava.bio.seq.SequenceIterator;
import org.biojava.bio.seq.io.ReferenceAnnotation;
import org.biojava.bio.seq.io.SeqIOTools;

import cn.ac.rcpa.bio.database.ebi.protein.entry.Db_reference;
import cn.ac.rcpa.bio.database.ebi.protein.entry.Feature_table;
import cn.ac.rcpa.bio.database.ebi.protein.entry.Free_comment;
import cn.ac.rcpa.bio.database.ebi.protein.entry.ProteinEntry;
import cn.ac.rcpa.bio.database.ebi.protein.entry.Reference;
import cn.ac.rcpa.utils.RcpaObjectUtils;

/**
 * 创建日期 2004-5-26 读取IPI/SwissProt的Dat数据库，解析ProteinEntry
 * 
 * @author Li Long, Sheng Quan-Hu
 */
public class ProteinEntryParser {
	static private Pattern pid = Pattern
			.compile("(\\S+)\\s+(\\w+);\\s+(\\S+);\\s+(\\d+)");

	static private Pattern pdtc = Pattern.compile("Created\\)");

	static private Pattern pdts = Pattern.compile("sequence update\\)");

	static private Pattern pdta = Pattern.compile("annotation update\\)");

	// static private Pattern pac = Pattern.compile("(\\S+?);");

	static private Pattern pcc = Pattern.compile("-!-\\s(.*?):\\s(.*)");

	// static private Pattern pnull = Pattern.compile("^\\s+(.*)");

	static private Pattern pMedline = Pattern.compile("MEDLINE=(\\d+)");

	static private Pattern pPudmed = Pattern.compile("PubMed=(\\d+)");

	static private Pattern pDoi = Pattern.compile("DOI=(.+);");

	private SequenceIterator seqI = null;

	public void open(String filename) throws FileNotFoundException {
		seqI = SeqIOTools
				.readSwissprot(new BufferedReader(new FileReader(filename)));
	}

	public boolean hasNext() {
		if (seqI == null) {
			throw new RuntimeException("call open first!");
		}
		return seqI.hasNext();
	}

	public void close() {
		seqI = null;
	}

	public ProteinEntry getNextEntry() throws IOException,
			NoSuchElementException, BioException {
		if (!hasNext()) {
			throw new RuntimeException(
					"call hasNext to ensure there is protein entry first!");
		}

		Sequence swissProteinSeq = seqI.nextSequence();
		final Annotation anno = swissProteinSeq.getAnnotation();
		final ProteinEntry entry = new ProteinEntry();

		parseID(anno, entry);
		parseAC(anno, entry);
		parseDT(anno, entry);
		parseDE(anno, entry);
		parseGN(anno, entry);
		parseOS(anno, entry);
		parseOG(anno, entry);
		parseOC(anno, entry);
		parseOX(anno, entry);
		parseReference(anno, entry);
		parseCC(anno, entry);
		parseDR(anno, entry);
		parseKW(anno, entry);
		parseFT(swissProteinSeq, entry);
		parseSQ(swissProteinSeq, entry);

		return entry;
	}

	/**
	 * @param anno
	 * @param entry
	 */
	private static void parseID(Annotation anno, ProteinEntry entry) {
		String[] id = getProperty(anno, "ID");
		if (id.length == 0) {
			throw new IllegalArgumentException("Annotation has no ID");
		}

		String idstr = id[0];
		Matcher mid = pid.matcher(idstr);
		if (!mid.find()) {
			throw new IllegalArgumentException(idstr
					+ " is not a valid id description");
		}

		entry.setEntry_name(mid.group(1));
		entry.setData_class(mid.group(2));
		entry.setMolecule_type(mid.group(3));
		entry.setSequence_length(Integer.parseInt(mid.group(4)));
	}

	private static void parseAC(Annotation anno, ProteinEntry entry) {
		entry.clearAc_number();

		String[] acarray = getProperty(anno, "swissprot.accessions");
		if (acarray.length == 0) {
			throw new IllegalArgumentException("Annotation has no AC");
		}

		for (String ac : acarray) {
			entry.addAc_number(ac);
		}
	}

	private static void parseDT(Annotation anno, ProteinEntry entry) {
		String[] dtarray = getProperty(anno, "DT");
		if (dtarray.length == 0) {
			throw new IllegalArgumentException("Annotation has not DT");
		}

		entry.setCreate(null);
		entry.setSequence_update(null);
		entry.setAnnotation_update(null);

		for (int i = 0; i < dtarray.length; i++) {
			if (pdtc.matcher(dtarray[i]).find()) {
				entry.setCreate(dtarray[i]);
			} else if (pdts.matcher(dtarray[i]).find()) {
				entry.setSequence_update(dtarray[i]);
			} else if (pdta.matcher(dtarray[i]).find()) {
				entry.setAnnotation_update(dtarray[i]);
			} else {
				throw new IllegalStateException("Unknow DT Line : " + dtarray[i]);
			}
		}
	}

	private static void parseDE(Annotation anno, ProteinEntry entry) {
		String[] dearray = getProperty(anno, "DE");
		String de = unitProperty(dearray);
		entry.setDescription(de);
	}

	private static void parseGN(Annotation anno, ProteinEntry entry) {
		String[] gnarray = getProperty(anno, "GN");
		String gn = unitProperty(gnarray);
		entry.setGene_name(gn);
	}

	private static void parseOS(Annotation anno, ProteinEntry entry) {
		String[] osarray = getProperty(anno, "OS");
		if (osarray.length == 0) {
			throw new IllegalArgumentException("Annotation has not OS");
		}

		String ostemp = unitProperty(osarray);
		entry.setOrganism_species(ostemp.substring(0, ostemp.length() - 1));
	}

	private static void parseOG(Annotation anno, ProteinEntry entry) {
		String[] ogarray = getProperty(anno, "OG");
		String og = unitProperty(ogarray);
		entry.setOrganelle(og);
	}

	private static void parseOC(Annotation anno, ProteinEntry entry) {
		String[] ocarray = getProperty(anno, "OC");
		if (ocarray.length == 0) {
			throw new IllegalArgumentException("Annotation has not OC");
		}

		String octemp = unitProperty(ocarray);
		entry.setOrganism_classification(octemp.substring(0, octemp.length() - 1));
	}

	private static void parseOX(Annotation anno, ProteinEntry entry) {
		String[] oxarray = getProperty(anno, "OX");
		if (oxarray.length == 0) {
			throw new IllegalArgumentException("Annotation has not OX");
		}

		String ox = unitProperty(oxarray);
		entry.setTaxonomy_id(ox.substring(0, ox.length() - 1));
	}

	private static void parseCC(Annotation anno, ProteinEntry entry) {
		entry.clearFree_comment();

		List<String> ccp = new ArrayList<String>(Arrays.asList(getProperty(anno,
				"CC")));
		for (int j = 0; j < ccp.size(); j++) {
			if (ccp.get(j).startsWith("----------")) {
				for (int k = ccp.size() - 1; k >= j; k--) {
					ccp.remove(k);
				}
				break;
			}
		}

		for (int j = ccp.size() - 1; j > 0; j--) {
			if (!ccp.get(j).startsWith("-!-")) {
				ccp.set(j - 1, ccp.get(j - 1) + " " + ccp.get(j).trim());
				ccp.remove(j);
			}
		}

		for (int j = 0; j < ccp.size(); j++) {
			Matcher matcher = pcc.matcher(ccp.get(j));
			matcher.find();
			Free_comment cc = new Free_comment();
			cc.setCc_topic(matcher.group(1));
			cc.setCc_details(matcher.group(2));
			entry.addFree_comment(cc);
		}
	}

	private static void parseDR(Annotation anno, ProteinEntry entry) {
		entry.clearDb_reference();
		String[] drarray = getProperty(anno, "DR");
		for (int i = 0; i < drarray.length; i++) {
			String cuttail = drarray[i].substring(0, drarray[i].length() - 1);
			String[] identifier = cuttail.split("; ");
			Db_reference dr = new Db_reference();
			dr.setDb(identifier[0]);
			if (identifier.length >= 2) {
				dr.setPrimary_identifier(identifier[1]);
				if (identifier.length >= 3) {
					dr.setSecondary_identifier(identifier[2]);
					if (identifier.length >= 4) {
						dr.setTertiary_identifier(identifier[3]);
					}
				}
			}
			entry.addDb_reference(dr);
		}
	}

	public static void parseFT(Sequence seq, ProteinEntry entry) {
		entry.clearFeature_table();

		Iterator it = seq.features();
		while (it.hasNext()) {
			Feature f = (Feature) it.next();
			if (f.getAnnotation().containsProperty("swissprot.featureattribute")) {
				Feature_table ft = new Feature_table();
				ft.setKey_name(f.getType());
				ft.setFt_description((String) f.getAnnotation().getProperty(
						"swissprot.featureattribute"));
				ft.setSequence_from(f.getLocation().getMin());
				ft.setSequence_to(f.getLocation().getMax());
				entry.addFeature_table(ft);
			}
		}
	}

	@SuppressWarnings("unchecked")
	private static void parseReference(Annotation anno, ProteinEntry entry) {
		entry.clearReference();

		if (anno.containsProperty(ReferenceAnnotation.class)) {
			Object refers = anno.getProperty(ReferenceAnnotation.class);
			ArrayList references = new ArrayList();
			if (refers instanceof ArrayList) {
				references = (ArrayList) refers;
			} else {
				references.add(refers);
			}
			for (Iterator iter = references.iterator(); iter.hasNext();) {
				ReferenceAnnotation refAnno = (ReferenceAnnotation) iter.next();
				Reference ref = new Reference();
				ref.setPosition(unitProperty(getProperty(refAnno, "RP")));
				ref.setComment(unitProperty(getProperty(refAnno, "RC")));

				if (refAnno.containsProperty("RX")) {
					String rx = unitProperty(getProperty(refAnno, "RX"));
					Matcher mMedline = pMedline.matcher(rx);
					if (mMedline.find()) {
						ref.setMedline_num(Integer.parseInt(mMedline.group(1)));
					}
					Matcher mPubmed = pPudmed.matcher(rx);
					if (mPubmed.find()) {
						ref.setPubmed_num(Integer.parseInt(mPubmed.group(1)));
					}
					Matcher mDoi = pDoi.matcher(rx);
					if (mDoi.find()) {
						ref.setDoi_num(mDoi.group(1));
					}
				}

				ref.setAuthor(unitProperty(getProperty(refAnno, "RA")));

				if (refAnno.containsProperty("RT")) {
					String title = unitProperty(getProperty(refAnno, "RT"));
					title = title.substring(1, title.length() - 2);
					ref.setTitle(title);
				}

				ref.setLocation(unitProperty(getProperty(refAnno, "RL")));

				entry.addReference(ref);
			}
		}
	}

	private static void parseKW(Annotation anno, ProteinEntry entry) {
		String[] kwarray = getProperty(anno, "KW");
		String kw = unitProperty(kwarray);
		entry.setKeyword(kw);
	}

	private static void parseSQ(Sequence seq, ProteinEntry entry) {
		entry.setSequence(seq.seqString());
	}

	private static String[] getProperty(Annotation an, String key) {
		if (!an.containsProperty(key)) {
			return new String[0];
		}

		Object pro = (Object) an.getProperty(key);

		if (pro instanceof ArrayList) {
			ArrayList lst = (ArrayList) an.getProperty(key);
			return RcpaObjectUtils.asArray(lst, new String[0]);
		}

		return new String[] { pro.toString() };
	}

	private static String unitProperty(String[] unit) {
		if (unit.length == 0) {
			return null;
		}

		StringBuffer sb = new StringBuffer(unit[0]);
		for (int i = 1; i < unit.length; i++) {
			if (unit[i - 1].endsWith("-")) {
				sb.append(unit[i]);
			} else {
				sb.append(" " + unit[i]);
			}
		}
		return sb.toString();
	}

}
