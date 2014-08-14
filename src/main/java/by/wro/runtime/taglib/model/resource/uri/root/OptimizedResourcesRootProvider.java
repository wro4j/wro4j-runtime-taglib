/*
 * Copyright (c) 2014 Aleksey Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package by.wro.runtime.taglib.model.resource.uri.root;

/**
 * Finds out the root of optimized resources.
 */
public interface OptimizedResourcesRootProvider {

  /**
   * @return relative URI of the root of optimized resources.
   */
  String getRoot();

}
