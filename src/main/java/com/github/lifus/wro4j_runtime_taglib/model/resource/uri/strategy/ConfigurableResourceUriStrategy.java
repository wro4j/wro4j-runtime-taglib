/*
 * Copyright (c) 2014 Aleksei Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.lifus.wro4j_runtime_taglib.model.resource.uri.strategy;

import java.util.Map;
import java.util.Properties;

import ro.isdc.wro.manager.factory.WroManagerFactory;
import ro.isdc.wro.model.resource.ResourceType;
import ro.isdc.wro.model.resource.support.AbstractConfigurableSingleStrategy;

import com.github.lifus.wro4j_runtime_taglib.config.ConfigurationHelper;
import com.github.lifus.wro4j_runtime_taglib.model.group.name.VersionedGroupNameFactory;
import com.github.lifus.wro4j_runtime_taglib.model.resource.uri.root.strategy.OptimizedResourcesRootStrategy;
import com.github.lifus.wro4j_runtime_taglib.model.resource.uri.strategy.provider.DefaultResourceUriStrategyProvider;
import com.github.lifus.wro4j_runtime_taglib.model.resource.uri.strategy.provider.ResourceUriStrategyProvider;

/**
 * Creates the {@link ResourceUriStrategy} associated with an alias read from properties file.
 */
public final class ConfigurableResourceUriStrategy extends AbstractConfigurableSingleStrategy<ResourceUriStrategy, ResourceUriStrategyProvider>
                                                   implements ResourceUriStrategy {

  public static final String KEY = "resourceUriStrategy";

  private final WroManagerFactory wroManagerFactory;
  private final OptimizedResourcesRootStrategy optimizedResourcesRootStrategy;
  private final VersionedGroupNameFactory versionedGroupNameFactory;
  private final ConfigurationHelper configuration;
  private final String contextPath;

  public ConfigurableResourceUriStrategy(
    final WroManagerFactory wroManagerFactory,
    final OptimizedResourcesRootStrategy optimizedResourcesRootStrategy,
    final VersionedGroupNameFactory versionedGroupNameFactory,
    final ConfigurationHelper configuration,
    final String contextPath
  ) {

    this.wroManagerFactory = wroManagerFactory;
    this.optimizedResourcesRootStrategy = optimizedResourcesRootStrategy;
    this.versionedGroupNameFactory = versionedGroupNameFactory;
    this.configuration = configuration;
    this.contextPath = contextPath;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Properties newProperties() {
    return configuration.getPropertiesForKey(KEY);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected ResourceUriStrategy getDefaultStrategy() {
    return new VersionedResourceUriStrategy(contextPath, optimizedResourcesRootStrategy, versionedGroupNameFactory, configuration);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Map<String, ResourceUriStrategy> getStrategies(final ResourceUriStrategyProvider provider) {
    return provider.provideResourceUriStrategies();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String getStrategyKey() {
    return KEY;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Class<ResourceUriStrategyProvider> getProviderClass() {
    return ResourceUriStrategyProvider.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void overrideDefaultStrategyMap(final Map<String, ResourceUriStrategy> map) {
    final DefaultResourceUriStrategyProvider strategyProvider = new DefaultResourceUriStrategyProvider(
      contextPath,
      wroManagerFactory,
      configuration,
      optimizedResourcesRootStrategy,
      versionedGroupNameFactory
    );
    final Map<String, ResourceUriStrategy> strategies = strategyProvider.provideResourceUriStrategies();
    map.putAll(strategies);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String[] getResourcesUris(final String groupName, final ResourceType resourceType) {
    return getConfiguredStrategy().getResourcesUris(groupName, resourceType);
  }

}
