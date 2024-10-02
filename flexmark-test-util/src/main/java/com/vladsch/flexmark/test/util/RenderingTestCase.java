package com.vladsch.flexmark.test.util;

import static org.junit.Assert.assertEquals;

import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.DataKey;
import com.vladsch.flexmark.util.data.SharedDataKeys;
import com.vladsch.flexmark.util.misc.Extension;
import java.util.Collection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.ComparisonFailure;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

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
  public static final DataKey<Collection<Class<? extends Extension>>> UNLOAD_EXTENSIONS =
      TestUtils.UNLOAD_EXTENSIONS;
  public static final DataKey<Collection<Extension>> LOAD_EXTENSIONS = TestUtils.LOAD_EXTENSIONS;
  public static final DataKey<Collection<Extension>> EXTENSIONS = SharedDataKeys.EXTENSIONS;

  @Rule public ExpectedException thrown = ExpectedException.none();

  /**
   * Called when processing full spec test case by DumpSpecReader
   *
   * @param exampleRenderer example renderer
   * @param exampleParse example parse state
   * @param exampleOptions example options
   * @param ignoredTestCase true if ignored example
   * @param html html used for comparison to expected html
   * @param ast ast used for comparison to expected ast
   */
  @Override
  public void addFullSpecExample(
      @NotNull SpecExampleRenderer exampleRenderer,
      @NotNull SpecExampleParse exampleParse,
      DataHolder exampleOptions,
      boolean ignoredTestCase,
      @NotNull String html,
      @Nullable String ast) {}

  /*
   * Convenience functions for those tests that do not have an example
   */
  protected final void assertRendering(@NotNull String source, @NotNull String html) {
    assertRendering(SpecExample.ofCaller(1, this.getClass(), source, html, null));
  }

  protected final void assertRendering(
      @NotNull String source, @NotNull String html, @Nullable String ast) {
    assertRendering(SpecExample.ofCaller(1, this.getClass(), source, html, ast));
  }

  protected final void assertRendering(@NotNull SpecExample specExample) {
    SpecExample example = checkExample(specExample);
    String message = example.getFileUrlWithLineNumber();
    String source = example.getSource();
    String optionsSet = example.getOptionsSet();
    String expectedHtml = example.getHtml();
    String expectedAst = example.getAst();
    DataHolder exampleOptions = TestUtils.getOptions(example, optionsSet, this::options);

    SpecExampleRenderer exampleRenderer = getSpecExampleRenderer(example, exampleOptions);

    SpecExampleParse specExampleParse =
        new SpecExampleParse(exampleRenderer.getOptions(), exampleRenderer, exampleOptions, source);
    boolean timed = specExampleParse.isTimed();
    int iterations = specExampleParse.getIterations();

    String html = exampleRenderer.getHtml();
    for (int i = 1; i < iterations; i++) exampleRenderer.getHtml();
    long render = System.nanoTime();

    String ast = expectedAst == null ? null : exampleRenderer.getAst();
    boolean embedTimed = TestUtils.EMBED_TIMED.get(exampleRenderer.getOptions());

    String formattedTimingInfo =
        TestUtils.getFormattedTimingInfo(
            iterations, specExampleParse.getStartTime(), specExampleParse.getParseTime(), render);

    exampleRenderer.finalizeRender();

    String expected;
    String actual;

    if (example.getSection() != null) {
      StringBuilder outExpected = new StringBuilder();
      if (embedTimed) {
        outExpected.append(formattedTimingInfo);
      }

      TestUtils.addSpecExample(
          true,
          outExpected,
          source,
          expectedHtml,
          expectedAst,
          optionsSet,
          true,
          example.getSection(),
          example.getExampleNumber());
      expected = outExpected.toString();

      StringBuilder outActual = new StringBuilder();
      TestUtils.addSpecExample(
          true,
          outActual,
          source,
          html,
          ast,
          optionsSet,
          true,
          example.getSection(),
          example.getExampleNumber());
      actual = outActual.toString();
    } else {
      if (embedTimed) {
        expected =
            formattedTimingInfo
                + TestUtils.addSpecExample(true, source, expectedHtml, expectedAst, optionsSet);
      } else {
        expected = TestUtils.addSpecExample(true, source, expectedHtml, ast, optionsSet);
      }
      actual = TestUtils.addSpecExample(true, source, html, ast, optionsSet);
    }

    if (exampleOptions != null && TestUtils.FAIL.get(exampleOptions)) {
      thrown.expect(ComparisonFailure.class);
    }

    if (!message.isEmpty()) {
      assertEquals(message, expected, actual);
    } else {
      assertEquals(expected, actual);
    }
  }
}
