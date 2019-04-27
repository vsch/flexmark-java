package com.vladsch.flexmark.ext.macros.internal;

import com.vladsch.flexmark.ext.gitlab.GitLabBlockQuote;
import com.vladsch.flexmark.ext.gitlab.internal.GitLabBlockQuoteParser;
import com.vladsch.flexmark.ext.macros.MacroDefinitionBlock;
import com.vladsch.flexmark.ext.macros.MacrosExtension;
import com.vladsch.flexmark.parser.InlineParser;
import com.vladsch.flexmark.parser.block.*;
import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.ast.BlockContent;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MacroDefinitionBlockParser extends AbstractBlockParser {
    static Pattern MACRO_BLOCK_START = Pattern.compile(">>>([\\w_-]+)(\\s*$)");
    static Pattern MACRO_BLOCK_START_INTELLIJ = Pattern.compile(">>>([\u001f\\w_-]+)(\\s*$)");
    static Pattern MACRO_BLOCK_END = Pattern.compile("<<<(\\s*$)");

    private final MacroDefinitionBlock block = new MacroDefinitionBlock();
    private BlockContent content = new BlockContent();
    private final MacrosOptions options;
    private boolean hadClose = false;

    MacroDefinitionBlockParser(DataHolder options, BasedSequence openMarker, BasedSequence name, BasedSequence openTrailing) {
        this.options = new MacrosOptions(options);
        this.block.setOpeningMarker(openMarker);
        this.block.setName(name);
        this.block.setOpeningTrailing(openTrailing);
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

        final int index = state.getIndex();

        BasedSequence line = state.getLineWithEOL();
        final Matcher matcher = MACRO_BLOCK_END.matcher(line);
        if (!matcher.matches()) {
            return BlockContinue.atIndex(index);
        } else {
            // if have open gitlab block quote last child then let them handle it
            Node lastChild = block.getLastChild();
            if (lastChild instanceof GitLabBlockQuote) {
                //final BlockParser parser = state.getActiveBlockParser((Block) lastChild);
                if (((GitLabBlockQuote) lastChild).getClosingMarker().isEmpty()) {
                    // let the child handle it
                    return BlockContinue.atIndex(index);
                }
            }
            hadClose = true;
            block.setClosingMarker(state.getLine().subSequence(index, index + 3));
            block.setClosingTrailing(state.getLineWithEOL().subSequence(matcher.start(1), matcher.end(1)));
            return BlockContinue.atIndex(state.getLineEndIndex());
        }
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

        // set the footnote from closingMarker to end
        block.setCharsFromContent();

        // add it to the map
        MacroDefinitionRepository macrosRepository = state.getProperties().get(MacrosExtension.MACRO_DEFINITIONS);
        macrosRepository.put(macrosRepository.normalizeKey(block.getName()), block);
    }

    @Override
    public boolean isContainer() {
        return true;
    }

    @Override
    public boolean canContain(final ParserState state, final BlockParser blockParser, final Block block) {
        return true; //options.nestedBlockQuotes || !(blockParser instanceof MacrosBlockQuoteParser);
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
            return new HashSet<Class<? extends CustomBlockParserFactory>>(Arrays.asList(
                    GitLabBlockQuoteParser.Factory.class
            ));
        }

        @Override
        public boolean affectsGlobalScope() {
            return false;
        }

        @Override
        public BlockParserFactory apply(DataHolder options) {
            return new BlockFactory(options);
        }
    }

    private static class BlockFactory extends AbstractBlockParserFactory {
        private final MacrosOptions options;

        BlockFactory(DataHolder options) {
            super(options);
            this.options = new MacrosOptions(options);
        }

        boolean haveBlockQuoteParser(ParserState state) {
            final List<BlockParser> parsers = state.getActiveBlockParsers();
            int i = parsers.size();
            while (i-- > 0) {
                if (parsers.get(i) instanceof MacroDefinitionBlockParser) return true;
            }
            return false;
        }

        @Override
        public BlockStart tryStart(ParserState state, MatchedBlockParser matchedBlockParser) {
            if (state.getIndex() == 0 && !haveBlockQuoteParser(state)) {
                BasedSequence line = state.getLineWithEOL();
                final Matcher matcher = (state.getParsing().intellijDummyIdentifier ? MACRO_BLOCK_START_INTELLIJ : MACRO_BLOCK_START).matcher(line);
                if (matcher.matches()) {
                    return BlockStart.of(new MacroDefinitionBlockParser(state.getProperties(), line.subSequence(0, 3), line.subSequence(matcher.start(1), matcher.end(1)), line.subSequence(matcher.start(2), matcher.end(1))))
                            .atIndex(state.getLineEndIndex())
                            //.replaceActiveBlockParser()
                            ;
                }
            }
            return BlockStart.none();
        }
    }
}
