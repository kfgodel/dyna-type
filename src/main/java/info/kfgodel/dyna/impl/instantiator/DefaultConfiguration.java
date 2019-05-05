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
}
