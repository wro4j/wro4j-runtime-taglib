/*
 * Copyright (c) 2014 Aleksey Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.lifus.wro4j_runtime_taglib.model.resource.uri.cache;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

import org.mockito.Mock;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import ro.isdc.wro.cache.CacheStrategy;
import ro.isdc.wro.model.resource.ResourceType;

import com.github.lifus.wro4j_runtime_taglib.cache.TaglibCacheKey;
import com.github.lifus.wro4j_runtime_taglib.model.resource.uri.strategy.ResourceUriStrategy;

/**
 * Tests for {@link ResourceUriCacheStrategyDecorator}
 */
public class ResourceUriCacheStrategyDecoratorTest extends PowerMockTestCase {

  private ResourceUriCacheStrategyDecorator decorator;

  @Mock
  private CacheStrategy<TaglibCacheKey, String[]> cacheStrategy;
  @Mock
  private ResourceUriStrategy resourceUriStrategy;

  @BeforeMethod
  public void setUp() {
    decorator = new ResourceUriCacheStrategyDecorator(cacheStrategy, resourceUriStrategy);
  }

  @Test
  public void shouldDecorateCacheStrategy() {
    assertThat(decorator.getDecoratedObject(), is(cacheStrategy));
  }

  @Test
  public void shouldLoadValue() {
    final String[] uris = new String[1];
    final String groupName = "group";
    final ResourceType resourceType = ResourceType.values()[0];
    final TaglibCacheKey key = new TaglibCacheKey(groupName, resourceType);

    when(resourceUriStrategy.getResourcesUris(groupName, resourceType)).thenReturn(uris);

    assertThat(decorator.loadValue(key), is(uris));
  }
}
