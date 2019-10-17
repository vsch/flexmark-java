package com.vladsch.flexmark.test;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.ParserEmulationProfile;
import com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.runners.Parameterized;

import java.util.List;

public class ComboMultiMarkdownCompatibilitySpecTest extends ComboExtraSpecTest {
    private static final String SPEC_RESOURCE = "/multi_markdown_compatibility_spec.md";
    private static final DataHolder OPTIONS = new MutableDataSet()
            .setFrom(ParserEmulationProfile.MULTI_MARKDOWN)
            .set(HtmlRenderer.INDENT_SIZE, 4)
            .set(HtmlRenderer.PERCENT_ENCODE_URLS, true);

    public ComboMultiMarkdownCompatibilitySpecTest(SpecExample example) {
        super(example);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return getTestData( SPEC_RESOURCE);
    }

    @Nullable
    @Override
    public DataHolder options(String option) {
        return null;
    }

    @NotNull
    @Override
    public String getSpecResourceName() {
        return SPEC_RESOURCE;
    }
    @Override
    public @NotNull SpecExampleRenderer getSpecExampleRenderer(@NotNull SpecExample example, @Nullable DataHolder exampleOptions) {
        DataHolder combinedOptions = combineOptions(OPTIONS, exampleOptions);
        return new FlexmarkSpecExampleRenderer(example, combinedOptions, Parser.builder(combinedOptions).build(), HtmlRenderer.builder(combinedOptions).build(), true);
}
}
