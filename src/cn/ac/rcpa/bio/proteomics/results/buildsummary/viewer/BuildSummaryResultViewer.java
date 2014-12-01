package cn.ac.rcpa.bio.proteomics.results.buildsummary.viewer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import cn.ac.rcpa.bio.models.IObjectRemoveEvent;
import cn.ac.rcpa.bio.proteomics.IIdentifiedPeptide;
import cn.ac.rcpa.bio.proteomics.IIdentifiedProtein;
import cn.ac.rcpa.bio.proteomics.IIdentifiedProteinGroup;
import cn.ac.rcpa.bio.proteomics.IdentifiedResultIOFactory;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryProteinGroup;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.BuildSummaryResult;
import cn.ac.rcpa.bio.proteomics.results.buildsummary.io.BuildSummaryResultWriter;
import cn.ac.rcpa.bio.proteomics.utils.IdentifiedResultUtils;
import cn.ac.rcpa.filter.IFilter;
import cn.ac.rcpa.utils.GUIUtils;

public class BuildSummaryResultViewer extends JFrame {
	public static String version = "1.0.0";

	BuildSummaryResult sequestResult;

	private static final String title = "BuildSummaryResultViewer -- qhsheng@sibs.ac.cn";

	JToolBar barTools = new JToolBar();

	JButton btnLoad = new JButton();

	JButton btnClose = new JButton();

	BorderLayout borderLayout1 = new BorderLayout();

	JFileChooser chooser = new JFileChooser();

	JSplitPane pnlCenter = new JSplitPane();

	DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(
			"Sequest Protein Groups");

	JScrollPane pnlTree = new JScrollPane();

	JTree tvwGroups = new JTree(rootNode);

	SequestResultViewer_tvwGroups_treeSelectionAdapter treeSelectionAdapter;

	BuildSummaryProteinGroupViewer viewer = new BuildSummaryProteinGroupViewer();

	private ArrayList<IFilter<IIdentifiedProteinGroup>> groupFilters = new ArrayList<IFilter<IIdentifiedProteinGroup>>();

	private String currentFilename;

	IObjectRemoveEvent removeEvent = new IObjectRemoveEvent() {
		public void objectRemoved(Object fromObject, Object removedObject) {
			if (fromObject == this) {
				return;
			}
			DefaultMutableTreeNode node = findNode(getRootNode(), removedObject);

			if (node != null) {
				deleteNode(node);
			}
		}
	};

	JPopupMenu pmResult = new JPopupMenu();

	JMenuItem mniRebuild = new JMenuItem();

	JMenuItem mniRemove = new JMenuItem();

	JButton btnSave = new JButton();

	DefaultMutableTreeNode findNode(DefaultMutableTreeNode node, Object obj) {
		if (!node.isRoot() && obj == node.getUserObject()) {
			return node;
		}

		for (Enumeration nodes = node.children(); nodes.hasMoreElements();) {
			DefaultMutableTreeNode result = findNode((DefaultMutableTreeNode) nodes
					.nextElement(), obj);
			if (result != null) {
				return result;
			}
		}
		return null;
	}

	private boolean showGroup(IIdentifiedProteinGroup group) {
		for (IFilter<IIdentifiedProteinGroup> filter : groupFilters) {
			if (!filter.accept(group)) {
				return false;
			}
		}
		return true;
	}

