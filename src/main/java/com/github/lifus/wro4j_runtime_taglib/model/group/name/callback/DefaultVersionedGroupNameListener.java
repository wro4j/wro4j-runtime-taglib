/*
 * Copyright (c) 2014 Aleksei Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.lifus.wro4j_runtime_taglib.model.group.name.callback;

import ro.isdc.wro.model.resource.ResourceType;

import com.github.lifus.wro4j_runtime_taglib.cache.TaglibCacheKey;
import com.github.lifus.wro4j_runtime_taglib.model.group.name.cache.GroupNameCache;

/**
 * Listens for versioned group names creation.
 */
public final class DefaultVersionedGroupNameListener implements VersionedGroupNameListener {

  private final GroupNameCache groupNameCache;

  public DefaultVersionedGroupNameListener(final GroupNameCache groupNameCache) {
    this.groupNameCache = groupNameCache;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onVersionedNameCreated(final String versionedGroupName, final String groupName, final ResourceType type) {
    final TaglibCacheKey key = new TaglibCacheKey(processVersionedGroupName(versionedGroupName), type);
    groupNameCache.put(key, groupName);
  }

  /*
   * Retrieves the last portion of generated "name" because there is
   * FolderHashEncoderNamingStrategy and there might be some custom NamingStrategies.
   */
  private String processVersionedGroupName(final String versionedGroupName) {
    return removeFolders(versionedGroupName);
  }

  private String removeFolders(final String versionedGroupName) {
    final String[] versionedGroupNameParts = versionedGroupName.split("/");
    final int indexOfGroupName = versionedGroupNameParts.length - 1;
    return versionedGroupNameParts[indexOfGroupName];
  }

}
