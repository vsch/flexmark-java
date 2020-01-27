package com.vladsch.flexmark.ext.aside;

import com.vladsch.flexmark.core.test.util.RendererSpecTest;
import com.vladsch.flexmark.parser.Parser;
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

public class ComboAsideSpecTest extends RendererSpecTest {
    final private static String SPEC_RESOURCE = "/ext_aside_ast_spec.md";
    final public static @NotNull ResourceLocation RESOURCE_LOCATION = ResourceLocation.of(SPEC_RESOURCE);
    final private static DataHolder OPTIONS = new MutableDataSet()
            .set(Parser.EXTENSIONS, Collections.singleton(AsideExtension.create()))
            .toImmutable();

    final private static Map<String, DataHolder> optionsMap = new HashMap<>();
    static {
        optionsMap.put("extend-to-blank-line", new MutableDataSet().set(AsideExtension.EXTEND_TO_BLANK_LINE, true).set(Parser.BLOCK_QUOTE_EXTEND_TO_BLANK_LINE, true));
        optionsMap.put("ignore-blank-line", new MutableDataSet().set(AsideExtension.IGNORE_BLANK_LINE, true).set(Parser.BLOCK_QUOTE_IGNORE_BLANK_LINE, true));
        optionsMap.put("blank-lines", new MutableDataSet().set(Parser.BLANK_LINES_IN_AST, true).set(TestUtils.NO_FILE_EOL, false));
    }
    public ComboAsideSpecTest(@NotNull SpecExample example) {
        super(example, optionsMap, OPTIONS);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return getTestData(RESOURCE_LOCATION);
    }
}