	public BuildSummaryResultViewer() {
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(new Dimension(800, 600));
		this.setTitle(title);
		this.getContentPane().setLayout(borderLayout1);

		chooser.setSelectedFile(new File("."));

		btnLoad.setMaximumSize(new Dimension(100, 27));
		btnLoad.setMinimumSize(new Dimension(100, 27));
		btnLoad.setPreferredSize(new Dimension(100, 27));
		btnLoad.setActionCommand("jButton1");
		btnLoad.setText("Load From File...");
		btnLoad.addActionListener(new SequestResultViewer_btnLoad_actionAdapter(
				this));
		btnClose.setMaximumSize(new Dimension(100, 27));
		btnClose.setMinimumSize(new Dimension(100, 27));
		btnClose.setPreferredSize(new Dimension(100, 27));
		btnClose.setText("Close");
		btnClose.addActionListener(new SequestResultViewer_btnClose_actionAdapter(
				this));
		pnlTree.setMinimumSize(new Dimension(200, 16));
		pnlTree.setPreferredSize(new Dimension(200, 16));
		treeSelectionAdapter = new SequestResultViewer_tvwGroups_treeSelectionAdapter(
				this);
		tvwGroups.addTreeSelectionListener(treeSelectionAdapter);
		mniRemove.setText("Remove Current Group");
		mniRemove
				.addActionListener(new SequestResultViewer_mniRemove_actionAdapter(this));
		mniRebuild.setText("Rebuild Group Index");
		mniRebuild
				.addActionListener(new SequestResultViewer_mniRebuild_actionAdapter(
						this));
		btnSave.setMaximumSize(new Dimension(100, 27));
		btnSave.setMinimumSize(new Dimension(100, 27));
		btnSave.setPreferredSize(new Dimension(100, 27));
		btnSave.setText("Save To File...");
		btnSave.addActionListener(new SequestResultViewer_btnSave_actionAdapter(
				this));
		barTools.add(btnLoad, null);
		barTools.add(btnSave, null);
		barTools.add(btnClose, null);

		pnlTree.getViewport().add(tvwGroups, null);
		pnlCenter.add(pnlTree, JSplitPane.LEFT);
		pnlCenter.add(viewer, JSplitPane.RIGHT);

		this.getContentPane().add(barTools, BorderLayout.NORTH);
		this.getContentPane().add(pnlCenter, BorderLayout.CENTER);

		viewer.addObjectRemovedEvent(removeEvent);

		// groupFilters.add(new MultipleProteinFilter());
		// groupFilters.add(new ProteinDeltaCnFilter());

		pmResult.add(mniRebuild);
		pmResult.add(mniRemove);

		tvwGroups.addMouseListener(new SequestResultPopupListener(this));

		// btnRunHPPP.setVisible(false);
		// loadFromFile("D:\\Projects\\HPPP\\SM.noredundant");
	}

	void btnClose_actionPerformed(ActionEvent e) {
		dispose();
	}

	private DefaultTreeModel getTreeModel() {
		return (DefaultTreeModel) tvwGroups.getModel();
	}

	private DefaultMutableTreeNode getRootNode() {
		return (DefaultMutableTreeNode) getTreeModel().getRoot();
	}

