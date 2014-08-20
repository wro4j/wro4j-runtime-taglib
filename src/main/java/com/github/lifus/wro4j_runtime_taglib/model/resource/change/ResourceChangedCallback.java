/*
 * Copyright (c) 2014 Aleksei Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.lifus.wro4j_runtime_taglib.model.resource.change;

import java.util.Collection;

import ro.isdc.wro.manager.callback.LifecycleCallbackSupport;
import ro.isdc.wro.model.WroModelInspector;
import ro.isdc.wro.model.factory.WroModelFactory;
import ro.isdc.wro.model.group.Inject;
import ro.isdc.wro.model.resource.Resource;
import ro.isdc.wro.model.resource.ResourceType;

import com.github.lifus.wro4j_runtime_taglib.cache.TaglibCacheKey;
import com.github.lifus.wro4j_runtime_taglib.model.resource.uri.cache.ResourceUriCache;

/**
 * Listens for resource changes.
 */
public final class ResourceChangedCallback extends LifecycleCallbackSupport {

  private static final String[] NO_VALUE = null;

  @Inject
  private WroModelFactory modelFactory;

  private final ResourceUriCache resourceUriCache;

  public ResourceChangedCallback(final ResourceUriCache resourceUriCache) {
    this.resourceUriCache = resourceUriCache;
  }

  /**
   * Finds all groups associated with changed resource,
   * resets paths for these groups in cache.
   * 
   * @param resource
   *          changed resource.
   */
  @Override
  public void onResourceChanged(final Resource resource) {
    final ResourceType type = resource.getType();
    for (final String groupName : getGroupNames(resource)) {
      final TaglibCacheKey key = new TaglibCacheKey(groupName, type);
      resourceUriCache.put(key, NO_VALUE);
    }
  }

  private Collection<String> getGroupNames(final Resource resource) {
    final String uri = resource.getUri();
    final WroModelInspector wroModelInspector = new WroModelInspector(modelFactory.create());
    return wroModelInspector.getGroupNamesContainingResource(uri);
  }

}
