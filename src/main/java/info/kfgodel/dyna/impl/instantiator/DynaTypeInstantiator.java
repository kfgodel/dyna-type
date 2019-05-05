package info.kfgodel.dyna.impl.instantiator;

import com.google.common.collect.Lists;
import info.kfgodel.dyna.api.exceptions.DynaException;
import info.kfgodel.dyna.api.instantiator.Instantiator;
import info.kfgodel.dyna.impl.proxy.ProxyInvocationHandler;
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
import info.kfgodel.dyna.impl.proxy.invocation.DynaMethodInvocationHandler;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.implementation.InvocationHandlerAdapter;
import net.bytebuddy.matcher.ElementMatcher;
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
      .method(isInterceptable())
      .intercept(InvocationHandlerAdapter.of(handler))
      .make()
      .load(expectedInstanceType.getClassLoader())
      .getLoaded();
    try {
      T proxy = proxyClass.getConstructor().newInstance();
      return proxy;
    } catch (Exception e) {
      throw new DynaException("Failed to instantiate proxy: " + e.getMessage(), e);
    }
  }

  private ElementMatcher.Junction<MethodDescription> isInterceptable() {
    // Every method declared by object is intercepted so it can be redefined
    ElementMatcher.Junction<MethodDescription> isDeclaredByObject = ElementMatchers.isDeclaredBy(Object.class);

    // All abstract methods without a definition are intercepted
    ElementMatcher.Junction<MethodDescription> isAnUnimplementedAbstractMethod = ElementMatchers.isAbstract();

    // All non default interface methods are intercepted
    ElementMatcher.Junction<MethodDescription> isDeclaredByInterfaceAndIsNotDefault = ElementMatchers.isDeclaredBy(ElementMatchers.isInterface())
      .and(ElementMatchers.not(ElementMatchers.isDefaultMethod()));

    return isDeclaredByObject.or(isAnUnimplementedAbstractMethod).or(isDeclaredByInterfaceAndIsNotDefault);
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
      EqualsMethodHandler.create()
    );
  }

}
