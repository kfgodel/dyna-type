package info.kfgodel.dyna.impl.instantiator.handlers;

import info.kfgodel.dyna.impl.instantiator.invocation.DynaMethodInvocationHandler;
import info.kfgodel.dyna.impl.instantiator.invocation.DynaTypeMethodInvocation;
import info.kfgodel.dyna.impl.instantiator.invocation.result.HandledResult;
import info.kfgodel.dyna.impl.instantiator.invocation.result.HandlingResult;
import info.kfgodel.dyna.impl.instantiator.invocation.result.UnhandledResult;

import java.util.function.Function;

/**
 * This type tries to handle the method invocation using a {@link java.util.function.Function} value if present
 * Date: 01/05/19 - 20:56
 */
public class FunctionValueAsMethodHandler implements DynaMethodInvocationHandler {

  @Override
  public HandlingResult tryToHandle(DynaTypeMethodInvocation invocation) {
    String methodName = invocation.getMethodName();
    Object value = invocation.getDynaState().get(methodName);
    if ((value instanceof Function)) {
      Function functionLambda = (Function) value;
      Object firstArgument = invocation.getArgument(0);
      Object functionResult = functionLambda.apply(firstArgument);
      return HandledResult.create(functionResult);
    }
    return UnhandledResult.instance();
  }

  public static FunctionValueAsMethodHandler create() {
    FunctionValueAsMethodHandler handler = new FunctionValueAsMethodHandler();
    return handler;
  }

}
