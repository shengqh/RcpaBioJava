package cn.ac.rcpa.bio;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import cn.ac.rcpa.component.IRcpaComponent;
import cn.ac.rcpa.component.JRcpaComponentList;
import cn.ac.rcpa.models.MessageType;
import cn.ac.rcpa.utils.GUIUtils;
import cn.ac.rcpa.utils.RcpaFileUtils;
import cn.ac.rcpa.utils.XMLFile;

public abstract class AbstractUI extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6558658006518097358L;

	private String title;

	private String optionFileName = RcpaFileUtils.getConfigFile(this.getClass());

	private JRcpaComponentList componentList;

	protected JPanel pnlButton = new JPanel();

	protected JButton btnGo = new JButton("Go");

	protected JButton btnClose = new JButton("Close");

	protected GridBagLayout gbLayout = new GridBagLayout();

	public AbstractUI(String title) {
		super();

		this.title = title;
		this.componentList = new JRcpaComponentList();
	}

	private void loadOption() {
		try {
			XMLFile option = new XMLFile(optionFileName);
			componentList.loadFromFile(option);
		} catch (Exception ex) {
			System.err.println("Load option error : " + ex.getMessage());
		}
	}

	private void saveOption() {
		try {
			XMLFile option = new XMLFile(optionFileName);
			componentList.saveToFile(option);
			option.saveToFile();
		} catch (Exception ex) {
			System.err.println("Save option error : " + ex.getMessage());
		}
	}

	void jbInit() throws Exception {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle(title);
		this.getContentPane().setLayout(gbLayout);

		int totalColumn = 2;
		if (totalColumn < componentList.columnCountNeeded()) {
			totalColumn = componentList.columnCountNeeded();
		}

		int iNextRow = componentList.addTo(this.getContentPane(), 0, totalColumn);

		this.getContentPane().add(
				pnlButton,
				new GridBagConstraints(0, iNextRow, totalColumn, 1, 1.0, 0.0,
						GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10,
								10, 10, 10), 0, 0));

		btnClose.setMaximumSize(new Dimension(80, 25));
		btnClose.setMinimumSize(new Dimension(80, 25));
		btnClose.setPreferredSize(new Dimension(80, 25));
		btnClose.setRolloverEnabled(false);
		btnClose.addActionListener(new AbstractUI_btnClose_actionAdapter(this));

		btnGo.setMaximumSize(new Dimension(80, 25));
		btnGo.setMinimumSize(new Dimension(80, 25));
		btnGo.setPreferredSize(new Dimension(80, 25));
		btnGo.setRolloverEnabled(false);
		btnGo.addActionListener(new AbstractUI_btnGo_actionAdapter(this));

		pnlButton.add(btnGo, null);
		pnlButton.add(btnClose, null);
	}

	void btnClose_actionPerformed(ActionEvent e) {
		dispose();
	}

	void btnGo_actionPerformed(ActionEvent e) {
		try {
			componentList.validate();
		} catch (IllegalAccessException ex) {
			JOptionPane.showMessageDialog(this, "Error : " + ex.getMessage(),
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		saveOption();

		doRealGo();
	}

	abstract protected void doRealGo();

	public void showSelf() {
		try {
			jbInit();
			loadOption();
		} catch (Exception ex) {
			ex.printStackTrace();
			return;
		}
		this.setSize(getPerfectWidth(), getPerfectHeight());
		GUIUtils.setFrameDesktopCentre(this);
		this.setVisible(true);
	}

	protected final void addComponent(IRcpaComponent comp) {
		componentList.addComponent(comp);
	}

	protected int getPerfectWidth() {
		return 800;
	}

	protected int getPerfectHeight() {
		return (int) (50 + componentList.getPreferredHeight() + pnlButton
				.getPreferredSize().getHeight());
	}

	public void showMessage(MessageType messageType, String message) {
		if (messageType == MessageType.ERROR_MESSAGE) {
			JOptionPane.showMessageDialog(this, message, "Error",
					JOptionPane.ERROR_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(this, message, "Information",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public static void main(String[] args) {
		new AbstractUI("Test") {
			private static final long serialVersionUID = 1L;

			@Override
			protected void doRealGo() {
			}
		}.showSelf();
	}
}

class AbstractUI_btnClose_actionAdapter implements
		java.awt.event.ActionListener {
	AbstractUI adaptee;

	AbstractUI_btnClose_actionAdapter(AbstractUI adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.btnClose_actionPerformed(e);
	}
}

class AbstractUI_btnGo_actionAdapter implements java.awt.event.ActionListener {
	AbstractUI adaptee;

	AbstractUI_btnGo_actionAdapter(AbstractUI adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.btnGo_actionPerformed(e);
	}
}
