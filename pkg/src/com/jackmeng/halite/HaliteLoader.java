package com.jackmeng.halite;

/**
 * Defines the basis for a loader class.
 *
 * This is primarily seen with the main types of
 * loaders:
 * <ul>
 * <li>Definition Based Loader
 * {@link com.jackmeng.halite.def.use_HaliteDefLoader}</li>
 * <li>Model Based Loader
 * {@link com.jackmeng.halite.model.use_HaliteModelLoader}</li>
 * </ul>
 */
public interface HaliteLoader
{
  /**
   * Loads a file
   *
   * @param fileName
   *          String based for the file name location
   */
  void load(String fileName);

  /**
   * Syncs the current loaded property model or definition
   *
   * @param
   */
  void sync(String fileName);

  default String loaderName()
  {
    return this.hashCode() + "@" + toString();
  }
}
