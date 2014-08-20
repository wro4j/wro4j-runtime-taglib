/*
 * Copyright (c) 2014 Aleksei Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.lifus.wro4j_runtime_taglib.context;

import javax.servlet.ServletContext;

import ro.isdc.wro.model.resource.ResourceType;

import com.github.lifus.wro4j_runtime_taglib.cache.TaglibCacheKey;
import com.github.lifus.wro4j_runtime_taglib.model.resource.uri.cache.ResourceUriCache;
import com.github.lifus.wro4j_runtime_taglib.model.resource.uri.cache.ResourceUriCacheInitializer;

/**
 * Stores all the data required by tag library during it's run time.
 */
public final class TaglibContext {

  private static final String KEY = TaglibContext.class.getName();

  private final ResourceUriCacheInitializer resourceUriCacheInitializer;

  /**
   * Initializes tag library context exclusively for current servlet.
   *
   * @param servletContext
   *          current servlet context.
   */
  public static void initialize(final ServletContext servletContext) {
    final TaglibContextFactory taglibContextFactory = new TaglibContextFactory(servletContext);

    servletContext.setAttribute(KEY, taglibContextFactory.create());
  }

  /**
   * Gives tag library context for given servlet context.
   *
   * @param servletContext
   *          current servlet context.
   * @return tag library context.
   */
  public static TaglibContext get(final ServletContext servletContext) {
    return (TaglibContext) servletContext.getAttribute(KEY);
  }

  // should be invoked from factory only.
  TaglibContext(final ResourceUriCacheInitializer groupPathsCacheInitializer) {
    this.resourceUriCacheInitializer = groupPathsCacheInitializer;
  }

  /**
   * Returns resource URIs associated with given group name and resource type.
   *
   * @param groupName
   *          name of the group.
   * @param type
   *          resource type.
   * @return resource URIs.
   */
  public String[] getResourceUris(final String groupName, final ResourceType type) {
    final TaglibCacheKey cacheKey = new TaglibCacheKey(groupName, type);
    final ResourceUriCache resourceUriCache = resourceUriCacheInitializer.get();
    return resourceUriCache.get(cacheKey);
  }

}
