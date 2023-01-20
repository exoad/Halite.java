package com.jackmeng.halite.model;

import com.jackmeng.halite.core.core_mark;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@core_mark @Retention(RetentionPolicy.RUNTIME) @Documented @Target(ElementType.FIELD) public @interface HSingleton {
  Class< ? extends Object > expected_type() default String.class;
}