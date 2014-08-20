/*
 * Copyright (c) 2014 Aleksey Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.lifus.wro4j_runtime_taglib.model.group.name.callback;

import ro.isdc.wro.model.resource.ResourceType;

/**
 * Listens for versioned group names creation.
 */
public interface VersionedGroupNameListener {

  /**
   * Callback that gets invoked upon versioning of group name.
   *
   * @param versionedGroupName
   *          versioned group name.
   * @param groupName
   *          name of the group.
   * @param type
   *          resources type.
   */
  void onVersionedNameCreated(String versionedGroupName, String groupName, ResourceType type);

}
