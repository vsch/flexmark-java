package com.vladsch.flexmark.formatter;

import com.vladsch.flexmark.util.ast.Document;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface MergeContext {
    @NotNull Document getDocument(@NotNull TranslationContext context);
    void forEachPrecedingDocument(@Nullable Document document, @NotNull MergeContextConsumer consumer);
}
