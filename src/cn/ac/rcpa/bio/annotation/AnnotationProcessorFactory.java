package cn.ac.rcpa.bio.annotation;

import cn.ac.rcpa.bio.annotation.impl.GOAnnotationProcessorByGO;
import cn.ac.rcpa.bio.annotation.impl.GravyAnnotationProcessor;
import cn.ac.rcpa.bio.annotation.impl.MWAnnotationProcessor;
import cn.ac.rcpa.bio.annotation.impl.PIAnnotationProcessor;
import cn.ac.rcpa.bio.database.SequenceDatabaseType;
import cn.ac.rcpa.bio.processor.IFileProcessor;
import cn.ac.rcpa.bio.processor.SerialFileProcessor;

public class AnnotationProcessorFactory {
  private AnnotationProcessorFactory() {
  }

  public static IFileProcessor getGravyAnnotationProcessor(double[] gravyThresholds) {
    return new GravyAnnotationProcessor(gravyThresholds);
  }

  public static IFileProcessor getGravyAnnotationProcessor() {
    return getGravyAnnotationProcessor(StatisticRanges.getGRAVYRange());
  }

  public static IFileProcessor getMWAnnotationProcessor(double[] mwThresholds) {
    return new MWAnnotationProcessor(mwThresholds);
  }

  public static IFileProcessor getMWAnnotationProcessor() {
    return  getMWAnnotationProcessor( StatisticRanges.getProteinMWRange());
  }

  public static IFileProcessor getPIFileProcessor(double[] piThresholds) {
    return new PIAnnotationProcessor(piThresholds);
  }

  public static IFileProcessor getPIAnnotationProcessor() {
    return getPIFileProcessor( StatisticRanges.getPIRange());
  }

  public static IFileProcessor getGOAnnotationProcessor(SequenceDatabaseType dbType){
    return new GOAnnotationProcessorByGO(dbType);
  }

  public static IFileProcessor getAnnotationProcessor(IFileProcessor[] processors) {
    return new SerialFileProcessor(processors);
  }

  public static IFileProcessor getAnnotationProcessor(){
    IFileProcessor[] processors = new IFileProcessor[3];

    processors[0] = getMWAnnotationProcessor();
    processors[1] = getPIAnnotationProcessor();
    processors[2] = getGravyAnnotationProcessor();

    return getAnnotationProcessor(processors);
  }
}
