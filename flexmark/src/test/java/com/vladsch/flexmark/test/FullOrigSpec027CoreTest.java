package com.vladsch.flexmark.test;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.ParserEmulationProfile;
import com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;

public class FullOrigSpec027CoreTest extends FullSpecTestCase {
    static final String SPEC_RESOURCE = "/spec.0.27.txt";
    static final DataHolder OPTIONS = ParserEmulationProfile.COMMONMARK_0_27.getProfileOptions();
    static final Parser PARSER = Parser.builder(OPTIONS).build();
    // The spec says URL-escaping is optional, but the examples assume that it's enabled.
    static final HtmlRenderer RENDERER = HtmlRenderer.builder(OPTIONS).percentEncodeUrls(true).build();

    @Override
    public SpecExample example() {
        return null;
    }

    @Override
    public String getSpecResourceName() {
        return SPEC_RESOURCE;
    }

    @Override
    public Parser parser() {
        return PARSER;
    }

    @Override
    public boolean includeExampleCoords() {
        return false;
    }

    @Override
    public HtmlRenderer renderer() {
        return RENDERER;
    }
}
