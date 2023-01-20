package com.jackmeng.halite.model;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jackmeng.halite.core.core_mark;

@core_mark @Retention(RetentionPolicy.RUNTIME) @Documented @Target(ElementType.FIELD) public @interface HList {
  Class< ? extends Object >[] expected_types() default String[].class;
}
