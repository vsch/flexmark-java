package com.vladsch.flexmark.ext.admonition;

import com.vladsch.flexmark.core.test.util.RendererSpecTest;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.test.util.spec.ResourceLocation;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.jetbrains.annotations.NotNull;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComboAdmonitionSpecTest extends RendererSpecTest {
    final private static String SPEC_RESOURCE = "/ext_admonition_ast_spec.md";
    final public static @NotNull ResourceLocation RESOURCE_LOCATION = ResourceLocation.of(SPEC_RESOURCE);
    final private static DataHolder OPTIONS = new MutableDataSet()
            .set(Parser.EXTENSIONS, Arrays.asList(AdmonitionExtension.create(), TablesExtension.create()))
            .toImmutable();

    final private static Map<String, DataHolder> optionsMap = new HashMap<>();
    static {
        optionsMap.put("src-pos", new MutableDataSet().set(HtmlRenderer.SOURCE_POSITION_ATTRIBUTE, "md-pos"));
        optionsMap.put("no-lazy-continuation", new MutableDataSet().set(AdmonitionExtension.ALLOW_LAZY_CONTINUATION, false));
        optionsMap.put("no-lead-space", new MutableDataSet().set(AdmonitionExtension.ALLOW_LEADING_SPACE, false));
        optionsMap.put("intellij", new MutableDataSet().set(Parser.INTELLIJ_DUMMY_IDENTIFIER, true));
    }
    public ComboAdmonitionSpecTest(@NotNull SpecExample example) {
        super(example, optionsMap, OPTIONS);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return getTestData(RESOURCE_LOCATION);
    }
}
