package info.kfgodel.dyna.api.instantiator;

import java.util.Map;

/**
 * This interface is implemented by all created instances of dyna types
 * Date: 07/12/18 - 01:43
 */
public interface DynaObject {

  /**
   * Returns the state instance that represents the identity of this object
   *
   * @return The map with the internal state
   */
  Map<String, Object> getState();
}
