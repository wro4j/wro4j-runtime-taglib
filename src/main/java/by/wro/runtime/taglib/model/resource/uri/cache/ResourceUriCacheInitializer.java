/*
 * Copyright (c) 2014 Aleksey Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package by.wro.runtime.taglib.model.resource.uri.cache;

import ro.isdc.wro.cache.CacheStrategy;
import ro.isdc.wro.util.LazyInitializer;
import by.wro.runtime.taglib.cache.CacheStrategyFactory;
import by.wro.runtime.taglib.cache.TaglibCacheKey;
import by.wro.runtime.taglib.config.ConfigurationHelper;
import by.wro.runtime.taglib.manager.InjectorInitializer;
import by.wro.runtime.taglib.model.group.name.DefaultVersionedGroupNameFactory;
import by.wro.runtime.taglib.model.group.name.VersionedGroupNameFactory;
import by.wro.runtime.taglib.model.group.name.cache.GroupNameCacheInitializer;
import by.wro.runtime.taglib.model.group.name.callback.DefaultVersionedGroupNameListener;
import by.wro.runtime.taglib.model.group.name.callback.VersionedGroupNameCallbackRegistry;
import by.wro.runtime.taglib.model.group.name.callback.VersionedGroupNameListener;
import by.wro.runtime.taglib.model.resource.uri.root.DefaultOptimizedResourcesRootProvider;
import by.wro.runtime.taglib.model.resource.uri.strategy.ConfigurableResourceUriStrategy;

/**
 * Initializes and holds instance of {@link ResourceUriCache}.
 */
public final class ResourceUriCacheInitializer extends LazyInitializer<ResourceUriCache> {

  private final GroupNameCacheInitializer groupNameCacheInitializer;
  private final ConfigurationHelper  configuration;
  private final CacheStrategyFactory cacheStrategyFactory;
  private final InjectorInitializer  injectorInitializer;

  public ResourceUriCacheInitializer(
    final GroupNameCacheInitializer nameCacheInitializer,
    final ConfigurationHelper configuration,
    final CacheStrategyFactory cacheStrategyFactory,
    final InjectorInitializer injectorInitializer
  ) {
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
    return injectWroDataInto(
      new ConfigurableResourceUriStrategy(
        createOptimizedResourceRootProvider(),
        createVersionedGroupNameFactory(),
        configuration
      )
    );
  }

  private DefaultOptimizedResourcesRootProvider createOptimizedResourceRootProvider() {
    return injectWroDataInto(
      new DefaultOptimizedResourcesRootProvider()
    );
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
