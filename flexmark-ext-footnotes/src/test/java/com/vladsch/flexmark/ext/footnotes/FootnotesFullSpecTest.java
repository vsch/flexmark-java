package com.vladsch.flexmark.ext.footnotes;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.node.Node;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.test.FullSpecTestCase;
import org.junit.Test;

import java.util.Collections;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class FootnotesFullSpecTest extends FullSpecTestCase {
    static final String SPEC_RESOURCE = "/ext_footnotes_ast_spec.txt";
    private static final Set<Extension> EXTENSIONS = Collections.singleton(FootnoteExtension.create());
    static final HtmlRenderer RENDERER = HtmlRenderer.builder().extensions(EXTENSIONS).build();
    static final Parser PARSER = Parser.builder().extensions(EXTENSIONS).build();

    @Override
    protected String getSpecResourceName() {
        return SPEC_RESOURCE;
    }

    @Override
    protected Node parse(String source) {
        return PARSER.parse(source);
    }

    @Override
    protected String render(Node node) {
        return RENDERER.render(node);
    }

    @Test
    public void delimited() {
        Node document = PARSER.parse(":warning:");
        Footnote emoji = (Footnote) document.getFirstChild().getFirstChild();
        assertEquals(":", emoji.getOpeningMarker().toString());
        assertEquals(":", emoji.getClosingMarker().toString());
    }
}
