package com.vladsch.flexmark.ext.anchorlink;

import com.vladsch.flexmark.core.test.util.RendererSpecTest;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
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

public class ComboAnchorLinkSpecTest extends RendererSpecTest {
    final private static String SPEC_RESOURCE = "/ext_anchorlink_ast_spec.md";
    final public static @NotNull ResourceLocation RESOURCE_LOCATION = ResourceLocation.of(SPEC_RESOURCE);
    final private static DataHolder OPTIONS = new MutableDataSet()
            .set(Parser.EXTENSIONS, Collections.singleton(AnchorLinkExtension.create()))
            .set(AnchorLinkExtension.ANCHORLINKS_ANCHOR_CLASS, "anchor")
            .set(AnchorLinkExtension.ANCHORLINKS_NO_BLOCK_QUOTE, true)
            .set(HtmlRenderer.RENDER_HEADER_ID, false)
            .set(HtmlRenderer.GENERATE_HEADER_ID, true)
            .toImmutable();

    final private static Map<String, DataHolder> optionsMap = new HashMap<>();
    static {
        optionsMap.put("no-wrap", new MutableDataSet().set(AnchorLinkExtension.ANCHORLINKS_WRAP_TEXT, false));
        optionsMap.put("set-name", new MutableDataSet().set(AnchorLinkExtension.ANCHORLINKS_SET_NAME, true));
        optionsMap.put("no-id", new MutableDataSet().set(AnchorLinkExtension.ANCHORLINKS_SET_ID, false));
        optionsMap.put("no-class", new MutableDataSet().set(AnchorLinkExtension.ANCHORLINKS_ANCHOR_CLASS, ""));
        optionsMap.put("prefix-suffix", new MutableDataSet()
                .set(AnchorLinkExtension.ANCHORLINKS_TEXT_PREFIX, "<span class=\"anchor\">")
                .set(AnchorLinkExtension.ANCHORLINKS_TEXT_SUFFIX, "</span>")
        );
    }
    public ComboAnchorLinkSpecTest(@NotNull SpecExample example) {
        super(example, optionsMap, OPTIONS);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return getTestData(RESOURCE_LOCATION);
    }
}
