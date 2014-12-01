package cn.ac.rcpa.chem;

public class SpectrumItem {
  private double mz;
  private double intensity;

  public double getIntensity() {
    return intensity;
  }

  public void setMz(double mz) {
    this.mz = mz;
  }

  public void setIntensity(double intensity) {
    this.intensity = intensity;
  }

  public double getMz() {
    return mz;
  }

  public SpectrumItem() {
  }

  public SpectrumItem(double mz, double intensity) {
    this.mz = mz;
    this.intensity = intensity;
  }
}
