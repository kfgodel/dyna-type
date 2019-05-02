package info.kfgodel.dyna.impl.instantiator.handlers;

import info.kfgodel.dyna.impl.instantiator.invocation.DynaMethodInvocationHandler;
import info.kfgodel.dyna.impl.instantiator.invocation.DynaTypeMethodInvocation;
import info.kfgodel.dyna.impl.instantiator.invocation.result.HandledResult;
import info.kfgodel.dyna.impl.instantiator.invocation.result.HandlingResult;
import info.kfgodel.dyna.impl.instantiator.invocation.result.UnhandledResult;

/**
 * This type tries to handle the method invocation using a {@link Runnable} value if present
 * Date: 01/05/19 - 20:56
 */
public class RunnableValueAsMethodHandler implements DynaMethodInvocationHandler {

  @Override
  public HandlingResult tryToHandle(DynaTypeMethodInvocation invocation) {
    String methodName = invocation.getMethodName();
    Object value = invocation.getDynaState().get(methodName);
    if (value instanceof Runnable) {
      Runnable runnableLambda = (Runnable) value;
      runnableLambda.run();
      return HandledResult.create(null);
    }
    return UnhandledResult.instance();
  }

  public static RunnableValueAsMethodHandler create() {
    RunnableValueAsMethodHandler handler = new RunnableValueAsMethodHandler();
    return handler;
  }

}
