/*
 * Copyright (c) 2015 Aleksei Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.lifus.wro4j_runtime_taglib.model.resource.uri.root.strategy.provider;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.both;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.instanceOf;
import static org.powermock.api.mockito.PowerMockito.mock;

import javax.servlet.ServletContext;

import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.github.lifus.wro4j_runtime_taglib.config.ConfigurationHelper;
import com.github.lifus.wro4j_runtime_taglib.model.resource.uri.root.strategy.InferredOptimizedResourcesRootStrategy;
import com.github.lifus.wro4j_runtime_taglib.model.resource.uri.root.strategy.PredefinedOptimizedResourcesRootStrategy;

/**
 * Test for {@link DefaultOptimizedResourcesRootStrategyProvider}.
 */
@PrepareForTest(ConfigurationHelper.class)
public class DefaultOptimizedResourcesRootStrategyProviderTest extends PowerMockTestCase {

  private DefaultOptimizedResourcesRootStrategyProvider provider;

  @BeforeMethod
  public void setUp() {
    provider = new DefaultOptimizedResourcesRootStrategyProvider(mock(ServletContext.class), mock(ConfigurationHelper.class));
  }

  @Test
  public void shouldProvideOptimizedResourcesRootStrategies() {
    assertThat(provider.provideOptimizedResourcesRootStrategies(),
        both(
            hasEntry(equalTo(InferredOptimizedResourcesRootStrategy.ALIAS), instanceOf(InferredOptimizedResourcesRootStrategy.class)))
        .and(
            hasEntry(equalTo(PredefinedOptimizedResourcesRootStrategy.ALIAS), instanceOf(PredefinedOptimizedResourcesRootStrategy.class))));
  }

}
