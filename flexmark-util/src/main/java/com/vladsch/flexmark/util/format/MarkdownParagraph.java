package com.vladsch.flexmark.util.format;

import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.misc.CharPredicate;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.Range;
import com.vladsch.flexmark.util.sequence.RepeatedSequence;
import com.vladsch.flexmark.util.sequence.SequenceUtils;
import com.vladsch.flexmark.util.sequence.builder.SequenceBuilder;
import com.vladsch.flexmark.util.sequence.builder.tree.BasedOffsetTracker;
import com.vladsch.flexmark.util.sequence.builder.tree.OffsetInfo;
import com.vladsch.flexmark.util.sequence.builder.tree.Segment;
import com.vladsch.flexmark.util.sequence.mappers.SpecialLeadInHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.vladsch.flexmark.util.misc.CharPredicate.WHITESPACE;

public class MarkdownParagraph {
    final private static char MARKDOWN_START_LINE_CHAR = SequenceUtils.LS;             // https://www.fileformat.info/info/unicode/char/2028/index.htm LINE_SEPARATOR this one is not preserved but will cause a line break if not already at beginning of line
    public static final List<SpecialLeadInHandler> EMPTY_LEAD_IN_HANDLERS = Collections.emptyList();
    public static final List<TrackedOffset> EMPTY_OFFSET_LIST = Collections.emptyList();
    public static final TrackedOffset[] EMPTY_OFFSETS = new TrackedOffset[0];

    final @NotNull BasedSequence baseSeq;
    final @NotNull BasedSequence altSeq;
    final @NotNull CharWidthProvider charWidthProvider;

    private BasedSequence firstIndent = BasedSequence.NULL;
    private BasedSequence indent = BasedSequence.NULL;
    private int firstWidthOffset = 0;
    int width = 0;
    boolean keepHardLineBreaks = true;
    boolean keepSoftLineBreaks = false;
    boolean unEscapeSpecialLeadInChars = true;
    boolean escapeSpecialLeadInChars = true;
    boolean restoreTrackedSpaces = false;
    @Nullable DataHolder options = null;

    @NotNull List<? extends SpecialLeadInHandler> leadInHandlers = EMPTY_LEAD_IN_HANDLERS;
    private List<TrackedOffset> trackedOffsets = EMPTY_OFFSET_LIST;
    private boolean trackedOffsetsSorted = true;

    public MarkdownParagraph(CharSequence chars) {
        this(BasedSequence.of(chars));
    }

    public MarkdownParagraph(BasedSequence chars) {
        this(chars, chars, CharWidthProvider.NULL);
    }

    public MarkdownParagraph(@NotNull BasedSequence chars, @NotNull CharWidthProvider charWidthProvider) {
        this(chars, chars, charWidthProvider);
    }

    public MarkdownParagraph(@NotNull BasedSequence chars, @NotNull BasedSequence altChars, @NotNull CharWidthProvider charWidthProvider) {
        baseSeq = chars;
        this.altSeq = altChars;
        this.charWidthProvider = charWidthProvider;
    }

    public BasedSequence wrapTextNotTracked() {
        if (getFirstWidth() <= 0) return baseSeq;

        LeftAlignedWrapping wrapping = new LeftAlignedWrapping(baseSeq, baseSeq, null);
        return wrapping.wrapText();
    }

    @NotNull
    public Range getContinuationStartSplice(int offset, boolean afterSpace, boolean afterDelete) {
        BasedSequence baseSequence = altSeq.getBaseSequence();
        assert offset >= 0 && offset <= baseSequence.length();
        if (afterSpace && afterDelete) {
            BasedOffsetTracker preFormatTracker = BasedOffsetTracker.create(altSeq);
            int startOfLine = baseSequence.startOfLine(offset);
            if (startOfLine > altSeq.getStartOffset() && !baseSequence.isCharAt(offset, CharPredicate.SPACE_TAB_LINE_SEP)) {
                int previousNonBlank = baseSequence.lastIndexOfAnyNot(CharPredicate.SPACE_TAB_EOL, offset - 1);
                if (previousNonBlank < startOfLine) {
                    // delete range between last non-blank and offset index
                    @NotNull OffsetInfo offsetInfo = preFormatTracker.getOffsetInfo(offset, true);
                    int offsetIndex = offsetInfo.endIndex;
                    int previousNonBlankIndex = altSeq.lastIndexOfAnyNot(CharPredicate.SPACE_TAB_EOL, offsetIndex - 1);
                    return Range.of(previousNonBlankIndex + 1, offsetIndex);
                }
            }
        }
        return Range.NULL;
    }

