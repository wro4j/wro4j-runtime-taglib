/*
 * Copyright (c) 2015 Aleksei Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.lifus.wro4j_runtime_taglib.model.resource.uri.root.strategy;

import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletContext;

import ro.isdc.wro.model.resource.support.AbstractConfigurableSingleStrategy;

import com.github.lifus.wro4j_runtime_taglib.config.ConfigurationHelper;
import com.github.lifus.wro4j_runtime_taglib.model.resource.uri.root.strategy.provider.DefaultOptimizedResourcesRootStrategyProvider;
import com.github.lifus.wro4j_runtime_taglib.model.resource.uri.root.strategy.provider.OptimizedResourcesRootStrategyProvider;

/**
 * Creates the {@link OptimizedResourcesRootStrategy} associated with an alias read from properties file.
 */
public class ConfigurableOptimizedResourceRootStrategy
    extends AbstractConfigurableSingleStrategy<OptimizedResourcesRootStrategy, OptimizedResourcesRootStrategyProvider>
    implements OptimizedResourcesRootStrategy
{
  public static final String KEY = "optimizedResourceRootStrategy";

  private final ServletContext servletContext;
  private final ConfigurationHelper configurationHelper;

  public ConfigurableOptimizedResourceRootStrategy(final ServletContext servletContext, final ConfigurationHelper configurationHelper) {
    this.servletContext = servletContext;
    this.configurationHelper = configurationHelper;
  }

  // the following 4 methods are common for all standard subclasses of AbstractConfigurableSingleStrategy
  @Override
  protected String getStrategyKey() {
    return KEY;
  }

  @Override
  protected OptimizedResourcesRootStrategy getDefaultStrategy() {
    return new InferredOptimizedResourcesRootStrategy(servletContext);
  }

  @Override
  protected Map<String, OptimizedResourcesRootStrategy> getStrategies(final OptimizedResourcesRootStrategyProvider provider) {
    return provider.provideOptimizedResourcesRootStrategies();
  }

  @Override
  protected Class<OptimizedResourcesRootStrategyProvider> getProviderClass() {
    return OptimizedResourcesRootStrategyProvider.class;
  }

  // the following method was added to avoid creation of anonymous classes
  @Override
  protected Properties newProperties() {
    return configurationHelper.getPropertiesForKey(KEY);
  }

  // the following method is required
  // because ConfigurableProvider doesn't extend OptimizedResourceRootStrategyProvider
  @Override
  protected void overrideDefaultStrategyMap(final Map<String, OptimizedResourcesRootStrategy> map) {
    final DefaultOptimizedResourcesRootStrategyProvider strategyProvider = new DefaultOptimizedResourcesRootStrategyProvider(
        servletContext
    );
    final Map<String, OptimizedResourcesRootStrategy> strategies = strategyProvider.provideOptimizedResourcesRootStrategies();
    map.putAll(strategies);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getRoot() {
    return getConfiguredStrategy().getRoot();
  }
}
