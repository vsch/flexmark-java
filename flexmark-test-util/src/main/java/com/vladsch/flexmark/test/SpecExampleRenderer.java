package com.vladsch.flexmark.test;

import com.vladsch.flexmark.util.data.DataHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface SpecExampleRenderer {
    boolean includeExampleInfo();

    @Nullable DataHolder getOptions();

    void includeDocument(@NotNull String includedText);
    void parse(CharSequence input);

    // after all parsing is complete gives a chance to handle insertion of included doc
    void finalizeDocument();

    // caches values and does not regenerate
    @NotNull String renderHtml();
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
        public void finalizeDocument() {

        }

        @Override
        public @NotNull String renderHtml() {
            return "";
        }

        @Override
        public @Nullable String getAst() {
            return null;
        }
    };
}
