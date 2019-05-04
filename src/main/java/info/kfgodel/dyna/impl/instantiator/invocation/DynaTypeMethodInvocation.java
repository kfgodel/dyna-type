package info.kfgodel.dyna.impl.instantiator.invocation;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

/**
 * This type represents the invocation of a dynatype object method.<br>
 *   It captures all the invocation context including the dyna state
 * Date: 01/05/19 - 19:24
 */
public class DynaTypeMethodInvocation {

  private Object invokedProxy;
  private Method invokedMethod;
  private Object[] passedArguments;
  private Map<String, Object> dynaState;

  public static DynaTypeMethodInvocation create(Object invokedProxy, Method invokedMethod, Object[] passedArguments, Map<String, Object> dynaState) {
    DynaTypeMethodInvocation invocation = new DynaTypeMethodInvocation();
    invocation.invokedProxy = invokedProxy;
    invocation.invokedMethod = invokedMethod;
    invocation.passedArguments = passedArguments;
    invocation.dynaState = dynaState;
    return invocation;
  }

  @Override
  public String toString() {
    return "DynaTypeMethodInvocation{" +
      "invokedMethod=" + invokedMethod.getDeclaringClass().getSimpleName() + "." + invokedMethod.getName() +
      ", passedArguments=" + Arrays.toString(passedArguments) +
      '}';
  }

  public Map<String, Object> getDynaState() {
    return dynaState;
  }

  /**
   * Tries to extract from the invoked method name the suffix string left after the indicated prefix.<br>
   *   If the invoked method name doesn't start with the given prefix or the resulting string is empty, then an empty optional is returned.<br>
   *   Otherwise the rest of the method name is returned inside the optional. First letter is assured to be lower case
   * @param prefix The prefix to substract from the method name
   * @return The rest of the method name or empty if the method doesn't start with the prefix
   */
  public Optional<String> getSubstringOnMethodNameAfter(String prefix) {
    if (!getMethodName().startsWith(prefix)) {
      // Doesn't even have the prefix
      return Optional.empty();
    }
    String withoutPrefix = getMethodName().substring(prefix.length());
    if (withoutPrefix.isEmpty()) {
      // It doesn't contain more characters after the prefix
      return Optional.empty();
    }
    String lowerFirstChar = withoutPrefix.substring(0, 1).toLowerCase();
    String restOfChars = withoutPrefix.substring(lowerFirstChar.length());
    String propertyName = lowerFirstChar + restOfChars;
    return Optional.of(propertyName);
  }

  public String getMethodName() {
    return invokedMethod.getName();
  }

  public int getArgumentCount() {
    return passedArguments.length ;
  }

  public Object getArgument(int argumentIndex) {
    if (passedArguments == null || argumentIndex >= passedArguments.length) {
      return null;
    }
    return passedArguments[argumentIndex];
  }
}
