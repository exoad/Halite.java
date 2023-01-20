package com.jackmeng.halite;

/**
 * A modifier is an implementation that can modify a property's set value
 * after it has been processed as "TRUE" or "VALID" by the desired
 * loader. This can be treated as a "after-down action," which parses
 * the property so that is fits a desired constraint or because of aliasing.
 *
 * This is extremely helpful for using a DefinitionBased Property Configuration
 * and less
 * so for a Model based.
 *
 * @author Jack Meng
 */
@FunctionalInterface public interface impl_Modifier< T extends CharSequence >
{
  /**
   * @param payload
   *          The desired CharSequence
   * @return The modified CharSequence
   */
  T modify(T payload);
}
