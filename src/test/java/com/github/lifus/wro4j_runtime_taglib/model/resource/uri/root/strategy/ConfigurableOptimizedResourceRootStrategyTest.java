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
public class ConfigurableOptimizedResourceRootStrategyTest  extends PowerMockTestCase {
  private static final String ROOT = "root";

  private ConfigurableOptimizedResourceRootStrategy configurableOptimizedResourceRootStrategy;

  @Mock
  private ServletContext servletContext;
  @Mock
  private ConfigurationHelper configurationHelper;

  @BeforeMethod
  public void setUp() {
    configurableOptimizedResourceRootStrategy = new ConfigurableOptimizedResourceRootStrategy(servletContext, configurationHelper);
  }

  @Test
  public void shouldReturnStrategyKey() {
    assertThat(configurableOptimizedResourceRootStrategy.getStrategyKey(), is(ConfigurableOptimizedResourceRootStrategy.KEY));
  }

  @Test
  public void shouldReturnDefaultStrategy() {
    assertThat(configurableOptimizedResourceRootStrategy.getDefaultStrategy(), is(instanceOf(OptimizedResourcesRootStrategy.class)));
  }

  @Test
  public void shouldRetrieveStrategiesFromProvider() {
    final OptimizedResourcesRootStrategyProvider provider = mock(OptimizedResourcesRootStrategyProvider.class);
    final Map<String, OptimizedResourcesRootStrategy> strategies = new HashMap<>();
    when(provider.provideOptimizedResourcesRootStrategies()).thenReturn(strategies);

    assertThat(configurableOptimizedResourceRootStrategy.getStrategies(provider), is(strategies));

    verify(provider).provideOptimizedResourcesRootStrategies();
  }

  @Test
  public void shouldReturnClassOfProvider() {
    assertThat(configurableOptimizedResourceRootStrategy.getProviderClass(), is(equalTo(OptimizedResourcesRootStrategyProvider.class)));
  }

  @Test
  public void shouldCreateProperties() {
    final Properties properties = mock(Properties.class);
    when(configurationHelper.getPropertiesForKey(ConfigurableOptimizedResourceRootStrategy.KEY)).thenReturn(properties);

    assertThat(configurableOptimizedResourceRootStrategy.newProperties(), is(properties));
  }

  @Test
  public void shouldOverrideDefaultStrategyMap() {
    final Map<String, OptimizedResourcesRootStrategy> strategies = new HashMap<>();

    configurableOptimizedResourceRootStrategy.overrideDefaultStrategyMap(strategies);

    assertThat(strategies.size(), is(greaterThan(0)));
  }

  @Test
  public void shouldReturnUris() {
    givenConfiguredRootIs(ROOT);

    assertThat(configurableOptimizedResourceRootStrategy.getRoot(), is(ROOT));
  }

  private void givenConfiguredRootIs(final String root) {
    @SuppressWarnings("unchecked")
    final LazyInitializer<OptimizedResourcesRootStrategy> lazyInitializer = mock(LazyInitializer.class);
    setInternalState(configurableOptimizedResourceRootStrategy, "lazyInitializer", lazyInitializer);
    final OptimizedResourcesRootStrategy rootStrategy = mock(OptimizedResourcesRootStrategy.class);
    when(lazyInitializer.get()).thenReturn(rootStrategy);
    when(rootStrategy.getRoot()).thenReturn(root);
  }
}
