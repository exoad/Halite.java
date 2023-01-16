package com.jackmeng.halite;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * This is the base definition for a single "property" in a property file.
 *
 * @author Jack Meng
 */
public class use_Def
    implements
    Consumer< String >
{

  /**
   * Defines a bunch of definition states that it can be in during
   * runtime.
   *
   * @author Jack Meng
   */
  public enum def_State {
    /**
     * A not loaded state means the property has not been read
     * and processed yet.
     */
    NOT_LOADED,
    /**
     * The encountered property_value was marked by the specified
     * guards as "valid"
     */
    VALID,
    /**
     * The encountered property_value was marked by the specified
     * guard as "invalid"
     */
    INVALID
  }

  /**
   * Defines how this definition should behave when it is being interacted with by
   * the HaliteLoader
   *
   * @author Jack Meng
   */
  public enum def_Strict {
    /**
     * If any of the guards return false for invalidation, this definition will not
     * consume
     * a "DEFAULT_VALUE" and instead will panic.
     */
    CRIT_EFFORT,

    /**
     * If any of the guards return false for invalidation, simply return
     * property_default_value
     */
    LAZY_EFFORT;
  }

  public final String key, property_name, property_default_value;
  public Optional< String > opt_Descriptor;
  private def_State myState = def_State.NOT_LOADED;
  private Optional< Runnable > actionpotential;
  private final impl_PGuard[] coalesce;
  private final def_Strict effort;

  /**
   * @param effort
   *          The effort used to critique the parameter values.
   *          {@link com.jackmeng.halite.use_Def.def_Strict}
   * @param key
   *          The Key Name, this is not the formal name of the key.
   * @param property_name
   *          The formal property_name used in the property files.
   * @param property_default_value
   *          The default value of this property structure returned when value was
   *          invalidated. This property can be nullable and should ONLY BE
   *          NULLABLE
   *          when the INVALIDATION_STATE has been set to "CRITICAL_EFFORT"
   * @param e
   *          The guard coalescing group to use
   */
  public use_Def(def_Strict effort, String key, String property_name, String property_default_value, impl_PGuard... e)
  {
    this(effort, null, null, key, property_name, property_default_value, e);
  }

  /**
   *
   * @param effort
   *          The effort used to critique the parameter values.
   *          {@link com.jackmeng.halite.use_Def.def_Strict}
   * @param action
   *          The action to run when this definition is being loaded. This action
   *          is run as a none blocking action initiated by default by
   *          HaliteLoader.
   * @param descriptor
   *          A basic description attribtue of this property_definition. May be
   *          optional
   * @param key
   *          The Key Name, this is not the formal name of the key.
   * @param property_name
   *          The formal property_name used in the property files.
   * @param property_default_value
   *          The default value of this property structure returned when value was
   *          invalidated. This property can be nullable and should ONLY BE
   *          NULLABLE
   *          when the INVALIDATION_STATE has been set to "CRITICAL_EFFORT"
   * @param e
   *          The guard coalescing group to use
   */
  public use_Def(def_Strict effort, Runnable action, String descriptor, String key, String property_name,
      String property_default_value,
      impl_PGuard... e)
  {
    this.key = key;
    this.actionpotential = action == null ? Optional.empty() : Optional.of(action);
    this.property_default_value = property_default_value;
    this.property_name = property_name;
    this.coalesce = e;
    this.effort = effort;
    this.opt_Descriptor = descriptor == null ? Optional.empty() : Optional.of(descriptor);
  }

  /**
   * @return The current state of this definition
   */
  public def_State state()
  {
    return myState;
  }

  /**
   * @return The desired action to run before this property_definition is loaded
   *         by the HaliteLoader
   */
  public Optional< Runnable > dawn_action()
  {
    return actionpotential;
  }

  @Override public void accept(String t)
  {

  }
}
