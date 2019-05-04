package info.kfgodel.dyna.testtypes;

/**
 * This class acts as an example for tests on an interface-like type that can have private methods
 * Date: 04/05/19 - 15:51
 */
public abstract class AbstractClass extends AbstractSuperClass implements TestContract {

  public abstract void setName(String aName);

  protected String getTitle() {
    return "Mr.";
  }

}
