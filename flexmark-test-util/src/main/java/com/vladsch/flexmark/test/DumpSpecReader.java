package com.vladsch.flexmark.test;

import com.vladsch.flexmark.node.Node;
import com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.spec.SpecReader;

import java.io.InputStream;

class DumpSpecReader extends SpecReader {
    final private StringBuilder sb = new StringBuilder();
    final private FullSpecTestCase testCase;

    DumpSpecReader(InputStream stream, FullSpecTestCase testCase) {
        super(stream);
        this.testCase = testCase;
    }

    public String getFullSpec() {
        return sb.toString();
    }

    @Override
    protected void addSpecLine(String line) {
        sb.append(line).append("\n");
    }

    @Override
    protected void addSpecExample(SpecExample example) {
        Node node = testCase.parse(example.getSource());
        String html = testCase.useActualHtml() ? testCase.render(node) : example.getHtml();
        String ast = testCase.ast(node);

        // include source so that diff can be used to update spece
        String actual;
        
        if (example.getAst() != null) {
            actual = SpecReader.EXAMPLE_START + "\n" + RenderingTestCase.showTabs(source + SpecReader.TYPE_BREAK + "\n" + html) + SpecReader.TYPE_BREAK + "\n" + ast + SpecReader.EXAMPLE_BREAK + "\n";
        } else {
            actual = SpecReader.EXAMPLE_START + "\n" + RenderingTestCase.showTabs(source + SpecReader.TYPE_BREAK + "\n" + html) + SpecReader.EXAMPLE_BREAK + "\n";
        }
        
        sb.append(actual);
    }
}
