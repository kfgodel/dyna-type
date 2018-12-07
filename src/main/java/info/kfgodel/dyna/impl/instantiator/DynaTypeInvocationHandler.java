package info.kfgodel.dyna.impl.instantiator;

import info.kfgodel.dyna.api.exceptions.DynaException;
import info.kfgodel.dyna.api.instantiator.DynaObject;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

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

    if (method.getName().equals("getState") && method.getParameterTypes().length == 0) {
      return dynaState;
    }

    Optional<String> gettedProperty = tryAsGetter(methodName);
    if (gettedProperty.isPresent()) {
      return dynaState.get(gettedProperty.get());
    }
    Optional<String> settedProperty = tryAsSetter(methodName);
    if (settedProperty.isPresent() && args.length == 1) {
      return dynaState.put(settedProperty.get(), args[0]);
    }

    Object value = dynaState.get(methodName);
    if (value instanceof Runnable) {
      Runnable runnableLambda = (Runnable) value;
      runnableLambda.run();
      return null;
    }
    if ((value instanceof Supplier)) {
      Supplier supplierLambda = (Supplier) value;
      return supplierLambda.get();
    }
    if ((value instanceof Consumer)) {
      Consumer consumerLambda = (Consumer) value;
      consumerLambda.accept(ensureArg(args, 0));
      return null;
    }
    if ((value instanceof BiConsumer)) {
      BiConsumer biConsumerLambda = (BiConsumer) value;
      biConsumerLambda.accept(ensureArg(args, 0), ensureArg(args, 1));
      return null;
    }
    if ((value instanceof Function)) {
      Function functionLambda = (Function) value;
      return functionLambda.apply(ensureArg(args, 0));
    }
    if ((value instanceof BiFunction)) {
      BiFunction biFunctionLambda = (BiFunction) value;
      return biFunctionLambda.apply(ensureArg(args, 0), ensureArg(args, 1));
    }

    if (methodName.equals("equals") && method.getParameterTypes().length == 1 && Object.class.equals(method.getParameterTypes()[0]) && args.length == 1) {
      Object arg = args[0];
      if (DynaObject.class.isInstance(args[0])) {
        DynaObject otherDyna = (DynaObject) arg;
        return dynaState == otherDyna.getState();
      }
    }

    throw new DynaException("Missing implementation for method[" + method + "] and args: " + Arrays.toString(args));
  }

  private Object ensureArg(Object[] args, int argumentIndex) {
    if (args == null || argumentIndex >= args.length) {
      return null;
    }
    return args[argumentIndex];
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
    String restOfChars = withoutPrefix.substring(lowerFirstChar.length());
    String propertyName = lowerFirstChar + restOfChars;
    return Optional.of(propertyName);
  }

  public static DynaTypeInvocationHandler createEmpty() {
    HashMap<String, Object> initialState = new HashMap<>();
    return create(initialState);
  }

  public static DynaTypeInvocationHandler create(Map<String, Object> initialState) {
    DynaTypeInvocationHandler handler = new DynaTypeInvocationHandler();
    handler.dynaState = initialState;
    return handler;
  }

}
