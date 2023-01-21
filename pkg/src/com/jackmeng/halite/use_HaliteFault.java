package com.jackmeng.halite;

import com.jackmeng.halite.core.l0;

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
    l0.LOG.push(cause);
    new use_HaliteFault("\n===================================\n" + cause + "\n===================================")
        .printStackTrace();
  }
}
