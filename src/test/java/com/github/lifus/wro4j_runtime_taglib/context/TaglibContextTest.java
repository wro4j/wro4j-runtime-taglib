/*
 * Copyright (c) 2014 Aleksey Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.lifus.wro4j_runtime_taglib.context;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.ServletContext;

import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import ro.isdc.wro.manager.factory.WroManagerFactory;
import ro.isdc.wro.model.resource.ResourceType;

import com.github.lifus.wro4j_runtime_taglib.cache.TaglibCacheKey;
import com.github.lifus.wro4j_runtime_taglib.model.resource.uri.cache.ResourceUriCache;
import com.github.lifus.wro4j_runtime_taglib.model.resource.uri.cache.ResourceUriCacheInitializer;

/**
 * Tests for {@link TaglibContext}.
 */
@PrepareForTest(ResourceUriCacheInitializer.class)
public class TaglibContextTest extends PowerMockTestCase {

  private TaglibContext taglibContext;

  @Mock
  private ServletContext servletContext;
  @Mock
  private ResourceUriCacheInitializer resourceUriCacheInitializer;

  @BeforeMethod
  public void setUp() {
    taglibContext = new TaglibContext(resourceUriCacheInitializer);
  }

  @Test
  public void shouldInitializeTaglibContext() {
    givenWroManagerFactoryHasBeenSetUp();

    TaglibContext.initialize(servletContext);

    verify(servletContext, atLeastOnce()).setAttribute(anyString(), any(TaglibContext.class));
  }

  private void givenWroManagerFactoryHasBeenSetUp() {
    when(servletContext.getAttribute(anyString())).thenReturn(mock(WroManagerFactory.class));
  }

  @Test
  public void shouldReturnTaglibContext() {
    when(servletContext.getAttribute(anyString())).thenReturn(taglibContext);

    assertThat(TaglibContext.get(servletContext), is(taglibContext));
  }

  @Test
  public void shouldReturnResourceUris() {
    final ResourceUriCache pathCache = mock(ResourceUriCache.class);
    when(resourceUriCacheInitializer.get()).thenReturn(pathCache);

    taglibContext.getResourceUris("group", ResourceType.values()[0]);

    verify(pathCache).get(any(TaglibCacheKey.class));
  }

}
