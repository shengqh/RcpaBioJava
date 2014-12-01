/**
 * Created on 2003-11-27
 */
package cn.ac.rcpa.bio.aminoacid;

import java.text.DecimalFormat;

/**
 * @author Sheng Quanhu (shengqh@gmail.com/shengqh@263.net)
 */

public class Aminoacid {
  private double averageMass;

  private String description;

  private double monoMass;

  private int nominalMass;

  private double hydropathicity;

  private char oneLetter;

  private String threeLetter;

  private boolean visible;

  public double getHydropathicity() {
    return hydropathicity;
  }

  public char getOneLetter() {
    return oneLetter;
  }

  public int getNominalMass() {
    return nominalMass;
  }

  public boolean isVisible() {
    return visible;
  }

  public String getDescription() {
    return description;
  }

  public String getThreeLetter() {
    return threeLetter;
  }

  public double getMonoMass() {
    return monoMass;
  }

  public void setHydropathicity(double hydropathicity) {
    this.hydropathicity = hydropathicity;
  }

  public void setAverageMass(double averageMass) {
    this.averageMass = averageMass;
  }

  public void setOneLetter(char oneLetter) {
    this.oneLetter = oneLetter;
  }

  public void setVisible(boolean visible) {
    this.visible = visible;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * 
   * @param threeLetter
   *          amino acid's three-charactor-coded name, like "Gly"
   */
  public void setThreeLetter(String threeLetter) {
    this.threeLetter = threeLetter;
  }

  public void setMonoMass(double monoMass) {
    this.monoMass = monoMass;
    this.nominalMass = (int) (monoMass + 0.5);
  }

  public double getAverageMass() {
    return averageMass;
  }

  public void addModification(double addMass) {
    monoMass += addMass;
    averageMass += addMass;
  }

  public Aminoacid() {
    super();
    init();
  }

  private void init() {
    oneLetter = ' ';
    threeLetter = "   ";
    setMonoMass(0.0);
    averageMass = 0.0;
    description = "";
    visible = false;
    hydropathicity = 0.0;
  }

  /**
   * initialize amino acid to default value
   */
  public void clear() {
    init();
  }

  /**
   * @return whether two amino acids are same by checking their
   *         one-character-coded name.
   */
  @Override
  public boolean equals(Object arg0) {
    if (arg0 == this) {
      return true;
    }

    if (!(arg0 instanceof Aminoacid)) {
      return false;
    }

    Aminoacid source = (Aminoacid) arg0;
    return oneLetter == source.oneLetter;
  }

  @Override
  public int hashCode() {
    return oneLetter;
  }

  public void reset(char oneLetter, String threeLetter, double monoMass,
      double averageMass, double hydropathicity, String description,
      boolean visible) {
    this.oneLetter = oneLetter;
    setThreeLetter(threeLetter);
    setMonoMass(monoMass);
    this.averageMass = averageMass;
    this.description = description;
    this.hydropathicity = hydropathicity;
    this.visible = visible;
  }

  /**
   * @return amino acid's total information
   */
  @Override
  public String toString() {
    final DecimalFormat df1 = new DecimalFormat("0.00");
    final DecimalFormat df2 = new DecimalFormat("0.0000");

    StringBuilder res = new StringBuilder().append(oneLetter).append(' ')
        .append(threeLetter).append(' ').append(df1.format(averageMass))
        .append(' ').append(df2.format(monoMass)).append(' ').append(
            df1.format(hydropathicity)).append(' ').append(description).append(
            ' ');
    if (visible) {
      res.append("Visible");
    } else {
      res.append("Unvisible");
    }
    return res.toString();
  }
}
