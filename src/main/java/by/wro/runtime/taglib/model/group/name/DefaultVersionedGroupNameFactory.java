/*
 * Copyright (c) 2014 Aleksey Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package by.wro.runtime.taglib.model.group.name;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import ro.isdc.wro.cache.CacheKey;
import ro.isdc.wro.model.group.Inject;
import ro.isdc.wro.model.group.processor.GroupsProcessor;
import ro.isdc.wro.model.resource.ResourceType;
import ro.isdc.wro.model.resource.support.naming.NamingStrategy;
import by.wro.runtime.taglib.model.group.name.callback.VersionedGroupNameListener;

/**
 * Uses {@link NamingStrategy} and {@link GroupsProcessor} to version group names.
 */
public final class DefaultVersionedGroupNameFactory implements VersionedGroupNameFactory {

  @Inject
  private GroupsProcessor groupsProcessor;
  @Inject
  private NamingStrategy namingStrategy;
  // I wish I could inject this but Injector has only package private constructor
  private final VersionedGroupNameListener versionedNameListeners;

  public DefaultVersionedGroupNameFactory(final VersionedGroupNameListener listeners) {
    this.versionedNameListeners = listeners;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String create(final String groupName, final ResourceType type) {
    try (InputStream inputStream = getInputStream(groupName, type)) {
      final String versionedGroupName = namingStrategy.rename(groupName, inputStream);
      versionedNameListeners.onVersionedNameCreated(versionedGroupName, groupName, type);
      return versionedGroupName;
    } catch (final IOException exception) {
      throw new RuntimeException(exception);
    }
  }

  /*
   * Returns input stream for optimized resource associated with given group name and resource type.
   */
  private InputStream getInputStream(final String group, final ResourceType type) {
    final CacheKey cacheKey = new CacheKey(group, type);
    final String rawContent = groupsProcessor.process(cacheKey);
    return new ByteArrayInputStream(rawContent.getBytes()); // don't care about encoding
  }

}
