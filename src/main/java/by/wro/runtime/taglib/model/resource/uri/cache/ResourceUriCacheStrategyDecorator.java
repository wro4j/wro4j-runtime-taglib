/*
 * Copyright (c) 2014 Aleksey Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package by.wro.runtime.taglib.model.resource.uri.cache;

import ro.isdc.wro.cache.CacheStrategy;
import ro.isdc.wro.cache.support.AbstractSynchronizedCacheStrategyDecorator;
import by.wro.runtime.taglib.cache.TaglibCacheKey;
import by.wro.runtime.taglib.model.resource.uri.strategy.ResourceUriStrategy;

/**
 * Required to generate resource URIs associated with group name and resource type
 * and then cache them using {@link CacheStrategy} for future reference.
 */
public final class ResourceUriCacheStrategyDecorator extends AbstractSynchronizedCacheStrategyDecorator<TaglibCacheKey, String[]>
                                                     implements ResourceUriCache {

  // I wish I could inject this but the only constructor for Injector is a package private constructor
  private final ResourceUriStrategy groupPathsStrategy;

  public ResourceUriCacheStrategyDecorator(
    final CacheStrategy<TaglibCacheKey, String[]> decorated,
    final ResourceUriStrategy groupPathsStrategy
  ) {
    super(decorated);
    this.groupPathsStrategy = groupPathsStrategy;
  }

  /**
   * {@inheritDoc}
   * <p>
   *   Uses {@link ResourceUriStrategy} to get URIs.
   * </p>
   */
  @Override
  protected String[] loadValue(final TaglibCacheKey key) {
    return groupPathsStrategy.getResourcesUris(key.getName(), key.getType());
  }
}
