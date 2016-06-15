package com.vladsch.flexmark.test;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.internal.util.KeepType;
import com.vladsch.flexmark.internal.util.MutableDataHolder;
import com.vladsch.flexmark.internal.util.MutableDataSet;
import com.vladsch.flexmark.parser.Parser;

public class FullKeepLastSpecTest extends FullSpecTestCase {
    static final String SPEC_RESOURCE = "/keep_last_ast_spec.txt";

    // The spec says URL-escaping is optional, but the examples assume that it's enabled.
    static final MutableDataHolder OPTIONS = new MutableDataSet()
            .set(Parser.REFERENCES_KEEP, KeepType.LAST)
            .set(HtmlRenderer.INDENT_SIZE, 2)
            .set(HtmlRenderer.PERCENT_ENCODE_URLS, true);

    static final Parser PARSER = Parser.builder(OPTIONS).build();
    static final HtmlRenderer RENDERER = HtmlRenderer.builder(OPTIONS).build();

    @Override
    protected String getSpecResourceName() {
        return SPEC_RESOURCE;
    }

    @Override
    protected Parser parser() {
        return PARSER;
    }

    @Override
    protected HtmlRenderer renderer() {
        return RENDERER;
    }
}