    public BasedSequence wrapText() {
        if (getFirstWidth() <= 0) return baseSeq;
        if (trackedOffsets.isEmpty()) return wrapTextNotTracked();

        // Adjust input text for wrapping by removing any continuation splice regions
        BasedSequence input = baseSeq;
        BasedSequence altInput = altSeq;
        Range lastRange = Range.NULL;

        sortedTrackedOffsets();

        // delete any space ranges that need to be spliced
        int iMax = trackedOffsets.size();
        for (int i = iMax; i-- > 0; ) {
            TrackedOffset trackedOffset = trackedOffsets.get(i);
            if (lastRange.isEmpty() || !lastRange.contains(trackedOffset.getOffset())) {
                lastRange = getContinuationStartSplice(trackedOffset.getOffset(), trackedOffset.isAfterSpaceEdit(), trackedOffset.isAfterDelete());
                if (lastRange.isNotEmpty()) {
                    trackedOffset.setSpliced(true);
                    input = input.delete(lastRange.getStart(), lastRange.getEnd());
                    altInput = altInput.delete(lastRange.getStart(), lastRange.getEnd());
                }
            }
        }

        LeftAlignedWrapping wrapping = new LeftAlignedWrapping(input, altInput, trackedOffsets);
        BasedSequence wrapped = wrapping.wrapText();

        // FIX: apply after wrapping fixes
        BasedOffsetTracker tracker = BasedOffsetTracker.create(wrapped);
        BasedSequence unwrapped = this.baseSeq;

        if (restoreTrackedSpaces) {
            // NOTE: Restore trailing spaces at end of line if it has tracked offset on it
            int restoredAppendSpaces = 0;

            int unwrappedLastNonBlank = unwrapped.lastIndexOfAnyNot(CharPredicate.WHITESPACE) + 1;
            BasedOffsetTracker unwrappedTracker = BasedOffsetTracker.create(altSeq);

            for (int i = iMax; i-- > 0; ) {
                TrackedOffset trackedOffset = trackedOffsets.get(i);
                boolean isAfterSpaceInsert = trackedOffset.isAfterSpaceEdit() && trackedOffset.isAfterInsert() && trackedOffset.getOffset() > 0;
                int startDelta = isAfterSpaceInsert ? 1 : 0;
                OffsetInfo baseInfo = unwrappedTracker.getOffsetInfo(trackedOffset.getOffset() - startDelta, startDelta == 0);
                boolean isLineSepPrev = unwrapped.safeCharAt(baseInfo.startIndex) == SequenceUtils.LS;
                int unwrappedIndex = isLineSepPrev ? baseInfo.startIndex : baseInfo.endIndex;

                int countedSpacesBefore = unwrapped.countTrailing(CharPredicate.SPACE_TAB, unwrappedIndex);
                int countedSpacesAfter = unwrapped.countLeading(CharPredicate.SPACE_TAB, unwrappedIndex);
                int remainingSpacesBefore = countedSpacesBefore;

                int indexSpacesBefore = trackedOffset.getSpacesBefore() >= 0 ? Math.min(countedSpacesBefore, trackedOffset.getSpacesBefore()) : countedSpacesBefore;
                remainingSpacesBefore -= indexSpacesBefore;

                if (trackedOffset.isSpliced()) {
                    indexSpacesBefore = 0;
                }
                final int indexSpacesAfter = trackedOffset.getSpacesAfter() >= 0 ? Math.min(countedSpacesAfter + remainingSpacesBefore, trackedOffset.getSpacesAfter()) : countedSpacesAfter;

                int unwrappedSpacesBefore = indexSpacesBefore;
                int unwrappedSpacesAfter = indexSpacesAfter;
                boolean needSpace = unwrappedSpacesBefore > 0;
                int unwrappedPrevIndex = unwrappedIndex + indexSpacesAfter;
                boolean isLineSep = !isLineSepPrev && unwrapped.safeCharAt(unwrappedPrevIndex) == SequenceUtils.LS;

                int endLine = unwrapped.endOfLine(unwrappedIndex);
                int startLine = unwrapped.startOfLine(unwrappedIndex);
                int firstNonBlank = unwrapped.indexOfAnyNot(CharPredicate.SPACE, startLine, endLine);
                startDelta = Math.min(startDelta, unwrappedSpacesBefore);

                // NOTE: if have alternate base sequence then mapping is done using the computed baseSeq offset
                OffsetInfo info = tracker.getOffsetInfo(unwrappedIndex, startDelta == 0 && !isLineSepPrev);
                int index = isLineSepPrev ? info.startIndex : info.endIndex;

                if (isAfterSpaceInsert && startDelta > 0 && indexSpacesAfter == 0 && wrapped.safeCharAt(index) != this.baseSeq.safeCharAt(unwrappedIndex)
                        && wrapped.safeCharAt(index - 1) == this.baseSeq.safeCharAt(unwrappedIndex) && wrapped.safeCharAt(info.startIndex - 1) == ' ') {
                    index = index - 1;
                }

                if (info.pos >= 0 && info.pos < tracker.size() && isAfterSpaceInsert) {
                    Segment segment = tracker.getSegmentOffsetTree().getSegment(info.pos, tracker.getSequence());
                    if (segment.getStartOffset() == unwrappedIndex) {
                        // at start of segment after space need to move it after prev segment
                        info = tracker.getOffsetInfo(unwrappedIndex - unwrappedSpacesBefore, false);
                        index = info.endIndex;
                    } else if (wrapped.isCharAt(index + 1, CharPredicate.EOL)) {
                        // EOL inserted in between, move it to next char
                        info = tracker.getOffsetInfo(unwrappedIndex + 1, false);
                        index = info.endIndex;
                    }

//                    System.out.println(String.format("startDelta: %d, spacesBefore: %d, spacesAfter: %d, prevCharAt: %d, charAt: %d, nextCharAt: %d, prevBaseAt: %d, baseAt: %d, nextBaseAt: %d, baseIndex: %d, info: %s, segment: %s",
//                            startDelta,
//                            baseIndexSpaces,
//                            baseIndexSpacesAfter,
//                            (int) wrapped.safeCharAt(index - 1),
//                            (int) wrapped.safeCharAt(index),
//                            (int) wrapped.safeCharAt(index + 1),
//                            (int) baseSeq.safeCharAt(baseIndex - 1),
//                            (int) baseSeq.safeCharAt(baseIndex),
//                            (int) baseSeq.safeCharAt(baseIndex + 1),
//                            baseIndex,
//                            info.toString(),
//                            segment.toString()));
                }

                int endLineWrapped = wrapped.endOfLine(index);
                int startLineWrapped = wrapped.startOfLine(index);
                int firstNonBlankWrapped = wrapped.indexOfAnyNot(CharPredicate.SPACE, startLineWrapped, endLineWrapped);

                if (trackedOffset.isAfterInsert() || trackedOffset.isAfterDelete()) {
                    // need to keep it at the previous character but not when inserting space or deleting 1 char surrounded by spaces,
                    // except when offset is at the end of line
                    if (!trackedOffset.isAfterSpaceEdit()) {
                        // if deleting non-space surrounded by spaces at the beginning of a paragraph then the preceding space is deleted so need to keep at position and insert spaces before
                        if (index == 0 && unwrappedSpacesBefore > 0 && unwrappedSpacesAfter > 0) {

                        } else {
//                            int basePrevIndex = baseIndex - 1;
//                            boolean isLineSep = baseSeq.safeCharAt(baseIndex) == SequenceUtils.LS;
//                            if (isLineSep) {
//                                info = tracker.getOffsetInfo(baseIndex - 1, true);
//                            }
                            index = info.startIndex;
                            endLineWrapped = wrapped.endOfLine(index);
                            startLineWrapped = wrapped.startOfLine(index);
                            firstNonBlankWrapped = wrapped.indexOfAnyNot(CharPredicate.SPACE, startLineWrapped, endLineWrapped);
                            if (trackedOffset.isAfterDelete() && index == endLineWrapped) unwrappedSpacesAfter = 0;
                            unwrappedSpacesBefore = 0;
                        }
                    } else if (index == firstNonBlankWrapped) {
                        unwrappedSpacesBefore = 0;
                    }
                }

                // NOTE: if typing space before or on start of continuation line, then do not move tracked offset to previous line
                if (index <= firstNonBlankWrapped) {
                    int unwrappedOffset = wrapped.getIndexOffset(firstNonBlankWrapped);

                    if (unwrappedOffset >= 0) {
                        if (!(isLineSep || isLineSepPrev) && unwrappedIndex <= firstNonBlank && trackedOffset.isAfterSpaceEdit() && !trackedOffset.isAfterDelete()) {
                            unwrappedSpacesBefore = 0;
                            unwrappedSpacesAfter = 0;
                        } else if (isLineSep || isLineSepPrev || trackedOffset.isAfterDelete() || (trackedOffset.isAfterSpaceEdit() && unwrappedSpacesAfter > 0)) {
                            // tracked offset is followed by Line Separator, move the offset to end of previous line
                            index = wrapped.endOfLine(wrapped.startOfLine(index) - 1);

                            unwrappedIndex = unwrappedPrevIndex;
                            endLine = unwrapped.startOfLine(unwrappedIndex);
                            startLine = unwrapped.startOfLine(unwrappedIndex);
                            firstNonBlank = unwrapped.indexOfAnyNot(CharPredicate.SPACE, startLine, endLine);
                            unwrappedSpacesBefore = (needSpace || isLineSep
                                    || trackedOffset.isAfterInsert() && trackedOffset.isAfterSpaceEdit()
                                    || trackedOffset.isAfterDelete() && !trackedOffset.isAfterSpaceEdit()) ? 1 : Math.min(1, unwrappedSpacesBefore);
                            unwrappedSpacesAfter = 0;
                        }
                    }
                }

                int lastNonBlank = unwrapped.lastIndexOfAnyNot(CharPredicate.SPACE, endLine);
                int wrappedOffsetSpaces = wrapped.countTrailing(CharPredicate.SPACE, index);

                if (unwrappedIndex >= lastNonBlank) {
                    // add only what is missing
                    unwrappedSpacesBefore = Math.max(0, unwrappedSpacesBefore - wrappedOffsetSpaces);
                    unwrappedSpacesAfter = 0;
                } else if (unwrappedIndex > firstNonBlank) {
                    // spaces before caret, see if need to add max 1
                    int spacesBefore = wrapped.countTrailing(CharPredicate.SPACE, index);
                    unwrappedSpacesBefore = Math.max(0, Math.min(1, unwrappedSpacesBefore - spacesBefore));
                    int spacesAfter = wrapped.countLeading(CharPredicate.SPACE, index);
                    unwrappedSpacesAfter = Math.max(0, Math.min(1, unwrappedSpacesAfter - spacesAfter));
                } else if (unwrappedIndex < firstNonBlank && trackedOffset.isAfterDelete() && !trackedOffset.isAfterSpaceEdit() && unwrappedSpacesBefore > 0 && unwrappedSpacesAfter > 0) {
                    // spaces before caret, see if need to add max 1
                    info = tracker.getOffsetInfo(unwrapped.getIndexOffset(firstNonBlank), true);
                    index = info.endIndex;
                    unwrappedSpacesBefore = 0;
                    unwrappedSpacesAfter = 1;
                } else {
                    unwrappedSpacesBefore = 0;
                    unwrappedSpacesAfter = 0;
                }

                if (unwrappedIndex < unwrappedLastNonBlank) {
                    // insert in middle
                    if (unwrappedSpacesBefore + unwrappedSpacesAfter > 0) {
                        wrapped = wrapped.insert(index, RepeatedSequence.ofSpaces(unwrappedSpacesBefore + unwrappedSpacesAfter));
                        // need to adjust all following offsets by the amount inserted
                        for (int j = i + 1; j < iMax; j++) {
                            TrackedOffset trackedOffset1 = trackedOffsets.get(j);
                            int indexJ = trackedOffset1.getIndex();
                            trackedOffset1.setIndex(indexJ + unwrappedSpacesBefore + unwrappedSpacesAfter);
                        }
                    }
                } else {
                    restoredAppendSpaces = Math.max(restoredAppendSpaces, unwrappedSpacesBefore);
                }

                trackedOffset.setIndex(index + unwrappedSpacesBefore);
            }

            // append any trailing spaces
            if (restoredAppendSpaces > 0) {
                wrapped = wrapped.appendSpaces(restoredAppendSpaces);
            }
        } else {
            // Now we map the tracked offsets to indexes in the resulting text
            for (int i = iMax; i-- > 0; ) {
                TrackedOffset trackedOffset = trackedOffsets.get(i);
                int offset = trackedOffset.getOffset();
                boolean baseIsWhiteSpaceAtOffset = unwrapped.isBaseCharAt(offset, WHITESPACE);

                if (baseIsWhiteSpaceAtOffset && !(unwrapped.isBaseCharAt(offset - 1, WHITESPACE))) {
                    // we need to use previous non-blank and use that offset
                    OffsetInfo info = tracker.getOffsetInfo(offset - 1, false);
                    trackedOffset.setIndex(info.endIndex);
                } else if (!baseIsWhiteSpaceAtOffset && unwrapped.isBaseCharAt(offset + 1, WHITESPACE)) {
                    // we need to use this non-blank and use that offset
                    OffsetInfo info = tracker.getOffsetInfo(offset, false);
                    trackedOffset.setIndex(info.startIndex);
                } else {
                    OffsetInfo info = tracker.getOffsetInfo(offset, true);
                    trackedOffset.setIndex(info.endIndex);
                }
            }
        }

        return wrapped;
    }

