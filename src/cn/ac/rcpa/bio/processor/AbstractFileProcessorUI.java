package cn.ac.rcpa.bio.processor;

import javax.swing.JOptionPane;

import cn.ac.rcpa.bio.AbstractUI;
import cn.ac.rcpa.component.JRcpaFileField;
import cn.ac.rcpa.models.IInterruptable;
import cn.ac.rcpa.utils.IFileArgument;
import cn.ac.rcpa.utils.RcpaStringUtils;

/**
 * <p>Title: RCPA Package</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: RCPA.SIBS.AC.CN</p>
 * @author not attributable
 * @version 1.0
 */

public abstract class AbstractFileProcessorUI
    extends AbstractUI implements IThreadCaller {
  /**
	 * 
	 */
	private static final long serialVersionUID = 7127537614216801434L;
private String originFileKey;
  private boolean isProcessorInterruptable = false;
  private JRcpaFileField originFileText; 

  public AbstractFileProcessorUI(String title, IFileArgument fileArgument) {
    super(title);

    this.originFileKey = RcpaStringUtils.getXMLCompartibleName(fileArgument.
        getFileDescription());
    
    this.originFileText = new JRcpaFileField(originFileKey, fileArgument, true);
    
    addComponent(originFileText);
  }

  private FileProcessorThread fpt;

  @Override
  protected void doRealGo(){
    try {
      IFileProcessor processor = getProcessor();
      isProcessorInterruptable = processor instanceof IInterruptable;
//      ComponentMessageShower cms = new ComponentMessageShower(this);
      fpt = new FileProcessorThread(processor,
                                    originFileText.getFilename(), this);
      fpt.start();
    }
    catch (Exception ex) {
      ex.printStackTrace();
      JOptionPane.showMessageDialog(this, "Error : " + ex.getMessage(),
                                    "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  public void threadStarted(Thread currentThread){
    if (isProcessorInterruptable){
      btnGo.setText("Cancel");
    }
    else {
      btnGo.setEnabled(false);
    }
    btnGo.updateUI();
  }

  public void threadFinished(Thread currentThread) {
    btnGo.setText("Go");
    btnGo.setEnabled(true);
    btnGo.updateUI();
    fpt = null;
  }

  protected abstract IFileProcessor getProcessor() throws Exception;
}
