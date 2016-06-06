package com.vladsch.flexmark.test;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.node.Node;
import com.vladsch.flexmark.parser.Parser;

public class FullSpecCoreTest extends FullSpecTestCase {
    private static final Parser PARSER = Parser.builder().build();
    // The spec says URL-escaping is optional, but the examples assume that it's enabled.
    private static final HtmlRenderer RENDERER = HtmlRenderer.builder().percentEncodeUrls(true).build();

    @Override
    protected String getSpecResourceName() {
        return FullSpecTestCase.SPEC_RESOURCE;
    }

    @Override
    protected Node parse(String source) {
        return PARSER.parse(source);
    }

    @Override
    protected String render(Node node) {
        return RENDERER.render(node);
    }
}