    public void addTrackedOffset(@NotNull TrackedOffset trackedOffset) {
        if (trackedOffsets == EMPTY_OFFSET_LIST) trackedOffsets = new ArrayList<>();
        assert trackedOffset.getOffset() >= 0 && trackedOffset.getOffset() <= altSeq.getBaseSequence().length();
        trackedOffsets.removeIf(it -> it.getOffset() == trackedOffset.getOffset());
        trackedOffsets.add(trackedOffset);
        trackedOffsetsSorted = false;
    }

    public List<TrackedOffset> getTrackedOffsets() {
        return sortedTrackedOffsets();
    }

    private List<TrackedOffset> sortedTrackedOffsets() {
        if (!trackedOffsetsSorted) {
            trackedOffsets.sort(Comparator.comparing(TrackedOffset::getOffset));
            trackedOffsetsSorted = true;
        }
        return trackedOffsets;
    }

    @Nullable
    public TrackedOffset getTrackedOffset(int offset) {
        sortedTrackedOffsets();

        for (TrackedOffset trackedOffset : trackedOffsets) {
            if (trackedOffset.getOffset() == offset) return trackedOffset;
            if (trackedOffset.getOffset() > offset) break;
        }
        return null;
    }

    @NotNull
    public List<? extends SpecialLeadInHandler> getLeadInHandlers() {
        return leadInHandlers;
    }

