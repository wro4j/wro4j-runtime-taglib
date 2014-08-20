/*
 * Copyright (c) 2014 Aleksei Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.lifus.wro4j_runtime_taglib.tag;

import ro.isdc.wro.model.resource.ResourceType;

import com.github.lifus.wro4j_runtime_taglib.context.TaglibContext;
import com.github.lifus.wro4j_runtime_taglib.model.resource.uri.strategy.ConfigurableResourceUriStrategy;

/**
 * Base class for rendering resources associated with specified group name and configured groupPathStrategy.
 * 
 * @see ConfigurableResourceUriStrategy
 */
public abstract class AbstractWroElementTag extends AbstractHtmlElementTag {

  private String group;

  /**
   * Defines group name.
   *
   * @param group
   *          WRO group name
   */
  public void setGroup(final String group) {
    this.group = group;
  }

  /** @return WRO group name */
  protected String getGroup() {
    return this.group;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected final CharSequence getOutput() {
    final StringBuilder output = new StringBuilder();
    for (final String resourceUri : getResourceUris()) {
      String outputForResource = String.format(getFormat(), resourceUri);
      output.append(outputForResource);
    }
    return output.toString();
  }

  private String[] getResourceUris() {
    return getTaglibContext().getResourceUris(getGroup(), getResourceType());
  }

  private TaglibContext getTaglibContext() {
    return TaglibContext.get(getServletContext());
  }

  /**
   * @return web resource type.
   */
  protected abstract ResourceType getResourceType();

  /**
   * @return format of resource HTML representation.
   */
  protected abstract String getFormat();
}
