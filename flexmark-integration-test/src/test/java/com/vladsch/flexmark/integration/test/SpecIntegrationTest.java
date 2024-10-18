package com.vladsch.flexmark.integration.test;

import com.vladsch.flexmark.core.test.util.RendererSpecTest;
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.ext.yaml.front.matter.YamlFrontMatterExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.test.specs.TestSpecLocator;
import com.vladsch.flexmark.test.util.TestUtils;
import com.vladsch.flexmark.test.util.spec.ResourceLocation;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/** Tests that the spec examples still render the same with all extensions enabled. */
@RunWith(Parameterized.class)
public class SpecIntegrationTest extends RendererSpecTest {
  private static final DataHolder OPTIONS =
      new MutableDataSet()
          .set(
              Parser.EXTENSIONS,
              Arrays.asList(
                  StrikethroughExtension.create(),
                  TablesExtension.create(),
                  YamlFrontMatterExtension.create()))
          .set(TestUtils.NO_FILE_EOL, false)
          .set(HtmlRenderer.INDENT_SIZE, 0)
          .set(HtmlRenderer.PERCENT_ENCODE_URLS, true)
          .toImmutable();

  private static final Map<String, String> OVERRIDDEN_EXAMPLES = getOverriddenExamples();

  public SpecIntegrationTest(@NotNull SpecExample example) {
    super(example, null, OPTIONS);
  }

  @Override
  @NotNull
  public SpecExample checkExample(@NotNull SpecExample example) {
    String expectedHtml = OVERRIDDEN_EXAMPLES.get(example.getSource());
    if (expectedHtml != null) {
      return example.withHtml(expectedHtml);
    }
    return example;
  }

  @Parameterized.Parameters(name = "{0}")
  public static List<Object[]> data() {
    return getTestData(ResourceLocation.of(TestSpecLocator.DEFAULT_SPEC_RESOURCE));
  }

  @Override
  protected boolean wantExampleInfo() {
    return false;
  }

  private static Map<String, String> getOverriddenExamples() {
    Map<String, String> m = new HashMap<>();

    // YAML front matter block
    m.put("---\nFoo\n---\nBar\n---\nBaz\n", "<h2>Bar</h2>\n<p>Baz</p>\n");
    m.put("---\n---\n", "");

    return m;
  }
}
