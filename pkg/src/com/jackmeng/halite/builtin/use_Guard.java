package com.jackmeng.halite.builtin;

import com.jackmeng.halite.impl_PGuard;

/**
 * This class provides some very generic and simple PropertyGuards that
 * can be used without having to implement your own.
 *
 * It is highly recommended to make your own instead of relying on these premade
 * ones for more sophisticated guards.
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

  /**
   * Checks if a boolean expression is matched using the inbuilt Boolean.parse;
   * This is not as reliable
   * instead seek
   */
  public static final impl_PGuard BOOLEAN = Boolean::parseBoolean;

  /**
   * This premade guard checks for a binary BOOLEAN expression. This is much
   * better than {@link #BOOLEAN} which uses
   * the inbuilt Boolean.parse which has way less options.
   */
  public static final impl_PGuard BETTER_BOOL = x -> x.equalsIgnoreCase("false") || x.equalsIgnoreCase("true")
      || x.equalsIgnoreCase("1") || x.equalsIgnoreCase("0") || x.equalsIgnoreCase("yes") || x.equalsIgnoreCase("no")
      || x.equalsIgnoreCase("on") || x.equalsIgnoreCase("off");

  /**
   * Checks if a numerical expression is matched using the inbuilt Integer.parse;
   */
  public static final impl_PGuard INTEGER = e -> {
    try
    {
      Integer.parseInt(e);
    } catch (Exception ex)
    {
      return false;
    }
    return true;
  };
}
