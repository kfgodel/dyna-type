package info.kfgodel.dyna.testtypes;

/**
 * This type defines methods that can be implemented by lambdas
 * Date: 01/11/18 - 00:30
 */
public interface TestTypeWithMethods {

  void runnable();

  String supplier();

  void consumer(String value);

  void biConsumer(String value1, String value2);

  String function(String value);

  String biFunction(String value1, String value2);
}
