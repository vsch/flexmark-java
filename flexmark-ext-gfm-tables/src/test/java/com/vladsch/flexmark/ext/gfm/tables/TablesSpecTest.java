package com.vladsch.flexmark.ext.gfm.tables;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.spec.SpecReader;
import com.vladsch.flexmark.test.SpecTestCase;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.List;

public class TablesSpecTest extends SpecTestCase {
    public TablesSpecTest(SpecExample example) {
        super(example);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        List<SpecExample> examples = SpecReader.readExamples(TablesFullSpecTest.SPEC_RESOURCE);
        List<Object[]> data = new ArrayList<>();
        for (SpecExample example : examples) {
            data.add(new Object[] { example });
        }
        return data;
    }

    @Override
    protected Parser parser() {
        return TablesFullSpecTest.PARSER;
    }

    @Override
    protected HtmlRenderer renderer() {
        return TablesFullSpecTest.RENDERER;
    }
}
