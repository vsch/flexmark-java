package com.vladsch.flexmark.test.util;

import com.vladsch.flexmark.test.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface SpecExampleProcessor {
    /**
     * Customize options for an example
     *
     * @param option name of the options set to use
     * @return options or null to use default
     */
    @Nullable DataHolder options(String option);

    @NotNull SpecExample getExample();

    // return combined options, some may need more than just overwrites, as in case of consumers that set some instance values
    @Nullable DataHolder combineOptions(@Nullable DataHolder other, @Nullable DataHolder overrides);

    /**
     * Get spec renderer for an example spec
     *
     * @param example        spec example
     * @param exampleOptions example custom options
     * @return spec renderer for given example and options
     */
    @NotNull SpecExampleRenderer getSpecExampleRenderer(@NotNull SpecExample example, @Nullable DataHolder exampleOptions);

    /**
     * Called by DumpSpecReader for each example when processing full test spec
     *
     * @param exampleRenderer example renderer
     * @param exampleParse    example parse state
     * @param exampleOptions  example options
     * @param ignoredTestCase true if ignored example
     * @param html            html used for comparison to expected html
     * @param ast             ast used for comparison to expected ast
     */
    void addSpecExample(@NotNull SpecExampleRenderer exampleRenderer, @NotNull SpecExampleParse exampleParse, @Nullable DataHolder exampleOptions, boolean ignoredTestCase, @NotNull String html, @Nullable String ast);
}
