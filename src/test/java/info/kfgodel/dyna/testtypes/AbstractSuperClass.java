package info.kfgodel.dyna.testtypes;

/**
 * This type serves as superclass for an abstract class
 * Date: 04/05/19 - 18:08
 */
public abstract class AbstractSuperClass implements TestContract {

  public String getEntitledName(){
    return getTitle() + " " + getName();
  }

  protected abstract String getTitle();

}
