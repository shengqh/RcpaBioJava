/*
 * �������� 2004-8-24
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
package cn.ac.rcpa.bio.database.link;

import java.io.FileWriter;
import java.io.IOException;

import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;

/**
 * @author long
 *
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class DatabaseLinkPaser {
	
	public static void putxml() throws IOException, MarshalException, ValidationException {
		final FileWriter result = new FileWriter("D:/database/testdblink.xml");
		final DatabaseLinkSet dblinkset = new DatabaseLinkSet();
		final DatabaseLink dblink = new DatabaseLink();
		dblink.setShortlabel("uniprot");
		dblink.setSearch_url_ascii("http://www.ebi.uniprot.org/entry/${ac}?format=text&ascii");
		dblink.setSearch_url("http://www.ebi.uniprot.org/entry/${ac}");
		dblink.setTerm("UniProt protein sequence database");
		dblink.addUrl(0,"aa");
		dblinkset.addDatabaseLink(dblink);
		dblinkset.marshal(result);
	}

}
