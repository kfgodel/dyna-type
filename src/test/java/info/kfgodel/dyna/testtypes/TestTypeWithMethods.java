package info.kfgodel.dyna.testtypes;

import info.kfgodel.dyna.api.instantiator.DynaObject;

/**
 * This type defines methods that can be implemented by lambdas
 * Date: 01/11/18 - 00:30
 */
public interface TestTypeWithMethods extends DynaObject {

  void runnable();

  void methodWithoutReturn();

  void consumer(String value);

  void methodWithoutReturn(String value);

  void biConsumer(String value1, String value2);

  void methodWithoutReturn(String value1, String value2);

  void methodWithoutReturn(String value1, String value2, String value3);

  String supplier();

  String methodWithReturn();

  String function(String value);

  String methodWithReturn(String value1);

  String biFunction(String value1, String value2);

  String methodWithReturn(String value1, String value2);

  String methodWithReturn(String value1, String value2, String value3);
}
