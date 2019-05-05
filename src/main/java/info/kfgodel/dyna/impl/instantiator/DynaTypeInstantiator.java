package info.kfgodel.dyna.impl.instantiator;

import info.kfgodel.dyna.api.exceptions.DynaException;
import info.kfgodel.dyna.api.instantiator.Instantiator;
import info.kfgodel.dyna.api.instantiator.InstantiatorConfiguration;
import info.kfgodel.dyna.impl.proxy.ProxyInvocationHandler;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.implementation.InvocationHandlerAdapter;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This type implements an instantiator for dynamic type instances.<br>
 * Date: 28/10/18 - 17:51
 */
public class DynaTypeInstantiator implements Instantiator {

  private InstantiatorConfiguration configuration;

  public static DynaTypeInstantiator createDefault() {
    return create(DefaultConfiguration.create());
  }

  public static DynaTypeInstantiator create(InstantiatorConfiguration configuration) {
    DynaTypeInstantiator intantiator = new DynaTypeInstantiator();
    intantiator.configuration = configuration;
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
    InvocationHandler handler = ProxyInvocationHandler.create(initialState, configuration.getChainOfHandlers());
    List<Type> interfaceTypes = configuration.getImplementedInterfaces();
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


}
