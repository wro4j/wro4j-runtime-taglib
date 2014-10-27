/*
 * Copyright (c) 2014 Aleksei Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.lifus.wro4j_runtime_taglib.model.group.name;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import ro.isdc.wro.cache.CacheKey;
import ro.isdc.wro.cache.CacheStrategy;
import ro.isdc.wro.cache.CacheValue;
import ro.isdc.wro.manager.factory.WroManagerFactory;
import ro.isdc.wro.model.group.processor.GroupsProcessor;
import ro.isdc.wro.model.resource.ResourceType;
import ro.isdc.wro.model.resource.support.naming.NamingStrategy;

import com.github.lifus.wro4j_runtime_taglib.model.group.name.callback.VersionedGroupNameListener;

/**
 * Uses {@link NamingStrategy} and {@link GroupsProcessor} to version group names.
 */
public final class DefaultVersionedGroupNameFactory implements VersionedGroupNameFactory {

  private final WroManagerFactory wroManagerFactory;

  private final VersionedGroupNameListener versionedNameListeners;

  public DefaultVersionedGroupNameFactory(final WroManagerFactory wroManagerFactory, final VersionedGroupNameListener listeners) {
    this.wroManagerFactory = wroManagerFactory;
    this.versionedNameListeners = listeners;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String create(final String groupName, final ResourceType type) {
    try (InputStream inputStream = getInputStream(groupName, type)) {
      final String versionedGroupName = getNamingStrategy().rename(groupName, inputStream);
      versionedNameListeners.onVersionedNameCreated(versionedGroupName, groupName, type);
      return versionedGroupName;
    } catch (final IOException exception) {
      throw new RuntimeException(exception);
    }
  }

  private NamingStrategy getNamingStrategy() {
    return wroManagerFactory.create().getNamingStrategy();
  }

  /*
   * Returns input stream for optimized resource associated with given group name and resource type.
   */
  private InputStream getInputStream(final String group, final ResourceType type) {
    final CacheKey cacheKey = new CacheKey(group, type);
    final CacheValue cacheValue = getCacheStrategy().get(cacheKey);
    final String rawContent = cacheValue.getRawContent();
    return new ByteArrayInputStream(rawContent.getBytes()); // don't care about encoding
  }

  private CacheStrategy<CacheKey, CacheValue> getCacheStrategy() {
    return wroManagerFactory.create().getCacheStrategy();
  }

}
