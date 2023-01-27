package com.jackmeng.halite.core;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jackmeng.stl.stl_Logger;

/**
 * The standard internal utility tools bank for the HaliteLoader program.
 *
 * @author Jack Meng
 */
public final class l0
{
  private l0()
  {
  }

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
  public static Optional< File > $File_create_file0(final String path, final boolean try_create)
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

  public static final ResourceBundle err = ResourceBundle.getBundle("com.jackmeng.halite.core.error_codes");

  public static final stl_Logger LOG = new stl_Logger("com_dot_jmeng_HaliteLoader",
      "/home/jackm/Code/property-manager/dump");

  static
  {
    LOG.run();
  }

  public static < T > Iterable< T > itrb(T[] e)
  {
    return new Iterable< T >() {

      @Override public Iterator< T > iterator()
      {
        return Arrays.stream(e).iterator();
      }

    };
  }

  public static < T > String to_string_arr_class(T[] e)
  {
    StringBuilder sb = new StringBuilder("\n{\n");
    for (int i = 0; i < e.length; i++)
    {
      sb.append("\t");
      if (e[i].getClass().getCanonicalName() == null)
        sb.append(e[i].getClass().hashCode()).append("->")
            .append(e[i].getClass().getTypeName());
      else
        sb.append(e[i].getClass().getCanonicalName()).append("->").append(e[i].getClass().getClassLoader().getName());
      if (i + 1 < e.length)
        sb.append(",\n");
    }
    return sb.append("\n}").toString();
  }
}
