package cn.ac.rcpa.bio.proteomics;

public class SequenceValidateException
    extends RuntimeException {
  public SequenceValidateException() {
  }

  public SequenceValidateException(String message) {
    super(message);
  }

  public SequenceValidateException(String message, Throwable cause) {
    super(message, cause);
  }

  public SequenceValidateException(Throwable cause) {
    super(cause);
  }
}
