package com.vladsch.flexmark.ext.yaml.front.matter.internal;

import com.vladsch.flexmark.ext.yaml.front.matter.YamlFrontMatterBlock;
import com.vladsch.flexmark.ext.yaml.front.matter.YamlFrontMatterNode;
import com.vladsch.flexmark.parser.InlineParser;
import com.vladsch.flexmark.parser.block.*;
import com.vladsch.flexmark.parser.core.DocumentBlockParser;
import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.ast.BlockContent;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.PrefixedSubSequence;
import com.vladsch.flexmark.util.sequence.SegmentedSequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YamlFrontMatterBlockParser extends AbstractBlockParser {
    final private static Pattern REGEX_METADATA = Pattern.compile("^[ ]{0,3}([A-Za-z0-9_\\-.]+):\\s*(.*)");
    final private static Pattern REGEX_METADATA_LIST = Pattern.compile("^[ ]+-\\s*(.*)");
    final private static Pattern REGEX_METADATA_LITERAL = Pattern.compile("^\\s*(.*)");
    final private static Pattern REGEX_BEGIN = Pattern.compile("^-{3}(\\s.*)?");
    final private static Pattern REGEX_END = Pattern.compile("^(-{3}|\\.{3})(\\s.*)?");

    private boolean inYAMLBlock;
    private boolean inLiteral;
    private BasedSequence currentKey;
    private List<BasedSequence> currentValues;
    private YamlFrontMatterBlock block;
    private BlockContent content;

    public YamlFrontMatterBlockParser() {
        inYAMLBlock = true;
        inLiteral = false;
        currentKey = null;
        currentValues = new ArrayList<>();
        block = new YamlFrontMatterBlock();
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

        if (inYAMLBlock) {
            if (REGEX_END.matcher(line).matches()) {
                if (currentKey != null) {
                    YamlFrontMatterNode child = new YamlFrontMatterNode(currentKey, currentValues);
                    child.setCharsFromContent();
                    block.appendChild(child);
                }
                // add the last line
                addLine(state, line);
                return BlockContinue.finished();
            }

            Matcher matcher = REGEX_METADATA.matcher(line);
            if (matcher.matches()) {
                if (currentKey != null) {
                    YamlFrontMatterNode child = new YamlFrontMatterNode(currentKey, currentValues);
                    child.setCharsFromContent();
                    block.appendChild(child);
                }

                inLiteral = false;
                currentKey = line.subSequence(matcher.start(1), matcher.end(1));
                currentValues = new ArrayList<>();
                if ("|".equals(matcher.group(2))) {
                    inLiteral = true;
                } else if (!"".equals(matcher.group(2))) {
                    currentValues.add(line.subSequence(matcher.start(2), matcher.end(2)));
                }

                return BlockContinue.atIndex(state.getIndex());
            } else {
                if (inLiteral) {
                    matcher = REGEX_METADATA_LITERAL.matcher(line);
                    if (matcher.matches()) {
                        if (currentValues.size() == 1) {
                            BasedSequence combined = SegmentedSequence.create(currentValues.get(0), PrefixedSubSequence.prefixOf("\n", line.subSequence(matcher.start(1), matcher.end(1)).trim()));
                            currentValues.set(0, combined);
                        } else {
                            currentValues.add(line.subSequence(matcher.start(1), matcher.end(1)).trim());
                        }
                    }
                } else {
                    matcher = REGEX_METADATA_LIST.matcher(line);
                    if (matcher.matches()) {
                        currentValues.add(line.subSequence(matcher.start(1), matcher.end(1)));
                    }
                }

                return BlockContinue.atIndex(state.getIndex());
            }
        } else if (REGEX_BEGIN.matcher(line).matches()) {
            inYAMLBlock = true;
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
                return BlockStart.of(new YamlFrontMatterBlockParser()).atIndex(state.getNextNonSpaceIndex());
            }

            return BlockStart.none();
        }
    }
}
