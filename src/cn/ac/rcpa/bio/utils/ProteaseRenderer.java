package cn.ac.rcpa.bio.utils;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import org.biojava.bio.proteomics.Protease;

/**
 * <p>Title: RCPA Package</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: RCPA.SIBS.AC.CN</p>
 *
 * @author Sheng Quan-Hu
 * @version 1.0
 */
public class ProteaseRenderer
    extends JLabel implements ListCellRenderer {
  public ProteaseRenderer(){
    super();
    setOpaque(true);
  }

  public Component getListCellRendererComponent(JList list, Object value,
                                                int index, boolean isSelected,
                                                boolean cellHasFocus) {
    Protease protease = (Protease) value;
    if (isSelected) {
      setBackground(list.getSelectionBackground());
      setForeground(list.getSelectionForeground());
    }
    else {
      setBackground(list.getBackground());
      setForeground(list.getForeground());
    }


    setText(getProteaseCaption(protease));
    return this;
  }

  public static String getProteaseCaption(Protease protease){
    String result = protease.getName();
    if (protease.isEndoProtease()){
      result = result + "    1";
    }
    else{
      result = result + "    0";
    }

    if (protease.getCleaveageResidues().length() == 0){
      result = result + "    -";
    }
    else{
      result = result + "    " + protease.getCleaveageResidues().seqString();
    }

    if (protease.getNotCleaveResidues().length() == 0){
      result = result + "    -";
    }
    else{
      result = result + "    " + protease.getNotCleaveResidues().seqString();
    }
    return result;
  }
}
