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
 * This test verifies that a lambda can be used to define a method implementation
 * Date: 01/11/18 - 00:28
 */
@RunWith(JavaSpecRunner.class)
public class LambdaForMethodExecutionTest extends JavaSpec<DynaTestContext> {
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
          context().propertyName(() -> "methodWithoutReturn");

          Variable<Boolean> executed = Variable.of(false);
          beforeEach(() -> {
            executed.set(false);
            context().lambda(() -> (Runnable) () -> executed.set(true));
          });

          it("executes the lambda when an undefined method is called having the same property name", () -> {
            context().objectWithMethods().methodWithoutReturn();

            assertThat(executed.get()).isTrue();
          });

          it("ignores any arguments the method may have", () -> {
            context().objectWithMethods().methodWithoutReturn("ignored", "and this too");

            assertThat(executed.get()).isTrue();
          });
          it("always returns null if the method has a return value", () -> { //primitives?
            context().propertyName(() -> "methodWithReturn");

            String result = context().objectWithMethods().methodWithReturn();

            assertThat(result).isNull();
          });

        });

        describe("if the lambda is a Supplier", () -> {
          context().propertyName(() -> "methodWithReturn");
          beforeEach(() -> {
            context().lambda(() -> (Supplier) () -> "result");
          });
          it("executes the lamda to get the result of an undefined method having the same property name", () -> {
            String result = context().objectWithMethods().methodWithReturn();

            assertThat(result).isEqualTo("result");
          });
          it("ignores any arguments the method may have", () -> {
            String result = context().objectWithMethods().methodWithReturn("ignored", "and this too");

            assertThat(result).isEqualTo("result");
          });
        });

        describe("if the lambda is a Consumer", () -> {
          context().propertyName(() -> "methodWithoutReturn");

          Variable<String> capturedValue = Variable.of(null);
          beforeEach(() -> {
            capturedValue.set(null);
            context().lambda(() -> (Consumer<String>) capturedValue::set);
          });
          it("executes the lambda when an undefined method is called having the same property name", () -> {
            context().objectWithMethods().methodWithoutReturn("a value");

            assertThat(capturedValue.get()).isEqualTo("a value");
          });
          it("ignores any additional method arguments", () -> {
            context().objectWithMethods().methodWithoutReturn("a value", "ignored");

            assertThat(capturedValue.get()).isEqualTo("a value");
          });
          it("always returns null if the method has a return value", () -> { //primitives?
            context().propertyName(() -> "methodWithReturn");

            String result = context().objectWithMethods().methodWithReturn("a value");

            assertThat(result).isNull();
          });
          it("passes null to the lambda if the method doesn't have enough arguments", () -> {
            context().objectWithMethods().methodWithoutReturn();

            assertThat(capturedValue.get()).isNull();
          });
        });

        describe("if the lambda is a Function", () -> {
          context().propertyName(() -> "methodWithReturn");
          beforeEach(() -> {
            context().lambda(() -> (Function) (value) -> value + "++");
          });
          it("executes the lambda when an undefined method is called having the same property name", () -> {
            String result = context().objectWithMethods().methodWithReturn("a value");

            assertThat(result).isEqualTo("a value++");
          });
          it("ignores any additional method arguments", () -> {
            String result = context().objectWithMethods().methodWithReturn("a value", "ignored");

            assertThat(result).isEqualTo("a value++");
          });
          it("passes null to the lambda if the method doesn't have enough arguments", () -> {
            String result = context().objectWithMethods().methodWithReturn();

            assertThat(result).isEqualTo("null++");
          });
        });

        describe("if the lambda is a BiConsumer", () -> {
          context().propertyName(() -> "methodWithoutReturn");
          Variable<String> capturedValue = Variable.of(null);
          beforeEach(() -> {
            capturedValue.set(null);
            context().lambda(() -> (BiConsumer) (firstArg, secondArg) -> capturedValue.set(firstArg + ", " + secondArg));
          });
          it("executes the lambda when an undefined method is called having the same property name", () -> {
            context().objectWithMethods().methodWithoutReturn("1", "2");

            assertThat(capturedValue.get()).isEqualTo("1, 2");
          });
          it("ignores any additional method arguments", () -> {
            context().objectWithMethods().methodWithoutReturn("1", "2", "3");

            assertThat(capturedValue.get()).isEqualTo("1, 2");
          });
          it("always returns null if the method has a return value", () -> { //primitives?
            context().propertyName(() -> "methodWithReturn");

            String result = context().objectWithMethods().methodWithReturn("1", "2");

            assertThat(result).isNull();
          });
          it("passes null to the lambda if the method doesn't have enough arguments", () -> {
            context().objectWithMethods().methodWithoutReturn();

            assertThat(capturedValue.get()).isEqualTo("null, null");
          });
        });

        describe("if the lambda is a BiFunction", () -> {
          context().propertyName(() -> "methodWithReturn");
          beforeEach(() -> {
            context().lambda(() -> (BiFunction) (firstArg, secondArg) -> firstArg + "&" + secondArg);
          });
          it("executes the lambda when an undefined method is called having the same property name", () -> {
            String result = context().objectWithMethods().methodWithReturn("A", "B");

            assertThat(result).isEqualTo("A&B");
          });
          it("ignores any additional method arguments", () -> {
            String result = context().objectWithMethods().methodWithReturn("A", "B", "C");

            assertThat(result).isEqualTo("A&B");
          });
          it("passes null to the lambda if the method doesn't have enough arguments", () -> {
            String result = context().objectWithMethods().methodWithReturn("A");

            assertThat(result).isEqualTo("A&null");
          });
        });

      });

    });

  }
}