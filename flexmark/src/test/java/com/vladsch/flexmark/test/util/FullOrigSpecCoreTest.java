package com.vladsch.flexmark.test.util;

import com.vladsch.flexmark.test.spec.ResourceLocation;
import org.jetbrains.annotations.NotNull;

final public class FullOrigSpecCoreTest extends OrigSpecCoreTest {
    static final String SPEC_RESOURCE = "/spec.txt";

    public FullOrigSpecCoreTest() {
        super(null);
    }

    @Override
    public @NotNull ResourceLocation getSpecResourceLocation() {
        return ResourceLocation.of(SPEC_RESOURCE);
    }
}
