package info.kfgodel.dyna;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.dgarcia.javaspec.api.variable.Variable;
import info.kfgodel.dyna.impl.instantiator.DynaTypeInstantiator;
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

      it("returns the current state value of a property when invoking its undefined getter accessor", () -> {
        String name = context().objectWithAccessors().getName();
        assertThat(name).isNull();
      });

      it("changes the current state value using the undefined property setter accessor", () -> {
        context().objectWithAccessors().setName("a new name");
        assertThat(context().objectWithAccessors().getName()).isEqualTo("a new name");
      });

      it("invokes a default method definition", () -> {
        context().objectWithAccessors().setName("BareName");
        assertThat(context().objectWithAccessors().getTitledName()).isEqualTo("Mr/Mrs. BareName");
      });

      describe("when a state property value is a lambda and an undefined method with the same name gets called", () -> {
        describe("if the lambda is a Runnable", () -> {
          Variable<Boolean> executed = Variable.of(false);
          beforeEach(() -> {
            context().objectWithAccessors().setRunnable(() -> executed.set(true));
          });
          it("is used to implement as the method behavior", () -> {
            context().objectWithAccessors().runnable();

            assertThat(executed.get()).isTrue();
          });
          xit("ignores any method arguments", () -> {

          });
          xit("always returns null", () -> { //primitives?

          });
        });


      });
    });

  }
}