/*
 * Copyright (c) 2014 Aleksey Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.lifus.wro4j_runtime_taglib.model.group.name.callback;

import java.util.ArrayList;
import java.util.List;

import ro.isdc.wro.model.resource.ResourceType;
import ro.isdc.wro.model.resource.processor.Destroyable;

/**
 * Manages {@link VersionedGroupNameListener}s.
 */
public final class VersionedGroupNameCallbackRegistry implements VersionedGroupNameListener, Destroyable {

  private final List<VersionedGroupNameListener> versionedGroupNameListeners = new ArrayList<>();

  public void registerPublicNameListener(final VersionedGroupNameListener versionedGroupNameListener) {
    versionedGroupNameListeners.add(versionedGroupNameListener);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onVersionedNameCreated(final String versionedGroupName, final String groupName, final ResourceType type) {
    for (final VersionedGroupNameListener publicNameListener : versionedGroupNameListeners) {
      publicNameListener.onVersionedNameCreated(versionedGroupName, groupName, type);
    }
  }

  /**
   * Removes all listeners.
   */
  @Override
  public void destroy() {
    versionedGroupNameListeners.clear();
  }

}
