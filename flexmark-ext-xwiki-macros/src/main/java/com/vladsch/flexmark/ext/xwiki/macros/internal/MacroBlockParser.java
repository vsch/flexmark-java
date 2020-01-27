package com.vladsch.flexmark.ext.xwiki.macros.internal;

import com.vladsch.flexmark.ast.Paragraph;
import com.vladsch.flexmark.ast.util.Parsing;
import com.vladsch.flexmark.ext.xwiki.macros.Macro;
import com.vladsch.flexmark.ext.xwiki.macros.MacroAttribute;
import com.vladsch.flexmark.ext.xwiki.macros.MacroBlock;
import com.vladsch.flexmark.ext.xwiki.macros.MacroClose;
import com.vladsch.flexmark.parser.InlineParser;
import com.vladsch.flexmark.parser.block.*;
import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.ast.BlockContent;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;

public class MacroBlockParser extends AbstractBlockParser {
    final private MacroBlock block = new MacroBlock();
    private BlockContent content = new BlockContent();
    final private MacroParsing parsing;
    final private BasedSequence macroName;
    final private boolean oneLine;
    private boolean hadClose;

    MacroBlockParser(DataHolder options, MacroParsing parsing, BasedSequence macroName, boolean oneLine) {
        this.parsing = parsing;
        this.macroName = macroName;
        this.oneLine = oneLine;
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

        final BasedSequence line = state.getLine();
        final Matcher closeMatcher = parsing.MACRO_CLOSE.matcher(line);
        if (closeMatcher.find()) {
            // check if this close belongs to a nested child block macro
            if (macroName.equals(closeMatcher.group(1))) {
                final List<BlockParser> parsers = state.getActiveBlockParsers();
                boolean isChildClose = false;
                for (int i = parsers.size(); i-- > 0; ) {
                    final BlockParser parser = parsers.get(i);
                    if (parser == this) break;

                    if (parser instanceof MacroBlockParser) {
                        if (!((MacroBlockParser) parser).hadClose && ((MacroBlockParser) parser).macroName.equals(macroName)) {
                            isChildClose = true;
                        }
                    }
                }

                if (!isChildClose) {
                    hadClose = true;
                    MacroClose macroClose = new MacroClose(line.subSequence(closeMatcher.start(), closeMatcher.start() + 3),
                            line.subSequence(closeMatcher.start(1), closeMatcher.end(1)),
                            line.subSequence(closeMatcher.end() - 2, closeMatcher.end()));

                    macroClose.setCharsFromContent();

                    block.appendChild(macroClose);
                    return BlockContinue.atIndex(state.getLineEndIndex());
                }
            }
        }
        return BlockContinue.atIndex(state.getIndex());
    }

    @Override
    public void addLine(ParserState state, BasedSequence line) {
        content.add(line, state.getIndent());
    }

    @Override
    public boolean isContainer() {
        return true;
    }

    @Override
    public boolean canContain(ParserState state, BlockParser blockParser, final Block block) {
        return true;
    }

    @Override
    public void closeBlock(ParserState state) {
        // first line is macro open and possibly close
        if (oneLine) {
            List<BasedSequence> lines = new ArrayList<>();
            Macro macro = (Macro) block.getFirstChild();
            Node node = block.getLastChild();
            BasedSequence contentLine;
            if (node instanceof MacroClose) {
                contentLine = macro.baseSubSequence(macro.getEndOffset(), node.getStartOffset());
            } else {
                contentLine = macro.baseSubSequence(macro.getEndOffset(), macro.getEndOffset());
            }

            lines.add(contentLine);
            block.setContent(lines);
        } else {
            // last line is close, first line is open
            if (hadClose) {
                final List<BasedSequence> lines = content.getLines();
                block.setContent(lines);
            } else {
                final List<BasedSequence> lines = content.getLines();
                block.setContent(lines.subList(0, lines.size()));
            }
        }

        block.setCharsFromContent();
        content = null;
    }

