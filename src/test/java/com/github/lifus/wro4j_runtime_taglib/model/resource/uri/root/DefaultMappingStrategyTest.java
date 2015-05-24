/*
 * Copyright (c) 2014 Aleksei Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.lifus.wro4j_runtime_taglib.model.resource.uri.root;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;

import org.mockito.Mock;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import ro.isdc.wro.WroRuntimeException;
import ro.isdc.wro.http.ConfigurableWroFilter;
import ro.isdc.wro.http.WroFilter;

/**
 * Tests for {@link DefaultOptimizedResourcesRootProvider}.
 */
public class DefaultMappingStrategyTest extends PowerMockTestCase {

  private static final String PATTERN = "/wro/*";
  private static final String NO_PATTERN = null;
  private static final String EXPECTED_ROOT = "/wro/";

  private DefaultOptimizedResourcesRootProvider defaultOptimizedResourcesRootProvider;

  @Mock
  private ServletContext servletContext;

  @BeforeMethod
  public void setUp() {
    defaultOptimizedResourcesRootProvider = new DefaultOptimizedResourcesRootProvider(servletContext);
  }

  @Test(expectedExceptions=WroRuntimeException.class, expectedExceptionsMessageRegExp="There is no filter assignable to [\\w\\.]+ found in web.xml.")
  public void shouldThrowAnExceptionIfThereAreNoFilters() {
    givenFiltersAreRegistratered();

    whenGettingRoot();
  }

  @Test(expectedExceptions=WroRuntimeException.class, expectedExceptionsMessageRegExp="There is no filter assignable to [\\w\\.]+ found in web.xml.")
  public void shouldThrowAnExceptionIfThereIsNoWroFilter() {
    final Filter someOtherFilter = mock(Filter.class);
    givenFiltersAreRegistratered(mockFilterRegistration(someOtherFilter.getClass(), PATTERN));

    whenGettingRoot();
  }

  @Test(expectedExceptions=WroRuntimeException.class, expectedExceptionsMessageRegExp="url-pattern for null is not found in web.xml.")
  public void shouldThrowAnExceptionIfThereIsNoUrlPattern() {
    givenFiltersAreRegistratered(mockFilterRegistration(WroFilter.class, NO_PATTERN));

    whenGettingRoot();
  }

  @Test
  public void shouldAcceptWroFilter() {
    shouldReturnRootFor(WroFilter.class);
  }

  @Test
  // https://github.com/lifus/wro4j-runtime-taglib/issues/3
  public void shouldAcceptSubclasses() {
    shouldReturnRootFor(ConfigurableWroFilter.class);
  }

  private void shouldReturnRootFor(Class<? extends WroFilter> clazz) {
    givenFiltersAreRegistratered(mockFilterRegistration(clazz, PATTERN));

    assertThat(whenGettingRoot(), is(EXPECTED_ROOT));
  }

  private FilterRegistration mockFilterRegistration(Class<? extends Filter> clazz, String pattern) {
    final FilterRegistration filterRegistration = mock(FilterRegistration.class);

    when(filterRegistration.getClassName()).thenReturn(clazz.getCanonicalName());

    final List<String> urlPatternMappings = new ArrayList<>();
    if (pattern != NO_PATTERN) {
      urlPatternMappings.add(pattern);
    }
    when(filterRegistration.getUrlPatternMappings()).thenReturn(urlPatternMappings);

    return filterRegistration;
  }

  @SuppressWarnings("unchecked")
  private void givenFiltersAreRegistratered(final FilterRegistration... filters) {
    @SuppressWarnings("rawtypes")
    final Map filterRegistrations = mock(Map.class);
    when(servletContext.getFilterRegistrations()).thenReturn(filterRegistrations);

    final List<FilterRegistration> registrations = new ArrayList<>();
    when(filterRegistrations.values()).thenReturn(registrations);

    registrations.addAll(Arrays.asList(filters));
  }

  private String whenGettingRoot() {
    return defaultOptimizedResourcesRootProvider.getRoot();
  }

}
