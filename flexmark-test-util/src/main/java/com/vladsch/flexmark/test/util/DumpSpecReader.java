package com.vladsch.flexmark.test.util;

import com.vladsch.flexmark.test.util.spec.ResourceLocation;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.test.util.spec.SpecReader;
import com.vladsch.flexmark.util.data.DataHolder;
import org.jetbrains.annotations.NotNull;
import org.junit.AssumptionViolatedException;

import java.io.InputStream;

public class DumpSpecReader extends SpecReader {
    protected final StringBuilder sb = new StringBuilder();
    protected final StringBuilder sbExp = new StringBuilder();
    protected final SpecExampleProcessor testCase;
    protected StringBuilder exampleComment;

    public DumpSpecReader(@NotNull InputStream stream, @NotNull SpecExampleProcessor testCase, @NotNull ResourceLocation location, boolean compoundSections) {
        super(stream, location, compoundSections);
        this.testCase = testCase;
    }

    public String getFullSpec() {
        return sb.toString();
    }

    public String getExpectedFullSpec() {
        return sbExp.toString();
    }

    public void readExamples() {
        super.readExamples();
    }

    @Override
    public void addSpecLine(String line, boolean isSpecExampleOpen) {
        if (!isSpecExampleOpen) sb.append(line).append("\n");
        sbExp.append(line).append("\n");
    }

    @Override
    protected void addSpecExample(@NotNull SpecExample specExample) {
        // not needed but to keep it consistent with SpecReader
        super.addSpecExample(specExample);

        SpecExample example = testCase.checkExample(specExample);
        DataHolder exampleOptions;
        boolean ignoredTestCase = false;

        try {
            exampleOptions = TestUtils.getOptions(example, example.getOptionsSet(), testCase::options);
        } catch (AssumptionViolatedException ignored) {
            ignoredTestCase = true;
            exampleOptions = null;
        }

        if (exampleOptions != null && TestUtils.FAIL.get(exampleOptions)) {
            ignoredTestCase = true;
        }

        SpecExampleRenderer exampleRenderer = testCase.getSpecExampleRenderer(example, exampleOptions);

        final SpecExampleParse exampleParse = new SpecExampleParse(exampleRenderer.getOptions(), exampleRenderer, exampleOptions, example.getSource());
        final String source = exampleParse.getSource();
        final boolean timed = exampleParse.isTimed();
        final int iterations = exampleParse.getIterations();
        final long start = exampleParse.getStartTime();
        final long parse = exampleParse.getParseTime();

        String html;
        if (!ignoredTestCase) {
            html = exampleRenderer.getHtml();
            for (int i = 1; i < iterations; i++) exampleRenderer.getHtml();
        } else {
            html = example.getHtml();
        }
        long render = System.nanoTime();

        boolean embedTimed = TestUtils.EMBED_TIMED.get(exampleRenderer.getOptions());

        String timingInfo = TestUtils.getFormattedTimingInfo(example.getSection(), example.getExampleNumber(), iterations, start, parse, render);

        if (timed || embedTimed) {
            System.out.println(timingInfo);
        }

        String ast = example.getAst() == null ? null : (!ignoredTestCase ? exampleRenderer.getAst() : example.getAst());

        // allow other formats to accumulate
        testCase.addFullSpecExample(exampleRenderer, exampleParse, exampleOptions, ignoredTestCase, html, ast);
        exampleRenderer.finalizeRender();

        if (embedTimed) {
            sb.append(timingInfo);
        }

        // include source so that diff can be used to update spec
        TestUtils.addSpecExample(true, sb, source, html, ast, example.getOptionsSet(), exampleRenderer.includeExampleInfo(), example.getSection(), example.getExampleNumber());
        TestUtils.addSpecExample(false, sbExp, source, example.getHtml(), example.getAst(), example.getOptionsSet(), exampleRenderer.includeExampleInfo(), example.getSection(), example.getExampleNumber());
    }
}
