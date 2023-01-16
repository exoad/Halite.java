package com.jackmeng.halite.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import com.jackmeng.halite.use_HaliteFault;

/**
 * The standard internal utility tools bank for the HaliteLoader program.
 *
 * @author Jack Meng
 */
public final class $l0
{
  private $l0()
  {
  }

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
  public static Optional< File > File_create_file0(final String path)
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
  public static Optional< File > File_create_file1(final String path)
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

  public static Optional< String > File_read_file0(final String path)
  {
    final StringBuilder sb = new StringBuilder();
    AtomicBoolean s = new AtomicBoolean(true);
    File_create_file0(path).ifPresentOrElse(e -> {
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
}
