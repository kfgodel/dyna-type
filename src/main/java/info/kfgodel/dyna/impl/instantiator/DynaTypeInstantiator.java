package info.kfgodel.dyna.impl.instantiator;

import info.kfgodel.dyna.api.exceptions.DynaException;
import info.kfgodel.dyna.api.instantiator.DynaObject;
import info.kfgodel.dyna.api.instantiator.Instantiator;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.InvocationHandlerAdapter;
import net.bytebuddy.matcher.ElementMatchers;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * This type implements an instantiator for dynamic type instances.<br>
 * Date: 28/10/18 - 17:51
 */
public class DynaTypeInstantiator implements Instantiator {

  public static DynaTypeInstantiator create() {
    DynaTypeInstantiator intantiator = new DynaTypeInstantiator();
    return intantiator;
  }

  public <T> T instantiate(Class<T> expectedInstanceType, Map<String, Object> initialState) {
    InvocationHandler handler = DynaTypeInvocationHandler.create(initialState);
    return instantiateProxyOf(expectedInstanceType, handler);
  }

  @Override
  public <T> T instantiate(Class<T> expectedInstanceType) {
    InvocationHandler handler = DynaTypeInvocationHandler.createEmpty();
    return instantiateProxyOf(expectedInstanceType, handler);
  }

  private <T> T instantiateProxyOf(Class<T> expectedInstanceType, InvocationHandler handler) {
    List<Type> interfaceTypes = Arrays.asList(DynaObject.class);
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
}
