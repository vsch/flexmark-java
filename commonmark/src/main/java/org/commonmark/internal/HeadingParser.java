package org.commonmark.internal;

import org.commonmark.internal.util.BasedSequence;
import org.commonmark.node.Block;
import org.commonmark.node.Heading;
import org.commonmark.parser.InlineParser;
import org.commonmark.parser.block.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HeadingParser extends AbstractBlockParser {

    private static Pattern ATX_HEADING = Pattern.compile("^#{1,6}(?: +|$)");
    private static Pattern ATX_TRAILING = Pattern.compile("(^| ) *#+ *$");
    private static Pattern SETEXT_HEADING = Pattern.compile("^(?:=+|-+) *$");

    private final Heading block = new Heading();
    private final BlockContent content;

    public HeadingParser(int level, BlockContent content) {
        block.setLevel(level);
        this.content = content;
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
        inlineParser.parse(content.getContents(), block);
    }

    public static class Factory extends AbstractBlockParserFactory {

        @Override
        public BlockStart tryStart(ParserState state, MatchedBlockParser matchedBlockParser) {
            if (state.getIndent() >= 4) {
                return BlockStart.none();
            }
            BasedSequence line = state.getLine();
            int nextNonSpace = state.getNextNonSpaceIndex();
            BasedSequence paragraph = matchedBlockParser.getParagraphContent();
            Matcher matcher;
            if ((matcher = ATX_HEADING.matcher(line.subSequence(nextNonSpace, line.length()))).find()) {
                // ATX heading
                int newOffset = nextNonSpace + matcher.group(0).length();
                int level = matcher.group(0).trim().length(); // number of #s

                BlockContent content = new BlockContent();
                content.add(line.subSequence(newOffset, line.length()), line.baseSubSequence(state.getLineEOL(), state.getLineEnd()));

                // remove trailing ###s:
                //String content = ATX_TRAILING.matcher().;

                return BlockStart.of(new HeadingParser(level, content))
                        .atIndex(line.length());

            } else if (paragraph != null && ((matcher = SETEXT_HEADING.matcher(line.subSequence(nextNonSpace, line.length()))).find())) {
                // setext heading line
                int level = matcher.group(0).charAt(0) == '=' ? 1 : 2;

                BlockContent content = new BlockContent();
                content.addAll(matchedBlockParser.getParagraphLines(), matchedBlockParser.getParagraphEOLs());

                return BlockStart.of(new HeadingParser(level, content))
                        .atIndex(line.length())
                        .replaceActiveBlockParser();
            } else {
                return BlockStart.none();
            }
        }
    }
}
