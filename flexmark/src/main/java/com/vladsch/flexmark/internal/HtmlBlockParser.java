package com.vladsch.flexmark.internal;

import com.vladsch.flexmark.internal.util.BasedSequence;
import com.vladsch.flexmark.internal.util.DataHolder;
import com.vladsch.flexmark.internal.util.Parsing;
import com.vladsch.flexmark.node.Block;
import com.vladsch.flexmark.node.HtmlBlock;
import com.vladsch.flexmark.node.HtmlCommentBlock;
import com.vladsch.flexmark.node.Paragraph;
import com.vladsch.flexmark.parser.block.*;

import java.util.regex.Pattern;

public class HtmlBlockParser extends AbstractBlockParser {

    private static final int COMMENT_PATTERN_INDEX = 2;
    private static final Pattern[][] BLOCK_PATTERNS = new Pattern[][] {
            { null, null }, // not used (no type 0)
            {
                    Pattern.compile("^<(?:script|pre|style)(?:\\s|>|$)", Pattern.CASE_INSENSITIVE),
                    Pattern.compile("</(?:script|pre|style)>", Pattern.CASE_INSENSITIVE)
            },
            {
                    Pattern.compile("^<!--"),
                    Pattern.compile("-->")
            },
            {
                    Pattern.compile("^<[?]"),
                    Pattern.compile("\\?>")
            },
            {
                    Pattern.compile("^<![A-Z]"),
                    Pattern.compile(">")
            },
            {
                    Pattern.compile("^<!\\[CDATA\\["),
                    Pattern.compile("\\]\\]>")
            },
            {
                    Pattern.compile("^</?(?:" +
                            "address|article|aside|" +
                            "base|basefont|blockquote|body|" +
                            "caption|center|col|colgroup|" +
                            "dd|details|dialog|dir|div|dl|dt|" +
                            "fieldset|figcaption|figure|footer|form|frame|frameset|" +
                            "h1|head|header|hr|html|" +
                            "iframe|" +
                            "legend|li|link|" +
                            "main|menu|menuitem|meta|" +
                            "nav|noframes|" +
                            "ol|optgroup|option|" +
                            "p|param|" +
                            "section|source|summary|" +
                            "table|tbody|td|tfoot|th|thead|title|tr|track|" +
                            "ul" +
                            ")(?:\\s|[/]?[>]|$)", Pattern.CASE_INSENSITIVE),
                    null // terminated by blank line
            },
            {
                    Pattern.compile("^(?:" + Parsing.OPENTAG + '|' + Parsing.CLOSETAG + ")\\s*$", Pattern.CASE_INSENSITIVE),
                    null // terminated by blank line
            }
    };

    private final HtmlBlock block;
    private final Pattern closingPattern;

    private boolean finished = false;
    private BlockContent content = new BlockContent();

    private HtmlBlockParser(Pattern closingPattern, boolean isComment) {
        this.closingPattern = closingPattern;
        this.block = isComment ?  new HtmlCommentBlock() : new HtmlBlock();
    }

    @Override
    public Block getBlock() {
        return block;
    }

    @Override
    public BlockContinue tryContinue(ParserState state) {
        if (finished) {
            return BlockContinue.none();
        }

        // Blank line ends type 6 and type 7 blocks
        if (state.isBlank() && closingPattern == null) {
            return BlockContinue.none();
        } else {
            return BlockContinue.atIndex(state.getIndex());
        }
    }

    @Override
    public void addLine(ParserState state, BasedSequence line) {
        content.add(line, state.getIndent());

        if (closingPattern != null && closingPattern.matcher(line).find()) {
            finished = true;
        }
    }

    @Override
    public void closeBlock(ParserState parserState) {
        block.setContent(content);
        content = null;
    }

    public static class Factory implements CustomBlockParserFactory {
        @Override
        public BlockParserFactory create(DataHolder options) {
            return new BlockFactory(options);
        }
    }

    private static class BlockFactory extends AbstractBlockParserFactory {
        private BlockFactory(DataHolder options) {
            super(options);
        }

        @Override
        public BlockStart tryStart(ParserState state, MatchedBlockParser matchedBlockParser) {
            int nextNonSpace = state.getNextNonSpaceIndex();
            BasedSequence line = state.getLine();

            if (state.getIndent() < 4 && line.charAt(nextNonSpace) == '<') {
                for (int blockType = 1; blockType <= 7; blockType++) {
                    // Type 7 can not interrupt a paragraph
                    if (blockType == 7 && matchedBlockParser.getMatchedBlockParser().getBlock() instanceof Paragraph) {
                        continue;
                    }
                    Pattern opener = BLOCK_PATTERNS[blockType][0];
                    Pattern closer = BLOCK_PATTERNS[blockType][1];
                    boolean matches = opener.matcher(line.subSequence(nextNonSpace, line.length())).find();
                    if (matches) {
                        return BlockStart.of(new HtmlBlockParser(closer, blockType == COMMENT_PATTERN_INDEX)).atIndex(state.getIndex());
                    }
                }
            }
            return BlockStart.none();
        }
    }
}