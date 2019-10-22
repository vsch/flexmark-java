package com.vladsch.flexmark.formatter;

import com.vladsch.flexmark.util.ast.Document;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface TranslationHandler extends TranslationContext {
    void beginRendering(@NotNull Document node, @NotNull NodeFormatterContext context, @NotNull MarkdownWriter out);

    @NotNull List<String> getTranslatingTexts();
    void setTranslatedTexts(@NotNull List<? extends CharSequence> translatedTexts);
    void setRenderPurpose(@NotNull RenderPurpose renderPurpose);

    void setMergeContext(@NotNull MergeContext context);
}
