package cn.ac.rcpa.processor;

import java.util.ArrayList;

public abstract class AbstractProcessor<E> implements IProcessor<E>{
  protected ArrayList<String> processed;
  protected ArrayList<String> unprocessed;

  public AbstractProcessor() {
    super();
    processed = new ArrayList<String>();
    unprocessed = new ArrayList<String>();
  }
}
