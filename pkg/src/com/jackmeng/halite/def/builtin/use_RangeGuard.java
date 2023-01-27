package com.jackmeng.halite.def.builtin;

/**
 * Presents a range guard which bounds/range of [minimum, maximum] with
 * square brackets representing inclusive of both bounds.
 *
 * This guard is numeric.
 *
 * @author Jack Meng
 */
public final class use_RangeGuard
    implements
    impl_DGuard
{
  private double minimum, maximum;

  /**
   * Constructor
   *
   * @param minimum
   *          Minimum value (inclusive)
   * @param maximum
   *          Maximum Value (inclusive)
   */
  public use_RangeGuard(double minimum, double maximum)
  {
    this.minimum = minimum;
    this.maximum = maximum;
  }

  /**
   * Set Minimum value
   *
   * @param e
   *          val
   */
  public void minimum(double e)
  {
    this.minimum = e;
  }

  /**
   * Get Minimum value
   *
   * @return val
   */
  public double minimum()
  {
    return minimum;
  }

  /**
   * Get Maximum value
   *
   * @return val
   */
  public double maximum()
  {
    return maximum;
  }

  /**
   * Set Maximum value
   *
   * @param e
   *          val
   */
  public void maximum(double e)
  {
    this.maximum = e;
  }

  @Override public boolean check(String supplement)
  {
    double e = Double.parseDouble(supplement);
    return minimum <= e && e <= maximum;
  }
}
