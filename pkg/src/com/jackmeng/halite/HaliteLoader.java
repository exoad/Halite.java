package com.jackmeng.halite;

import java.util.Iterator;

import com.jackmeng.halite.core.l0;

/**
 * The main class to creating a Halite Configuration Manager. This Loader keeps
 * track
 * of both reading (IN) properties, validating these properties, and saving
 * these properties
 *
 * @author Jack Meng
 */
public final class HaliteLoader
    implements
    Iterable< use_Def >
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

  private final Iterable< use_Def > defs;
  private final halite_FaultingStyle style;

  public HaliteLoader(halite_FaultingStyle style, Iterable< use_Def > property_def)
  {
    this.defs = property_def;
    this.style = style;
  }

  public synchronized void load(String fileName)
  {
    l0.File_create_file0(fileName);

  }

  public synchronized void save(String fileName)
  {

  }

  @Override public Iterator< use_Def > iterator()
  {
    return defs.iterator();
  }
}