package info.kfgodel.dyna.impl.instantiator.handlers;

import info.kfgodel.dyna.impl.instantiator.invocation.DynaMethodInvocationHandler;
import info.kfgodel.dyna.impl.instantiator.invocation.DynaTypeMethodInvocation;
import info.kfgodel.dyna.impl.instantiator.invocation.result.HandledResult;
import info.kfgodel.dyna.impl.instantiator.invocation.result.HandlingResult;
import info.kfgodel.dyna.impl.instantiator.invocation.result.UnhandledResult;

import java.util.function.BiFunction;

/**
 * This type tries to handle the method invocation using a {@link java.util.function.BiFunction} value if present
 * Date: 01/05/19 - 20:56
 */
public class BiFunctionValueAsMethodHandler implements DynaMethodInvocationHandler {

  @Override
  public HandlingResult tryToHandle(DynaTypeMethodInvocation invocation) {
    String methodName = invocation.getMethodName();
    Object value = invocation.getDynaState().get(methodName);
    if ((value instanceof BiFunction)) {
      BiFunction biFunctionLambda = (BiFunction) value;
      Object firstArgument = invocation.getArgument(0);
      Object secondArgument = invocation.getArgument(1);
      Object functionResult = biFunctionLambda.apply(firstArgument, secondArgument);
      return HandledResult.create(functionResult);
    }
    return UnhandledResult.instance();
  }

  public static BiFunctionValueAsMethodHandler create() {
    BiFunctionValueAsMethodHandler handler = new BiFunctionValueAsMethodHandler();
    return handler;
  }

}
