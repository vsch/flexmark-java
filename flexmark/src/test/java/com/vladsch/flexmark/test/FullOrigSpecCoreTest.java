package com.vladsch.flexmark.test;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FullOrigSpecCoreTest extends FullSpecTestCase {
    static final String SPEC_RESOURCE = "/spec.txt";
    static final DataHolder OPTIONS = new MutableDataSet()
            .set(HtmlRenderer.PERCENT_ENCODE_URLS, true)
            .set(TestUtils.NO_FILE_EOL, false)
            .toImmutable();

    @NotNull
    @Override
    public SpecExample getExample() {
        return SpecExample.NULL;
    }

    @Override
    public @Nullable DataHolder options(String option) {
        return null;
    }

    @NotNull
    @Override
    public String getSpecResourceName() {
        return SPEC_RESOURCE;
    }

    @NotNull
    public Parser parser(@Nullable DataHolder OPTIONS) {
        return Parser.builder(OPTIONS).build();
    }

    @NotNull
    public HtmlRenderer renderer(@Nullable DataHolder OPTIONS) {
        return HtmlRenderer.builder(OPTIONS).build();
    }

    @Override
    public @NotNull SpecExampleRenderer getSpecExampleRenderer(@NotNull SpecExample example, @Nullable DataHolder exampleOptions) {
        DataHolder combinedOptions = combineOptions(OPTIONS, exampleOptions);
        return new FlexmarkSpecExampleRenderer(example, combinedOptions, parser(combinedOptions), renderer(combinedOptions), false);
    }
}
