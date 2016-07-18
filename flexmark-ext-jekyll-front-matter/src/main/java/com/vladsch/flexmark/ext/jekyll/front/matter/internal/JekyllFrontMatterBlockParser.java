package com.vladsch.flexmark.ext.jekyll.front.matter.internal;

import com.vladsch.flexmark.ext.front.matter.internal.YamlFrontMatterBlockParser;
import com.vladsch.flexmark.ext.jekyll.front.matter.JekyllFrontMatterBlock;
import com.vladsch.flexmark.internal.BlockContent;
import com.vladsch.flexmark.internal.DocumentBlockParser;
import com.vladsch.flexmark.internal.util.options.DataHolder;
import com.vladsch.flexmark.internal.util.sequence.BasedSequence;
import com.vladsch.flexmark.node.Block;
import com.vladsch.flexmark.parser.InlineParser;
import com.vladsch.flexmark.parser.block.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JekyllFrontMatterBlockParser extends AbstractBlockParser {
    private static String COL = "\\s*:?-{3,}:?\\s*";
    static Pattern JEKYLL_FRONT_MATTER_BLOCK_START = Pattern.compile("^-{3}(\\s.*)?");
    private static Pattern JEKYLL_FRONT_MATTER_BLOCK_END = Pattern.compile("^(-{3}|\\.{3})(\\s.*)?");
    //private static Pattern JEKYLL_FRONT_MATTER_BLOCK_END = Pattern.compile("^(-{3})(\\s.*)?");

    private final JekyllFrontMatterBlock block = new JekyllFrontMatterBlock();
    private BlockContent content = new BlockContent();
    private final JekyllFrontMatterOptions options;
    private boolean inYAMLBlock;

    JekyllFrontMatterBlockParser(DataHolder options, BasedSequence openingMarker) {
        this.options = new JekyllFrontMatterOptions(options);
        inYAMLBlock = true;
        block.setOpeningMarker(openingMarker);
    }

    @Override
    public Block getBlock() {
        return block;
    }

    @Override
    public BlockContinue tryContinue(ParserState state) {
        final BasedSequence line = state.getLine();
        if (inYAMLBlock) {
            Matcher matcher = JEKYLL_FRONT_MATTER_BLOCK_END.matcher(line);
            if (matcher.matches()) {
                block.setClosingMarker(line.subSequence(matcher.start(1), matcher.end(1)));
                return BlockContinue.finished();
            }
            return BlockContinue.atIndex(state.getIndex());
        } else if (JEKYLL_FRONT_MATTER_BLOCK_START.matcher(line).matches()) {
            inYAMLBlock = true;
            return BlockContinue.atIndex(state.getIndex());
        }
        return BlockContinue.none();
    }

    @Override
    public void addLine(ParserState state, BasedSequence line) {
        content.add(line, state.getIndent());
    }

    @Override
    public void closeBlock(ParserState parserState) {
        block.setContent(content.getLines().subList(1, content.getLineCount()));
        block.setCharsFromContent();
        content = null;
    }

    @Override
    public void parseInlines(InlineParser inlineParser) {
    }

    public static class Factory implements CustomBlockParserFactory {
        @Override
        public Set<Class<? extends CustomBlockParserFactory>> getAfterDependents() {
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
        public Set<Class<? extends CustomBlockParserFactory>> getBeforeDependents() {
            return new HashSet<>(Arrays.asList(
                    YamlFrontMatterBlockParser.Factory.class
                    //        BlockQuoteParser.Factory.class,
                    //        HeadingParser.Factory.class,
                    //        FencedCodeBlockParser.Factory.class,
                    //        HtmlBlockParser.Factory.class,
                    //        ThematicBreakParser.Factory.class,
                    //        ListBlockParser.Factory.class,
                    //        IndentedCodeBlockParser.Factory.class
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
        private final JekyllFrontMatterOptions options;

        private BlockFactory(DataHolder options) {
            super(options);
            this.options = new JekyllFrontMatterOptions(options);
        }

        @Override
        public BlockStart tryStart(ParserState state, MatchedBlockParser matchedBlockParser) {
            BasedSequence line = state.getLine();
            BlockParser parentParser = matchedBlockParser.getMatchedBlockParser();
            if (parentParser instanceof DocumentBlockParser && parentParser.getBlock().getFirstChild() == null) {
                Matcher matcher = JEKYLL_FRONT_MATTER_BLOCK_START.matcher(line);
                if (matcher.matches()) {
                    BasedSequence openingMarker = line.subSequence(0, 3);
                    JekyllFrontMatterBlockParser parser = new JekyllFrontMatterBlockParser(state.getProperties(), openingMarker);
                    return BlockStart.of(parser).atIndex(-1);
                }
            }

            return BlockStart.none();
        }
    }
}
