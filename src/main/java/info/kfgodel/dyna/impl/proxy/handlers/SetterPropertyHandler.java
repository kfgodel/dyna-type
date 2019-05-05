package info.kfgodel.dyna.impl.proxy.handlers;

import info.kfgodel.dyna.impl.proxy.invocation.DynaMethodInvocationHandler;
import info.kfgodel.dyna.impl.proxy.invocation.DynaTypeMethodInvocation;
import info.kfgodel.dyna.impl.proxy.invocation.result.HandledResult;
import info.kfgodel.dyna.impl.proxy.invocation.result.HandlingResult;
import info.kfgodel.dyna.impl.proxy.invocation.result.UnhandledResult;

import java.util.Optional;

/**
 * This handler tris to handle the method invocation as it were a property setter.<br>
 *   An argument is needed to be set
 * Date: 01/05/19 - 20:42
 */
public class SetterPropertyHandler implements DynaMethodInvocationHandler {

  public static final String SETTER_PREFIX = "set";

  @Override
  public HandlingResult tryToHandle(DynaTypeMethodInvocation invocation) {
    Optional<String> settedProperty = invocation.getSubstringOnMethodNameAfter(SETTER_PREFIX);
    if (settedProperty.isPresent() && invocation.getArgumentCount() == 1) {
      String propertyName = settedProperty.get();
      Object firstArgument = invocation.getArgument(0);
      Object previousValue = invocation.getDynaState().put(propertyName, firstArgument);
      return HandledResult.create(previousValue);
    }
    return UnhandledResult.instance();
  }

  public static SetterPropertyHandler create() {
    SetterPropertyHandler handler = new SetterPropertyHandler();
    return handler;
  }

}
