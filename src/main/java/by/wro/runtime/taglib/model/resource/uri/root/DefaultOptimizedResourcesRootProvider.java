/*
 * Copyright (c) 2014 Aleksey Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package by.wro.runtime.taglib.model.resource.uri.root;

import java.util.Collection;
import java.util.Map;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;

import ro.isdc.wro.WroRuntimeException;
import ro.isdc.wro.config.ReadOnlyContext;
import ro.isdc.wro.http.WroFilter;
import ro.isdc.wro.model.group.Inject;

/**
 * Finds out the root of WRO resources using {@link FilterRegistration} of {@link WroFilter}.
 * <p>
 *   Requires {@link ServletContext#getFilterRegistrations()} introduced in servlet 3.0.
 * </p>
 */
public final class DefaultOptimizedResourcesRootProvider extends AbstractOptimizedResourcesRootProvider {

  protected static final String FILTER_CLASS_NAME = WroFilter.class.getCanonicalName();

  @Inject
  private ReadOnlyContext context;

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
    final ServletContext servletContext = context.getServletContext();
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