package info.kfgodel.dyna.impl.instantiator;

import com.google.common.collect.Lists;
import info.kfgodel.dyna.api.instantiator.InstantiatorConfiguration;
import info.kfgodel.dyna.impl.proxy.handlers.BiConsumerValueAsMethodHandler;
import info.kfgodel.dyna.impl.proxy.handlers.BiFunctionValueAsMethodHandler;
import info.kfgodel.dyna.impl.proxy.handlers.ConsumerValueAsMethodHandler;
import info.kfgodel.dyna.impl.proxy.handlers.EqualsMethodHandler;
import info.kfgodel.dyna.impl.proxy.handlers.FunctionValueAsMethodHandler;
import info.kfgodel.dyna.impl.proxy.handlers.GetterPropertyHandler;
import info.kfgodel.dyna.impl.proxy.handlers.HashcodeMethodHandler;
import info.kfgodel.dyna.impl.proxy.handlers.RunnableValueAsMethodHandler;
import info.kfgodel.dyna.impl.proxy.handlers.SetterPropertyHandler;
import info.kfgodel.dyna.impl.proxy.handlers.SupplierValueAsMethodHandler;
import info.kfgodel.dyna.impl.proxy.handlers.ToStringMethodHandler;
import info.kfgodel.dyna.impl.proxy.invocation.DynaMethodInvocationHandler;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the default configuration
 *
 * Date: 05/05/19 - 16:36
 */
public class DefaultConfiguration implements InstantiatorConfiguration {


  private List<Type> implementedInterfaces;
  private List<DynaMethodInvocationHandler> chainOfHandlers;

  public static DefaultConfiguration create() {
    DefaultConfiguration configuration = new DefaultConfiguration();
    configuration.chainOfHandlers = initializeHandlers();
    configuration.implementedInterfaces = new ArrayList<>();
    return configuration;
  }

  @Override
  public List<DynaMethodInvocationHandler> getChainOfHandlers() {
    return chainOfHandlers;
  }

  @Override
  public List<Type> getImplementedInterfaces() {
    return implementedInterfaces;
  }

  public DefaultConfiguration setImplementedInterfaces(List<Type> implementedInterfaces) {
    this.implementedInterfaces = implementedInterfaces;
    return this;
  }

  public DefaultConfiguration setChainOfHandlers(List<DynaMethodInvocationHandler> chainOfHandlers) {
    this.chainOfHandlers = chainOfHandlers;
    return this;
  }

  private static List<DynaMethodInvocationHandler> initializeHandlers() {
    return Lists.newArrayList(
      RunnableValueAsMethodHandler.create(),
      SupplierValueAsMethodHandler.create(),
      ConsumerValueAsMethodHandler.create(),
      BiConsumerValueAsMethodHandler.create(),
      FunctionValueAsMethodHandler.create(),
      BiFunctionValueAsMethodHandler.create(),
      GetterPropertyHandler.create(),
      SetterPropertyHandler.create(),
      HashcodeMethodHandler.create(),
      EqualsMethodHandler.create(),
      ToStringMethodHandler.create()
    );
  }

  public DefaultConfiguration withInterface(Class<?> anInterface) {
    this.implementedInterfaces.add(anInterface);
    return this;
  }

  public DefaultConfiguration withHandler(DynaMethodInvocationHandler methodHandler) {
    this.chainOfHandlers.add(methodHandler);
    return this;
  }

  /**
   * Adds the given handler after the one referenced by its type.<br>
   *   If the chain doesn't contain a handler of that type, then the new is added at the end
   * @param referenceHandlerClass The class that indicates the type of handler to find
   * @param addedHandler The new handler to add
   * @return This instance for method chaining
   */
  public DefaultConfiguration addBefore(Class<?> referenceHandlerClass, DynaMethodInvocationHandler addedHandler) {
    int referenceHandlerIndex = this.findIndexOf(referenceHandlerClass);
    this.chainOfHandlers.add(referenceHandlerIndex, addedHandler);
    return this;
  }

  private int findIndexOf(Class<?> referenceHandlerClass) {
    for (int i = 0; i < chainOfHandlers.size(); i++) {
      DynaMethodInvocationHandler handler = chainOfHandlers.get(i);
      if(referenceHandlerClass.isInstance(handler)){
        return i;
      }
    }
    return chainOfHandlers.size();
  }

  /**
   * Replaces the handler referenced by its type on the chain with the one provided
   * @param referenceHandlerClass The type of handler to remove
   * @param replacementHandler The hanbdler to use as replacement
   * @return This instance for method chaining
   */
  public DefaultConfiguration addInsteadOf(Class<?> referenceHandlerClass, DynaMethodInvocationHandler replacementHandler) {
    int replacedIndex = this.findIndexOf(referenceHandlerClass);
    this.chainOfHandlers.set(replacedIndex, replacementHandler);
    return this;
  }
}
