package cn.ac.rcpa.tools;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import cn.ac.rcpa.utils.GUIUtils;
import cn.ac.rcpa.utils.Pair;
import cn.ac.rcpa.utils.SimplePopupListener;
import cn.ac.rcpa.utils.SpecialSwingFileFilter;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: RCPA.SIBS.AC.CN</p>
 *
 * @author Sheng Quan-Hu
 * @version 1.0.0
 */
public class ClassificationUI
    extends JFrame {
  JFileChooser filechooser = new JFileChooser();
  File defaultUserFile = new File("config/usernames.txt");
  File defaultGroupFile = new File("config/groups.txt");
  JPanel jPanel1 = new JPanel();
  JPanel jPanel2 = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  JButton btnClassification = new JButton();
  JButton btnClose = new JButton();
  JScrollPane jScrollPane1 = new JScrollPane();
  DefaultListModel listModel = new DefaultListModel(); ;
  JList lstUsers = new JList(listModel);
  JScrollPane jScrollPane2 = new JScrollPane();
  DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(
      "RCPA Classification Groups");
  JTree tvwGroups = new JTree(rootNode);
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JPopupMenu pmUser = new JPopupMenu();
  JMenuItem mmiUserAdd = new JMenuItem();
  JMenuItem btnUserDelete = new JMenuItem();
  JMenuItem mmiUserSave = new JMenuItem();
  JMenuItem mmiUserLoad = new JMenuItem();
  ArrayList<Pair<String, ArrayList<String>>> groups = new ArrayList<Pair<String,
      ArrayList<String>>> ();
  JButton btnSave = new JButton();
  JPopupMenu pmGroup = new JPopupMenu();
  JMenuItem mmiGroupAdd = new JMenuItem();
  JMenuItem mmiGroupDelete = new JMenuItem();
  JMenuItem mmiGroupLoad = new JMenuItem();
  JMenuItem mmiGroupSave = new JMenuItem();
  JMenuItem mmiGroupModifyName = new JMenuItem();
  Random random = new Random();

  public ClassificationUI() {
    try {
      jbInit();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    this.getContentPane().setLayout(borderLayout1);
    jPanel1.setLayout(gridBagLayout1);
    btnClose.addActionListener(new CloseActionAdapter(this));
    mmiUserAdd.setText("Add user");
    mmiUserAdd.addActionListener(new AddUserActionAdapter(this));
    btnUserDelete.setText("Delete user");
    btnUserDelete.addActionListener(new DeleteUserActionAdapter(this));
    mmiUserLoad.setText("Load users");
    mmiUserLoad.addActionListener(new LoadUserActionAdapter(this));
    mmiUserSave.setText("Save users");
    mmiUserSave.addActionListener(new SaveUserActionAdapter(this));
    btnClassification.addActionListener(new
                                        ClassifyUserActionAdapter(this));
    btnSave.setText("Save classification result");
    btnSave.addActionListener(new SaveGroupActionAdapter(this));
    mmiGroupSave.addActionListener(new SaveGroupActionAdapter(this));
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    mmiGroupAdd.setText("Add group");
    mmiGroupDelete.setText("Delete group");
    mmiGroupDelete.addActionListener(new
        ClassificationUI_mmiGroupDelete_actionAdapter(this));
    mmiGroupLoad.setText("Load groups");
    mmiGroupLoad.addActionListener(new
                                   ClassificationUI_mmiGroupLoad_actionAdapter(this));
    mmiGroupSave.setText("Save groups");
    tvwGroups.setAutoscrolls(true);
    tvwGroups.setDoubleBuffered(true);
    mmiGroupModifyName.setText("Modify group name");
    mmiGroupModifyName.addActionListener(new
        ClassificationUI_mmiGroupModifyName_actionAdapter(this));
    this.getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);
    this.getContentPane().add(jPanel2, java.awt.BorderLayout.SOUTH);
    btnClassification.setText("Do classification now!");
    btnClose.setText("Ok, it\'s over.");
    jPanel2.add(btnClassification);
    jPanel2.add(btnSave);
    jPanel2.add(btnClose);
    jScrollPane1.getViewport().add(lstUsers);
    jScrollPane2.getViewport().add(tvwGroups);
    jPanel1.add(jScrollPane1, new GridBagConstraints(0, 0, 1, 1, 0.4, 1.0
        , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
        new Insets(10, 10, 10, 10), 0, 0));
    jPanel1.add(jScrollPane2, new GridBagConstraints(1, 0, 1, 1, 0.6, 1.0
        , GridBagConstraints.CENTER, GridBagConstraints.BOTH,
        new Insets(10, 10, 10, 10), 0, 0));
    pmUser.add(mmiUserAdd);
    pmUser.add(btnUserDelete);
    pmUser.addSeparator();
    pmUser.add(mmiUserLoad);
    pmUser.add(mmiUserSave);
    pmGroup.add(mmiGroupAdd);
    pmGroup.add(mmiGroupDelete);
    pmGroup.add(mmiGroupModifyName);
    pmGroup.addSeparator();
    pmGroup.add(mmiGroupLoad);
    pmGroup.add(mmiGroupSave);

    TreeSelectionModel tsm = new DefaultTreeSelectionModel();
    tsm.setSelectionMode(DefaultTreeSelectionModel.SINGLE_TREE_SELECTION);
    tvwGroups.setSelectionModel(tsm);

    MouseListener userPopupListener = new SimplePopupListener(pmUser);
    lstUsers.addMouseListener(userPopupListener);

    MouseListener groupPopupListener = new SimplePopupListener(pmGroup);
    tvwGroups.addMouseListener(groupPopupListener);

    if (defaultUserFile.exists()) {
      loadUsersFromFile(defaultUserFile);
    }

    if (defaultGroupFile.exists()) {
      loadGroupsFromFile(defaultGroupFile);
    }
  }

  private DefaultTreeModel getTreeModel() {
    return (DefaultTreeModel) tvwGroups.getModel();
  }

  private DefaultMutableTreeNode addObjectToTree(Object obj,
                                                 DefaultMutableTreeNode
                                                 parentNode) {
    DefaultMutableTreeNode result = new DefaultMutableTreeNode(obj);
    getTreeModel().insertNodeInto(result, parentNode, parentNode.getChildCount());
    return result;
  }

  private void loadGroupsFromFile(File groupFile) {
    for (int i = rootNode.getChildCount() - 1; i >= 0; i--) {
      getTreeModel().removeNodeFromParent( (DefaultMutableTreeNode) rootNode.
                                          getChildAt(i));
    }

    if (!groupFile.exists()) {
      return;
    }

    try {
      ArrayList<String> groupList = new ArrayList<String> ();
      BufferedReader br = new BufferedReader(new FileReader(groupFile));
      String line;
      while ( (line = br.readLine()) != null) {
        if (line.trim().length() > 0) {
          groupList.add(line.trim().toLowerCase());
        }
      }

      groups.clear();
      for (int i = 0; i < groupList.size(); i++) {
        String[] users = groupList.get(i).split("\t");

        ArrayList<String> userList = new ArrayList<String> ();
        Pair<String, ArrayList<String>> pair = new Pair<String,
            ArrayList<String>> (users[0], userList);
        groups.add(pair);

        for (int j = 1; j < users.length; j++) {
          userList.add(users[j]);
        }
      }

      loadGroupsToTree();

    }
    catch (Exception ex) {
      JOptionPane.showMessageDialog(this, ex.getMessage(), "Error",
                                    JOptionPane.ERROR_MESSAGE);
    }
  }

  private void loadGroupsToTree() {
    for (int i = rootNode.getChildCount() - 1; i >= 0; i--) {
      getTreeModel().removeNodeFromParent( (DefaultMutableTreeNode) rootNode.
                                          getChildAt(i));
    }

    for (int i = 0; i < groups.size(); i++) {
      Pair<String, ArrayList<String>> pair = groups.get(i);
      DefaultMutableTreeNode groupNode = addObjectToTree(pair.fst, rootNode);
      ArrayList<String> users = pair.snd;
      for (int j = 0; j < users.size(); j++) {
        DefaultMutableTreeNode userNode = addObjectToTree(users.get(j),
            groupNode);
        TreePath visiblePath = new TreePath(getTreeModel().getPathToRoot(
            userNode));
        tvwGroups.makeVisible(visiblePath);
      }
    }
  }

  public static void main(String[] args) {
    ClassificationUI frame = new ClassificationUI();
    frame.setSize(800, 420);
    GUIUtils.setFrameDesktopCentre(frame);
    frame.setVisible(true);

  }

  private void loadUsersFromFile(File userFile) {
    if (!userFile.exists()) {
      listModel.clear();
      return;
    }

    try {
      ArrayList<String> names = new ArrayList<String> ();
      BufferedReader br = new BufferedReader(new FileReader(userFile));
      String line;
      while ( (line = br.readLine()) != null) {
        if (line.trim().length() > 0) {
          names.add(line.trim().toLowerCase());
        }
      }

      listModel.clear();
      for (int i = 0; i < names.size(); i++) {
        listModel.addElement(names.get(i));
      }
    }
    catch (Exception ex) {
      JOptionPane.showMessageDialog(this, ex.getMessage(), "Error",
                                    JOptionPane.ERROR_MESSAGE);
    }
  }

  public void closePerformed(ActionEvent e) {
    dispose();
  }

  public void mmiUserAdd_actionPerformed(ActionEvent e) {
    String newUsername;
    if ( (newUsername = JOptionPane.showInputDialog(this, "Input new user name")) != null) {
      newUsername = newUsername.trim().toLowerCase();
      for (int i = 0; i < listModel.size(); i++) {
        if (newUsername.equals(listModel.elementAt(i))) {
          return;
        }
      }

      listModel.addElement(newUsername);
    }
  }

  public void btnUserDelete_actionPerformed(ActionEvent e) {
    int[] indices = lstUsers.getSelectedIndices();
    for (int i = indices.length - 1; i >= 0; i--) {
      listModel.remove(indices[i]);
    }
  }

  public void mmiUserLoad_actionPerformed(ActionEvent e) {
    filechooser.setFileFilter(new SpecialSwingFileFilter("txt",
        "User name list file", false));
    filechooser.setSelectedFile(defaultUserFile.getAbsoluteFile());
    filechooser.setDialogTitle("Browse user name list file :");
    if (filechooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
      loadUsersFromFile(filechooser.getSelectedFile());
    }
  }

  public void mmiUserSave_actionPerformed(ActionEvent e) {
    if (!defaultUserFile.getParentFile().exists()) {
      defaultUserFile.getParentFile().mkdirs();
    }

    filechooser.setFileFilter(new SpecialSwingFileFilter("txt",
        "User name list file", false));
    filechooser.setSelectedFile(defaultUserFile);
    filechooser.setDialogTitle("Save user name list file to:");
    if (filechooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
      saveUsersToFile(filechooser.getSelectedFile());
    }
  }

  private void saveUsersToFile(File userFile) {
    try {
      PrintWriter pw = new PrintWriter(userFile);
      for (int i = 0; i < listModel.size(); i++) {
        pw.println(listModel.elementAt(i));
      }
      pw.close();
      JOptionPane.showMessageDialog(this,
                                    "Save users to " + userFile.getAbsolutePath() +
                                    " succeed!", "Congratulation",
                                    JOptionPane.INFORMATION_MESSAGE);
    }
    catch (Exception ex) {
      JOptionPane.showMessageDialog(this, ex.getMessage(), "Error",
                                    JOptionPane.ERROR_MESSAGE);
    }
  }

  public void btnClassification_actionPerformed(ActionEvent e) {
    if (groups.size() == 0) {
      JOptionPane.showMessageDialog(this, "Setting group first", "Error",
                                    JOptionPane.ERROR_MESSAGE);
      return;
    }

    for (int i = 0; i < groups.size(); i++) {
      for (int j = groups.get(i).snd.size() - 1; j >= 1; j--) {
        groups.get(i).snd.remove(j);
      }
    }

    loadGroupsToTree();

    HashSet<String> assignedUsers = new HashSet<String> ();
    for (int i = 0; i < groups.size(); i++) {
      for (int j = 0; j < groups.get(i).snd.size(); j++) {
        assignedUsers.add(groups.get(i).snd.get(j));
      }
    }

    HashSet<String> users = new HashSet<String> ();
    for (int i = 0; i < listModel.size(); i++) {
      users.add( (String) listModel.elementAt(i));
    }
    users.addAll(assignedUsers);

    int maxUserNumber = users.size() / groups.size();
    int maxFullGroupNumber = users.size() - maxUserNumber * groups.size();
    if (maxFullGroupNumber > 0) {
      maxUserNumber++;
    }
    else {
      maxFullGroupNumber = groups.size();
    }

    users.removeAll(assignedUsers);
    System.out.println(users);

    for (Iterator iter = users.iterator(); iter.hasNext(); ) {
      String user = (String) iter.next();

      ArrayList<Pair<String,
          ArrayList<String>>> unFullGroups = new ArrayList<Pair<String,
          ArrayList<String>>> ();
      for (int j = 0; j < groups.size(); j++) {
        if (groups.get(j).snd.size() < maxUserNumber) {
          unFullGroups.add(groups.get(j));
        }
      }

      if (unFullGroups.size() == 0) {
        JOptionPane.showMessageDialog(this, user + " cannot be assigned",
                                      "Error",
                                      JOptionPane.INFORMATION_MESSAGE);
        break;
      }
      int groupIndex = random.nextInt(unFullGroups.size());
      unFullGroups.get(groupIndex).snd.add(user);

      loadGroupsToTree();
//      JOptionPane.showMessageDialog(this, user + " is assigned to group " + unFullGroups.get(groupIndex).fst, "Congratulation",
//                                    JOptionPane.INFORMATION_MESSAGE);

      System.out.println(user + " is assigned to group " +
                         unFullGroups.get(groupIndex).fst);

      int nowFullGroupNumber = 0;
      for (int j = 0; j < groups.size(); j++) {
        if (groups.get(j).snd.size() >= maxUserNumber) {
          nowFullGroupNumber++;
        }
      }

      if (nowFullGroupNumber == maxFullGroupNumber) {
        maxUserNumber--;
        maxFullGroupNumber = groups.size();
      }
    }
  }

  public void saveGroupActionPerformed(ActionEvent e) {
    if (!defaultGroupFile.getParentFile().exists()) {
      defaultGroupFile.getParentFile().mkdirs();
    }

    filechooser.setFileFilter(new SpecialSwingFileFilter("txt",
        "Group list file", false));
    filechooser.setSelectedFile(defaultGroupFile.getAbsoluteFile());
    filechooser.setDialogTitle("Save group information to:");
    if (filechooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
      saveGroupsToFile(filechooser.getSelectedFile());
    }
  }

  private void saveGroupsToFile(File groupFile) {
    try {
      PrintWriter pw = new PrintWriter(groupFile);
      for (int i = 0; i < groups.size(); i++) {
        Pair<String, ArrayList<String>> pair = groups.get(i);
        pw.print(pair.fst);
        ArrayList<String> users = pair.snd;
        for (int j = 0; j < users.size(); j++) {
          pw.print("\t" + users.get(j));
        }
        pw.println();
      }
      pw.close();
    }
    catch (Exception ex) {
      JOptionPane.showMessageDialog(this, ex.getMessage(), "Error",
                                    JOptionPane.ERROR_MESSAGE);
    }
  }

  public void mmiGroupLoad_actionPerformed(ActionEvent e) {
    filechooser.setFileFilter(new SpecialSwingFileFilter("txt",
        "Group list file", false));
    filechooser.setSelectedFile(defaultGroupFile.getAbsoluteFile());
    filechooser.setDialogTitle("Browse group list file :");
    if (filechooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
      loadGroupsFromFile(filechooser.getSelectedFile());
    }
  }

  private DefaultMutableTreeNode getGroupNode(DefaultMutableTreeNode node){
    if (node == null || node.isRoot()){
      return null;
    }

    while (node.getLevel() != 1){
      node = node.getPreviousNode();
    }

    return node;
  }

  public void mmiGroupDelete_actionPerformed(ActionEvent e) {
    if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this,
        "Are you sure to delete those groups?",
        "Confirm",
        JOptionPane.YES_NO_OPTION)) {
      DefaultMutableTreeNode node = getGroupNode((DefaultMutableTreeNode) tvwGroups.
          getLastSelectedPathComponent());
      if (node != null){
        getTreeModel().removeNodeFromParent(node);
      }

      assignTreeToGroups();
    }
  }

  private void assignTreeToGroups() {
    groups.clear();
    for(int i = 0;i < rootNode.getChildCount();i++){
      DefaultMutableTreeNode groupNode = (DefaultMutableTreeNode)rootNode.getChildAt(i);
      ArrayList<String> users = new ArrayList<String>();
      Pair<String, ArrayList<String>> pair = new Pair<String,ArrayList<String>>(groupNode.toString(), users);
      groups.add(pair);
      for(int j = 0;j < groupNode.getChildCount();j++){
        users.add(groupNode.getChildAt(j).toString());
      }
    }
  }

  public void mmiGroupModifyName_actionPerformed(ActionEvent e) {
    DefaultMutableTreeNode node = getGroupNode((DefaultMutableTreeNode) tvwGroups.
        getLastSelectedPathComponent());
    if (node != null){
      String newGroupname = node.toString();
      if ( (newGroupname = JOptionPane.showInputDialog (this,
          "Input new group name", newGroupname)) != null) {
        newGroupname = newGroupname.trim().toLowerCase();
        node.setUserObject(newGroupname);
      }
    }
    assignTreeToGroups();
  }
}

