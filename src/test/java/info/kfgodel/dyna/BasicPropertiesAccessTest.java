package info.kfgodel.dyna;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import info.kfgodel.dyna.impl.instantiator.DynaTypeInstantiator;
import info.kfgodel.dyna.testtypes.TestTypeWithAccessors;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This test verifies how object properties can be accessed
 * Date: 28/10/18 - 17:47
 */
@RunWith(JavaSpecRunner.class)
public class BasicPropertiesAccessTest extends JavaSpec<DynaTestContext> {
  @Override
  public void define() {
    describe("a dyna object with property accessor methods", () -> {
      context().objectWithAccessors(() -> DynaTypeInstantiator.create().instantiate(TestTypeWithAccessors.class, context().initialState()));
      context().initialState(() -> Maps.newHashMap(
        ImmutableMap.<String, Object>builder()
          .put("name", "Hello")
          .build()
      ));

      it("returns the current value of a property when invoking an undefined getter method", () -> {
        String name = context().objectWithAccessors().getName();
        assertThat(name).isEqualTo("Hello");
      });
      it("sets a property when invoking an undefined setter method", () -> {
        context().objectWithAccessors().setName("a new name");
        assertThat(context().objectWithAccessors().getName()).isEqualTo("a new name");
      });

      it("uses the method definition instead of getting a property value if there's a default one", () -> {
        String name = context().objectWithAccessors().getSurname();
        assertThat(name).isEqualTo("World");
      });
      it("uses the method definition instead of setting a property value if there's a default one", () -> {
        context().objectWithAccessors().setSurname("a new surname");
        assertThat(context().objectWithAccessors().getSurname()).isEqualTo("World");
      });

      it("allows definition of calculated properties combining both options for getting values", () -> {
        assertThat(context().objectWithAccessors().getFullName()).isEqualTo("Hello World!");
      });
      it("allows definition of calculated properties combining both options for setting values", () -> {
        context().objectWithAccessors().setFullName("Changed SurnameIgnored");
        assertThat(context().objectWithAccessors().getName()).isEqualTo("Changed");
        assertThat(context().objectWithAccessors().getSurname()).isEqualTo("World");
      });



    });

  }
}