package com.vladsch.flexmark.test;

import com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.util.SharedDataKeys;
import com.vladsch.flexmark.util.builder.BuilderBase;
import com.vladsch.flexmark.util.builder.Extension;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.DataKey;
import com.vladsch.flexmark.util.data.DataSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.ComparisonFailure;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;

import static org.junit.Assert.assertEquals;

public abstract class RenderingTestCase implements SpecExampleProcessor {
    public static final DataKey<Boolean> IGNORE = TestUtils.IGNORE;
    public static final DataKey<Boolean> FAIL = TestUtils.FAIL;
    public static final DataKey<Boolean> NO_FILE_EOL = TestUtils.NO_FILE_EOL;
    public static final DataKey<Integer> TIMED_ITERATIONS = TestUtils.TIMED_ITERATIONS;
    public static final DataKey<Boolean> EMBED_TIMED = TestUtils.EMBED_TIMED;
    public static final DataKey<Boolean> TIMED = TestUtils.TIMED;
    public static final DataKey<String> INCLUDED_DOCUMENT = TestUtils.INCLUDED_DOCUMENT;
    public static final DataKey<String> SOURCE_PREFIX = TestUtils.SOURCE_PREFIX;
    public static final DataKey<String> SOURCE_SUFFIX = TestUtils.SOURCE_SUFFIX;
    public static final DataKey<String> SOURCE_INDENT = TestUtils.SOURCE_INDENT;

    public static final DataHolder NO_FILE_EOL_FALSE = TestUtils.NO_FILE_EOL_FALSE;
    public static final String DEFAULT_SPEC_RESOURCE = TestUtils.DEFAULT_SPEC_RESOURCE;
    public static final String DEFAULT_URL_PREFIX = TestUtils.DEFAULT_URL_PREFIX;
    public static final DataKey<Collection<Extension>> UNLOAD_EXTENSIONS = TestUtils.UNLOAD_EXTENSIONS;
    public static final DataKey<Collection<Extension>> LOAD_EXTENSIONS = TestUtils.LOAD_EXTENSIONS;
    public static final DataKey<Collection<Extension>> EXTENSIONS = SharedDataKeys.EXTENSIONS;

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
        // here we handle load and unload extension directives to convert them to adding/removing extensions from EXTENSIONS
        if (other != null && overrides != null) {
            return new DataSet(resolveLoadUnload(other), resolveLoadUnload(overrides));
        } else if (other != null) {
            return resolveLoadUnload(other);
        } else {
            return resolveLoadUnload(overrides);
        }
    }

    private DataHolder resolveLoadUnload(@Nullable DataHolder options) {
        if (options != null && (options.contains(LOAD_EXTENSIONS) || options.contains(UNLOAD_EXTENSIONS))) {
            Collection<Extension> extensions = options.get(EXTENSIONS);
            Collection<Extension> loadExtensions = options.get(LOAD_EXTENSIONS);
            Collection<Extension> unloadExtensions = options.get(UNLOAD_EXTENSIONS);
            if (!loadExtensions.isEmpty() || !unloadExtensions.isEmpty() && !extensions.isEmpty()) {
                LinkedHashSet<Extension> resolvedExtensions = new LinkedHashSet<>(extensions);
                resolvedExtensions.addAll(loadExtensions);
                resolvedExtensions.removeAll(unloadExtensions);
                return options.toMutable()
                        .remove(LOAD_EXTENSIONS)
                        .remove(UNLOAD_EXTENSIONS)
                        .set(EXTENSIONS, new ArrayList<>(resolvedExtensions))
                        .toImmutable();
            } else {
                // just remove the offending keys
                return options.toMutable()
                        .remove(LOAD_EXTENSIONS)
                        .remove(UNLOAD_EXTENSIONS)
                        .toImmutable();
            }
        }
        return options;
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

        String html = exampleRenderer.getHtml();
        for (int i = 1; i < iterations; i++) exampleRenderer.getHtml();
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
