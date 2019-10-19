package com.vladsch.flexmark.core.test.util.renderer;

import com.vladsch.flexmark.test.util.spec.ResourceLocation;
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