    public void setLeadInHandlers(@NotNull List<? extends SpecialLeadInHandler> leadInHandlers) {
        this.leadInHandlers = leadInHandlers;
    }

    @Nullable
    public DataHolder getOptions() {
        return options;
    }

    public void setOptions(@Nullable DataHolder options) {
        this.options = options;
    }

    public boolean isRestoreTrackedSpaces() {
        return restoreTrackedSpaces;
    }

    public void setRestoreTrackedSpaces(boolean restoreTrackedSpaces) {
        this.restoreTrackedSpaces = restoreTrackedSpaces;
    }

    @NotNull
    public BasedSequence getChars() {
        return baseSeq;
    }

    public CharSequence getFirstIndent() {
        return firstIndent;
    }

    public void setFirstIndent(CharSequence firstIndent) {
        this.firstIndent = BasedSequence.of(firstIndent);
    }

    public CharSequence getIndent() {
        return indent;
    }

    public void setIndent(CharSequence indent) {
        this.indent = BasedSequence.of(indent);
        if (this.firstIndent.isNull()) this.firstIndent = this.indent;
    }

    public int getFirstWidth() {
        return (width == 0) ? 0 : Math.max(0, width + firstWidthOffset);
    }

    public int getFirstWidthOffset() {
        return firstWidthOffset;
    }

