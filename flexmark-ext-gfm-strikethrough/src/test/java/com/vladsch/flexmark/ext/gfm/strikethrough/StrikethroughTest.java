package com.vladsch.flexmark.ext.gfm.strikethrough;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.test.FlexmarkSpecExampleRenderer;
import com.vladsch.flexmark.test.RenderingTestCase;
import com.vladsch.flexmark.test.SpecExampleRenderer;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.builder.Extension;
import com.vladsch.flexmark.util.data.DataHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Test;

import java.util.Collections;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class StrikethroughTest extends RenderingTestCase {

    private static final Set<Extension> EXTENSIONS = Collections.singleton(StrikethroughExtension.create());
    private static final Parser PARSER = Parser.builder().extensions(EXTENSIONS).build();
    private static final HtmlRenderer RENDERER = HtmlRenderer.builder().extensions(EXTENSIONS).build();

    @Override
    public @Nullable DataHolder options(String optionSet) {
        return null;
    }

    @NotNull
    @Override
    public SpecExample getExample() {
        return SpecExample.getNull();
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


    @Override
    public @NotNull SpecExampleRenderer getSpecExampleRenderer(@Nullable DataHolder exampleOptions) {
        return new FlexmarkSpecExampleRenderer(exampleOptions, PARSER, RENDERER, true);
    }
}
