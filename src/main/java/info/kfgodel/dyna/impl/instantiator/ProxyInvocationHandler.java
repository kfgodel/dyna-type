package info.kfgodel.dyna.impl.instantiator;

import com.google.common.collect.Lists;
import info.kfgodel.dyna.api.exceptions.DynaException;
import info.kfgodel.dyna.impl.instantiator.handlers.BiConsumerValueAsMethodHandler;
import info.kfgodel.dyna.impl.instantiator.handlers.BiFunctionValueAsMethodHandler;
import info.kfgodel.dyna.impl.instantiator.handlers.ConsumerValueAsMethodHandler;
import info.kfgodel.dyna.impl.instantiator.handlers.FunctionValueAsMethodHandler;
import info.kfgodel.dyna.impl.instantiator.handlers.GetterPropertyHandler;
import info.kfgodel.dyna.impl.instantiator.handlers.RunnableValueAsMethodHandler;
import info.kfgodel.dyna.impl.instantiator.handlers.SetterPropertyHandler;
import info.kfgodel.dyna.impl.instantiator.handlers.SupplierValueAsMethodHandler;
import info.kfgodel.dyna.impl.instantiator.invocation.DynaMethodInvocationHandler;
import info.kfgodel.dyna.impl.instantiator.invocation.DynaTypeMethodInvocation;
import info.kfgodel.dyna.impl.instantiator.invocation.result.HandlingResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * This type represents the method invocation handler for dyna type objects
 * Date: 28/10/18 - 18:07
 */
public class ProxyInvocationHandler implements InvocationHandler {
  public static Logger LOG = LoggerFactory.getLogger(ProxyInvocationHandler.class);

  private Map<String, Object> dynaState;
  private List<DynaMethodInvocationHandler> orderedHandlers;

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    DynaTypeMethodInvocation invocation = DynaTypeMethodInvocation.create(proxy, method, args, dynaState);

    for (DynaMethodInvocationHandler handler : orderedHandlers) {
      HandlingResult result = handler.tryToHandle(invocation);
      if(result.wasHandled()){
        return result.getValue();
      }else{
        LOG.trace("Handler[{}] couldn't handle: {}. Trying next",handler, invocation);
      }
    }

    // If no handler was used
    throw new DynaException("Missing implementation for method[" + method + "] and args: " + Arrays.toString(args));
  }

  public static ProxyInvocationHandler create(Map<String, Object> initialState) {
    ProxyInvocationHandler handler = new ProxyInvocationHandler();
    handler.dynaState = initialState;
    handler.orderedHandlers = initializeHandlers();
    return handler;
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