	private void focusNode(TreeNode node) {
		if (node != null) {
			tvwGroups.removeTreeSelectionListener(treeSelectionAdapter);
			TreePath visiblePath = new TreePath(getTreeModel().getPathToRoot(node));
			tvwGroups.makeVisible(visiblePath);
			tvwGroups.setSelectionPath(visiblePath);
			tvwGroups.addTreeSelectionListener(treeSelectionAdapter);

			while (node.getParent() != getRootNode()) {
				node = node.getParent();
			}
			BuildSummaryProteinGroup group = (BuildSummaryProteinGroup) ((DefaultMutableTreeNode) node)
					.getUserObject();
			try {
				if (viewer.getGroup() != group) {
					viewer.load(group);
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	void loadFromFile(String filename) {
		try {
			filename = filename.trim();
			sequestResult = IdentifiedResultIOFactory.getBuildSummaryResultReader()
					.read(filename);
			if (new File(filename + ".fasta").exists()) {
				IdentifiedResultUtils.fillSequenceByName(sequestResult
						.getProteins(), filename + ".fasta");
			}
			setCurrentFilename(filename);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		reloadTree();
	}

	private void reloadTree() {
		clearTree();
		for (int i = 0; i < sequestResult.getProteinGroupCount(); i++) {
			if (showGroup(sequestResult.getProteinGroup(i))) {
				addGroupToTree(sequestResult.getProteinGroup(i), getRootNode());
			}
		}
		focusNode(getRootNode().getFirstChild());
	}

	private void deleteNodeChildren(DefaultMutableTreeNode node) {
		int numberOfChildren = node.getChildCount();

		ArrayList<DefaultMutableTreeNode> children = new ArrayList<DefaultMutableTreeNode>();

		for (int i = 0; i < numberOfChildren; i++) {
			DefaultMutableTreeNode currentChild = (DefaultMutableTreeNode) node
					.getChildAt(i);
			children.add(currentChild);
		}

		for (int i = 0; i < numberOfChildren; i++) {
			DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) children
					.get(i);
			deleteNode(currentNode);
		}
	}

	private void deleteNode(DefaultMutableTreeNode node) {
		deleteNodeChildren(node);
		getTreeModel().removeNodeFromParent(node);
	}

	private void clearTree() {
		deleteNodeChildren(getRootNode());
	}

	void btnLoad_actionPerformed(ActionEvent e) {
		if (currentFilename != null) {
			chooser.setSelectedFile(new File(currentFilename));
		}
		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			loadFromFile(chooser.getSelectedFile().getAbsolutePath());
		}
	}

	private DefaultMutableTreeNode addObjectToTree(Object obj,
			DefaultMutableTreeNode parentNode) {
		DefaultMutableTreeNode result = new DefaultMutableTreeNode(obj);
		getTreeModel().insertNodeInto(result, parentNode,
				parentNode.getChildCount());
		return result;
	}

	private void addGroupToTree(IIdentifiedProteinGroup group,
			DefaultMutableTreeNode parentNode) {
		DefaultMutableTreeNode node = addObjectToTree(group, parentNode);
		for (int i = 0; i < group.getProteinCount(); i++) {
			addProteinHitToTree(group.getProtein(i), node);
		}
	}

	private void addProteinHitToTree(IIdentifiedProtein proteinHit,
			DefaultMutableTreeNode parentNode) {
		DefaultMutableTreeNode node = addObjectToTree(proteinHit, parentNode);
		for (int i = 0; i < proteinHit.getPeptideCount(); i++) {
			addPeptideHitToTree(proteinHit.getPeptide(i), node);
		}
	}

	private void addPeptideHitToTree(IIdentifiedPeptide peptideHit,
			DefaultMutableTreeNode parentNode) {
		addObjectToTree(peptideHit, parentNode);
	}

	void tvwGroups_valueChanged(TreeSelectionEvent e) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tvwGroups
				.getLastSelectedPathComponent();

		if (node == null || node.isRoot()) {
			return;
		}
		focusNode(node);
	}

	public static void main(String[] args) {
		BuildSummaryResultViewer viewer = new BuildSummaryResultViewer();
		GUIUtils.setFrameDesktopCentre(viewer);
		viewer.setVisible(true);
	}

	void mniRemove_actionPerformed(ActionEvent e) {
		if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this,
				"Are you sure to delete current group?", "Confirm",
				JOptionPane.YES_NO_OPTION)) {

			DefaultMutableTreeNode node = (DefaultMutableTreeNode) tvwGroups
					.getLastSelectedPathComponent();
			DefaultMutableTreeNode focus = node.getNextSibling();
			if (focus == null) {
				focus = node.getPreviousSibling();
			}
			deleteNode(node);
			sequestResult.removeProteinGroup((BuildSummaryProteinGroup) node
					.getUserObject());
			focusNode(focus);
		}
	}

	void mniRebuild_actionPerformed(ActionEvent e) {
		rebuildGroupIndex();
	}

	private void rebuildGroupIndex() {
		sequestResult = getSequestResultFromTree();
		sequestResult.rebuildGroupIndex();
		reloadTree();
	}

