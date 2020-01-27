package com.vladsch.flexmark.ext.xwiki.macros;

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

public class ComboMacroSpecTest extends RendererSpecTest {
    final private static String SPEC_RESOURCE = "/xwiki_macro_ast_spec.md";
    final public static @NotNull ResourceLocation RESOURCE_LOCATION = ResourceLocation.of(SPEC_RESOURCE);
    final private static DataHolder OPTIONS = new MutableDataSet()
            .set(MacroExtension.ENABLE_RENDERING, true)
            .set(Parser.EXTENSIONS, Collections.singleton(MacroExtension.create()))
            .toImmutable();

    final private static Map<String, DataHolder> optionsMap = new HashMap<>();
    static {
        optionsMap.put("no-rendering", new MutableDataSet().set(MacroExtension.ENABLE_RENDERING, false));
        optionsMap.put("no-inlines", new MutableDataSet().set(MacroExtension.ENABLE_INLINE_MACROS, false));
        optionsMap.put("no-blocks", new MutableDataSet().set(MacroExtension.ENABLE_BLOCK_MACROS, false));
    }
    public ComboMacroSpecTest(@NotNull SpecExample example) {
        super(example, optionsMap, OPTIONS);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return getTestData(RESOURCE_LOCATION);
    }
}
