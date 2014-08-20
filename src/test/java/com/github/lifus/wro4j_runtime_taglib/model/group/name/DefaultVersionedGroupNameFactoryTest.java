/*
 * Copyright (c) 2014 Aleksey Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.lifus.wro4j_runtime_taglib.model.group.name;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.util.reflection.Whitebox.setInternalState;

import java.io.IOException;
import java.io.InputStream;

import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import ro.isdc.wro.cache.CacheKey;
import ro.isdc.wro.model.group.processor.GroupsProcessor;
import ro.isdc.wro.model.resource.ResourceType;
import ro.isdc.wro.model.resource.support.naming.NamingStrategy;

import com.github.lifus.wro4j_runtime_taglib.model.group.name.callback.VersionedGroupNameCallbackRegistry;

/**
 * Tests for {@link DefaultVersionedGroupNameFactory}
 */
@PrepareForTest({VersionedGroupNameCallbackRegistry.class})
public class DefaultVersionedGroupNameFactoryTest extends PowerMockTestCase {

  private static final String NAME = "name";
  private static final ResourceType TYPE = ResourceType.values()[0];

  private DefaultVersionedGroupNameFactory defaultVersionedGroupNameFactory;

  @Mock
  private GroupsProcessor groupsProcessor;
  @Mock
  private NamingStrategy namingStrategy;
  @Mock
  private VersionedGroupNameCallbackRegistry listeners;
  private String result;

  @BeforeMethod
  public void setUp() {
    defaultVersionedGroupNameFactory = new DefaultVersionedGroupNameFactory(listeners);
  }

  @Test(expectedExceptions=RuntimeException.class)
  public void shouldThrowRuntimeExceptionInCaseOfIoException() throws IOException {
    givenRequiredDataHasBeenInjected();

    whenThrowsIoException();
    whenCreatingVesrionedName();
  }

  @SuppressWarnings("unchecked")
  private void whenThrowsIoException() throws IOException {
    when(groupsProcessor.process(any(CacheKey.class))).thenReturn("processed content");
    when(namingStrategy.rename(eq(NAME), any(InputStream.class))).thenThrow(IOException.class);
  }

  @Test
  public void shouldCreateVersionedNameAndNotifyListeners() throws IOException {
    final String versionedName = "versioned name";
    givenRequiredDataHasBeenInjected();
    givenResultingVersionedName(versionedName);

    whenCreatingVesrionedName();

    thenCreateVersionedNameAndNotifyListeners(versionedName);
  }

  private void givenRequiredDataHasBeenInjected() {
    setInternalState(defaultVersionedGroupNameFactory, "groupsProcessor", groupsProcessor);
    setInternalState(defaultVersionedGroupNameFactory, "namingStrategy", namingStrategy);
  }

  private void givenResultingVersionedName(final String versionedName) throws IOException {
    when(groupsProcessor.process(any(CacheKey.class))).thenReturn("processed content");
    when(namingStrategy.rename(eq(NAME), any(InputStream.class))).thenReturn(versionedName);
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
