/*
 * Copyright (c) 2014 Aleksey Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.lifus.wro4j_runtime_taglib.config;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.mockito.internal.util.reflection.Whitebox.setInternalState;

import java.util.Properties;

import javax.servlet.ServletContext;

import org.mockito.Mock;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Tests for {@link ConfigurationHelper}.
 */
public class ConfigurationHelperTest extends PowerMockTestCase {

  private static final String KEY = "key";
  private static final String VALUE = "value";

  private ConfigurationHelper configurationHelper;

  @Mock
  private ServletContext servletContext;
  @Mock
  private Properties wroProperties;
  private Properties propertiesForKey;

  @BeforeMethod
  public void setUp() {
    configurationHelper = new ConfigurationHelper(servletContext);
  }

  @Test
  public void shouldGetPropertiesForKey() {
    givenPropertiesHasBeenSetUp();
    givenPropertiesContain(KEY, VALUE);
    givenPropertiesContain("anotherKey", "anotherValue");

    whenGetPropertiesFor(KEY);

    thenPropertiesForKeyShouldContainOnly(KEY, VALUE);
  }

  @Test
  public void shouldReturnEmptyPropertiesIfKeyIsMissing() {
    givenPropertiesHasBeenSetUp();

    whenGetPropertiesFor(KEY);

    thenPropertiesForKeyShouldBeEmpty();
  }

  private void givenPropertiesHasBeenSetUp() {
    setInternalState(configurationHelper, "wroProperties", wroProperties);
  }

  private void givenPropertiesContain(final String key, final String value) {
    when(wroProperties.containsKey(key)).thenReturn(true);
    when(wroProperties.getProperty(key)).thenReturn(value);
  }

  private void whenGetPropertiesFor(final String key) {
    propertiesForKey = configurationHelper.getPropertiesForKey(key);
  }

  private void thenPropertiesForKeyShouldContainOnly(final Object key, final Object value) {
    assertThat(propertiesForKey.size(), is(1)); // IsMapWithSize#aMapWithSize isn't in current release
    assertThat(propertiesForKey, hasEntry(key, value));
  }

  private void thenPropertiesForKeyShouldBeEmpty() {
    assertThat(propertiesForKey.size(), is(0));
  }
}
