/*
 * Copyright (c) 2014 Aleksei Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.lifus.wro4j_runtime_taglib.model.resource.uri.cache;

import javax.servlet.ServletContext;

import ro.isdc.wro.cache.CacheStrategy;
import ro.isdc.wro.util.LazyInitializer;

import com.github.lifus.wro4j_runtime_taglib.cache.CacheStrategyFactory;
import com.github.lifus.wro4j_runtime_taglib.cache.TaglibCacheKey;
import com.github.lifus.wro4j_runtime_taglib.config.ConfigurationHelper;
import com.github.lifus.wro4j_runtime_taglib.manager.InjectorInitializer;
import com.github.lifus.wro4j_runtime_taglib.model.group.name.DefaultVersionedGroupNameFactory;
import com.github.lifus.wro4j_runtime_taglib.model.group.name.VersionedGroupNameFactory;
import com.github.lifus.wro4j_runtime_taglib.model.group.name.cache.GroupNameCacheInitializer;
import com.github.lifus.wro4j_runtime_taglib.model.group.name.callback.DefaultVersionedGroupNameListener;
import com.github.lifus.wro4j_runtime_taglib.model.group.name.callback.VersionedGroupNameCallbackRegistry;
import com.github.lifus.wro4j_runtime_taglib.model.group.name.callback.VersionedGroupNameListener;
import com.github.lifus.wro4j_runtime_taglib.model.resource.uri.root.DefaultOptimizedResourcesRootProvider;
import com.github.lifus.wro4j_runtime_taglib.model.resource.uri.strategy.ConfigurableResourceUriStrategy;

/**
 * Initializes and holds instance of {@link ResourceUriCache}.
 */
public final class ResourceUriCacheInitializer extends LazyInitializer<ResourceUriCache> {

  private final ServletContext servletContext;
  private final GroupNameCacheInitializer groupNameCacheInitializer;
  private final ConfigurationHelper  configuration;
  private final CacheStrategyFactory cacheStrategyFactory;
  private final InjectorInitializer  injectorInitializer;

  public ResourceUriCacheInitializer(
    final ServletContext servletContext,
    final GroupNameCacheInitializer nameCacheInitializer,
    final ConfigurationHelper configuration,
    final CacheStrategyFactory cacheStrategyFactory,
    final InjectorInitializer injectorInitializer
  ) {
    this.servletContext = servletContext;
    this.groupNameCacheInitializer = nameCacheInitializer;
    this.configuration = configuration;
    this.cacheStrategyFactory = cacheStrategyFactory;
    this.injectorInitializer = injectorInitializer;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected ResourceUriCache initialize() {
    return createResourceUriCache();
  }

  private ResourceUriCache createResourceUriCache() {
    final CacheStrategy<TaglibCacheKey, String[]> cache = cacheStrategyFactory.create();
    return new ResourceUriCacheStrategyDecorator(cache, createResourceUriStrategy());
  }

  private ConfigurableResourceUriStrategy createResourceUriStrategy() {
    return new ConfigurableResourceUriStrategy(
      createOptimizedResourceRootProvider(),
      createVersionedGroupNameFactory(),
      configuration,
      servletContext.getContextPath()
    );
  }

  private DefaultOptimizedResourcesRootProvider createOptimizedResourceRootProvider() {
    return new DefaultOptimizedResourcesRootProvider(servletContext);
  }

  private VersionedGroupNameFactory createVersionedGroupNameFactory() {
    return injectWroDataInto(
      new DefaultVersionedGroupNameFactory(
        createVersionedGroupNameCallbackRegistry()
      )
    );
  }
  
  private <T> T injectWroDataInto(T target) {
    return injectorInitializer.get().inject(target);
  }

  private VersionedGroupNameCallbackRegistry createVersionedGroupNameCallbackRegistry() {
    final VersionedGroupNameCallbackRegistry callbackRegistry = new VersionedGroupNameCallbackRegistry();
    callbackRegistry.registerPublicNameListener(createVersionedGroupNameListener());
    return callbackRegistry;
  }

  private VersionedGroupNameListener createVersionedGroupNameListener() {
    return new DefaultVersionedGroupNameListener(groupNameCacheInitializer.get());
  }

}
