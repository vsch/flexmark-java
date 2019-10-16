package com.vladsch.flexmark.convert.html;

import com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.test.*;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import org.jetbrains.annotations.NotNull;
import org.junit.AssumptionViolatedException;

import java.io.InputStream;

public class HtmlSpecReader extends DumpSpecReader {
    public HtmlSpecReader(InputStream stream, FullSpecTestCase testCase, String fileUrl) {
        super(stream, testCase, fileUrl);
    }

    @Override
    protected void addSpecExample(SpecExample example) {
        DataHolder options;
        boolean ignoredCase = false;
        try {
            options = TestUtils.getOptions(example, example.getOptionsSet(), testCase::options, testCase::combineOptions);
        } catch (AssumptionViolatedException ignored) {
            ignoredCase = true;
            options = null;
        }

        if (options != null && options.get(TestUtils.FAIL)) {
            ignoredCase = true;
        }

        String parseSource = example.getHtml();
        if (options != null && options.get(TestUtils.NO_FILE_EOL)) {
            parseSource = TestUtils.trimTrailingEOL(parseSource);
        }

        @NotNull SpecExampleRenderer exampleRenderer = testCase.getSpecExampleRenderer(options);
        exampleRenderer.parse(parseSource);
        exampleRenderer.finalizeDocument();
        String source = !ignoredCase ? exampleRenderer.renderHtml() : example.getSource();
        String html = example.getHtml();
        String ast = example.getAst() == null ? null : (!ignoredCase ? exampleRenderer.getAst() : example.getAst());

        // include source so that diff can be used to update spec
        TestUtils.addSpecExample(sb, source, html, ast, example.getOptionsSet(), exampleRenderer.includeExampleInfo(), example.getSection(), example.getExampleNumber());
    }
}
