/*
 * Copyright (c) 2014 Aleksey Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package by.wro.runtime.taglib.model.resource.uri.strategy.provider;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.both;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.instanceOf;
import static org.powermock.api.mockito.PowerMockito.mock;

import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import by.wro.runtime.taglib.model.group.name.VersionedGroupNameFactory;
import by.wro.runtime.taglib.model.resource.uri.root.OptimizedResourcesRootProvider;
import by.wro.runtime.taglib.model.resource.uri.strategy.ResourceUriStrategy;
import by.wro.runtime.taglib.model.resource.uri.strategy.UnoptimizedResourceUriStrategy;
import by.wro.runtime.taglib.model.resource.uri.strategy.VersionedResourceUriStrategy;

/**
 * Test for {@link DefaultResourceUriStrategyProvider}.
 */
public class DefaultResourceUriStrategyProviderTest {

  private DefaultResourceUriStrategyProvider defaultResourceUriStrategyProvider;

  @BeforeMethod
  public void setUp() {
    defaultResourceUriStrategyProvider = new DefaultResourceUriStrategyProvider(mock(OptimizedResourcesRootProvider.class), mock(VersionedGroupNameFactory.class));
  }

  @Test
  public void shouldProvideResourceUriStrategies() {
    assertThat(whenProvideResourceUritrategies(),
        both(
            hasEntry(equalTo(UnoptimizedResourceUriStrategy.ALIAS), instanceOf(UnoptimizedResourceUriStrategy.class)))
        .and(
            hasEntry(equalTo(VersionedResourceUriStrategy.ALIAS), instanceOf(VersionedResourceUriStrategy.class))));
  }

  private Map<String, ResourceUriStrategy> whenProvideResourceUritrategies() {
    return defaultResourceUriStrategyProvider.provideResourceUriStrategies();
  }

}
