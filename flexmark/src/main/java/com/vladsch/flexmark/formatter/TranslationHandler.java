package com.vladsch.flexmark.formatter;

import com.vladsch.flexmark.util.ast.Document;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public interface TranslationHandler extends TranslationContext {
    void beginRendering(@NotNull Document node, @NotNull NodeFormatterContext context, @NotNull MarkdownWriter out);

    @NotNull List<String> getTranslatingTexts();
    void setTranslatedTexts(@NotNull List<? extends CharSequence> translatedTexts);
    void setRenderPurpose(@NotNull RenderPurpose renderPurpose);

    void setMergeContext(@NotNull MergeContext context);
}
