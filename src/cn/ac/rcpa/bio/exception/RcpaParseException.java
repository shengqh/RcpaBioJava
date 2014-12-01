package cn.ac.rcpa.bio.exception;

public class RcpaParseException
    extends Exception {
  public RcpaParseException() {
  }

  public RcpaParseException(String message) {
    super(message);
  }

  public RcpaParseException(String message, Throwable cause) {
    super(message, cause);
  }

  public RcpaParseException(Throwable cause) {
    super(cause);
  }
}
