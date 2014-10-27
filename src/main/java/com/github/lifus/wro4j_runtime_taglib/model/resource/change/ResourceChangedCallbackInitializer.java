/*
 * Copyright (c) 2014 Aleksei Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.lifus.wro4j_runtime_taglib.model.resource.change;

import ro.isdc.wro.manager.factory.WroManagerFactory;
import ro.isdc.wro.util.LazyInitializer;

import com.github.lifus.wro4j_runtime_taglib.model.resource.uri.cache.ResourceUriCacheInitializer;

/**
 * Lazily instantiates {@link ResourceChangedCallback}.
 */
public final class ResourceChangedCallbackInitializer extends LazyInitializer<ResourceChangedCallback> {

  private final WroManagerFactory wroManagerFactory;
  private final ResourceUriCacheInitializer resourceUriCacheInitializer;

  public ResourceChangedCallbackInitializer(
    final WroManagerFactory wroManagerFactory,
    final ResourceUriCacheInitializer resourceUriCacheInitializer
  ) {
    this.wroManagerFactory = wroManagerFactory;
    this.resourceUriCacheInitializer = resourceUriCacheInitializer;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected ResourceChangedCallback initialize() {
    return newResourceChangedCallback();
  }

  private ResourceChangedCallback newResourceChangedCallback() {
    return new ResourceChangedCallback(wroManagerFactory, resourceUriCacheInitializer.get());
  }

}
