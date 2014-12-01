/*
 * Created on 2005-12-25
 *
 *                    Rcpa development code
 *
 * Author Sheng QuanHu(qhsheng@sibs.ac.cn / shengqh@gmail.com)
 * This code is developed by RCPA Bioinformatic Platform
 * http://www.proteomics.ac.cn/
 *
 */
package cn.ac.rcpa;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;

public class ParserComposite<T> extends ArrayList<IParser<T>> implements
		IParser<T> {
	private static final long serialVersionUID = 4713183892942380265L;

	private String splitToken;

	public ParserComposite(String splitToken) {
		this.splitToken = splitToken;

	}

	public String getValue(T name) {
		ArrayList<String> values = new ArrayList<String>();
		for (IParser<T> parser : this) {
			values.add(parser.getValue(name));
		}
		return StringUtils.join(values.iterator(), splitToken);
	}

	public String getTitle() {
		ArrayList<String> titles = new ArrayList<String>();
		for (IParser<T> parser : this) {
			titles.add(parser.getTitle());
		}
		return StringUtils.join(titles.iterator(), splitToken);
	}

	public String getDescription() {
		return "Collection of parser";
	}

}
