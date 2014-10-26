/*
 * Copyright (c) 2014 Aleksei Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.lifus.wro4j_runtime_taglib.model.resource.uri.root;

import static com.github.lifus.wro4j_runtime_taglib.model.resource.uri.root.DefaultOptimizedResourcesRootProvider.FILTER_CLASS_NAME;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;

import org.mockito.Mock;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import ro.isdc.wro.WroRuntimeException;

/**
 * Tests for {@link DefaultOptimizedResourcesRootProvider}.
 */
public class DefaultMappingStrategyTest extends PowerMockTestCase {

  private static final String NO_PATTERN = null;

  private DefaultOptimizedResourcesRootProvider defaultOptimizedResourcesRootProvider;

  @Mock
  private ServletContext servletContext;

  @BeforeMethod
  public void setUp() {
    defaultOptimizedResourcesRootProvider = new DefaultOptimizedResourcesRootProvider(servletContext);
  }

  @Test(expectedExceptions=WroRuntimeException.class, expectedExceptionsMessageRegExp="[\\w\\.]+ is not registered in web.xml.")
  public void shouldThrowAnExceptionIfThereAreNoFilters() {
    givenFiltersAreRegistratered();

    whenGettingRoot();
  }

  @Test(expectedExceptions=WroRuntimeException.class, expectedExceptionsMessageRegExp="[\\w\\.]+ is not registered in web.xml.")
  public void shouldThrowAnExceptionIfThereIsNoWroFilter() {
    givenFiltersAreRegistratered(mock(FilterRegistration.class));

    whenGettingRoot();
  }

  @Test(expectedExceptions=WroRuntimeException.class, expectedExceptionsMessageRegExp="url-pattern for null is not found in web.xml.")
  public void shouldThrowAnExceptionIfThereIsNoUrlPattern() {
    givenFiltersAreRegistratered(mockWroFilterRegistration(NO_PATTERN));

    whenGettingRoot();
  }

  @Test
  public void shouldReturnRoot() {
    givenFiltersAreRegistratered(mockWroFilterRegistration("/wro/*"));

    final String root = whenGettingRoot();
    assertThat(whenGettingRoot(), is(root));
  }

  private FilterRegistration mockWroFilterRegistration(final String pattern) {
    final FilterRegistration filterRegistration = mock(FilterRegistration.class);

    when(filterRegistration.getClassName()).thenReturn(FILTER_CLASS_NAME);

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