    public void setFirstWidthOffset(int firstWidthOffset) {
        this.firstWidthOffset = firstWidthOffset;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = Math.max(0, width);
    }

    public boolean getKeepHardBreaks() {
        return keepHardLineBreaks;
    }

    public void setKeepHardBreaks(boolean keepHardBreaks) {
        this.keepHardLineBreaks = keepHardBreaks;
    }

    public boolean getKeepSoftBreaks() {
        return keepSoftLineBreaks;
    }

    public boolean isUnEscapeSpecialLeadIn() {
        return unEscapeSpecialLeadInChars;
    }

    public void setUnEscapeSpecialLeadIn(boolean unEscapeSpecialLeadInChars) {
        this.unEscapeSpecialLeadInChars = unEscapeSpecialLeadInChars;
    }

    public boolean isEscapeSpecialLeadIn() {
        return escapeSpecialLeadInChars;
    }

    public void setEscapeSpecialLeadIn(boolean escapeSpecialLeadInChars) {
        this.escapeSpecialLeadInChars = escapeSpecialLeadInChars;
    }

    public void setKeepSoftBreaks(boolean keepLineBreaks) {
        this.keepSoftLineBreaks = keepLineBreaks;
    }

    @NotNull
    public CharWidthProvider getCharWidthProvider() {
        return charWidthProvider;
    }

