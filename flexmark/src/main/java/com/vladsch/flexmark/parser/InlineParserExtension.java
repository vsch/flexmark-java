package com.vladsch.flexmark.parser;

import org.jetbrains.annotations.NotNull;

public interface InlineParserExtension {
  void finalizeDocument(@NotNull InlineParser inlineParser);

  void finalizeBlock(@NotNull InlineParser inlineParser);

  /**
   * Parse input
   *
   * @param inlineParser
   * @return true if character input was processed
   */
  boolean parse(@NotNull LightInlineParser inlineParser);
}
