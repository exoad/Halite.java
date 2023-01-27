package com.jackmeng.halite.model;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * A ready model basically enforces the fact that the proper clone method is
 * observed.
 * Furthermore, it also makes sure that the end resultant Model class that
 * extends this class does not extend anything else.
 *
 * @author Jack Meng
 */
public abstract class use_ReadyModel
    implements
    impl_Model
{
  @Override public final Object clone() throws CloneNotSupportedException
  {
    try
    {
      Object newObj = this.getClass().getDeclaredConstructor().newInstance();
      for (Field field : this.getClass().getDeclaredFields())
      {
        field.setAccessible(true);
        field.set(newObj, field.get(this));
      }
      return newObj;
    } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
        | NoSuchMethodException | SecurityException e)
    {
      throw new CloneNotSupportedException();
    }
  }
}
