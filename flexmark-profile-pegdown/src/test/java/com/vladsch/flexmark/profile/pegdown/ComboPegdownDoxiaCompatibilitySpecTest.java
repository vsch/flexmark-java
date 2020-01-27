package com.vladsch.flexmark.profile.pegdown;

import com.vladsch.flexmark.core.test.util.RendererSpecTest;
import com.vladsch.flexmark.ext.anchorlink.AnchorLinkExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.PegdownExtensions;
import com.vladsch.flexmark.test.util.TestUtils;
import com.vladsch.flexmark.test.util.spec.ResourceLocation;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.jetbrains.annotations.NotNull;
import org.junit.runners.Parameterized;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.vladsch.flexmark.parser.PegdownExtensions.*;

public class ComboPegdownDoxiaCompatibilitySpecTest extends RendererSpecTest {
    final private static String SPEC_RESOURCE = "/pegdown_doxia_compatibility_spec.md";
    final public static @NotNull ResourceLocation RESOURCE_LOCATION = ResourceLocation.of(SPEC_RESOURCE);
    final private static DataHolder OPTIONS = PegdownOptionsAdapter.flexmarkOptions(ALL & ~(HARDWRAPS | ANCHORLINKS)).toMutable()
            .set(HtmlRenderer.FENCED_CODE_LANGUAGE_CLASS_PREFIX, "")
            .set(HtmlRenderer.OBFUSCATE_EMAIL_RANDOM, false)
            .set(HtmlRenderer.INDENT_SIZE, 2)
            .set(HtmlRenderer.PERCENT_ENCODE_URLS, true)
            .toImmutable();

    final private static Map<String, DataHolder> optionsMap = new HashMap<>();
    static {
        optionsMap.put("hard-breaks", PegdownOptionsAdapter.flexmarkOptions((PegdownExtensions.ALL & ~PegdownExtensions.HARDWRAPS) | (PegdownExtensions.ALL_OPTIONALS & ~(PegdownExtensions.EXTANCHORLINKS | PegdownExtensions.EXTANCHORLINKS_WRAP)) | PegdownExtensions.HARDWRAPS)/*.toMutable().remove(Parser.EXTENSIONS)*/);
        optionsMap.put("no-deep-parser", new MutableDataSet().set(Parser.HTML_BLOCK_DEEP_PARSER, false));
        optionsMap.put("anchor-links", PegdownOptionsAdapter.flexmarkOptions((PegdownExtensions.ALL & ~PegdownExtensions.HARDWRAPS) | (PegdownExtensions.ALL_OPTIONALS & ~(PegdownExtensions.EXTANCHORLINKS | PegdownExtensions.EXTANCHORLINKS_WRAP)) | PegdownExtensions.ANCHORLINKS)/*.toMutable().remove(Parser.EXTENSIONS)*/);
        optionsMap.put("no-anchor-links", PegdownOptionsAdapter.flexmarkOptions((PegdownExtensions.ALL & ~(PegdownExtensions.HARDWRAPS | PegdownExtensions.ANCHORLINKS)) | (PegdownExtensions.ALL_OPTIONALS & ~(PegdownExtensions.EXTANCHORLINKS | PegdownExtensions.EXTANCHORLINKS_WRAP)))
                .toMutable().set(TestUtils.UNLOAD_EXTENSIONS, Collections.singleton(AnchorLinkExtension.class)));
        optionsMap.put("code-soft-breaks", new MutableDataSet().set(Parser.CODE_SOFT_LINE_BREAKS, true).set(HtmlRenderer.SOFT_BREAK, "\n"));
        optionsMap.put("code-soft-break-spaces", new MutableDataSet().set(Parser.CODE_SOFT_LINE_BREAKS, true).set(HtmlRenderer.SOFT_BREAK, " \t"));
    }
    public ComboPegdownDoxiaCompatibilitySpecTest(@NotNull SpecExample example) {
        super(example, optionsMap, OPTIONS);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return getTestData(RESOURCE_LOCATION);
    }
}
