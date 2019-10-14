package com.vladsch.flexmark.test;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CoreSpcUrlRenderingTestCase extends RenderingTestCase {
    private static final DataHolder OPTIONS = new MutableDataSet()
            .set(Parser.SPACE_IN_LINK_URLS, true);

    private static final Parser PARSER = Parser.builder(OPTIONS).build();
    private static final HtmlRenderer RENDERER = HtmlRenderer.builder(OPTIONS).build();

    @Nullable
    @Override
    public DataHolder options(String optionSet) {
        return null;
    }

    @NotNull
    @Override
    public SpecExample getExample() {
        return SpecExample.getNull();
    }

    @Override
    public @NotNull SpecExampleRenderer getSpecExampleRenderer(@Nullable DataHolder exampleOptions) {
        return new FlexmarkSpecExampleRenderer(exampleOptions, PARSER, RENDERER, true);
    }
}
