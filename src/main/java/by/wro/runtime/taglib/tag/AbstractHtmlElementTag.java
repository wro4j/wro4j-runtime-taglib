/*
 * Copyright (c) 2014 Aleksey Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package by.wro.runtime.taglib.tag;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * Base  class for Rendering specific HTML elements on a page.
 */
public abstract class AbstractHtmlElementTag extends SimpleTagSupport {

  /**
   * {@inheritDoc}
   */
  @Override
  public void doTag() throws IOException {
    getJspContext().getOut().append(getOutput());
  }

  /**
   * @return current {@link ServletContext}.
   */
  protected ServletContext getServletContext() {
    return ((PageContext) getJspContext()).getServletContext();
  }

  /**
   * @return raw HTML output.
   */
  protected abstract CharSequence getOutput();

}
