/*
 * Copyright (c) 2014 Aleksey Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package by.wro.runtime.taglib.model.resource.uri.strategy;

import ro.isdc.wro.model.resource.ResourceType;

/**
 * Returns resource URIs associated with group name and resource type.
 */
public interface ResourceUriStrategy {

  /**
   * Returns URIs of all resources associated with given group name and resource type.
   * 
   * @param groupName
   *          name of the group.
   * @param resourceType
   *          type of the resource.
   * @return URIs of resources.
   */
  String[] getResourcesUris(String groupName, ResourceType resourceType);

}
