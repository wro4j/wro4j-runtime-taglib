package com.github.lifus.wro4j_runtime_taglib.model.resource.uri.root.strategy;

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

import javax.servlet.ServletContext;

import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import ro.isdc.wro.util.LazyInitializer;

import com.github.lifus.wro4j_runtime_taglib.config.ConfigurationHelper;
import com.github.lifus.wro4j_runtime_taglib.model.resource.uri.root.strategy.provider.OptimizedResourcesRootStrategyProvider;

@PrepareForTest(ConfigurationHelper.class)
public class ConfigurableOptimizedResourcesRootStrategyTest  extends PowerMockTestCase {
  private static final String ROOT = "root";

  private ConfigurableOptimizedResourcesRootStrategy configurableOptimizedResourcesRootStrategy;

  @Mock
  private ServletContext servletContext;
  @Mock
  private ConfigurationHelper configurationHelper;

  @BeforeMethod
  public void setUp() {
    configurableOptimizedResourcesRootStrategy = new ConfigurableOptimizedResourcesRootStrategy(servletContext, configurationHelper);
  }

  @Test
  public void shouldReturnStrategyKey() {
    assertThat(configurableOptimizedResourcesRootStrategy.getStrategyKey(), is(ConfigurableOptimizedResourcesRootStrategy.KEY));
  }

  @Test
  public void shouldReturnDefaultStrategy() {
    assertThat(configurableOptimizedResourcesRootStrategy.getDefaultStrategy(), is(instanceOf(OptimizedResourcesRootStrategy.class)));
  }

  @Test
  public void shouldRetrieveStrategiesFromProvider() {
    final OptimizedResourcesRootStrategyProvider provider = mock(OptimizedResourcesRootStrategyProvider.class);
    final Map<String, OptimizedResourcesRootStrategy> strategies = new HashMap<>();
    when(provider.provideOptimizedResourcesRootStrategies()).thenReturn(strategies);

    assertThat(configurableOptimizedResourcesRootStrategy.getStrategies(provider), is(strategies));

    verify(provider).provideOptimizedResourcesRootStrategies();
  }

  @Test
  public void shouldReturnClassOfProvider() {
    assertThat(configurableOptimizedResourcesRootStrategy.getProviderClass(), is(equalTo(OptimizedResourcesRootStrategyProvider.class)));
  }

  @Test
  public void shouldCreateProperties() {
    final Properties properties = mock(Properties.class);
    when(configurationHelper.getPropertiesForKey(ConfigurableOptimizedResourcesRootStrategy.KEY)).thenReturn(properties);

    assertThat(configurableOptimizedResourcesRootStrategy.newProperties(), is(properties));
  }

  @Test
  public void shouldOverrideDefaultStrategyMap() {
    final Map<String, OptimizedResourcesRootStrategy> strategies = new HashMap<>();

    configurableOptimizedResourcesRootStrategy.overrideDefaultStrategyMap(strategies);

    assertThat(strategies.size(), is(greaterThan(0)));
  }

  @Test
  public void shouldReturnUris() {
    givenConfiguredRootIs(ROOT);

    assertThat(configurableOptimizedResourcesRootStrategy.getRoot(), is(ROOT));
  }

  private void givenConfiguredRootIs(final String root) {
    @SuppressWarnings("unchecked")
    final LazyInitializer<OptimizedResourcesRootStrategy> lazyInitializer = mock(LazyInitializer.class);
    setInternalState(configurableOptimizedResourcesRootStrategy, "lazyInitializer", lazyInitializer);
    final OptimizedResourcesRootStrategy rootStrategy = mock(OptimizedResourcesRootStrategy.class);
    when(lazyInitializer.get()).thenReturn(rootStrategy);
    when(rootStrategy.getRoot()).thenReturn(root);
  }
}
