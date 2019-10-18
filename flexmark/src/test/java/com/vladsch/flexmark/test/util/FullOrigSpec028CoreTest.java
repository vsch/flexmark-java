package com.vladsch.flexmark.test.util;

import com.vladsch.flexmark.parser.ParserEmulationProfile;
import com.vladsch.flexmark.test.spec.ResourceLocation;
import com.vladsch.flexmark.util.data.DataHolder;
import org.jetbrains.annotations.NotNull;

final public class FullOrigSpec028CoreTest extends OrigSpecCoreTest {
    static final String SPEC_RESOURCE = "/spec.0.28.txt";
    static final DataHolder OPTIONS = ParserEmulationProfile.COMMONMARK_0_28.getProfileOptions().toImmutable();

    public FullOrigSpec028CoreTest() {
        super(OPTIONS);
    }

    @Override
    public @NotNull ResourceLocation getSpecResourceLocation() {
        return ResourceLocation.of(SPEC_RESOURCE);
    }
}