    public enum TextType {
        WORD,
        SPACE,
        BREAK,
        MARKDOWN_BREAK,
        MARKDOWN_START_LINE
    }

    public static class Token {
        public final @NotNull TextType type;
        public final @NotNull Range range;
        public final boolean isFirstWord;

        private Token(@NotNull TextType type, @NotNull Range range, boolean isFirstWord) {
            this.type = type;
            this.range = range;
            this.isFirstWord = isFirstWord;
        }

        @Override
        public String toString() {
            return "token: " + type + " " + range + (isFirstWord ? " isFirst" : "");
        }

        public BasedSequence subSequence(BasedSequence charSequence) {
            return range.basedSubSequence(charSequence);
        }

        public CharSequence subSequence(CharSequence charSequence) {
            return range.charSubSequence(charSequence);
        }

        @NotNull
        public static Token of(@NotNull TextType type, @NotNull Range range) {
            return new Token(type, range, false);
        }

        @NotNull
        public static Token of(@NotNull TextType type, int start, int end) {
            return new Token(type, Range.of(start, end), false);
        }

        @NotNull
        public static Token of(@NotNull TextType type, @NotNull Range range, boolean isFirstWord) {
            return new Token(type, range, isFirstWord);
        }

        @NotNull
        public static Token of(@NotNull TextType type, int start, int end, boolean isFirstWord) {
            return new Token(type, Range.of(start, end), isFirstWord);
        }
    }

    class LeftAlignedWrapping {
        final @NotNull BasedSequence baseSeq;
        final @NotNull BasedSequence altSeq;
        final @Nullable List<TrackedOffset> trackedOffsets;
        final SequenceBuilder result;
        final TextTokenizer tokenizer;
        int col = 0;
        int lineCount = 0;
        final int spaceWidth = charWidthProvider.getSpaceWidth();
        CharSequence lineIndent = getFirstIndent();
        final CharSequence nextIndent = getIndent();
        int lineWidth = spaceWidth * getFirstWidth();
        final int nextWidth = width <= 0 ? Integer.MAX_VALUE : spaceWidth * width;
        int wordsOnLine = 0;
        BasedSequence leadingIndent = null;
        BasedSequence lastSpace = null;
        @NotNull List<? extends SpecialLeadInHandler> leadInHandlers = MarkdownParagraph.this.leadInHandlers;
        boolean unEscapeSpecialLeadInChars = MarkdownParagraph.this.unEscapeSpecialLeadInChars;
        boolean escapeSpecialLeadInChars = MarkdownParagraph.this.escapeSpecialLeadInChars;

