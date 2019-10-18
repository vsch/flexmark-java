package com.vladsch.flexmark.ext.anchorlink;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.test.spec.SpecExample;
import com.vladsch.flexmark.test.util.ComboSpecTestCase;
import com.vladsch.flexmark.test.util.FlexmarkSpecExampleRenderer;
import com.vladsch.flexmark.test.util.SpecExampleRenderer;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.runners.Parameterized;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComboAnchorLinkSpecTest extends ComboSpecTestCase {
    private static final String SPEC_RESOURCE = "/ext_anchorlink_ast_spec.md";
    private static final DataHolder OPTIONS = new MutableDataSet()
            .set(AnchorLinkExtension.ANCHORLINKS_ANCHOR_CLASS, "anchor")
            .set(HtmlRenderer.INDENT_SIZE, 2)
            .set(AnchorLinkExtension.ANCHORLINKS_NO_BLOCK_QUOTE, true)
            .set(HtmlRenderer.RENDER_HEADER_ID, false)
            .set(HtmlRenderer.GENERATE_HEADER_ID, true)
            //.set(HtmlRenderer.PERCENT_ENCODE_URLS, true)
            .set(Parser.EXTENSIONS, Collections.singleton(AnchorLinkExtension.create()));

    private static final Map<String, DataHolder> optionsMap = new HashMap<>();
    static {
        optionsMap.put("src-pos", new MutableDataSet().set(HtmlRenderer.SOURCE_POSITION_ATTRIBUTE, "md-pos"));
        optionsMap.put("no-wrap", new MutableDataSet().set(AnchorLinkExtension.ANCHORLINKS_WRAP_TEXT, false));
        optionsMap.put("set-name", new MutableDataSet().set(AnchorLinkExtension.ANCHORLINKS_SET_NAME, true));
        optionsMap.put("no-id", new MutableDataSet().set(AnchorLinkExtension.ANCHORLINKS_SET_ID, false));
        optionsMap.put("no-class", new MutableDataSet().set(AnchorLinkExtension.ANCHORLINKS_ANCHOR_CLASS, ""));
        optionsMap.put("prefix-suffix", new MutableDataSet()
                .set(AnchorLinkExtension.ANCHORLINKS_TEXT_PREFIX, "<span class=\"anchor\">")
                .set(AnchorLinkExtension.ANCHORLINKS_TEXT_SUFFIX, "</span>")
        );
    }
    public ComboAnchorLinkSpecTest(SpecExample example) {
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
