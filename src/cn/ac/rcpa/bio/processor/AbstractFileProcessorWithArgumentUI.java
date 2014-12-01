package cn.ac.rcpa.bio.processor;

import cn.ac.rcpa.component.JRcpaTextField;
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

public abstract class AbstractFileProcessorWithArgumentUI extends
    AbstractFileProcessorUI {
  private String argumentNameKey;

  private JRcpaTextField txtArgument;

  public AbstractFileProcessorWithArgumentUI(String title,
      IFileArgument fileArgument, String argumentName) {
    super(title, fileArgument);
    this.argumentNameKey = RcpaStringUtils.getXMLCompartibleName(argumentName);
    this.txtArgument = new JRcpaTextField(argumentNameKey, argumentName, "",
        true);
    addComponent(txtArgument);
  }

  protected String getArgument() {
    return txtArgument.getText();
  }
}
