package com.vladsch.flexmark.test;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FullOrigSpecCoreTest extends FullSpecTestCase {
    static final String SPEC_RESOURCE = "/spec.txt";
    static final Parser PARSER = Parser.builder().build();
    // The spec says URL-escaping is optional, but the examples assume that it's enabled.
    static final HtmlRenderer RENDERER = HtmlRenderer.builder().percentEncodeUrls(true).build();

    @NotNull
    @Override
    public SpecExample getExample() {
        return SpecExample.getNull();
    }

    @Override
    public @Nullable DataHolder options(String optionSet) {
        return null;
    }

    @NotNull
    @Override
    public String getSpecResourceName() {
        return SPEC_RESOURCE;
    }

    @NotNull
    public Parser parser() {
        return PARSER;
    }

    @NotNull
    public HtmlRenderer renderer() {
        return RENDERER;
    }

    @Override
    public @NotNull SpecExampleRenderer getSpecExampleRenderer(@Nullable DataHolder exampleOptions) {
        return new FlexmarkSpecExampleRenderer(exampleOptions, parser(),renderer(), false);
    }
}
