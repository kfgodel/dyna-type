package info.kfgodel.dyna.impl.proxy.handlers;

import info.kfgodel.dyna.impl.proxy.invocation.DynaMethodInvocationHandler;
import info.kfgodel.dyna.impl.proxy.invocation.DynaTypeMethodInvocation;
import info.kfgodel.dyna.impl.proxy.invocation.result.HandledResult;
import info.kfgodel.dyna.impl.proxy.invocation.result.HandlingResult;
import info.kfgodel.dyna.impl.proxy.invocation.result.UnhandledResult;

import java.util.Map;

/**
 * This class implements toString based on the object state
 * Date: 05/05/19 - 15:37
 */
public class ToStringMethodHandler implements DynaMethodInvocationHandler {

  public static final String TO_STRING_METHOD_NAME = "toString";

  @Override
  public HandlingResult tryToHandle(DynaTypeMethodInvocation invocation) {
    if(TO_STRING_METHOD_NAME.equals(invocation.getMethodName()) && invocation.getArgumentCount() == 0){
      String stringRepresentation = calculateRepresentationFor(invocation.getInvokedProxy(), invocation.getDynaState());
      return HandledResult.create(stringRepresentation);
    }
    return UnhandledResult.instance();
  }

  private String calculateRepresentationFor(Object invokedProxy, Map<String, Object> dynaState) {
    return calculateClassname(invokedProxy) + dynaState.toString();
  }

  private String calculateClassname(Object invokedProxy) {
    Class<?> proxyClass = invokedProxy.getClass();
    Class<?> superclass = proxyClass.getSuperclass();
    // In case it's just interface implementations
    if(superclass.equals(Object.class)){
      Class<?>[] interfaces = proxyClass.getInterfaces();
      if(interfaces.length > 0){
        superclass = interfaces[0];
      }
    }
    return superclass.getSimpleName();
  }

  public static ToStringMethodHandler create() {
    ToStringMethodHandler handler = new ToStringMethodHandler();
    return handler;
  }

}
