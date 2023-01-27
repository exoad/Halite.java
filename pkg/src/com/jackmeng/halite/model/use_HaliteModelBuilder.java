package com.jackmeng.halite.model;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Queue;

import com.jackmeng.halite.HaliteBuilder;
import com.jackmeng.halite.use_FaultingStyle;
import com.jackmeng.halite.use_HaliteFault;
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
 * <p>
 * All models declare fields which are subjected to "supported_types" which
 * can be viewed in ModelUtils which has a field SUPPORTED_TYPES_01 which lists
 * all
 * of the primitive types that are supported.
 *
 * @author Jack Meng
 * @see com.jackmeng.halite.model.impl_Model
 * @see com.jackmeng.halite.model.is_Peekable
 */
public final class use_HaliteModelBuilder
    implements
    HaliteBuilder
{
  public static final class Model_Utils
  {
    private Model_Utils()
    {
    }

    /**
     * The primitive types that are permitted to be used as "peekables." It is also
     * highly recommended to coalseces all
     * floating point and fixed to be either a double or int with their respective
     * Array types.
     *
     *
     * <p>
     * Note if an alternative type is found that is not supported, on an
     * IGNORE_ON_FAULT panic model, the builder will completely ignore this field
     * (i.e. not emplacing that field in memory). On the other hand, a
     * PANIC_ON_FAULT panic model will throw an exception on a faulty type that does
     * not match.
     */
    public static final Class< ? >[] SUPPORTED_TYPES_01 = {
        String.class,
        String[].class,
        int.class,
        int[].class,
        byte.class,
        byte[].class,
        char.class,
        char[].class,
        double.class,
        double[].class,
        float.class,
        float[].class,
        short.class,
        short[].class,
        long.class,
        long[].class,
        Number.class,
        Number[].class,
        Collection.class
    };
  }

  private impl_Model model;
  private use_FaultingStyle f_style;
  private boolean running = false;
  private int loaded = 0, incorrect_format = 0;

  /**
   * Constructs the builder with the barebones parsing utils.
   *
   * @param faulting
   *          Faulting method
   * @param default_model
   *          The default model. This reference contains a copy of the original
   *          property model with default values if something fails on an
   *          INGNORE_ON_FAULT call. If the field is not found, it will be set to
   *          that native type's default value.
   *
   */
  public use_HaliteModelBuilder(use_FaultingStyle faulting, impl_Model default_model)
  {
    this.f_style = faulting;
    this.model = default_model;
    l0.LOG.push("HaliteModelLoader[" + hashCode() + "] ARMED");
  }
  
  public Field[] peekable_fields(impl_Model model)
  {
    assert model != null;
    List< Field > fl = new ArrayList<>(5);
    if (f_style == use_FaultingStyle.IGNORE_ON_FAULT)
    {
      for (Field e : model.getClass().getFields())
        if (e.isAnnotationPresent(is_Peekable.class)
            && Arrays.binarySearch(Model_Utils.SUPPORTED_TYPES_01, e.getType()) != -1)
          fl.add(e);
    }
    else if (f_style == use_FaultingStyle.PANIC_ON_FAULT)
    {
      for (Field e : model.getClass().getFields())
        if (e.isAnnotationPresent(is_Peekable.class))
          if (Arrays.binarySearch(Model_Utils.SUPPORTED_TYPES_01, e.getType()) == -1)
            use_HaliteFault.launch_fault(
                "The builder tried to fetch the model's fields but encountered an errn. " + l0.err.getString("4"));
          else
            fl.add(e);
    }

    return fl.toArray(new Field[0]);
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
