package com.jackmeng.halite;

/**
 * Represents what kind of property file is being read.
 *
 * @author Jack Meng
 */
public enum use_PropertyStyle {
  YAML,
  /**
   * Use {@link java.util.Properties}
   */
  JAVA_UTIL_PROPERTIES;
}
