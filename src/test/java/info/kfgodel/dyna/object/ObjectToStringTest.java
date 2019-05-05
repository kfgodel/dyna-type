package info.kfgodel.dyna.object;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import info.kfgodel.dyna.DynaTestContext;
import info.kfgodel.dyna.impl.instantiator.DynaTypeInstantiator;
import info.kfgodel.dyna.testtypes.TestTypeWithAccessors;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Date: 05/05/19 - 15:34
 */
@RunWith(JavaSpecRunner.class)
public class ObjectToStringTest extends JavaSpec<DynaTestContext> {
  @Override
  public void define() {
    describe("a dyna object", () -> {
      test().objectWithAccessors(() -> DynaTypeInstantiator.createDefault().instantiate(TestTypeWithAccessors.class, test().initialState()));
      context().initialState(() -> Maps.newHashMap(
        ImmutableMap.<String, Object>builder()
          .put("name", "Hello")
          .build()
      ));

      it("has a toString implementation that relies on the state", () -> {
        assertThat(test().objectWithAccessors().toString()).isEqualTo("TestTypeWithAccessors{name=Hello}");
      });

    });

  }
}