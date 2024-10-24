package com.vladsch.flexmark.core.test.util.renderer;

import com.vladsch.flexmark.test.util.spec.ResourceLocation;

public final class FullOrigSpecCoreTest extends OrigSpecCoreTest {
  private static final String SPEC_RESOURCE = "/spec.txt";
  private static final ResourceLocation RESOURCE_LOCATION = ResourceLocation.of(SPEC_RESOURCE);

  public FullOrigSpecCoreTest() {
    super(null);
  }

  @Override
  protected ResourceLocation getSpecResourceLocation() {
    return RESOURCE_LOCATION;
  }
}
