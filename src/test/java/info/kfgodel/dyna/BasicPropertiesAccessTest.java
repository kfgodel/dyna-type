package info.kfgodel.dyna;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import info.kfgodel.dyna.impl.instantiator.DynaTypeInstantiator;
import info.kfgodel.dyna.testtypes.TestTypeWithAccessors;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Date: 28/10/18 - 17:47
 */
@RunWith(JavaSpecRunner.class)
public class BasicPropertiesAccessTest extends JavaSpec<DynaTestContext> {
  @Override
  public void define() {
    describe("a dyna object with property accessors", () -> {
      context().objectWithAccessors(() -> DynaTypeInstantiator.create().instantiate(TestTypeWithAccessors.class));

      it("returns the current initialState value of a property when invoking its undefined getter accessor", () -> {
        String name = context().objectWithAccessors().getName();
        assertThat(name).isNull();
      });

      it("changes the current initialState value using the undefined property setter accessor", () -> {
        context().objectWithAccessors().setName("a new name");
        assertThat(context().objectWithAccessors().getName()).isEqualTo("a new name");
      });

      it("invokes a default method definition if present on the hierarchy", () -> {
        context().objectWithAccessors().setName("BareName");
        assertThat(context().objectWithAccessors().getTitledName()).isEqualTo("Mr/Mrs. BareName");
      });

    });

  }
}