package com.jackmeng.halite;

/**
 * Just an abstraction for a specific Halite Exception??
 *
 * @author Jack Meng
 */
public class use_HaliteFault
    extends Exception
{

  public static String $fault0_1()
  {
    return "FilePromise_Denied_RealType";
  }

  public static String $fault0_2()
  {
    return "FileRead_Corrupt_RealType";
  }

  public static String $fault0_3()
  {
    return "PropertyName_Unmatched_CritType";
  }

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
    new use_HaliteFault(cause).printStackTrace();
  }
}
