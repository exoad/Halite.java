
package com.jackmeng.halite.def;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

import com.jackmeng.halite.HaliteLoader;
import com.jackmeng.halite.use_HaliteFault;
import com.jackmeng.halite.core.l0;

/**
 * A definition based loader uses String based definitions to create keyed
 * attributes
 * within the property file.
 *
 * These string files are represented by a
 * {@link com.jackmeng.halite.def.use_Def} object
 * which contains specific attributes for how a specific property should be
 * found, processed,
 * and have its value returned back to the program.
 *
 * @author Jack Meng
 * @see com.jackmeng.halite.HaliteLoader
 */
public final class use_HaliteDefLoader
    implements
    HaliteLoader
{

  /**
   * Standard File IO function, determines specific objectives to meet when
   * creating a file.
   *
   * For example if the file is present, don't create a file, if the file is not,
   * create a file.
   *
   *
   * @param path
   *          Path to the desired file. This means including the file name and
   *          extension.
   * @param try_create
   *          CreateNewFile() ?
   * @return The file object, null if the file could not be created.
   */
  private static Optional< File > $File_create_file0(final String path, final boolean try_create)
  {
    File t = new File(path);
    if (t.exists() && t.isFile() && t.canRead() && t.canWrite())
      return Optional.of(t);
    if (try_create)
    {
      try
      {
        t.createNewFile();
      } catch (IOException e)
      {
        return Optional.empty();
      }
    }
    return Optional.empty();
  }

  public enum halite_FaultingStyle {
    /**
     * A panic_on_fault style means that whenever any of the following criteria is
     * met, the program will throw an exception:
     * <ul>
     * <li>Invalid property_value</li>
     * <li>Parsing error</li>
     * </ul>
     */
    PANIC_ON_FAULT,
    /**
     * This should be the default selected option for when any kind of fault(s) are
     * encountered in which, where everything is handled by the guards themselves.
     */
    IGNORE_ON_FAULT;
  }

  /**
   * Represents what kind of property file is being read.
   *
   * @author Jack Meng
   */
  public enum halite_PropertyStyle {
    YAML,
    /**
     * Use {@link java.util.Properties}
     */
    JAVA_UTIL_PROPERTIES;
  }

  private final Map< use_Def< ? >, Object > defs;
  private final halite_FaultingStyle style;
  private int loaded = 0, incorrect_format = 0;
  private boolean load = false;

  public use_HaliteDefLoader(halite_FaultingStyle style, Iterable< use_Def< ? > > property_def)
  {
    defs = new HashMap<>();
    property_def.forEach(r -> defs.put(r, null));
    this.style = style == null ? halite_FaultingStyle.PANIC_ON_FAULT : style;
  }

  public use_HaliteDefLoader(halite_FaultingStyle style, use_Def< ? >[] property_Def)
  {
    this(style, l0.itrb(property_Def));
  }

  /**
   * This function loads a file into memory for reading, but does not perform any
   * parsing yet.
   *
   * @param fileName
   *          The file to load information from
   * @param style
   *          How the program should panic if there is to be an error(s)
   * @param create
   *          If the desired property file could not be found, then create one
   *          with the default properties. If this parameter is false, then the
   *          default properties will not be written to storage and all values
   *          will have a default value.
   * @param end_goal_check
   *          IF true, then the program would perform finalization diagnostics. If
   *          style is also to PANIC_ON_FAULT, then the program would also check
   *          if any values are null and panic if there are, else it would just
   *          store some general data about how much got loaded etc.. It is best
   *          to turn this parameter to TRUE
   */
  public synchronized void load(String fileName, halite_PropertyStyle style, boolean create, boolean end_goal_check)
  {
    loaded = 0;
    incorrect_format = 0;
    if (style == halite_PropertyStyle.JAVA_UTIL_PROPERTIES)
    {
      $File_create_file0(fileName, create).ifPresentOrElse(e -> {
        Properties p = new Properties();
        boolean loaded = true;
        try
        {
          p.load(new FileInputStream(e));
        } catch (IOException e1)
        {
          e1.printStackTrace();
          loaded = false;
        }
        if (loaded)
        {
          for (use_Def< ? > r : defs.keySet())
          {
            r.dawn_action().ifPresent(Runnable::run);
            String property = p.getProperty(r.property_name);
            if (property != null && r.call(property))
            {
              r.modifier().ifPresentOrElse(x -> defs.put(r, x.modify(property)), () -> defs.put(r, property));
              this.loaded++;
            }
            else
            {
              if (this.style == halite_FaultingStyle.PANIC_ON_FAULT)
                use_HaliteFault.launch_fault("Failed to validate the property. Panic Reason: " + l0.err.getString("2")
                    + "\nInstead I got: " + property + "\nDoes not match guard(s): "
                    + l0.to_string_arr_class(r.coalesce) + "\nFor property: " + r.property_name + "\nKey: " + r.key);
              else
                defs.put(r, r.property_default_value);
              incorrect_format++;
            }
          }
        }
        else if (!loaded)
        {
          if (this.style == halite_FaultingStyle.PANIC_ON_FAULT)
            use_HaliteFault.launch_fault("Failed to load properties. Reason: " + l0.err.getString("1"));
          else
          {
            for (use_Def< ? > r : defs.keySet())
              defs.put(r, r.property_default_value);
          }
        }
        load = loaded;
      }, () -> {
        load = false;
        if (this.style == halite_FaultingStyle.IGNORE_ON_FAULT && !create)
          for (use_Def< ? > r : defs.keySet())
          {
            defs.put(r, r.property_default_value);
            loaded++;
          }
        else if (this.style == halite_FaultingStyle.PANIC_ON_FAULT && !create)
          use_HaliteFault.launch_fault("Failed to load properties. Reason: " + l0.err.getString("1"));
        else if (create)
        {
          File t = new File(fileName);
          try
          {
            t.createNewFile();
          } catch (IOException e1)
          {
            e1.printStackTrace();
          }
          for (use_Def< ? > r : defs.keySet())
          {
            defs.put(r, r.property_default_value);
            loaded++;
          }
        }
      });
    }

    if (end_goal_check)
    {
      defs.forEach((a, b) -> {
        if (b == null)
          use_HaliteFault.launch_fault("End goal check failed. Reason: " + l0.err.getString("5"));
      });
      System.out.println("[\u2713] " + loaded + "\n[\u2573] " + incorrect_format + "\n[\u2211] "
          + (loaded + incorrect_format) + "\nFile_Load: " + this.load + "\n=========");
    }

  }

  public synchronized void save(String fileName, halite_FaultingStyle style)
  {

  }

  /**
   * Unsafe operation, this method assumes that the file is loaded as a standard
   * Java Properties
   * File instead of any other supported formats.
   */
  @Override public void load(String fileName)
  {
    load(fileName, halite_PropertyStyle.JAVA_UTIL_PROPERTIES, false, true);
  }

  @Override public void sync(String fileName)
  {
    // TODO Auto-generated method stub

  }

  @Override public String toString()
  {
    return "[\u2713] " + loaded + "\n[\u2573] " + incorrect_format + "\n[\u2211] "
        + (loaded + incorrect_format) + "\n" + defs.toString();
  }

}