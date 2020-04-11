package com.vladsch.flexmark.util.format;

import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.SharedDataKeys;
import com.vladsch.flexmark.util.misc.CharPredicate;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.Range;
import com.vladsch.flexmark.util.sequence.RepeatedSequence;
import com.vladsch.flexmark.util.sequence.SequenceUtils;
import com.vladsch.flexmark.util.sequence.builder.SequenceBuilder;
import com.vladsch.flexmark.util.sequence.builder.tree.BasedOffsetTracker;
import com.vladsch.flexmark.util.sequence.builder.tree.OffsetInfo;
import com.vladsch.flexmark.util.sequence.mappers.SpecialLeadInHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.vladsch.flexmark.util.misc.CharPredicate.*;

public class MarkdownParagraph {
    final private static char MARKDOWN_START_LINE_CHAR = SequenceUtils.LS;             // https://www.fileformat.info/info/unicode/char/2028/index.htm LINE_SEPARATOR this one is not preserved but will cause a line break if not already at beginning of line
    final public static List<SpecialLeadInHandler> EMPTY_LEAD_IN_HANDLERS = Collections.emptyList();
    final public static List<TrackedOffset> EMPTY_OFFSET_LIST = Collections.emptyList();

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

        LeftAlignedWrapping wrapping = new LeftAlignedWrapping(baseSeq);
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

    @NotNull
    BasedSequence resolveTrackedOffsets(@NotNull BasedSequence unwrapped, @NotNull BasedSequence wrapped) {
        // Now we map the tracked offsets to indexes in the resulting text
        BasedOffsetTracker tracker = BasedOffsetTracker.create(wrapped);
        int iMax = trackedOffsets.size();
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
        return wrapped;
    }

    public BasedSequence wrapText() {
        if (getFirstWidth() <= 0) return baseSeq;
        if (trackedOffsets.isEmpty()) return wrapTextNotTracked();

        // Adjust input text for wrapping by removing any continuation splice regions
        sortedTrackedOffsets();

        // delete any space ranges that need to be spliced
        BasedSequence baseSpliced = baseSeq;
        BasedSequence altSpliced = altSeq;
        Range lastRange = Range.NULL;

        {
            int iMax = trackedOffsets.size();
            for (int i = iMax; i-- > 0; ) {
                TrackedOffset trackedOffset = trackedOffsets.get(i);
                if (lastRange.isEmpty() || !lastRange.contains(trackedOffset.getOffset())) {
                    lastRange = getContinuationStartSplice(trackedOffset.getOffset(), trackedOffset.isAfterSpaceEdit(), trackedOffset.isAfterDelete());
                    if (lastRange.isNotEmpty()) {
                        trackedOffset.setSpliced(true);
                        baseSpliced = baseSpliced.delete(lastRange.getStart(), lastRange.getEnd());
                        altSpliced = altSpliced.delete(lastRange.getStart(), lastRange.getEnd());
                    }
                }
            }
        }

        assert baseSpliced.equals(altSpliced);

        LeftAlignedWrapping textWrapper = new LeftAlignedWrapping(baseSpliced);
        BasedSequence wrapped = textWrapper.wrapText();

        if (restoreTrackedSpaces) {
            if (indent.isNotEmpty() || firstIndent.isNotEmpty()) throw new IllegalStateException("restoreTrackedSpaces is not supported with indentation applied by MarkdownParagraph");

            wrapped = resolveTrackedOffsetsEdit(baseSpliced, altSpliced, wrapped);
//            wrapped = resolveTrackedOffsetsEdit(baseSeq, wrapped);
        } else {
            wrapped = resolveTrackedOffsets(baseSeq, wrapped);
        }

        return wrapped;
    }

