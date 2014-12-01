package cn.ac.rcpa.bio.proteomics.io;

import java.io.IOException;

import cn.ac.rcpa.bio.exception.RcpaParseException;
import cn.ac.rcpa.bio.proteomics.IIdentifiedResult;

public interface IIdentifiedResultReader<E extends IIdentifiedResult> {
  E read(String filename) throws IOException, RcpaParseException;
}
