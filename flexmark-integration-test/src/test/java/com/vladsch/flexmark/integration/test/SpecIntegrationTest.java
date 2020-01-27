package com.vladsch.flexmark.integration.test;

import com.vladsch.flexmark.core.test.util.RendererSpecTest;
import com.vladsch.flexmark.ext.autolink.AutolinkExtension;
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
import org.jetbrains.annotations.NotNull;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Tests that the spec examples still render the same with all extensions enabled.
 */

@RunWith(Parameterized.class)
public class SpecIntegrationTest extends RendererSpecTest {
    final public static @NotNull ResourceLocation RESOURCE_LOCATION = ResourceLocation.of(TestSpecLocator.DEFAULT_SPEC_RESOURCE);
    final private static DataHolder OPTIONS = new MutableDataSet()
            .set(Parser.EXTENSIONS, Arrays.asList(
                    AutolinkExtension.create(),
                    StrikethroughExtension.create(),
                    TablesExtension.create(),
                    YamlFrontMatterExtension.create())
            )
            .set(TestUtils.NO_FILE_EOL, false)
            .set(HtmlRenderer.INDENT_SIZE, 0)
            .set(HtmlRenderer.PERCENT_ENCODE_URLS, true)
            .toImmutable();

    final private static Map<String, String> OVERRIDDEN_EXAMPLES = getOverriddenExamples();

    public SpecIntegrationTest(@NotNull SpecExample example) {
        super(example, null, OPTIONS);
    }

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
        return getTestData(RESOURCE_LOCATION);
    }

    @Override
    protected boolean wantExampleInfo() {
        return false;
    }

    private static Map<String, String> getOverriddenExamples() {
        Map<String, String> m = new HashMap<>();

        // Not a spec autolink because of space, but the resulting text contains a valid URL
        m.put("<http://foo.bar/baz bim>\n", "<p>&lt;<a href=\"http://foo.bar/baz\">http://foo.bar/baz</a> bim&gt;</p>\n");

        // Not a spec autolink, but the resulting text contains a valid email
        m.put("<foo\\+@bar.example.com>\n", "<p>&lt;<a href=\"mailto:foo+@bar.example.com\">foo+@bar.example.com</a>&gt;</p>\n");

        // Not a spec autolink because of unknown scheme, but autolink extension doesn't limit schemes
        m.put("<heck://bing.bong>\n", "<p>&lt;<a href=\"heck://bing.bong%3E\">heck://bing.bong&gt;</a></p>\n");

        // Not a spec autolink because of spaces, but autolink extension doesn't limit schemes
        m.put("< http://foo.bar >\n", "<p>&lt; <a href=\"http://foo.bar\">http://foo.bar</a> &gt;</p>\n");

        // Plain autolink
        m.put("http://example.com\n", "<p><a href=\"http://example.com\">http://example.com</a></p>\n");

        // Plain autolink
        m.put("foo@bar.example.com\n", "<p><a href=\"mailto:foo@bar.example.com\">foo@bar.example.com</a></p>\n");

        // YAML front matter block
        m.put("---\nFoo\n---\nBar\n---\nBaz\n", "<h2>Bar</h2>\n<p>Baz</p>\n");
        m.put("---\n---\n", "");

        return m;
    }
}
