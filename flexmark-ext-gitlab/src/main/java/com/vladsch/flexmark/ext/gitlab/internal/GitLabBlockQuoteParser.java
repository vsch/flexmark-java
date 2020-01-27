package com.vladsch.flexmark.ext.gitlab.internal;

import com.vladsch.flexmark.ext.gitlab.GitLabBlockQuote;
import com.vladsch.flexmark.parser.InlineParser;
import com.vladsch.flexmark.parser.block.*;
import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.ast.BlockContent;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GitLabBlockQuoteParser extends AbstractBlockParser {
    static Pattern GIT_LAB_BLOCK_START = Pattern.compile(">>>(\\s*$)");
    static Pattern GIT_LAB_BLOCK_END = Pattern.compile(">>>(\\s*$)");

    final private GitLabBlockQuote block = new GitLabBlockQuote();
    private BlockContent content = new BlockContent();
    private boolean hadClose = false;

    GitLabBlockQuoteParser(DataHolder options, BasedSequence openMarker, BasedSequence openTrailing) {
        this.block.setOpeningMarker(openMarker);
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

        int index = state.getIndex();

        BasedSequence line = state.getLineWithEOL();
        Matcher matcher = GIT_LAB_BLOCK_END.matcher(line.subSequence(index));
        if (!matcher.matches()) {
            return BlockContinue.atIndex(index);
        } else {
            // if have open gitlab block quote last child then let them handle it
            Node lastChild = block.getLastChild();
            if (lastChild instanceof GitLabBlockQuote) {
                BlockParser parser = state.getActiveBlockParser((Block) lastChild);
                if (parser instanceof GitLabBlockQuoteParser && !((GitLabBlockQuoteParser) parser).hadClose) {
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
    }

    @Override
    public boolean isContainer() {
        return true;
    }

    @Override
    public boolean canContain(ParserState state, BlockParser blockParser, Block block) {
        return true; //options.nestedBlockQuotes || !(blockParser instanceof GitLabBlockQuoteParser);
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
        final private GitLabOptions options;

        BlockFactory(DataHolder options) {
            super(options);
            this.options = new GitLabOptions(options);
        }

        boolean haveBlockQuoteParser(ParserState state) {
            List<BlockParser> parsers = state.getActiveBlockParsers();
            int i = parsers.size();
            while (i-- > 0) {
                if (parsers.get(i) instanceof GitLabBlockQuoteParser) return true;
            }
            return false;
        }

        @Override
        public BlockStart tryStart(ParserState state, MatchedBlockParser matchedBlockParser) {
            if (options.nestedBlockQuotes || !haveBlockQuoteParser(state)) {
                BasedSequence line = state.getLineWithEOL();
                Matcher matcher = GIT_LAB_BLOCK_START.matcher(line);
                if (matcher.matches()) {
                    return BlockStart.of(new GitLabBlockQuoteParser(state.getProperties(), line.subSequence(0, 3), line.subSequence(matcher.start(1), matcher.end(1))))
                            .atIndex(state.getLineEndIndex())
                            //.replaceActiveBlockParser()
                            ;
                }
            }
            return BlockStart.none();
        }
    }
}
