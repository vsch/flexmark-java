package com.vladsch.flexmark.internal;

import com.vladsch.flexmark.internal.util.BasedSequence;
import com.vladsch.flexmark.internal.util.DataHolder;
import com.vladsch.flexmark.node.Block;
import com.vladsch.flexmark.node.ThematicBreak;
import com.vladsch.flexmark.parser.block.*;

import java.util.regex.Pattern;

public class ThematicBreakParser extends AbstractBlockParser {

    private static Pattern PATTERN = Pattern.compile("^(?:(?:\\* *){3,}|(?:_ *){3,}|(?:- *){3,}) *$");

    private final ThematicBreak block = new ThematicBreak();

    public ThematicBreakParser(BasedSequence line) {
        block.setChars(line);
    }

    @Override
    public Block getBlock() {
        return block;
    }

    @Override
    public void closeBlock(ParserState parserState) {
        block.setCharsFromContent();
    }

    @Override
    public BlockContinue tryContinue(ParserState state) {
        // a horizontal rule can never container > 1 line, so fail to match
        return BlockContinue.none();
    }

    public static class Factory implements CustomBlockParserFactory {
        @Override
        public BlockParserFactory create(DataHolder options) {
            return new BlockFactory(options);
        }
    }

    private static class BlockFactory extends AbstractBlockParserFactory {
        final private ThematicBreakOptions options;
        
        private BlockFactory(DataHolder options) {
            super(options);
            this.options = new ThematicBreakOptions(options);
        }

        @Override
        public BlockStart tryStart(ParserState state, MatchedBlockParser matchedBlockParser) {
            if (state.getIndent() >= 4 || matchedBlockParser.getMatchedBlockParser().isParagraphParser() && !options.relaxedStart) {
                return BlockStart.none();
            }
            int nextNonSpace = state.getNextNonSpaceIndex();
            BasedSequence line = state.getLine();
            if (PATTERN.matcher(line.subSequence(nextNonSpace, line.length())).matches()) {
                return BlockStart.of(new ThematicBreakParser(line)).atIndex(line.length());
            } else {
                return BlockStart.none();
            }
        }
    }
}
