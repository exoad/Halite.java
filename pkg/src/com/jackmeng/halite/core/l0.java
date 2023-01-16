package com.jackmeng.halite.core;

import java.io.File;
import java.io.IOException;

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
   * <br>
   * For more aggressive pedantics, try {@link #File_create_file1(String)} which
   * will always delete if
   * a duplicate is found.
   *
   * @param path
   *          Path to the desired file. This means including the file name and
   *          extension.
   * @return true or false for file creation (usually ignored)
   */
  public static boolean File_create_file0(final String path)
  {
    File t = new File(path);
    if (t.exists() && t.isFile())
      return true;
    try
    {
      return t.createNewFile();
    } catch (IOException e)
    {
      e.printStackTrace();
    }
    return false;
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
   * @return true or false for file creation (usually ignored)
   */
  public static boolean File_create_file1(final String path)
  {
    File t = new File(path);
    if (t.exists() && t.isFile())
      t.delete();
    try
    {
      return t.createNewFile();
    } catch (IOException e)
    {
      e.printStackTrace();
    }
    return false;
  }
}
