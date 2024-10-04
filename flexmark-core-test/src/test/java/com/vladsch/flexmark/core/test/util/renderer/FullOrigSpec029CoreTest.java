package com.vladsch.flexmark.core.test.util.renderer;

import com.vladsch.flexmark.parser.ParserEmulationProfile;
import com.vladsch.flexmark.test.util.spec.ResourceLocation;
import com.vladsch.flexmark.util.data.DataHolder;
import org.jetbrains.annotations.NotNull;

public final class FullOrigSpec029CoreTest extends OrigSpecCoreTest {
  private static final String SPEC_RESOURCE = "/spec.0.29.txt";
  private static final @NotNull ResourceLocation RESOURCE_LOCATION =
      ResourceLocation.of(SPEC_RESOURCE);
  private static final DataHolder OPTIONS =
      ParserEmulationProfile.COMMONMARK_0_29.getProfileOptions().toImmutable();

  public FullOrigSpec029CoreTest() {
    super(OPTIONS);
  }

  @Override
  @NotNull
  protected ResourceLocation getSpecResourceLocation() {
    // FIX: implement 0.29 spec and enable test
    return ResourceLocation.NULL;
  }
}