	private BuildSummaryResult getSequestResultFromTree() {
		BuildSummaryResult result = new BuildSummaryResult();
		for (DefaultMutableTreeNode node = (DefaultMutableTreeNode) rootNode
				.getFirstChild(); node != null; node = node.getNextSibling()) {
			result.addProteinGroup((BuildSummaryProteinGroup) node.getUserObject());
		}
		return result;
	}

	void btnSave_actionPerformed(ActionEvent e) {
		chooser.setSelectedFile(new File(currentFilename));
		if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			try {
				saveToFile(chooser.getSelectedFile().getAbsolutePath());
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Save to file error : "
						+ ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void saveToFile(String filename) throws Exception {
		sequestResult = getSequestResultFromTree();
		BuildSummaryResultWriter.getInstance().write(filename, sequestResult);
		setCurrentFilename(filename);
	}

	private void setCurrentFilename(String filename) {
		setTitle(title + " : " + filename);
		currentFilename = filename;
	}

}

class SequestResultViewer_btnClose_actionAdapter implements
		java.awt.event.ActionListener {
	BuildSummaryResultViewer adaptee;

	SequestResultViewer_btnClose_actionAdapter(BuildSummaryResultViewer adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.btnClose_actionPerformed(e);
	}
}

class SequestResultViewer_btnLoad_actionAdapter implements
		java.awt.event.ActionListener {
	BuildSummaryResultViewer adaptee;

	SequestResultViewer_btnLoad_actionAdapter(BuildSummaryResultViewer adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.btnLoad_actionPerformed(e);
	}
}

class SequestResultViewer_tvwGroups_treeSelectionAdapter implements
		javax.swing.event.TreeSelectionListener {
	BuildSummaryResultViewer adaptee;

	SequestResultViewer_tvwGroups_treeSelectionAdapter(
			BuildSummaryResultViewer adaptee) {
		this.adaptee = adaptee;
	}

	public void valueChanged(TreeSelectionEvent e) {
		adaptee.tvwGroups_valueChanged(e);
	}
}

class MultipleProteinFilter implements IFilter<IIdentifiedProteinGroup> {
	public boolean accept(IIdentifiedProteinGroup group) {
		return group.getProteinCount() > 1;
	}

	public String getType() {
		return "MultipleProtein";
	}
}

class SequestResultPopupListener extends MouseAdapter {
	BuildSummaryResultViewer viewer;

	SequestResultPopupListener(BuildSummaryResultViewer viewer) {
		this.viewer = viewer;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		maybeShowPopup(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		maybeShowPopup(e);
	}

	private void maybeShowPopup(MouseEvent e) {
		if (e.isPopupTrigger()) {
			if (e.getComponent() instanceof JTree) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) viewer.tvwGroups
						.getLastSelectedPathComponent();
				viewer.mniRemove.setEnabled(node.getParent() == viewer.rootNode);
			}
			viewer.pmResult.show(e.getComponent(), e.getX(), e.getY());
		}
	}
}

class SequestResultViewer_mniRemove_actionAdapter implements
		java.awt.event.ActionListener {
	BuildSummaryResultViewer adaptee;

	SequestResultViewer_mniRemove_actionAdapter(BuildSummaryResultViewer adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.mniRemove_actionPerformed(e);
	}
}

class SequestResultViewer_mniRebuild_actionAdapter implements
		java.awt.event.ActionListener {
	BuildSummaryResultViewer adaptee;

	SequestResultViewer_mniRebuild_actionAdapter(BuildSummaryResultViewer adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.mniRebuild_actionPerformed(e);
	}
}

class SequestResultViewer_btnSave_actionAdapter implements
		java.awt.event.ActionListener {
	BuildSummaryResultViewer adaptee;

	SequestResultViewer_btnSave_actionAdapter(BuildSummaryResultViewer adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.btnSave_actionPerformed(e);
	}
}
