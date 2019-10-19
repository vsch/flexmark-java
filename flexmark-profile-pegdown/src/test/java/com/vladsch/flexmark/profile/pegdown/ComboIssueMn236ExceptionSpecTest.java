package com.vladsch.flexmark.profile.pegdown;

import com.vladsch.flexmark.core.test.util.RendererSpecTest;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.test.spec.ResourceLocation;
import com.vladsch.flexmark.test.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import org.jetbrains.annotations.NotNull;
import org.junit.runners.Parameterized;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.vladsch.flexmark.profile.pegdown.Extensions.*;

public class ComboIssueMn236ExceptionSpecTest extends RendererSpecTest {
    private static final String SPEC_RESOURCE = "/issue_mn_236_exception_spec.md";
    static final DataHolder OPTIONS = PegdownOptionsAdapter.flexmarkOptions((ALL & ~HARDWRAPS) | (ALL_OPTIONALS & ~(EXTANCHORLINKS | EXTANCHORLINKS_WRAP))).toMutable()
            .set(HtmlRenderer.FENCED_CODE_LANGUAGE_CLASS_PREFIX, "")
            .set(HtmlRenderer.OBFUSCATE_EMAIL_RANDOM, false)
            .toImmutable();

    private static final Map<String, DataHolder> optionsMap = new HashMap<>();
    static {
        optionsMap.put("hard-breaks", PegdownOptionsAdapter.flexmarkOptions((ALL & ~HARDWRAPS) | (ALL_OPTIONALS & ~(EXTANCHORLINKS | EXTANCHORLINKS_WRAP)) | HARDWRAPS)/*.toMutable().remove(Parser.EXTENSIONS)*/);
        optionsMap.put("anchor-links", PegdownOptionsAdapter.flexmarkOptions((ALL & ~HARDWRAPS) | (ALL_OPTIONALS & ~(EXTANCHORLINKS | EXTANCHORLINKS_WRAP)) | ANCHORLINKS)/*.toMutable().remove(Parser.EXTENSIONS)*/);
    }
    public ComboIssueMn236ExceptionSpecTest(@NotNull SpecExample example) {
        super(example, optionsMap, OPTIONS);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return getTestData(SPEC_RESOURCE);
    }

    @Override
    public @NotNull ResourceLocation getSpecResourceLocation() {
        return ResourceLocation.of(SPEC_RESOURCE);
    }
}