    BasedSequence resolveTrackedOffsetsEdit(BasedSequence baseSpliced, BasedSequence altSpliced, BasedSequence wrapped) {
        Boolean inTest = SharedDataKeys.RUNNING_TESTS.get(options);
        BasedSequence spliced = BasedSequence.of(baseSpliced.toString());
        LeftAlignedWrapping altTextWrapper = new LeftAlignedWrapping(spliced);
        BasedSequence altWrapped = spliced.getBuilder().append(altTextWrapper.wrapText()).toSequence(altSpliced, CharPredicate.LINE_SEP, CharPredicate.SPACE_TAB_EOL);

        BasedOffsetTracker tracker = BasedOffsetTracker.create(altSeq);
        BasedOffsetTracker altTracker = BasedOffsetTracker.create(altWrapped);

        int iMax = trackedOffsets.size();
        BasedSequence baseSequence = altSeq.getBaseSequence();
        BasedSequence altUnwrapped = altSeq;

        // NOTE: Restore trailing spaces at end of line if it has tracked offset on it
        int restoredAppendSpaces = 0;

        // determine in reverse offset order
        for (int i = iMax; i-- > 0; ) {
            TrackedOffset trackedOffset = trackedOffsets.get(i);

            int offset = trackedOffset.getOffset();
            int countedSpacesBefore = baseSequence.countTrailingSpaceTab(offset);
            int countedSpacesAfter = baseSequence.countLeadingSpaceTab(offset);

            if (inTest) {
                assert trackedOffset.getSpacesBefore() == countedSpacesBefore;
                assert trackedOffset.getSpacesAfter() == countedSpacesAfter;
            }

            char baseCharAt = baseSequence.safeCharAt(offset);
            char prevBaseCharAt = baseSequence.safeCharAt(offset - countedSpacesBefore - 1);
            char nextBaseCharAt = baseSequence.safeCharAt(offset + countedSpacesAfter);

            int anchorOffset;
            int anchorDelta = 0;
            int anchorIndex;
            boolean isLineSep = false;
            String anchorResolvedBy = "";

            if (inTest) {
                System.out.println(trackedOffset);
            }

            if (!CharPredicate.SPACE_TAB.test(baseCharAt)) {
                anchorOffset = offset;
                anchorIndex = tracker.getOffsetInfo(anchorOffset, false).startIndex;

                if (altUnwrapped.safeCharAt(anchorIndex - 1) == SequenceUtils.LS) {
                    // have line sep at anchor
                    isLineSep = true;
                    anchorResolvedBy = "LSep ";
                }
            } else {
                if (!CharPredicate.SPACE_TAB_EOL.test(prevBaseCharAt)) {
                    anchorOffset = offset - countedSpacesBefore;
                    anchorIndex = tracker.getOffsetInfo(anchorOffset - 1, false).endIndex;
                    anchorResolvedBy = "Prev ";
                } else if (!CharPredicate.SPACE_TAB_EOL.test(nextBaseCharAt)) {
                    anchorOffset = offset + countedSpacesAfter;
                    anchorIndex = tracker.getOffsetInfo(anchorOffset, false).startIndex;
                    anchorResolvedBy = "Next ";
                } else {
                    throw new IllegalStateException(String.format("Should not be here. altSeq: '%s'", altUnwrapped));
                }
            }

            if (inTest) {
                System.out.println(String.format("%sBaseSequence offset: `%s`", anchorResolvedBy, baseSequence.safeSubSequence(offset - 10, offset).toVisibleWhitespaceString() + "|" + baseSequence.safeSubSequence(offset, offset + 10).toVisibleWhitespaceString()));
                System.out.println(String.format("%sBaseSequence anchor: `%s`", anchorResolvedBy, baseSequence.safeSubSequence(anchorOffset - 10, anchorOffset).toVisibleWhitespaceString() + "|" + baseSequence.safeSubSequence(anchorOffset, anchorOffset + 10).toVisibleWhitespaceString()));
                System.out.println(String.format("%saltUnwrapped anchor: `%s`", anchorResolvedBy, altUnwrapped.safeSubSequence(anchorIndex - 10, anchorIndex).toVisibleWhitespaceString() + "|" + altUnwrapped.safeSubSequence(anchorIndex, anchorIndex + 10).toVisibleWhitespaceString()));
            }

            // now see where it is in the wrapped sequence
            int wrappedIndex = altTracker.getOffsetInfo(anchorOffset, false).startIndex;

            if (inTest) {
                System.out.println(String.format("altWrapped anchor: `%s`", altWrapped.safeSubSequence(wrappedIndex - 10, wrappedIndex).toVisibleWhitespaceString() + "|" + altWrapped.safeSubSequence(wrappedIndex, wrappedIndex + 10).toVisibleWhitespaceString()));
                System.out.println(String.format("wrapped anchor: `%s`", wrapped.safeSubSequence(wrappedIndex - 10, wrappedIndex).toVisibleWhitespaceString() + "|" + wrapped.safeSubSequence(wrappedIndex, wrappedIndex + 10).toVisibleWhitespaceString()));
            }

            assert baseSequence.safeCharAt(anchorOffset) == altUnwrapped.safeCharAt(anchorIndex + anchorDelta)
                    // NOTE: alt sequence could have spaces removed which would result in space in base sequence and EOL in alt sequence
                    || baseSequence.isCharAt(anchorOffset, WHITESPACE) && altUnwrapped.isCharAt(anchorIndex + anchorDelta, WHITESPACE_OR_NUL)
                    : String.format("baseSeq.charAt: %d != altUnwrapped.charAt: %d, altWrapped anchor: '%s', wrapped anchor: '%s'"
                    , (int) baseSequence.safeCharAt(anchorOffset)
                    , (int) altUnwrapped.safeCharAt(anchorIndex + anchorDelta)
                    , altWrapped.safeSubSequence(wrappedIndex - 10, wrappedIndex).toVisibleWhitespaceString() + "|" + altWrapped.safeSubSequence(wrappedIndex, wrappedIndex + 10).toVisibleWhitespaceString()
                    , wrapped.safeSubSequence(wrappedIndex - 10, wrappedIndex).toVisibleWhitespaceString() + "|" + wrapped.safeSubSequence(wrappedIndex, wrappedIndex + 10).toVisibleWhitespaceString()
            );

            int wrappedAdjusted = 0;
            // take char start but if at whitespace, use previous for validation
            if (WHITESPACE.test(altUnwrapped.safeCharAt(anchorIndex + anchorDelta))) {
                wrappedAdjusted = -1;
            } else if (altUnwrapped.safeCharAt(anchorIndex + anchorDelta) == SequenceUtils.LS) {
                // have line sep at anchor, if prev is not whitespace, use it for validation
                if (!WHITESPACE.test(altUnwrapped.safeCharAt(anchorIndex + anchorDelta - 1))) {
                    wrappedAdjusted--;
                } else {
                    // use next char, it should not be whitespace
                    anchorDelta++;
                    assert !WHITESPACE.test(altUnwrapped.safeCharAt(anchorIndex + anchorDelta)) :
                            String.format("Character(%s) after LS should not be whitespace.",
                                    SequenceUtils.toVisibleWhitespaceString(Character.toString(altUnwrapped.safeCharAt(anchorIndex + anchorDelta)))
                            );
                    isLineSep = true;
                }
            }

            char altUnwrappedCharAt = altUnwrapped.safeCharAt(anchorIndex + anchorDelta + wrappedAdjusted);
            char wrappedCharAt = wrapped.safeCharAt(wrappedIndex + wrappedAdjusted);

            assert altUnwrappedCharAt == wrappedCharAt
                    || WHITESPACE.test(altUnwrappedCharAt) && WHITESPACE.test(wrappedCharAt)
                    : String.format("altUnwrapped.charAt: '%s' != wrapped.charAt: '%s' for width=%d, unwrapped: '%s', wrapped: '%s'"
                    , SequenceUtils.toVisibleWhitespaceString(Character.toString(altUnwrappedCharAt))
                    , SequenceUtils.toVisibleWhitespaceString(Character.toString(wrappedCharAt))
                    , width
                    , SequenceUtils.toVisibleWhitespaceString(altUnwrapped)
                    , SequenceUtils.toVisibleWhitespaceString(altWrapped)
            );

            // Adjust index position and restore spaces if needed
            if (isLineSep) {
                wrappedIndex = Math.max(0, wrappedIndex - 1);
                if (inTest) {
                    System.out.println(String.format("LSep Adj wrapped anchor: `%s`", wrapped.safeSubSequence(wrappedIndex - 10, wrappedIndex).toVisibleWhitespaceString() + "|" + wrapped.safeSubSequence(wrappedIndex, wrappedIndex + 10).toVisibleWhitespaceString()));
                }
            }

            if (wrapped.isCharAt(wrappedIndex - 1, CharPredicate.ANY_EOL) && countedSpacesAfter > 0) {
                // at start of line with spaces to be inserted after, move to before prev EOL
                wrappedIndex -= wrapped.eolEndLength(wrappedIndex);
            }

            int wrappedSpacesBefore = wrapped.countTrailingSpaceTab(wrappedIndex);
            int wrappedSpacesAfter = wrapped.countLeadingSpaceTab(wrappedIndex);

            if (trackedOffset.isAfterSpaceEdit()) {
                if (trackedOffset.isAfterInsert()) {
                    // need at least one space before
                    countedSpacesBefore = Math.max(1, countedSpacesBefore);
                } else if (trackedOffset.isAfterDelete()) {
                    countedSpacesBefore = 0;
                }
            }

            int addSpacesBefore = trackedOffset.isSpliced() ? 0 : Math.max(0, countedSpacesBefore - wrappedSpacesBefore);
            int addSpacesAfter = Math.max(0, countedSpacesAfter - wrappedSpacesAfter);

            if (wrapped.isCharAt(wrappedIndex, ANY_EOL_NUL)) {
                // at end of line add only before, nothing after
                addSpacesAfter = 0;
                if (trackedOffset.isAfterDelete()) addSpacesBefore = Math.min(1, addSpacesBefore);
            } else if (!wrapped.isCharAt(wrappedIndex - 1, ANY_EOL_NUL)) {
                // not at start of line
                // spaces before caret, see if need to add max 1, and all spaces after
                addSpacesBefore = Math.min(1, addSpacesBefore);
            } else if (trackedOffset.isAfterDelete() && !trackedOffset.isAfterSpaceEdit()) {
                // at start of line, add max 1 space after
                // spaces before caret, see if need to add max 1
                addSpacesBefore = 0;
                addSpacesAfter = Math.min(1, addSpacesAfter);
            } else {
                // at start of line, not after delete or after space edit
                if (!trackedOffset.isAfterInsert() && !trackedOffset.isAfterDelete()) addSpacesAfter = 0;
                addSpacesBefore = 0;
            }

            if (addSpacesBefore + addSpacesAfter > 0) {
                int lastNonBlank = wrapped.lastIndexOfAnyNot(WHITESPACE);
                if (wrappedIndex < lastNonBlank) {
                    // insert in middle
                    wrapped = wrapped.insert(wrappedIndex, RepeatedSequence.ofSpaces(addSpacesBefore + addSpacesAfter));

                    // need to adjust all following indices by the amount inserted
                    for (int j = i + 1; j < iMax; j++) {
                        TrackedOffset trackedOffset1 = trackedOffsets.get(j);
                        int indexJ = trackedOffset1.getIndex();
                        trackedOffset1.setIndex(indexJ + addSpacesBefore + addSpacesAfter);
                    }
                } else {
                    restoredAppendSpaces = Math.max(restoredAppendSpaces, addSpacesBefore);
                }

                wrappedIndex += addSpacesBefore;
            }

            trackedOffset.setIndex(wrappedIndex);

            if (inTest) {
                System.out.println(String.format("Adj wrapped anchor: `%s`", wrapped.safeSubSequence(wrappedIndex - 20, wrappedIndex).toVisibleWhitespaceString() + "|" + wrapped.safeSubSequence(wrappedIndex, wrappedIndex + 20).toVisibleWhitespaceString()));
                System.out.println();
            }
        }

        // append any trailing spaces
        if (restoredAppendSpaces > 0) {
            wrapped = wrapped.appendSpaces(restoredAppendSpaces);
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
        final public @NotNull TextType type;
        final public @NotNull Range range;
        final public boolean isFirstWord;

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
        BasedSequence lastSpace = null;
        @NotNull List<? extends SpecialLeadInHandler> leadInHandlers = MarkdownParagraph.this.leadInHandlers;
        boolean unEscapeSpecialLeadInChars = MarkdownParagraph.this.unEscapeSpecialLeadInChars;
        boolean escapeSpecialLeadInChars = MarkdownParagraph.this.escapeSpecialLeadInChars;

        LeftAlignedWrapping(@NotNull BasedSequence baseSeq) {
            this.baseSeq = baseSeq;
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
                        if (col != 0) lastSpace = baseSeq.subSequence(token.range);
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
        final private int maxIndex;
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
            ArrayList<Token> tokens = new ArrayList<>();
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

