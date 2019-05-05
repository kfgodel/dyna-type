package info.kfgodel.dyna.impl.proxy.handlers;

import info.kfgodel.dyna.impl.proxy.invocation.DynaMethodInvocationHandler;
import info.kfgodel.dyna.impl.proxy.invocation.DynaTypeMethodInvocation;
import info.kfgodel.dyna.impl.proxy.invocation.result.HandledResult;
import info.kfgodel.dyna.impl.proxy.invocation.result.HandlingResult;
import info.kfgodel.dyna.impl.proxy.invocation.result.UnhandledResult;

import java.util.function.BiConsumer;

/**
 * This type tries to handle the method invocation using a {@link java.util.function.BiConsumer} value if present
 * Date: 01/05/19 - 20:56
 */
public class BiConsumerValueAsMethodHandler implements DynaMethodInvocationHandler {

  @Override
  public HandlingResult tryToHandle(DynaTypeMethodInvocation invocation) {
    String methodName = invocation.getMethodName();
    Object value = invocation.getDynaState().get(methodName);
    if ((value instanceof BiConsumer)) {
      BiConsumer biConsumerLambda = (BiConsumer) value;
      Object firstArgument = invocation.getArgument(0);
      Object secondArgument = invocation.getArgument(1);
      biConsumerLambda.accept(firstArgument, secondArgument);
      return HandledResult.create(null);
    }
    return UnhandledResult.instance();
  }

  public static BiConsumerValueAsMethodHandler create() {
    BiConsumerValueAsMethodHandler handler = new BiConsumerValueAsMethodHandler();
    return handler;
  }

}
