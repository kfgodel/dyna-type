package info.kfgodel.dyna.impl.proxy.handlers;

/**
 * This class implements the equals method by comparing object states by identity.<br>
 *   Two objects are equal if they have the same state
 *
 * Date: 05/05/19 - 14:38
 */
public class EqualsMethodHandler extends EqualiltyMethodHandlerSupport  {

  public static EqualiltyMethodHandlerSupport create() {
    EqualiltyMethodHandlerSupport handler = new EqualsMethodHandler();
    return handler;
  }

}
