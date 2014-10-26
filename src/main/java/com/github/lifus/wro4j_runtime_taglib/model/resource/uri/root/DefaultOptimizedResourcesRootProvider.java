/*
 * Copyright (c) 2014 Aleksei Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.lifus.wro4j_runtime_taglib.model.resource.uri.root;

import java.util.Collection;
import java.util.Map;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;

import ro.isdc.wro.WroRuntimeException;
import ro.isdc.wro.http.WroFilter;

/**
 * Finds out the root of WRO resources using {@link FilterRegistration} of {@link WroFilter}.
 * <p>
 *   Requires {@link ServletContext#getFilterRegistrations()} introduced in servlet 3.0.
 * </p>
 */
public final class DefaultOptimizedResourcesRootProvider extends AbstractOptimizedResourcesRootProvider {

  protected static final String FILTER_CLASS_NAME = WroFilter.class.getCanonicalName();

  private final ServletContext servletContext;

  public DefaultOptimizedResourcesRootProvider(final ServletContext servletContext) {
    this.servletContext = servletContext;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String findOutRoot() {
    for (final FilterRegistration filterRegistration : getFilterRegistrations()) {
      if (FILTER_CLASS_NAME.equals(filterRegistration.getClassName())) {
        return retrieveRoot(filterRegistration);
      }
    }
    throw new WroRuntimeException(String.format("%s is not registered in web.xml.", FILTER_CLASS_NAME));
  }

  private Collection<? extends FilterRegistration> getFilterRegistrations() {
    final Map<String, ? extends FilterRegistration> filterRegistrations = servletContext.getFilterRegistrations();
    return filterRegistrations.values();
  }

  private String retrieveRoot(final FilterRegistration filterRegistration) {
    final Collection<String> patternMappings = filterRegistration.getUrlPatternMappings();
    if (!patternMappings.isEmpty()) {
      // return the first one
      return patternMappings.iterator().next().replace("*", "");
    } else {
      String message = String.format("url-pattern for %s is not found in web.xml.", filterRegistration.getName());
      throw new WroRuntimeException(message);
    }
  }

}
