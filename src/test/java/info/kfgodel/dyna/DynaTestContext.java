package info.kfgodel.dyna;

import ar.com.dgarcia.javaspec.api.contexts.TestContext;
import info.kfgodel.dyna.impl.instantiator.DynaTypeInstantiator;
import info.kfgodel.dyna.testtypes.TestType;
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

  TestType aTestObject();

  void aTestObject(Supplier<TestType> definition);

  TestType otherObject();

  void otherObject(Supplier<TestType> definition);

  Map<String, Object> anObjectState();

  void anObjectState(Supplier<Map<String, Object>> definition);

  Map<String, Object> otherObjectState();

  void otherObjectState(Supplier<Map<String, Object>> definition);

  Boolean comparisonResult();

  void comparisonResult(Supplier<Boolean> definition);


}
