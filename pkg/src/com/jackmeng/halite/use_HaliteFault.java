package com.jackmeng.halite;

/**
 * Just an abstraction for a specific Halite Exception??
 *
 * @author Jack Meng
 */
public class use_HaliteFault
    extends Exception
{

  /**
   * @param cause
   *          Super
   */
  public use_HaliteFault(String cause)
  {
    super(cause);
  }

  /**
   * Static
   *
   * @param cause
   *          Super
   */
  public static void launch_fault(final String cause)
  {
    new use_HaliteFault("\n===================================\n" + cause + "\n===================================")
        .printStackTrace();
  }
}
