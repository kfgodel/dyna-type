package info.kfgodel.dyna;

import ar.com.dgarcia.javaspec.api.contexts.TestContext;
import info.kfgodel.dyna.impl.instantiator.DynaTypeInstantiator;

import java.util.function.Supplier;

/**
 * Date: 28/10/18 - 17:43
 */
public interface DynaTestContext extends TestContext {

  TestTypeWithAccessors objectWithAccessors();
  void objectWithAccessors(Supplier<TestTypeWithAccessors> definition);

  DynaTypeInstantiator instantiator();
  void instantiator(Supplier<DynaTypeInstantiator> definition);

}
