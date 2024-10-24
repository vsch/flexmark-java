package com.vladsch.flexmark.core.test.util.renderer;

import com.vladsch.flexmark.parser.ParserEmulationProfile;
import com.vladsch.flexmark.test.util.spec.ResourceLocation;
import com.vladsch.flexmark.util.data.DataHolder;

public final class FullOrigSpec027CoreTest extends OrigSpecCoreTest {
  private static final String SPEC_RESOURCE = "/spec.0.27.txt";
  private static final ResourceLocation RESOURCE_LOCATION = ResourceLocation.of(SPEC_RESOURCE);
  private static final DataHolder OPTIONS =
      ParserEmulationProfile.COMMONMARK_0_27.getProfileOptions().toImmutable();

  public FullOrigSpec027CoreTest() {
    super(OPTIONS);
  }

  @Override
  protected ResourceLocation getSpecResourceLocation() {
    return RESOURCE_LOCATION;
  }
}
