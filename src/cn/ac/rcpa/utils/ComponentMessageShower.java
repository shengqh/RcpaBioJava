package cn.ac.rcpa.utils;

import java.awt.Component;

import javax.swing.JOptionPane;

import cn.ac.rcpa.models.IMessageShower;
import cn.ac.rcpa.models.MessageType;

public class ComponentMessageShower  implements IMessageShower{
  private Component comp;
  public ComponentMessageShower(Component comp) {
    this.comp = comp;
  }

  /**
   * showMessage
   *
   * @param messageType int
   * @param message String
   */
  public void showMessage(MessageType messageType, String message) {
    if (messageType == MessageType.ERROR_MESSAGE){
      JOptionPane.showMessageDialog(comp,
                                    message,
                                    "Error", JOptionPane.ERROR_MESSAGE);
    }
    else{
      JOptionPane.showMessageDialog(comp,
                                    message,
                                    "Information", JOptionPane.INFORMATION_MESSAGE);
    }
  }
}
