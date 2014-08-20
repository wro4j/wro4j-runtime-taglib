/*
 * Copyright (c) 2014 Aleksey Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.lifus.wro4j_runtime_taglib.cache;

import static org.apache.commons.lang3.Validate.notNull;

import java.io.Serializable;

import ro.isdc.wro.model.resource.ResourceType;

/**
 * Immutable key for caches used in tag library.
 */
public final class TaglibCacheKey implements Serializable {

  private static final long  serialVersionUID = -3645334183587858068L;

  private final String name;
  private final ResourceType type;

  public TaglibCacheKey(final String name, final ResourceType type) {
    notNull(name);
    notNull(type);
    this.name = name;
    this.type = type;
  }

  /** @return name of the group. */
  public String getName() {
    return name;
  }

  /** @return resource type. */
  public ResourceType getType() {
    return type;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(final Object that) {
    if (this == that) {
      return true;
    } else if (!(that instanceof TaglibCacheKey)) {
      return false;
    }

    final TaglibCacheKey other = (TaglibCacheKey) that;
    return type == other.type && name.equals(other.name);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {
    int result = 17;
    result = 31 * result + name.hashCode();
    result = 31 * result + type.hashCode();
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return name + "." + type.name().toLowerCase();
  }

}
