package cn.ac.rcpa.bio.proteomics;

import java.text.DecimalFormat;

public class FollowCandidatePeptide {
  private String sequence;
  private double score;
  private double deltaScore;

  public FollowCandidatePeptide(String sequence, double score,
                                double deltaScore) {
    this.sequence = sequence;
    this.score = score;
    this.deltaScore = deltaScore;
  }

  public String getSequence() {
    return sequence;
  }

  public double getScore() {
    return score;
  }

  public double getDeltaScore() {
    return deltaScore;
  }

  private static DecimalFormat df4 = new DecimalFormat("#0.0000");

  @Override
  public String toString() {
    return sequence + "(" + df4.format( score) + "," + df4.format(deltaScore) + ")";
  }
};
