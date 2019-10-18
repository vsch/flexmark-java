package com.vladsch.flexmark.test.util;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.test.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CoreSpcUrlRenderingTestCase extends RenderingTestCase {
    private static final DataHolder OPTIONS = new MutableDataSet()
            .set(Parser.SPACE_IN_LINK_URLS, true);

    @Nullable
    @Override
    public DataHolder options(String option) {
        return null;
    }

    @NotNull
    @Override
    public SpecExample getExample() {
        return SpecExample.NULL;
    }

    private Parser parser(@Nullable DataHolder OPTIONS) {
        return Parser.builder(OPTIONS).build();
    }

    private HtmlRenderer renderer(@Nullable DataHolder OPTIONS) {
        return HtmlRenderer.builder(OPTIONS).build();
    }

    @Override
    public @NotNull SpecExampleRenderer getSpecExampleRenderer(@NotNull SpecExample example, @Nullable DataHolder exampleOptions) {
        DataHolder combinedOptions = combineOptions(OPTIONS, exampleOptions);
        return new FlexmarkSpecExampleRenderer(example, combinedOptions, Parser.builder(combinedOptions).build(), HtmlRenderer.builder(combinedOptions).build(), true);
    }
}
