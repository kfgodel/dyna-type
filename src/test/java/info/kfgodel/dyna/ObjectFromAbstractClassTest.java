package info.kfgodel.dyna;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import info.kfgodel.dyna.impl.instantiator.DynaTypeInstantiator;
import info.kfgodel.dyna.testtypes.AbstractClass;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Date: 04/05/19 - 15:50
 */
@RunWith(JavaSpecRunner.class)
public class ObjectFromAbstractClassTest extends JavaSpec<DynaTestContext> {
  @Override
  public void define() {
    describe("a dyna object created from an abstract class", () -> {
      test().abstractObject(() -> DynaTypeInstantiator.create().instantiate(AbstractClass.class));

      it("has its own state data", () -> {
        test().abstractObject().setName("a name");
        assertThat(test().abstractObject().getName()).isEqualTo("a name");
      });

      it("can have computed properties", () -> {
        test().abstractObject().setName("X");
        assertThat(test().abstractObject().getEntitledName()).isEqualTo("Mr. X");
      });
    });

  }
}