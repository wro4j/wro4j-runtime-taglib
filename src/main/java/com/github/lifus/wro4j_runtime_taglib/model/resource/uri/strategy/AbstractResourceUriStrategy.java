/*
 * Copyright (c) 2014 Aleksei Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.lifus.wro4j_runtime_taglib.model.resource.uri.strategy;

import ro.isdc.wro.util.LazyInitializer;

import com.github.lifus.wro4j_runtime_taglib.model.resource.uri.root.OptimizedResourcesRootProvider;

/**
 * Base class for {@link ResourceUriStrategy} implementations.
 */
public abstract class AbstractResourceUriStrategy implements ResourceUriStrategy {

  private final String contextPath;

  private final OptimizedResourcesRootProvider optimizedResourcesRootProvider;

  private final LazyInitializer<String> wroRootInitializer = new LazyInitializer<String>() {
    @Override
    protected String initialize() {
      return getContextPath() + optimizedResourcesRootProvider.getRoot();
    }
  };

  protected AbstractResourceUriStrategy(final String contextPath, final OptimizedResourcesRootProvider optimizedResourcesRootProvider) {
    this.contextPath = contextPath;
    this.optimizedResourcesRootProvider = optimizedResourcesRootProvider;
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
