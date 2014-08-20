/*
 * Copyright (c) 2014 Aleksei Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.lifus.wro4j_runtime_taglib.model.group.name.cache;

import ro.isdc.wro.cache.CacheStrategy;
import ro.isdc.wro.cache.support.CacheStrategyDecorator;

import com.github.lifus.wro4j_runtime_taglib.cache.TaglibCacheKey;

/**
 * Required to get group names by versioned group name and resource type from {@link CacheStrategy}.
 */
public final class GroupNameCacheStrategyDecorator extends CacheStrategyDecorator<TaglibCacheKey, String>
                                                   implements GroupNameCache {

  public GroupNameCacheStrategyDecorator(final CacheStrategy<TaglibCacheKey, String> cacheStrategy) {
    super(cacheStrategy);
  }

}
