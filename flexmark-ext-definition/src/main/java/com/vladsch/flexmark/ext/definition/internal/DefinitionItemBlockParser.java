package com.vladsch.flexmark.ext.definition.internal;

import com.vladsch.flexmark.ast.Paragraph;
import com.vladsch.flexmark.ast.util.Parsing;
import com.vladsch.flexmark.ext.definition.DefinitionExtension;
import com.vladsch.flexmark.ext.definition.DefinitionItem;
import com.vladsch.flexmark.parser.InlineParser;
import com.vladsch.flexmark.parser.ParserEmulationProfile;
import com.vladsch.flexmark.parser.block.*;
import com.vladsch.flexmark.parser.core.DocumentBlockParser;
import com.vladsch.flexmark.parser.core.ParagraphParser;
import com.vladsch.flexmark.util.ast.BlankLine;
import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.misc.CharPredicate;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.mappers.SpecialLeadInCharsHandler;
import com.vladsch.flexmark.util.sequence.mappers.SpecialLeadInHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

import static com.vladsch.flexmark.parser.ParserEmulationProfile.*;

public class DefinitionItemBlockParser extends AbstractBlockParser {
    final private DefinitionItem block;
    //private BlockContent content = new BlockContent();
    final private DefinitionOptions options;
    final private ItemData itemData;
    private boolean hadBlankLine;

    DefinitionItemBlockParser(DataHolder options, ItemData itemData) {
        this.options = new DefinitionOptions(options);
        this.itemData = itemData;
        this.block = new DefinitionItem();
        this.block.setOpeningMarker(itemData.itemMarker);
        this.block.setTight(itemData.isTight);
    }

    static class ItemData {
        final boolean isEmpty;
        final boolean isTight;
        final int markerIndex;
        final int markerColumn;
        final int markerIndent;
        final int contentOffset;
        final BasedSequence itemMarker;

        ItemData(boolean isEmpty, boolean isTight, int markerIndex, int markerColumn, int markerIndent, int contentOffset, BasedSequence itemMarker) {
            this.isEmpty = isEmpty;
            this.isTight = isTight;
            this.markerIndex = markerIndex;
            this.markerColumn = markerColumn;
            this.markerIndent = markerIndent;
            this.contentOffset = contentOffset;
            this.itemMarker = itemMarker;
        }
    }

    private int getContentIndent() {
        return itemData.markerIndent + itemData.itemMarker.length() + itemData.contentOffset;
    }

    @Override
    public Block getBlock() {
        return block;
    }

    @Override
    public boolean isContainer() {
        return true;
    }

    @Override
    public boolean canContain(ParserState state, BlockParser blockParser, final Block block) {
        return true;
    }

    static ItemData parseItemMarker(DefinitionOptions options, ParserState state, boolean isTight) {
        BasedSequence line = state.getLine();
        int markerIndex = state.getNextNonSpaceIndex();
        int markerColumn = state.getColumn() + state.getIndent();
        int markerIndent = state.getIndent();

        BasedSequence rest = line.subSequence(markerIndex, line.length());
        final char c1 = rest.firstChar();
        if (!(c1 == ':' && options.colonMarker) && !(c1 == '~' && options.tildeMarker)) {
            return null;
        }

        // marker doesn't include tabs, so counting them as columns directly is ok
        // the column within the line where the content starts
        int contentOffset = 0;

        // See at which column the content starts if there is content
        boolean hasContent = false;
        for (int i = markerIndex + 1; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '\t') {
                contentOffset += Parsing.columnsToNextTabStop(markerColumn + 1 + contentOffset);
            } else if (c == ' ') {
                contentOffset++;
            } else {
                hasContent = true;
                break;
            }
        }

        if (hasContent && contentOffset < options.markerSpaces) {
            return null;
        }

        if (!hasContent || options.myParserEmulationProfile == COMMONMARK && contentOffset > options.newItemCodeIndent) {
            // If this line is blank or has a code block, default to 1 space after marker
            contentOffset = 1;
        }

