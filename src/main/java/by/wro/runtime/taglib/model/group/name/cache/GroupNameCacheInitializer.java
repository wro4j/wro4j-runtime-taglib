/*
 * Copyright (c) 2014 Aleksey Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package by.wro.runtime.taglib.model.group.name.cache;

import ro.isdc.wro.cache.CacheStrategy;
import ro.isdc.wro.util.LazyInitializer;
import by.wro.runtime.taglib.cache.TaglibCacheKey;
import by.wro.runtime.taglib.cache.CacheStrategyFactory;

/**
 * Lazily initializes {@link GroupNameCache}.
 * <p>
 *   Uses the same subclass of {@link CacheStrategy} as WRO does.
 * </p>
 */
public final class GroupNameCacheInitializer extends LazyInitializer<GroupNameCache> {

  private final CacheStrategyFactory cacheStrategyFactory;

  public GroupNameCacheInitializer(final CacheStrategyFactory cacheStrategyFactory) {
    this.cacheStrategyFactory = cacheStrategyFactory;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected GroupNameCache initialize() {
    return createGroupNameCache();
  }

  private GroupNameCache createGroupNameCache() {
    final CacheStrategy<TaglibCacheKey, String> cacheStrategy = cacheStrategyFactory.create();
    return new GroupNameCacheStrategyDecorator(cacheStrategy);
  }

}
