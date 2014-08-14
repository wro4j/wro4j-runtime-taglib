/*
 * Copyright (c) 2014 Aleksey Polkanov. All rights reserved.
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package by.wro.runtime.taglib.manager;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import ro.isdc.wro.manager.factory.WroManagerFactory;
import ro.isdc.wro.model.group.processor.InjectorBuilder;

/**
 * Test for {@link InjectorInitializer}.
 */
@PrepareForTest(InjectorBuilder.class)
public class InjectorInitializerTest extends PowerMockTestCase {

  private InjectorInitializer injectorInitializer;

  @Mock
  private WroManagerFactory managerFactory;

  @BeforeMethod
  public void setUp() {
    injectorInitializer = new InjectorInitializer(managerFactory);
  }

  @Test
  public void shouldGetInjector() {
    givenInjectorBuilderHasBeenSetUp();

    whenGettingInitializer();

    thenShouldCreateInjectorBuilder();
  }

  private void givenInjectorBuilderHasBeenSetUp() {
    mockStatic(InjectorBuilder.class);
    final InjectorBuilder injectorBuilder = mock(InjectorBuilder.class);
    when(InjectorBuilder.create(managerFactory)).thenReturn(injectorBuilder);
  }

  private void whenGettingInitializer() {
    injectorInitializer.get();
  }

  private void thenShouldCreateInjectorBuilder() {
    verifyStatic();
    InjectorBuilder.create(managerFactory);
  }

}
