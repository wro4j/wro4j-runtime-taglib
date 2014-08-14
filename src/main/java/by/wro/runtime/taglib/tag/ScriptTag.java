/*
 * Copyright (c) 2014 Aleksey Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package by.wro.runtime.taglib.tag;

import by.wro.runtime.taglib.model.resource.uri.strategy.ConfigurableResourceUriStrategy;
import ro.isdc.wro.model.resource.ResourceType;

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
