/*
 * Copyright (c) 2014 Aleksei Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.lifus.wro4j_runtime_taglib.cache;

import java.util.Properties;

import ro.isdc.wro.WroRuntimeException;
import ro.isdc.wro.cache.CacheKey;
import ro.isdc.wro.cache.CacheStrategy;
import ro.isdc.wro.cache.CacheValue;
import ro.isdc.wro.cache.ConfigurableCacheStrategy;
import ro.isdc.wro.util.LazyInitializer;

import com.github.lifus.wro4j_runtime_taglib.config.ConfigurationHelper;

/**
 * Instantiates {@link CacheStrategy} objects associated with an alias read from configuration.
 */
public final class ConfigurableCacheStrategyFactory implements CacheStrategyFactory {

  private final ConfigurationHelper configuration;
  private final LazyInitializer<Class<? extends CacheStrategy<?, ?>>> configuredCacheStrategyClassInitializer = new LazyInitializer<Class<? extends CacheStrategy<?, ?>>>() {
    @Override
    protected Class<? extends CacheStrategy<?, ?>> initialize() {
      return getConfiguredCacheStrategyClass();
    }
  };

  @SuppressWarnings("unchecked")
  private Class<? extends CacheStrategy<?, ?>> getConfiguredCacheStrategyClass() {
    final ConfigurableCacheStrategy configurableCacheStrategy = createConfigurableCacheStrategy();
    final CacheStrategy<CacheKey, CacheValue> configuredStrategy = configurableCacheStrategy.getConfiguredStrategy();
    return (Class<? extends CacheStrategy<?, ?>>) configuredStrategy.getClass();
  }

  private ConfigurableCacheStrategy createConfigurableCacheStrategy() {
    return new ConfigurableCacheStrategy() {
      @Override
      protected Properties newProperties() {
        return configuration.getPropertiesForKey(KEY);
      }
    };
  }

  public ConfigurableCacheStrategyFactory(final ConfigurationHelper configuration) {
    this.configuration = configuration;
  }

  /**
   * Instantiates configured {@link CacheStrategy}.
   *
   * @return new instance of configured {@link CacheStrategy}.
   */
  @Override
  @SuppressWarnings("unchecked")
  public <K, V> CacheStrategy<K, V> create() {
    try {
      final Class<CacheStrategy<K, V>> cacheStrategyClass = (Class<CacheStrategy<K, V>>) configuredCacheStrategyClassInitializer.get();
      return cacheStrategyClass.newInstance();
    } catch (InstantiationException | IllegalAccessException e) {
      throw new WroRuntimeException(e.getMessage(), e);
    }
  }

}
