/*
 * Copyright (c) 2014 Aleksey Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.lifus.wro4j_runtime_taglib.tag;

import org.testng.annotations.BeforeMethod;

import ro.isdc.wro.model.resource.ResourceType;

/**
 * Tests for {@link ScriptTag}.
 */
public class ScriptTagTest extends AbstractWroElementTagBaseTest {

  private ScriptTag scriptTag;

  @BeforeMethod
  public void setUp() {
    scriptTag = new ScriptTag();
  }

  @Override
  protected AbstractWroElementTag getTag() {
    return scriptTag;
  }

  @Override
  protected String getExpectedOutput() {
    return "<script src='uri'></script>";
  }

  @Override
  protected ResourceType getResourceType() {
    return ResourceType.JS;
  }

}
