package com.vladsch.flexmark.test;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.ParserEmulationProfile;
import com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FullOrigSpec028CoreTest extends FullSpecTestCase {
    static final String SPEC_RESOURCE = "/spec.0.28.txt";
    static final DataHolder OPTIONS =
            ParserEmulationProfile.COMMONMARK_0_28.getProfileOptions()
                    .set(TestUtils.NO_FILE_EOL, false).toImmutable();

    static final Parser PARSER = Parser.builder(OPTIONS).build();
    // The spec says URL-escaping is optional, but the examples assume that it's enabled.
    static final HtmlRenderer RENDERER = HtmlRenderer.builder(OPTIONS).percentEncodeUrls(true).build();

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

    @NotNull
    @Override
    public SpecExample getExample() {
        return SpecExample.getNull();
    }

    @Override
    public @Nullable DataHolder options(String optionSet) {
        return OPTIONS;
    }

    @Override
    public @NotNull SpecExampleRenderer getSpecExampleRenderer(@NotNull SpecExample example, @Nullable DataHolder exampleOptions) {
        DataHolder combinedOptions = combineOptions(OPTIONS, exampleOptions);
        return new FlexmarkSpecExampleRenderer(example, combinedOptions, PARSER.withOptions(combinedOptions), RENDERER.withOptions(combinedOptions), false);
    }
}
