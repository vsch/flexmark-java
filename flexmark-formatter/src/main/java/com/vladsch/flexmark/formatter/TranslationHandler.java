package com.vladsch.flexmark.formatter;

import com.vladsch.flexmark.util.ast.Document;

import java.util.List;

public interface TranslationHandler extends TranslationContext {
    void beginRendering(Document node, NodeFormatterContext context, MarkdownWriter out);

    List<String> getTranslatingTexts();
    void setTranslatedTexts(List<CharSequence> translatedTexts);
    void setRenderPurpose(RenderPurpose renderPurpose);
}