    @Override
    public void parseInlines(InlineParser inlineParser) {
        Node node = block.getLastChild();
        if (node instanceof MacroClose) {
            node.unlink();
        }

        inlineParser.parse(block.getContentChars(), block);

        if (node instanceof MacroClose) {
            block.appendChild(node);
        }
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
        final private MacroParsing parsing;

        BlockFactory(DataHolder options) {
            super(options);
            this.parsing = new MacroParsing(new Parsing(options));
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
                    BasedSequence macroName = line.subSequence(matcher.start(1), matcher.end(1));
                    BasedSequence macroOpen = tryLine.subSequence(0, matcher.end());
                    final BasedSequence afterOpen = tryLine.subSequence(matcher.end());
                    boolean oneLine = false;
                    boolean isClosedTag = false;
                    MacroClose macroClose = null;

                    if (macroOpen.endsWith("/}}")) {
                        // closed tag, if the end is blank then it is a block
                        if (afterOpen.isBlank()) {
                            // this is an open/close tag
                            oneLine = true;
                            isClosedTag = true;
                        } else {
                            return BlockStart.none();
                        }
                    } else {
                        // see if close or blank
                        if (!afterOpen.isBlank()) {
                            Matcher closeMatcher = parsing.MACRO_CLOSE_END.matcher(afterOpen);
                            if (closeMatcher.find()) {
                                if (macroName.equals(closeMatcher.group(1)) && (closeMatcher.groupCount() < 2 || closeMatcher.start(2) == -1 || (closeMatcher.group(2).length() & 1) == 1)) {
                                    // same name and not escaped
                                    oneLine = true;
                                    macroClose = new MacroClose(afterOpen.subSequence(closeMatcher.start(), closeMatcher.start() + 3),
                                            afterOpen.subSequence(closeMatcher.start(1), closeMatcher.end(1)),
                                            afterOpen.subSequence(closeMatcher.end() - 2, closeMatcher.end()));
                                    macroClose.setCharsFromContent();
                                }
                            }

                            if (!oneLine) {
                                return BlockStart.none();
                            }
                        }
                    }

                    Macro macro = new Macro(macroOpen.subSequence(0, 2), macroName, macroOpen.endSequence(isClosedTag ? 3 : 2));
                    macro.setCharsFromContent();

                    BasedSequence attributeText = macroOpen.baseSubSequence(macroName.getEndOffset(), macro.getClosingMarker().getStartOffset()).trim();
                    if (!attributeText.isEmpty()) {
                        // have some attribute text
                        macro.setAttributeText(attributeText);

                        // parse attributes
                        Matcher attributeMatcher = parsing.MACRO_ATTRIBUTE.matcher(attributeText);
                        while (attributeMatcher.find()) {
                            BasedSequence attributeName = attributeText.subSequence(attributeMatcher.start(1), attributeMatcher.end(1));
                            BasedSequence attributeSeparator = attributeMatcher.groupCount() == 1 || attributeMatcher.start(2) == -1 ? BasedSequence.NULL : attributeText.subSequence(attributeMatcher.end(1), attributeMatcher.start(2)).trim();
                            BasedSequence attributeValue = attributeMatcher.groupCount() == 1 || attributeMatcher.start(2) == -1 ? BasedSequence.NULL : attributeText.subSequence(attributeMatcher.start(2), attributeMatcher.end(2));
                            boolean isQuoted = attributeValue.length() >= 2 && (attributeValue.charAt(0) == '"' && attributeValue.endCharAt(1) == '"' || attributeValue.charAt(0) == '\'' && attributeValue.endCharAt(1) == '\'');
                            BasedSequence attributeOpen = !isQuoted ? BasedSequence.NULL : attributeValue.subSequence(0, 1);
                            BasedSequence attributeClose = !isQuoted ? BasedSequence.NULL : attributeValue.endSequence(1, 0);

                            if (isQuoted) {
                                attributeValue = attributeValue.midSequence(1, -1);
                            }

                            MacroAttribute attribute = new MacroAttribute(attributeName, attributeSeparator, attributeOpen, attributeValue, attributeClose);
                            macro.appendChild(attribute);
                        }
                    }

                    final MacroBlockParser parser = new MacroBlockParser(state.getProperties(), parsing, macroName, oneLine);
                    if (oneLine) {
                        parser.hadClose = true;
                    }

                    parser.block.appendChild(macro);
                    if (macroClose != null) parser.block.appendChild(macroClose);

                    return BlockStart.of(parser)
                            .atIndex(state.getLineEndIndex())
                            ;
                }
            }
            return BlockStart.none();
        }
    }
}
