package cn.ac.rcpa.bio.sequest;

import cn.ac.rcpa.bio.exception.RcpaParseException;

public class SequestParseException
    extends RcpaParseException {
  public SequestParseException() {
  }

  public SequestParseException(String message) {
    super(message);
  }

  public SequestParseException(String message, Throwable cause) {
    super(message, cause);
  }

  public SequestParseException(Throwable cause) {
    super(cause);
  }
}
