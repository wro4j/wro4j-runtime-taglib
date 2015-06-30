/*
 * Copyright (c) 2014 Aleksei Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.lifus.wro4j_runtime_taglib.model.resource.uri.root.strategy;

/**
 * Base class for {@link OptimizedResourcesRootStrategy} with a single root.
 */
public abstract class AbstractOptimizedResourcesRootStrategy implements OptimizedResourcesRootStrategy {

  private String url;

  /**
   * {@inheritDoc}
   */
  @Override
  public String getRoot() {
    if (url == null) { // no synchronization intended
      url = findOutRoot();
    }
    return url;
  }

  /**
   * Finds out root URL of WroFilter.
   *
   * Idempotent method.
   *
   * @return root of WroFilter.
   */
  protected abstract String findOutRoot();

}
