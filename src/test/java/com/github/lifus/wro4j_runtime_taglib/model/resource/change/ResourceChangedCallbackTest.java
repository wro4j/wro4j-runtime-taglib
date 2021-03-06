/*
 * Copyright (c) 2014 Aleksei Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.lifus.wro4j_runtime_taglib.model.resource.change;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

import java.util.ArrayList;
import java.util.Collection;

import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import ro.isdc.wro.manager.WroManager;
import ro.isdc.wro.manager.factory.WroManagerFactory;
import ro.isdc.wro.model.WroModel;
import ro.isdc.wro.model.factory.WroModelFactory;
import ro.isdc.wro.model.group.Group;
import ro.isdc.wro.model.resource.Resource;
import ro.isdc.wro.model.resource.ResourceType;

import com.github.lifus.wro4j_runtime_taglib.cache.TaglibCacheKey;
import com.github.lifus.wro4j_runtime_taglib.model.resource.uri.cache.ResourceUriCache;

/**
 * Test for {@link ResourceChangedCallback}.
 */
@PrepareForTest({Group.class, WroManager.class, WroModel.class})
public class ResourceChangedCallbackTest extends PowerMockTestCase {

  private static final ResourceType TYPE = ResourceType.values()[0];
  private static final String URI = "uri";
  private static final String[] CLEAR_PATH = null;

  private ResourceChangedCallback resourceChangedCallback;

  @Mock
  private WroManagerFactory wroManagerFactory;
  @Mock
  private ResourceUriCache pathCache;
  @Mock
  private WroModel wroModel;

  @BeforeMethod
  public void setUp() {
    resourceChangedCallback = new ResourceChangedCallback(wroManagerFactory, pathCache);
  }

  @DataProvider(name="groups")
  public Object[][] groupsProvider() {
    return new Object[][] {
        { groups(/* no groups */) },
        { groups("single group") },
        { groups("two", "groups") }};
  }

  private Collection<Group> groups(final String... names) {
    final Collection<Group> groups = new ArrayList<>();
    for (final String name : names) {
      groups.add(mockGroup(name));
    }
    return groups;
  }

  private Group mockGroup(final String name) {
    final Group group = mock(Group.class);
    when(group.hasResource(URI)).thenReturn(true);
    when(group.getName()).thenReturn(name);
    return group;
  }

  @Test(dataProvider="groups")
  public void shouldResetCache(final Collection<Group> groups) {
    givenWroManagerIsCorrect();
    givenGroupsAreRegistered(groups);

    whenResourceChanged();

    thenShouldClearExactly(groups.size());
  }

  private void givenWroManagerIsCorrect() {
    final WroManager wroManager = mock(WroManager.class);
    when(wroManagerFactory.create()).thenReturn(wroManager);
    final WroModelFactory wroModelFactory = mock(WroModelFactory.class);
    when(wroManager.getModelFactory()).thenReturn(wroModelFactory);
    when(wroModelFactory.create()).thenReturn(wroModel);
  }

  private void givenGroupsAreRegistered(final Collection<Group> groups) {
    when(wroModel.getGroups()).thenReturn(groups);
  }

  private void whenResourceChanged() {
    resourceChangedCallback.onResourceChanged(mockResource());
  }

  private Resource mockResource() {
    final Resource resource = mock(Resource.class);
    when(resource.getType()).thenReturn(TYPE);
    when(resource.getUri()).thenReturn(URI);
    return resource;
  }

  private void thenShouldClearExactly(final int size) {
    verify(pathCache, times(size)).put(any(TaglibCacheKey.class), eq(CLEAR_PATH));
  }

}
