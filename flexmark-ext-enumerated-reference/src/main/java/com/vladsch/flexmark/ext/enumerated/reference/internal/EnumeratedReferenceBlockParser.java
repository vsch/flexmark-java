package com.vladsch.flexmark.ext.enumerated.reference.internal;

import com.vladsch.flexmark.ast.Paragraph;
import com.vladsch.flexmark.ext.enumerated.reference.EnumeratedReferenceBlock;
import com.vladsch.flexmark.ext.enumerated.reference.EnumeratedReferenceExtension;
import com.vladsch.flexmark.ext.enumerated.reference.EnumeratedReferenceRepository;
import com.vladsch.flexmark.parser.InlineParser;
import com.vladsch.flexmark.parser.block.*;
import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.ast.BlockContent;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EnumeratedReferenceBlockParser extends AbstractBlockParser {
    static String ENUM_REF_ID = "(?:[^0-9].*)?";
    static Pattern ENUM_REF_ID_PATTERN = Pattern.compile("\\[[\\@|#]\\s*(" + ENUM_REF_ID + ")\\s*\\]");
    static Pattern ENUM_REF_DEF_PATTERN = Pattern.compile("^(\\[[\\@]\\s*(" + ENUM_REF_ID + ")\\s*\\]:)\\s+");

    final EnumeratedReferenceBlock block = new EnumeratedReferenceBlock();
    private BlockContent content = new BlockContent();

    public EnumeratedReferenceBlockParser(EnumeratedReferenceOptions options, int contentOffset) {

    }

    public BlockContent getBlockContent() {
        return content;
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
        throw new IllegalStateException("Abbreviation Blocks hold a single line");
    }

    @Override
    public void closeBlock(ParserState state) {
        // set the enumeratedReference from closingMarker to end
        block.setCharsFromContent();
        block.setEnumeratedReference(block.getChars().subSequence(block.getClosingMarker().getEndOffset() - block.getStartOffset()).trimStart());
        content = null;

        // add block to reference repository
        EnumeratedReferenceRepository enumeratedReferences = EnumeratedReferenceExtension.ENUMERATED_REFERENCES.get(state.getProperties());
        enumeratedReferences.put(block.getText().toString(), block);
    }

    @Override
    public void parseInlines(InlineParser inlineParser) {
        Node paragraph = block.getFirstChild();
        if (paragraph != null) {
            inlineParser.parse(paragraph.getChars(), paragraph);
        }
    }

    @Override
    public boolean isContainer() {
        return true;
    }

    @Override
    public boolean canContain(ParserState state, BlockParser blockParser, Block block) {
        return blockParser.isParagraphParser();
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
        final private EnumeratedReferenceOptions options;

        BlockFactory(DataHolder options) {
            super(options);
            this.options = new EnumeratedReferenceOptions(options);
        }

        @Override
        public BlockStart tryStart(ParserState state, MatchedBlockParser matchedBlockParser) {
            if (state.getIndent() >= 4) {
                return BlockStart.none();
            }

            BasedSequence line = state.getLineWithEOL();
            int nextNonSpace = state.getNextNonSpaceIndex();

            BasedSequence trySequence = line.subSequence(nextNonSpace, line.length());
            Matcher matcher = ENUM_REF_DEF_PATTERN.matcher(trySequence);
            if (matcher.find()) {
                // abbreviation definition
                int openingStart = nextNonSpace + matcher.start(1);
                int openingEnd = nextNonSpace + matcher.end(1);
                BasedSequence openingMarker = line.subSequence(openingStart, openingStart + 2);
                BasedSequence text = line.subSequence(matcher.start(2), matcher.end(2));
                BasedSequence closingMarker = line.subSequence(openingEnd - 2, openingEnd);

                int contentOffset = options.contentIndent;

                EnumeratedReferenceBlockParser enumeratedReferenceBlockParser = new EnumeratedReferenceBlockParser(options, contentOffset);
                enumeratedReferenceBlockParser.block.setOpeningMarker(openingMarker);
                enumeratedReferenceBlockParser.block.setText(text);
                enumeratedReferenceBlockParser.block.setClosingMarker(closingMarker);
                BasedSequence enumeratedReference = trySequence.subSequence(matcher.end());
                enumeratedReferenceBlockParser.block.setEnumeratedReference(enumeratedReference);
                Paragraph paragraph = new Paragraph(enumeratedReference);
                enumeratedReferenceBlockParser.block.appendChild(paragraph);
                enumeratedReferenceBlockParser.block.setCharsFromContent();

                return BlockStart.of(enumeratedReferenceBlockParser)
                        .atIndex(line.length());
            } else {
                return BlockStart.none();
            }
        }
    }
}
