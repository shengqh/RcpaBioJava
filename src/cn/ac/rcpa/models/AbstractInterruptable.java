package cn.ac.rcpa.models;

public class AbstractInterruptable implements IInterruptable{
  public AbstractInterruptable() {
  }

  protected boolean interrupted = false;

  public void interrupt() {
    interrupted = true;
  }
}
