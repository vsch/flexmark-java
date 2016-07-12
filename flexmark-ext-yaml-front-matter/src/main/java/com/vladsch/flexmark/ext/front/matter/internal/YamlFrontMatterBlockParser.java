package com.vladsch.flexmark.ext.front.matter.internal;

import com.vladsch.flexmark.ext.front.matter.YamlFrontMatterBlock;
import com.vladsch.flexmark.ext.front.matter.YamlFrontMatterNode;
import com.vladsch.flexmark.internal.DocumentBlockParser;
import com.vladsch.flexmark.internal.util.options.DataHolder;
import com.vladsch.flexmark.internal.util.sequence.BasedSequence;
import com.vladsch.flexmark.node.Block;
import com.vladsch.flexmark.parser.InlineParser;
import com.vladsch.flexmark.parser.block.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YamlFrontMatterBlockParser extends AbstractBlockParser {
    private static final Pattern REGEX_METADATA = Pattern.compile("^[ ]{0,3}([A-Za-z0-9_-]+):\\s*(.*)");
    private static final Pattern REGEX_METADATA_LIST = Pattern.compile("^[ ]+-\\s*(.*)");
    private static final Pattern REGEX_METADATA_LITERAL = Pattern.compile("^\\s*(.*)");
    private static final Pattern REGEX_BEGIN = Pattern.compile("^-{3}(\\s.*)?");
    private static final Pattern REGEX_END = Pattern.compile("^(-{3}|\\.{3})(\\s.*)?");

    private boolean inYAMLBlock;
    private boolean inLiteral;
    private String currentKey;
    private List<String> currentValues;
    private YamlFrontMatterBlock block;

    public YamlFrontMatterBlockParser() {
        inYAMLBlock = true;
        inLiteral = false;
        currentKey = null;
        currentValues = new ArrayList<>();
        block = new YamlFrontMatterBlock();
    }

    @Override
    public Block getBlock() {
        return block;
    }

    @Override
    public void addLine(ParserState state, BasedSequence line) {
    }

    @Override
    public void closeBlock(ParserState parserState) {
        block.setCharsFromContent();
    }

    @Override
    public BlockContinue tryContinue(ParserState parserState) {
        final CharSequence line = parserState.getLine();

        if (inYAMLBlock) {
            if (REGEX_END.matcher(line).matches()) {
                if (currentKey != null) {
                    block.appendChild(new YamlFrontMatterNode(currentKey, currentValues));
                }
                return BlockContinue.finished();
            }

            Matcher matcher = REGEX_METADATA.matcher(line);
            if (matcher.matches()) {
                if (currentKey != null) {
                    block.appendChild(new YamlFrontMatterNode(currentKey, currentValues));
                }

                inLiteral = false;
                currentKey = matcher.group(1);
                currentValues = new ArrayList<>();
                if ("|".equals(matcher.group(2))) {
                    inLiteral = true;
                } else if (!"".equals(matcher.group(2))) {
                    currentValues.add(matcher.group(2));
                }

                return BlockContinue.atIndex(parserState.getIndex());
            } else {
                if (inLiteral) {
                    matcher = REGEX_METADATA_LITERAL.matcher(line);
                    if (matcher.matches()) {
                        if (currentValues.size() == 1) {
                            currentValues.set(0, currentValues.get(0) + "\n" + matcher.group(1).trim());
                        } else {
                            currentValues.add(matcher.group(1).trim());
                        }
                    }
                } else {
                    matcher = REGEX_METADATA_LIST.matcher(line);
                    if (matcher.matches()) {
                        currentValues.add(matcher.group(1));
                    }
                }

                return BlockContinue.atIndex(parserState.getIndex());
            }
        } else if (REGEX_BEGIN.matcher(line).matches()) {
            inYAMLBlock = true;
            return BlockContinue.atIndex(parserState.getIndex());
        }

        return BlockContinue.none();
    }

    @Override
    public void parseInlines(InlineParser inlineParser) {
    }

    public static class Factory implements CustomBlockParserFactory {
        @Override
        public Set<Class<? extends CustomBlockParserFactory>> getAfterDependents() {
            return null;
        }

        @Override
        public Set<Class<? extends CustomBlockParserFactory>> getBeforeDependents() {
            return null;
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
        private BlockFactory(DataHolder options) {
            super(options);
        }

        @Override
        public BlockStart tryStart(ParserState state, MatchedBlockParser matchedBlockParser) {
            CharSequence line = state.getLine();
            BlockParser parentParser = matchedBlockParser.getMatchedBlockParser();
            // check whether this line is the first line of whole document or not
            if (parentParser instanceof DocumentBlockParser && parentParser.getBlock().getFirstChild() == null &&
                    REGEX_BEGIN.matcher(line).matches()) {
                return BlockStart.of(new YamlFrontMatterBlockParser()).atIndex(state.getNextNonSpaceIndex());
            }

            return BlockStart.none();
        }
    }
}
