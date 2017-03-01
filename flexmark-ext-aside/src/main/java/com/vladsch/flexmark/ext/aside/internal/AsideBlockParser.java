package com.vladsch.flexmark.ext.aside.internal;

import com.vladsch.flexmark.ast.Block;
import com.vladsch.flexmark.ast.util.Parsing;
import com.vladsch.flexmark.ext.aside.AsideBlock;
import com.vladsch.flexmark.ext.aside.AsideExtension;
import com.vladsch.flexmark.internal.*;
import com.vladsch.flexmark.parser.block.*;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class AsideBlockParser extends AbstractBlockParser {

    private final AsideBlock block = new AsideBlock();
    private final boolean continueToBlankLine;
    private final boolean ignoreBlankLine;
    private int lastWasBlankLine = 0;

    public AsideBlockParser(DataHolder options, BasedSequence marker) {
        block.setOpeningMarker(marker);
        continueToBlankLine = options.get(AsideExtension.EXTEND_TO_BLANK_LINE);
        ignoreBlankLine = options.get(AsideExtension.IGNORE_BLANK_LINE);
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
    public AsideBlock getBlock() {
        return block;
    }

    @Override
    public void closeBlock(ParserState state) {
        block.setCharsFromContent();
    }

    @Override
    public BlockContinue tryContinue(ParserState state) {
        int nextNonSpace = state.getNextNonSpaceIndex();
        boolean isMarker = false;
        if (!state.isBlank() && ((isMarker = isMarker(state, nextNonSpace)) || (continueToBlankLine && lastWasBlankLine == 0))) {
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
        } else {
            if (ignoreBlankLine && state.isBlank()) {
                lastWasBlankLine++;
                int newColumn = state.getColumn() + state.getIndent();
                return BlockContinue.atColumn(newColumn);
            }
            return BlockContinue.none();
        }
    }

    static boolean isMarker(ParserState state, int index) {
        CharSequence line = state.getLine();
        return state.getIndent() < state.getParsing().CODE_BLOCK_INDENT && index < line.length() && line.charAt(index) == '|';
    }

    static boolean endsWithMarker(BasedSequence line) {
        int tailBlanks = line.countTrailing(BasedSequence.WHITESPACE_NBSP_CHARS);
        return tailBlanks + 1 < line.length() && line.charAt(line.length() - tailBlanks - 1) == '|';
    }

    public static class Factory implements CustomBlockParserFactory {
        @SuppressWarnings("UnnecessaryLocalVariable")
        @Override
        public Set<Class<? extends CustomBlockParserFactory>> getAfterDependents() {
            HashSet<Class<? extends  CustomBlockParserFactory>> set = new HashSet<>();
            //set.add(BlockQuoteParser.Factory.class);
            return set;
            //return new HashSet<>(Arrays.asList(
            //        //BlockQuoteParser.Factory.class,
            //        //HeadingParser.Factory.class,
            //        //FencedCodeBlockParser.Factory.class,
            //        //HtmlBlockParser.Factory.class,
            //        //ThematicBreakParser.Factory.class,
            //        //ListBlockParser.Factory.class,
            //        //IndentedCodeBlockParser.Factory.class
            //));
        }

        @Override
        public Set<Class<? extends CustomBlockParserFactory>> getBeforeDependents() {
            return new HashSet<>(Arrays.asList(
                    //BlockQuoteParser.Factory.class,
                    HeadingParser.Factory.class,
                    FencedCodeBlockParser.Factory.class,
                    HtmlBlockParser.Factory.class,
                    ThematicBreakParser.Factory.class,
                    ListBlockParser.Factory.class,
                    IndentedCodeBlockParser.Factory.class
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
        BlockFactory(DataHolder options) {
            super(options);
        }

        public BlockStart tryStart(ParserState state, MatchedBlockParser matchedBlockParser) {
            int nextNonSpace = state.getNextNonSpaceIndex();
            if (isMarker(state, nextNonSpace) && !endsWithMarker(state.getLine())) {
                int newColumn = state.getColumn() + state.getIndent() + 1;
                // optional following space or tab
                if (Parsing.isSpaceOrTab(state.getLine(), nextNonSpace + 1)) {
                    newColumn++;
                }
                return BlockStart.of(new AsideBlockParser(state.getProperties(), state.getLine().subSequence(nextNonSpace, nextNonSpace + 1))).atColumn(newColumn);
            } else {
                return BlockStart.none();
            }
        }
    }
}
