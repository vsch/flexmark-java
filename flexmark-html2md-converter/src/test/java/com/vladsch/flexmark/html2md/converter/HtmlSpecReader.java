package com.vladsch.flexmark.html2md.converter;

import com.vladsch.flexmark.test.spec.SpecExample;
import com.vladsch.flexmark.test.util.DumpSpecReader;
import com.vladsch.flexmark.test.util.FullSpecTestCase;
import com.vladsch.flexmark.test.util.SpecExampleRenderer;
import com.vladsch.flexmark.test.util.TestUtils;
import com.vladsch.flexmark.util.data.DataHolder;
import org.jetbrains.annotations.NotNull;
import org.junit.AssumptionViolatedException;

import java.io.InputStream;

public class HtmlSpecReader extends DumpSpecReader {
    public HtmlSpecReader(InputStream stream, FullSpecTestCase testCase, String fileUrl) {
        super(stream, testCase, fileUrl);
    }

    @Override
    protected void addSpecExample(@NotNull SpecExample example) {
        DataHolder exampleOptions;
        boolean ignoredCase = false;
        try {
            exampleOptions = TestUtils.getOptions(example, example.getOptionsSet(), testCase::options, testCase::combineOptions);
        } catch (AssumptionViolatedException ignored) {
            ignoredCase = true;
            exampleOptions = null;
        }

        if (exampleOptions != null && exampleOptions.get(TestUtils.FAIL)) {
            ignoredCase = true;
        }

        String parseSource = example.getHtml();
        @NotNull SpecExampleRenderer exampleRenderer = testCase.getSpecExampleRenderer(example, exampleOptions);

        if (TestUtils.NO_FILE_EOL.getFrom(exampleRenderer.getOptions())) {
            parseSource = TestUtils.trimTrailingEOL(parseSource);
        }

        exampleRenderer.parse(parseSource);
        exampleRenderer.finalizeDocument();
        String source = !ignoredCase ? exampleRenderer.getHtml() : example.getSource();
        String html = example.getHtml();
        String ast = example.getAst() == null ? null : (!ignoredCase ? exampleRenderer.getAst() : example.getAst());

        // include source so that diff can be used to update spec
        TestUtils.addSpecExample(sb, source, html, ast, example.getOptionsSet(), exampleRenderer.includeExampleInfo(), example.getSection(), example.getExampleNumber());
    }
}
