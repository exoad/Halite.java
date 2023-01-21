package com.jackmeng.test.def_tester;

import java.util.Random;

import com.jackmeng.halite.builtin.use_Guard;
import com.jackmeng.halite.core.l0;
import com.jackmeng.halite.def.use_Def;
import com.jackmeng.halite.def.use_HaliteDefLoader;
import com.jackmeng.halite.def.use_HaliteDefLoader.halite_FaultingStyle;

public class test_Halite
{
  public static void main(String[] args) throws Exception
  {
    use_HaliteDefLoader loader = new use_HaliteDefLoader(halite_FaultingStyle.IGNORE_ON_FAULT,
        new use_Def[] { new use_Def< Boolean >("AMOGUS", "uwu", Boolean.TRUE, use_Guard.BOOLEAN) });
    loader.load("/home/jackm/Code/property-manager/pkg/src/com/jackmeng/test/def_tester/tcs/1.psp");


    while(true)
    {
      l0.LOG.push("idk");
      Thread.sleep(new Random().nextLong(400, 1500));
    }
  }
}
