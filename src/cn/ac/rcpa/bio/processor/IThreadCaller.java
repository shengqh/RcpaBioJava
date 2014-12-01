package cn.ac.rcpa.bio.processor;

import cn.ac.rcpa.models.IMessageShower;

public interface IThreadCaller
    extends IMessageShower {
  void threadStarted(Thread currentThread);

  void threadFinished(Thread currentThread);
}
