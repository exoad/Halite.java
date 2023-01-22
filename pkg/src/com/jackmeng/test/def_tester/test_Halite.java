package com.jackmeng.test.def_tester;


import com.jackmeng.halite.builtin.use_Guard;
import com.jackmeng.halite.def.use_Def;
import com.jackmeng.halite.def.use_HaliteDefBuilder;
import com.jackmeng.halite.def.use_HaliteDefBuilder.halite_FaultingStyle;

public class test_Halite
{
  public static void main(String[] args) throws Exception
  {
    use_HaliteDefBuilder loader = new use_HaliteDefBuilder(halite_FaultingStyle.IGNORE_ON_FAULT,
        new use_Def[] { new use_Def< Boolean >("AMOGUS", "uwu", Boolean.TRUE, use_Guard.BOOLEAN) });
    loader.load("/home/jackm/Code/property-manager/pkg/src/com/jackmeng/test/def_tester/tcs/1.psp");
    System.out.println(loader.get("uwu"));
    loader.set("uwu", "amgous");
    System.out.println(loader.get("uwu"));
  }
}
