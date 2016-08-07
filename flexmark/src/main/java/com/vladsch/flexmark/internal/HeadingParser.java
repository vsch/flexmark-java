package com.vladsch.flexmark.internal;

import com.vladsch.flexmark.ast.Block;
import com.vladsch.flexmark.ast.BlockContent;
import com.vladsch.flexmark.ast.Heading;
import com.vladsch.flexmark.ast.util.Parsing;
import com.vladsch.flexmark.parser.InlineParser;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.block.*;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HeadingParser extends AbstractBlockParser {

    static class HeadingParsing extends Parsing {
        final private Pattern ATX_HEADING;
        final private Pattern ATX_TRAILING;
        final private Pattern SETEXT_HEADING;

        public HeadingParsing(DataHolder options) {
            super(options);

            ATX_HEADING = Parser.HEADING_NO_ATX_SPACE.getFrom(options) ? Pattern.compile("^#{1,6}(?: *|$)") : Pattern.compile("^#{1,6}(?:[ \t]+|$)");
            ATX_TRAILING = Pattern.compile("(^| ) *#+ *$");

            int minLength = Parser.HEADING_SETEXT_MARKER_LENGTH.getFrom(options);
            SETEXT_HEADING = minLength <= 1 ? Pattern.compile("^(?:=+|-+) *$") : Pattern.compile("^(?:={" + minLength + ",}|-{" + minLength + ",}) *$");
        }
    }

    final Heading block = new Heading();

    public HeadingParser(int level) {
        block.setLevel(level);
    }

    @Override
    public Block getBlock() {
        return block;
    }

    @Override
    public BlockContinue tryContinue(ParserState state) {
        // In both ATX and Setext headings, once we have the heading markup, there's nothing more to parse.
        return BlockContinue.none();
    }

    @Override
    public void parseInlines(InlineParser inlineParser) {
        inlineParser.parse(block.getText(), block);
    }

    @Override
    public void closeBlock(ParserState state) {
    }

    public static class Factory implements CustomBlockParserFactory {
        @Override
        public Set<Class<? extends CustomBlockParserFactory>> getAfterDependents() {
            return new HashSet<>(Arrays.asList(
                    BlockQuoteParser.Factory.class
                    //HeadingParser.Factory.class,
                    //FencedCodeBlockParser.Factory.class,
                    //HtmlBlockParser.Factory.class,
                    //ThematicBreakParser.Factory.class,
                    //ListBlockParser.Factory.class,
                    //IndentedCodeBlockParser.Factory.class
            ));
        }

        @Override
        public Set<Class<? extends CustomBlockParserFactory>> getBeforeDependents() {
            return new HashSet<>(Arrays.asList(
                    //BlockQuoteParser.Factory.class,
                    //HeadingParser.Factory.class,
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
        final private HeadingOptions options;
        final private HeadingParsing myParsing;

        BlockFactory(DataHolder options) {
            super(options);
            this.options = new HeadingOptions(options);
            this.myParsing = new HeadingParsing(options);
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
            matcher = myParsing.ATX_HEADING.matcher(trySequence);
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
                matcher = myParsing.ATX_TRAILING.matcher(headerText);
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
            } else if (paragraph != null && ((matcher = myParsing.SETEXT_HEADING.matcher(trySequence)).find())) {
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
