/*
 * Copyright (c) 2015 Aleksei Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.lifus.wro4j_runtime_taglib.model.resource.uri.root.strategy.provider;

import java.util.Map;

import com.github.lifus.wro4j_runtime_taglib.model.resource.uri.root.strategy.OptimizedResourcesRootStrategy;

/**
 * Provides instances of {@link OptimizedResourcesRootStrategy}.
 *
 * @see OptimizedResourcesRootStrategy
 */
public interface OptimizedResourcesRootStrategyProvider {

  /**
   * @return available instances of {@link OptimizedResourcesRootStrategy} associated with alias.
   */
  Map<String, OptimizedResourcesRootStrategy> provideOptimizedResourcesRootStrategies();

}
