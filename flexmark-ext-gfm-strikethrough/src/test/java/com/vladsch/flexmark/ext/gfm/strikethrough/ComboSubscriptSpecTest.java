package com.vladsch.flexmark.ext.gfm.strikethrough;

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

public class ComboSubscriptSpecTest extends RendererSpecTest {
    static final String SPEC_RESOURCE = "/ext_subscript_ast_spec.md";
    final public static @NotNull ResourceLocation RESOURCE_LOCATION = ResourceLocation.of(SPEC_RESOURCE);
    final private static DataHolder OPTIONS = new MutableDataSet()
            .set(HtmlRenderer.INDENT_SIZE, 0)
            .set(Parser.EXTENSIONS, Collections.singleton(SubscriptExtension.create()))
            .toImmutable();

    final private static Map<String, DataHolder> optionsMap = new HashMap<>();
    static {
        optionsMap.put("style-subscript", new MutableDataSet().set(SubscriptExtension.SUBSCRIPT_STYLE_HTML_OPEN, "<span class=\"text-sub\">").set(SubscriptExtension.SUBSCRIPT_STYLE_HTML_CLOSE, "</span>"));
    }
    public ComboSubscriptSpecTest(@NotNull SpecExample example) {
        super(example, optionsMap, OPTIONS);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return getTestData(RESOURCE_LOCATION);
    }
}
