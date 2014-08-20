/*
 * Copyright (c) 2014 Aleksey Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.lifus.wro4j_runtime_taglib.model.group.name.callback;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.util.reflection.Whitebox.getInternalState;

import java.util.List;

import org.mockito.Mock;
import org.mockito.verification.VerificationMode;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import ro.isdc.wro.model.resource.ResourceType;

/**
 * Tests for {@link VersionedGroupNameCallbackRegistry}.
 */
public class VersionedGroupNameCallbackRegistryTest extends PowerMockTestCase {

  private VersionedGroupNameCallbackRegistry registry;

  @Mock
  private VersionedGroupNameListener listener1;
  @Mock
  private VersionedGroupNameListener listener2;

  @Mock
  private String name;
  @Mock
  private ResourceType type;

  @BeforeMethod
  public void setUp() {
    registry = new VersionedGroupNameCallbackRegistry();
  }

  @Test
  public void shouldNotifyAllListeners() {
    givenTwoListenersAreRegistered();

    whenPublicNameCreated();

    thenBothListenersNotified(only()); // once
  }

  @Test
  public void shouldRemoveAllListeners() {
    givenTwoListenersAreRegistered();

    registry.destroy();

    thenRegistryShouldBeEmpty();
  }

  private void givenTwoListenersAreRegistered() {
    registry.registerPublicNameListener(listener1);
    registry.registerPublicNameListener(listener2);
  }

  private void whenPublicNameCreated() {
    registry.onVersionedNameCreated(name, name, type);
  }

  private void thenBothListenersNotified(final VerificationMode mode) {
    verify(listener1, mode).onVersionedNameCreated(name, name, type);
    verify(listener2, mode).onVersionedNameCreated(name, name, type);
  }

  private void thenRegistryShouldBeEmpty() {
    assertThat(versionedGroupNameListeners(), empty());
  }

  @SuppressWarnings("unchecked")
  private List<VersionedGroupNameListener> versionedGroupNameListeners() {
    return (List<VersionedGroupNameListener>) getInternalState(registry, "versionedGroupNameListeners");
  }

}
