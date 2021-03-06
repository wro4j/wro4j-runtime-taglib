/*
 * Copyright (c) 2014 Aleksei Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.lifus.wro4j_runtime_taglib.config;

import static org.apache.commons.lang3.Validate.notNull;

import java.util.Properties;

import javax.servlet.ServletContext;

import ro.isdc.wro.config.factory.ServletContextPropertyWroConfigurationFactory;
import ro.isdc.wro.config.jmx.WroConfiguration;

import com.github.lifus.wro4j_runtime_taglib.model.resource.uri.strategy.ConfigurableResourceUriStrategy;

/**
 * Allows to access WRO properties.
 * <p>
 *   It's required for custom Strategies, e.g {@link ConfigurableResourceUriStrategy}.
 * </p>
 *
 * @see {@link ServletContextPropertyWroConfigurationFactory} for properties source.
 */
public final class ConfigurationHelper {

  private final boolean ignoreEmptyGroup;
  private final Properties wroProperties;

  /**
   * Creates instance of ConfigurationHelper. Loads WRO properties.
   *
   * @param servletContext
   *          current servlet context.
   */
  public ConfigurationHelper(final ServletContext servletContext) {
    notNull(servletContext);
    final ServletContextPropertyWroConfigurationFactory wroConfigurationFactory = new ServletContextPropertyWroConfigurationFactory(servletContext);
    final WroConfiguration wroConfiguration = wroConfigurationFactory.create();
    ignoreEmptyGroup = wroConfiguration.isIgnoreEmptyGroup();
    wroProperties = wroConfigurationFactory.createProperties();
  }

  /**
   * Checks if WRO configuration contains a key.
   *
   * @param key
   *          possible key.
   * @return true if WRO configuration contains key.
   * @see Properties#containsKey(Object)
   */
  public boolean containsKey(final String key) {
    return wroProperties.containsKey(key);
  }

  /**
   * Returns value from WRO configuration.
   *
   * @param key
   *          the property key.
   * @return value from WRO configuration.
   * @see Properties#getProperty(String)
   */
  public String getProperty(final String key) {
    return wroProperties.getProperty(key);
  }

  /**
   * Returns value from WRO configuration or fallbacks to default one.
   *
   * @param key
   *          the property key.
   * @param defaultValue
   *          a default value.
   * @return value from WRO configuration or default one.
   */
  public String getProperty(final String key, final String defaultValue) {
    return wroProperties.getProperty(key, defaultValue);
  }

  /**
   * Instantiates {@link Properties} objects for a given key.
   * Enforces immutability of underlying WRO properties.
   *
   * @param key
   *          property key.
   * @return either properties with single entry or empty properties.
   */
  public Properties getPropertiesForKey(final String key) {
    final Properties propertiesForKey = new Properties();
    if (wroProperties.containsKey(key)) {
      propertiesForKey.put(key, wroProperties.getProperty(key));
    }
    return propertiesForKey;
  }

  /**
   * @return true if empty groups are allowed.
   */
  public boolean isIgnoreEmptyGroup() {
    return ignoreEmptyGroup;
  }

}
