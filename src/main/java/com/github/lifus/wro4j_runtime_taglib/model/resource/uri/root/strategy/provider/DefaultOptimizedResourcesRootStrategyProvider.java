/*
 * Copyright (c) 2015 Aleksei Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.lifus.wro4j_runtime_taglib.model.resource.uri.root.strategy.provider;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import com.github.lifus.wro4j_runtime_taglib.config.ConfigurationHelper;
import com.github.lifus.wro4j_runtime_taglib.model.resource.uri.root.strategy.InferredOptimizedResourcesRootStrategy;
import com.github.lifus.wro4j_runtime_taglib.model.resource.uri.root.strategy.OptimizedResourcesRootStrategy;
import com.github.lifus.wro4j_runtime_taglib.model.resource.uri.root.strategy.PredefinedOptimizedResourceRootStrategy;

public class DefaultOptimizedResourcesRootStrategyProvider implements OptimizedResourcesRootStrategyProvider {

  private final ServletContext servletContext;
  private final ConfigurationHelper configurationHelper;

  public DefaultOptimizedResourcesRootStrategyProvider(final ServletContext servletContext, final ConfigurationHelper configurationHelper) {
    this.servletContext = servletContext;
    this.configurationHelper = configurationHelper;
  }

  @Override
  public Map<String, OptimizedResourcesRootStrategy> provideOptimizedResourcesRootStrategies() {
    final Map<String, OptimizedResourcesRootStrategy> map = new HashMap<>();
    map.put(InferredOptimizedResourcesRootStrategy.ALIAS, new InferredOptimizedResourcesRootStrategy(servletContext));
    map.put(PredefinedOptimizedResourceRootStrategy.ALIAS, new PredefinedOptimizedResourceRootStrategy(configurationHelper));
    return map;
  }

}
