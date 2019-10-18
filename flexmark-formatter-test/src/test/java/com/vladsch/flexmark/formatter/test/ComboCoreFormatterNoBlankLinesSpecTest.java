package com.vladsch.flexmark.formatter.test;

import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.jetbrains.annotations.NotNull;
import org.junit.runners.Parameterized;

import java.util.List;

public class ComboCoreFormatterNoBlankLinesSpecTest extends ComboCoreFormatterSpecTestBase {
    private static final String SPEC_RESOURCE = "/core_formatter_no_blanklines_spec.md";
    private static final DataHolder OPTIONS = new MutableDataSet()
            .set(Parser.BLANK_LINES_IN_AST, false)
            .set(Parser.HEADING_NO_ATX_SPACE, true);

    public ComboCoreFormatterNoBlankLinesSpecTest(SpecExample example) {
        super(example, OPTIONS, null);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return getTestData(SPEC_RESOURCE);
    }

    @NotNull
    @Override
    public String getSpecResourceName() {
        return SPEC_RESOURCE;
    }
}
