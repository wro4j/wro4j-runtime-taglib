/*
 * Copyright (c) 2014 Aleksei Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.lifus.wro4j_runtime_taglib.tag;

import ro.isdc.wro.model.resource.ResourceType;

/**
 * Renders style resources associated with specified group name and configured groupPathStrategy.
 */
public final class StylesheetTag extends AbstractWroElementTag {

  private static final String STYLESHEET = "<link rel='stylesheet' href='%s' />";

  /**
   * {@inheritDoc}
   */
  @Override
  protected ResourceType getResourceType() {
    return ResourceType.CSS;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String getFormat() {
    return STYLESHEET;
  }
}
