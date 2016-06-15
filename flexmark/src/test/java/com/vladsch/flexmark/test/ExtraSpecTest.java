package com.vladsch.flexmark.test;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.internal.util.DataHolder;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.spec.SpecReader;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.List;

public class ExtraSpecTest extends SpecTestCase {

    public ExtraSpecTest(SpecExample example) {
        super(example);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        List<SpecExample> examples = SpecReader.readExamples(FullExtraSpecTest.SPEC_RESOURCE);
        List<Object[]> data = new ArrayList<>();
        for (SpecExample example : examples) {
            data.add(new Object[] { example });
        }
        return data;
    }

    @Override
    protected DataHolder options(String optionSet) {
        return FullExtraSpecTest.optionsSet(optionSet);
    }

    @Override
    protected Parser parser() {
        return FullExtraSpecTest.PARSER;
    }

    @Override
    protected HtmlRenderer renderer() {
        return FullExtraSpecTest.RENDERER;
    }
}
