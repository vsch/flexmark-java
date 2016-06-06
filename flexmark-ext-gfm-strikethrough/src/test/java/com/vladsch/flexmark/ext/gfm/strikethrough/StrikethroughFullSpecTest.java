package com.vladsch.flexmark.ext.gfm.strikethrough;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.node.Node;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.test.FullSpecTestCase;
import org.junit.Test;

import java.util.Collections;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class StrikethroughFullSpecTest extends FullSpecTestCase {
    static final String SPEC_RESOURCE = "/ext_gfm_strikethrough_ast_spec.txt";
    private static final Set<Extension> EXTENSIONS = Collections.singleton(StrikethroughExtension.create());
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
        Node document = PARSER.parse("~~foo~~");
        Strikethrough strikethrough = (Strikethrough) document.getFirstChild().getFirstChild();
        assertEquals("~~", strikethrough.getOpeningMarker().toString());
        assertEquals("~~", strikethrough.getClosingMarker().toString());
    }
}
