package cn.ac.rcpa.chem;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Atom {
  public static final String VALID_ATOMS = "HCNOPS";

  private static AtomIsotopic[] H_HEAVY = { new AtomIsotopic(2.0141017,
      0.00015, 1) };

  public static final Atom H = new Atom('H', new AtomIsotopic(1.0078250,
      0.99985, 0), H_HEAVY);

  private static AtomIsotopic[] C_HEAVY = { new AtomIsotopic(13.003354, 0.0111,
      1) };

  public static final Atom C = new Atom('C', new AtomIsotopic(12.00000, 0.9889,
      0), C_HEAVY);

  private static AtomIsotopic[] N_HEAVY = { new AtomIsotopic(15.000108,
      0.00366, 1) };

  public static final Atom N = new Atom('N', new AtomIsotopic(14.003073,
      0.99634, 0), N_HEAVY);

  private static AtomIsotopic[] O_HEAVY = {
      new AtomIsotopic(16.999131, 0.00038, 1),
      new AtomIsotopic(17.999160, 0.00200, 2) };

  public static final Atom O = new Atom('O', new AtomIsotopic(15.994914,
      0.99762, 0), O_HEAVY);

  public static final Atom P = new Atom('P', new AtomIsotopic(30.973761,
      1.0000, 0), new AtomIsotopic[0]);

  private static AtomIsotopic[] S_HEAVY = {
      new AtomIsotopic(32.971458, 0.0075, 1),
      new AtomIsotopic(33.967866, 0.0421, 2),
      new AtomIsotopic(35.967080, 0.0002, 4) };

  public static final Atom S = new Atom('S', new AtomIsotopic(31.972070,
      0.9502, 0), S_HEAVY);

  private char name;

  private AtomIsotopic mono_isotopic;

  private AtomIsotopic[] heavy_isotopics;

  private static Atom[] atoms = null;

  public static Atom getAtom(char name) {
    if (atoms == null) {
      atoms = new Atom[128];
      atoms['H'] = H;
      atoms['C'] = C;
      atoms['N'] = N;
      atoms['O'] = O;
      atoms['P'] = P;
      atoms['S'] = S;
    }
    if (atoms[name] == null) {
      throw new IllegalArgumentException("Atom " + name
          + " has not be defined!");
    }
    return atoms[name];
  }

  public char getName() {
    return name;
  }

  public AtomIsotopic[] getHeavy_isotopics() {
    return heavy_isotopics;
  }

  public AtomIsotopic getMono_isotopic() {
    return mono_isotopic;
  }

  private Atom(char name, AtomIsotopic mono_isotopic,
      AtomIsotopic[] heavy_isotopics) {
    this.name = name;
    this.mono_isotopic = mono_isotopic;
    this.heavy_isotopics = heavy_isotopics;
  }

  /**
   * @see java.lang.Object#equals(Object)
   */
  @Override
  public boolean equals(Object object) {
    if (!(object instanceof Atom)) {
      return false;
    }
    Atom rhs = (Atom) object;
    return new EqualsBuilder().append(this.name, rhs.name).isEquals();
  }

  /**
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return new HashCodeBuilder(-219269699, -307293393).append(this.name)
        .toHashCode();
  }

}
