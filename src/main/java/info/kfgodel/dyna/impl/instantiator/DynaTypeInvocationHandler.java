package info.kfgodel.dyna.impl.instantiator;

import info.kfgodel.dyna.api.exceptions.DynaException;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * This type represents the method invocation handler for dyna type objects
 * Date: 28/10/18 - 18:07
 */
public class DynaTypeInvocationHandler implements InvocationHandler {

  public static final String GETTER_PREFIX = "get";
  public static final String SETTER_PREFIX = "set";
  private Map<String, Object> dynaState;

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    String methodName = method.getName();
    Optional<String> gettedProperty = tryAsGetter(methodName);
    if (gettedProperty.isPresent()) {
      return dynaState.get(gettedProperty.get());
    }
    Optional<String> settedProperty = tryAsSetter(methodName);
    if (settedProperty.isPresent() && args.length == 1) {
      return dynaState.put(settedProperty.get(), args[0]);
    }

    throw new DynaException("Missing implementation for method[" + method + "] and args: " + Arrays.toString(args));
  }

  private Optional<String> tryAsSetter(String methodName) {
    return tryAsPropertyNameStartingWith(SETTER_PREFIX, methodName);
  }

  private Optional<String> tryAsGetter(String methodName) {
    return tryAsPropertyNameStartingWith(GETTER_PREFIX, methodName);
  }

  private Optional<String> tryAsPropertyNameStartingWith(String prefix, String methodName) {
    if (!methodName.startsWith(prefix)) {
      return Optional.empty();
    }
    String withoutPrefix = methodName.substring(prefix.length());
    if (withoutPrefix.isEmpty()) {
      return Optional.empty();
    }
    String lowerFirstChar = withoutPrefix.substring(0, 1).toLowerCase();
    String restOfChars = lowerFirstChar + withoutPrefix.substring(2);
    String propertyName = lowerFirstChar + restOfChars;
    return Optional.of(propertyName);
  }

  public static DynaTypeInvocationHandler create() {
    DynaTypeInvocationHandler handler = new DynaTypeInvocationHandler();
    handler.dynaState = new HashMap<>();
    return handler;
  }

}
