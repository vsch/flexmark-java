package com.vladsch.flexmark.test;

import com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.DataSet;
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

    /**
     * Override if combining option by overwriting corresponding keys is not sufficient
     *
     * @param other     options (set first), can be null
     * @param overrides options (set second) can be null
     * @return combined options, default implementation simply overwrites values of corresponding keys in overrides
     */
    @Nullable
    @Override
    public DataHolder combineOptions(@Nullable DataHolder other, @Nullable DataHolder overrides) {
        return other != null && overrides != null ? new DataSet(other, overrides) : other != null ? other : overrides;
    }

    /**
     * Called after processing individual test case
     *
     * @param exampleRenderer renderer used
     * @param exampleParse    parse information
     * @param exampleOptions  example options
     */
    public void testCase(SpecExampleRenderer exampleRenderer, SpecExampleParse exampleParse, DataHolder exampleOptions) {

    }

    public void addSpecExample(@NotNull SpecExampleRenderer exampleRenderer, @NotNull SpecExampleParse exampleParse, DataHolder exampleOptions, boolean ignoredTestCase, @NotNull String html, @Nullable String ast) {

    }

    final protected void assertRendering(String fileUrl, String source, String expectedHtml) {
        assertRendering(fileUrl, source, expectedHtml, null, null);
    }

    final protected void assertRendering(String source, String expectedHtml) {
        assertRendering(null, source, expectedHtml, null, null);
    }

    final protected void assertRendering(String fileUrl, String source, String expectedHtml, String optionsSet) {
        assertRendering(fileUrl, source, expectedHtml, null, optionsSet);
    }

    protected void assertRendering(@Nullable String message, @NotNull String source, @NotNull String expectedHtml, @Nullable String expectedAst, @Nullable String optionsSet) {
        SpecExample example = getExample();
        DataHolder exampleOptions = TestUtils.getOptions(example, optionsSet, this::options, this::combineOptions);

        SpecExampleRenderer exampleRenderer = getSpecExampleRenderer(example, exampleOptions);

        SpecExampleParse specExampleParse = new SpecExampleParse(exampleRenderer.getOptions(), exampleRenderer, exampleOptions, source);
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

        testCase(exampleRenderer, specExampleParse, exampleOptions);
        exampleRenderer.finalizeRender();

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

        if (exampleOptions != null && exampleOptions.get(TestUtils.FAIL)) {
            thrown.expect(ComparisonFailure.class);
        }

        if (message != null) {
            assertEquals(message, expected, actual);
        } else {
            assertEquals(expected, actual);
        }
    }
}
