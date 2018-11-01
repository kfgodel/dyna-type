package info.kfgodel.dyna.testtypes;

/**
 * This type defines a name property with a pair of accessors
 * Date: 28/10/18 - 17:48
 */
public interface TestTypeWithAccessors {

  String getName();
  void setName(String aName);

  default String getTitledName() {
    return "Mr/Mrs. " + getName();
  }

}
