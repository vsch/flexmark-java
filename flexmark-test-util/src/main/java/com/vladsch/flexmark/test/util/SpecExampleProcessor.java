package com.vladsch.flexmark.test.util;

import com.vladsch.flexmark.test.util.spec.SpecExample;
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
    @Nullable DataHolder options(@NotNull String option);

    /**
     * Allows tests to modify example during reading (DumpSpecReader)
     *
     * @param example example as it is in the test or spec file
     * @return modified example if needed
     */
    default @NotNull SpecExample checkExample(@NotNull SpecExample example) {
        return example;
    }

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
    void addFullSpecExample(@NotNull SpecExampleRenderer exampleRenderer, @NotNull SpecExampleParse exampleParse, @Nullable DataHolder exampleOptions, boolean ignoredTestCase, @NotNull String html, @Nullable String ast);
}
