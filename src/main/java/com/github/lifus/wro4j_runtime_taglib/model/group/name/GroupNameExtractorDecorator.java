/*
 * Copyright (c) 2014 Aleksey Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.lifus.wro4j_runtime_taglib.model.group.name;

import javax.servlet.http.HttpServletRequest;

import ro.isdc.wro.model.group.GroupExtractor;
import ro.isdc.wro.model.group.processor.GroupExtractorDecorator;
import ro.isdc.wro.model.resource.ResourceType;

import com.github.lifus.wro4j_runtime_taglib.cache.TaglibCacheKey;
import com.github.lifus.wro4j_runtime_taglib.model.group.name.cache.GroupNameCache;
import com.github.lifus.wro4j_runtime_taglib.model.group.name.cache.GroupNameCacheInitializer;

/**
 * Extracts name of the group.
 */
public final class GroupNameExtractorDecorator extends GroupExtractorDecorator {

  private final GroupNameCacheInitializer groupNameCacheInitializer;

  public GroupNameExtractorDecorator(final GroupExtractor decorated, final GroupNameCacheInitializer nameCacheInitializer) {
    super(decorated);
    this.groupNameCacheInitializer = nameCacheInitializer;
  }

  /**
   * Retrieves name of the group.
   * If required, unversions group name retrieved from request URI.
   *
   * @param request
   *          current request
   * @return group names.
   */
  @Override
  public String getGroupName(final HttpServletRequest request) {
    final String requestedGroupName = super.getGroupName(request);
    final ResourceType type = super.getResourceType(request);

    final String unversionedGroupName = getGroupNameFromCache(requestedGroupName, type);
    if (unversionedGroupName != null) {
      return unversionedGroupName;
    } else {
      return requestedGroupName;
    }
  }

  private String getGroupNameFromCache(final String publicName, final ResourceType type) {
    final TaglibCacheKey cacheKey = new TaglibCacheKey(publicName, type);
    final GroupNameCache nameCache = groupNameCacheInitializer.get();
    return nameCache.get(cacheKey);
  }

}
