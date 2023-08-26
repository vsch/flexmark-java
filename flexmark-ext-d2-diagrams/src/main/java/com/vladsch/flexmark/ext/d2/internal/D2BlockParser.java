package com.vladsch.flexmark.ext.d2.internal;

import com.vladsch.flexmark.ext.d2.D2Block;
import com.vladsch.flexmark.ext.d2.D2Node;
import com.vladsch.flexmark.parser.InlineParser;
import com.vladsch.flexmark.parser.block.*;
import com.vladsch.flexmark.parser.core.DocumentBlockParser;
import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.ast.BlockContent;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public class D2BlockParser extends AbstractBlockParser {
    final private static Pattern REGEX_BEGIN = Pattern.compile("^`{3}d2(\\s.*)?");
    final private static Pattern REGEX_END = Pattern.compile("^`{3}(\\s.*)?");

    private boolean inD2Block;
    private BasedSequence currentKey;
    private List<BasedSequence> currentValues;
    private D2Block block;
    private BlockContent content;

    public D2BlockParser() {
        inD2Block = true;
        currentKey = null;
        currentValues = new ArrayList<>();
        block = new D2Block();
        content = new BlockContent();
    }

    @Override
    public Block getBlock() {
        return block;
    }

    @Override
    public boolean isContainer() {
        return false;
    }

    @Override
    public void addLine(ParserState state, BasedSequence line) {
        content.add(line, state.getIndent());
    }

    @Override
    public void closeBlock(ParserState state) {
        block.setContent(content.getLines().subList(0, content.getLineCount()));
        block.setCharsFromContent();
        content = null;
    }

    @Override
    public BlockContinue tryContinue(ParserState state) {
        final BasedSequence line = state.getLine();

        if (inD2Block) {
            if (REGEX_END.matcher(line).matches()) {
                System.out.println("test");
                D2Node child = new D2Node(line, currentValues);
                child.setCharsFromContent();
                block.appendChild(child);
                return BlockContinue.finished();
            } else {
                return BlockContinue.atIndex(state.getIndex());
            }
        } else if (REGEX_BEGIN.matcher(line).matches()) {
            inD2Block = true;
            return BlockContinue.atIndex(state.getIndex());
        }
        return BlockContinue.none();
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
        private BlockFactory(DataHolder options) {
            super(options);
        }

        @Override
        public BlockStart tryStart(ParserState state, MatchedBlockParser matchedBlockParser) {
            CharSequence line = state.getLine();
            BlockParser parentParser = matchedBlockParser.getBlockParser();
            // check whether this line is the first line of whole document or not
            if (parentParser instanceof DocumentBlockParser && parentParser.getBlock().getFirstChild() == null &&
                    REGEX_BEGIN.matcher(line).matches()) {
                return BlockStart.of(new D2BlockParser()).atIndex(state.getNextNonSpaceIndex());
            }

            return BlockStart.none();
        }
    }
}
