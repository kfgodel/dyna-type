package info.kfgodel.dyna;

import ar.com.dgarcia.javaspec.api.contexts.TestContext;
import info.kfgodel.dyna.impl.instantiator.DynaTypeInstantiator;
import info.kfgodel.dyna.testtypes.AbstractClass;
import info.kfgodel.dyna.testtypes.TestTypeWithAccessors;
import info.kfgodel.dyna.testtypes.TestTypeWithMethods;

import java.util.Map;
import java.util.function.Supplier;

/**
 * Date: 28/10/18 - 17:43
 */
public interface DynaTestContext extends TestContext {

  TestTypeWithAccessors objectWithAccessors();
  void objectWithAccessors(Supplier<TestTypeWithAccessors> definition);

  TestTypeWithMethods objectWithMethods();
  void objectWithMethods(Supplier<TestTypeWithMethods> definition);

  DynaTypeInstantiator instantiator();
  void instantiator(Supplier<DynaTypeInstantiator> definition);

  Map<String, Object> initialState();
  void initialState(Supplier<Map<String, Object>> definition);

  String propertyName();
  void propertyName(Supplier<String> definition);

  <L> L lambda();
  <L> void lambda(Supplier<L> definition);

  AbstractClass abstractObject();
  void abstractObject(Supplier<AbstractClass> definition);


}
