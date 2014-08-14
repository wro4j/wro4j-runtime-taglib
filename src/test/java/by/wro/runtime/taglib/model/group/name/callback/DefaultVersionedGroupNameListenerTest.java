/*
 * Copyright (c) 2014 Aleksey Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package by.wro.runtime.taglib.model.group.name.callback;

import static org.mockito.Mockito.verify;

import org.mockito.Mock;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import ro.isdc.wro.model.resource.ResourceType;
import by.wro.runtime.taglib.cache.TaglibCacheKey;
import by.wro.runtime.taglib.model.group.name.cache.GroupNameCache;

/**
 * Test for {@link DefaultVersionedGroupNameListener}.
 */
public class DefaultVersionedGroupNameListenerTest extends PowerMockTestCase {

  private static final String NAME = "name";
  private static final String VERSIONED_NAME = "versioned-name";
  private static final String VERSIONED_PATH = "path/to/versioned-name";
  private static final ResourceType TYPE = ResourceType.values()[0];

  private DefaultVersionedGroupNameListener listener;

  @Mock
  private GroupNameCache nameCache;

  @BeforeMethod
  public void setUp() {
    listener = new DefaultVersionedGroupNameListener(nameCache);
  }

  @Test
  public void shouldPutNameIntoCache() throws Exception {
    final TaglibCacheKey key = new TaglibCacheKey(VERSIONED_NAME, TYPE);

    listener.onVersionedNameCreated(VERSIONED_PATH, NAME, TYPE);

    verify(nameCache).put(key, NAME);
  }

}
