package info.kfgodel.dyna.impl.instantiator.handlers;

import info.kfgodel.dyna.impl.instantiator.invocation.DynaMethodInvocationHandler;
import info.kfgodel.dyna.impl.instantiator.invocation.DynaTypeMethodInvocation;
import info.kfgodel.dyna.impl.instantiator.invocation.result.HandledResult;
import info.kfgodel.dyna.impl.instantiator.invocation.result.HandlingResult;
import info.kfgodel.dyna.impl.instantiator.invocation.result.UnhandledResult;

import java.util.function.Supplier;

/**
 * This type tries to handle the method invocation using a {@link Supplier} value if present
 * Date: 01/05/19 - 20:56
 */
public class SupplierValueAsMethodHandler implements DynaMethodInvocationHandler {

  @Override
  public HandlingResult tryToHandle(DynaTypeMethodInvocation invocation) {
    String methodName = invocation.getMethodName();
    Object value = invocation.getDynaState().get(methodName);
    if ((value instanceof Supplier)) {
      Supplier supplierLambda = (Supplier) value;
      Object suppliedValue = supplierLambda.get();
      return HandledResult.create(suppliedValue);
    }
    return UnhandledResult.instance();
  }

  public static SupplierValueAsMethodHandler create() {
    SupplierValueAsMethodHandler handler = new SupplierValueAsMethodHandler();
    return handler;
  }

}
