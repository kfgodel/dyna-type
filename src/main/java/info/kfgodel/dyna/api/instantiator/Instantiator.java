package info.kfgodel.dyna.api.instantiator;

/**
 * This type defines a protocol for instantiating objects
 * Date: 28/10/18 - 17:50
 */
public interface Instantiator {
  /**
   * Creates an instance of the given type.<br>
   * This object may decide how to create the instance, or use an previously existing one
   *
   * @param expectedInstanceType A class token to indicate the expected type
   * @param <T>                  The type of expected result
   * @return An instance of the expected type
   */
  <T> T instantiate(Class<T> expectedInstanceType);
}
