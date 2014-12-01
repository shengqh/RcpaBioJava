package cn.ac.rcpa.chem;

public class AtomIsotopic {
  private double mass;
  private double percent;
  private int diff_to_mono;

  public double getMass() {
    return mass;
  }

  public int getDiff_to_mono() {
    return diff_to_mono;
  }

  public void setPercent(double percent) {
    this.percent = percent;
  }

  public void setMass(double mass) {
    this.mass = mass;
  }

  public void setDiff_to_mono(int diff_to_mono) {
    this.diff_to_mono = diff_to_mono;
  }

  public double getPercent() {
    return percent;
  }

  public AtomIsotopic(double mass, double percent, int diff_to_mono) {
    this.mass = mass;
    this.percent = percent;
    this.diff_to_mono = diff_to_mono;
  }
}
