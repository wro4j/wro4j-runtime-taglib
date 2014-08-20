/*
 * Copyright (c) 2014 Aleksey Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.lifus.wro4j_runtime_taglib.config;

import static org.apache.commons.lang3.Validate.notNull;

import java.util.Properties;

import javax.servlet.ServletContext;

import ro.isdc.wro.config.factory.PropertiesAndFilterConfigWroConfigurationFactory;
import ro.isdc.wro.config.factory.ServletContextPropertyWroConfigurationFactory;

import com.github.lifus.wro4j_runtime_taglib.model.resource.uri.strategy.ConfigurableResourceUriStrategy;

/**
 * Allows to access WRO properties.
 * <p>
 *   It's required for custom Strategies, e.g {@link ConfigurableResourceUriStrategy}.
 * </p>
 *
 * @see {@link ServletContextPropertyWroConfigurationFactory} for properties source.
 */
public class ConfigurationHelper {

  private final Properties wroProperties;

  /**
   * Creates instance of ConfigurationHelper. Loads WRO properties.
   *
   * @param servletContext
   *          current servlet context.
   */
  public ConfigurationHelper(final ServletContext servletContext) {
    notNull(servletContext);
    wroProperties = createProperties(servletContext);
  }

  /**
   * Creates {@link Properties} using {@link ServletContextPropertyWroConfigurationFactory}.
   * You may override it and use e.g. {@link PropertiesAndFilterConfigWroConfigurationFactory}.
   *
   * @param servletContext
   *          current servlet context.
   * @return WRO properties.
   */
  protected Properties createProperties(final ServletContext servletContext) {
    return new ServletContextPropertyWroConfigurationFactory(servletContext).createProperties();
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

}
