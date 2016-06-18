package com.vladsch.flexmark.test;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.spec.SpecExample;

public class FullOrigSpecCoreTest extends FullSpecTestCase {
    static final String SPEC_RESOURCE = "/spec.txt";
    static final Parser PARSER = Parser.builder().build();
    // The spec says URL-escaping is optional, but the examples assume that it's enabled.
    static final HtmlRenderer RENDERER = HtmlRenderer.builder().percentEncodeUrls(true).build();

    @Override
    protected SpecExample example() {
        return null;
    }

    @Override
    protected String getSpecResourceName() {
        return SPEC_RESOURCE;
    }

    @Override
    protected Parser parser() {
        return PARSER;
    }

    @Override
    protected boolean includeExampleCoords() {
        return false;
    }

    @Override
    protected HtmlRenderer renderer() {
        return RENDERER;
    }
}
