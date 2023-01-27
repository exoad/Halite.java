package com.jackmeng.test.model_tester;

import java.lang.reflect.Field;

import com.jackmeng.halite.use_FaultingStyle;
import com.jackmeng.halite.use_PropertyStyle;
import com.jackmeng.halite.core.l0;
import com.jackmeng.halite.model.impl_Model;
import com.jackmeng.halite.model.use_HaliteModelBuilder;

public class test_Model
{
  public static void main(String[] args) throws Exception
  {
    use_HaliteModelBuilder builder = new use_HaliteModelBuilder(use_FaultingStyle.PANIC_ON_FAULT, new test_FakeModel());
    impl_Model e = builder.load("pkg/src/com/jackmeng/test/model_tester/2.psp", use_PropertyStyle.JAVA_UTIL_PROPERTIES);
    for (Field er : e.getClass().getFields())
      System.out.println(er.get(e));
    l0.LOG.kill();
  }
}
