package com.vladsch.flexmark.html.converter;

import com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.test.ActualExampleModifier;
import com.vladsch.flexmark.test.DumpSpecReader;
import com.vladsch.flexmark.test.FullSpecTestCase;
import com.vladsch.flexmark.test.RenderingTestCase;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import org.junit.AssumptionViolatedException;

import java.io.InputStream;

public class HtmlSpecReader extends DumpSpecReader {
    public HtmlSpecReader(InputStream stream, FullSpecTestCase testCase, String fileUrl, ActualExampleModifier exampleModifier) {
        super(stream, testCase, fileUrl, exampleModifier);
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

        if (options != null && options.get(RenderingTestCase.FAIL)) {
            ignoredCase = true;
        }

        String parseSource = example.getHtml();
        if (options != null && options.get(RenderingTestCase.NO_FILE_EOL)) {
            parseSource = trimTrailingEOL(parseSource);
        }

        Node node = testCase.parser().withOptions(options).parse(parseSource);
        String source = !ignoredCase && testCase.useActualHtml() ? testCase.renderer().withOptions(options).render(node) : example.getSource();
        String html = example.getHtml();
        String ast = example.getAst() == null ? null : (!ignoredCase ? testCase.ast(node) : example.getAst());

        // include source so that diff can be used to update spec
        addSpecExample(sb, source, html, ast, example.getOptionsSet(), testCase.includeExampleCoords(), example.getSection(), example.getExampleNumber());
    }
}
