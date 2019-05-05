package info.kfgodel.dyna.object;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import info.kfgodel.dyna.DynaTestContext;
import info.kfgodel.dyna.impl.instantiator.DynaTypeInstantiator;
import info.kfgodel.dyna.testtypes.TestTypeWithAccessors;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This test verifies the definition for equality and hashcode on dyna objects
 * Date: 05/05/19 - 14:07
 */
@RunWith(JavaSpecRunner.class)
public class ObjectEqualityTest extends JavaSpec<DynaTestContext> {
  @Override
  public void define() {
    describe("a dyna object", () -> {
      test().objectWithAccessors(() -> DynaTypeInstantiator.create().instantiate(TestTypeWithAccessors.class, test().initialState()));
      test().initialState(HashMap::new);

      describe("by default", () -> {

        describe("compared to itself", () -> {
          test().otherObject(() -> test().objectWithAccessors());

          it("is equal", () -> {
            assertThat(test().objectWithAccessors()).isEqualTo(test().otherObject());
          });

          it("has the same hashcode", () -> {
            assertThat(test().objectWithAccessors().hashCode()).isEqualTo(test().otherObject().hashCode());
          });
        });

        describe("compared to other object", () -> {
          test().otherObject(() -> DynaTypeInstantiator.create().instantiate(TestTypeWithAccessors.class));

          it("is not equal", () -> {
            assertThat(test().objectWithAccessors()).isNotEqualTo(test().otherObject());
          });

          it("may have different hashcode ", () -> {
            // Because we use system hashcode we are pretty sure they are different, but this test may fail
            // randomly if two state instances happen to have the same system hashcode (possible but very rare)
            assertThat(test().objectWithAccessors().hashCode()).isNotEqualTo(test().otherObject().hashCode());
          });

        });

      });

      describe("when equals and hashcode are redefined", () -> {
        context().initialState(() -> Maps.newHashMap(
          ImmutableMap.<String, Object>builder()
            .put("equals", (Function) (other) -> true)
            .put("hashCode", (Supplier) () -> 3)
            .build()
        ));

        it("can be equal to anything", () -> {
          assertThat(test().objectWithAccessors()).isEqualTo("an object");
        });

        it("can have an arbitrary hashcode",()->{
          assertThat(test().objectWithAccessors().hashCode()).isEqualTo(3);
        });
      });


    });

  }
}