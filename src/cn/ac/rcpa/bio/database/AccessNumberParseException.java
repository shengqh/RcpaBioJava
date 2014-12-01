package cn.ac.rcpa.bio.database;

public class AccessNumberParseException
    extends Exception {
  public AccessNumberParseException() {
  }

  public AccessNumberParseException(String message) {
    super(message);
  }

  public AccessNumberParseException(String message, Throwable cause) {
    super(message, cause);
  }

  public AccessNumberParseException(Throwable cause) {
    super(cause);
  }
}
