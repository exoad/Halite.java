package com.jackmeng.halite;

/**
 * Defines some way for a preferred way for a builder to "fault" or produce an
 * error.
 *
 * @author Jack Meng
 */
public enum use_FaultingStyle {
  /**
   * A panic_on_fault style means that whenever any of the following criteria is
   * met, the program will throw an exception:
   * <ul>
   * <li>Invalid property_value</li>
   * <li>Parsing error</li>
   * </ul>
   */
  PANIC_ON_FAULT,
  /**
   * This should be the default selected option for when any kind of fault(s) are
   * encountered in which, where everything is handled by the guards themselves.
   */
  IGNORE_ON_FAULT;
}
