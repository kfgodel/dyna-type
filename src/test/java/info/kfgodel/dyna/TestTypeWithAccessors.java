package info.kfgodel.dyna;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * This type defines a name property with a pair of accessors
 * Date: 28/10/18 - 17:48
 */
public interface TestTypeWithAccessors {

  String getName();
  void setName(String aName);

  default String getTitledName() {
    return "Mr/Mrs. " + getName();
  }

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
