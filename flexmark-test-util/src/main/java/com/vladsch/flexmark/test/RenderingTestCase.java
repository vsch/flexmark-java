package com.vladsch.flexmark.test;

import com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.spec.UrlString;
import com.vladsch.flexmark.util.data.DataHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.ComparisonFailure;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public abstract class RenderingTestCase implements SpecExampleProcessor {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @NotNull
    public abstract SpecExample getExample();

    public void testCase(SpecExampleRenderer exampleRenderer, DataHolder options) {

    }

    public void addSpecExample(@NotNull SpecExample example, @NotNull SpecExampleRenderer exampleRenderer, DataHolder options, boolean ignoredTestCase, @NotNull String renderedHtml, @Nullable String renderedAst) {

    }

    final protected void assertRendering(UrlString fileUrl, String source, String expectedHtml) {
        assertRendering(fileUrl, source, expectedHtml, null, null);
    }

    final protected void assertRendering(String source, String expectedHtml) {
        assertRendering(null, source, expectedHtml, null, null);
    }

    final protected void assertRendering(UrlString fileUrl, String source, String expectedHtml, String optionsSet) {
        assertRendering(fileUrl, source, expectedHtml, null, optionsSet);
    }

    protected void assertRendering(UrlString fileUrl, String source, String expectedHtml, String expectedAst, String optionsSet) {
        SpecExample example = getExample();
        DataHolder options = optionsSet == null ? null : TestUtils.getOptions(example, optionsSet, this::options);

        SpecExampleRenderer exampleRenderer = getSpecExampleRenderer(options);

        SpecExampleParse specExampleParse = new SpecExampleParse(options, exampleRenderer, exampleRenderer.getOptions(), source);
        String parseSource = specExampleParse.getSource();
        boolean timed = specExampleParse.isTimed();
        int iterations = specExampleParse.getIterations();

        String html = exampleRenderer.renderHtml();
        for (int i = 1; i < iterations; i++) exampleRenderer.renderHtml();
        long render = System.nanoTime();

        String ast = expectedAst == null ? "" : exampleRenderer.getAst();
        boolean embedTimed = TestUtils.EMBED_TIMED.getFrom(exampleRenderer.getOptions());

        String formattedTimingInfo = TestUtils.getFormattedTimingInfo(iterations, specExampleParse.getStartTime(), specExampleParse.getParseTime(), render);
        if (timed || embedTimed) {
            System.out.print(formattedTimingInfo);
        }

        testCase(exampleRenderer, options);

        String expected;
        String actual;

        if (example.getSection() != null) {
            StringBuilder outExpected = new StringBuilder();
            if (embedTimed) {
                outExpected.append(formattedTimingInfo);
            }

            TestUtils.addSpecExample(outExpected, source, expectedHtml, expectedAst == null ? "" : expectedAst, optionsSet, true, example.getSection(), example.getExampleNumber());
            expected = outExpected.toString();

            StringBuilder outActual = new StringBuilder();
            TestUtils.addSpecExample(outActual, source, html, ast, optionsSet, true, example.getSection(), example.getExampleNumber());
            actual = outActual.toString();
        } else {
            if (embedTimed) {
                expected = formattedTimingInfo +
                        TestUtils.addSpecExample(source, expectedHtml, expectedAst == null ? "" : expectedAst, optionsSet);
            } else {
                expected = TestUtils.addSpecExample(source, expectedHtml, ast, optionsSet);
            }
            actual = TestUtils.addSpecExample(source, html, ast, optionsSet);
        }

        if (options != null && options.get(TestUtils.FAIL)) {
            thrown.expect(ComparisonFailure.class);
        }

        if (fileUrl != null) {
            assertEquals(fileUrl.toString(), expected, actual);
        } else {
            assertEquals(expected, actual);
        }
    }
}
