/*
 * Copyright (c) 2014 Aleksey Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package by.wro.runtime.taglib.model.group.name.cache;

import ro.isdc.wro.cache.CacheStrategy;
import ro.isdc.wro.cache.support.CacheStrategyDecorator;
import by.wro.runtime.taglib.cache.TaglibCacheKey;

/**
 * Required to get group names by versioned group name and resource type from {@link CacheStrategy}.
 */
public final class GroupNameCacheStrategyDecorator extends CacheStrategyDecorator<TaglibCacheKey, String>
                                                   implements GroupNameCache {

  public GroupNameCacheStrategyDecorator(final CacheStrategy<TaglibCacheKey, String> cacheStrategy) {
    super(cacheStrategy);
  }

}
