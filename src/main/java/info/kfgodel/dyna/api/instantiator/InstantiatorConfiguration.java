package info.kfgodel.dyna.api.instantiator;

import info.kfgodel.dyna.impl.proxy.invocation.DynaMethodInvocationHandler;

import java.lang.reflect.Type;
import java.util.List;

/**
 * This type represents the configuration given to an instantiator to define how should it generate new instances.<br>
 *   This configuration allows changing the custom method handlers, or which interfaces are implemented by the object
 * Date: 05/05/19 - 16:34
 */
public interface InstantiatorConfiguration {

  /**
   * Returns the ordered list of handlers that must be applied for intercepted methods.<br>
   *   Each handler will be asked, in order, to handle a method invocation. The first to answer a result will used
   * @return The chain of responsibility handlers
   */
  List<DynaMethodInvocationHandler> getChainOfHandlers();

  /**
   * Returns the list of interfaces that all instantiated objects will implement
   * @return The list of interface types
   */
  List<Type> getImplementedInterfaces();
}
