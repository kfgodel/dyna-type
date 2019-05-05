package info.kfgodel.dyna.impl.proxy.handlers;

import info.kfgodel.dyna.impl.proxy.invocation.DynaMethodInvocationHandler;
import info.kfgodel.dyna.impl.proxy.invocation.DynaTypeMethodInvocation;
import info.kfgodel.dyna.impl.proxy.invocation.result.HandledResult;
import info.kfgodel.dyna.impl.proxy.invocation.result.HandlingResult;
import info.kfgodel.dyna.impl.proxy.invocation.result.UnhandledResult;

/**
 * Base type for classes trying to implement hashcode method
 * Date: 05/05/19 - 18:58
 */
public class HashcodeMethodHandlerSupport implements DynaMethodInvocationHandler {

  public static final String HASHCODE_METHOD_NAME = "hashCode";

  @Override
  public HandlingResult tryToHandle(DynaTypeMethodInvocation invocation) {
    if(HASHCODE_METHOD_NAME.equals(invocation.getMethodName()) && invocation.getArgumentCount() == 0){
      int hashcode = calculateHashCodeFor(invocation.getInvokedProxy());
      return HandledResult.create(hashcode);
    }
    return UnhandledResult.instance();
  }

  protected int calculateHashCodeFor(Object invokedProxy) {
    return System.identityHashCode(invokedProxy);
  }
}
