package com.vladsch.flexmark.core.test.util.renderer;

import com.vladsch.flexmark.test.util.spec.ResourceLocation;

final public class FullOrigSpecCoreTest extends OrigSpecCoreTest {
    static final String SPEC_RESOURCE = "/spec.txt";
    final public static ResourceLocation RESOURCE_LOCATION = ResourceLocation.of(SPEC_RESOURCE);

    public FullOrigSpecCoreTest() {
        super(null);
    }
    
    @Override
    protected ResourceLocation getSpecResourceLocation() {
        return RESOURCE_LOCATION;
    }
}
