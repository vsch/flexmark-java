package com.vladsch.flexmark.core.test.util.renderer;

import com.vladsch.flexmark.parser.ParserEmulationProfile;
import com.vladsch.flexmark.test.util.spec.ResourceLocation;
import com.vladsch.flexmark.util.data.DataHolder;
import org.jetbrains.annotations.NotNull;

final public class FullOrigSpec028CoreTest extends OrigSpecCoreTest {
    static final String SPEC_RESOURCE = "/spec.0.28.txt";
    final public static @NotNull ResourceLocation RESOURCE_LOCATION = ResourceLocation.of(SPEC_RESOURCE);
    final private static DataHolder OPTIONS = ParserEmulationProfile.COMMONMARK_0_28.getProfileOptions().toImmutable();

    public FullOrigSpec028CoreTest() {
        super(OPTIONS);
    }

    @Override
    @NotNull
    protected ResourceLocation getSpecResourceLocation() {
        return RESOURCE_LOCATION;
    }
}
