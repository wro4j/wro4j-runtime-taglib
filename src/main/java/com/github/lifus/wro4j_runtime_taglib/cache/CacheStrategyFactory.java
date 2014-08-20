/*
 * Copyright (c) 2014 Aleksey Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.lifus.wro4j_runtime_taglib.cache;

import ro.isdc.wro.cache.CacheStrategy;

/**
 * Instantiates {@link CacheStrategy} objects.
 */
public interface CacheStrategyFactory {

  /**
   * @return new instance of {@link CacheStrategy}.
   */
  <K, V> CacheStrategy<K, V> create();

}
