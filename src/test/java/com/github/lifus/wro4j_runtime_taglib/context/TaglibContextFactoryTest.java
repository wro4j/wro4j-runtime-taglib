/*
 * Copyright (c) 2014 Aleksey Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.lifus.wro4j_runtime_taglib.context;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.ServletContext;

import org.mockito.Mock;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import ro.isdc.wro.manager.factory.WroManagerFactory;

/**
 * Test for {@link TaglibContextFactory}.
 */
public class TaglibContextFactoryTest extends PowerMockTestCase {

  private TaglibContextFactory taglibContextFactory;

  @Mock
  private ServletContext servletContext;

  @BeforeMethod
  public void setUp() {
    taglibContextFactory = new TaglibContextFactory(servletContext);
  }

  @Test
  public void shouldCreateTaglibContext() {
    givenWroManagerFactoryHasBeenSetUp();

    assertThat(taglibContextFactory.create(), is(instanceOf(TaglibContext.class)));
  }

  private void givenWroManagerFactoryHasBeenSetUp() {
    when(servletContext.getAttribute(anyString())).thenReturn(mock(WroManagerFactory.class));
  }

}
