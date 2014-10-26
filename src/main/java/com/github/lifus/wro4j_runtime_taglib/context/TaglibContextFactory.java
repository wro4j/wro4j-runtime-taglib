/*
 * Copyright (c) 2014 Aleksei Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.lifus.wro4j_runtime_taglib.context;

import javax.servlet.ServletContext;

import ro.isdc.wro.http.support.ServletContextAttributeHelper;
import ro.isdc.wro.manager.factory.WroManagerFactory;

import com.github.lifus.wro4j_runtime_taglib.cache.CacheStrategyFactory;
import com.github.lifus.wro4j_runtime_taglib.cache.ConfigurableCacheStrategyFactory;
import com.github.lifus.wro4j_runtime_taglib.config.ConfigurationHelper;
import com.github.lifus.wro4j_runtime_taglib.manager.InjectorInitializer;
import com.github.lifus.wro4j_runtime_taglib.manager.WroTaglibManagerFactoryDecorator;
import com.github.lifus.wro4j_runtime_taglib.model.group.name.cache.GroupNameCacheInitializer;
import com.github.lifus.wro4j_runtime_taglib.model.resource.uri.cache.ResourceUriCacheInitializer;

/**
 * Instantiates {@link TaglibContext} objects.
 */
public final class TaglibContextFactory {

  private final ServletContext servletContext;

  TaglibContextFactory(final ServletContext servletContext) {
    this.servletContext = servletContext;
  }

  /**
   * Instantiates {@link TaglibContext} and decorates {@link WroManagerFactory}.
   * 
   * @return new instance of {@link TaglibContext}.
   */
  TaglibContext create() {
    final ServletContextAttributeHelper attributeHelper = new ServletContextAttributeHelper(servletContext);
    WroManagerFactory wroManagerFactory = attributeHelper.getManagerFactory();
    final InjectorInitializer injectorInitializer = new InjectorInitializer(wroManagerFactory);
    final ConfigurationHelper configurationHelper = new ConfigurationHelper(servletContext);
    final CacheStrategyFactory cacheStrategyFactory = new ConfigurableCacheStrategyFactory(configurationHelper);

    final GroupNameCacheInitializer groupNameCacheInitializer = new GroupNameCacheInitializer(cacheStrategyFactory);
    final ResourceUriCacheInitializer resourceUriCacheInitializer = new ResourceUriCacheInitializer(
      servletContext,
      groupNameCacheInitializer,
      configurationHelper,
      cacheStrategyFactory,
      injectorInitializer
    );

    // TODO: it's a side-effect. consider to move it elsewhere.
    wroManagerFactory = new WroTaglibManagerFactoryDecorator(
      wroManagerFactory,
      groupNameCacheInitializer,
      resourceUriCacheInitializer,
      injectorInitializer
    );
    attributeHelper.setManagerFactory(wroManagerFactory);

    return new TaglibContext(resourceUriCacheInitializer);
  }

}
