/*
 * Copyright (c) 2014 Aleksei Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.lifus.wro4j_runtime_taglib.cache;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.reflect.Whitebox.setInternalState;
import static ro.isdc.wro.cache.ConfigurableCacheStrategy.KEY;

import java.util.Properties;

import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import ro.isdc.wro.WroRuntimeException;
import ro.isdc.wro.cache.CacheStrategy;
import ro.isdc.wro.cache.impl.LruMemoryCacheStrategy;
import ro.isdc.wro.cache.impl.MemoryCacheStrategy;
import ro.isdc.wro.util.LazyInitializer;

import com.github.lifus.wro4j_runtime_taglib.config.ConfigurationHelper;

/**
 * Tests for {@link ConfigurableCacheStrategyFactory}.
 */
@PrepareForTest(ConfigurationHelper.class)
public class ConfigurableCacheStrategyFactoryTest extends PowerMockTestCase {

  private ConfigurableCacheStrategyFactory configurableCacheStrategyFactory;

  @Mock
  private ConfigurationHelper configuration;

  @BeforeMethod
  public void setUp() {
    configurableCacheStrategyFactory = new ConfigurableCacheStrategyFactory(configuration);
  }

  @Test(dataProvider="exceptions", expectedExceptions=WroRuntimeException.class)
  public void shouldThrowWroRuntimeExceptionsUponInvalidInstantiation(final Class<? extends Exception> exception) {
    whenThrowsException(exception);

    configurableCacheStrategyFactory.create();
  }

  @DataProvider(name="exceptions")
  public Object[][] exceptionsData() {
    return new Object[][] {
        { InstantiationException.class },
        { IllegalAccessException.class }};
  }

  @SuppressWarnings("unchecked")
  private void whenThrowsException(final Class<? extends Exception> exception) {
    final LazyInitializer<?> lazyInitializer = mock(LazyInitializer.class);
    when(lazyInitializer.get()).thenThrow(exception);
    setInternalState(configurableCacheStrategyFactory, "configuredCacheStrategyClassInitializer", lazyInitializer);
  }

  @Test
  public void shouldCreateCacheStrategy() {
    givenCacheStrategyIsNotConfigured();

    assertThat(configurableCacheStrategyFactory.create(), is(instanceOf(CacheStrategy.class)));
  }

  private void givenCacheStrategyIsNotConfigured() {
    mockProperties();
  }

  @Test(dataProvider="cacheStrategies")
  public void shouldCreateSpecifiedCacheStrategy(final String alias, @SuppressWarnings("rawtypes") final Class<? extends CacheStrategy> clazz) {
    givenCacheStrategyIs(alias);
    assertThat(configurableCacheStrategyFactory.create(), hasProperty("class", equalTo(clazz)));
  }

  @DataProvider(name="cacheStrategies")
  public Object[][] cacheStrategiesData() {
    return new Object[][] {
        { MemoryCacheStrategy.ALIAS, MemoryCacheStrategy.class },
        { LruMemoryCacheStrategy.ALIAS, LruMemoryCacheStrategy.class }};
  }

  private void givenCacheStrategyIs(final String alias) {
    when(mockProperties().getProperty(KEY)).thenReturn(alias);
  }

  private Properties mockProperties() {
    final Properties properties = mock(Properties.class);
    when(configuration.getPropertiesForKey(KEY)).thenReturn(properties);
    return properties;
  }

}
