/*
 * Copyright (c) 2014 Aleksey Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.lifus.wro4j_runtime_taglib.model.resource.uri.strategy.provider;

import java.util.HashMap;
import java.util.Map;

import com.github.lifus.wro4j_runtime_taglib.model.group.name.VersionedGroupNameFactory;
import com.github.lifus.wro4j_runtime_taglib.model.resource.uri.root.OptimizedResourcesRootProvider;
import com.github.lifus.wro4j_runtime_taglib.model.resource.uri.strategy.ResourceUriStrategy;
import com.github.lifus.wro4j_runtime_taglib.model.resource.uri.strategy.UnoptimizedResourceUriStrategy;
import com.github.lifus.wro4j_runtime_taglib.model.resource.uri.strategy.VersionedResourceUriStrategy;

/**
 * Provides default instances of {@link ResourceUriStrategy}.
 *
 * @see ResourceUriStrategy
 */
public final class DefaultResourceUriStrategyProvider implements ResourceUriStrategyProvider {

  private final OptimizedResourcesRootProvider optimizedResourcesRootProvider;
  private final VersionedGroupNameFactory versionedGroupNameFactory;

  public DefaultResourceUriStrategyProvider(
    final OptimizedResourcesRootProvider optimizedResourcesRootProvider,
    final VersionedGroupNameFactory versionedGroupNameFactory
  ) {

    this.optimizedResourcesRootProvider = optimizedResourcesRootProvider;
    this.versionedGroupNameFactory = versionedGroupNameFactory;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Map<String, ResourceUriStrategy> provideResourceUriStrategies() {
    final HashMap<String, ResourceUriStrategy> map = new HashMap<>();
    map.put(
      VersionedResourceUriStrategy.ALIAS,
      new VersionedResourceUriStrategy(optimizedResourcesRootProvider, versionedGroupNameFactory)
    );
    map.put(
      UnoptimizedResourceUriStrategy.ALIAS,
      new UnoptimizedResourceUriStrategy(optimizedResourcesRootProvider)
    );
    return map;
  }

}
