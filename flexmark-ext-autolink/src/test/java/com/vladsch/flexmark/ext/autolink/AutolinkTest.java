package com.vladsch.flexmark.ext.autolink;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.test.util.FlexmarkSpecExampleRenderer;
import com.vladsch.flexmark.test.util.RenderingTestCase;
import com.vladsch.flexmark.test.util.SpecExampleRenderer;
import com.vladsch.flexmark.test.util.TestUtils;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.DataSet;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Test;

import java.util.Collections;

public class AutolinkTest extends RenderingTestCase {
    final private static DataHolder OPTIONS = new MutableDataSet()
            .set(TestUtils.NO_FILE_EOL, false)
            .set(Parser.EXTENSIONS, Collections.singleton(AutolinkExtension.create()))
            .toImmutable();

    final private static @NotNull Parser PARSER = Parser.builder(OPTIONS).build();
    final private static @NotNull HtmlRenderer RENDERER = HtmlRenderer.builder(OPTIONS).build();

    @Override
    public @NotNull SpecExampleRenderer getSpecExampleRenderer(@NotNull SpecExample example, @Nullable DataHolder exampleOptions) {
        DataHolder combinedOptions = DataSet.aggregate(OPTIONS, exampleOptions);
        return new FlexmarkSpecExampleRenderer(example, combinedOptions, PARSER, RENDERER, true);
    }

    @Override
    public @Nullable DataHolder options(@NotNull String option) {
        return null;
    }

    @Test
    public void oneTextNode() {
        assertRendering(
                "foo http://one.org/ bar http://two.org/", "<p>foo <a href=\"http://one.org/\">http://one.org/</a> bar <a href=\"http://two.org/\">http://two.org/</a></p>\n");
    }

    @Test
    public void textNodeAndOthers() {
        assertRendering(
                "foo http://one.org/ bar `code` baz http://two.org/", "<p>foo <a href=\"http://one.org/\">http://one.org/</a> bar <code>code</code> baz <a href=\"http://two.org/\">http://two.org/</a></p>\n");
    }

    @Test
    public void tricky() {
        assertRendering(
                "http://example.com/one. Example 2 (see http://example.com/two). Example 3: http://example.com/foo_(bar)", "<p><a href=\"http://example.com/one\">http://example.com/one</a>. " +
                        "Example 2 (see <a href=\"http://example.com/two\">http://example.com/two</a>). " +
                        "Example 3: <a href=\"http://example.com/foo_(bar)\">http://example.com/foo_(bar)</a></p>\n");
    }

    @Test
    public void emailUsesMailto() {
        assertRendering(
                "foo@example.com", "<p><a href=\"mailto:foo@example.com\">foo@example.com</a></p>\n");
    }

    @Test
    public void emailWithTldNotLinked() {
        assertRendering(
                "foo@com", "<p>foo@com</p>\n");
    }

    @Test
    public void dontLinkTextWithinLinks() {
        assertRendering(
                "<http://example.com>", "<p><a href=\"http://example.com\">http://example.com</a></p>\n");
    }
}
