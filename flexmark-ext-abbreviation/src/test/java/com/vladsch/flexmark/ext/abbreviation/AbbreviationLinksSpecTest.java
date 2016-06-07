package com.vladsch.flexmark.ext.abbreviation;

import com.vladsch.flexmark.node.Node;
import com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.spec.SpecReader;
import com.vladsch.flexmark.test.SpecTestCase;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.List;

public class AbbreviationLinksSpecTest extends SpecTestCase {

    public AbbreviationLinksSpecTest(SpecExample example) {
        super(example);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        List<SpecExample> examples = SpecReader.readExamples(AbbreviationLinksFullSpecTest.SPEC_RESOURCE);
        List<Object[]> data = new ArrayList<>();
        for (SpecExample example : examples) {
            data.add(new Object[] { example });
        }
        return data;
    }

    @Override
    protected Node parse(String source) {
        return AbbreviationLinksFullSpecTest.PARSER.parse(source);
    }

    @Override
    protected String render(Node node) {
        return AbbreviationLinksFullSpecTest.RENDERER.render(node);
    }
}
