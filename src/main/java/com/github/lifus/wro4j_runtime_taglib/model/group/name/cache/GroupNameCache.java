/*
 * Copyright (c) 2014 Aleksey Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.lifus.wro4j_runtime_taglib.model.group.name.cache;

import ro.isdc.wro.cache.CacheStrategy;

import com.github.lifus.wro4j_runtime_taglib.cache.TaglibCacheKey;

/**
 * Stores group names associated with versioned group name and resource type.
 */
public interface GroupNameCache extends CacheStrategy<TaglibCacheKey, String> {

}
