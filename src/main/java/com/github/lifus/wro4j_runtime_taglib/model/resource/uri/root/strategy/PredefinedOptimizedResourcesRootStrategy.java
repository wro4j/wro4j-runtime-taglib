/*
 * Copyright (c) 2015 Aleksei Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.lifus.wro4j_runtime_taglib.model.resource.uri.root.strategy;

import ro.isdc.wro.WroRuntimeException;

import com.github.lifus.wro4j_runtime_taglib.config.ConfigurationHelper;

/**
 * Retrieves predefined value of the root of optimized resources from WRO configuration.
 */
public final class PredefinedOptimizedResourcesRootStrategy extends AbstractOptimizedResourcesRootStrategy {
  public static final String ALIAS = "predefined";

  // Available for tests
  static final String VALUE_KEY = "optimizedResourcesRoot";

  private final ConfigurationHelper configuration;

  public PredefinedOptimizedResourcesRootStrategy(final ConfigurationHelper configuration) {
    this.configuration = configuration;
  }

  /**
   * @return predefined value of the root of optimized resources from WRO configuration.
   */
  @Override
  protected String findOutRoot() {
    if (configuration.containsKey(VALUE_KEY)) {
      return configuration.getProperty(VALUE_KEY);
    } else {
      final String message = String.format("'%s' is required when '%s=%s'", VALUE_KEY, ConfigurableOptimizedResourcesRootStrategy.KEY, ALIAS);
      throw new WroRuntimeException(message);
    }
  }

}
