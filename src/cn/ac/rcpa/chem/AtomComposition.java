package cn.ac.rcpa.chem;

public class AtomComposition {
  private char atom;
  private int count;

  public int getCount() {
    return count;
  }

  public void setAtom(char atom) {
    this.atom = atom;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public char getAtom() {
    return atom;
  }

  public AtomComposition() {
  }

  public AtomComposition(char atom, int count) {
    this.atom = atom;
    this.count = count;
  }

  public double getMonoMass(){
    return Atom.getAtom(atom).getMono_isotopic().getMass() * count;
  }
}
