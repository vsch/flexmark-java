package com.vladsch.flexmark.test;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;

public class CoreSpcUrlRenderingTestCase extends RenderingTestCase {

    private static final DataHolder OPTIONS = new MutableDataSet()
            .set(Parser.SPACE_IN_LINK_URLS, true);

    private static final Parser PARSER = Parser.builder(OPTIONS).build();
    private static final HtmlRenderer RENDERER = HtmlRenderer.builder(OPTIONS).build();

    @Override
    public SpecExample example() {
        return null;
    }

    @Override
    public Parser parser() {
        return PARSER;
    }

    @Override
    public HtmlRenderer renderer() {
        return RENDERER;
    }
}
