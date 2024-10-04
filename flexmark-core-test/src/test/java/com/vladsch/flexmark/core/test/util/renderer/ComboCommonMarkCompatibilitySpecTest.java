package com.vladsch.flexmark.core.test.util.renderer;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.ParserEmulationProfile;
import com.vladsch.flexmark.test.util.spec.ResourceLocation;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.junit.runners.Parameterized;

public final class ComboCommonMarkCompatibilitySpecTest extends CoreRendererSpecTest {
  private static final String SPEC_RESOURCE = "/core_commonmark_compatibility_spec.md";
  private static final @NotNull ResourceLocation RESOURCE_LOCATION =
      ResourceLocation.of(SPEC_RESOURCE);
  private static final DataHolder OPTIONS =
      new MutableDataSet()
          .setFrom(ParserEmulationProfile.COMMONMARK.getOptions())
          .set(HtmlRenderer.INDENT_SIZE, 4)
          .set(HtmlRenderer.RENDER_HEADER_ID, true)
          .toImmutable();

  public ComboCommonMarkCompatibilitySpecTest(@NotNull SpecExample example) {
    super(example, null, OPTIONS);
  }

  @Parameterized.Parameters(name = "{0}")
  public static List<Object[]> data() {
    return getTestData(RESOURCE_LOCATION);
  }
}
