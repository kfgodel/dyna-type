package info.kfgodel.dyna.impl.proxy.handlers;

import info.kfgodel.dyna.impl.proxy.invocation.DynaMethodInvocationHandler;
import info.kfgodel.dyna.impl.proxy.invocation.DynaTypeMethodInvocation;
import info.kfgodel.dyna.impl.proxy.invocation.result.HandledResult;
import info.kfgodel.dyna.impl.proxy.invocation.result.HandlingResult;
import info.kfgodel.dyna.impl.proxy.invocation.result.UnhandledResult;

import java.util.function.Consumer;

/**
 * This type tries to handle the method invocation using a {@link java.util.function.Consumer} value if present
 * Date: 01/05/19 - 20:56
 */
public class ConsumerValueAsMethodHandler implements DynaMethodInvocationHandler {

  @Override
  public HandlingResult tryToHandle(DynaTypeMethodInvocation invocation) {
    String methodName = invocation.getMethodName();
    Object value = invocation.getDynaState().get(methodName);
    if ((value instanceof Consumer)) {
      Consumer consumerLambda = (Consumer) value;
      Object firstArgument = invocation.getArgument(0);
      consumerLambda.accept(firstArgument);
      return HandledResult.create(null);
    }
    return UnhandledResult.instance();
  }

  public static ConsumerValueAsMethodHandler create() {
    ConsumerValueAsMethodHandler handler = new ConsumerValueAsMethodHandler();
    return handler;
  }

}
