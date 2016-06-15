package com.vladsch.flexmark.ext.footnotes;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.test.FullSpecTestCase;

import java.util.Collections;
import java.util.Set;

public class FootnotesFullSpecTest extends FullSpecTestCase {
    static final String SPEC_RESOURCE = "/ext_footnotes_ast_spec.txt";
    private static final Set<Extension> EXTENSIONS = Collections.singleton(FootnoteExtension.create());
    static final HtmlRenderer RENDERER = HtmlRenderer.builder().indentSize(2).extensions(EXTENSIONS).build();
    static final Parser PARSER = Parser.builder().extensions(EXTENSIONS).build();

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
