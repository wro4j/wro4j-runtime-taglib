/*
 * Copyright (c) 2014 Aleksei Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.lifus.wro4j_runtime_taglib.model.resource.uri.strategy;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.is;
import static org.powermock.api.mockito.PowerMockito.when;

import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.github.lifus.wro4j_runtime_taglib.model.group.name.VersionedGroupNameFactory;

/**
 * Test for {@link VersionedResourceUriStrategy}.
 */
public class VersionedResourceUriStrategyTest extends ResourceUriStrategyTestBase {

  private static final String VERSIONED_NAME = "publicName";
  private static final String EXPECTED_LOCAL_URI = CONTEXT_PATH + ROOT + VERSIONED_NAME + "." + RESOURCE_TYPE.name().toLowerCase();
  private static final String EXPECTED_GLOBAL_URI = RESOURCE_DOMAIN + EXPECTED_LOCAL_URI;

  private VersionedResourceUriStrategy versionedResourceUriStrategy;

  @Mock
  private VersionedGroupNameFactory versionedGroupNameFactory;

  @Override
  protected ResourceUriStrategy getResourceUriStrategy() {
    return versionedResourceUriStrategy;
  }

  @BeforeMethod
  public void setUp() {
    versionedResourceUriStrategy = new VersionedResourceUriStrategy(CONTEXT_PATH, getOptimizedResourcesRootStrategy(), versionedGroupNameFactory, getConfigurationHelper());
  }

  @Test
  public void shouldReturnRelativePathIfDomainHasNotBeenSetUp() {
    givenResourceDomainHasNotBeenSetUp();
    givenContextPathHasBeenSetUp();
    givenWroRootHasBeenSetUp();
    givenVesrionedNameGenerated();

    assertThat(whenGetResourceUris(), is(arrayContaining(EXPECTED_LOCAL_URI)));
  }

  @Test
  public void shouldReturnExternalPathIfDomainHasNotBeenSetUp() {
    givenResourceDomainHasBeenSetUp();
    givenContextPathHasBeenSetUp();
    givenWroRootHasBeenSetUp();
    givenVesrionedNameGenerated();

    assertThat(whenGetResourceUris(), is(arrayContaining(EXPECTED_GLOBAL_URI)));
  }

  private void givenVesrionedNameGenerated() {
    when(versionedGroupNameFactory.create(GROUP, RESOURCE_TYPE)).thenReturn(VERSIONED_NAME);
  }

}
