
package com.jackmeng.halite.def;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import com.jackmeng.halite.HaliteBuilder;
import com.jackmeng.halite.use_FaultingStyle;
import com.jackmeng.halite.use_HaliteFault;
import com.jackmeng.halite.use_PropertyStyle;
import com.jackmeng.halite.core.l0;
import com.jackmeng.stl.stl_Wrap;

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
 * @see com.jackmeng.halite.HaliteBuilder
 * @see com.jackmeng.halite.model.use_HaliteModelBuilder
 */
public final class use_HaliteDefBuilder
    implements
    HaliteBuilder
{

  /**
   * All mutable definitions are stored here along with there resultants (As the
   * value pair).
   *
   * No definition is safe from unwanted manipulation via the unsafe method:
   * {@link #set(String, Object)}
   */
  private final Map< use_Def< ? >, Object > defs;
  private use_FaultingStyle style;
  private int loaded = 0, incorrect_format = 0;
  private boolean loaded_b = false;

  /**
   * @param style
   *          The Default faulting style of this loader (FINAL)
   * @param property_def
   *          The Property definition that is used to define and build this loader
   */
  public use_HaliteDefBuilder(use_FaultingStyle style, Iterable< use_Def< ? > > property_def)
  {
    defs = new Hashtable<>();
    property_def.forEach(r -> defs.put(r, null));
    this.style = style == null ? use_FaultingStyle.PANIC_ON_FAULT : style;
    l0.LOG.push("HaliteDefLoader[" + hashCode() + "] ARMED");
  }

  /**
   * @param style
   *          The Default faulting style of this loader (FINAL)
   * @param property_Def
   *          Array of property definitions used to define and build this loader
   */
  public use_HaliteDefBuilder(use_FaultingStyle style, use_Def< ? >[] property_Def)
  {
    this(style, l0.itrb(property_Def));
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
    this.style = style;
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
    return this.style;
  }

  /**
   * This is an unsafe method. It deletes all stored resultants within
   * {@link #defs} which can cause
   * adverse effects when dependent callers require certain values only to get
   * Optional.of(null)
   *
   * No closures are provided for the original states of each property definition
   *
   * @see #rmv(String)
   */
  public synchronized void wipe()
  {
    l0.LOG.push(
        "[0][DefinitionLoader] Unsafe operation being performed: Programmatic Property setting! This call may leave certain dependencies dangling!");
    defs.forEach((a, b) -> defs.put(a, null));
  }

  /**
   * This is an unsafe method. It deletes a desired property definition entirely.
   * However, it returns a closure by returning the last value as a Map.Entry<K,V>
   * to the caller.
   *
   * This is especially unsafe to use when you use in a fashion similar to
   * {@link #load(String)} then
   * {@link #rmv(String)} then
   * {@link #get(String)} which can cause unexpected Nullability checks, but can
   * be handled with java.util.Optional, but it is highly to avoid patterns like
   * this.
   *
   * Furthermore, it can also cause other complications like
   * ConcurrentModificationExceptions, NullPointerExceptions
   * if you are somehow calling {@link #load(String)} and {@link #rmv(String)} at
   * the same time.
   *
   * @param property_name
   * @return
   */
  public synchronized Optional< Map.Entry< use_Def< ? >, Object > > rmv(String property_name)
  {
    l0.LOG.push(
        "[1][DefinitionLoader] Unsafe operation being performed: Programmatic Property setting! This call may leave certain dependencies dangling!");
    Map.Entry< use_Def< ? >, Object > e = null;
    for (use_Def< ? > r : defs.keySet())
      if (r.property_name.equals(property_name))
        e = Map.entry(r, defs.get(r));
    return e == null ? Optional.empty() : Optional.of(e);
  }

  /**
   * Searches for a property_name value. Note this is the "key" that appears in a
   * property file.
   *
   * @param property_name
   *          The Stringified key
   * @return The property from the load. If not loaded or could not be found, this
   *         method returns an empty Optional<Object>
   */
  public synchronized Optional< Object > get(String property_name)
  {
    stl_Wrap< Object > e = new stl_Wrap<>(null);
    if (loaded_b)
    {
      defs.forEach((a, b) -> {
        l0.LOG.push("Getting_Search[DefinitionLoader]: " + a.property_name);
        if (a.property_name.equals(property_name))
          e.obj = b;
      });
    }
    l0.LOG.push(
        "Gets: " + property_name + " with " + (e.obj == null ? "[?null]" : e.obj.getClass().getCanonicalName()));
    return e.obj == null ? Optional.empty() : Optional.of(e.obj);
  }

  /**
   * This is an unsafe method. It sets the value within the map programmatically.
   * Furthermore, the programmer must realize that any value
   * they put in to this Builder for a specified definition is always mutable and
   * can always be erased.
   * <br>
   * This means if you do a {@link #set(String, Object)} and then following it
   * with a {@link #load(String, use_PropertyStyle, boolean, boolean)},
   * it can result in the original {@link #set(String, Object)} having no effect.
   * <br>
   * This method is also unsafe in that, there are no initial calls to the
   * definition's impl_PGuard which poses
   * risks when others call {@link #get(String)} and get the wrong type causing
   * Casting errors.
   *
   * For a semi-checked setter, see {@link #checked_set(String, Object)}
   * <br>
   * Side Note: There are no set(use_Def<?>,T) because it is highly unadvised to
   * keep separate instance declarations outside of the constructor calls:
   * {@link #use_HaliteDefBuilder(use_FaultingStyle, Iterable)} or
   * {@link #use_HaliteDefBuilder(use_FaultingStyle, use_Def[])}.
   *
   * @param <T>
   *          The type of the value to set(T)
   * @param property_Name
   *          The property_name. This value is the formal "key" name that appears
   *          in the property file
   * @param value
   *          The desired value.
   * @see #checked_set(String, Object)
   */
  public synchronized < T > void set(String property_Name, T value)
  {
    l0.LOG.push(
        "[DefinitionLoader] Unsafe operation being performed: Programmatic Property setting! This can overwrite the original desired value(s)!");
    defs.forEach((a, b) -> {
      if (a.property_name.equals(property_Name))
        defs.put(a, value);
    });
  }

  /**
   * This is an unsafe method. This is a hardcoded definition guarded version of
   * {@link #set(String, Object)}. However, it is still not safe in that, any
   * programmatic modification of the values
   * are deemed unsafe due to the fact that it can be malicious or provide no
   * closures for other dependencies. Additionally, the call to the guard's
   * checker
   * assumes that calling value#toString() results in the "raw" value of that
   * property_value instead of
   * some gibberish regarding its internals or other debug diagnostic data.
   *
   * This method is only to save you from having the wrong type for setting and
   * not anything else.
   * <br>
   * Side Note: There are no set(use_Def<?>,T) because it is highly unadvised to
   * keep separate instance declarations outside of the constructor calls:
   * {@link #use_HaliteDefBuilder(use_FaultingStyle, Iterable)} or
   * {@link #use_HaliteDefBuilder(use_FaultingStyle, use_Def[])}.
   *
   * @param <T>
   *          The type of the value to set(T)
   * @param property_Name
   *          The property_name. This value is the formal "key" name that appears
   *          in the property file
   * @param value
   *          The desired value.
   * @see #set(String, Object)
   */
  public synchronized < T > void checked_set(String property_Name, T value)
  {
    l0.LOG.push(
        "[DefinitionLoader] Unsafe operation being performed: Programmatic Property setting! This can overwrite the original desired value(s)!");
    defs.forEach((a, b) -> {
      if (a.property_name.equals(property_Name) && Boolean.TRUE.equals(a.call(value.toString())))
      {
        defs.put(a, value);
        return;
      }
    });
    if (style == use_FaultingStyle.PANIC_ON_FAULT)
      use_HaliteFault.launch_fault("Inproper checked_set, could not set the desired [CHECKED] property to "
          + property_Name + ". Mismatched type!");
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
  public synchronized void load(String fileName, use_PropertyStyle style, boolean create, boolean end_goal_check)
  {
    l0.LOG.push("Loading a [DEFINITION] based property configuration file: " + fileName);
    loaded = 0;
    incorrect_format = 0;
    if (style == use_PropertyStyle.JAVA_UTIL_PROPERTIES)
    {
      l0.$File_create_file0(fileName, create).ifPresentOrElse(e -> {
        l0.LOG.push("[DEFINITION] based property file exists. Proceeding with loading and processing");
        Properties p = new Properties();
        loaded_b = true;
        try
        {
          p.load(new FileInputStream(e));
        } catch (IOException e1)
        {
          e1.printStackTrace();
          l0.LOG.push(e1.getMessage());
          loaded_b = false;
        }
        if (loaded_b)
        {
          l0.LOG.push("Property init of java::io::Properties.load() was successful");
          for (use_Def< ? > r : defs.keySet())
          {
            r.dawn_action().ifPresent(Runnable::run);
            String property = p.getProperty(r.property_name);
            if (property != null && r.call(property))
            {
              l0.LOG
                  .push("DefinitionLoader: Loaded_Property: " + r.key + " as " + r.property_name + " with " + property);
              r.modifier().ifPresentOrElse(x -> defs.put(r, x.modify(property)), () -> defs.put(r, property));
              this.loaded++;
            }
            else
            {
              if (this.style == use_FaultingStyle.PANIC_ON_FAULT)
                use_HaliteFault.launch_fault("Failed to validate the property. Panic Reason: " + l0.err.getString("2")
                    + "\nInstead I got: " + property + "\nDoes not match guard(s): "
                    + l0.to_string_arr_class(r.coalesce) + "\nFor property: " + r.property_name + "\nKey: " + r.key);
              else
              {
                defs.put(r, r.property_default_value);
                l0.LOG.push("[0]Loaded property: " + r.key + " as " + r.property_name + " with default: "
                    + r.property_default_value);
              }
              incorrect_format++;
            }
          }
        }
        else if (!loaded_b)
        {
          if (this.style == use_FaultingStyle.PANIC_ON_FAULT)
            use_HaliteFault.launch_fault("Failed to load properties. Reason: " + l0.err.getString("1"));
          else
          {
            for (use_Def< ? > r : defs.keySet())
            {
              defs.put(r, r.property_default_value);
              l0.LOG.push("[1]Loaded property: " + r.key + " as " + r.property_name + " with [default]: "
                  + r.property_default_value);
            }
          }
        }
      }, () -> {
        if (this.style == use_FaultingStyle.IGNORE_ON_FAULT && !create)
        {
          for (use_Def< ? > r : defs.keySet())
          {
            defs.put(r, r.property_default_value);
            loaded++;
          }
          loaded_b = true;
        }
        else if (this.style == use_FaultingStyle.PANIC_ON_FAULT && !create)
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
            l0.LOG.push(e1.getMessage());
          }
          loaded_b = true;
          for (use_Def< ? > r : defs.keySet())
          {
            defs.put(r, r.property_default_value);
            l0.LOG.push("[2]Loaded property: " + r.key + " as " + r.property_name + " with default: "
                + r.property_default_value);
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
          + (loaded + incorrect_format) + "\nFile_Load: " + this.loaded_b + "\n=========");
    }
  }

  /**
   * Saves the current state of the property builder to a file on the hard drive.
   * This operation should be used wisely as there can be extreme performance
   * deprecation when a large builder with an uncached property map is being
   * transfered in this map.
   *
   * @param style
   *          The desired property type to load from.
   * @param fileName
   *          The desired file to save to.
   * @param comments
   *          Any comments to append to.
   */
  public synchronized void save(use_PropertyStyle style, String fileName, String comments)
  {
    if (style == use_PropertyStyle.JAVA_UTIL_PROPERTIES)
    {
      if (loaded_b)
      {
        l0.LOG.push("[DEFINITION_LOADER] Attempting to save the current desired property definitions to: " + fileName);
        Properties t = new Properties();
        defs.forEach(
            (a, b) -> t.setProperty(a.property_name, b == null ? a.property_default_value.toString() : b.toString()));
        try
        {
          t.store(new FileOutputStream(new File(fileName)), comments);
        } catch (Exception e)
        {
          e.printStackTrace();
          l0.LOG.push(e.getMessage());
        }
      }
      else use_HaliteFault.launch_fault("Failed to save properties, due to reason: " + l0.err.getString("5"));
    }
    else use_HaliteFault.launch_fault("Currently no support for YAML");
  }

  /**
   * Unsafe operation, this method assumes that the file is loaded as a standard
   * Java Properties
   * File instead of any other supported formats.
   */
  @Override public synchronized void load(String fileName)
  {
    l0.LOG.push("Default loading with #load(String)");
    load(fileName, use_PropertyStyle.JAVA_UTIL_PROPERTIES, false, true);
  }

  @Override public synchronized void sync(String fileName)
  {
    save(use_PropertyStyle.JAVA_UTIL_PROPERTIES, fileName,
        this.getClass().getCanonicalName() + "[" + hashCode() + "]");
  }

  @Override public synchronized String toString()
  {
    return "[\u2713] " + loaded + "\n[\u2573] " + incorrect_format + "\n[\u2211] "
        + (loaded + incorrect_format) + "\n" + defs.toString();
  }

}