package com.vladsch.flexmark.test;

import com.vladsch.flexmark.node.Node;
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
    protected Node parse(String source) {
        return FullExtraSpecTest.PARSER.parse(source);
    }

    @Override
    protected String render(Node node) {
        return FullExtraSpecTest.RENDERER.render(node);
    }
}
