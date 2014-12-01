package cn.ac.rcpa.bio.sequest;

public class SequestEnzyme {
  private String enzymeName;
  private int offset;
  private String cleaveAt;
  private String uncleaveAt;

  public String getEnzymeName() {
    return enzymeName;
  }

  public int getOffset() {
    return offset;
  }

  public String getCleaveAt() {
    return cleaveAt;
  }

  public void setUncleaveAt(String uncleaveAt) {
    this.uncleaveAt = uncleaveAt;
  }

  public void setEnzymeName(String enzymeName) {
    this.enzymeName = enzymeName;
  }

  public void setOffset(int offset) {
    this.offset = offset;
  }

  public void setCleaveAt(String cleaveAt) {
    this.cleaveAt = cleaveAt;
  }

  public String getUncleaveAt() {
    return uncleaveAt;
  }

  public SequestEnzyme(String enzymeName,
                       int offset,
                       String cleaveAt,
                       String uncleaveAt){
    this.enzymeName = enzymeName;
    this.offset = offset;
    this.cleaveAt = cleaveAt;
    this.uncleaveAt = uncleaveAt;
  }
}
