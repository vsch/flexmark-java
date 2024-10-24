package com.vladsch.flexmark.parser.core;

import com.vladsch.flexmark.ast.ThematicBreak;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.block.AbstractBlockParser;
import com.vladsch.flexmark.parser.block.AbstractBlockParserFactory;
import com.vladsch.flexmark.parser.block.BlockContinue;
import com.vladsch.flexmark.parser.block.BlockParserFactory;
import com.vladsch.flexmark.parser.block.BlockStart;
import com.vladsch.flexmark.parser.block.CustomBlockParserFactory;
import com.vladsch.flexmark.parser.block.MatchedBlockParser;
import com.vladsch.flexmark.parser.block.ParserState;
import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class ThematicBreakParser extends AbstractBlockParser {
  private static final Pattern PATTERN =
      Pattern.compile("^(?:(?:\\*[ \t]*){3,}|(?:_[ \t]*){3,}|(?:-[ \t]*){3,})[ \t]*$");

  private final ThematicBreak block = new ThematicBreak();

  private ThematicBreakParser(BasedSequence line) {
    block.setChars(line);
  }

  @Override
  public Block getBlock() {
    return block;
  }

  @Override
  public void closeBlock(ParserState state) {
    block.setCharsFromContent();
  }

  @Override
  public BlockContinue tryContinue(ParserState state) {
    // a horizontal rule can never container > 1 line, so fail to match
    return BlockContinue.none();
  }

  public static class Factory implements CustomBlockParserFactory {

    @Override
    public Set<Class<?>> getAfterDependents() {
      return new HashSet<>(
          Arrays.asList(
              BlockQuoteParser.Factory.class,
              HeadingParser.Factory.class,
              FencedCodeBlockParser.Factory.class,
              HtmlBlockParser.Factory.class
              // ThematicBreakParser.Factory.class,
              // ListBlockParser.Factory.class,
              // IndentedCodeBlockParser.Factory.class
              ));
    }

    @Override
    public Set<Class<?>> getBeforeDependents() {
      return new HashSet<>(
          Arrays.asList(
              // BlockQuoteParser.Factory.class,
              // HeadingParser.Factory.class,
              // FencedCodeBlockParser.Factory.class,
              // HtmlBlockParser.Factory.class,
              // ThematicBreakParser.Factory.class,
              ListBlockParser.Factory.class, IndentedCodeBlockParser.Factory.class));
    }

    @Override
    public boolean affectsGlobalScope() {
      return false;
    }

    @Override
    public BlockParserFactory apply(DataHolder options) {
      return new BlockFactory(options);
    }
  }

  private static class BlockFactory extends AbstractBlockParserFactory {
    private final ThematicBreakOptions options;

    BlockFactory(DataHolder options) {
      super();
      this.options = new ThematicBreakOptions(options);
    }

    @Override
    public BlockStart tryStart(ParserState state, MatchedBlockParser matchedBlockParser) {
      if (state.getIndent() >= 4
          || matchedBlockParser.getBlockParser().isParagraphParser() && !options.relaxedStart) {
        return BlockStart.none();
      }
      BasedSequence line = state.getLine();
      final BasedSequence input = line.subSequence(state.getNextNonSpaceIndex(), line.length());
      if (PATTERN.matcher(input).matches()) {
        return BlockStart.of(new ThematicBreakParser(line.subSequence(state.getIndex())))
            .atIndex(line.length());
      }

      return BlockStart.none();
    }
  }

  private static class ThematicBreakOptions {
    private final boolean relaxedStart;

    private ThematicBreakOptions(DataHolder options) {
      this.relaxedStart = Parser.THEMATIC_BREAK_RELAXED_START.get(options);
    }
  }
}
