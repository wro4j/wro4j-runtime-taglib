/*
 * Copyright (c) 2014 Aleksey Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.lifus.wro4j_runtime_taglib.servlet;

import static org.mockito.Mockito.verifyZeroInteractions;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.github.lifus.wro4j_runtime_taglib.context.TaglibContext;

/**
 * Tests for {@link TaglibServletContextListener}.
 */
@PrepareForTest(TaglibContext.class)
public class TaglibServletContextListenerTest extends PowerMockTestCase {

  private TaglibServletContextListener servletContextListener;
  
  @Mock
  private ServletContextEvent servletContextEvent;

  @BeforeMethod
  public void setUp() {
    servletContextListener = new TaglibServletContextListener();
  }

  @Test
  public void shouldInitializeTaglibConfiguration() {
    mockStatic(TaglibContext.class);
    final ServletContext servletContext = mockServletContext();

    servletContextListener.contextInitialized(servletContextEvent);

    verifyStatic();
    TaglibContext.initialize(servletContext);
  }

  private ServletContext mockServletContext() {
    final ServletContext servletContext = mock(ServletContext.class);
    when(servletContextEvent.getServletContext()).thenReturn(servletContext);
    return servletContext;
  }

  @Test
  public void shouldntDoAnythingUponContextDestruction() {
    servletContextListener.contextDestroyed(servletContextEvent);

    verifyZeroInteractions(servletContextEvent);
  }

}
