package info.kfgodel.dyna.impl.proxy.handlers;

import info.kfgodel.dyna.impl.proxy.invocation.DynaMethodInvocationHandler;
import info.kfgodel.dyna.impl.proxy.invocation.DynaTypeMethodInvocation;
import info.kfgodel.dyna.impl.proxy.invocation.result.HandledResult;
import info.kfgodel.dyna.impl.proxy.invocation.result.HandlingResult;
import info.kfgodel.dyna.impl.proxy.invocation.result.UnhandledResult;

/**
 * This class implements the hashcode method definition delegating on the state {@link System#identityHashCode(Object)}.<br>
 *   Two objects share the same hashcode when they have the same state
 * Date: 05/05/19 - 14:22
 */
public class HashcodeMethodHandler implements DynaMethodInvocationHandler {

  public static final String HASHCODE_METHOD_NAME = "hashCode";

  @Override
  public HandlingResult tryToHandle(DynaTypeMethodInvocation invocation) {
    if(HASHCODE_METHOD_NAME.equals(invocation.getMethodName()) && invocation.getArgumentCount() == 0){
      int hashcode = calculateHashCodeFor(invocation.getInvokedProxy());
      return HandledResult.create(hashcode);
    }
    return UnhandledResult.instance();
  }

  private int calculateHashCodeFor(Object dynaState) {
    return System.identityHashCode(dynaState);
  }

  public static HashcodeMethodHandler create() {
    HashcodeMethodHandler handler = new HashcodeMethodHandler();
    return handler;
  }

}
