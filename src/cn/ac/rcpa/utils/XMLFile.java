package cn.ac.rcpa.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.output.Format.TextMode;

/**
 * <p>
 * Title: RCPA Package
 * </p>
 * 
 * <p>
 * Description: XML File Class
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
public class XMLFile {
	private File file;

	private Document doc;

	/**
	 * XMLFile construction The XML file passed into the constructor must be
	 * writtable.
	 * 
	 * @param filename
	 *            the full path of the xml file.
	 * @throws JDOMException
	 */
	public XMLFile(String filename) throws IOException, JDOMException {
		this.file = new File(filename);
		if (file.exists()) {
			SAXBuilder builder = new SAXBuilder();
			doc = builder.build(file);
		} else {
			doc = new Document();
			doc.setRootElement(new Element("Root"));
		}
	}

	public Document getDocument() {
		return this.doc;
	}

	public Element getElement(Element root, String leafRelativePath) {
		Element result = root;

		String[] propName = parsePropertyName(leafRelativePath);
		for (int i = 0; i < propName.length; i++) {
			result = result.getChild(propName[i]);
			if (result == null) {
				break;
			}
		}
		return result;
	}

	public Element getElement(String leafAbsolutePath) {
		return getElement(doc.getRootElement(), leafAbsolutePath);
	}

	@SuppressWarnings("unchecked")
	public Element[] getElements(Element root, String parentRelativePath,
			String childName) {
		Element parent = getElement(root, parentRelativePath);
		if (parent == null) {
			return new Element[0];
		}

		return (Element[]) parent.getChildren(childName)
				.toArray(new Element[0]);
	}

	public Element[] getElements(String parentAbsolutePath, String childName) {
		return getElements(doc.getRootElement(), parentAbsolutePath, childName);
	}

	@SuppressWarnings("unchecked")
	public Element[] getChildren(Element root, String parentRelativePath) {
		Element element = getElement(root, parentRelativePath);

		if (element == null) {
			return new Element[0];
		}

		return (Element[]) element.getChildren().toArray(new Element[0]);
	}

	public Element[] getChildren(String parentAbsolutePath) {
		return getChildren(doc.getRootElement(), parentAbsolutePath);
	}

	public String[] getChildrenNames(Element root, String leafRelativePath) {
		Element[] children = getChildren(root, leafRelativePath);

		String[] childrenNames = new String[children.length];
		for (int i = 0; i < children.length; i++) {
			childrenNames[i] = children[i].getName();
		}

		return childrenNames;
	}

	public String[] getChildrenNames(String leafAbsolutePath) {
		return getChildrenNames(doc.getRootElement(), leafAbsolutePath);
	}

	private String[] parsePropertyName(String name) {
		if (null == name || 0 == name.trim().length()) {
			return new String[0];
		} else {
			return name.split("\\.");
		}
	}

	public String getElementValue(Element root, String leafRelativePath,
			String defaultValue) {
		Element element = getElement(root, leafRelativePath);
		if (element == null) {
			return defaultValue;
		}

		return element.getTextTrim();
	}

	public String getElementValue(String leafAbsolutePath, String defaultValue) {
		return getElementValue(doc.getRootElement(), leafAbsolutePath,
				defaultValue);
	}

	public String[] getElementValues(Element root, String parentRelativePath,
			String leafName) {
		Element parent = getElement(root, parentRelativePath);
		if (parent == null) {
			return new String[0];
		}

		List element = parent.getChildren(leafName);
		String[] result = new String[element.size()];
		for (int i = 0; i < element.size(); i++) {
			result[i] = ((Element) element.get(i)).getTextTrim();
		}

		return result;
	}

	public String[] getElementValues(String parentAbsolutePath, String leafName) {
		return getElementValues(doc.getRootElement(), parentAbsolutePath,
				leafName);
	}

	/**
	 * 本函数从XMLFile指定的Element中读取Map<String,String>。
	 * 
	 * @param root
	 *            XMLFile的根元素
	 * @param leafRelativePath
	 *            欲读取元素与根元素的相对路径
	 * @param mapName
	 *            欲读取的Map的名称
	 * @return valueMap 读取的Map
	 */
	public Map<String, String> getElementMap(Element root,
			String leafRelativePath, String mapName) {
		Map<String, String> result = new HashMap<String, String>();
		Element parent = getElement(root, leafRelativePath);
		if (parent == null) {
			return result;
		}

		List elements = parent.getChildren(mapName);

		for (int i = 0; i < elements.size(); i++) {
			final Element elem = (Element) elements.get(i);
			result.put(elem.getAttributeValue("key"), elem
					.getAttributeValue("value"));
		}

		return result;
	}

	public Map<String, String> getElementMap(String leafRelativePath,
			String mapName) {
		return getElementMap(doc.getRootElement(), leafRelativePath, mapName);
	}

	public Attribute getAttribute(Element root, String leafRelativePath,
			String attName) {
		Element element = getElement(root, leafRelativePath);
		if (element == null) {
			return null;
		}

		return element.getAttribute(attName);
	}

	public Attribute getAttribute(String leafAbsolutePath, String attName) {
		return getAttribute(doc.getRootElement(), leafAbsolutePath, attName);
	}

	public void setElementValue(Element root, String leafRelativePath,
			String value) {
		Element element = this.findCreate(root, leafRelativePath);
		element.setText(value);
	}

	/**
	 * 设置一个元素值。如果指定的元素不存在，就自动创建该元素。
	 * 
	 * @param leafAbsolutePath
	 *            需要赋值的元素名称，格式为 X.Y.Z
	 * @param value
	 *            元素值
	 * 
	 * @throws IOException
	 */
	public void setElementValue(String leafAbsolutePath, String value) {
		setElementValue(doc.getRootElement(), leafAbsolutePath, value);
	}

	public void setElementValues(Element root, String parentRelativePath,
			String leafName, String[] values) {
		Element parent = this.findCreate(root, parentRelativePath);
		parent.removeChildren(leafName);
		for (int i = 0; i < values.length; i++) {
			Element child = new Element(leafName);
			parent.addContent(child);
			child.setText(values[i]);
		}
	}

	public void setElementValues(String parentAbsolutePath, String leafName,
			String[] values) {
		setElementValues(doc.getRootElement(), parentAbsolutePath, leafName,
				values);
	}

	/**
	 * 设置一个元素的Attribute值。如果指定的元素不存在，就自动创建该元素。
	 * 
	 * @param leafRelativePath
	 *            需要赋值的元素名称，格式为 X.Y.Z
	 * @param attr
	 *            Attribute名称
	 * @param value
	 *            元素值
	 * 
	 * @throws IOException
	 */
	public void setAttribute(Element root, String leafRelativePath,
			String attr, String value) {
		Element element = this.findCreate(root, leafRelativePath);
		element.removeAttribute(attr);
		element.setAttribute(attr, value);
	}

	public void setAttribute(String leafAbsolutePath, String attr, String value) {
		setAttribute(doc.getRootElement(), leafAbsolutePath, attr, value);
	}

	/**
	 * 本函数将一个Map<String,String>保存到XMLFile指定的Element中。
	 * 
	 * @param root
	 *            XMLFile的根元素
	 * @param leafRelativePath
	 *            欲添加元素与根元素的相对路径
	 * @param mapName
	 *            欲添加的Map的名称
	 * @param valueMap
	 *            欲添加的Map
	 */
	public void setElementMap(Element root, String leafRelativePath,
			String mapName, Map<String, String> valueMap) {
		Element element = this.findCreate(root, leafRelativePath);
		element.removeChildren(mapName);

		for (String key : valueMap.keySet()) {
			Element annotationElem = new Element(mapName);
			annotationElem.setAttribute("key", key);
			annotationElem.setAttribute("value", valueMap.get(key));
			element.addContent(annotationElem);
		}
	}

	public void setElementMap(String leafRelativePath, String mapName,
			Map<String, String> valueMap) {
		setElementMap(doc.getRootElement(), leafRelativePath, mapName, valueMap);
	}

	/**
	 * helper方法，查找一个指定的元素,如果这个元素不存在，创建它
	 * 
	 * @param leafRelativePath
	 *            元素名称，格式为 X.Y.Z
	 * @return Element 找到就返回这个元素，否则返回创建的新元素
	 */
	private Element findCreate(Element root, String leafRelativePath) {
		// 分解元素的名称
		String[] propName = parsePropertyName(leafRelativePath);

		Element element = root;
		// 遍历搜索匹配的元素，不存在则创建它
		for (int i = 0; i < propName.length; i++) {
			if (element.getChild(propName[i]) == null) {
				// 自动创建该元素
				element.addContent(new Element(propName[i]));
			}
			element = element.getChild(propName[i]);
		}
		return element;
	}

	public void deleteElement(Element root, String leafRelativePath) {
		Element element = getElement(root, leafRelativePath);
		if (element != null && element.getParentElement() != null) {
			element.getParentElement().removeChild(leafRelativePath);
		}
	}

	/**
	 * 删除指定的元素，如果该元素不存在，不做任何事情
	 * 
	 * @param leafAbsolutePath
	 *            要删除的元素的名称，格式为 X.Y.Z
	 * 
	 */
	public void deleteElement(String leafAbsolutePath) {
		deleteElement(doc.getRootElement(), leafAbsolutePath);
	}

	public void deleteAttribute(Element root, String leafRelativePath,
			String attr) {
		Element element = getElement(root, leafRelativePath);
		if (element != null) {
			element.removeAttribute(attr);
		}
	}

	/**
	 * 删除指定的元素的Attribute，如果该元素或者Attribute不存在，不做任何事情
	 * 
	 * @param leafAbsolutePath
	 *            元素的名称，格式为 X.Y.Z
	 * @param attr
	 *            Attribute的名称
	 */
	public void deleteAttribute(String leafAbsolutePath, String attr)
			throws IOException {
		deleteAttribute(doc.getRootElement(), leafAbsolutePath, attr);
	}

	// Using saveToFile("GB2312") to save chinese
	public void saveToFile(String encoding) throws IOException {
		if (file.getParentFile() != null && !file.getParentFile().exists()
				&& !file.getParentFile().mkdirs()) {
			throw new IOException("Error create directory: "
					+ file.getParentFile().getAbsolutePath());
		}

		OutputStream out = null;
		// Write data out to a temporary file first.
		File tempFile = null;
		try {
			tempFile = new File(file.getAbsolutePath() + ".tmp");
			// Use JDOM's XMLOutputter to do the writing and formatting. The
			// file should always come out pretty-printed.
			Format fm = Format.getPrettyFormat();
			fm.setEncoding(encoding);
			fm.setLineSeparator("\n");
			fm.setTextMode(TextMode.TRIM);
			XMLOutputter outputter = new XMLOutputter(fm);
			out = new BufferedOutputStream(new FileOutputStream(tempFile));
			outputter.output(doc, out);
		} finally {
			out.close();
		}

		if (file.exists() && !file.delete()) {
			throw new IOException("Error deleting property file: "
					+ file.getAbsolutePath());
		}
		// Rename the temp file. The delete and rename won't be an
		// automic operation, but we should be pretty safe in general.
		// At the very least, the temp file should remain in some form.
		if (!tempFile.renameTo(file)) {
			throw new IOException("Error renaming temp file from "
					+ tempFile.getAbsolutePath() + " to "
					+ file.getAbsolutePath());
		}

	}

	/**
	 * Saves the properties to disk as an XML document. A temporary file is used
	 * during the writing process for maximum safety.
	 */
	public void saveToFile() throws FileNotFoundException, IOException {
		saveToFile("UTF-8");
	}
}
