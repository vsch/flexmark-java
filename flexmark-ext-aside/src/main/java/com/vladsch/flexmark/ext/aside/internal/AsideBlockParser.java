package com.vladsch.flexmark.ext.aside.internal;

import com.vladsch.flexmark.ast.ListItem;
import com.vladsch.flexmark.ast.util.Parsing;
import com.vladsch.flexmark.ext.aside.AsideBlock;
import com.vladsch.flexmark.ext.aside.AsideExtension;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.block.*;
import com.vladsch.flexmark.parser.core.*;
import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.misc.CharPredicate;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.mappers.SpecialLeadInHandler;
import com.vladsch.flexmark.util.sequence.mappers.SpecialLeadInStartsWithCharsHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class AsideBlockParser extends AbstractBlockParser {

    final public static char MARKER_CHAR = '|';
    final private AsideBlock block = new AsideBlock();
    final private boolean allowLeadingSpace;
    final private boolean continueToBlankLine;
    final private boolean ignoreBlankLine;
    final private boolean interruptsParagraph;
    final private boolean interruptsItemParagraph;
    final private boolean withLeadSpacesInterruptsItemParagraph;
    private int lastWasBlankLine = 0;

    public AsideBlockParser(DataHolder options, BasedSequence marker) {
        block.setOpeningMarker(marker);
        continueToBlankLine = AsideExtension.EXTEND_TO_BLANK_LINE.get(options);
        allowLeadingSpace = AsideExtension.ALLOW_LEADING_SPACE.get(options);
        ignoreBlankLine = AsideExtension.IGNORE_BLANK_LINE.get(options);
        interruptsParagraph = AsideExtension.INTERRUPTS_PARAGRAPH.get(options);
        interruptsItemParagraph = AsideExtension.INTERRUPTS_ITEM_PARAGRAPH.get(options);
        withLeadSpacesInterruptsItemParagraph = AsideExtension.WITH_LEAD_SPACES_INTERRUPTS_ITEM_PARAGRAPH.get(options);
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

        if (!Parser.BLANK_LINES_IN_AST.get(state.getProperties())) {
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

    static boolean endsWithMarker(BasedSequence line) {
        int tailBlanks = line.countTrailing(CharPredicate.WHITESPACE_NBSP);
        return tailBlanks + 1 < line.length() && line.charAt(line.length() - tailBlanks - 1) == MARKER_CHAR;
    }

    public static class Factory implements CustomBlockParserFactory {
        @Nullable
        @SuppressWarnings("UnnecessaryLocalVariable")
        @Override
        public Set<Class<?>> getAfterDependents() {
            HashSet<Class<?>> set = new HashSet<>();
            //set.add(BlockQuoteParser.Factory.class);
            return set;
        }

        @Nullable
        @Override
        public Set<Class<?>> getBeforeDependents() {
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
        public @Nullable SpecialLeadInHandler getLeadInHandler(@NotNull DataHolder options) {
            return AsideLeadInHandler.HANDLER;
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

    static class AsideLeadInHandler {
        final static SpecialLeadInHandler HANDLER = SpecialLeadInStartsWithCharsHandler.create('|');
    }

    private static class BlockFactory extends AbstractBlockParserFactory {
        final private boolean allowLeadingSpace;
        final private boolean interruptsParagraph;
        final private boolean interruptsItemParagraph;
        final private boolean withLeadSpacesInterruptsItemParagraph;

        BlockFactory(DataHolder options) {
            super(options);
            allowLeadingSpace = AsideExtension.ALLOW_LEADING_SPACE.get(options);
            interruptsParagraph = AsideExtension.INTERRUPTS_PARAGRAPH.get(options);
            interruptsItemParagraph = AsideExtension.INTERRUPTS_ITEM_PARAGRAPH.get(options);
            withLeadSpacesInterruptsItemParagraph = AsideExtension.WITH_LEAD_SPACES_INTERRUPTS_ITEM_PARAGRAPH.get(options);
        }

        public BlockStart tryStart(ParserState state, MatchedBlockParser matchedBlockParser) {
            int nextNonSpace = state.getNextNonSpaceIndex();
            BlockParser matched = matchedBlockParser.getBlockParser();
            boolean inParagraph = matched.isParagraphParser();
            boolean inParagraphListItem = inParagraph && matched.getBlock().getParent() instanceof ListItem && matched.getBlock() == matched.getBlock().getParent().getFirstChild();

            if (!endsWithMarker(state.getLine()) && isMarker(state, nextNonSpace, inParagraph, inParagraphListItem, allowLeadingSpace, interruptsParagraph, interruptsItemParagraph, withLeadSpacesInterruptsItemParagraph)) {
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
