package com.vladsch.flexmark.parser.core;

import com.vladsch.flexmark.ast.BlockQuote;
import com.vladsch.flexmark.ast.ListItem;
import com.vladsch.flexmark.ast.util.Parsing;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.block.AbstractBlockParser;
import com.vladsch.flexmark.parser.block.AbstractBlockParserFactory;
import com.vladsch.flexmark.parser.block.BlockContinue;
import com.vladsch.flexmark.parser.block.BlockParser;
import com.vladsch.flexmark.parser.block.BlockParserFactory;
import com.vladsch.flexmark.parser.block.BlockStart;
import com.vladsch.flexmark.parser.block.CustomBlockParserFactory;
import com.vladsch.flexmark.parser.block.MatchedBlockParser;
import com.vladsch.flexmark.parser.block.ParserState;
import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.mappers.SpecialLeadInHandler;
import com.vladsch.flexmark.util.sequence.mappers.SpecialLeadInStartsWithCharsHandler;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockQuoteParser extends AbstractBlockParser {
  private static final char MARKER_CHAR = '>';

  private final BlockQuote block = new BlockQuote();
  private final boolean allowLeadingSpace;
  private final boolean continueToBlankLine;
  private final boolean ignoreBlankLine;
  private final boolean interruptsParagraph;
  private final boolean interruptsItemParagraph;
  private final boolean withLeadSpacesInterruptsItemParagraph;
  private int lastWasBlankLine = 0;

  private BlockQuoteParser(DataHolder options, BasedSequence marker) {
    block.setOpeningMarker(marker);
    continueToBlankLine = Parser.BLOCK_QUOTE_EXTEND_TO_BLANK_LINE.get(options);
    allowLeadingSpace = Parser.BLOCK_QUOTE_ALLOW_LEADING_SPACE.get(options);
    ignoreBlankLine = Parser.BLOCK_QUOTE_IGNORE_BLANK_LINE.get(options);
    interruptsParagraph = Parser.BLOCK_QUOTE_INTERRUPTS_PARAGRAPH.get(options);
    interruptsItemParagraph = Parser.BLOCK_QUOTE_INTERRUPTS_ITEM_PARAGRAPH.get(options);
    withLeadSpacesInterruptsItemParagraph =
        Parser.BLOCK_QUOTE_WITH_LEAD_SPACES_INTERRUPTS_ITEM_PARAGRAPH.get(options);
  }

  @Override
  public boolean isContainer() {
    return true;
  }

  @Override
  public boolean isPropagatingLastBlankLine(BlockParser lastMatchedBlockParser) {
    return false;
  }

  @Override
  public boolean canContain(ParserState state, BlockParser blockParser, Block block) {
    return true;
  }

  @Override
  public BlockQuote getBlock() {
    return block;
  }

  @Override
  public void closeBlock(ParserState state) {
    block.setCharsFromContent();

    if (!Parser.BLANK_LINES_IN_AST.get(state.getProperties())) {
      removeBlankLines();
    }
  }

  @Override
  public BlockContinue tryContinue(ParserState state) {
    int nextNonSpace = state.getNextNonSpaceIndex();
    boolean isMarker;
    if (!state.isBlank()
        && ((isMarker =
                isMarker(
                    state,
                    nextNonSpace,
                    false,
                    false,
                    allowLeadingSpace,
                    interruptsParagraph,
                    interruptsItemParagraph,
                    withLeadSpacesInterruptsItemParagraph))
            || (continueToBlankLine && lastWasBlankLine == 0))) {
      int newColumn = state.getColumn() + state.getIndent();
      lastWasBlankLine = 0;

      if (isMarker) {
        newColumn++;
        // optional following space or tab
        if (Parsing.isSpaceOrTab(state.getLine(), nextNonSpace + 1)) {
          newColumn++;
        }
      }
      return BlockContinue.atColumn(newColumn);
    }

    if (ignoreBlankLine && state.isBlank()) {
      lastWasBlankLine++;
      int newColumn = state.getColumn() + state.getIndent();
      return BlockContinue.atColumn(newColumn);
    }
    return BlockContinue.none();
  }

  private static boolean isMarker(
      final ParserState state,
      final int index,
      final boolean inParagraph,
      final boolean inParagraphListItem,
      final boolean allowLeadingSpace,
      final boolean interruptsParagraph,
      final boolean interruptsItemParagraph,
      final boolean withLeadSpacesInterruptsItemParagraph) {
    CharSequence line = state.getLine();
    if ((!inParagraph || interruptsParagraph)
        && index < line.length()
        && line.charAt(index) == MARKER_CHAR) {
      if ((allowLeadingSpace || state.getIndent() == 0)
          && (!inParagraphListItem || interruptsItemParagraph)) {
        if (inParagraphListItem && !withLeadSpacesInterruptsItemParagraph) {
          return state.getIndent() == 0;
        }

        return state.getIndent() < state.getParsing().codeBlockIndent;
      }
    }
    return false;
  }

  public static class Factory implements CustomBlockParserFactory {
    @Nullable
    @Override
    public Set<Class<?>> getAfterDependents() {
      return Collections.emptySet();
    }

    @Nullable
    @Override
    public Set<Class<?>> getBeforeDependents() {
      return new HashSet<>(
          Arrays.asList(
              // BlockQuoteParser.Factory.class,
              HeadingParser.Factory.class,
              FencedCodeBlockParser.Factory.class,
              HtmlBlockParser.Factory.class,
              ThematicBreakParser.Factory.class,
              ListBlockParser.Factory.class,
              IndentedCodeBlockParser.Factory.class));
    }

    @Override
    public @Nullable SpecialLeadInHandler getLeadInHandler(@NotNull DataHolder options) {
      return BlockQuoteLeadInHandler.HANDLER;
    }

    @Override
    public boolean affectsGlobalScope() {
      return false;
    }

    @NotNull
    @Override
    public BlockParserFactory apply(@NotNull DataHolder options) {
      return new BlockFactory(options);
    }
  }

  private static class BlockQuoteLeadInHandler {
    static final SpecialLeadInHandler HANDLER = SpecialLeadInStartsWithCharsHandler.create('>');
  }

  private static class BlockFactory extends AbstractBlockParserFactory {
    private final boolean allowLeadingSpace;
    private final boolean interruptsParagraph;
    private final boolean interruptsItemParagraph;
    private final boolean withLeadSpacesInterruptsItemParagraph;

    BlockFactory(DataHolder options) {
      super();
      allowLeadingSpace = Parser.BLOCK_QUOTE_ALLOW_LEADING_SPACE.get(options);
      interruptsParagraph = Parser.BLOCK_QUOTE_INTERRUPTS_PARAGRAPH.get(options);
      interruptsItemParagraph = Parser.BLOCK_QUOTE_INTERRUPTS_ITEM_PARAGRAPH.get(options);
      withLeadSpacesInterruptsItemParagraph =
          Parser.BLOCK_QUOTE_WITH_LEAD_SPACES_INTERRUPTS_ITEM_PARAGRAPH.get(options);
    }

    @Override
    public BlockStart tryStart(ParserState state, MatchedBlockParser matchedBlockParser) {
      int nextNonSpace = state.getNextNonSpaceIndex();
      BlockParser matched = matchedBlockParser.getBlockParser();
      boolean inParagraph = matched.isParagraphParser();
      boolean inParagraphListItem =
          inParagraph
              && matched.getBlock().getParent() instanceof ListItem
              && matched.getBlock() == matched.getBlock().getParent().getFirstChild();

      if (isMarker(
          state,
          nextNonSpace,
          inParagraph,
          inParagraphListItem,
          allowLeadingSpace,
          interruptsParagraph,
          interruptsItemParagraph,
          withLeadSpacesInterruptsItemParagraph)) {
        int newColumn = state.getColumn() + state.getIndent() + 1;
        // optional following space or tab
        if (Parsing.isSpaceOrTab(state.getLine(), nextNonSpace + 1)) {
          newColumn++;
        }
        return BlockStart.of(
                new BlockQuoteParser(
                    state.getProperties(),
                    state.getLine().subSequence(nextNonSpace, nextNonSpace + 1)))
            .atColumn(newColumn);
      }

      return BlockStart.none();
    }
  }
}
