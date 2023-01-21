package com.jackmeng.test;

import com.jackmeng.halite.builtin.use_Guard;
import com.jackmeng.halite.def.use_Def;
import com.jackmeng.halite.def.use_HaliteDefLoader;
import com.jackmeng.halite.def.use_HaliteDefLoader.halite_FaultingStyle;

public class test_Halite
{
  public static void main(String[] args)
  {
    use_HaliteDefLoader loader = new use_HaliteDefLoader(halite_FaultingStyle.PANIC_ON_FAULT,
        new use_Def[] { new use_Def< Boolean >("AMOGUS", "uwu", Boolean.TRUE, use_Guard.BOOLEAN) });
    loader.load("/home/jackm/Code/property-manager/pkg/src/com/jackmeng/test/test.psp");
    System.out.println(loader);
  }
}
