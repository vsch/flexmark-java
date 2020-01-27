package com.vladsch.flexmark.core.test.util.formatter;

import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.test.util.ComboSpecTestCase;
import com.vladsch.flexmark.test.util.spec.ResourceLocation;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.jetbrains.annotations.NotNull;
import org.junit.runners.Parameterized;

import java.util.List;

public class ComboCoreFormatterNoBlankLinesSpecTest extends ComboCoreFormatterSpecTestBase {
    final private static String SPEC_RESOURCE = "/core_formatter_no_blanklines_spec.md";
    final public static @NotNull ResourceLocation RESOURCE_LOCATION = ResourceLocation.of(SPEC_RESOURCE);
    final private static DataHolder OPTIONS = new MutableDataSet()
            .set(Parser.BLANK_LINES_IN_AST, false)
            .toImmutable();

    public ComboCoreFormatterNoBlankLinesSpecTest(@NotNull SpecExample example) {
        super(example, null, OPTIONS);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return ComboSpecTestCase.getTestData(RESOURCE_LOCATION);
    }
}
