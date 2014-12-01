/*
 * Created on 2006-1-11
 *
 *                    Rcpa development code
 *
 * Author Sheng QuanHu(qhsheng@sibs.ac.cn / shengqh@gmail.com)
 * This code is developed by RCPA Bioinformatic Platform
 * http://www.proteomics.ac.cn/
 *
 */
package cn.ac.rcpa.component;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JComponent;

import cn.ac.rcpa.utils.XMLFile;

/**
 * 这个类是用来做为真实类的包装类,把它可以方便的加入到IRcpaComponent的管理中去。 例如 <code>
 *   grappaContainer = new JScrollPane();
 *   grappaContainer.setMinimumSize(new Dimension(800, 600));
 *   grappaContainer.setPreferredSize(new Dimension(800, 600));
 *   panelProxy = new JRcpaComponentProxy(grappaContainer, 1.0);
 *   addComponent(panelProxy);
 * </code>
 * 
 * @author sqh
 * 
 */
public class JRcpaComponentProxy implements IRcpaComponent {
	private JComponent comp;

	private double weighty;

	private int anchor = GridBagConstraints.CENTER;

	private int fill = GridBagConstraints.HORIZONTAL;

	private int columnWidth = 0;

	public JRcpaComponentProxy(JComponent comp, double weighty, int anchor,
			int fill, int columnWidth) {
		super();
		this.comp = comp;
		this.weighty = weighty;
		this.anchor = anchor;
		this.fill = fill;
		this.columnWidth = columnWidth;
	}

	public JRcpaComponentProxy(JComponent comp, double weighty) {
		super();
		this.comp = comp;
		this.weighty = weighty;
	}

	public JRcpaComponentProxy(JComponent comp) {
		this.comp = comp;
		this.weighty = 0.0;
	}

	public int addTo(Container container, int addToRow, int totalColumnCount) {
		int cWidth = (columnWidth == 0) ? totalColumnCount : columnWidth;
		container.add(comp, new GridBagConstraints(0, addToRow, cWidth,
				1, 1.0, weighty, anchor, fill, new Insets(10, 10, 0, 10), 0, 0));
		return addToRow + 1;
	}

	public void validate() throws IllegalAccessException {
		return;
	}

	public int columnCountNeeded() {
		return 1;
	}

	public void loadFromFile(XMLFile option) {
		return;
	}

	public void saveToFile(XMLFile option) {
		return;
	}

	public double getPreferredHeight() {
		return comp.getPreferredSize().getHeight();
	}

}
