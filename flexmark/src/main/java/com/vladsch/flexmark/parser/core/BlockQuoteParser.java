package com.vladsch.flexmark.parser.core;

import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.ast.BlockQuote;
import com.vladsch.flexmark.ast.ListItem;
import com.vladsch.flexmark.ast.util.Parsing;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.block.*;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class BlockQuoteParser extends AbstractBlockParser {

    public static final char MARKER_CHAR = '>';
    private final BlockQuote block = new BlockQuote();
    private final boolean allowLeadingSpace;
    private final boolean continueToBlankLine;
    private final boolean ignoreBlankLine;
    private final boolean interruptsParagraph;
    private final boolean interruptsItemParagraph;
    private final boolean withLeadSpacesInterruptsItemParagraph;
    private int lastWasBlankLine = 0;

    public BlockQuoteParser(DataHolder options, BasedSequence marker) {
        block.setOpeningMarker(marker);
        continueToBlankLine = options.get(Parser.BLOCK_QUOTE_EXTEND_TO_BLANK_LINE);
        allowLeadingSpace = options.get(Parser.BLOCK_QUOTE_ALLOW_LEADING_SPACE);
        ignoreBlankLine = options.get(Parser.BLOCK_QUOTE_IGNORE_BLANK_LINE);
        interruptsParagraph = options.get(Parser.BLOCK_QUOTE_INTERRUPTS_PARAGRAPH);
        interruptsItemParagraph = options.get(Parser.BLOCK_QUOTE_INTERRUPTS_ITEM_PARAGRAPH);
        withLeadSpacesInterruptsItemParagraph = options.get(Parser.BLOCK_QUOTE_WITH_LEAD_SPACES_INTERRUPTS_ITEM_PARAGRAPH);
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

        if (!state.getProperties().get(Parser.BLANK_LINES_IN_AST)) {
            removeBlankLines();
        }
    }

    @Override
    public BlockContinue tryContinue(ParserState state) {
        int nextNonSpace = state.getNextNonSpaceIndex();
        boolean isMarker;
        if (!state.isBlank() && ((isMarker = isMarker(state, nextNonSpace, false, false, allowLeadingSpace, interruptsParagraph, interruptsItemParagraph, withLeadSpacesInterruptsItemParagraph)) || (continueToBlankLine && lastWasBlankLine == 0))) {
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

    static boolean isMarker(
            final ParserState state,
            final int index,
            final boolean inParagraph,
            final boolean inParagraphListItem,
            final boolean allowLeadingSpace,
            final boolean interruptsParagraph,
            final boolean interruptsItemParagraph,
            final boolean withLeadSpacesInterruptsItemParagraph
    ) {
        CharSequence line = state.getLine();
        if ((!inParagraph || interruptsParagraph) && index < line.length() && line.charAt(index) == MARKER_CHAR) {
            if ((allowLeadingSpace || state.getIndent() == 0) && (!inParagraphListItem || interruptsItemParagraph)) {
                if (inParagraphListItem && !withLeadSpacesInterruptsItemParagraph) {
                    return state.getIndent() == 0;
                } else {
                    return state.getIndent() < state.getParsing().CODE_BLOCK_INDENT;
                }
            }
        }
        return false;
    }

    public static class Factory implements CustomBlockParserFactory {
        @Override
        public Set<Class<? extends CustomBlockParserFactory>> getAfterDependents() {
            return Collections.emptySet();
        }

        @Override
        public Set<Class<? extends CustomBlockParserFactory>> getBeforeDependents() {
            return new HashSet<Class<? extends CustomBlockParserFactory>>(Arrays.asList(
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
        private final boolean allowLeadingSpace;
        private final boolean interruptsParagraph;
        private final boolean interruptsItemParagraph;
        private final boolean withLeadSpacesInterruptsItemParagraph;
        BlockFactory(DataHolder options) {
            super(options);
            allowLeadingSpace = Parser.BLOCK_QUOTE_ALLOW_LEADING_SPACE.getFrom( options);
            interruptsParagraph = Parser.BLOCK_QUOTE_INTERRUPTS_PARAGRAPH.getFrom( options);
            interruptsItemParagraph = Parser.BLOCK_QUOTE_INTERRUPTS_ITEM_PARAGRAPH.getFrom( options);
            withLeadSpacesInterruptsItemParagraph = Parser.BLOCK_QUOTE_WITH_LEAD_SPACES_INTERRUPTS_ITEM_PARAGRAPH.getFrom( options);
        }

        public BlockStart tryStart(ParserState state, MatchedBlockParser matchedBlockParser) {
            int nextNonSpace = state.getNextNonSpaceIndex();
            BlockParser matched = matchedBlockParser.getBlockParser();
            boolean inParagraph = matched.isParagraphParser();
            boolean inParagraphListItem = inParagraph && matched.getBlock().getParent() instanceof ListItem && matched.getBlock() == matched.getBlock().getParent().getFirstChild();

            if (isMarker(state, nextNonSpace, inParagraph, inParagraphListItem, allowLeadingSpace, interruptsParagraph, interruptsItemParagraph, withLeadSpacesInterruptsItemParagraph)) {
                int newColumn = state.getColumn() + state.getIndent() + 1;
                // optional following space or tab
                if (Parsing.isSpaceOrTab(state.getLine(), nextNonSpace + 1)) {
                    newColumn++;
                }
                return BlockStart.of(new BlockQuoteParser(state.getProperties(), state.getLine().subSequence(nextNonSpace, nextNonSpace + 1))).atColumn(newColumn);
            } else {
                return BlockStart.none();
            }
        }
    }
}
