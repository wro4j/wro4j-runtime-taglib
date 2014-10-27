/*
 * Copyright (c) 2014 Aleksei Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.lifus.wro4j_runtime_taglib.manager;

import ro.isdc.wro.manager.WroManager;
import ro.isdc.wro.manager.WroManager.Builder;
import ro.isdc.wro.manager.callback.LifecycleCallback;
import ro.isdc.wro.manager.factory.WroManagerFactory;
import ro.isdc.wro.manager.factory.WroManagerFactoryDecorator;
import ro.isdc.wro.model.group.GroupExtractor;
import ro.isdc.wro.util.ObjectFactory;

import com.github.lifus.wro4j_runtime_taglib.model.group.name.GroupNameExtractorDecorator;
import com.github.lifus.wro4j_runtime_taglib.model.group.name.cache.GroupNameCacheInitializer;
import com.github.lifus.wro4j_runtime_taglib.model.resource.change.ResourceChangedCallbackInitializer;
import com.github.lifus.wro4j_runtime_taglib.model.resource.uri.cache.ResourceUriCacheInitializer;

/**
 * Adds listeners and decorators to {@link WroManagerFactory}.
 */
public final class WroTaglibManagerFactoryDecorator extends WroManagerFactoryDecorator {

  private final GroupNameCacheInitializer groupNameCacheInitializer;
  private final ResourceChangedCallbackInitializer resourceChangedCallbackInitializer;

  public WroTaglibManagerFactoryDecorator(
    final WroManagerFactory wroManagerFactory,
    final GroupNameCacheInitializer nameCacheInitializer,
    final ResourceUriCacheInitializer resourceUriCacheInitializer
  ) {
    super(wroManagerFactory);
    this.groupNameCacheInitializer = nameCacheInitializer;
    this.resourceChangedCallbackInitializer = new ResourceChangedCallbackInitializer(wroManagerFactory, resourceUriCacheInitializer);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void onBeforeBuild(final Builder builder) {
    super.onBeforeBuild(builder);

    final WroManager wroManager = getDecoratedObject().create();
    decorateGroupExtractor(builder, wroManager);
    addResourceChangedCallbackListener(wroManager);
  }

  private void decorateGroupExtractor(final Builder builder, final WroManager wroManager) {
    GroupExtractor groupExtractor = wroManager.getGroupExtractor();
    groupExtractor = new GroupNameExtractorDecorator(groupExtractor, groupNameCacheInitializer);
    builder.setGroupExtractor(groupExtractor);
  }

  private void addResourceChangedCallbackListener(final WroManager wroManager) {
    wroManager.registerCallback(new ObjectFactory<LifecycleCallback>() {

      @Override
      public LifecycleCallback create() {
        return resourceChangedCallbackInitializer.get();
      }
    });
  }

}
