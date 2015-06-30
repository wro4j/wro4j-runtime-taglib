/*
 * Copyright (c) 2014 Aleksei Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.lifus.wro4j_runtime_taglib.model.resource.uri.strategy;

import ro.isdc.wro.model.resource.ResourceType;
import ro.isdc.wro.util.LazyInitializer;

import com.github.lifus.wro4j_runtime_taglib.model.group.name.VersionedGroupNameFactory;
import com.github.lifus.wro4j_runtime_taglib.model.resource.uri.root.strategy.OptimizedResourcesRootStrategy;

/**
 * Returns versioned URI of optimized resource associated with given group name and resource type.
 */
public final class VersionedResourceUriStrategy extends AbstractResourceUriStrategy {

  public static final String ALIAS = "versioned";

  private final VersionedGroupNameFactory versionedGroupNameFactory;

  // format of URI is the same for all resources
  private final LazyInitializer<String> uriFormatInitializer = new LazyInitializer<String>() {
    @Override
    protected String initialize() {
      return getWroRoot() + "%s.%s";
    }
  };

  public VersionedResourceUriStrategy(
    final String contextPath,
    final OptimizedResourcesRootStrategy optimizedResourcesRootStrategy,
    final VersionedGroupNameFactory versionedGroupNameFactory
  ) {

    super(contextPath, optimizedResourcesRootStrategy);
    this.versionedGroupNameFactory = versionedGroupNameFactory;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String[] getResourcesUris(final String groupName, final ResourceType type) {
    return new String[] { getUriOfOptimizedResource(groupName, type) };
  }

  private String getUriOfOptimizedResource(final String groupName, final ResourceType type) {
    return String.format(getUriFormat(), getPathRelativeToWroRoot(groupName, type), getExtension(type));
  }

  private String getUriFormat() {
    return uriFormatInitializer.get();
  }

  /*
   * I'm not using WroManager#encodeVersionIntoGroupPath(String, ResourceType, boolean) here
   * because DefaultSynchronizedCacheStrategyDecorator#loadValue(CacheKey) gets invoked once for a key
   * thus CssUrlRewritingProcessor wouldn't be able to produce correct URLs.
   */
  private String getPathRelativeToWroRoot(final String groupName, final ResourceType type) {
    return versionedGroupNameFactory.create(groupName, type);
  }

  private String getExtension(final ResourceType type) {
    return type.name().toLowerCase();
  }

}
