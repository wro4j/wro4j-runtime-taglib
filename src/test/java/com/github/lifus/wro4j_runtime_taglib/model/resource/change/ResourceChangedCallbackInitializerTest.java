/*
 * Copyright (c) 2014 Aleksei Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.lifus.wro4j_runtime_taglib.model.resource.change;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;

import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import ro.isdc.wro.manager.factory.WroManagerFactory;

import com.github.lifus.wro4j_runtime_taglib.model.resource.uri.cache.ResourceUriCacheInitializer;

/**
 * Tests for {@link ResourceChangedCallbackInitializer}.
 */
@PrepareForTest(ResourceUriCacheInitializer.class)
public class ResourceChangedCallbackInitializerTest extends PowerMockTestCase {

  private ResourceChangedCallbackInitializer resourceChangedCallbackInitializer;

  @Mock
  private WroManagerFactory wroManagerFactory;
  @Mock
  private ResourceUriCacheInitializer groupPathsCacheInitializer;


  @BeforeMethod
  public void setUp() {
    resourceChangedCallbackInitializer = new ResourceChangedCallbackInitializer(wroManagerFactory, groupPathsCacheInitializer);
  }

  @Test
  public void shouldReturnTheSameInstance() {
    final ResourceChangedCallback initialInstance = resourceChangedCallbackInitializer.get();
    final ResourceChangedCallback followingInstance = resourceChangedCallbackInitializer.get();

    assertThat(followingInstance, is(initialInstance));
  }

  @Test
  public void shouldAccessResourceUrisCache() {
    resourceChangedCallbackInitializer.get();

    verify(groupPathsCacheInitializer).get();
  }

}
