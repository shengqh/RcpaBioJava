package cn.ac.rcpa.bio.processor;

public abstract class AbstractFileProcessor implements IFileProcessor {
  public AbstractFileProcessor(){
  }

  protected boolean interrupted = false;

  public void interrupt(){
    interrupted = true;
  }

  protected void checkInterrupted() throws InterruptedException {
    if (interrupted){
      throw new InterruptedException("User interrupted");
    }
  }
}
