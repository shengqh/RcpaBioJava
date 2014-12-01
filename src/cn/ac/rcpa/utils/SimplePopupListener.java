package cn.ac.rcpa.utils;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPopupMenu;

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
public class SimplePopupListener
    extends MouseAdapter {
  JPopupMenu popup;
  public SimplePopupListener(JPopupMenu popup) {
    this.popup = popup;
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
      popup.show(e.getComponent(),
                 e.getX(), e.getY());
    }
  }
}
