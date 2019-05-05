package info.kfgodel.dyna.impl.proxy.handlers;

import info.kfgodel.dyna.impl.proxy.invocation.DynaMethodInvocationHandler;
import info.kfgodel.dyna.impl.proxy.invocation.DynaTypeMethodInvocation;
import info.kfgodel.dyna.impl.proxy.invocation.result.HandledResult;
import info.kfgodel.dyna.impl.proxy.invocation.result.HandlingResult;
import info.kfgodel.dyna.impl.proxy.invocation.result.UnhandledResult;

/**
 * Support class for any type trying to implement equality method
 * Date: 05/05/19 - 18:55
 */
public class EqualiltyMethodHandlerSupport implements DynaMethodInvocationHandler {

  public static final String EQUALS_METHOD_NAME = "equals";

  @Override
  public HandlingResult tryToHandle(DynaTypeMethodInvocation invocation) {
    if(EQUALS_METHOD_NAME.equals(invocation.getMethodName()) && invocation.getArgumentCount() == 1){
      boolean areEquals = calculateEqualityBetween(invocation.getInvokedProxy(), invocation.getArgument(0));
      return HandledResult.create(areEquals);
    }
    return UnhandledResult.instance();
  }

  protected boolean calculateEqualityBetween(Object object, Object other) {
    return object == other;
  }

}
