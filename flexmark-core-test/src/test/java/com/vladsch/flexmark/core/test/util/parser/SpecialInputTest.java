package com.vladsch.flexmark.core.test.util.parser;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.test.util.*;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.DataSet;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Test;

final public class SpecialInputTest extends RenderingTestCase {
    final private static DataHolder OPTIONS = new MutableDataSet()
            .set(TestUtils.NO_FILE_EOL, false)
            .toImmutable();

    @Test
    public void empty() {
        assertRendering("", "");
    }

    @Test
    public void nullCharacterShouldBeReplaced() {
        assertRendering("foo\0bar", "<p>foo\uFFFDbar</p>\n");
    }

    @Test
    public void nullCharacterEntityShouldBeReplaced() {
        assertRendering("foo&#0;bar", "<p>foo\uFFFDbar</p>\n");
    }

    @Test
    public void crLfAsLineSeparatorShouldBeParsed() {
        assertRendering("foo\r\nbar", "<p>foo\nbar</p>\n");
    }

    @Test
    public void crLfAtEndShouldBeParsed() {
        assertRendering("foo\r\n", "<p>foo</p>\n");
    }

    @Test
    public void mixedLineSeparators() {
        assertRendering("- a\n- b\r- c\r\n- d", "<ul>\n<li>a</li>\n<li>b</li>\n<li>c</li>\n<li>d</li>\n</ul>\n");
        assertRendering("a\n\nb\r\rc\r\n\r\nd\n\re", "<p>a</p>\n<p>b</p>\n<p>c</p>\n<p>d</p>\n<p>e</p>\n");
    }

    @Test
    public void surrogatePair() {
        assertRendering("surrogate pair: \uD834\uDD1E", "<p>surrogate pair: \uD834\uDD1E</p>\n");
    }

    @Test
    public void surrogatePairInLinkDestination() {
        assertRendering("[title](\uD834\uDD1E)", "<p><a href=\"\uD834\uDD1E\">title</a></p>\n");
    }

    @Test
    public void indentedCodeBlockWithMixedTabsAndSpaces() {
        assertRendering("    foo\n\tbar", "<pre><code>foo\nbar\n</code></pre>\n");
    }

    @Test
    public void tightListInBlockQuote() {
        assertRendering("> *\n> * a", "<blockquote>\n<ul>\n<li></li>\n<li>a</li>\n</ul>\n</blockquote>\n");
    }

    @Test
    public void looseListInBlockQuote() {
        // Second line in block quote is considered blank for purpose of loose list
        assertRendering("> *\n>\n> * a", "<blockquote>\n<ul>\n<li></li>\n<li>\n<p>a</p>\n</li>\n</ul>\n</blockquote>\n");
    }

    @Test
    public void lineWithOnlySpacesAfterListBullet() {
        assertRendering("-  \n  \n  foo\n", "<ul>\n<li></li>\n</ul>\n<p>foo</p>\n");
    }

    @Test
    public void listWithTwoSpacesForFirstBullet() {
        // We have two spaces after the bullet, but no content. With content, the next line would be required
        assertRendering("*  \n  foo\n", "<ul>\n<li>foo</li>\n</ul>\n");
    }

    @Test
    public void orderedListMarkerOnly() {
        assertRendering("2.", "<ol start=\"2\">\n<li></li>\n</ol>\n");
    }

    @Test
    public void columnIsInTabOnPreviousLine() {
        assertRendering(
                "- foo\n\n\tbar\n\n# baz\n", "<ul>\n<li>\n<p>foo</p>\n<p>bar</p>\n</li>\n</ul>\n<h1>baz</h1>\n");
        assertRendering(
                "- foo\n\n\tbar\n# baz\n", "<ul>\n<li>\n<p>foo</p>\n<p>bar</p>\n</li>\n</ul>\n<h1>baz</h1>\n");
    }

    @Test
    public void linkLabelWithBracket() {
        assertRendering("[a[b]\n\n[a[b]: /", "<p>[a[b]</p>\n<p>[a[b]: /</p>\n");
        assertRendering("[a]b]\n\n[a]b]: /", "<p>[a]b]</p>\n<p>[a]b]: /</p>\n");
        assertRendering("[a[b]]\n\n[a[b]]: /", "<p>[a[b]]</p>\n<p>[a[b]]: /</p>\n");
    }

    @Test
    public void linkLabelLength() {
        String label1 = Strings.repeat("a", 999);
        assertRendering("[foo][" + label1 + "]\n\n[" + label1 + "]: /", "<p><a href=\"/\">foo</a></p>\n");
        assertRendering(
                "[foo][x" + label1 + "]\n\n[x" + label1 + "]: /", "<p>[foo][x" + label1 + "]</p>\n<p>[x" + label1 + "]: /</p>\n");
        assertRendering(
                "[foo][\n" + label1 + "]\n\n[\n" + label1 + "]: /", "<p>[foo][\n" + label1 + "]</p>\n<p>[\n" + label1 + "]: /</p>\n");

        String label2 = Strings.repeat("a\n", 499);
        assertRendering("[foo][" + label2 + "]\n\n[" + label2 + "]: /", "<p><a href=\"/\">foo</a></p>\n");
        assertRendering(
                "[foo][12" + label2 + "]\n\n[12" + label2 + "]: /", "<p>[foo][12" + label2 + "]</p>\n<p>[12" + label2 + "]: /</p>\n");
    }

    @Test
    public void manyUnderscores() {
        assertRendering(Strings.repeat("_", 5000), "<hr />");
    }

    @Nullable
    @Override
    public DataHolder options(@NotNull String option) {
        return null;
    }

    @Override
    public @NotNull SpecExampleRenderer getSpecExampleRenderer(@NotNull SpecExample example, @Nullable DataHolder exampleOptions) {
        DataHolder combineOptions = DataSet.aggregate(OPTIONS, exampleOptions);
        return new FlexmarkSpecExampleRenderer(example, combineOptions, Parser.builder(combineOptions).build(), HtmlRenderer.builder(combineOptions).build(), true);
    }
}
