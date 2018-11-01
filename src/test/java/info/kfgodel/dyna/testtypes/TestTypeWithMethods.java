package info.kfgodel.dyna.testtypes;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * This type defines methods that can be implemented by lambdas
 * Date: 01/11/18 - 00:30
 */
public interface TestTypeWithMethods {

  void runnable();

  void setRunnable(Runnable definition);

  String supplier();

  void setSupplier(Supplier<String> definition);

  void consumer(String value);

  void setConsumer(Consumer<String> definition);

  void biConsumer(String value1, String value2);

  void setBiConsumer(BiConsumer<String, String> definition);

  String function(String value);

  void setFunction(Function<String, String> definition);

  String biFunction(String value1, String value2);

  void setBiFunction(BiFunction<String, String, String> definition);
}
