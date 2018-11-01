package info.kfgodel.dyna;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.dgarcia.javaspec.api.variable.Variable;
import com.google.common.collect.ImmutableMap;
import info.kfgodel.dyna.impl.instantiator.DynaTypeInstantiator;
import info.kfgodel.dyna.testtypes.TestTypeWithMethods;
import org.junit.runner.RunWith;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Date: 01/11/18 - 00:28
 */
@RunWith(JavaSpecRunner.class)
public class MethodExecutionTest extends JavaSpec<DynaTestContext> {
  @Override
  public void define() {
    describe("a dyna object", () -> {
      context().objectWithMethods(() -> DynaTypeInstantiator.create().instantiate(TestTypeWithMethods.class, context().initialState()));

      describe("with a lambda as a property value", () -> {
        context().initialState(() -> ImmutableMap.<String, Object>builder()
          .put(context().propertyName(), context().lambda())
          .build()
        );


        describe("if the lambda is a runnable", () -> {
          context().propertyName(() -> "runnable");

          Variable<Boolean> executed = Variable.of(false);
          beforeEach(() -> {
            executed.set(false);
            context().lambda(() -> (Runnable) () -> executed.set(true));
          });

          it("uses the lambda as the behavior of an undefined method with the same name", () -> {
            context().objectWithMethods().runnable();

            assertThat(executed.get()).isTrue();
          });

          xit("ignores any method arguments", () -> {

          });
          xit("always returns null", () -> { //primitives?

          });

        });

        describe("if the lambda is a Supplier", () -> {
          context().propertyName(() -> "supplier");
          beforeEach(() -> {
            context().lambda(() -> (Supplier) () -> "result");
          });
          it("is used to implement as the method behavior", () -> {
            String result = context().objectWithMethods().supplier();

            assertThat(result).isEqualTo("result");
          });
          xit("ignores any method arguments", () -> {

          });
        });

        describe("if the lambda is a Consumer", () -> {
          context().propertyName(() -> "consumer");

          Variable<String> capturedValue = Variable.of(null);
          beforeEach(() -> {
            capturedValue.set(null);
            context().lambda(() -> (Consumer<String>) capturedValue::set);
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
          context().propertyName(() -> "function");
          beforeEach(() -> {
            context().lambda(() -> (Function) (value) -> value + "&" + value);
          });
          it("is used to implement as the method behavior", () -> {
            String result = context().objectWithMethods().function("aValue");

            assertThat(result).isEqualTo("aValue&aValue");
          });
          xit("ignores any additional method arguments", () -> {

          });
        });

        describe("if the lambda is a BiConsumer", () -> {
          context().propertyName(() -> "biConsumer");
          Variable<String> capturedValue = Variable.of(null);
          beforeEach(() -> {
            capturedValue.set(null);
            context().lambda(() -> (BiConsumer) (firstArg, secondArg) -> capturedValue.set(firstArg + " " + secondArg));
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
          context().propertyName(() -> "biFunction");
          beforeEach(() -> {
            context().lambda(() -> (BiFunction) (firstArg, secondArg) -> firstArg + " " + secondArg);
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