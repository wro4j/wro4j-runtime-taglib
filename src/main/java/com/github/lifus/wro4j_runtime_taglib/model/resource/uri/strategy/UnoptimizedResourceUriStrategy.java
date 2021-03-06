/*
 * Copyright (c) 2014 Aleksei Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.lifus.wro4j_runtime_taglib.model.resource.uri.strategy;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ro.isdc.wro.WroRuntimeException;
import ro.isdc.wro.http.handler.ResourceProxyRequestHandler;
import ro.isdc.wro.manager.factory.WroManagerFactory;
import ro.isdc.wro.model.WroModel;
import ro.isdc.wro.model.WroModelInspector;
import ro.isdc.wro.model.group.Group;
import ro.isdc.wro.model.resource.Resource;
import ro.isdc.wro.model.resource.ResourceType;
import ro.isdc.wro.model.resource.locator.ServletContextUriLocator;
import ro.isdc.wro.util.LazyInitializer;

import com.github.lifus.wro4j_runtime_taglib.config.ConfigurationHelper;
import com.github.lifus.wro4j_runtime_taglib.model.resource.uri.root.strategy.OptimizedResourcesRootStrategy;

/**
 * Returns URIs of unprocessed resources associated with given group name and resource type.
 */
public final class UnoptimizedResourceUriStrategy extends AbstractResourceUriStrategy {

  public static final String ALIAS = "unoptimized";

  private static final Logger LOGGER = LoggerFactory.getLogger(UnoptimizedResourceUriStrategy.class);

  private final WroManagerFactory wroManagerFactory;

  private final LazyInitializer<String> proxyPrefixInitializer = new LazyInitializer<String>() {
    @Override
    protected String initialize() {
      return ResourceProxyRequestHandler.createProxyPath(getWroRoot(), "");
    }
  };

  public UnoptimizedResourceUriStrategy(
    final String contextPath,
    final WroManagerFactory wroManagerFactory,
    final ConfigurationHelper configurationHelper,
    final OptimizedResourcesRootStrategy optimizedResourcesRootStrategy
  ) {
    super(contextPath, optimizedResourcesRootStrategy, configurationHelper);
    this.wroManagerFactory = wroManagerFactory;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String[] getResourcesUris(final String groupName, final ResourceType type) {
    final List<Resource> resources = getResources(groupName, type);
    if (resources.isEmpty()) {
      return handleEmptyResources(groupName, type);
    } else {
      return getUris(resources);
    }
  }

  private List<Resource> getResources(final String groupName, final ResourceType type) {
    final Group group = new WroModelInspector(getWroModel()).getGroupByName(groupName);
    if (group == null) {
      throw new WroRuntimeException("No such group available in the model: " + groupName);
    }
    final Group filteredGroup = group.collectResourcesOfType(type);
    return filteredGroup.getResources();
  }

  private WroModel getWroModel() {
    return wroManagerFactory.create().getModelFactory().create();
  }

  private String[] handleEmptyResources(final String groupName, final ResourceType resourceType) {
    LOGGER.debug("No resources found in group: {} and resource type: {}", groupName, resourceType);

    if (configuration.isIgnoreEmptyGroup()) {
      return ArrayUtils.EMPTY_STRING_ARRAY;
    } else {
      throw new WroRuntimeException("No resources found in group: " + groupName);
    }
  }

  private String[] getUris(final List<Resource> resources) {
    final List<String> uris = new ArrayList<>();
    for (final Resource resource : resources) {
      final String resourceUri = resource.getUri();
      uris.add(getAccessibleUri(resourceUri));
    }
    return uris.toArray(new String[uris.size()]);
  }

  private String getAccessibleUri(final String resourceUri) {
    if (ServletContextUriLocator.isProtectedResource(resourceUri)) {
      return proxyPrefixInitializer.get() + resourceUri;
    }

    if (isAbsolute(resourceUri)) {
      return resourceUri;
    } else {
      return getContextPath() + resourceUri;
    }
  }

  private boolean isAbsolute(final String resourceUri) {
    try {
      return new URI(resourceUri).isAbsolute();
    } catch (URISyntaxException e) {
      throw new WroRuntimeException("Invalid URI: " + resourceUri, e);
    }
  }

}
