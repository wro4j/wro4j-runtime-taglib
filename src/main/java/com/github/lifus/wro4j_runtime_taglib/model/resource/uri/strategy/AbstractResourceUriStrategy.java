/*
 * Copyright (c) 2014 Aleksei Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.lifus.wro4j_runtime_taglib.model.resource.uri.strategy;

import javax.servlet.ServletContext;

import ro.isdc.wro.config.ReadOnlyContext;
import ro.isdc.wro.model.group.Inject;
import ro.isdc.wro.util.LazyInitializer;

import com.github.lifus.wro4j_runtime_taglib.model.resource.uri.root.OptimizedResourcesRootProvider;

/**
 * Base class for {@link ResourceUriStrategy} implementations.
 */
public abstract class AbstractResourceUriStrategy implements ResourceUriStrategy {

  @Inject
  private ReadOnlyContext context;

  private final OptimizedResourcesRootProvider optimizedResourcesRootProvider;

  private final LazyInitializer<String> contextPathInitializer = new LazyInitializer<String>() {
    @Override
    protected String initialize() {
      final ServletContext servletContext = context.getServletContext();
      return servletContext.getContextPath();
    }
  };
  private final LazyInitializer<String> wroRootInitializer = new LazyInitializer<String>() {
    @Override
    protected String initialize() {
      return getContextPath() + optimizedResourcesRootProvider.getRoot();
    }
  };

  protected AbstractResourceUriStrategy(final OptimizedResourcesRootProvider optimizedResourcesRootProvider) {
    this.optimizedResourcesRootProvider = optimizedResourcesRootProvider;
  }

  /**
   * @return context path of current servlet.
   */
  protected String getContextPath() {
    return contextPathInitializer.get();
  }

  /**
   * @return common root for WRO resources.
   */
  protected String getWroRoot() {
    return wroRootInitializer.get();
  }

  /**
   * @return true if empty groups are allowed.
   */
  protected boolean isIgnoreEmptyGroup() {
    return context.getConfig().isIgnoreEmptyGroup();
  }

}
