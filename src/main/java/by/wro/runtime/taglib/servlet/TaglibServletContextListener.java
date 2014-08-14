/*
 * Copyright (c) 2014 Aleksey Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package by.wro.runtime.taglib.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import ro.isdc.wro.http.WroServletContextListener;
import by.wro.runtime.taglib.context.TaglibContext;

/**
 * Listens for servlet context events. Manages {@link TaglibContext}.
 * <p>
 *   Should be added to web.xml after {@link WroServletContextListener}.
 * </p>
 *
 * @see TaglibContext
 */
public final class TaglibServletContextListener implements ServletContextListener {

  /**
   * {@inheritDoc}
   */
  @Override
  public void contextInitialized(final ServletContextEvent event) {
    TaglibContext.initialize(event.getServletContext());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void contextDestroyed(final ServletContextEvent event) {
  }

}
