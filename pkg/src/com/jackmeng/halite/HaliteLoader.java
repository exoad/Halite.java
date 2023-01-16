package com.jackmeng.halite;

import java.nio.file.FileSystemException;
import java.util.HashMap;
import java.util.Map;

import com.jackmeng.halite.core.$l0;

/**
 * The main class to creating a Halite Configuration Manager. This Loader keeps
 * track
 * of both reading (IN) properties, validating these properties, and saving
 * these properties
 *
 * @author Jack Meng
 */
public final class HaliteLoader
{

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

  private final Map< use_Def< ? >, ? > defs;
  private final halite_FaultingStyle style;

  public HaliteLoader(halite_FaultingStyle style, Iterable< use_Def< ? > > property_def)
  {
    defs = new HashMap<>();
    property_def.forEach(r -> defs.put(r, null));
    this.style = style;
  }

  /**
   * This function loads a file into memory for reading, but does not perform any
   * parsing yet.
   *
   * @param fileName
   *          The file to load information from
   */
  public synchronized void load(String fileName)
  {
    $l0.File_read_file0(fileName).ifPresentOrElse(e -> {

    }, () -> use_HaliteFault.launch_fault("Failed to the desired input file. Reason: " + use_HaliteFault.$fault0_2()));
  }

  public synchronized void save(String fileName)
  {

  }

}