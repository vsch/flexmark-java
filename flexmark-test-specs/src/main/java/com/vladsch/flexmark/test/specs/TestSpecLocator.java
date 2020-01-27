package com.vladsch.flexmark.test.specs;

import com.vladsch.flexmark.test.util.spec.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class TestSpecLocator {
    final public static String DEFAULT_SPEC_RESOURCE = "/spec.txt";
    final public static @NotNull ResourceLocation DEFAULT_RESOURCE_LOCATION = ResourceLocation.of(TestSpecLocator.class, DEFAULT_SPEC_RESOURCE);
}
