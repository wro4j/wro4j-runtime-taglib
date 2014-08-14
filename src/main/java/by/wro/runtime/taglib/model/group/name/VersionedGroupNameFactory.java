/*
 * Copyright (c) 2014 Aleksey Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package by.wro.runtime.taglib.model.group.name;

import ro.isdc.wro.model.resource.ResourceType;

/**
 * Required to version group names in a consistent manner.
 */
public interface VersionedGroupNameFactory {

  /**
   * @param groupName
   *          name of the group.
   * @param type
   *          resources type.
   * @return versioned group name.
   */
  String create(String groupName, ResourceType type);

}
