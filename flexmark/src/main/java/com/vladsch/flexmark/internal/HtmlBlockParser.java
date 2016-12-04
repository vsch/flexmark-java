package com.vladsch.flexmark.internal;

import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.ast.util.Parsing;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.block.*;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlBlockParser extends AbstractBlockParser {

    private static class Patterns {
        final public int COMMENT_PATTERN_INDEX;
        final public Pattern[][] BLOCK_PATTERNS;

        public Patterns(Parsing parsing) {
            this.COMMENT_PATTERN_INDEX = 2;
            this.BLOCK_PATTERNS = new Pattern[][] {
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
                            Pattern.compile("^(?:" + parsing.OPENTAG + '|' + parsing.CLOSETAG + ")\\s*$", Pattern.CASE_INSENSITIVE),
                            null // terminated by blank line
                    }
            };
        }
    }

    private final HtmlBlockBase block;
    private final Pattern closingPattern;

    private boolean finished = false;
    private BlockContent content = new BlockContent();
    final private boolean parseInnerHtmlComments;

    private HtmlBlockParser(DataHolder options, Pattern closingPattern, boolean isComment) {
        this.closingPattern = closingPattern;
        this.block = isComment ? new HtmlCommentBlock() : new HtmlBlock();
        this.parseInnerHtmlComments = options.get(Parser.PARSE_INNER_HTML_COMMENTS);
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
    public void closeBlock(ParserState state) {
        block.setContent(content);
        content = null;

        // split out inner comments
        if (!(block instanceof HtmlCommentBlock) && parseInnerHtmlComments) {
            // need to break it up into non-comments and comments
            int lastIndex = 0;
            BasedSequence chars = block.getContentChars();
            if (chars.eolLength() > 0) chars = chars.midSequence(0, -1);
            Matcher matcher = state.getParsing().HTML_COMMENT.matcher(chars);
            while (matcher.find()) {
                int index = matcher.start();
                if (lastIndex < index) {
                    HtmlInnerBlock html = new HtmlInnerBlock(chars.subSequence(lastIndex, index));
                    block.appendChild(html);
                }

                lastIndex = matcher.end();
                HtmlInnerBlockComment htmlComment = new HtmlInnerBlockComment(chars.subSequence(index, lastIndex));
                block.appendChild(htmlComment);
            }

            if (lastIndex > 0) {
                if (lastIndex < chars.length()) {
                    HtmlInnerBlock html = new HtmlInnerBlock(chars.subSequence(lastIndex, chars.length()));
                    block.appendChild(html);
                }
            }
        }
    }

    public static class Factory implements CustomBlockParserFactory {
        @Override
        public Set<Class<? extends CustomBlockParserFactory>> getAfterDependents() {
            return new HashSet<>(Arrays.asList(
                    BlockQuoteParser.Factory.class,
                    HeadingParser.Factory.class,
                    FencedCodeBlockParser.Factory.class
                    //HtmlBlockParser.Factory.class,
                    //ThematicBreakParser.Factory.class,
                    //ListBlockParser.Factory.class,
                    //IndentedCodeBlockParser.Factory.class
            ));
        }

        @Override
        public Set<Class<? extends CustomBlockParserFactory>> getBeforeDependents() {
            return new HashSet<>(Arrays.asList(
                    //BlockQuoteParser.Factory.class,
                    //HeadingParser.Factory.class,
                    //FencedCodeBlockParser.Factory.class,
                    //HtmlBlockParser.Factory.class,
                    ThematicBreakParser.Factory.class,
                    ListBlockParser.Factory.class,
                    IndentedCodeBlockParser.Factory.class
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
        private Patterns myPatterns = null;
        private final boolean myHtmlCommentBlocksInterruptParagraph;
        private BlockFactory(DataHolder options) {
            super(options);
            myHtmlCommentBlocksInterruptParagraph = Parser.HTML_COMMENT_BLOCKS_INTERRUPT_PARAGRAPH.getFrom(options);
        }

        @Override
        public BlockStart tryStart(ParserState state, MatchedBlockParser matchedBlockParser) {
            int nextNonSpace = state.getNextNonSpaceIndex();
            BasedSequence line = state.getLine();

            if (state.getIndent() < 4 && line.charAt(nextNonSpace) == '<') {
                for (int blockType = 1; blockType <= 7; blockType++) {
                    // Type 7 can not interrupt a paragraph
                    if (blockType == 7 && matchedBlockParser.getBlockParser().getBlock() instanceof Paragraph) {
                        continue;
                    }

                    if (myPatterns == null) {
                        myPatterns = new Patterns(state.getParsing());
                    }

                    Pattern opener = myPatterns.BLOCK_PATTERNS[blockType][0];
                    Pattern closer = myPatterns.BLOCK_PATTERNS[blockType][1];
                    boolean matches = opener.matcher(line.subSequence(nextNonSpace, line.length())).find();

                    // TEST: non-interrupting of paragraphs by HTML comments
                    if (matches && (myHtmlCommentBlocksInterruptParagraph || blockType != myPatterns.COMMENT_PATTERN_INDEX || !(matchedBlockParser.getBlockParser() instanceof ParagraphParser))) {
                        return BlockStart.of(new HtmlBlockParser(state.getProperties(), closer, blockType == myPatterns.COMMENT_PATTERN_INDEX)).atIndex(state.getIndex());
                    }
                }
            }
            return BlockStart.none();
        }
    }
}
