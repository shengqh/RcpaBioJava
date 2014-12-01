package cn.ac.rcpa.bio.proteomics.results.buildsummary.viewer;

import cn.ac.rcpa.bio.models.IObjectRemoveEvent;

public interface IObjectViewer {
  void load(Object obj) throws Exception;
  void addObjectRemovedEvent(IObjectRemoveEvent e);
  void removeObjectMovedEvent(IObjectRemoveEvent e);
}
