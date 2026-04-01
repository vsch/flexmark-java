package com.vladsch.flexmark.ext.docusaurus.admonition;

import com.vladsch.flexmark.parser.block.*;
import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.ast.BlockContent;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

class DocusaurusAdmonitionBlockParser extends AbstractBlockParser {
    private final DocusaurusAdmonitionBlock block = new DocusaurusAdmonitionBlock();
    private BlockContent content = new BlockContent();
    private boolean hadClose = false;

    private final String closingLine;

    public DocusaurusAdmonitionBlockParser(BasedSequence openingMarker, BasedSequence type, int nestingLevel) {
        block.setOpeningMarker(openingMarker);
        block.setType(type);
        closingLine = ":".repeat(nestingLevel);
    }

    @Override
    public Block getBlock() {
        return block;
    }

    @Override
    public BlockContinue tryContinue(ParserState state) {

        if (hadClose) {
            return BlockContinue.none();
        }

        BasedSequence line = state.getLine();

        boolean isClosingLine = line.matches(closingLine);

        if (isClosingLine) {
            block.setClosingMarker(line);
            hadClose = true;
            return BlockContinue.atIndex(state.getLineEndIndex());
        }

        return BlockContinue.atIndex(state.getIndex());
    }

    @Override
    public boolean isContainer() {
        return true; // can contain other blocks
    }

    @Override
    public boolean canContain(ParserState state, BlockParser blockParser, Block block) {
        return true; // can contain anything
    }

    @Override
    public void addLine(ParserState state, BasedSequence line) {
        content.add(line, state.getIndent());

    }

    @Override
    public void closeBlock(ParserState state) {
        block.setContent(content);
        block.setCharsFromContent();

        content = null;
    }

    public static class Factory implements CustomBlockParserFactory {
        @Override
        public @NotNull BlockParserFactory apply(@NotNull DataHolder options) {
            return new BlockFactory(options);
        }

        @Override
        public Set<Class<?>> getAfterDependents() {
            return null;
        }

        @Override
        public Set<Class<?>> getBeforeDependents() {
            return null;
        }

        @Override
        public boolean affectsGlobalScope() {
            return false;
        }
    }

    private static class BlockFactory extends AbstractBlockParserFactory {
        BlockFactory(DataHolder options) {
            super(options);
        }

        @Override
        public BlockStart tryStart(ParserState state, MatchedBlockParser matchedBlockParser) {
            BasedSequence line = state.getLine();
            if (line.startsWith(":::")) {
                int colons = 0;
                while (colons < line.length() && line.charAt(colons) == ':') {
                    colons++;
                }
                BasedSequence type = line.subSequence(colons).trim();
                if (!type.isEmpty() && !type.toString().contains(" ")) {
                    return BlockStart.of(new DocusaurusAdmonitionBlockParser(line.subSequence(0, colons), type, colons))
                            .atIndex(line.length());
                }
            }
            return BlockStart.none();
        }
    }
}
