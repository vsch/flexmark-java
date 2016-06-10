package com.vladsch.flexmark.ext.footnotes;

import com.vladsch.flexmark.node.Node;
import com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.spec.SpecReader;
import com.vladsch.flexmark.test.SpecTestCase;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.List;

public class FootnotesSpecTest extends SpecTestCase {

    public FootnotesSpecTest(SpecExample example) {
        super(example);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        List<SpecExample> examples = SpecReader.readExamples(FootnotesFullSpecTest.SPEC_RESOURCE);
        List<Object[]> data = new ArrayList<>();
        for (SpecExample example : examples) {
            data.add(new Object[] { example });
        }
        return data;
    }

    @Override
    protected Node parse(String source) {
        return FootnotesFullSpecTest.PARSER.parse(source);
    }

    @Override
    protected String render(Node node) {
        return FootnotesFullSpecTest.RENDERER.render(node);
    }
}
