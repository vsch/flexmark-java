package com.vladsch.flexmark.ext.jekyll.front.matter.internal;

import com.vladsch.flexmark.ext.jekyll.front.matter.JekyllFrontMatterBlock;
import com.vladsch.flexmark.parser.InlineParser;
import com.vladsch.flexmark.parser.block.*;
import com.vladsch.flexmark.parser.core.DocumentBlockParser;
import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.ast.BlockContent;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JekyllFrontMatterBlockParser extends AbstractBlockParser {
    final static Pattern JEKYLL_FRONT_MATTER_BLOCK_START = Pattern.compile("^-{3}(\\s.*)?");
    final static Pattern JEKYLL_FRONT_MATTER_BLOCK_END = Pattern.compile("^(-{3}|\\.{3})(\\s.*)?");

    final private JekyllFrontMatterBlock block = new JekyllFrontMatterBlock();
    private BlockContent content = new BlockContent();
    private boolean inYAMLBlock;

    JekyllFrontMatterBlockParser(DataHolder options, BasedSequence openingMarker) {
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
    public void closeBlock(ParserState state) {
        block.setContent(content.getLines().subList(1, content.getLineCount()));
        block.setCharsFromContent();
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
        }

        @Nullable
        @Override
        public Set<Class<?>> getBeforeDependents() {
            return null;
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
        BlockFactory(DataHolder options) {
            super(options);
        }

        @Override
        public BlockStart tryStart(ParserState state, MatchedBlockParser matchedBlockParser) {
            BasedSequence line = state.getLine();
            BlockParser parentParser = matchedBlockParser.getBlockParser();
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
