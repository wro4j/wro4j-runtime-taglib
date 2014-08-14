/*
 * Copyright (c) 2014 Aleksey Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package by.wro.runtime.taglib.model.group.name.cache;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.mockito.Mock;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import ro.isdc.wro.cache.CacheStrategy;
import ro.isdc.wro.cache.support.CacheStrategyDecorator;

/**
 * Test for {@link GroupNameCacheStrategyDecorator}.
 */
public class GroupNameCacheStrategyDecoratorTest extends PowerMockTestCase {

  private CacheStrategyDecorator<Object,Object> decorator;

  @Mock
  private CacheStrategy<Object, Object> cacheStrategy;

  @BeforeMethod
  private void setUp() {
    decorator = new CacheStrategyDecorator<>(cacheStrategy);
  }

  @Test
  public void shouldDecorateCacheStrategy() {
    assertThat(decorator.getDecoratedObject(), is(cacheStrategy));
  }

}
