package info.kfgodel.dyna;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.dgarcia.javaspec.api.variable.Variable;
import info.kfgodel.dyna.impl.instantiator.DynaTypeInstantiator;
import info.kfgodel.dyna.testtypes.TestTypeWithMethods;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Date: 01/11/18 - 00:28
 */
@RunWith(JavaSpecRunner.class)
public class MethodExecutionTest extends JavaSpec<DynaTestContext> {
  @Override
  public void define() {
    describe("a dyna object", () -> {
      context().objectWithMethods(() -> DynaTypeInstantiator.create().instantiate(TestTypeWithMethods.class));

      describe("when a state property value is a lambda and an undefined method with the same name gets called", () -> {
        describe("if the lambda is a Runnable", () -> {
          Variable<Boolean> executed = Variable.of(false);
          beforeEach(() -> {
            context().objectWithMethods().setRunnable(() -> executed.set(true));
          });
          it("is used to implement as the method behavior", () -> {
            context().objectWithMethods().runnable();

            assertThat(executed.get()).isTrue();
          });
          xit("ignores any method arguments", () -> {

          });
          xit("always returns null", () -> { //primitives?

          });
        });

        describe("if the lambda is a Supplier", () -> {
          beforeEach(() -> {
            context().objectWithMethods().setSupplier(() -> "result");
          });
          it("is used to implement as the method behavior", () -> {
            String result = context().objectWithMethods().supplier();

            assertThat(result).isEqualTo("result");
          });
          xit("ignores any method arguments", () -> {

          });
        });

        describe("if the lambda is a Consumer", () -> {
          Variable<String> capturedValue = Variable.of(null);
          beforeEach(() -> {
            context().objectWithMethods().setConsumer(capturedValue::set);
          });
          it("is used to implement as the method behavior", () -> {
            context().objectWithMethods().consumer("a value");

            assertThat(capturedValue.get()).isEqualTo("a value");
          });
          xit("ignores any additional method arguments", () -> {

          });
          xit("always returns null", () -> { //primitives?

          });
        });

        describe("if the lambda is a Function", () -> {
          beforeEach(() -> {
            context().objectWithMethods().setFunction((value) -> value + "&" + value);
          });
          it("is used to implement as the method behavior", () -> {
            String result = context().objectWithMethods().function("aValue");

            assertThat(result).isEqualTo("aValue&aValue");
          });
          xit("ignores any additional method arguments", () -> {

          });
        });

        describe("if the lambda is a BiConsumer", () -> {
          Variable<String> capturedValue = Variable.of(null);
          beforeEach(() -> {
            context().objectWithMethods().setBiConsumer((firstArg, secondArg) -> capturedValue.set(firstArg + " " + secondArg));
          });
          it("is used to implement as the method behavior", () -> {
            context().objectWithMethods().biConsumer("1", "2");

            assertThat(capturedValue.get()).isEqualTo("1 2");
          });
          xit("ignores any additional method arguments", () -> {

          });
          xit("always returns null", () -> { //primitives?

          });
        });

        describe("if the lambda is a BiFunction", () -> {
          beforeEach(() -> {
            context().objectWithMethods().setBiFunction((firstArg, secondArg) -> firstArg + " " + secondArg);
          });
          it("is used to implement as the method behavior", () -> {
            String result = context().objectWithMethods().biFunction("A", "B");

            assertThat(result).isEqualTo("A B");
          });
          xit("ignores any additional method arguments", () -> {

          });
        });

      });

    });

  }
}