package info.kfgodel.dyna.instantiator;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import info.kfgodel.dyna.DynaTestContext;
import info.kfgodel.dyna.impl.instantiator.DynaTypeInstantiator;
import info.kfgodel.dyna.testtypes.TestTypeWithAccessors;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Date: 28/10/18 - 17:58
 */
@RunWith(JavaSpecRunner.class)
public class DynaTypeInstantiatorTest extends JavaSpec<DynaTestContext> {
  @Override
  public void define() {
    describe("a dyna instantiator", () -> {
      context().instantiator(DynaTypeInstantiator::create);

      it("creates an instance of the given interface", () -> {
        TestTypeWithAccessors instance = context().instantiator().instantiate(TestTypeWithAccessors.class);
        assertThat(instance).isNotNull();
      });

      itThrows(IllegalArgumentException.class, "when used for primitive types", () -> {
        context().instantiator().instantiate(String.class);
      }, (e) -> {
        assertThat(e).hasMessage("Cannot subclass primitive, array or final types: class java.lang.String");
      });
    });

  }
}