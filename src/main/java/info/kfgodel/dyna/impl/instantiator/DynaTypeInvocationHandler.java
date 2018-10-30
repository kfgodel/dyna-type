package info.kfgodel.dyna.impl.instantiator;

import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * This type represents the method invocation handler for dyna type objects
 * Date: 28/10/18 - 18:07
 */
public class DynaTypeInvocationHandler implements InvocationHandler {

  private Map<String, Object> dynaState;

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    final Class<?> declaringClass = method.getDeclaringClass();

    Constructor<Lookup> constructor = Lookup.class.getDeclaredConstructor(Class.class);
    constructor.setAccessible(true);
    Object result = constructor.newInstance(declaringClass)
      .in(declaringClass)
      .unreflectSpecial(method, declaringClass)
      .bindTo(proxy)
      .invokeWithArguments(args);
    return result;
//    return method.invoke(proxy, args);
  }

  public static DynaTypeInvocationHandler create() {
    DynaTypeInvocationHandler handler = new DynaTypeInvocationHandler();
    handler.dynaState = new HashMap<>();
    return handler;
  }

}
