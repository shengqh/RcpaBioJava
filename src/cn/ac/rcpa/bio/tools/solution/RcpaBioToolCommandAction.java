package cn.ac.rcpa.bio.tools.solution;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class RcpaBioToolCommandAction
    extends AbstractAction {
  private IRcpaBioToolCommand command;

  public RcpaBioToolCommandAction(IRcpaBioToolCommand command) {
    super(command.getCaption());
    this.command = command;
  }

  public void actionPerformed(ActionEvent e) {
    command.run();
  }
}
