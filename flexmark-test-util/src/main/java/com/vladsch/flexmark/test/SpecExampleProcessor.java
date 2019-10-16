package com.vladsch.flexmark.test;

import com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface SpecExampleProcessor {
    /**
     * Customize options for an example
     *
     * @param optionSet name of the options set to use
     * @return options or null to use default
     */
    @Nullable DataHolder options(String optionSet);
    @NotNull SpecExample getExample();

    /**
     * Get spec renderer for an example spec
     *
     * @param example    spec example
     * @param exampleOptions  example custom options
     * @return spec renderer for given example and options
     */
    @NotNull SpecExampleRenderer getSpecExampleRenderer(@NotNull SpecExample example, @Nullable DataHolder exampleOptions);

    /**
     * Called by DumpSpecReader for each example when processing full test spec
     *
     * @param exampleRenderer   example renderer
     * @param exampleParse      example parse state
     * @param exampleOptions    example options
     * @param ignoredTestCase   true if ignored example
     * @param renderedHtml      rendered html
     * @param renderedAst       rendered ast
     */
    void addSpecExample(@NotNull SpecExampleRenderer exampleRenderer, @NotNull SpecExampleParse exampleParse, @Nullable DataHolder exampleOptions, boolean ignoredTestCase, @NotNull String renderedHtml, @Nullable String renderedAst);

    // return combined options, some may need more than just overwrites, as in case of consumers that set some instance values
    @Nullable DataHolder combineOptions(@Nullable DataHolder other, @Nullable DataHolder overrides);
}
