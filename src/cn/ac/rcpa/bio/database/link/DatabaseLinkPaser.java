/*
 * 创建日期 2004-8-24
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package cn.ac.rcpa.bio.database.link;

import java.io.FileWriter;
import java.io.IOException;

import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;

/**
 * @author long
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
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
