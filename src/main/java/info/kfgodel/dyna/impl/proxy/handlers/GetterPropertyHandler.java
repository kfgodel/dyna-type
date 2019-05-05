package info.kfgodel.dyna.impl.proxy.handlers;

import info.kfgodel.dyna.impl.proxy.invocation.DynaMethodInvocationHandler;
import info.kfgodel.dyna.impl.proxy.invocation.DynaTypeMethodInvocation;
import info.kfgodel.dyna.impl.proxy.invocation.result.HandledResult;
import info.kfgodel.dyna.impl.proxy.invocation.result.HandlingResult;
import info.kfgodel.dyna.impl.proxy.invocation.result.UnhandledResult;

import java.util.Optional;

/**
 * This handler tries to handle the method invocations as it were a getter method accessing a property of the object
 * Date: 01/05/19 - 20:14
 */
public class GetterPropertyHandler implements DynaMethodInvocationHandler {

  public static final String GETTER_PREFIX = "get";

  @Override
  public HandlingResult tryToHandle(DynaTypeMethodInvocation invocation) {
    Optional<String> gettedProperty = invocation.getSubstringOnMethodNameAfter(GETTER_PREFIX);
    if (gettedProperty.isPresent()) {
      String propertyName = gettedProperty.get();
      Object propertyValue = invocation.getDynaState().get(propertyName);
      return HandledResult.create(propertyValue);
    }
    return UnhandledResult.instance();
  }

  public static GetterPropertyHandler create() {
    GetterPropertyHandler handler = new GetterPropertyHandler();
    return handler;
  }

}
