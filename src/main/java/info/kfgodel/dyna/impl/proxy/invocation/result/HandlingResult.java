package info.kfgodel.dyna.impl.proxy.invocation.result;

/**
 * This type represents the possible outcomes of an invocation handler.<br>
 *   It either handles the invocation and generates a result, or it rejects handling the invocation and has no result
 * Date: 01/05/19 - 19:40
 */
public interface HandlingResult {
  /**
   * Indicates whether this represents a handled result or not
   * @return True if this result has a value to be returned
   */
  boolean wasHandled();

  /**
   * The value that this result has, or an exception if the handler didn't handle the invocation.<br>
   *   This method should only be invoked if {@link #wasHandled()} is true
   * @return The produced value after a successful
   */
  Object getValue();
}
