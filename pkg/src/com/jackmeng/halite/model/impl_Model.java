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
 * <p>
 * Thus a model follows a similar pattern:
 *
 * <pre>
 * public final class MyModel
 *     implements
 *     com.jackmeng.halite.model.impl_Model
 * {
 *   &#064;is_Peekable
 *   String myproperty;
 * }
 *
 * </pre>
 *
 * @author Jack Meng
 */
public interface impl_Model
{

}
