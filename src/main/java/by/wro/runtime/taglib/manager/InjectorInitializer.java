/*
 * Copyright (c) 2014 Aleksey Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package by.wro.runtime.taglib.manager;

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

  private final WroManagerFactory managerFactory;

  public InjectorInitializer(final WroManagerFactory managerFactory) {
    this.managerFactory = managerFactory;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Injector initialize() {
    return InjectorBuilder.create(managerFactory).build();
  }

}
