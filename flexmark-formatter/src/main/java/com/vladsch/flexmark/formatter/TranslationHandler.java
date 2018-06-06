package com.vladsch.flexmark.formatter;

import com.vladsch.flexmark.ast.Document;
import com.vladsch.flexmark.formatter.internal.MarkdownWriter;
import com.vladsch.flexmark.formatter.internal.NodeFormatterContext;

import java.util.List;

public interface TranslationHandler extends TranslationContext {
    void beginRendering(Document node, NodeFormatterContext context, MarkdownWriter out);

    List<String> getTranslatingTexts();
    void setTranslatedTexts(List<CharSequence> translatedTexts);
    void setRenderPurpose(RenderPurpose renderPurpose);
}
