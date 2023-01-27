package com.jackmeng.halite.def.builtin;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This guard class provides a grouping of Guards to run at
 * once to validate.
 *
 * When adding guards, it is highly suggested to not add two very similar
 * guards and guards which can be combined into one. This is because this
 * can cause severe performance impacts which are not feasible in performance
 * requiring softwares.
 *
 * @author Jack Meng
 * @see com.jackmeng.halite.def.builtin.impl_DGuard
 */
public class use_DGuardCoalesce
    extends ArrayList< impl_DGuard >
    implements impl_DGuard
{
  @Override public boolean check(String supplement)
  {
    AtomicBoolean res = new AtomicBoolean(false);
    forEach(e -> res.set(e.check(supplement)));
    return res.get();
  }
}
