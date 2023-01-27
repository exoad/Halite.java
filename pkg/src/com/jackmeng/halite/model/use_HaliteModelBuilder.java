package com.jackmeng.halite.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import com.jackmeng.halite.HaliteBuilder;
import com.jackmeng.halite.use_FaultingStyle;
import com.jackmeng.halite.use_HaliteFault;
import com.jackmeng.halite.use_PropertyStyle;
import com.jackmeng.halite.core.l0;
import com.jackmeng.stl.stl_Callback;
import com.jackmeng.stl.stl_Wrap;

/**
 * <p>
 * This is the Halite Model builder. A model builder uses a concrete
 * representation of the configuration file
 * passed as an "impl_Model" instance which should also have marked up
 * "is_Peekable" fields. However, this is considerably much slower, as it relies
 * heavily on Java Reflection and other non conventional methods to create this
 * concrete representation.
 *
 * <p>
 * Compared to a simpler Definition builder, the vagueness of what properties
 * are there and what are not and accessing them through code are all gone.
 * Everything is presented in a concrete fashion based on a marked-up model.
 * Furthermore, the definition builder bundled with Halite uses a
 * "read-store-retrieve-wipe" method in which the builder itself keeps track of
 * what it is building. This is done so by retaining a Table of all of the
 * values built and the values saved. However, compared to a ModelBuilder, when
 * you call {@link #load(String, use_PropertyStyle)}, an instance of impl_Model
 * with the selected attributes is directly returned back. The builder itself
 * will
 * store a copy of the value, but it is weakily referenced.
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
        List.class
    };
  }

  private impl_Model model;
  private WeakReference< impl_Model > cached_Model;
  private use_FaultingStyle f_style;
  private boolean running = false, loaded_b = false;

  /**
   * Constructs the builder with the barebones parsing utils.
   *
   * @param faulting
   *          Faulting method
   * @param default_model
   *          The default model. This reference contains a copy of the original
   *          property model with default values if something fails on an
   *          INGNORE_ON_FAULT call. If the field is not found, it cannot be
   *          loaded as a property in the final Model construct.
   *
   */
  public use_HaliteModelBuilder(use_FaultingStyle faulting, impl_Model default_model)
  {
    this.f_style = faulting;
    this.model = default_model;
    cached_Model = new WeakReference<>(null);
    l0.LOG.push("HaliteModelLoader[" + hashCode() + "] ARMED");
  }

  private Field[] peekable_fields(impl_Model model)
  {
    assert model != null;
    List< Field > fl = new ArrayList<>(5);
    if (f_style == use_FaultingStyle.IGNORE_ON_FAULT)
    {
      for (Field e : model.getClass().getFields())
        if (e.isAnnotationPresent(is_Peekable.class)
            && l0.has(Model_Utils.SUPPORTED_TYPES_01, e.getType()))
        {
          e.setAccessible(true);
          fl.add(e);
        }
    }
    else if (f_style == use_FaultingStyle.PANIC_ON_FAULT)
    {
      for (Field e : model.getClass().getFields())
        if (e.isAnnotationPresent(is_Peekable.class))
        {
          if (!l0.has(Model_Utils.SUPPORTED_TYPES_01, e.getType()))
            use_HaliteFault.launch_fault(
                "The builder tried to fetch the model's fields but encountered an errn. " + l0.err.getString("4")
                    + "\nGot field: " + e.getName() + ":" + e.getType().getCanonicalName());
          else
          {
            e.setAccessible(true);
            fl.add(e);
          }
        }
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

  /**
   * This method will load the file using the current model, however instead of
   * strongly caching like a DefinitionBuilder, the value is weakily cached
   * (meaning a WeakReference). Therefore, the resultant Model is directly feeded
   * as a model afterwards
   *
   * @param fileName
   * @param propertyStyle
   * @return
   */
  public impl_Model load(String fileName, use_PropertyStyle propertyStyle)
  {
    stl_Wrap< impl_Model > m = new stl_Wrap<>(null);
    try
    {
      m.obj((impl_Model) model.clone());
    } catch (CloneNotSupportedException e)
    {
      // IGNORE
    }
    if (m != null)
    {
      if (propertyStyle == use_PropertyStyle.JAVA_UTIL_PROPERTIES)
      {
        l0.$File_create_file0(fileName, true).ifPresentOrElse(x -> {

          l0.LOG.push("[MODEL] based property file exists. Proceeding with loading and processing");
          Properties p = new Properties();
          loaded_b = true;
          try
          {
            p.load(new FileInputStream(x));
          } catch (IOException err)
          {
            err.printStackTrace();
            l0.LOG.push(err.getMessage());
            loaded_b = false;
          }
          if (loaded_b)
          {
            l0.LOG.push("Property init of java::io::Properties.load() was successful");
            for (Field e : peekable_fields(model))
            {
              Object str = p.get(e.getName());
              if (str != null)
              {
                Field t = null;
                try
                {
                  t = m.obj().getClass().getDeclaredField(e.getName());
                  t.setAccessible(true);
                  t.set(m.obj, str);
                } catch (NoSuchFieldException | SecurityException | IllegalArgumentException
                    | IllegalAccessException e1)
                {
                  e1.printStackTrace();
                }
              }
            }
          }
        }, () -> {
        });

      }
    }
    return m.obj();
  }

  public void save(String fileName, impl_Model model, use_PropertyStyle propertyStyle)
  {

  }

  /**
   * Not used
   *
   * @param fileName
   * @deprecated NOT USED
   *
   * @see #load(String, use_PropertyStyle)
   */
  @Override @Deprecated public void load(String fileName)
  {
    throw new UnsupportedOperationException("UNUSED");
  }

  /**
   * Not used
   *
   * @param fileName
   * @deprecated NOT USED
   *
   * @see #
   */
  @Override public void sync(String fileName)
  {
    throw new UnsupportedOperationException("UNUSED");
  }

}
