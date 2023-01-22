package com.jackmeng.halite.model;

import java.io.File;

import com.jackmeng.halite.HaliteBuilder;
import com.jackmeng.halite.use_FaultingStyle;
import com.jackmeng.halite.core.l0;

/**
 * <p>
 * This is the Halite Model builder. A model builder uses a concrete
 * representation of the configuration file
 * passed as an "impl_Model" instance which should also have marked up
 * "is_Peekable" fields.
 *
 * <p>
 * Compared to a simpler Definition builder, the vagueness of what properties
 * are there and what are not and accessing them through code are all gone.
 * Everything is presented in a concrete fashion based on a marked-up model.
 *
 * @author Jack Meng
 * @see com.jackmeng.halite.model.impl_Model
 * @see com.jackmeng.halite.model.is_Peekable
 */
public final class use_HaliteModelBuilder
    implements
    HaliteBuilder
{

  private impl_Model model;
  private use_FaultingStyle f_style;
  private boolean running = false;
  private int loaded = 0, incorrect_format = 0;

  public use_HaliteModelBuilder(use_FaultingStyle faulting, impl_Model model)
  {
    this.f_style = faulting;
    this.model = model;
    l0.LOG.push("HaliteModelLoader[" + hashCode() + "] ARMED");
  }

  /**
   * Sets the desired faulting style
   *
   * @param style
   *          Desired faulting style
   * @see com.jackmeng.halite.use_FaultingStyle
   */
  public void fault_style(use_FaultingStyle style)
  {
    this.f_style = style;
  }

  /**
   * Gets the desired faulting style
   *
   * @return
   *         Desired faulting style
   * @see com.jackmeng.halite.use_FaultingStyle
   */
  public use_FaultingStyle fault_style()
  {
    return this.f_style;
  }

  @Override public void load(String fileName)
  {
    // TODO Auto-generated method stub

  }

  @Override public void sync(String fileName)
  {
    // TODO Auto-generated method stub

  }

}
