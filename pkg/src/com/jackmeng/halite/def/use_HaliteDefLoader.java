
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
   * <br>
   * For more aggressive pedantics, try {@link #File_create_file1(String)} which
   * will always delete if
   * a duplicate is found.
   *
   * @param path
   *          Path to the desired file. This means including the file name and
   *          extension.
   * @return The file object, null if the file could not be created.
   */
  private static Optional< File > $File_create_file0(final String path)
  {
    File t = new File(path);
    if (t.exists() && t.isFile() && t.canRead() && t.canWrite())
      return Optional.of(t);
    try
    {
      t.createNewFile();
    } catch (IOException e)
    {
      return Optional.empty();
    }
    return Optional.of(t);
  }

  /**
   * Standard File IO function, determines specific objectives to meet when
   * creating a file.
   *
   * Check the docs of {@link #File_create_file0(String)} for more information
   *
   * Deletes duplicate
   *
   * @param path
   *          Path to the desired file. This means including the file name and
   *          extension.
   * @return The file object, null if the file could not be created.
   */
  private static Optional< File > $File_create_file1(final String path)
  {
    File t = new File(path);
    if (t.exists() && t.isFile())
      t.delete();
    try
    {
      t.createNewFile();
    } catch (IOException e)
    {
      return Optional.empty();
    }
    return Optional.of(t);
  }

  private static Optional< String > $File_read_file0(final String path)
  {
    final StringBuilder sb = new StringBuilder();
    AtomicBoolean s = new AtomicBoolean(true);
    $File_create_file0(path).ifPresentOrElse(e -> {
      try
      {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(e)));
        if (br.ready())
        {
          String temp;
          while ((temp = br.readLine()) != null)
            sb.append(temp);
        }
        br.close();
      } catch (IOException err)
      {
        err.printStackTrace();
        s.set(false);
      }
    }, () -> new use_HaliteFault(
        "Could not parse said file. Reason: File could not be found. " + use_HaliteFault.$fault0_1())
            .printStackTrace());
    return s.get() ? Optional.of(sb.toString()) : Optional.empty();
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

  public use_HaliteDefLoader(halite_FaultingStyle style, Iterable< use_Def< ? > > property_def)
  {
    defs = new HashMap<>();
    property_def.forEach(r -> defs.put(r, null));
    this.style = style == null ? halite_FaultingStyle.PANIC_ON_FAULT : style;
  }

  /**
   * This function loads a file into memory for reading, but does not perform any
   * parsing yet.
   *
   * @param fileName
   *          The file to load information from
   */
  public synchronized void load(String fileName, halite_PropertyStyle style)
  {
    if (style == halite_PropertyStyle.JAVA_UTIL_PROPERTIES)
    {
      $File_create_file0(fileName).ifPresentOrElse(e -> {
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
            if (p.getProperty(r.property_name) != null && r.call(p.getProperty(r.property_name)))
            {
              
            }
            else defs.put(r, r.property_default_value);
          }
        }
        else if (!loaded)
        {
          if (this.style == halite_FaultingStyle.PANIC_ON_FAULT)
            use_HaliteFault.launch_fault("Failed to load properties. Reason: " + use_HaliteFault.$fault0_3());
          else
          {
            for (use_Def< ? > r : defs.keySet())
              defs.put(r, r.property_default_value);
          }
        }
      }, () -> use_HaliteFault
          .launch_fault("Failed to the desired input file. Reason: " + use_HaliteFault.$fault0_1()));
    }
    else new UnsupportedOperationException(
        "OTHER FILE FORMATS BESIDES: java.util.properties style have not been implemented").printStackTrace();

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
    load(fileName, halite_PropertyStyle.JAVA_UTIL_PROPERTIES);
  }

  @Override public void sync(String fileName)
  {
    // TODO Auto-generated method stub

  }

}