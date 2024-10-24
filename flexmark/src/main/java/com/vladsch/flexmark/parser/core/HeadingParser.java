package com.vladsch.flexmark.parser.core;

import com.vladsch.flexmark.ast.Heading;
import com.vladsch.flexmark.ast.ListItem;
import com.vladsch.flexmark.ast.util.Parsing;
import com.vladsch.flexmark.parser.InlineParser;
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
import com.vladsch.flexmark.util.ast.BlockContent;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.mappers.SpecialLeadInCharsHandler;
import com.vladsch.flexmark.util.sequence.mappers.SpecialLeadInHandler;
import com.vladsch.flexmark.util.sequence.mappers.SpecialLeadInStartsWithCharsHandler;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HeadingParser extends AbstractBlockParser {
  private static class HeadingParsing extends Parsing {
    private final Pattern atxHeading;
    private final Pattern atxTrailing;
    private final Pattern setextHeading;

    private HeadingParsing(DataHolder options) {
      super(options);

      atxHeading =
          Parser.HEADING_NO_ATX_SPACE.get(options)
              ? Pattern.compile("^#{1,6}(?:[ \t]*|$)")
              : Parser.HEADING_NO_EMPTY_HEADING_WITHOUT_SPACE.get(options)
                  ? Pattern.compile("^#{1,6}(?:[ \t]*(?=[^ \t#])|[ \t]+$)")
                  : Pattern.compile("^#{1,6}(?:[ \t]+|$)");
      atxTrailing =
          Parser.HEADING_NO_ATX_SPACE.get(options)
              ? Pattern.compile("[ \t]*#+[ \t]*$")
              : Pattern.compile("(^| |\t)[ \t]*#+[ \t]*$");

      int minLength = Parser.HEADING_SETEXT_MARKER_LENGTH.get(options);
      setextHeading =
          minLength <= 1
              ? Pattern.compile("^(?:=+|-+)[ \t]*$")
              : Pattern.compile("^(?:={" + minLength + ",}|-{" + minLength + ",})[ \t]*$");
    }
  }

  private final Heading block = new Heading();

  private HeadingParser(int level) {
    block.setLevel(level);
  }

  @Override
  public Block getBlock() {
    return block;
  }

  @Override
  public BlockContinue tryContinue(ParserState state) {
    // In both ATX and Setext headings, once we have the heading markup, there's nothing more to
    // parse.
    return BlockContinue.none();
  }

  @Override
  public void parseInlines(InlineParser inlineParser) {
    inlineParser.parse(block.getText(), block);
  }

  @Override
  public void closeBlock(ParserState state) {}

  public static class Factory implements CustomBlockParserFactory {

    @Override
    public Set<Class<?>> getAfterDependents() {
      Set<Class<?>> set = new HashSet<>();
      set.add(BlockQuoteParser.Factory.class);
      return set;
    }

    @Override
    public Set<Class<?>> getBeforeDependents() {
      return new HashSet<>(
          Arrays.asList(
              FencedCodeBlockParser.Factory.class,
              HtmlBlockParser.Factory.class,
              ThematicBreakParser.Factory.class,
              ListBlockParser.Factory.class,
              IndentedCodeBlockParser.Factory.class));
    }

    @Override
    public boolean affectsGlobalScope() {
      return false;
    }

    @Override
    public SpecialLeadInHandler getLeadInHandler(DataHolder options) {
      boolean noAtxSpace =
          Parser.ESCAPE_HEADING_NO_ATX_SPACE.get(options)
              || Parser.HEADING_NO_ATX_SPACE.get(options);
      return noAtxSpace
          ? HeadingLeadInHandler.HANDLER_NO_SPACE
          : HeadingLeadInHandler.HANDLER_SPACE;
    }

    @Override
    public BlockParserFactory apply(DataHolder options) {
      return new BlockFactory(options);
    }
  }

  private static class HeadingLeadInHandler {
    private static final SpecialLeadInHandler HANDLER_NO_SPACE =
        SpecialLeadInStartsWithCharsHandler.create('#');
    private static final SpecialLeadInHandler HANDLER_SPACE = SpecialLeadInCharsHandler.create('#');
  }

  private static class BlockFactory extends AbstractBlockParserFactory {
    private final HeadingOptions options;
    private final HeadingParsing myParsing;

    BlockFactory(DataHolder options) {
      super();
      this.options = new HeadingOptions(options);
      this.myParsing = new HeadingParsing(options);
    }

    @Override
    public BlockStart tryStart(ParserState state, MatchedBlockParser matchedBlockParser) {
      if (state.getIndent() >= 4 || options.noLeadSpace && state.getIndent() >= 1) {
        return BlockStart.none();
      }

      if (state.getActiveBlockParser() instanceof FencedCodeBlockParser) {
        return BlockStart.none();
      }

      if (!options.canInterruptItemParagraph) {
        BlockParser matched = matchedBlockParser.getBlockParser();
        boolean inParagraph = matched.isParagraphParser();
        boolean inParagraphListItem =
            inParagraph
                && matched.getBlock().getParent() instanceof ListItem
                && matched.getBlock() == matched.getBlock().getParent().getFirstChild();

        if (inParagraphListItem) {
          return BlockStart.none();
        }
      }

      BasedSequence line = state.getLine();
      int nextNonSpace = state.getNextNonSpaceIndex();
      BasedSequence paragraph = matchedBlockParser.getParagraphContent();
      Matcher matcher;
      BasedSequence trySequence = line.subSequence(nextNonSpace, line.length());
      matcher = myParsing.atxHeading.matcher(trySequence);
      if (matcher.find()) {
        // ATX heading
        int newOffset = nextNonSpace + matcher.group(0).length();
        int openingStart = matcher.start();
        int openingEnd = matcher.end();
        BasedSequence openingMarker = trySequence.subSequence(openingStart, openingEnd).trim();
        int level = openingMarker.length(); // number of #s

        BlockContent content = new BlockContent();
        content.add(state.getLineWithEOL().subSequence(newOffset), state.getIndent());

        BasedSequence headerText = trySequence.subSequence(openingEnd);
        BasedSequence closingMarker = null;
        matcher = myParsing.atxTrailing.matcher(headerText);
        if (matcher.find()) {
          // removeIndex trailing ###s:
          int closingStart = matcher.start();
          int closingEnd = matcher.end();
          closingMarker = headerText.subSequence(closingStart, closingEnd).trim();
          headerText = headerText.subSequence(0, closingStart);
        }

        HeadingParser headingParser = new HeadingParser(level);
        headingParser.block.setOpeningMarker(openingMarker);
        headingParser.block.setText(headerText.trim());
        headingParser.block.setClosingMarker(closingMarker);
        headingParser.block.setCharsFromContent();

        return BlockStart.of(headingParser).atIndex(line.length());
      }

      if ((matcher = myParsing.setextHeading.matcher(trySequence)).find()) {
        if (paragraph != null) {
          // setext heading line
          int level = matcher.group(0).charAt(0) == '=' ? 1 : 2;

          BlockContent content = new BlockContent();
          content.addAll(
              matchedBlockParser.getParagraphLines(), matchedBlockParser.getParagraphEolLengths());
          BasedSequence headingText = content.getContents().trim();
          BasedSequence closingMarker = line.trim();

          HeadingParser headingParser = new HeadingParser(level);
          headingParser.block.setText(headingText);
          headingParser.block.setClosingMarker(closingMarker);
          headingParser.block.setCharsFromContent();

          return BlockStart.of(headingParser).atIndex(line.length()).replaceActiveBlockParser();
        }

        return BlockStart.none();
      }

      return BlockStart.none();
    }
  }

  private static class HeadingOptions {
    private final boolean noLeadSpace;
    private final boolean canInterruptItemParagraph;

    HeadingOptions(DataHolder options) {
      this.noLeadSpace = Parser.HEADING_NO_LEAD_SPACE.get(options);
      this.canInterruptItemParagraph = Parser.HEADING_CAN_INTERRUPT_ITEM_PARAGRAPH.get(options);
    }
  }
}
