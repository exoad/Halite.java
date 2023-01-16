package com.jackmeng.halite.builtin;

import com.jackmeng.halite.impl_PGuard;

/**
 * This class provides some very generic and simple PropertyGuards that
 * can be used without having to implement your own.
 *
 * @author Jack Meng
 */
public final class use_Guard
{
  private use_Guard()
  {
  }

  /**
   * An always true guard that doesn't care what gets inputted and will always
   * say that the inputted property value is VALID.
   */
  public static final impl_PGuard ALWAYS_TRUE = e -> true;

  /**
   * An always false guard that doesn't care what gets inputted and will always
   * say that the inputted property value is INVALID.
   */
  public static final impl_PGuard ALWAYS_FALSE = e -> false;
}
