package com.vladsch.flexmark.internal;

import com.vladsch.flexmark.internal.util.collection.DataHolder;
import com.vladsch.flexmark.internal.util.sequence.BasedSequence;
import com.vladsch.flexmark.node.Block;
import com.vladsch.flexmark.node.Heading;
import com.vladsch.flexmark.parser.InlineParser;
import com.vladsch.flexmark.parser.block.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HeadingParser extends AbstractBlockParser {

    private static Pattern ATX_HEADING = Pattern.compile("^#{1,6}(?: +|$)");
    private static Pattern RELAXED_ATX_HEADING = Pattern.compile("^#{1,6}(?: *|$)");
    private static Pattern ATX_TRAILING = Pattern.compile("(^| ) *#+ *$");
    private static Pattern SETEXT_HEADING = Pattern.compile("^(?:=+|-+) *$");

    private final Heading block = new Heading();

    public HeadingParser(int level) {
        block.setLevel(level);
    }

    @Override
    public Block getBlock() {
        return block;
    }

    @Override
    public BlockContinue tryContinue(ParserState parserState) {
        // In both ATX and Setext headings, once we have the heading markup, there's nothing more to parse.
        return BlockContinue.none();
    }

    @Override
    public void parseInlines(InlineParser inlineParser) {
        inlineParser.parse(block.getText(), block);
    }

    @Override
    public void closeBlock(ParserState parserState) {
    }

    public static class Factory implements CustomBlockParserFactory {
        @Override
        public BlockParserFactory create(DataHolder options) {
            return new BlockFactory(options);
        }
    }

    private static class BlockFactory extends AbstractBlockParserFactory {
        private final HeadingOptions options;

        private BlockFactory(DataHolder options) {
            super(options);
            this.options = new HeadingOptions(options);
        }

        @Override
        public BlockStart tryStart(ParserState state, MatchedBlockParser matchedBlockParser) {
            if (state.getIndent() >= 4 || options.headersNoLeadSpace && state.getIndent() >= 1) {
                return BlockStart.none();
            }
            BasedSequence line = state.getLine();
            int nextNonSpace = state.getNextNonSpaceIndex();
            BasedSequence paragraph = matchedBlockParser.getParagraphContent();
            Matcher matcher;
            BasedSequence trySequence = line.subSequence(nextNonSpace, line.length());
            matcher = (options.headersNoAtxSpace ? RELAXED_ATX_HEADING : ATX_HEADING).matcher(trySequence);
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
                matcher = ATX_TRAILING.matcher(headerText);
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

                return BlockStart.of(headingParser)
                        .atIndex(line.length());
            } else if (paragraph != null && ((matcher = SETEXT_HEADING.matcher(trySequence)).find())) {
                // setext heading line
                int level = matcher.group(0).charAt(0) == '=' ? 1 : 2;

                BlockContent content = new BlockContent();
                content.addAll(matchedBlockParser.getParagraphLines(), matchedBlockParser.getParagraphEolLengths());
                BasedSequence headingText = content.getContents().trim();
                BasedSequence closingMarker = line.trim();

                HeadingParser headingParser = new HeadingParser(level);
                headingParser.block.setText(headingText);
                headingParser.block.setClosingMarker(closingMarker);
                headingParser.block.setCharsFromContent();

                return BlockStart.of(headingParser)
                        .atIndex(line.length())
                        .replaceActiveBlockParser();
            } else {
                return BlockStart.none();
            }
        }
    }
}
