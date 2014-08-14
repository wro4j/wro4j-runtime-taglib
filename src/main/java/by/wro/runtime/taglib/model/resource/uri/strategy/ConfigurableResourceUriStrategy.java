/*
 * Copyright (c) 2014 Aleksey Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package by.wro.runtime.taglib.model.resource.uri.strategy;

import java.util.Map;
import java.util.Properties;

import ro.isdc.wro.model.resource.ResourceType;
import ro.isdc.wro.model.resource.support.AbstractConfigurableSingleStrategy;
import by.wro.runtime.taglib.config.ConfigurationHelper;
import by.wro.runtime.taglib.model.group.name.VersionedGroupNameFactory;
import by.wro.runtime.taglib.model.resource.uri.root.OptimizedResourcesRootProvider;
import by.wro.runtime.taglib.model.resource.uri.strategy.provider.DefaultResourceUriStrategyProvider;
import by.wro.runtime.taglib.model.resource.uri.strategy.provider.ResourceUriStrategyProvider;

/**
 * Creates the {@link ResourceUriStrategy} associated with an alias read from properties file.
 */
public final class ConfigurableResourceUriStrategy extends AbstractConfigurableSingleStrategy<ResourceUriStrategy, ResourceUriStrategyProvider>
                                                   implements ResourceUriStrategy {

  public static final String KEY = "resourceUriStrategy";

  private final OptimizedResourcesRootProvider optimizedResourcesRootProvider;
  private final VersionedGroupNameFactory versionedGroupNameFactory;
  private final ConfigurationHelper configuration;

  public ConfigurableResourceUriStrategy(
    final OptimizedResourcesRootProvider optimizedResourcesRootProvider,
    final VersionedGroupNameFactory versionedGroupNameFactory,
    final ConfigurationHelper configuration
  ) {

    this.optimizedResourcesRootProvider = optimizedResourcesRootProvider;
    this.versionedGroupNameFactory = versionedGroupNameFactory;
    this.configuration = configuration;
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
    return new VersionedResourceUriStrategy(optimizedResourcesRootProvider, versionedGroupNameFactory);
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
      optimizedResourcesRootProvider,
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