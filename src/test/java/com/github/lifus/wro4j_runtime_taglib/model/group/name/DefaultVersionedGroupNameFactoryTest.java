/*
 * Copyright (c) 2014 Aleksei Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.lifus.wro4j_runtime_taglib.model.group.name;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mock;

import java.io.IOException;
import java.io.InputStream;

import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import ro.isdc.wro.cache.CacheKey;
import ro.isdc.wro.cache.CacheStrategy;
import ro.isdc.wro.cache.CacheValue;
import ro.isdc.wro.manager.WroManager;
import ro.isdc.wro.manager.factory.WroManagerFactory;
import ro.isdc.wro.model.resource.ResourceType;
import ro.isdc.wro.model.resource.support.naming.NamingStrategy;

import com.github.lifus.wro4j_runtime_taglib.model.group.name.callback.VersionedGroupNameCallbackRegistry;

/**
 * Tests for {@link DefaultVersionedGroupNameFactory}
 */
@PrepareForTest({WroManager.class, CacheValue.class, VersionedGroupNameCallbackRegistry.class})
public class DefaultVersionedGroupNameFactoryTest extends PowerMockTestCase {

  private static final String NAME = "name";
  private static final ResourceType TYPE = ResourceType.values()[0];

  private DefaultVersionedGroupNameFactory defaultVersionedGroupNameFactory;

  @Mock
  private WroManagerFactory wroManagerFactory;
  @Mock
  private CacheStrategy<CacheKey, CacheValue> cacheStrategy;
  @Mock
  private NamingStrategy namingStrategy;
  @Mock
  private VersionedGroupNameCallbackRegistry listeners;
  private String result;

  @BeforeMethod
  public void setUp() {
    defaultVersionedGroupNameFactory = new DefaultVersionedGroupNameFactory(wroManagerFactory, listeners);
  }

  @Test(expectedExceptions=RuntimeException.class)
  public void shouldThrowRuntimeExceptionInCaseOfIoException() throws IOException {
    givenWroManagerFactoryIsValid();

    whenThrowsIoException();
    whenCreatingVesrionedName();
  }

  @SuppressWarnings("unchecked")
  private void whenThrowsIoException() throws IOException {
    givenCacheValueExists();
    when(namingStrategy.rename(eq(NAME), any(InputStream.class))).thenThrow(IOException.class);
  }

  @Test
  public void shouldCreateVersionedNameAndNotifyListeners() throws IOException {
    final String versionedName = "versioned name";
    givenWroManagerFactoryIsValid();
    givenResultingVersionedName(versionedName);

    whenCreatingVesrionedName();

    thenCreateVersionedNameAndNotifyListeners(versionedName);
  }

  private void givenWroManagerFactoryIsValid() {
    final WroManager wroManager = mock(WroManager.class);
    when(wroManagerFactory.create()).thenReturn(wroManager);
    when(wroManager.getNamingStrategy()).thenReturn(namingStrategy);
    when(wroManager.getCacheStrategy()).thenReturn(cacheStrategy);
  }

  private void givenResultingVersionedName(final String versionedName) throws IOException {
    givenCacheValueExists();
    when(namingStrategy.rename(eq(NAME), any(InputStream.class))).thenReturn(versionedName);
  }

  private void givenCacheValueExists() {
    final CacheValue cacheValue = mock(CacheValue.class);
    when(cacheStrategy.get(any(CacheKey.class))).thenReturn(cacheValue);
    when(cacheValue.getRawContent()).thenReturn("processed content");
  }

  private void whenCreatingVesrionedName() {
    result = defaultVersionedGroupNameFactory.create(NAME, TYPE);
  }

  private void thenCreateVersionedNameAndNotifyListeners(final String versionedName) throws IOException {
    assertThat(result, is(versionedName));

    verify(namingStrategy).rename(eq(NAME), any(InputStream.class));
    verify(listeners).onVersionedNameCreated(versionedName, NAME, TYPE);
  }

}
