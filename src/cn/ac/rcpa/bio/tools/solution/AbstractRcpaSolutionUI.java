package cn.ac.rcpa.bio.tools.solution;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import cn.ac.rcpa.Constants;
import cn.ac.rcpa.utils.GUIUtils;

public abstract class AbstractRcpaSolutionUI extends JFrame {
	private String title;

	private String version;

	public AbstractRcpaSolutionUI(String title, String version) {
		this.title = title;
		this.version = version;

		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle(Constants.getSQHTitle(title, version));
		this.setJMenuBar(mainMenu);

		addAllCommand();
	}

	protected abstract void addAllCommand();

	JMenuBar mainMenu = new JMenuBar();

	public void addCommand(IRcpaBioToolCommand command) {
		JMenu parent = null;
		for (int i = 0; i < mainMenu.getMenuCount(); i++) {
			if (mainMenu.getMenu(i).getText().equals(command.getMenuNames()[0])) {
				parent = mainMenu.getMenu(i);
				break;
			}
		}

		if (parent == null) {
			parent = new JMenu(command.getMenuNames()[0]);
			mainMenu.add(parent);
		}

		for (int nextMenuIndex = 1; nextMenuIndex < command.getMenuNames().length; nextMenuIndex++) {
			String nextMenuName = command.getMenuNames()[nextMenuIndex];
			JMenu sMenu = null;
			for (int i = 0; i < parent.getItemCount(); i++) {
				if (parent.getItem(i).getText().equals(nextMenuName)) {
					sMenu = (JMenu) parent.getItem(i);
					break;
				}
			}
			if (null == sMenu) {
				sMenu = new JMenu(nextMenuName);
				parent.add(sMenu);
			}

			parent = sMenu;
		}

		JMenuItem currCommand = new JMenuItem(command.getCaption() + " - "
				+ command.getVersion());
		currCommand.addActionListener(new RcpaBioToolCommandAction(command));
		parent.add(currCommand);
	}

	public void showSelf() {
		this.setSize(800, 160);
		GUIUtils.setFrameDesktopCentre(this);
		this.setVisible(true);
	}
}
