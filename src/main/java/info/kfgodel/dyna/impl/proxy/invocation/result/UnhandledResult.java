package info.kfgodel.dyna.impl.proxy.invocation.result;

/**
 * Date: 01/05/19 - 20:34
 */
public class UnhandledResult implements HandlingResult {

  private static final UnhandledResult INSTANCE = new UnhandledResult();

  @Override
  public boolean wasHandled() {
    return false;
  }

  @Override
  public Object getValue() {
    throw new IllegalStateException("This result doesn't have a value and should not be accessed");
  }

  public static UnhandledResult instance() {
    return INSTANCE;
  }

  @Override
  public String toString() {
    return "UnhandledResult{}";
  }
}
