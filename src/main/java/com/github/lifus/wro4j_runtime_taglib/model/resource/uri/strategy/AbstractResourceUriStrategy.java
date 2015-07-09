/*
 * Copyright (c) 2014 Aleksei Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.lifus.wro4j_runtime_taglib.model.resource.uri.strategy;

import ro.isdc.wro.util.LazyInitializer;

import com.github.lifus.wro4j_runtime_taglib.config.ConfigurationHelper;
import com.github.lifus.wro4j_runtime_taglib.model.resource.uri.root.strategy.OptimizedResourcesRootStrategy;

/**
 * Base class for {@link ResourceUriStrategy} implementations.
 */
public abstract class AbstractResourceUriStrategy implements ResourceUriStrategy {

  // available for tests
  static final String RESOURCE_DOMAIN_KEY = "resourceDomain";
  // there is no default resource domain
  static final String DEFAULT_RESOURCE_DOMAIN = "";

  protected final ConfigurationHelper configuration;

  private final String contextPath;
  private final OptimizedResourcesRootStrategy optimizedResourcesRootStrategy;

  private final LazyInitializer<String> wroRootInitializer = new LazyInitializer<String>() {
    @Override
    protected String initialize() {
      return getResourceDomain() + getContextPath() + optimizedResourcesRootStrategy.getRoot();
    }
  };

  protected AbstractResourceUriStrategy(final String contextPath, final OptimizedResourcesRootStrategy optimizedResourcesRootStrategy, final ConfigurationHelper configuration) {
    this.contextPath = contextPath;
    this.optimizedResourcesRootStrategy = optimizedResourcesRootStrategy;
    this.configuration = configuration;
  }

  /**
   * @return context path of current servlet.
   */
  protected String getContextPath() {
    return contextPath;
  }

  /**
   * @return common root for WRO resources.
   */
  protected String getWroRoot() {
    return wroRootInitializer.get();
  }

  private String getResourceDomain() {
    return configuration.getProperty(RESOURCE_DOMAIN_KEY, DEFAULT_RESOURCE_DOMAIN);
  }

}
