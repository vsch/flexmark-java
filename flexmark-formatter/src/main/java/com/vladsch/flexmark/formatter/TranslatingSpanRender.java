package com.vladsch.flexmark.formatter;

import com.vladsch.flexmark.formatter.internal.MarkdownWriter;
import com.vladsch.flexmark.formatter.internal.NodeFormatterContext;

public interface TranslatingSpanRender {
    void render(NodeFormatterContext context, MarkdownWriter writer);
}
