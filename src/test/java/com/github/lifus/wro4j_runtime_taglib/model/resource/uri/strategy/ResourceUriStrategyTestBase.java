/*
 * Copyright (c) 2014 Aleksei Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.lifus.wro4j_runtime_taglib.model.resource.uri.strategy;

import static org.powermock.api.mockito.PowerMockito.when;

import javax.servlet.ServletContext;

import org.mockito.Mock;
import org.powermock.modules.testng.PowerMockTestCase;

import ro.isdc.wro.model.resource.ResourceType;

import com.github.lifus.wro4j_runtime_taglib.model.resource.uri.root.strategy.OptimizedResourcesRootStrategy;

/**
 * Base class for testing of {@link AbstractResourceUriStrategy} derivatives.
 */
public abstract class ResourceUriStrategyTestBase extends PowerMockTestCase {

  protected static final String CONTEXT_PATH = "contextPath";
  protected static final String ROOT = "root";
  protected static final ResourceType RESOURCE_TYPE = ResourceType.values()[0];
  protected static final String GROUP = "group";

  @Mock
  private ServletContext servletContext;
  @Mock
  private OptimizedResourcesRootStrategy optimizedResourcesRootStrategy;

  protected OptimizedResourcesRootStrategy getOptimizedResourcesRootStrategy() {
    return optimizedResourcesRootStrategy;
  }

  protected abstract ResourceUriStrategy getResourceUriStrategy();

  protected void givenWroRootHasBeenSetUp() {
    when(optimizedResourcesRootStrategy.getRoot()).thenReturn(ROOT);
  }

  protected void givenContextPathHasBeenSetUp() {
    when(servletContext.getContextPath()).thenReturn(CONTEXT_PATH);
  }

  protected String[] whenGetResourceUris() {
    return getResourceUriStrategy().getResourcesUris(GROUP, RESOURCE_TYPE);
  }
}
