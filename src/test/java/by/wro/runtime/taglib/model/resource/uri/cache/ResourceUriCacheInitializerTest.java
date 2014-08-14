/*
 * Copyright (c) 2014 Aleksey Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package by.wro.runtime.taglib.model.resource.uri.cache;

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
import by.wro.runtime.taglib.cache.CacheStrategyFactory;
import by.wro.runtime.taglib.config.ConfigurationHelper;
import by.wro.runtime.taglib.manager.InjectorInitializer;
import by.wro.runtime.taglib.model.group.name.cache.GroupNameCacheInitializer;

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
