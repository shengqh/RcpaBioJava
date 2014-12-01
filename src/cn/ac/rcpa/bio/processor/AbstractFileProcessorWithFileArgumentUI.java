package cn.ac.rcpa.bio.processor;

import cn.ac.rcpa.component.JRcpaFileField;
import cn.ac.rcpa.utils.IFileArgument;
import cn.ac.rcpa.utils.RcpaStringUtils;

/**
 * <p>
 * Title: RCPA Package
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: RCPA.SIBS.AC.CN
 * </p>
 * 
 * @author Sheng QuanHu
 * @version 1.0
 */

public abstract class AbstractFileProcessorWithFileArgumentUI extends
    AbstractFileProcessorUI {
  private String argumentNameKey;

  private JRcpaFileField argumentFile;

  public AbstractFileProcessorWithFileArgumentUI(String title,
      IFileArgument fileArgument, IFileArgument processorFileArgument) {
    super(title, fileArgument);
    this.argumentNameKey = RcpaStringUtils
        .getXMLCompartibleName(processorFileArgument.getFileDescription());
    this.argumentFile = new JRcpaFileField(argumentNameKey,
        processorFileArgument, true);
    addComponent(argumentFile);
  }

  protected String getArgument() {
    return argumentFile.getFilename();
  }
}
