/*
 * Copyright (c) 2014 Aleksei Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.lifus.wro4j_runtime_taglib.model.resource.uri.strategy;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.emptyArray;
import static org.hamcrest.Matchers.is;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import ro.isdc.wro.WroRuntimeException;
import ro.isdc.wro.config.jmx.WroConfiguration;
import ro.isdc.wro.manager.WroManager;
import ro.isdc.wro.manager.factory.WroManagerFactory;
import ro.isdc.wro.model.WroModel;
import ro.isdc.wro.model.factory.WroModelFactory;
import ro.isdc.wro.model.group.Group;
import ro.isdc.wro.model.resource.Resource;

import com.github.lifus.wro4j_runtime_taglib.config.ConfigurationHelper;

/**
 * Tests for {@link UnoptimizedResourceUriStrategy}.
 */
@PrepareForTest({WroManager.class, WroModel.class, Group.class, WroConfiguration.class, ConfigurationHelper.class})
public class UnoptimizedResourceUriStrategyTest extends ResourceUriStrategyTestBase {

  private static final String URI = "uri";
  private static final String INVALID_URI = "not a uri";
  private static final String ABSOLUTE_URI = "http://example.com";
  private static final String PROTECTED_URI = "/WEB-INF/" + URI;
  private static final String EXPECTED_PUBLIC_URI = CONTEXT_PATH + URI;
  private static final String EXPECTED_PROTECTED_URI = CONTEXT_PATH + ROOT + "?wroAPI=wroResources&id=" + PROTECTED_URI;

  private UnoptimizedResourceUriStrategy unoptimizedResourceUriStrategy;

  @Mock
  private WroManagerFactory wroManagerFactory;
  @Mock
  private WroModel wroModel;
  @Mock
  private ConfigurationHelper configurationHelper;

  @Override
  protected ResourceUriStrategy getResourceUriStrategy() {
    return unoptimizedResourceUriStrategy;
  }

  @BeforeMethod
  public void setUp() {
    unoptimizedResourceUriStrategy = new UnoptimizedResourceUriStrategy(CONTEXT_PATH, wroManagerFactory, configurationHelper, getOptimizedResourcesRootStrategy());
  }

  @Test(expectedExceptions=WroRuntimeException.class)
  public void shouldThrowExceptionIfThereIsNoSuchGroup() {
    givenWroManagerFactoryIsCorrect();

    whenGetResourceUris();
  }

  @Test(expectedExceptions=WroRuntimeException.class)
  public void shouldThrowExceptionIfUriIsInvalid() {
    givenWroManagerFactoryIsCorrect();
    givenModelContains(group(withResource(INVALID_URI)));

    whenGetResourceUris();
  }

  @Test(expectedExceptions=WroRuntimeException.class)
  public void shouldThrowExceptionIfThereIsNoSuchResourceAndIgnoreEmptyGroupIsFalse() {
    givenWroManagerFactoryIsCorrect();
    givenModelContains(group(withoutResources()));
    givenIgnoreEmptyGroupIs(false);

    whenGetResourceUris();
  }

  @Test
  public void shouldReturnEmptyArrayIfThereIsNoSuchResource() {
    givenWroManagerFactoryIsCorrect();
    givenModelContains(group(withoutResources()));
    givenIgnoreEmptyGroupIs(true);

    assertThat(whenGetResourceUris(), is(emptyArray()));
  }

  @Test
  public void shouldReturnLocalUriIfThereIsSuchResource() {
    givenWroManagerFactoryIsCorrect();
    givenModelContains(group(withResource(URI)));
    givenContextPathHasBeenSetUp();

    assertThat(whenGetResourceUris(), is(arrayContaining(EXPECTED_PUBLIC_URI)));
  }

  @Test
  public void shouldReturnProtectedUriIfThereIsSuchResource() {
    givenWroManagerFactoryIsCorrect();
    givenModelContains(group(withResource(PROTECTED_URI)));
    givenContextPathHasBeenSetUp();
    givenWroRootHasBeenSetUp();

    assertThat(whenGetResourceUris(), arrayContaining(EXPECTED_PROTECTED_URI));
  }

  @Test
  public void shouldReturnAbsoluteUriIfThereIsSuchResource() {
    givenWroManagerFactoryIsCorrect();
    givenModelContains(group(withResource(ABSOLUTE_URI)));
    givenContextPathHasBeenSetUp();

    assertThat(whenGetResourceUris(), is(arrayContaining(ABSOLUTE_URI)));
  }

  private void givenWroManagerFactoryIsCorrect() {
    final WroManager wroManager = mock(WroManager.class);
    when(wroManagerFactory.create()).thenReturn(wroManager);
    final WroModelFactory wroModelFactory = mock(WroModelFactory.class);
    when(wroManager.getModelFactory()).thenReturn(wroModelFactory);
    when(wroModelFactory.create()).thenReturn(wroModel);
  }

  private void givenModelContains(final Group group) {
    when(wroModel.getGroups()).thenReturn(Arrays.asList(group));
  }

  private void givenIgnoreEmptyGroupIs(final boolean value) {
    when(configurationHelper.isIgnoreEmptyGroup()).thenReturn(value);
  }

  private Group group(final List<Resource> resources) {
    final Group filteredGroup = mock(Group.class);
    when(filteredGroup.getResources()).thenReturn(resources);

    final Group group = mock(Group.class);
    when(group.getName()).thenReturn(GROUP);
    when(group.collectResourcesOfType(RESOURCE_TYPE)).thenReturn(filteredGroup);
    return group;
  }

  private List<Resource> withoutResources() {
    return Collections.emptyList();
  }

  private List<Resource> withResource(final String uri) {
    final List<Resource> resources = new ArrayList<>();
    final Resource resource = mock(Resource.class);
    when(resource.getUri()).thenReturn(uri);
    resources.add(resource);
    return resources;
  }

}