        LeftAlignedWrapping(@NotNull BasedSequence baseSeq, @NotNull BasedSequence altSeq, @Nullable List<TrackedOffset> trackedOffsets) {
            assert altSeq.equals(baseSeq) : "altSeq must be character identical to baseSeq";

            this.baseSeq = baseSeq;
            this.altSeq = altSeq;
            this.trackedOffsets = trackedOffsets;
            result = SequenceBuilder.emptyBuilder(baseSeq);
            tokenizer = new TextTokenizer(baseSeq);
        }

        void advance() {
            tokenizer.next();
        }

        void addToken(Token token) {
            addChars(baseSeq.subSequence(token.range.getStart(), token.range.getEnd()));
        }

        void addChars(CharSequence charSequence) {
            result.append(charSequence);
            col += charWidthProvider.getStringWidth(charSequence);
        }

        void addSpaces(int count) {
            result.append(' ', count);
            col += charWidthProvider.getSpaceWidth() * count;
        }

        BasedSequence addSpaces(BasedSequence sequence, int count) {
            if (count <= 0) return sequence;

            BasedSequence remainder = null;

            // NOTE: can do splitting add from sequence before and after padding spaces to have start/end range if needed
            if (sequence != null) {
                addChars(sequence.subSequence(0, Math.min(sequence.length(), count)));

                if (sequence.length() > count) {
                    remainder = sequence.subSequence(count);
                }

                count = Math.max(0, count - sequence.length());
            }

            // add more spaces if needed
            if (count > 0) {
                addSpaces(count);
            }

            return remainder;
        }

        void afterLineBreak() {
            col = 0;
            wordsOnLine = 0;
            lineCount++;
            lineIndent = nextIndent;
            lineWidth = nextWidth;
            lastSpace = null;
            leadingIndent = null;
        }

        void processLeadInEscape(List<? extends SpecialLeadInHandler> handlers, BasedSequence sequence) {
            if (sequence.isNotEmpty() && escapeSpecialLeadInChars) {
                for (SpecialLeadInHandler handler : handlers) {
                    if (handler.escape(sequence, options, this::addChars)) return;
                }
            }
            addChars(sequence);
        }

        void processLeadInUnEscape(List<? extends SpecialLeadInHandler> handlers, BasedSequence sequence) {
            if (sequence.isNotEmpty() && unEscapeSpecialLeadInChars) {
                for (SpecialLeadInHandler handler : handlers) {
                    if (handler.unEscape(sequence, options, this::addChars)) return;
                }
            }
            addChars(sequence);
        }

        @NotNull
        BasedSequence wrapText() {
            while (true) {
                final Token token = tokenizer.getToken();
                if (token == null) break;
                switch (token.type) {
                    case SPACE: {
                        if (col == 0) leadingIndent = baseSeq.subSequence(token.range);
                        else lastSpace = baseSeq.subSequence(token.range);
                        advance();
                        break;
                    }

                    case WORD: {
                        if (col == 0 || col + charWidthProvider.getStringWidth(token.subSequence(baseSeq)) + spaceWidth <= lineWidth) {
                            // fits, add it
                            boolean firstNonBlank = col == 0;
//                            System.out.println("token: " + token + " chars: " + chars.subSequence(token.range));

                            if (col > 0) {
                                lastSpace = addSpaces(lastSpace, 1);
                            } else {
                                if (!SequenceUtils.isEmpty(lineIndent)) {
                                    addChars(lineIndent);
                                }
                                leadingIndent = null;
                            }

                            if (firstNonBlank && !token.isFirstWord) {
                                processLeadInEscape(leadInHandlers, baseSeq.subSequence(token.range));
                            } else if (!firstNonBlank && token.isFirstWord) {
                                processLeadInUnEscape(leadInHandlers, baseSeq.subSequence(token.range));
                            } else {
                                addToken(token);
                            }

                            advance();
                            wordsOnLine++;
                        } else {
                            // need to insert a line break and repeat
                            addChars(SequenceUtils.EOL);
                            afterLineBreak();
                        }
                        break;
                    }

                    case MARKDOWN_START_LINE: {
                        // start a new line if not already new
                        if (col > 0) {
                            addChars(SequenceUtils.EOL);
                            afterLineBreak();
                        }
                        advance();
                        break;
                    }

                    case MARKDOWN_BREAK: {
                        // start a new line if not already new
                        if (keepHardLineBreaks) {
                            if (col > 0) {
                                addToken(token);
                                afterLineBreak();
                            }
                        } else {
                            // treat as a space
                            lastSpace = baseSeq.subSequence(token.range);
                        }
                        advance();
                        break;
                    }

                    case BREAK: {
                        if (col > 0 && keepSoftLineBreaks) {
                            // only use the EOL
                            addToken(token);
                            afterLineBreak();
                        }
                        advance();
                        break;
                    }
                }
            }

            return result.toSequence();
        }
    }

