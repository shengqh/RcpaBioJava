package cn.ac.rcpa.bio.processor;

import cn.ac.rcpa.bio.database.SequenceDatabaseType;
import cn.ac.rcpa.component.JRcpaComboBox;
import cn.ac.rcpa.utils.IFileArgument;

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

public abstract class AbstractFileProcessorByDatabaseTypeUI extends
    AbstractFileProcessorUI {
  private static final String DATABASE_TYPE_KEY = "DATABASE_TYPE";

  private JRcpaComboBox<SequenceDatabaseType> cbDatabaseType;

  public AbstractFileProcessorByDatabaseTypeUI(String title,
      IFileArgument fileArgument) {
    super(title, fileArgument);

    this.cbDatabaseType = new JRcpaComboBox<SequenceDatabaseType>(
        DATABASE_TYPE_KEY, "Database Type : ", SequenceDatabaseType.values(),
        SequenceDatabaseType.IPI);
    addComponent(cbDatabaseType);
  }

  protected SequenceDatabaseType getDatabaseType() {
    return cbDatabaseType.getSelectedItem();
  }
}
