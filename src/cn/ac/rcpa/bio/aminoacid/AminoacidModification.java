package cn.ac.rcpa.bio.aminoacid;

public class AminoacidModification {
  private char aminoacid;
  private double addMass;

  public double getAddMass() {
    return addMass;
  }

  public char getAminoacid() {
    return aminoacid;
  }

  public void setAddMass(double addMass) {
    this.addMass = addMass;
  }

  public void setAminoacid(char aminoacid) {
    this.aminoacid = aminoacid;
  }

  public void init(char aminoacid, double addMass){
    this.aminoacid = aminoacid;
    this.addMass = addMass;
  }

  public AminoacidModification(char aminoacid, double addMass) {
    this.aminoacid = aminoacid;
    this.addMass = addMass;
  }
}
