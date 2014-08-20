/*
 * Copyright (c) 2014 Aleksei Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.lifus.wro4j_runtime_taglib.tag;

import ro.isdc.wro.model.resource.ResourceType;

import com.github.lifus.wro4j_runtime_taglib.model.resource.uri.strategy.ConfigurableResourceUriStrategy;

/**
 * Renders script resources specified by group name and groupPathStrategy.
 * 
 * @see ConfigurableResourceUriStrategy
 */
public final class ScriptTag extends AbstractWroElementTag {

  private static final String SCRIPT = "<script src='%s'></script>";

  /**
   * {@inheritDoc}
   */
  @Override
  protected ResourceType getResourceType() {
    return ResourceType.JS;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String getFormat() {
    return SCRIPT;
  }

}
