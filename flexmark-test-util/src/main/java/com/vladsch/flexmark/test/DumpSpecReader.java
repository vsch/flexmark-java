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

        // include source so that diff can be used to update spec
        if (example.getAst() != null) {
            sb.append(SpecReader.EXAMPLE_START + " ").append(example.getSection()).append(": ").append(example.getExampleNumber()).append("\n")
                    .append(RenderingTestCase.showTabs(source + SpecReader.TYPE_BREAK + "\n" + html))
                    .append(SpecReader.TYPE_BREAK).append("\n")
                    .append(ast).append(SpecReader.EXAMPLE_BREAK).append("\n");
        } else {
            sb.append(SpecReader.EXAMPLE_START + " ").append(example.getSection()).append(": ").append(example.getExampleNumber()).append("\n")
                    .append(RenderingTestCase.showTabs(source + SpecReader.TYPE_BREAK + "\n" + html))
                    .append(SpecReader.EXAMPLE_BREAK).append("\n");
        }
    }
}
