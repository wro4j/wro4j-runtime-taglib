/*
 * Copyright (c) 2014 Aleksei Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.lifus.wro4j_runtime_taglib.model.resource.uri.strategy.provider;

import java.util.Map;

import com.github.lifus.wro4j_runtime_taglib.model.resource.uri.strategy.ResourceUriStrategy;

/**
 * Provides instances of {@link ResourceUriStrategy}.
 *
 * @see ResourceUriStrategy
 */
public interface ResourceUriStrategyProvider {

  /**
   * @return available instances of {@link ResourceUriStrategy} associated with alias.
   */
  Map<String, ResourceUriStrategy> provideResourceUriStrategies();

}
