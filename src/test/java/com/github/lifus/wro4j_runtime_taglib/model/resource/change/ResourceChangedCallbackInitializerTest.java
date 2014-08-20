/*
 * Copyright (c) 2014 Aleksey Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.lifus.wro4j_runtime_taglib.model.resource.change;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import ro.isdc.wro.model.group.processor.Injector;

import com.github.lifus.wro4j_runtime_taglib.manager.InjectorInitializer;
import com.github.lifus.wro4j_runtime_taglib.model.resource.uri.cache.ResourceUriCacheInitializer;

/**
 * Tests for {@link ResourceChangedCallbackInitializer}.
 */
@PrepareForTest({ResourceUriCacheInitializer.class, InjectorInitializer.class, Injector.class})
public class ResourceChangedCallbackInitializerTest extends PowerMockTestCase {

  private ResourceChangedCallbackInitializer resourceChangedCallbackInitializer;

  @Mock
  private ResourceUriCacheInitializer groupPathsCacheInitializer;
  @Mock
  private InjectorInitializer injectorInitializer;


  @BeforeMethod
  public void setUp() {
    resourceChangedCallbackInitializer = new ResourceChangedCallbackInitializer(groupPathsCacheInitializer, injectorInitializer);
  }

  @Test
  public void shouldReturnTheSameInstance() {
    givenInjectorHasBeenSetUp();

    final ResourceChangedCallback initialInstance = resourceChangedCallbackInitializer.get();
    final ResourceChangedCallback followingInstance = resourceChangedCallbackInitializer.get();

    assertThat(followingInstance, is(initialInstance));
  }

  @Test
  public void shouldInjectIntoCallback() {
    final Injector injector = givenInjectorHasBeenSetUp();

    final ResourceChangedCallback resourceChangedCallback = resourceChangedCallbackInitializer.get();

    verify(injector).inject(resourceChangedCallback);
  }

  @Test
  public void shouldAccessResourceUrisCache() {
    givenInjectorHasBeenSetUp();

    resourceChangedCallbackInitializer.get();

    verify(groupPathsCacheInitializer).get();
  }

  private Injector givenInjectorHasBeenSetUp() {
    final Injector injector = mock(Injector.class);
    when(injectorInitializer.get()).thenReturn(injector);
    when(injector.inject(any(ResourceChangedCallback.class))).thenAnswer(new Answer<ResourceChangedCallback>() {

      @Override
      public ResourceChangedCallback answer(final InvocationOnMock invocation) {
        return (ResourceChangedCallback) invocation.getArguments()[0];
      }
    });
    return injector;
  }

}
