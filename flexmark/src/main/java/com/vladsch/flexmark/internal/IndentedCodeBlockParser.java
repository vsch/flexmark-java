package com.vladsch.flexmark.internal;

import com.vladsch.flexmark.internal.util.collection.iteration.Reverse;
import com.vladsch.flexmark.internal.util.options.DataHolder;
import com.vladsch.flexmark.internal.util.sequence.BasedSequence;
import com.vladsch.flexmark.node.Block;
import com.vladsch.flexmark.node.IndentedCodeBlock;
import com.vladsch.flexmark.node.Paragraph;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.block.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class IndentedCodeBlockParser extends AbstractBlockParser {

    private static final Pattern TRAILING_BLANK_LINES = Pattern.compile("(?:\n[ \t]*)+$");

    final private IndentedCodeBlock block = new IndentedCodeBlock();
    private BlockContent content = new BlockContent();
    private boolean trimTrailingBlankLines;

    public IndentedCodeBlockParser(DataHolder options) {
        trimTrailingBlankLines = options.get(Parser.INDENTED_CODE_NO_TRAILING_BLANK_LINES);
    }

    @Override
    public Block getBlock() {
        return block;
    }

    @Override
    public BlockContinue tryContinue(ParserState state) {
        if (state.getIndent() >= state.getParsing().CODE_BLOCK_INDENT) {
            return BlockContinue.atColumn(state.getColumn() + state.getParsing().CODE_BLOCK_INDENT);
        } else if (state.isBlank()) {
            return BlockContinue.atIndex(state.getNextNonSpaceIndex());
        } else {
            return BlockContinue.none();
        }
    }

    @Override
    public void addLine(ParserState state, BasedSequence line) {
        content.add(line, state.getIndent());
    }

    @Override
    public void closeBlock(ParserState parserState) {
        // trim trailing blank lines out of the block
        if (trimTrailingBlankLines) {
            int trailingBlankLines = 0;
            List<BasedSequence> lines = content.getLines();
            for (BasedSequence line : new Reverse<>(lines)) {
                if (!line.isBlank()) break;
                trailingBlankLines++;
            }

            if (trailingBlankLines > 0) block.setContent(lines.subList(0, lines.size() - trailingBlankLines));
            else block.setContent(content);
        } else {
            block.setContent(content);
        }
        content = null;
    }

    public static class Factory implements CustomBlockParserFactory {
        @Override
        public Set<Class<? extends CustomBlockParserFactory>> getAfterDependents() {
            return new HashSet<>(Arrays.asList(
                    BlockQuoteParser.Factory.class,
                    HeadingParser.Factory.class,
                    FencedCodeBlockParser.Factory.class,
                    HtmlBlockParser.Factory.class,
                    ThematicBreakParser.Factory.class,
                    ListBlockParser.Factory.class
                    //IndentedCodeBlockParser.Factory.class
            ));
        }

        @Override
        public Set<Class<? extends CustomBlockParserFactory>> getBeforeDependents() {
            return new HashSet<>(Arrays.asList(
                    //BlockQuoteParser.Factory.class,
                    //HeadingParser.Factory.class,
                    //FencedCodeBlockParser.Factory.class,
                    //HtmlBlockParser.Factory.class,
                    //ThematicBreakParser.Factory.class,
                    //ListBlockParser.Factory.class,
                    //IndentedCodeBlockParser.Factory.class
            ));
        }

        @Override
        public boolean affectsGlobalScope() {
            return false;
        }

        @Override
        public BlockParserFactory create(DataHolder options) {
            return new BlockFactory(options);
        }
    }

    private static class BlockFactory extends AbstractBlockParserFactory {
        private BlockFactory(DataHolder options) {
            super(options);
        }

        @Override
        public BlockStart tryStart(ParserState state, MatchedBlockParser matchedBlockParser) {
            // An indented code block cannot interrupt a paragraph.
            if (state.getIndent() >= state.getParsing().CODE_BLOCK_INDENT && !state.isBlank() && !(state.getActiveBlockParser().getBlock() instanceof Paragraph)) {
                return BlockStart.of(new IndentedCodeBlockParser(state.getProperties())).atColumn(state.getColumn() + state.getParsing().CODE_BLOCK_INDENT);
            } else {
                return BlockStart.none();
            }
        }
    }
}