    public static class TextTokenizer {
        final private CharSequence chars;
        private final int maxIndex;
        private int index = 0;
        private int lastPos = 0;
        private boolean isInWord = false;
        private boolean isFirstNonBlank = true;
        private int lastConsecutiveSpaces = 0;
        private @Nullable Token token = null;

        TextTokenizer(@NotNull CharSequence chars) {
            this.chars = chars;
            maxIndex = this.chars.length();
            reset();
        }

        public void reset() {
            index = 0;
            lastPos = 0;
            isInWord = false;
            token = null;
            lastConsecutiveSpaces = 0;
            isFirstNonBlank = true;
            next();
        }

        @Nullable
        Token getToken() {
            return token;
        }

        @NotNull
        public List<Token> asList() {
            ArrayList<Token> tokens = new ArrayList<Token>();
            reset();

            while (token != null) {
                tokens.add(token);
                next();
            }

            return tokens;
        }

        void next() {
            token = null;
            while (index < maxIndex) {
                char c = chars.charAt(index);
                if (isInWord) {
                    if (c == ' ' || c == '\t' || c == '\n' || c == MARKDOWN_START_LINE_CHAR) {
                        isInWord = false;
                        boolean isFirstWord = isFirstNonBlank;
                        isFirstNonBlank = false;

                        if (lastPos < index) {
                            // have a word
                            token = Token.of(TextType.WORD, lastPos, index, isFirstWord);
                            lastPos = index;
                            break;
                        }
                    } else {
                        index++;
                    }
                } else {
                    // in white space
                    if (c != ' ' && c != '\t' && c != '\n' && c != MARKDOWN_START_LINE_CHAR) {
                        if (lastPos < index) {
                            token = Token.of(TextType.SPACE, lastPos, index);
                            lastPos = index;
                            isInWord = true;
                            lastConsecutiveSpaces = 0;
                            break;
                        } else {
                            isInWord = true;
                            lastConsecutiveSpaces = 0;
                        }
                    } else {
                        if (c == '\n') {
                            if (lastConsecutiveSpaces >= 2) {
                                token = Token.of(TextType.MARKDOWN_BREAK, index - lastConsecutiveSpaces, index + 1);
                            } else {
                                token = Token.of(TextType.BREAK, index, index + 1);
                            }

                            lastPos = index + 1;
                            lastConsecutiveSpaces = 0;
                            isFirstNonBlank = true;
                            index++;
                            break;
                        } else if (c == MARKDOWN_START_LINE_CHAR) {
                            token = Token.of(TextType.MARKDOWN_START_LINE, index, index + 1);
                            lastPos = index + 1;
                            lastConsecutiveSpaces = 0;
                            index++;
                            break;
                        } else {
                            if (c == ' ') lastConsecutiveSpaces++;
                            else lastConsecutiveSpaces = 0;
                            index++;
                        }
                    }
                }
            }

            if (lastPos < index) {
                if (isInWord) {
                    token = Token.of(TextType.WORD, lastPos, index, isFirstNonBlank);
                    isFirstNonBlank = false;
                } else {
                    token = Token.of(TextType.SPACE, lastPos, index);
                }
                lastPos = index;
            }
        }
    }
}

