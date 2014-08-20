/*
 * Copyright (c) 2014 Aleksey Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.lifus.wro4j_runtime_taglib.model.group.name.cache;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mock;

import org.mockito.Mock;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import ro.isdc.wro.cache.CacheStrategy;

import com.github.lifus.wro4j_runtime_taglib.cache.CacheStrategyFactory;

/**
 * Tests for {@link GroupNameCacheInitializer}.
 */
public class GroupNameCacheInitializerTest extends PowerMockTestCase {

  private GroupNameCacheInitializer groupNameCacheInitializer;

  @Mock
  private CacheStrategyFactory cacheStrategyFactory;

  @BeforeMethod
  public void createCacheFactory() {
    groupNameCacheInitializer = new GroupNameCacheInitializer(cacheStrategyFactory);
  }

  @Test
  public void shouldCreateNameCache() {
    givenCacheStrategyHasBeenSet();

    assertThat(groupNameCacheInitializer.get(), is(instanceOf(GroupNameCache.class)));
  }

  @Test
  public void shouldReturnTheSameInstanceOfNameCache() {
    givenCacheStrategyHasBeenSet();

    final GroupNameCache initiallyCreatedCache = groupNameCacheInitializer.get();
    assertThat(groupNameCacheInitializer.get(), is(initiallyCreatedCache));
  }

  @SuppressWarnings("unchecked")
  private void givenCacheStrategyHasBeenSet() {
    when(cacheStrategyFactory.create()).thenReturn(mock(CacheStrategy.class));
  }
}
