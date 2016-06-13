package com.vladsch.flexmark.ext.wikilink;

import com.vladsch.flexmark.node.Node;
import com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.spec.SpecReader;
import com.vladsch.flexmark.test.SpecTestCase;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.List;

public class WikiLinkSpecTest extends SpecTestCase {

    public WikiLinkSpecTest(SpecExample example) {
        super(example);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        List<SpecExample> examples = SpecReader.readExamples(WikiLinkFullSpecTest.SPEC_RESOURCE);
        List<Object[]> data = new ArrayList<>();
        for (SpecExample example : examples) {
            data.add(new Object[] { example });
        }
        return data;
    }

    @Override
    protected Node parse(String source) {
        return WikiLinkFullSpecTest.PARSER.parse(source);
    }

    @Override
    protected String render(Node node) {
        return WikiLinkFullSpecTest.RENDERER.render(node);
    }
}
