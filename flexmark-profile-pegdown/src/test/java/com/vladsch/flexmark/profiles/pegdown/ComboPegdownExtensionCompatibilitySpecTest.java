package com.vladsch.flexmark.profiles.pegdown;

import com.vladsch.flexmark.ext.anchorlink.AnchorLinkExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.test.spec.SpecExample;
import com.vladsch.flexmark.test.util.ComboSpecTestCase;
import com.vladsch.flexmark.test.util.FlexmarkSpecExampleRenderer;
import com.vladsch.flexmark.test.util.SpecExampleRenderer;
import com.vladsch.flexmark.test.util.TestUtils;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.runners.Parameterized;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.vladsch.flexmark.profiles.pegdown.Extensions.*;

public class ComboPegdownExtensionCompatibilitySpecTest extends ComboSpecTestCase {
    private static final String SPEC_RESOURCE = "/pegdown_extension_compatibility_spec.md";
    static final DataHolder OPTIONS = PegdownOptionsAdapter.flexmarkOptions((ALL & ~(HARDWRAPS | ANCHORLINKS)) | (ALL_OPTIONALS & ~(EXTANCHORLINKS_WRAP)) | ABBREVIATIONS | EXTANCHORLINKS).toMutable()
            .set(HtmlRenderer.INDENT_SIZE, 2)
            .set(HtmlRenderer.FENCED_CODE_LANGUAGE_CLASS_PREFIX, "")
            .set(HtmlRenderer.OBFUSCATE_EMAIL_RANDOM, false)
            .set(HtmlRenderer.PERCENT_ENCODE_URLS, true);

    private static final Map<String, DataHolder> optionsMap = new HashMap<>();
    static {
        optionsMap.put("hard-breaks", PegdownOptionsAdapter.flexmarkOptions((ALL & ~HARDWRAPS) | (ALL_OPTIONALS & ~(EXTANCHORLINKS | EXTANCHORLINKS_WRAP)) | HARDWRAPS)/*.toMutable().remove(Parser.EXTENSIONS)*/);
        optionsMap.put("anchor-links", PegdownOptionsAdapter.flexmarkOptions((ALL & ~HARDWRAPS) | (ALL_OPTIONALS & ~(EXTANCHORLINKS | EXTANCHORLINKS_WRAP)) | ANCHORLINKS)/*.toMutable().remove(Parser.EXTENSIONS)*/);
        optionsMap.put("no-anchor-links", PegdownOptionsAdapter.flexmarkOptions((ALL & ~(HARDWRAPS | ANCHORLINKS)) | (ALL_OPTIONALS & ~(EXTANCHORLINKS | EXTANCHORLINKS_WRAP)))
                .toMutable().set(TestUtils.UNLOAD_EXTENSIONS, Collections.singleton(AnchorLinkExtension.create())));
        optionsMap.put("code-soft-breaks", new MutableDataSet().set(Parser.CODE_SOFT_LINE_BREAKS, true).set(HtmlRenderer.SOFT_BREAK, "\n"));
        optionsMap.put("code-soft-break-spaces", new MutableDataSet().set(Parser.CODE_SOFT_LINE_BREAKS, true).set(HtmlRenderer.SOFT_BREAK, " \t"));
    }
    public ComboPegdownExtensionCompatibilitySpecTest(SpecExample example) {
        super(example);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return getTestData(SPEC_RESOURCE);
    }

    @Nullable
    @Override
    public DataHolder options(String option) {
        return optionsMap.get(option);
    }

    @NotNull
    @Override
    public String getSpecResourceName() {
        return SPEC_RESOURCE;
    }

    @Override
    public @NotNull SpecExampleRenderer getSpecExampleRenderer(@NotNull SpecExample example, @Nullable DataHolder exampleOptions) {
        DataHolder combinedOptions = combineOptions(OPTIONS, exampleOptions);
        return new FlexmarkSpecExampleRenderer(example, combinedOptions, Parser.builder(combinedOptions).build(), HtmlRenderer.builder(combinedOptions).build(), true);
    }
}
