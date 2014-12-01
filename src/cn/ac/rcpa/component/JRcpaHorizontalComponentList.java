package cn.ac.rcpa.component;

import java.awt.Container;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.ac.rcpa.utils.XMLFile;

public class JRcpaHorizontalComponentList implements IRcpaComponent {
	private List<IRcpaComponent> componentList = new ArrayList<IRcpaComponent>();

	public JRcpaHorizontalComponentList() {
	}

	public void addComponent(IRcpaComponent comp) {
		if (!componentList.contains(comp)) {
			componentList.add(comp);
		}
	}

	public List<IRcpaComponent> getComponents(){
		return Collections.unmodifiableList(componentList);
	}
	
	public void removeComponent(IRcpaComponent comp) {
		if (componentList.contains(comp)) {
			componentList.remove(comp);
		}
	}

	public int addTo(Container container, int addToRow, int totalColumnCount) {
		int result = 0;
		for (IRcpaComponent comp : componentList) {
			int nextRow = comp.addTo(container, addToRow, totalColumnCount);
			if (result < nextRow) {
				result = nextRow;
			}
		}
		return result;
	}

	public void validate() throws IllegalAccessException {
		for (IRcpaComponent comp : componentList) {
			comp.validate();
		}
	}

	public int columnCountNeeded() {
		int result = 0;
		for (IRcpaComponent comp : componentList) {
			if(result < comp.columnCountNeeded()){
				result = comp.columnCountNeeded();
			}
		}
		return result;
	}

	public void loadFromFile(XMLFile option) {
		for (IRcpaComponent comp : componentList) {
			comp.loadFromFile(option);
		}
	}

	public void saveToFile(XMLFile option) {
		for (IRcpaComponent comp : componentList) {
			comp.saveToFile(option);
		}
	}

	public double getPreferredHeight() {
		double result = 0;
		for (IRcpaComponent comp : componentList) {
			if (result < comp.getPreferredHeight()) {
				result = comp.getPreferredHeight();
			}
		}
		return result;
	}
}
