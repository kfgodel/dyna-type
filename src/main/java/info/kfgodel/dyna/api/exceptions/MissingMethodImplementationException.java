package info.kfgodel.dyna.api.exceptions;

import info.kfgodel.dyna.impl.proxy.invocation.DynaTypeMethodInvocation;

/**
 * This class represents the error of a missing method implementation for a method call on a dyna object
 * Date: 05/05/19 - 14:19
 */
public class MissingMethodImplementationException extends DynaException {

  private final DynaTypeMethodInvocation invocation;

  public MissingMethodImplementationException(DynaTypeMethodInvocation invocation) {
    super("Missing implementation for method[" + invocation.getInvokedMethod() + "] ");
    this.invocation = invocation;
  }

  public DynaTypeMethodInvocation getInvocation() {
    return invocation;
  }
}
