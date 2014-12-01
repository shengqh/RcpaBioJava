package cn.ac.rcpa.bio.sequest;

public class SequestDynamicModificationEntry {
  private char markChar;
  private double addMass;

  public char getMarkChar() {
    return markChar;
  }

  public double getAddMass() {
    return addMass;
  }

  public void setMarkChar(char markChar) {
    this.markChar = markChar;
  }

  public void setAddMass(double addMass) {
    this.addMass = addMass;
  }

  public SequestDynamicModificationEntry(char markChar, double addMass) {
    this.markChar = markChar;
    this.addMass = addMass;
  }
}
