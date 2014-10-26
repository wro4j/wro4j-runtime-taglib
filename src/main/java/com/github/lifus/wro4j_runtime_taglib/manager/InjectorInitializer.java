/*
 * Copyright (c) 2014 Aleksei Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.lifus.wro4j_runtime_taglib.manager;

import ro.isdc.wro.manager.factory.WroManagerFactory;
import ro.isdc.wro.model.group.processor.Injector;
import ro.isdc.wro.model.group.processor.InjectorBuilder;
import ro.isdc.wro.util.LazyInitializer;

/**
 * Lazily instantiates {@link Injector}.
 *
 * @see InjectorBuilder
 */
public final class InjectorInitializer extends LazyInitializer<Injector> {

  private final WroManagerFactory wroManagerFactory;

  public InjectorInitializer(final WroManagerFactory wroManagerFactory) {
    this.wroManagerFactory = wroManagerFactory;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Injector initialize() {
    return InjectorBuilder.create(wroManagerFactory).build();
  }

}
