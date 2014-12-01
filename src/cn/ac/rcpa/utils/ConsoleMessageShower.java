package cn.ac.rcpa.utils;

import cn.ac.rcpa.models.IMessageShower;
import cn.ac.rcpa.models.MessageType;

public class ConsoleMessageShower implements IMessageShower{
  private static ConsoleMessageShower instance = null;

  public static ConsoleMessageShower getInstance(){
    if (instance == null){
      instance = new ConsoleMessageShower();
    }
    return instance;
  }

  private ConsoleMessageShower() { }

  public void showMessage(MessageType messageType, String message){
    switch(messageType){
      case ERROR_MESSAGE:
        System.err.println(message);
        break;
      case INFO_MESSAGE:
        System.out.println(message);
        break;
      default:
        System.out.println(message);
    }
  }
}
