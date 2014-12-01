package cn.ac.rcpa.bio.models;

public interface IObjectRemoveEvent {
  void objectRemoved(Object fromObject, Object removedObject);
}
