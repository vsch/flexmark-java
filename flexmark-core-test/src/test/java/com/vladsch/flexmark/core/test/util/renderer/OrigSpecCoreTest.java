package com.vladsch.flexmark.core.test.util.renderer;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.test.util.FlexmarkSpecExampleRenderer;
import com.vladsch.flexmark.test.util.FullSpecTestCase;
import com.vladsch.flexmark.test.util.SpecExampleRenderer;
import com.vladsch.flexmark.test.util.TestUtils;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.DataSet;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class OrigSpecCoreTest extends FullSpecTestCase {
    final private static DataHolder OPTIONS = new MutableDataSet()
            .set(HtmlRenderer.PERCENT_ENCODE_URLS, true)
            .set(TestUtils.NO_FILE_EOL, false)
            .toImmutable();

    final private @Nullable DataHolder myDefaultOptions;

    public OrigSpecCoreTest(@Nullable DataHolder defaultOptions) {
        myDefaultOptions = DataSet.aggregate(OPTIONS, defaultOptions);
    }

    @Override
    final public @Nullable DataHolder options(@NotNull String option) {
        return null;
    }

    @Override
    final public @NotNull SpecExampleRenderer getSpecExampleRenderer(@NotNull SpecExample example, @Nullable DataHolder exampleOptions) {
        DataHolder combineOptions = DataSet.aggregate(myDefaultOptions, exampleOptions);
        return new FlexmarkSpecExampleRenderer(example, combineOptions, Parser.builder(combineOptions).build(), HtmlRenderer.builder(combineOptions).build(), false);
    }
}
