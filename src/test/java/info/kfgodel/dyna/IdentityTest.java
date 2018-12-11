package info.kfgodel.dyna;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import info.kfgodel.dyna.api.instantiator.DynaObject;
import info.kfgodel.dyna.impl.instantiator.DynaTypeInstantiator;
import info.kfgodel.dyna.testtypes.TestType;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Date: 07/12/18 - 01:22
 */
@RunWith(JavaSpecRunner.class)
public class IdentityTest extends JavaSpec<DynaTestContext> {
  @Override
  public void define() {
    describe("a dyna object", () -> {
      context().aTestObject(() -> DynaTypeInstantiator.create().instantiate(TestType.class, context().anObjectState()));

      describe("when compared to other object", () -> {
        context().otherObject(() -> DynaTypeInstantiator.create().instantiate(TestType.class, context().otherObjectState()));

        describe("for identity", () -> {
          context().comparisonResult(() -> context().aTestObject() == context().otherObject());

          it("is different regardless of its state", () -> {
            context().anObjectState(() -> mock(Map.class));
            context().otherObjectState(() -> mock(Map.class));

            assertThat(context().comparisonResult()).isFalse();
          });
        });

        describe("for equality", () -> {
          context().comparisonResult(() -> context().aTestObject().equals(context().otherObject()));

          it("is equal if the state is the same instance", () -> {
            HashMap<String, Object> state = new HashMap<>();
            context().anObjectState(() -> state);
            context().otherObjectState(() -> state);

            assertThat(context().comparisonResult()).isTrue();
          });

          it("is different if the state is different instance (regardless of state equality)", () -> {
            context().anObjectState(() -> new HashMap<>());
            context().otherObjectState(() -> new HashMap<>());

            assertThat(context().comparisonResult()).isFalse();
            assertThat(context().anObjectState()).isEqualTo(context().otherObjectState());
          });

          it("can override its equality definition", () -> {
            context().anObjectState(() -> new HashMap<>());
            context().otherObjectState(() -> new HashMap<>());

            // Equals if an instance of DynaObject
            Function<Object, Boolean> equality = DynaObject.class::isInstance;
            context().anObjectState().put("equals", equality);

            assertThat(context().comparisonResult()).isTrue();
          });

        });


      });

    });

  }

  private Map<String, Object> createStateWith(String name, String age) {
    HashMap<String, Object> map = new HashMap<>();
    map.put("nombre", name);
    map.put("edad", age);
    return map;
  }
}