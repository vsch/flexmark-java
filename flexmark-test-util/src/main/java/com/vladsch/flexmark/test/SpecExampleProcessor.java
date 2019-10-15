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
    @NotNull SpecExampleRenderer getSpecExampleRenderer(@Nullable DataHolder exampleOptions);
    void addSpecExample(@NotNull SpecExample example, @NotNull SpecExampleRenderer exampleRenderer, @Nullable DataHolder options, boolean ignoredTestCase, @NotNull String renderedHtml, @Nullable String renderedAst);

    // return combined options, some may need more than just overwrites, as in case of consumers that set some instance values
    @Nullable DataHolder combineOptions(@Nullable DataHolder other, @Nullable DataHolder overrides);
}
