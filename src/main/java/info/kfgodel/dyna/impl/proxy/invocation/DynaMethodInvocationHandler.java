package info.kfgodel.dyna.impl.proxy.invocation;

import info.kfgodel.dyna.impl.proxy.invocation.result.HandlingResult;

/**
 * This type represents the contract that an invocation handler must implement to handle a method invocation.<br>
 *   A {@link DynaTypeMethodInvocation} handler may not handle the invocation in which case it returns an empty optional
 * Date: 01/05/19 - 19:33
 */
public interface DynaMethodInvocationHandler {

  /**
   * Handles the given method invocation if this instance is capable of doing so.<br>
   *   The result will be empty if this handler can't handle the invocation, otherwise a value will be generated
   * @param invocation The invocation to try to handle
   * @return The attempt result. Either handled or not
   */
  HandlingResult tryToHandle(DynaTypeMethodInvocation invocation);
}
