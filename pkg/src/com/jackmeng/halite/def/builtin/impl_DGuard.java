package com.jackmeng.halite.def.builtin;

/**
 * An interface used to define a guard used for certain validation operations.
 * All
 * of which are primarily used for property validation following a general
 * layout of
 * If TRUE, return VALUE, else DEFAULT.
 *
 * By most common specifications, guards should follow the following criterions:
 * <br>
 * <ul>
 * <li>If property_value is empty, return DEFAULT</li>
 * <li>If property_value is invalid, return DEFAULT</li>
 * <li>If property_name is non-existent, return DEFAULT unless property_name is
 * not registered</li>
 * <li>Else property_value and property_name is registered and valid, return
 * property_value</li>
 * </ul><br>
 * DEFAULT should be defined as a "public static final" constant during
 * implementation if
 * possible.
 *
 * There are pre-implemented guards which you can find in
 * {@link com.jackmeng.halite.def.builtin}
 *
 * @author Jack Meng
 */
@FunctionalInterface public interface impl_DGuard
{

  /**
   * All guards must implement this function which provides a clear validation
   * pathway
   * provided a "String" based supplement to check and perform on.
   *
   * @param supplement
   *          The property_value
   * @return true or false. True -> Valid property_value. False -> Invalid
   *         property_value
   */
  boolean check(String supplement);
}
