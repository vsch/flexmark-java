package com.vladsch.flexmark.test.util;

import com.vladsch.flexmark.parser.ParserEmulationProfile;
import com.vladsch.flexmark.test.spec.ResourceLocation;
import com.vladsch.flexmark.util.data.DataHolder;
import org.jetbrains.annotations.NotNull;

final public class FullOrigSpec027CoreTest extends OrigSpecCoreTest {
    static final String SPEC_RESOURCE = "/spec.0.27.txt";
    static final DataHolder OPTIONS = ParserEmulationProfile.COMMONMARK_0_27.getProfileOptions().toImmutable();

    public FullOrigSpec027CoreTest() {
        super(OPTIONS);
    }

    @Override
    public @NotNull ResourceLocation getSpecResourceLocation() {
        return ResourceLocation.of(SPEC_RESOURCE);
    }
}
