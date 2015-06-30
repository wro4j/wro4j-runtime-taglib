/*
 * Copyright (c) 2014 Aleksei Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.lifus.wro4j_runtime_taglib.model.resource.uri.strategy;

import ro.isdc.wro.util.LazyInitializer;

import com.github.lifus.wro4j_runtime_taglib.model.resource.uri.root.strategy.OptimizedResourcesRootStrategy;

/**
 * Base class for {@link ResourceUriStrategy} implementations.
 */
public abstract class AbstractResourceUriStrategy implements ResourceUriStrategy {

  private final String contextPath;

  private final OptimizedResourcesRootStrategy optimizedResourcesRootStrategy;

  private final LazyInitializer<String> wroRootInitializer = new LazyInitializer<String>() {
    @Override
    protected String initialize() {
      return getContextPath() + optimizedResourcesRootStrategy.getRoot();
    }
  };

  protected AbstractResourceUriStrategy(final String contextPath, final OptimizedResourcesRootStrategy optimizedResourcesRootStrategy) {
    this.contextPath = contextPath;
    this.optimizedResourcesRootStrategy = optimizedResourcesRootStrategy;
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

}