        return new ItemData(!hasContent, isTight, markerIndex, markerColumn, markerIndent, contentOffset, rest.subSequence(0, 1));
    }

    @Override
    public BlockContinue tryContinue(ParserState state) {
        Node firstChild = block.getFirstChild();
        boolean isEmpty = firstChild == null;

        if (state.isBlank()) {
            if (isEmpty || firstChild.getNext() == null) {
                block.setHadBlankAfterItemParagraph(true);
            }
            hadBlankLine = true;
            return BlockContinue.atIndex(state.getNextNonSpaceIndex());
        }

        ParserEmulationProfile emulationFamily = options.myParserEmulationProfile.family;
        if (emulationFamily == COMMONMARK || emulationFamily == KRAMDOWN || emulationFamily == MARKDOWN) {
            int currentIndent = state.getIndent();
            int newColumn = state.getColumn() + getContentIndent();

            if (currentIndent >= getContentIndent()) {
                // our child element
                return BlockContinue.atColumn(newColumn);
            } else {
                if (isEmpty) {
                    return BlockContinue.atIndex(state.getIndex() + currentIndent);
                }

                ItemData itemData = parseItemMarker(options, state, false);

                if (itemData != null) {
                    return BlockContinue.none();
                }

                if (!hadBlankLine) {
                    return BlockContinue.atIndex(state.getIndex() + currentIndent);
                }
            }
        } else if (emulationFamily == FIXED_INDENT) {
            int currentIndent = state.getIndent();

            // advance by item indent
            int newColumn = state.getColumn() + options.itemIndent;

            if (currentIndent >= options.itemIndent) {
                // our child element
                return BlockContinue.atColumn(newColumn);
            } else {
                if (isEmpty) {
                    return BlockContinue.atIndex(state.getIndex() + currentIndent);
                }

                ItemData itemData = parseItemMarker(options, state, false);

                if (itemData != null) {
                    return BlockContinue.none();
                }

                if (!hadBlankLine) {
                    return BlockContinue.atIndex(state.getIndex() + currentIndent);
                }
            }
        }
        return BlockContinue.none();
    }

    @Override
    public void addLine(ParserState state, BasedSequence line) {

    }

    @Override
    public void closeBlock(ParserState state) {
        block.setCharsFromContent();
    }

    @Override
    public void parseInlines(InlineParser inlineParser) {

    }

    public static class Factory implements CustomBlockParserFactory {
        @Nullable
        @Override
        public Set<Class<?>> getAfterDependents() {
            return null;
            //return new HashSet<>(Arrays.asList(
            //        BlockQuoteParser.Factory.class,
            //        HeadingParser.Factory.class,
            //        FencedCodeBlockParser.Factory.class,
            //        HtmlBlockParser.Factory.class,
            //        ThematicBreakParser.Factory.class,
            //        ListBlockParser.Factory.class,
            //        IndentedCodeBlockParser.Factory.class
            //));
        }

        @Nullable
        @Override
        public Set<Class<?>> getBeforeDependents() {
            return null;
            //return new HashSet<>(Arrays.asList(
            //        BlockQuoteParser.Factory.class,
            //        HeadingParser.Factory.class,
            //        FencedCodeBlockParser.Factory.class,
            //        HtmlBlockParser.Factory.class,
            //        ThematicBreakParser.Factory.class,
            //        ListBlockParser.Factory.class,
            //        IndentedCodeBlockParser.Factory.class
            //));
        }

        @Override
        public @Nullable SpecialLeadInHandler getLeadInHandler(@NotNull DataHolder options) {
            boolean colon = DefinitionExtension.COLON_MARKER.get(options);
            boolean tilde = DefinitionExtension.TILDE_MARKER.get(options);
            return colon && tilde ? DefinitionLeadInHandler.HANDLER_COLON_TILDE : colon ? DefinitionLeadInHandler.HANDLER_COLON : tilde ? DefinitionLeadInHandler.HANDLER_TILDE : null;
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

    static class DefinitionLeadInHandler {
        final static SpecialLeadInHandler HANDLER_COLON_TILDE = SpecialLeadInCharsHandler.create(":~");
        final static SpecialLeadInHandler HANDLER_COLON = SpecialLeadInCharsHandler.create(":");
        final static SpecialLeadInHandler HANDLER_TILDE = SpecialLeadInCharsHandler.create("~");
    }

    private static class BlockFactory extends AbstractBlockParserFactory {
        final private DefinitionOptions options;

        BlockFactory(DataHolder options) {
            super(options);
            this.options = new DefinitionOptions(options);
        }

        @Override
        public BlockStart tryStart(ParserState state, MatchedBlockParser matchedBlockParser) {
            final BlockParser blockParser = matchedBlockParser.getBlockParser();
            if (blockParser instanceof DocumentBlockParser) {
                // if document has paragraph or another definition item at end then we can proceed
                final Document node = ((DocumentBlockParser) blockParser).getBlock();
                Block lastChildAnyNot = (Block) node.getLastChildAnyNot(BlankLine.class);
                if (!(lastChildAnyNot instanceof Paragraph || lastChildAnyNot instanceof DefinitionItem)) {
                    return BlockStart.none();
                }

                // check if we break list on double blank
                if (options.doubleBlankLineBreaksList) {
                    // intervening characters between previous paragraph and definition terms
                    lastChildAnyNot.setCharsFromContent();
                    CharSequence charSequence = state.getLine().baseSubSequence(lastChildAnyNot.getEndOffset(), state.getLine().getStartOffset()).normalizeEOL();
                    final BasedSequence interSpace = BasedSequence.of(charSequence);
                    if (interSpace.countLeading(CharPredicate.EOL) >= 2) {
                        return BlockStart.none();
                    }
                }
            } else if (!(blockParser instanceof DefinitionItemBlockParser || blockParser instanceof ParagraphParser)) {
                return BlockStart.none();
            }

            ParserEmulationProfile emulationFamily = options.myParserEmulationProfile;

            int currentIndent = state.getIndent();
            int codeIndent = emulationFamily == COMMONMARK || emulationFamily == FIXED_INDENT ? options.codeIndent : options.itemIndent;

            if (currentIndent < codeIndent) {
                ItemData itemData = parseItemMarker(options, state, state.getActiveBlockParser() instanceof ParagraphParser);
                if (itemData != null) {
                    final BlockStart blockStart = BlockStart.of(new DefinitionItemBlockParser(state.getProperties(), itemData))
                            .atColumn(itemData.markerColumn + itemData.itemMarker.length() + itemData.contentOffset);
                    return blockStart;
                }
            }
            return BlockStart.none();
        }
    }
}
