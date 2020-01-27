package com.vladsch.flexmark.profile.pegdown;

import com.vladsch.flexmark.core.test.util.RendererSpecTest;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.test.util.spec.ResourceLocation;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import org.jetbrains.annotations.NotNull;
import org.junit.runners.Parameterized;

import java.util.List;

public class ComboPegdownCompatibilitySpecTest extends RendererSpecTest {
    final private static String SPEC_RESOURCE = "/pegdown_profile_compatibility_spec.md";
    final public static @NotNull ResourceLocation RESOURCE_LOCATION = ResourceLocation.of(SPEC_RESOURCE);
    final private static DataHolder OPTIONS = PegdownOptionsAdapter.flexmarkOptions(
            Extensions.FENCED_CODE_BLOCKS | Extensions.AUTOLINKS
    ).toMutable()
            .set(HtmlRenderer.OBFUSCATE_EMAIL_RANDOM, false)
            .toImmutable();

    public ComboPegdownCompatibilitySpecTest(@NotNull SpecExample example) {
        super(example, null, OPTIONS);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return getTestData(RESOURCE_LOCATION);
    }
}
