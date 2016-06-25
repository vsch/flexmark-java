package com.vladsch.flexmark.ext.wikilink.internal;

import com.vladsch.flexmark.ext.wikilink.ModuleBlock;
import com.vladsch.flexmark.internal.BlockContent;
import com.vladsch.flexmark.internal.util.BasedSequence;
import com.vladsch.flexmark.internal.util.DataHolder;
import com.vladsch.flexmark.node.Block;
import com.vladsch.flexmark.parser.InlineParser;
import com.vladsch.flexmark.parser.block.*;

import java.util.regex.Pattern;

public class ModuleBlockParser extends AbstractBlockParser {
    private static String COL = "\\s*:?-{3,}:?\\s*";
    private static Pattern MODULE_BLOCK_START = Pattern.compile("");
    private static Pattern MODULE_BLOCK_CONTINUE = Pattern.compile("");

    private final ModuleBlock block = new ModuleBlock();
    private BlockContent content = new BlockContent();
    private final ModuleOptions options;

    private ModuleBlockParser(DataHolder options) {
        this.options = new ModuleOptions(options);
    }

    @Override
    public Block getBlock() {
        return block;
    }

    @Override
    public BlockContinue tryContinue(ParserState state) {
        if (MODULE_BLOCK_CONTINUE.matcher(state.getLine()).matches()) {
            return BlockContinue.atIndex(state.getIndex());
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
        block.setContent(content);
        content = null;
    }

    @Override
    public void parseInlines(InlineParser inlineParser) {
    }

    public static class Factory extends AbstractBlockParserFactory {

        @Override
        public BlockStart tryStart(ParserState state, MatchedBlockParser matchedBlockParser) {
            BasedSequence line = state.getLine();
            if (MODULE_BLOCK_START.matcher(line).matches()) {
                return BlockStart.of(new ModuleBlockParser(state.getProperties()))
                        .atIndex(state.getIndex())
                        //.replaceActiveBlockParser()
                        ;
            }
            return BlockStart.none();
        }
    }
}
