package com.jackmeng.test.model_tester;

import java.lang.reflect.Field;

import com.jackmeng.halite.use_FaultingStyle;
import com.jackmeng.halite.core.l0;
import com.jackmeng.halite.model.use_HaliteModelBuilder;

public class test_Model
{
  public static void main(String[] args)
  {
    use_HaliteModelBuilder builder = new use_HaliteModelBuilder(use_FaultingStyle.PANIC_ON_FAULT, new test_FakeModel());


    l0.LOG.kill();
  }
}
