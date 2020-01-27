package com.vladsch.flexmark.ext.superscript;

import com.vladsch.flexmark.core.test.util.RendererSpecTest;
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

public class ComboSuperscriptSpecTest extends RendererSpecTest {
    final private static String SPEC_RESOURCE = "/superscript_ast_spec.md";
    final public static @NotNull ResourceLocation RESOURCE_LOCATION = ResourceLocation.of(SPEC_RESOURCE);
    final private static DataHolder OPTIONS = new MutableDataSet()
            .set(Parser.EXTENSIONS, Collections.singleton(SuperscriptExtension.create()))
            .toImmutable();

    final private static Map<String, DataHolder> optionsMap = new HashMap<>();
    static {
        optionsMap.put("style-superscript", new MutableDataSet().set(SuperscriptExtension.SUPERSCRIPT_STYLE_HTML_OPEN, "<span class=\"text-sup\">").set(SuperscriptExtension.SUPERSCRIPT_STYLE_HTML_CLOSE, "</span>"));
    }
    public ComboSuperscriptSpecTest(@NotNull SpecExample example) {
        super(example, optionsMap, OPTIONS);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return getTestData(RESOURCE_LOCATION);
    }
}
