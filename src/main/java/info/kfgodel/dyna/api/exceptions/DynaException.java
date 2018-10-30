package info.kfgodel.dyna.api.exceptions;

/**
 * This type represents a generic dyna type error
 * Date: 30/10/18 - 01:08
 */
public class DynaException extends RuntimeException {

  public DynaException(String message) {
    super(message);
  }

  public DynaException(String message, Throwable cause) {
    super(message, cause);
  }

  public DynaException(Throwable cause) {
    super(cause);
  }

}
