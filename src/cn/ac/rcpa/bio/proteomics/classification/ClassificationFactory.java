/*
 * Created on 2005-1-18
 *
 *                    Rcpa development code
 *
 * Author Sheng QuanHu(qhsheng@sibs.ac.cn / shengqh@gmail.com)
 * This code is developed by RCPA Bioinformatic Platform
 * http://www.proteomics.ac.cn/
 *
 */
package cn.ac.rcpa.bio.proteomics.classification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.biojava.bio.proteomics.Protease;

import cn.ac.rcpa.bio.proteomics.IIdentifiedPeptide;
import cn.ac.rcpa.bio.proteomics.IIdentifiedPeptideHit;
import cn.ac.rcpa.bio.proteomics.classification.impl.IdentifiedPeptideAminoacidClassification;
import cn.ac.rcpa.bio.proteomics.classification.impl.IdentifiedPeptideChargeClassification;
import cn.ac.rcpa.bio.proteomics.classification.impl.IdentifiedPeptideDeltaCnClassification;
import cn.ac.rcpa.bio.proteomics.classification.impl.IdentifiedPeptideExperimentClassification;
import cn.ac.rcpa.bio.proteomics.classification.impl.IdentifiedPeptideGRAVYClassification;
import cn.ac.rcpa.bio.proteomics.classification.impl.IdentifiedPeptideLengthClassification;
import cn.ac.rcpa.bio.proteomics.classification.impl.IdentifiedPeptideMWClassification;
import cn.ac.rcpa.bio.proteomics.classification.impl.IdentifiedPeptideMapClassification;
import cn.ac.rcpa.bio.proteomics.classification.impl.IdentifiedPeptideMissedCleavagesClassification;
import cn.ac.rcpa.bio.proteomics.classification.impl.IdentifiedPeptideNumberOfProteaseTerminalClassification;
import cn.ac.rcpa.bio.proteomics.classification.impl.IdentifiedPeptidePIClassification;
import cn.ac.rcpa.bio.proteomics.classification.impl.IdentifiedPeptidePrecursorMzClassification;
import cn.ac.rcpa.bio.proteomics.classification.impl.IdentifiedPeptideXcorrClassification;
import cn.ac.rcpa.bio.proteomics.classification.impl.PeptideSequenceAcidicNetSolutionChargeClassification;
import cn.ac.rcpa.bio.proteomics.classification.impl.PeptideSequenceAminoacidClassification;
import cn.ac.rcpa.bio.proteomics.classification.impl.PeptideSequenceBasicNetSolutionChargeClassification;
import cn.ac.rcpa.bio.proteomics.classification.impl.PeptideSequenceGRAVYClassification;
import cn.ac.rcpa.bio.proteomics.classification.impl.PeptideSequenceLengthClassification;
import cn.ac.rcpa.bio.proteomics.classification.impl.PeptideSequenceMWClassification;
import cn.ac.rcpa.bio.proteomics.classification.impl.PeptideSequenceMissedCleavagesClassification;
import cn.ac.rcpa.bio.proteomics.classification.impl.PeptideSequencePIClassification;

/**
 * @author sqh
 * 
 */
public class ClassificationFactory {
  public static IClassification<IIdentifiedPeptideHit> getIdentifiedPeptideAminoacidClassification(
      char aminoacid) {
    return new IdentifiedPeptideAminoacidClassification(aminoacid);
  }

  public static IClassification<String> getPeptideSequenceAminoacidClassification(
      char aminoacid) {
    return new PeptideSequenceAminoacidClassification(aminoacid);
  }

  public static IClassification<IIdentifiedPeptide> getIdentifiedPeptideLengthClassification() {
    return IdentifiedPeptideLengthClassification.getInstance();
  }

  public static IClassification<String> getPeptideSequenceLengthClassification() {
    return PeptideSequenceLengthClassification.getInstance();
  }

  public static IClassification<IIdentifiedPeptide> getIdentifiedPeptideExperimentClassification(
      String principle) {
    return new IdentifiedPeptideExperimentClassification(principle);
  }

  public static IClassification<IIdentifiedPeptide> getIdentifiedPeptideMapClassification(
      String principle, Map<String, String> map) {
    return new IdentifiedPeptideMapClassification(principle, map);
  }

  public static IClassification<IIdentifiedPeptide> getIdentifiedPeptideMissedCleavagesClassification(
      Protease protease) {
    return new IdentifiedPeptideMissedCleavagesClassification(protease);
  }

  public static IClassification<IIdentifiedPeptide> getIdentifiedPeptideNumberOfProteaseTerminalClassification(
      Protease protease) {
    return new IdentifiedPeptideNumberOfProteaseTerminalClassification(protease);
  }
  
  public static IClassification<String> getPeptideSequenceMissedCleavagesClassification(
      Protease protease) {
    return new PeptideSequenceMissedCleavagesClassification(protease);
  }

  public static IClassification<IIdentifiedPeptide> getIdentifiedPeptideMWClassification(
      double[] ranges, boolean monoIsotopic) {
    return new IdentifiedPeptideMWClassification(ranges, monoIsotopic);
  }

  public static IClassification<String> getPeptideSequenceMWClassification(
      double[] ranges, boolean monoIsotopic) {
    return new PeptideSequenceMWClassification(ranges, monoIsotopic);
  }

  public static IClassification<IIdentifiedPeptide> getIdentifiedPeptidePIClassification(
      double[] ranges) {
    return new IdentifiedPeptidePIClassification(ranges);
  }

  public static IClassification<String> getPeptideSequencePIClassification(
      double[] ranges) {
    return new PeptideSequencePIClassification(ranges);
  }

  public static IClassification<IIdentifiedPeptide> getIdentifiedPeptideGRAVYClassification(
      double[] ranges) {
    return new IdentifiedPeptideGRAVYClassification(ranges);
  }

  public static IClassification<String> getPeptideSequenceGRAVYClassification(
      double[] ranges) {
    return new PeptideSequenceGRAVYClassification(ranges);
  }

  public static IClassification<IIdentifiedPeptide> getIdentifiedPeptidePrecursorMassClassification(
      double[] ranges) {
    return new IdentifiedPeptidePrecursorMzClassification(ranges);
  }

  public static IClassification<IIdentifiedPeptide> getIdentifiedPeptideChargeClassification() {
    return IdentifiedPeptideChargeClassification.getInstance();
  }

  public static IClassification<IIdentifiedPeptide> getIdentifiedPeptideXcorrClassification(
      double[] ranges) {
    return new IdentifiedPeptideXcorrClassification(ranges);
  }

  public static IClassification<IIdentifiedPeptide> getIdentifiedPeptideDeltaCnClassification(
      double[] ranges) {
    return new IdentifiedPeptideDeltaCnClassification(ranges);
  }

  public static IClassification<String> getPeptideSequenceAcidicNetSolutionChargeClassification() {
    return new PeptideSequenceAcidicNetSolutionChargeClassification();
  }

  public static IClassification<String> getPeptideSequenceBasicNetSolutionChargeClassification() {
    return new PeptideSequenceBasicNetSolutionChargeClassification();
  }

  public static Collection<IClassification<String>> getPeptideSequenceNetSolutionChargeClassification() {
    ArrayList<IClassification<String>> result = new ArrayList<IClassification<String>>();

    result.add(getPeptideSequenceAcidicNetSolutionChargeClassification());
    result.add(getPeptideSequenceBasicNetSolutionChargeClassification());

    return result;
  }
}
