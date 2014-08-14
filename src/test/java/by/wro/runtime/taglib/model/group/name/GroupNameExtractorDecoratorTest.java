/*
 * Copyright (c) 2014 Aleksey Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package by.wro.runtime.taglib.model.group.name;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;

import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import ro.isdc.wro.model.group.GroupExtractor;
import ro.isdc.wro.model.resource.ResourceType;
import by.wro.runtime.taglib.cache.TaglibCacheKey;
import by.wro.runtime.taglib.model.group.name.cache.GroupNameCache;
import by.wro.runtime.taglib.model.group.name.cache.GroupNameCacheInitializer;

/**
 * Tests for {@link GroupNameExtractorDecorator}.
 */
@PrepareForTest({TaglibCacheKey.class, GroupNameCacheInitializer.class})
public class GroupNameExtractorDecoratorTest extends PowerMockTestCase {

  private static final String ACTUAL_NAME = "actualName";
  private static final String NAME = "name";
  private static final ResourceType TYPE = ResourceType.values()[0];

  private GroupNameExtractorDecorator extractorDecorator;

  @Mock
  private GroupExtractor groupExtractor;
  @Mock
  private GroupNameCacheInitializer groupNameCacheInitializer;
  @Mock
  private GroupNameCache groupNameCache;
  @Mock
  private HttpServletRequest request;

  @BeforeMethod
  public void setUp() {
    extractorDecorator = new GroupNameExtractorDecorator(groupExtractor, groupNameCacheInitializer);
  }

  @Test
  public void shouldGetValueFromCache() {
    givenGenericGroupExtratorReturns(NAME, TYPE);
    givenNameCacheContains(ACTUAL_NAME);

    thenResultingGroupNameIs(ACTUAL_NAME);
  }

  @Test
  public void shouldFallbackToGenericGroupNameIfThereIsNoSuchEntry() {
    givenGenericGroupExtratorReturns(NAME, TYPE);

    thenResultingGroupNameIs(NAME);
  }

  private void givenGenericGroupExtratorReturns(final String name, final ResourceType type) {
    when(groupNameCacheInitializer.get()).thenReturn(groupNameCache);

    when(groupExtractor.getGroupName(request)).thenReturn(name);
    when(groupExtractor.getResourceType(request)).thenReturn(type);
  }

  private void givenNameCacheContains(final String actualName) {
    when(groupNameCache.get(any(TaglibCacheKey.class))).thenReturn(actualName);
  }

  private void thenResultingGroupNameIs(final String name) {
    assertThat(extractorDecorator.getGroupName(request), is(name));
  }
}
