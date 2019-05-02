package info.kfgodel.dyna.impl.instantiator;

import com.google.common.collect.Lists;
import info.kfgodel.dyna.api.exceptions.DynaException;
import info.kfgodel.dyna.api.instantiator.Instantiator;
import info.kfgodel.dyna.impl.instantiator.handlers.BiConsumerValueAsMethodHandler;
import info.kfgodel.dyna.impl.instantiator.handlers.BiFunctionValueAsMethodHandler;
import info.kfgodel.dyna.impl.instantiator.handlers.ConsumerValueAsMethodHandler;
import info.kfgodel.dyna.impl.instantiator.handlers.FunctionValueAsMethodHandler;
import info.kfgodel.dyna.impl.instantiator.handlers.GetterPropertyHandler;
import info.kfgodel.dyna.impl.instantiator.handlers.RunnableValueAsMethodHandler;
import info.kfgodel.dyna.impl.instantiator.handlers.SetterPropertyHandler;
import info.kfgodel.dyna.impl.instantiator.handlers.SupplierValueAsMethodHandler;
import info.kfgodel.dyna.impl.instantiator.invocation.DynaMethodInvocationHandler;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.InvocationHandlerAdapter;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This type implements an instantiator for dynamic type instances.<br>
 * Date: 28/10/18 - 17:51
 */
public class DynaTypeInstantiator implements Instantiator {

  private List<DynaMethodInvocationHandler> chainOfHandlers;

  public static DynaTypeInstantiator create() {
    DynaTypeInstantiator intantiator = new DynaTypeInstantiator();
    intantiator.chainOfHandlers = initializeHandlers();
    return intantiator;
  }

  public <T> T instantiate(Class<T> expectedInstanceType, Map<String, Object> initialState) {
    return instantiateProxyOf(expectedInstanceType, initialState);
  }

  @Override
  public <T> T instantiate(Class<T> expectedInstanceType) {
    return instantiateProxyOf(expectedInstanceType, new HashMap<>());
  }

  private <T> T instantiateProxyOf(Class<T> expectedInstanceType, Map<String, Object> initialState) {
    InvocationHandler handler = ProxyInvocationHandler.create(initialState, chainOfHandlers);
    List<Type> interfaceTypes = Arrays.asList();
    Class<? extends T> proxyClass = new ByteBuddy()
      .subclass(expectedInstanceType)
      .implement(interfaceTypes)
      .method(ElementMatchers.not(ElementMatchers.isDefaultMethod()))
      .intercept(InvocationHandlerAdapter.of(handler))
      .make()
      .load(expectedInstanceType.getClassLoader())
      .getLoaded();
    try {
      T proxy = proxyClass.getConstructor().newInstance();
      return proxy;
    } catch (Exception e) {
      throw new DynaException("Failed to instante proxy: " + e.getMessage(), e);
    }
  }

  private static List<DynaMethodInvocationHandler> initializeHandlers() {
    return Lists.newArrayList(
      GetterPropertyHandler.create(),
      SetterPropertyHandler.create(),
      RunnableValueAsMethodHandler.create(),
      SupplierValueAsMethodHandler.create(),
      ConsumerValueAsMethodHandler.create(),
      BiConsumerValueAsMethodHandler.create(),
      FunctionValueAsMethodHandler.create(),
      BiFunctionValueAsMethodHandler.create()
    );
  }

}
