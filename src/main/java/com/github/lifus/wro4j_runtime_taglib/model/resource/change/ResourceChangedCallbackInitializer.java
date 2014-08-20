/*
 * Copyright (c) 2014 Aleksey Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.lifus.wro4j_runtime_taglib.model.resource.change;

import ro.isdc.wro.util.LazyInitializer;

import com.github.lifus.wro4j_runtime_taglib.manager.InjectorInitializer;
import com.github.lifus.wro4j_runtime_taglib.model.resource.uri.cache.ResourceUriCacheInitializer;

/**
 * Lazily instantiates {@link ResourceChangedCallback}.
 */
public final class ResourceChangedCallbackInitializer extends LazyInitializer<ResourceChangedCallback> {

  private final ResourceUriCacheInitializer resourceUriCacheInitializer;
  private final InjectorInitializer injectorInitializer;

  public ResourceChangedCallbackInitializer(
    final ResourceUriCacheInitializer resourceUriCacheInitializer,
    final InjectorInitializer injectorInitializer
  ) {
    this.resourceUriCacheInitializer = resourceUriCacheInitializer;
    this.injectorInitializer = injectorInitializer;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected ResourceChangedCallback initialize() {
    return newResourceChangedCallback();
  }

  private ResourceChangedCallback newResourceChangedCallback() {
    return injectWroDataInto(
      new ResourceChangedCallback(resourceUriCacheInitializer.get()));
  }
  
  private <T> T injectWroDataInto(T target) {
    return injectorInitializer.get().inject(target);
  }

}
