package com.vladsch.flexmark.test.util;

import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface SpecExampleRenderer {
    boolean includeExampleInfo();

    @Nullable DataHolder getOptions();
    @NotNull SpecExample getExample();

    void includeDocument(@NotNull String includedText);
    void parse(CharSequence input);

    // after all parsing is complete gives a chance to handle insertion of included doc
    void finalizeDocument();

    // after all rendering information is collected, give chance to release resources and reset test settings needed for renderHtml or other functions.
    // after this there will be no more calls to renderer for this iteration
    void finalizeRender();

    // caches values and does not regenerate
    @NotNull String getHtml();
    @Nullable String getAst();

    SpecExampleRenderer NULL = new SpecExampleRenderer() {
        @Override
        public boolean includeExampleInfo() {
            return false;
        }

        @Override
        public @Nullable DataHolder getOptions() {
            return null;
        }

        @Override
        public void includeDocument(@NotNull String includedText) {

        }

        @Override
        public void parse(CharSequence input) {

        }

        @Override
        public @NotNull SpecExample getExample() {
            return SpecExample.NULL;
        }

        @Override
        public void finalizeDocument() {

        }

        @Override
        public @NotNull String getHtml() {
            return "";
        }

        @Override
        public @Nullable String getAst() {
            return null;
        }

        @Override
        public void finalizeRender() {

        }
    };
}