class ClassificationUI_mmiGroupModifyName_actionAdapter
    implements ActionListener {
  private ClassificationUI adaptee;
  ClassificationUI_mmiGroupModifyName_actionAdapter(ClassificationUI adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.mmiGroupModifyName_actionPerformed(e);
  }
}

class ClassificationUI_mmiGroupLoad_actionAdapter
    implements ActionListener {
  private ClassificationUI adaptee;
  ClassificationUI_mmiGroupLoad_actionAdapter(ClassificationUI adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.mmiGroupLoad_actionPerformed(e);
  }
}

class ClassificationUI_mmiGroupDelete_actionAdapter
    implements ActionListener {
  private ClassificationUI adaptee;
  ClassificationUI_mmiGroupDelete_actionAdapter(ClassificationUI adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.mmiGroupDelete_actionPerformed(e);
  }
}

class SaveGroupActionAdapter
    implements ActionListener {
  private ClassificationUI adaptee;
  SaveGroupActionAdapter(ClassificationUI adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.saveGroupActionPerformed(e);
  }
}

class ClassifyUserActionAdapter
    implements ActionListener {
  private ClassificationUI adaptee;
  ClassifyUserActionAdapter(ClassificationUI adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {

    adaptee.btnClassification_actionPerformed(e);
  }
}

class LoadUserActionAdapter
    implements ActionListener {
  private ClassificationUI adaptee;
  LoadUserActionAdapter(ClassificationUI adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.mmiUserLoad_actionPerformed(e);
  }
}

class SaveUserActionAdapter
    implements ActionListener {
  private ClassificationUI adaptee;
  SaveUserActionAdapter(ClassificationUI adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.mmiUserSave_actionPerformed(e);
  }
}

class DeleteUserActionAdapter
    implements ActionListener {
  private ClassificationUI adaptee;
  DeleteUserActionAdapter(ClassificationUI adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.btnUserDelete_actionPerformed(e);
  }
}

class AddUserActionAdapter
    implements ActionListener {
  private ClassificationUI adaptee;
  AddUserActionAdapter(ClassificationUI adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.mmiUserAdd_actionPerformed(e);
  }
}

class CloseActionAdapter
    implements ActionListener {
  private ClassificationUI adaptee;
  CloseActionAdapter(ClassificationUI adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.closePerformed(e);
  }
}
