/*
 * Copyright (c) 2014 Aleksei Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.lifus.wro4j_runtime_taglib.model.resource.uri.cache;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mock;

import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import ro.isdc.wro.cache.CacheStrategy;
import ro.isdc.wro.model.group.processor.Injector;

import com.github.lifus.wro4j_runtime_taglib.cache.CacheStrategyFactory;
import com.github.lifus.wro4j_runtime_taglib.config.ConfigurationHelper;
import com.github.lifus.wro4j_runtime_taglib.manager.InjectorInitializer;
import com.github.lifus.wro4j_runtime_taglib.model.group.name.cache.GroupNameCacheInitializer;

/**
 * Tests for {@link ResourceUriCacheInitializer}.
 */
@PrepareForTest(Injector.class)
public class ResourceUriCacheInitializerTest extends PowerMockTestCase {

  private ResourceUriCacheInitializer resourceUriCacheInitializer;

  @Mock
  private CacheStrategyFactory cacheStrategyFactory;
  @Mock
  private InjectorInitializer injectorInitializer;

  @BeforeMethod
  public void setUp() {
    resourceUriCacheInitializer = new ResourceUriCacheInitializer(mock(GroupNameCacheInitializer.class), mock(ConfigurationHelper.class), cacheStrategyFactory, injectorInitializer);
  }

  @Test
  public void shouleCreateResourceUriCache() {
    givenInjectorHasBeenInitialized();
    givenCacheStrategyHasBeenConfigured();

    thenShouldInitializeResourceUriCache();
  }

  private void givenInjectorHasBeenInitialized() {
    when(injectorInitializer.get()).thenReturn(mock(Injector.class));
  }

  @SuppressWarnings("unchecked")
  private void givenCacheStrategyHasBeenConfigured() {
    when(cacheStrategyFactory.create()).thenReturn(mock(CacheStrategy.class));
  }

  private void thenShouldInitializeResourceUriCache() {
    assertThat(resourceUriCacheInitializer.initialize(), is(instanceOf(ResourceUriCache.class)));
  }

}