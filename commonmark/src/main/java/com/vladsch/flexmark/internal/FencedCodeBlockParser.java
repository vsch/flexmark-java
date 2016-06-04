package com.vladsch.flexmark.internal;

import com.vladsch.flexmark.internal.util.BasedSequence;
import com.vladsch.flexmark.internal.util.SubSequence;
import com.vladsch.flexmark.node.Block;
import com.vladsch.flexmark.node.FencedCodeBlock;
import com.vladsch.flexmark.parser.block.*;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FencedCodeBlockParser extends AbstractBlockParser {

    private static final Pattern OPENING_FENCE = Pattern.compile("^`{3,}(?!.*`)|^~{3,}(?!.*~)");
    private static final Pattern CLOSING_FENCE = Pattern.compile("^(?:`{3,}|~{3,})(?= *$)");

    private final FencedCodeBlock block = new FencedCodeBlock();
    private BlockContent content = new BlockContent();
    private char fenceChar;
    private int fenceLength;
    private int fenceIndent;

    public FencedCodeBlockParser(char fenceChar, int fenceLength, int fenceIndent) {
        this.fenceChar = fenceChar;
        this.fenceLength = fenceLength;
        this.fenceIndent = fenceIndent;
    }

    @Override
    public Block getBlock() {
        return block;
    }

    @Override
    public BlockContinue tryContinue(ParserState state) {
        int nextNonSpace = state.getNextNonSpaceIndex();
        int newIndex = state.getIndex();
        BasedSequence line = state.getLine();
        Matcher matcher = null;
        boolean matches = (state.getIndent() <= 3 &&
                nextNonSpace < line.length() &&
                line.charAt(nextNonSpace) == fenceChar);

        if (matches) {
            BasedSequence trySequence = line.subSequence(nextNonSpace, line.length());
            matcher = CLOSING_FENCE.matcher(trySequence);
            if (matcher.find()) {
                int foundFenceLength = matcher.group(0).length();

                if (foundFenceLength >= fenceLength) {
                    // closing fence - we're at end of line, so we can finalize now
                    block.setClosingMarker(trySequence.subSequence(0, foundFenceLength));
                    return BlockContinue.finished();
                }
            }
        }
        // skip optional spaces of fence indent
        int i = fenceIndent;
        while (i > 0 && newIndex < line.length() && line.charAt(newIndex) == ' ') {
            newIndex++;
            i--;
        }
        return BlockContinue.atIndex(newIndex);
    }

    @Override
    public void addLine(BasedSequence line, int eolLength) {
        content.add(line, eolLength);
    }

    @Override
    public void closeBlock() {
        // first line, if not blank, has the info string
        List<BasedSequence> lines = content.getLines();
        if (lines.size() > 0) {
            BasedSequence info = lines.get(0);
            if (!info.isBlank()) {
                block.setInfo(info.trim());
            }

            BasedSequence chars = content.getSpanningChars();
            BasedSequence spanningChars = chars.baseSubSequence(chars.getStartOffset(), lines.get(0).getEndOffset());

            if (lines.size() > 1) {
                // have more lines
                block.setContent(spanningChars, lines.subList(1, lines.size()));
            } else {
                block.setContent(spanningChars, SubSequence.EMPTY_LIST);
            }
        } else {
            block.setContent(content);
        }
        content = null;
    }

    public static class Factory extends AbstractBlockParserFactory {

        @Override
        public BlockStart tryStart(ParserState state, MatchedBlockParser matchedBlockParser) {
            int nextNonSpace = state.getNextNonSpaceIndex();
            BasedSequence line = state.getLine();
            Matcher matcher;
            if (state.getIndent() < 4) {
                BasedSequence trySequence = line.subSequence(nextNonSpace, line.length());
                if ((matcher = OPENING_FENCE.matcher(trySequence)).find()) {
                    int fenceLength = matcher.group(0).length();
                    char fenceChar = matcher.group(0).charAt(0);
                    FencedCodeBlockParser blockParser = new FencedCodeBlockParser(fenceChar, fenceLength, state.getIndent());
                    blockParser.block.setOpeningMarker(trySequence.subSequence(0, fenceLength));
                    return BlockStart.of(blockParser).atIndex(nextNonSpace + fenceLength);
                }
            }
            return BlockStart.none();
        }
    }
}

