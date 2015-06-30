/*
 * Copyright (c) 2014 Aleksei Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.lifus.wro4j_runtime_taglib.model.resource.uri.strategy.provider;

import java.util.HashMap;
import java.util.Map;

import ro.isdc.wro.manager.factory.WroManagerFactory;

import com.github.lifus.wro4j_runtime_taglib.config.ConfigurationHelper;
import com.github.lifus.wro4j_runtime_taglib.model.group.name.VersionedGroupNameFactory;
import com.github.lifus.wro4j_runtime_taglib.model.resource.uri.root.strategy.OptimizedResourcesRootStrategy;
import com.github.lifus.wro4j_runtime_taglib.model.resource.uri.strategy.ResourceUriStrategy;
import com.github.lifus.wro4j_runtime_taglib.model.resource.uri.strategy.UnoptimizedResourceUriStrategy;
import com.github.lifus.wro4j_runtime_taglib.model.resource.uri.strategy.VersionedResourceUriStrategy;

/**
 * Provides default instances of {@link ResourceUriStrategy}.
 *
 * @see ResourceUriStrategy
 */
public final class DefaultResourceUriStrategyProvider implements ResourceUriStrategyProvider {

  private final String contextPath;
  private final WroManagerFactory wroManagerFactory;
  private final ConfigurationHelper configuration;
  private final OptimizedResourcesRootStrategy optimizedResourcesRootStrategy;
  private final VersionedGroupNameFactory versionedGroupNameFactory;

  public DefaultResourceUriStrategyProvider(
    final String contextPath,
    final WroManagerFactory wroManagerFactory,
    final ConfigurationHelper configuration,
    final OptimizedResourcesRootStrategy optimizedResourcesRootStrategy,
    final VersionedGroupNameFactory versionedGroupNameFactory
  ) {

    this.contextPath = contextPath;
    this.wroManagerFactory = wroManagerFactory;
    this.configuration = configuration;
    this.optimizedResourcesRootStrategy = optimizedResourcesRootStrategy;
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
      new VersionedResourceUriStrategy(contextPath, optimizedResourcesRootStrategy, versionedGroupNameFactory)
    );
    map.put(
      UnoptimizedResourceUriStrategy.ALIAS,
      new UnoptimizedResourceUriStrategy(contextPath, wroManagerFactory, configuration, optimizedResourcesRootStrategy)
    );
    return map;
  }

}
