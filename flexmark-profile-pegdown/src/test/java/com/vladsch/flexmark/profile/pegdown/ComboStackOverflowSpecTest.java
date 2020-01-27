package com.vladsch.flexmark.profile.pegdown;

import com.vladsch.flexmark.core.test.util.RendererSpecTest;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.PegdownExtensions;
import com.vladsch.flexmark.test.util.spec.ResourceLocation;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import org.jetbrains.annotations.NotNull;
import org.junit.runners.Parameterized;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComboStackOverflowSpecTest extends RendererSpecTest {
    final private static String SPEC_RESOURCE = "/stack_overflow_spec.md";
    final public static @NotNull ResourceLocation RESOURCE_LOCATION = ResourceLocation.of(SPEC_RESOURCE);
    final private static DataHolder OPTIONS = PegdownOptionsAdapter.flexmarkOptions((PegdownExtensions.ALL & ~PegdownExtensions.HARDWRAPS) | (PegdownExtensions.ALL_OPTIONALS & ~(PegdownExtensions.EXTANCHORLINKS | PegdownExtensions.EXTANCHORLINKS_WRAP))).toMutable()
            .set(HtmlRenderer.FENCED_CODE_LANGUAGE_CLASS_PREFIX, "")
            .set(HtmlRenderer.OBFUSCATE_EMAIL_RANDOM, false)
            .toImmutable();

    final private static Map<String, DataHolder> optionsMap = new HashMap<>();
    static {
        optionsMap.put("hard-breaks", PegdownOptionsAdapter.flexmarkOptions((PegdownExtensions.ALL & ~PegdownExtensions.HARDWRAPS) | (PegdownExtensions.ALL_OPTIONALS & ~(PegdownExtensions.EXTANCHORLINKS | PegdownExtensions.EXTANCHORLINKS_WRAP)) | PegdownExtensions.HARDWRAPS)/*.toMutable().remove(Parser.EXTENSIONS)*/);
        optionsMap.put("anchor-links", PegdownOptionsAdapter.flexmarkOptions((PegdownExtensions.ALL & ~PegdownExtensions.HARDWRAPS) | (PegdownExtensions.ALL_OPTIONALS & ~(PegdownExtensions.EXTANCHORLINKS | PegdownExtensions.EXTANCHORLINKS_WRAP)) | PegdownExtensions.ANCHORLINKS)/*.toMutable().remove(Parser.EXTENSIONS)*/);
    }
    public ComboStackOverflowSpecTest(@NotNull SpecExample example) {
        super(example, optionsMap, OPTIONS);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return getTestData(RESOURCE_LOCATION);
    }
}
