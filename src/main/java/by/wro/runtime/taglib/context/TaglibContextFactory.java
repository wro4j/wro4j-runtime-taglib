/*
 * Copyright (c) 2014 Aleksey Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package by.wro.runtime.taglib.context;

import javax.servlet.ServletContext;

import ro.isdc.wro.http.support.ServletContextAttributeHelper;
import ro.isdc.wro.manager.factory.WroManagerFactory;
import by.wro.runtime.taglib.cache.CacheStrategyFactory;
import by.wro.runtime.taglib.cache.ConfigurableCacheStrategyFactory;
import by.wro.runtime.taglib.config.ConfigurationHelper;
import by.wro.runtime.taglib.manager.WroTaglibManagerFactoryDecorator;
import by.wro.runtime.taglib.manager.InjectorInitializer;
import by.wro.runtime.taglib.model.group.name.cache.GroupNameCacheInitializer;
import by.wro.runtime.taglib.model.resource.uri.cache.ResourceUriCacheInitializer;

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
    WroManagerFactory managerFactory = attributeHelper.getManagerFactory();
    final InjectorInitializer injectorInitializer = new InjectorInitializer(managerFactory);
    final ConfigurationHelper configuration = new ConfigurationHelper(servletContext);
    final CacheStrategyFactory cacheStrategyFactory = new ConfigurableCacheStrategyFactory(configuration);

    final GroupNameCacheInitializer groupNameCacheInitializer = new GroupNameCacheInitializer(cacheStrategyFactory);
    final ResourceUriCacheInitializer resourceUriCacheInitializer = new ResourceUriCacheInitializer(groupNameCacheInitializer, configuration, cacheStrategyFactory, injectorInitializer);

    // TODO: it's a side-effect. consider to move it elsewhere.
    managerFactory = new WroTaglibManagerFactoryDecorator(managerFactory, groupNameCacheInitializer, resourceUriCacheInitializer, injectorInitializer);
    attributeHelper.setManagerFactory(managerFactory);

    return new TaglibContext(resourceUriCacheInitializer);
  }

}
