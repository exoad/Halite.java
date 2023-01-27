package com.jackmeng.halite.model;

import java.lang.reflect.Field;

/**
 * A ModelGuard is much more different from that of a definition guard in that
 * the checking factor requires the field
 * name, which requires a lookup routine. This makes a model guard much less
 * inefficient.
 *
 * @author Jack Meng
 */
@FunctionalInterface public interface impl_MGuard
{
  < T > boolean check(Field field, T value);
}
