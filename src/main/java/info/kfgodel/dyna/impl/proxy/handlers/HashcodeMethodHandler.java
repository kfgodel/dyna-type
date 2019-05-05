package info.kfgodel.dyna.impl.proxy.handlers;

/**
 * This class implements the hashcode method definition delegating on the state {@link System#identityHashCode(Object)}.<br>
 *   Two objects share the same hashcode when they have the same state
 * Date: 05/05/19 - 14:22
 */
public class HashcodeMethodHandler extends HashcodeMethodHandlerSupport {

  public static HashcodeMethodHandlerSupport create() {
    HashcodeMethodHandlerSupport handler = new HashcodeMethodHandler();
    return handler;
  }

}
