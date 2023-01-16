package com.jackmeng.halite;

/**
 * This class details some of the common terms used for determining
 * the stringency of the property checker system. For example, how relaxed or
 * how strict the file existence checker should be.
 *
 * @author Jack Meng
 */
public enum const_Strict
{
  /**
   * Instruct that the program should proceed with provided Default Values if possible,
   * if no default value is able to be assumed, an exception would be thrown.
   */
  LENIENT,

  /**
   * Instruct that the program should thrown an exception when the desired property file
   * is not found. No default values are returned.
   */
  PANIC
}
