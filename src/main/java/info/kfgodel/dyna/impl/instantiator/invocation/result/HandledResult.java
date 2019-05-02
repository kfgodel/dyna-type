package info.kfgodel.dyna.impl.instantiator.invocation.result;

/**
 * This represents the handling result where the invocation was handled
 * Date: 01/05/19 - 20:33
 */
public class HandledResult implements HandlingResult {

  private Object value;

  public static HandledResult create(Object value) {
    HandledResult result = new HandledResult();
    result.value = value;
    return result;
  }

  @Override
  public boolean wasHandled() {
    return true;
  }

  @Override
  public Object getValue() {
    return value;
  }

  @Override
  public String toString() {
    return "HandledResult{" +
      "value=" + value +
      '}';
  }
}
