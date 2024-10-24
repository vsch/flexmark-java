package com.vladsch.flexmark.test.specs;

import com.vladsch.flexmark.test.util.spec.ResourceLocation;

public final class TestSpecLocator {
  public static final String DEFAULT_SPEC_RESOURCE = "/spec.txt";
  public static final ResourceLocation DEFAULT_RESOURCE_LOCATION =
      ResourceLocation.of(TestSpecLocator.class, DEFAULT_SPEC_RESOURCE);

  private TestSpecLocator() {
    throw new IllegalStateException();
  }
}
