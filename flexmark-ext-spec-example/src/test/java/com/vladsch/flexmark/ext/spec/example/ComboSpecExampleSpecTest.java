package com.vladsch.flexmark.ext.spec.example;

import com.vladsch.flexmark.ext.spec.example.internal.RenderAs;
import com.vladsch.flexmark.core.test.util.RendererSpecTest;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.test.spec.ResourceLocation;
import com.vladsch.flexmark.test.spec.SpecExample;
import com.vladsch.flexmark.test.spec.SpecReader;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.jetbrains.annotations.NotNull;
import org.junit.runners.Parameterized;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComboSpecExampleSpecTest extends RendererSpecTest {
    private static final String SPEC_RESOURCE = "/ext_spec_example_ast_spec.md";
    private static final DataHolder OPTIONS = new MutableDataSet()
            .set(SpecExampleExtension.SPEC_EXAMPLE_BREAK, SpecReader.EXAMPLE_TEST_BREAK)
            .set(SpecExampleExtension.SPEC_TYPE_BREAK, SpecReader.TYPE_TEST_BREAK)
            .set(SpecExampleExtension.SPEC_EXAMPLE_RENDER_AS, RenderAs.SECTIONS)
            .set(Parser.EXTENSIONS, Collections.singleton(SpecExampleExtension.create()))
            .toImmutable();

    private static final Map<String, DataHolder> optionsMap = new HashMap<>();
    static {
        optionsMap.put("no-language-prefix", new MutableDataSet().set(HtmlRenderer.FENCED_CODE_LANGUAGE_CLASS_PREFIX, ""));
        optionsMap.put("no-option-nodes", new MutableDataSet().set(SpecExampleExtension.SPEC_OPTION_NODES, false));
        optionsMap.put("as-def-list", new MutableDataSet().set(SpecExampleExtension.SPEC_EXAMPLE_RENDER_AS, RenderAs.DEFINITION_LIST));
        optionsMap.put("as-fenced-code", new MutableDataSet().set(SpecExampleExtension.SPEC_EXAMPLE_RENDER_AS, RenderAs.FENCED_CODE));
    }
    public ComboSpecExampleSpecTest(@NotNull SpecExample example) {
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
