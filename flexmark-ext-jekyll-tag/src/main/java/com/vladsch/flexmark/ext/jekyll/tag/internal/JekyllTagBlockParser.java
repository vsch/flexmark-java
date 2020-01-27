package com.vladsch.flexmark.ext.jekyll.tag.internal;

import com.vladsch.flexmark.ast.Paragraph;
import com.vladsch.flexmark.ast.util.Parsing;
import com.vladsch.flexmark.ext.jekyll.tag.JekyllTag;
import com.vladsch.flexmark.ext.jekyll.tag.JekyllTagBlock;
import com.vladsch.flexmark.ext.jekyll.tag.JekyllTagExtension;
import com.vladsch.flexmark.parser.InlineParser;
import com.vladsch.flexmark.parser.block.*;
import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.ast.BlockContent;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;

public class JekyllTagBlockParser extends AbstractBlockParser {
    final public static String INCLUDE_TAG = "include";
    final JekyllTagBlock block = new JekyllTagBlock();
    private BlockContent content = new BlockContent();

    JekyllTagBlockParser(DataHolder options) {
    }

    @Override
    public Block getBlock() {
        return block;
    }

    @Override
    public BlockContinue tryContinue(ParserState state) {
        return BlockContinue.none();
    }

    @Override
    public void addLine(ParserState state, BasedSequence line) {
        content.add(line, state.getIndent());
    }

    @Override
    public void closeBlock(ParserState state) {
        block.setContent(content);
        //block.setCharsFromContent();
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
        final private JekyllTagParsing parsing;
        final private boolean listIncludesOnly;

        BlockFactory(DataHolder options) {
            super(options);
            this.parsing = new JekyllTagParsing(new Parsing(options));
            listIncludesOnly = JekyllTagExtension.LIST_INCLUDES_ONLY.get(options);
        }

        @Override
        public BlockStart tryStart(ParserState state, MatchedBlockParser matchedBlockParser) {
            BasedSequence line = state.getLine();
            int currentIndent = state.getIndent();
            if (currentIndent == 0 && !(matchedBlockParser.getBlockParser().getBlock() instanceof Paragraph)) {
                final BasedSequence tryLine = line.subSequence(state.getIndex());
                Matcher matcher = parsing.MACRO_OPEN.matcher(tryLine);

                if (matcher.find()) {
                    // see if it closes on the same line, then we create a block and close it
                    BasedSequence tag = tryLine.subSequence(0, matcher.end());
                    BasedSequence tagName = line.subSequence(matcher.start(1), matcher.end(1));
                    BasedSequence parameters = tryLine.subSequence(matcher.end(1), matcher.end() - 2).trim();

                    JekyllTag tagNode = new JekyllTag(tag.subSequence(0, 2), tagName, parameters, tag.endSequence(2));
                    tagNode.setCharsFromContent();

                    final JekyllTagBlockParser parser = new JekyllTagBlockParser(state.getProperties());
                    parser.block.appendChild(tagNode);

                    //noinspection EqualsBetweenInconvertibleTypes
                    if (!listIncludesOnly || tagName.equals(INCLUDE_TAG)) {
                        List<JekyllTag> tagList = JekyllTagExtension.TAG_LIST.get(state.getProperties());
                        tagList.add(tagNode);
                    }

                    return BlockStart.of(parser)
                            .atIndex(state.getLineEndIndex())
                            ;
                }
            }
            return BlockStart.none();
        }
    }
}
