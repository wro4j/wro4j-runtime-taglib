/*
 * Copyright (c) 2014 Aleksey Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package by.wro.runtime.taglib.model.resource.uri.strategy;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.util.reflection.Whitebox.setInternalState;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.mockito.Mock;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import ro.isdc.wro.model.resource.ResourceType;
import ro.isdc.wro.util.LazyInitializer;
import by.wro.runtime.taglib.config.ConfigurationHelper;
import by.wro.runtime.taglib.model.group.name.VersionedGroupNameFactory;
import by.wro.runtime.taglib.model.resource.uri.root.OptimizedResourcesRootProvider;
import by.wro.runtime.taglib.model.resource.uri.strategy.provider.ResourceUriStrategyProvider;

/**
 * Tests for {@link ConfigurableResourceUriStrategy}.
 */
public class ConfigurableResourceUriStrategyTest extends PowerMockTestCase {

  private static final String NAME = "name";
  private static final ResourceType TYPE = ResourceType.values()[0];

  private ConfigurableResourceUriStrategy configurableResourceUriStrategy;

  @Mock
  private ConfigurationHelper configurationHelper;

  @BeforeMethod
  public void setUp() {
    configurableResourceUriStrategy = new ConfigurableResourceUriStrategy(mock(OptimizedResourcesRootProvider.class), mock(VersionedGroupNameFactory.class), configurationHelper);
  }

  @Test
  public void shouldCreateProperties() {
    final Properties properties = mock(Properties.class);
    when(configurationHelper.getPropertiesForKey(ConfigurableResourceUriStrategy.KEY)).thenReturn(properties);

    assertThat(configurableResourceUriStrategy.newProperties(), is(properties));
  }

  @Test
  public void shouldReturnDefaultStrategy() {
    assertThat(configurableResourceUriStrategy.getDefaultStrategy(), is(instanceOf(ResourceUriStrategy.class)));
  }

  @Test
  public void shouldRetrieveStrategiesFromProvider() {
    final ResourceUriStrategyProvider provider = mock(ResourceUriStrategyProvider.class);
    final Map<String, ResourceUriStrategy> strategies = new HashMap<>();
    when(provider.provideResourceUriStrategies()).thenReturn(strategies);

    assertThat(configurableResourceUriStrategy.getStrategies(provider), is(strategies));

    verify(provider).provideResourceUriStrategies();
  }

  @Test
  public void shouldReturnStrategyKey() {
    assertThat(configurableResourceUriStrategy.getStrategyKey(), is(ConfigurableResourceUriStrategy.KEY));
  }

  @Test
  public void shouldReturnClassOfProvider() {
    assertThat(configurableResourceUriStrategy.getProviderClass(), is(equalTo(ResourceUriStrategyProvider.class)));
  }

  @Test
  public void shouldOverrideDefaultStrategyMap() {
    final Map<String, ResourceUriStrategy> strategies = new HashMap<>();

    configurableResourceUriStrategy.overrideDefaultStrategyMap(strategies);

    assertThat(strategies.size(), is(greaterThan(0)));
  }

  @Test
  public void shouldReturnUris() {
    final String[] uris = new String[] {"uri1", "uri2"};
    givenUrisForKeyAre(uris);

    assertThat(configurableResourceUriStrategy.getResourcesUris(NAME, TYPE), is(uris));
  }

  private void givenUrisForKeyAre(final String[] paths) {
    @SuppressWarnings("unchecked")
    final
    LazyInitializer<ResourceUriStrategy> lazyInitializer = mock(LazyInitializer.class);
    setInternalState(configurableResourceUriStrategy, "lazyInitializer", lazyInitializer);
    final ResourceUriStrategy pathStrategy = mock(ResourceUriStrategy.class);
    when(lazyInitializer.get()).thenReturn(pathStrategy);
    when(pathStrategy.getResourcesUris(NAME, TYPE)).thenReturn(paths);
  }

}
