package info.kfgodel.dyna.instantiator;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import com.google.common.collect.Lists;
import info.kfgodel.dyna.DynaTestContext;
import info.kfgodel.dyna.impl.instantiator.DefaultConfiguration;
import info.kfgodel.dyna.impl.instantiator.DynaTypeInstantiator;
import info.kfgodel.dyna.impl.proxy.invocation.DynaMethodInvocationHandler;
import info.kfgodel.dyna.impl.proxy.invocation.result.HandledResult;
import info.kfgodel.dyna.testtypes.TestContract;
import info.kfgodel.dyna.testtypes.TestTypeWithAccessors;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Date: 28/10/18 - 17:58
 */
@RunWith(JavaSpecRunner.class)
public class DynaTypeInstantiatorTest extends JavaSpec<DynaTestContext> {
  @Override
  public void define() {
    describe("a dyna instantiator", () -> {
      context().instantiator(DynaTypeInstantiator::createDefault);

      it("creates an instance of the given interface", () -> {
        TestTypeWithAccessors instance = context().instantiator().instantiate(TestTypeWithAccessors.class);
        assertThat(instance).isNotNull();
      });

      itThrows(IllegalArgumentException.class, "when used for primitive types", () -> {
        context().instantiator().instantiate(String.class);
      }, (e) -> {
        assertThat(e).hasMessage("Cannot subclass primitive, array or final types: class java.lang.String");
      });

      it("can be configured to make created objects implement additional interfaces",()->{
        DynaTypeInstantiator instantiator = DynaTypeInstantiator.create(DefaultConfiguration.create()
          .withInterface(TestContract.class)
        );
        TestTypeWithAccessors created = instantiator.instantiate(TestTypeWithAccessors.class);
        assertThat(created).isInstanceOf(TestContract.class);
      });

      it("can be configured to make created objects behave with custom handlers",()->{
        DynaTypeInstantiator instantiator = DynaTypeInstantiator.create(DefaultConfiguration.create()
          .setChainOfHandlers(Lists.newArrayList(returnMethodNameHandler()))
        );

        TestTypeWithAccessors created = instantiator.instantiate(TestTypeWithAccessors.class);
        assertThat(created.getName()).isEqualTo("getName");
      });
    });

  }

  public DynaMethodInvocationHandler returnMethodNameHandler() {
    return (invocation)-> HandledResult.create(invocation.getMethodName());
  }
}