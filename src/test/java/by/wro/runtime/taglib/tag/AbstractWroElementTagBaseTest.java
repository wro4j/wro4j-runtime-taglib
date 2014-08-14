/*
 * Copyright (c) 2014 Aleksey Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package by.wro.runtime.taglib.tag;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.util.reflection.Whitebox.setInternalState;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.Test;

import ro.isdc.wro.model.resource.ResourceType;
import by.wro.runtime.taglib.context.TaglibContext;

/**
 * Template for tests of {@link AbstractWroElementTag} derivatives.
 */
@PrepareForTest(TaglibContext.class)
public abstract class AbstractWroElementTagBaseTest extends PowerMockTestCase {

  private static final String GROUP_NAME = "groupName";

  @Test
  public void shouldProvideGroupNameAccessor() {
    givenGroupNameIs(GROUP_NAME);

    assertThat(getTag().getGroup(), is(GROUP_NAME));
  }

  @Test
  public void shouldOutputScriptTag() {
    givenGroupNameIs(GROUP_NAME);

    final PageContext currentPageContext = currentPageContext();
    final ServletContext servletContext = servletContextFor(currentPageContext);
    givenTaglibContextHasBeenInitializedFor(servletContext);

    assertThat((String) getTag().getOutput(), is(getExpectedOutput()));
  }

  @Test
  public void shouldAppendTagToOutput() throws IOException {
    givenGroupNameIs(GROUP_NAME);

    final PageContext pageContext = currentPageContext();
    final ServletContext servletContext = servletContextFor(pageContext);
    givenTaglibContextHasBeenInitializedFor(servletContext);
    final JspWriter jspWriter = givenWriterIsDefined(pageContext);

    getTag().doTag();

    verify(jspWriter).append(getExpectedOutput());
  }

  private void givenGroupNameIs(final String groupName) {
    getTag().setGroup(groupName);
  }

  private void givenTaglibContextHasBeenInitializedFor(final ServletContext servletContext) {
    mockStatic(TaglibContext.class);
    final TaglibContext taglibContext = mock(TaglibContext.class);
    when(TaglibContext.get(servletContext)).thenReturn(taglibContext);
    when(taglibContext.getResourceUris(GROUP_NAME, getResourceType())).thenReturn(new String[] { "uri" });
  }

  private PageContext currentPageContext() {
    final PageContext pageContext = mock(PageContext.class);
    setInternalState(getTag(), "jspContext", pageContext);
    return pageContext;
  }

  private ServletContext servletContextFor(final PageContext pageContext) {
    final ServletContext servletContext = mock(ServletContext.class);
    when(pageContext.getServletContext()).thenReturn(servletContext);
    return servletContext;
  }

  private JspWriter givenWriterIsDefined(final PageContext pageContext) {
    final JspWriter jspWriter = mock(JspWriter.class);
    when(pageContext.getOut()).thenReturn(jspWriter);
    return jspWriter;
  }

  protected abstract String getExpectedOutput();

  protected abstract ResourceType getResourceType();

  protected abstract AbstractWroElementTag getTag();

}
