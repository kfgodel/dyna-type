package info.kfgodel.dyna.impl.proxy.handlers;

import info.kfgodel.dyna.impl.proxy.invocation.DynaMethodInvocationHandler;
import info.kfgodel.dyna.impl.proxy.invocation.DynaTypeMethodInvocation;
import info.kfgodel.dyna.impl.proxy.invocation.result.HandledResult;
import info.kfgodel.dyna.impl.proxy.invocation.result.HandlingResult;
import info.kfgodel.dyna.impl.proxy.invocation.result.UnhandledResult;

/**
 * This class implements the equals method by comparing object states by identity.<br>
 *   Two objects are equal if they have the same state
 *
 * Date: 05/05/19 - 14:38
 */
public class EqualsMethodHandler implements DynaMethodInvocationHandler {

  public static final String EQUALS_METHOD_NAME = "equals";

  @Override
  public HandlingResult tryToHandle(DynaTypeMethodInvocation invocation) {
    if(EQUALS_METHOD_NAME.equals(invocation.getMethodName()) && invocation.getArgumentCount() == 1){
      boolean areEquals = calculateEqualityBetween(invocation.getInvokedProxy(), invocation.getArgument(0));
      return HandledResult.create(areEquals);
    }
    return UnhandledResult.instance();
  }

  private boolean calculateEqualityBetween(Object object, Object other) {
    return object == other;
  }

  public static EqualsMethodHandler create() {
    EqualsMethodHandler handler = new EqualsMethodHandler();
    return handler;
  }

}
