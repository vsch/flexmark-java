package com.vladsch.flexmark.test;

import com.vladsch.flexmark.internal.util.collection.DataHolder;
import com.vladsch.flexmark.node.Node;
import com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.spec.SpecReader;
import org.junit.AssumptionViolatedException;

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
        DataHolder options;
        boolean ignoredCase = false;
        try {
            options = testCase.getOptions(example, example.getOptionsSet());
        } catch (AssumptionViolatedException ignored) {
            ignoredCase = true;
            options = null;
        }

        Node node = testCase.parser().withOptions(options).parse(example.getSource());
        String html = !ignoredCase && testCase.useActualHtml() ? testCase.renderer().withOptions(options).render(node) : example.getHtml();
        String ast = example.getAst() == null ? null : (!ignoredCase ? testCase.ast(node) : example.getAst());

        // include source so that diff can be used to update spec
        addSpecExample(sb, example.getSource(), html, ast, example.getOptionsSet(), testCase.includeExampleCoords(), example.getSection(), example.getExampleNumber());
    }

    public static String addSpecExample(String source, String html, String ast, String optionsSet) {
        StringBuilder sb = new StringBuilder();
        addSpecExample(sb, source, html, ast, optionsSet, false, "", 0);
        return sb.toString();
    }

    public static void addSpecExample(StringBuilder sb, String source, String html, String ast, String optionsSet, boolean includeExampleCoords, String section, int number) {
        // include source so that diff can be used to update spec
        StringBuilder header = new StringBuilder();

        header.append(SpecReader.EXAMPLE_START);
        if (includeExampleCoords) {
            if (optionsSet != null) {
                header.append("(").append(section.trim()).append(": ").append(number).append(")");
            } else {
                header.append(" ").append(section.trim()).append(": ").append(number);
            }
        }
        if (optionsSet != null) {
            sb.append(SpecReader.OPTIONS_STRING + "(").append(optionsSet).append(")");
        }
        header.append("\n");

        // replace spaces so GitHub can display example as code fence, but not for original spec which has no coords
        if (includeExampleCoords) sb.append(header.toString().replace(' ', '\u00A0'));
        else sb.append(header.toString());

        if (ast != null) {
            sb.append(RenderingTestCase.showTabs(source + SpecReader.TYPE_BREAK + "\n" + html))
                    .append(SpecReader.TYPE_BREAK).append("\n")
                    .append(ast).append(SpecReader.EXAMPLE_BREAK).append("\n");
        } else {
            sb.append(RenderingTestCase.showTabs(source + SpecReader.TYPE_BREAK + "\n" + html))
                    .append(SpecReader.EXAMPLE_BREAK).append("\n");
        }
    }
}
