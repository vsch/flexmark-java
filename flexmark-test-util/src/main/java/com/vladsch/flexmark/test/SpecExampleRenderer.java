package com.vladsch.flexmark.test;

import com.vladsch.flexmark.util.ast.IParse;
import com.vladsch.flexmark.util.ast.IRender;
import com.vladsch.flexmark.util.data.DataHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface SpecExampleRenderer {
    boolean includeExampleInfo();

    @Nullable DataHolder getOptions();
    @NotNull IRender renderer();
    @NotNull IParse parser();

    void includeDocument(@NotNull String includedText);
    void parse(CharSequence input);

    // after all parsing is complete gives a chance to handle insertion of included doc
    void finalizeDocument();

    // caches values and does not regenerate
    @NotNull String renderHtml();
    @NotNull String getAst();
}
