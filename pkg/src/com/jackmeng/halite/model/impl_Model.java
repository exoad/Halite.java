package com.jackmeng.halite.model;

/**
 * <p>
 * A model is a more concrete representation of data processed, which puts less
 * "guessing" for the programmer, as most of the expected
 * properties are encountered as an instance of this type construct. This type
 * of building
 * is especially useful for when there are multiple layers of properties, like
 * in YAML, JSON, and XML. While for a simple
 * "java.util.Properties" based file, this type of parsing might less efficient.
 * <p>
 * A model can choose to have peekable definitions which are markers that
 * signify a property
 * should be expected in the input configuration file.
 *
 * <p><strong>
 * !!!! When programmatically creating Models, it is highly recommended that you
 * do not
 * add any unnecessary functionalities to your Model that may obscure meaning
 * from simply just being
 * a "cache" save location for the loaded values. This means that the class does
 * not extend any other classes (besides may a ReadyModel), implementing
 * unnecessary interfaces besides
 * impl_Model, having a constructor that takes parameters, having a constructor
 * that performs unnecessary routines, having any functions/methods that can
 * alter meaning or provide other unnecessary functionalities.</strong>
 *
 * <p>
 * Thus a model follows a similar pattern:
 *
 * <pre>
 * public final class MyModel
 *     implements
 *     com.jackmeng.halite.model.impl_Model
 * {
 *   &#064;is_Peekable String myproperty;
 * }
 *
 * </pre>
 *
 * <p>
 * Furthermore all Models must directly inherit {@link #clone()} in order for
 * the
 * builder to work. This means returning an object with the same fields aka a
 * different functionality of the Object.clone()
 *
 * @author Jack Meng
 */
public interface impl_Model
    extends Cloneable
{

  Object clone() throws CloneNotSupportedException;
}
