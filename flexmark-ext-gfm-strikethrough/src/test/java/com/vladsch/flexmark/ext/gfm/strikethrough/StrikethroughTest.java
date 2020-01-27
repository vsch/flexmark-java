package com.vladsch.flexmark.ext.gfm.strikethrough;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.test.util.FlexmarkSpecExampleRenderer;
import com.vladsch.flexmark.test.util.RenderingTestCase;
import com.vladsch.flexmark.test.util.SpecExampleRenderer;
import com.vladsch.flexmark.test.util.TestUtils;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.DataSet;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class StrikethroughTest extends RenderingTestCase {
    final private static DataHolder OPTIONS = new MutableDataSet()
            .set(TestUtils.NO_FILE_EOL, false)
            .set(Parser.EXTENSIONS, Collections.singleton(StrikethroughExtension.create()))
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
    public void oneTildeIsNotEnough() {
        assertRendering("~foo~", "<p>~foo~</p>\n");
    }

    @Test
    public void twoTildesYay() {
        assertRendering("~~foo~~", "<p><del>foo</del></p>\n");
    }

    @Test
    public void fourTildesNope() {
        assertRendering("foo ~~~~", "<p>foo ~~~~</p>\n");
    }

    @Test
    public void unmatched() {
        assertRendering("~~foo", "<p>~~foo</p>\n");
        assertRendering("foo~~", "<p>foo~~</p>\n");
    }

    @Test
    public void threeInnerThree() {
        assertRendering("~~~foo~~~", "<p>~<del>foo</del>~</p>\n");
    }

    @Test
    public void twoInnerThree() {
        assertRendering("~~foo~~~", "<p><del>foo</del>~</p>\n");
    }

    @Test
    public void tildesInside() {
        assertRendering("~~foo~bar~~", "<p><del>foo~bar</del></p>\n");
        assertRendering("~~foo~~bar~~", "<p><del>foo</del>bar~~</p>\n");
        assertRendering("~~foo~~~bar~~", "<p><del>foo</del>~bar~~</p>\n");
        assertRendering("~~foo~~~~bar~~", "<p><del>foo</del><del>bar</del></p>\n");
        assertRendering("~~foo~~~~~bar~~", "<p><del>foo</del>~<del>bar</del></p>\n");
        assertRendering("~~foo~~~~~~bar~~", "<p><del>foo</del>~~<del>bar</del></p>\n");
        assertRendering("~~foo~~~~~~~bar~~", "<p><del>foo</del>~~~<del>bar</del></p>\n");
    }

    @Test
    public void strikethroughWholeParagraphWithOtherDelimiters() {
        assertRendering("~~Paragraph with *emphasis* and __strong emphasis__~~", "<p><del>Paragraph with <em>emphasis</em> and <strong>strong emphasis</strong></del></p>\n");
    }

    @Test
    public void insideBlockQuote() {
        assertRendering("> strike ~~that~~", "<blockquote>\n<p>strike <del>that</del></p>\n</blockquote>\n");
    }

    @Test
    public void delimited() {
        Node document = PARSER.parse("~~foo~~");
        Strikethrough strikethrough = (Strikethrough) document.getFirstChild().getFirstChild();
        assertEquals("~~", strikethrough.getOpeningMarker().toString());
        assertEquals("~~", strikethrough.getClosingMarker().toString());
    }
}
