package com.jackmeng.halite.core;

import java.util.Arrays;
import java.util.Iterator;
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

  public static final ResourceBundle err = ResourceBundle.getBundle("com.jackmeng.halite.core.error_codes");

  public static final stl_Logger LOG = new stl_Logger("com_dot_jmeng_HaliteLoader", "/home/jackm/Code/property-manager/dump");
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
