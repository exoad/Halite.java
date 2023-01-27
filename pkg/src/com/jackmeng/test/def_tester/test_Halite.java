package com.jackmeng.test.def_tester;


import com.jackmeng.halite.def.use_Def;
import com.jackmeng.halite.def.use_HaliteDefBuilder;
import com.jackmeng.halite.def.builtin.use_Guard;
import com.jackmeng.halite.def.use_HaliteDefBuilder.halite_FaultingStyle;

public class test_Halite
{
  public static void main(String[] args) throws Exception
  {
    use_HaliteDefBuilder loader = new use_HaliteDefBuilder(halite_FaultingStyle.PANIC_ON_FAULT,
        new use_Def[] { new use_Def< Boolean >("AMOGUS", "uwu", Boolean.TRUE, use_Guard.BETTER_BOOL) });
    loader.load("/home/jackm/Code/property-manager/pkg/src/com/jackmeng/test/def_tester/tcs/2.psp");
    System.out.println(loader.get("uwu"));
  }
}
