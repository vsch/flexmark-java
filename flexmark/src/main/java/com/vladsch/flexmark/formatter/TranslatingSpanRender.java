package com.vladsch.flexmark.formatter;

public interface TranslatingSpanRender {
  void render(NodeFormatterContext context, MarkdownWriter markdown);
}
