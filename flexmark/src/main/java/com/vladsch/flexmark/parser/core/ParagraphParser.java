package com.vladsch.flexmark.parser.core;

import com.vladsch.flexmark.ast.Paragraph;
import com.vladsch.flexmark.parser.InlineParser;
import com.vladsch.flexmark.parser.block.AbstractBlockParser;
import com.vladsch.flexmark.parser.block.BlockContinue;
import com.vladsch.flexmark.parser.block.ParserState;
import com.vladsch.flexmark.util.ast.BlockContent;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.PrefixedSubSequence;

public class ParagraphParser extends AbstractBlockParser {
  private final Paragraph block = new Paragraph();
  private BlockContent content = new BlockContent();

  @Override
  public BlockContent getBlockContent() {
    return content;
  }

  @Override
  public Paragraph getBlock() {
    return block;
  }

  @Override
  public BlockContinue tryContinue(ParserState state) {
    if (!state.isBlank()) {
      // NOTE: here we can continue with any indent, unless the parent is a list item and the indent
      // is >= code indent
      return BlockContinue.atIndex(state.getIndex());
    }

    boolean blankLine = state.isBlankLine();
    block.setTrailingBlankLine(blankLine);
    return BlockContinue.none();
  }

  @Override
  public void addLine(ParserState state, BasedSequence line) {
    int indent = state.getIndent();
    if (indent > 0) {
      content.add(PrefixedSubSequence.repeatOf(' ', indent, line), indent);
    } else {
      content.add(line, indent);
    }
  }

  @Override
  public boolean isParagraphParser() {
    return true;
  }

  @Override
  public boolean isInterruptible() {
    return true;
  }

  @Override
  public void closeBlock(ParserState state) {
    block.setContent(content);
    content = null;
  }

  @Override
  public void parseInlines(InlineParser inlineParser) {
    inlineParser.parse(getBlock().getContentChars(), getBlock());
  }
}
