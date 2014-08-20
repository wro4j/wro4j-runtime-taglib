/*
 * Copyright (c) 2014 Aleksey Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.lifus.wro4j_runtime_taglib.tag;

import org.testng.annotations.BeforeMethod;

import ro.isdc.wro.model.resource.ResourceType;

/**
 * Tests for {@link StylesheetTag}.
 */
public class StylesheetTagTest extends AbstractWroElementTagBaseTest {

  private StylesheetTag stylesheetTag;

  @BeforeMethod
  public void setUp() {
    stylesheetTag = new StylesheetTag();
  }

  @Override
  protected AbstractWroElementTag getTag() {
    return stylesheetTag;
  }

  @Override
  protected String getExpectedOutput() {
    return "<link rel='stylesheet' href='uri' />";
  }

  @Override
  protected ResourceType getResourceType() {
    return ResourceType.CSS;
  }

}
