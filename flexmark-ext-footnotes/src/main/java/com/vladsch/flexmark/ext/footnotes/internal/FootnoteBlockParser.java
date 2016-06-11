package com.vladsch.flexmark.ext.footnotes.internal;

import com.vladsch.flexmark.ext.footnotes.FootnoteBlock;
import com.vladsch.flexmark.internal.BlockContent;
import com.vladsch.flexmark.internal.util.BasedSequence;
import com.vladsch.flexmark.node.Block;
import com.vladsch.flexmark.parser.InlineParser;
import com.vladsch.flexmark.parser.block.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FootnoteBlockParser extends AbstractBlockParser {
    static String FOOTNOTE_ID = ".*";
    static Pattern FOOTNOTE_ID_PATTERN = Pattern.compile("\\[\\^\\s*(" + FOOTNOTE_ID + ")\\s*\\]");
    static Pattern FOOTNOTE_DEF_PATTERN = Pattern.compile("^\\[\\^\\s*(" + FOOTNOTE_ID + ")\\s*\\]:");

    private final FootnoteBlock block = new FootnoteBlock();
    private BlockContent content = new BlockContent();
    private int contentIndent;

    public FootnoteBlockParser(int contentIndent) {
        this.contentIndent = contentIndent;
    }

    public BlockContent getContent() {
        return content;
    }

    @Override
    public Block getBlock() {
        return block;
    }

    @Override
    public BlockContinue tryContinue(ParserState state) {
        if (state.isBlank()) {
            if (block.getFirstChild() == null) {
                // Blank line after empty list item
                return BlockContinue.none();
            } else {
                return BlockContinue.atIndex(state.getNextNonSpaceIndex());
            }
        }

        if (state.getIndent() >= contentIndent) {
            return BlockContinue.atColumn(state.getColumn() + contentIndent);
        } else {
            return BlockContinue.none();
        }
    }

    @Override
    public void addLine(BasedSequence line, int eolLength) {
        content.add(line, eolLength);
    }

    @Override
    public void closeBlock(ParserState parserState) {
        // add it to the map
        FootnoteRepository footnoteMap = parserState.getPropertyHolder().getValueOrNew(FootnoteRepository.PROPERTY_KEY);
        footnoteMap.put(footnoteMap.normalizeKey(block.getText()), block);
    }

    @Override
    public void parseInlines(InlineParser inlineParser) {
        if (content != null) {
            inlineParser.parse(content.getContents(), block);
        }
    }

    @Override
    public boolean isContainer() {
        return true;
    }

    @Override
    public boolean canContain(Block block) {
        return true;
    }

    public static class Factory extends AbstractBlockParserFactory {

        @Override
        public BlockStart tryStart(ParserState state, MatchedBlockParser matchedBlockParser) {
            if (state.getIndent() >= 4) {
                return BlockStart.none();
            }

            BasedSequence line = state.getLine();
            int nextNonSpace = state.getNextNonSpaceIndex();

            BasedSequence trySequence = line.subSequence(nextNonSpace, line.length());
            Matcher matcher = FOOTNOTE_DEF_PATTERN.matcher(trySequence);
            if (matcher.find()) {
                // abbreviation definition
                int openingStart = nextNonSpace + matcher.start();
                int openingEnd = nextNonSpace + matcher.end();
                BasedSequence openingMarker = line.subSequence(openingStart, openingStart + 2);
                BasedSequence text = line.subSequence(openingStart + 2, openingEnd - 2).trim();
                BasedSequence closingMarker = line.subSequence(openingEnd - 2, openingEnd);

                FootnoteBlockParser footnoteBlockParser = new FootnoteBlockParser(state.getIndent() + 4);
                footnoteBlockParser.block.setOpeningMarker(openingMarker);
                footnoteBlockParser.block.setText(text);
                footnoteBlockParser.block.setClosingMarker(closingMarker);
                footnoteBlockParser.block.setFootnote(trySequence.subSequence(matcher.end()).trim());
                footnoteBlockParser.block.setCharsFromContent();

                return BlockStart.of(footnoteBlockParser)
                                 .atIndex(openingEnd);
            } else {
                return BlockStart.none();
            }
        }
    }
}
