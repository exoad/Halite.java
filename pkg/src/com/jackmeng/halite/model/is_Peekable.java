
package com.jackmeng.halite.model;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * A field marked as is_Peekable tells the ModelBuilder that that field should
 * be used for the generation and processing
 * of the input configuration file. However, it should also be noted that for
 * any objects that take generics, it is also highly advised that
 * the class wrapped within the generics also implements "impl_Model" along with
 * marking any necessary fields as peekable. For examples
 *
 * <pre>
 * &#064;is_Peekable List< MyObj > a = new ArrayList<>();
 *
 * &#064;is_Peekable AtomicReference< MyObject > b = new AtomicReference<>();
 *
 * &#064;is_Peekable SoftReference< MyObj > c = new SoftReference<>();
 *
 * </pre>
 *
 * <p>
 * MyObj must be of instance impl_Model and can declare fields with is_Peekable
 * in order for the building process not to fail (this means proper visibility
 * of PUBLIC)
 *
 * @author Jack Meng
 */
@Retention(RetentionPolicy.RUNTIME) @Documented @Target(ElementType.FIELD) public @interface is_Peekable {

}
