package info.kfgodel.dyna.testtypes;

/**
 * This type defines a name property with a pair of accessors
 * Date: 28/10/18 - 17:48
 */
public interface TestTypeWithAccessors {

  String getName();
  void setName(String aName);

  default String getSurname() {
    return "World";
  }

  default void setSurname(String value) {
    // Value is ignored
  }

  default String getFullName() {
    return getName() + " " + getSurname() + "!";
  }

  default void setFullName(String fullName) {
    String[] parts = fullName.split(" ");
    this.setName(parts[0]);
    this.setSurname(parts[1]);
  }

}
