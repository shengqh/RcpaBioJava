package cn.ac.rcpa.utils;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class GUIUtils {
  private GUIUtils() {
  }

  public static void setFrameDesktopCentre(JFrame frame) {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = frame.getSize();
    if (frameSize.height > screenSize.height) {
      frameSize.height = screenSize.height;
    }
    if (frameSize.width > screenSize.width) {
      frameSize.width = screenSize.width;
    }
    frame.setLocation( (screenSize.width - frameSize.width) / 2,
                      (screenSize.height - frameSize.height) / 2);
  }

  public static void removeAllActionListeners(JButton button){
    ActionListener[] listeners = button.getActionListeners();
    for (int i = 0; i < listeners.length; i++) {
      button.removeActionListener(listeners[i]);
    }
  }
}
