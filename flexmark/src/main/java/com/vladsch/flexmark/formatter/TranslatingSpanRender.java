package com.vladsch.flexmark.formatter;

import org.jetbrains.annotations.NotNull;

public interface TranslatingSpanRender {
  void render(@NotNull NodeFormatterContext context, @NotNull MarkdownWriter markdown);
}
