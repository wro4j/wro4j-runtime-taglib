/*
 * Copyright (c) 2014 Aleksei Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.lifus.wro4j_runtime_taglib.model.resource.uri.strategy;

import static com.github.lifus.wro4j_runtime_taglib.model.resource.uri.strategy.AbstractResourceUriStrategy.DEFAULT_RESOURCE_DOMAIN;
import static com.github.lifus.wro4j_runtime_taglib.model.resource.uri.strategy.AbstractResourceUriStrategy.RESOURCE_DOMAIN_KEY;
import static org.powermock.api.mockito.PowerMockito.when;

import javax.servlet.ServletContext;

import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;

import ro.isdc.wro.model.resource.ResourceType;

import com.github.lifus.wro4j_runtime_taglib.config.ConfigurationHelper;
import com.github.lifus.wro4j_runtime_taglib.model.resource.uri.root.strategy.OptimizedResourcesRootStrategy;

/**
 * Base class for testing of {@link AbstractResourceUriStrategy} derivatives.
 */
@PrepareForTest(ConfigurationHelper.class)
public abstract class ResourceUriStrategyTestBase extends PowerMockTestCase {

  protected static final String RESOURCE_DOMAIN = "//some-cdn";
  protected static final String CONTEXT_PATH = "/contextPath";
  protected static final String ROOT = "/root";
  protected static final ResourceType RESOURCE_TYPE = ResourceType.values()[0];
  protected static final String GROUP = "group";

  @Mock
  private ServletContext servletContext;
  @Mock
  private OptimizedResourcesRootStrategy optimizedResourcesRootStrategy;
  @Mock
  private ConfigurationHelper configurationHelper;

  protected OptimizedResourcesRootStrategy getOptimizedResourcesRootStrategy() {
    return optimizedResourcesRootStrategy;
  }

  protected ConfigurationHelper getConfigurationHelper() {
    return configurationHelper;
  }

  protected abstract ResourceUriStrategy getResourceUriStrategy();

  protected void givenResourceDomainHasBeenSetUp() {
    when(configurationHelper.getProperty(RESOURCE_DOMAIN_KEY, DEFAULT_RESOURCE_DOMAIN)).thenReturn(RESOURCE_DOMAIN);
  }

  protected void givenResourceDomainHasNotBeenSetUp() {
    when(configurationHelper.getProperty(RESOURCE_DOMAIN_KEY, DEFAULT_RESOURCE_DOMAIN)).thenReturn(DEFAULT_RESOURCE_DOMAIN);
  }

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
