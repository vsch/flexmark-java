package com.vladsch.flexmark.parser;

public interface InlineParserExtension {
  void finalizeDocument(InlineParser inlineParser);

  void finalizeBlock(InlineParser inlineParser);

  /**
   * Parse input
   *
   * @return true if character input was processed
   */
  boolean parse(LightInlineParser inlineParser);
}
