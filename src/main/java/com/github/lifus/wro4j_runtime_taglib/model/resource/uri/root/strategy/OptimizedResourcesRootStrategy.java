/*
 * Copyright (c) 2014 Aleksei Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.lifus.wro4j_runtime_taglib.model.resource.uri.root.strategy;

/**
 * Finds out the root of optimized resources.
 */
public interface OptimizedResourcesRootStrategy {

  /**
   * @return relative URI of the root of optimized resources.
   */
  String getRoot();

}
