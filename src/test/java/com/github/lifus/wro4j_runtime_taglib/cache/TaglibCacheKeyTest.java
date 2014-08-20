/*
 * Copyright (c) 2014 Aleksei Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package com.github.lifus.wro4j_runtime_taglib.cache;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.both;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import ro.isdc.wro.model.resource.ResourceType;

/**
 * Tests for {@link TaglibCacheKey}.
 */
public class TaglibCacheKeyTest {
  
  private static final String NAME = "name";
  private static final ResourceType TYPE = ResourceType.values()[0];
  
  private static final String OTHER_NAME = "othername";
  private static final ResourceType OTHER_TYPE = ResourceType.values()[1];

  private TaglibCacheKey cacheKey;

  @BeforeTest
  public void setUp() {
    cacheKey = new TaglibCacheKey(NAME, TYPE);
  }

  @Test(expectedExceptions=NullPointerException.class)
  public void shouldNotAcceptNullName() {
    new TaglibCacheKey(null, TYPE);
  }

  @Test(expectedExceptions=NullPointerException.class)
  public void shouldNotAcceptNullType() {
    new TaglibCacheKey(NAME, null);
  }

  @Test
  public void shouldStoreData() {
    assertThat(cacheKey,
        both(
            hasProperty(NAME, equalTo(NAME)))
        .and(
            hasProperty("type", equalTo(TYPE))));
  }

  @Test(dataProvider="equalsData")
  public void shouldObeyGeneralEqualsContract(final String message, final TaglibCacheKey anotherKey, final boolean asExpected) {
    assertThat(message, cacheKey.equals(anotherKey), is(asExpected));
  }

  @DataProvider(name="equalsData")
  public Object[][] equalsData() {
    return new Object[][] {
        { "reflexive", cacheKey, true },
        { "symmetrical and transitive", new TaglibCacheKey(NAME, TYPE), true },
        { "not equal to null", null, false },
        { "not equal to key with another name", new TaglibCacheKey(OTHER_NAME, TYPE), false},
        { "not equal to key with another type", new TaglibCacheKey(NAME, OTHER_TYPE), false},
        { "not equal to key with another name and type", new TaglibCacheKey(OTHER_NAME, OTHER_TYPE), false}};
  }

  @Test
  public void shouldObeyGeneralHashCodeContract() {
    final TaglibCacheKey sameKey = cacheKey;
    final TaglibCacheKey equalKey = new TaglibCacheKey(NAME, TYPE);
    final TaglibCacheKey anotherNameKey = new TaglibCacheKey(OTHER_NAME, TYPE);
    final TaglibCacheKey anotherTypeKey = new TaglibCacheKey(NAME, OTHER_TYPE);
    final TaglibCacheKey completelyDifferentKey = new TaglibCacheKey(OTHER_NAME, OTHER_TYPE);

    assertThat(cacheKey.hashCode(),
        allOf(
            is(sameKey.hashCode()),
            is(equalKey.hashCode()),
            is(not(anotherNameKey.hashCode())),
            is(not(anotherTypeKey.hashCode())),
            is(not(completelyDifferentKey.hashCode()))));
  }

  @Test
  public void shouldReturnValidString() {
    assertThat(cacheKey, hasToString(NAME + "." + TYPE.name().toLowerCase()));
  }

}
