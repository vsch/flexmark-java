package com.vladsch.flexmark.parser.core;

import com.vladsch.flexmark.ast.CodeBlock;
import com.vladsch.flexmark.ast.IndentedCodeBlock;
import com.vladsch.flexmark.ast.Paragraph;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.block.*;
import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.ast.BlockContent;
import com.vladsch.flexmark.util.collection.iteration.Reverse;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class IndentedCodeBlockParser extends AbstractBlockParser {

    final private IndentedCodeBlock block = new IndentedCodeBlock();
    private BlockContent content = new BlockContent();
    final private boolean trimTrailingBlankLines;
    final private boolean codeContentBlock;

    public IndentedCodeBlockParser(DataHolder options) {
        trimTrailingBlankLines = Parser.INDENTED_CODE_NO_TRAILING_BLANK_LINES.get(options);
        this.codeContentBlock = Parser.FENCED_CODE_CONTENT_BLOCK.get(options);
    }

    @Override
    public Block getBlock() {
        return block;
    }

    @Override
    public BlockContinue tryContinue(ParserState state) {
        if (state.getIndent() >= state.getParsing().CODE_BLOCK_INDENT) {
            return BlockContinue.atColumn(state.getColumn() + state.getParsing().CODE_BLOCK_INDENT);
        } else if (state.isBlank()) {
            return BlockContinue.atIndex(state.getNextNonSpaceIndex());
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
        // trim trailing blank lines out of the block
        if (trimTrailingBlankLines) {
            int trailingBlankLines = 0;
            List<BasedSequence> lines = content.getLines();
            for (BasedSequence line : new Reverse<>(lines)) {
                if (!line.isBlank()) break;
                trailingBlankLines++;
            }

            if (trailingBlankLines > 0) block.setContent(lines.subList(0, lines.size() - trailingBlankLines));
            else block.setContent(content);
        } else {
            block.setContent(content);
        }

        if (codeContentBlock) {
            CodeBlock codeBlock = new CodeBlock(block.getChars(), block.getContentLines());
            block.appendChild(codeBlock);
        }
        content = null;
    }

    public static class Factory implements CustomBlockParserFactory {
        @Nullable
        @Override
        public Set<Class<?>> getAfterDependents() {
            return new HashSet<>(Arrays.asList(
                    BlockQuoteParser.Factory.class,
                    HeadingParser.Factory.class,
                    FencedCodeBlockParser.Factory.class,
                    HtmlBlockParser.Factory.class,
                    ThematicBreakParser.Factory.class,
                    ListBlockParser.Factory.class
                    //IndentedCodeBlockParser.Factory.class
            ));
        }

        @Nullable
        @Override
        public Set<Class<?>> getBeforeDependents() {
            return Collections.emptySet();
            //return new HashSet<>(Arrays.asList(
            //        //BlockQuoteParser.Factory.class,
            //        //HeadingParser.Factory.class,
            //        //FencedCodeBlockParser.Factory.class,
            //        //HtmlBlockParser.Factory.class,
            //        //ThematicBreakParser.Factory.class,
            //        //ListBlockParser.Factory.class,
            //        //IndentedCodeBlockParser.Factory.class
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

    public static class BlockFactory extends AbstractBlockParserFactory {
        private BlockFactory(DataHolder options) {
            super(options);
        }

        @Override
        public BlockStart tryStart(ParserState state, MatchedBlockParser matchedBlockParser) {
            // An indented code block cannot interrupt a paragraph.
            if (state.getIndent() >= state.getParsing().CODE_BLOCK_INDENT && !state.isBlank() && !(state.getActiveBlockParser().getBlock() instanceof Paragraph)) {
                return BlockStart.of(new IndentedCodeBlockParser(state.getProperties())).atColumn(state.getColumn() + state.getParsing().CODE_BLOCK_INDENT);
            } else {
                return BlockStart.none();
            }
        }
    }
}

