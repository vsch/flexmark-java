package com.vladsch.flexmark.ext.zzzzzz.internal;

import com.vladsch.flexmark.ext.zzzzzz.ZzzzzzBlock;
import com.vladsch.flexmark.parser.InlineParser;
import com.vladsch.flexmark.parser.block.*;
import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.ast.BlockContent;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.regex.Pattern;

public class ZzzzzzBlockParser extends AbstractBlockParser {
    private static String COL = "\\s*:?-{3,}:?\\s*";
    static Pattern ZZZZZZ_BLOCK_START = Pattern.compile("ZzzzzzNoWayzzzzzZ");
    private static Pattern ZZZZZZ_BLOCK_CONTINUE = Pattern.compile("");

    final private ZzzzzzBlock block = new ZzzzzzBlock();
    private BlockContent content = new BlockContent();
    final private ZzzzzzOptions options;

    ZzzzzzBlockParser(DataHolder options) {
        this.options = new ZzzzzzOptions(options);
    }

    @Override
    public Block getBlock() {
        return block;
    }

    @Override
    public BlockContinue tryContinue(ParserState state) {
        if (ZZZZZZ_BLOCK_CONTINUE.matcher(state.getLine()).matches()) {
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
    public void closeBlock(ParserState state) {
        block.setContent(content);
        content = null;
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
        public boolean affectsGlobalScope() {
            return false;
        }

        @NotNull
        @Override
        public BlockParserFactory apply(@NotNull DataHolder options) {
            return new BlockFactory(options);
        }
    }

    private static class BlockFactory extends AbstractBlockParserFactory {
        final private ZzzzzzOptions options;

        BlockFactory(DataHolder options) {
            super(options);
            this.options = new ZzzzzzOptions(options);
        }

        @Override
        public BlockStart tryStart(ParserState state, MatchedBlockParser matchedBlockParser) {
            BasedSequence line = state.getLine();
            if (ZZZZZZ_BLOCK_START.matcher(line).matches()) {
                return BlockStart.of(new ZzzzzzBlockParser(state.getProperties()))
                        .atIndex(state.getIndex())
                        //.replaceActiveBlockParser()
                        ;
            }
            return BlockStart.none();
        }
    }
}
