package com.vladsch.flexmark.util.format;

import com.vladsch.flexmark.util.mappers.SpecialLeadInHandler;
import com.vladsch.flexmark.util.sequence.*;
import com.vladsch.flexmark.util.sequence.builder.SequenceBuilder;
import com.vladsch.flexmark.util.sequence.builder.tree.BasedOffsetTracker;
import com.vladsch.flexmark.util.sequence.builder.tree.OffsetInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class MarkdownParagraph {
    final private static char MARKDOWN_START_LINE_CHAR = SequenceUtils.LS;             // https://www.fileformat.info/info/unicode/char/2028/index.htm LINE_SEPARATOR this one is not preserved but will cause a line break if not already at beginning of line
    public static final List<SpecialLeadInHandler> EMPTY_LEAD_IN_HANDLERS = Collections.emptyList();
    public static final Map<TrackedOffset, Integer> EMPTY_OFFSET_MAP = Collections.emptyMap();

    final @NotNull BasedSequence baseSeq;
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

    @NotNull List<? extends SpecialLeadInHandler> leadInHandlers = EMPTY_LEAD_IN_HANDLERS;
    private Map<TrackedOffset, Integer> trackedOffsets = EMPTY_OFFSET_MAP;

    public MarkdownParagraph(CharSequence chars) {
        this(BasedSequence.of(chars), CharWidthProvider.NULL);
    }

    public MarkdownParagraph(BasedSequence chars) {
        this(chars, CharWidthProvider.NULL);
    }

    public MarkdownParagraph(@NotNull BasedSequence chars, @NotNull CharWidthProvider charWidthProvider) {
        baseSeq = chars;
        this.charWidthProvider = charWidthProvider;
    }

    public BasedSequence wrapTextNotTracked() {
        if (getFirstWidth() <= 0) return baseSeq;

        LeftAlignedWrapping wrapping = new LeftAlignedWrapping(baseSeq);
        return wrapping.wrapText();
    }

    @NotNull
    public Range getContinuationStartSplice(int offset, boolean afterSpace, boolean afterDelete) {
        BasedSequence baseSequence = baseSeq.getBaseSequence();
        assert offset >= 0 && offset <= baseSequence.length();
        if (afterSpace && afterDelete) {
            BasedOffsetTracker preFormatTracker = BasedOffsetTracker.create(baseSeq);
            int startOfLine = baseSequence.startOfLine(offset);
            if (startOfLine > baseSeq.getStartOffset() && baseSequence.safeCharAt(offset) != ' ') {
                int previousNonBlank = baseSequence.lastIndexOfAnyNot(CharPredicate.SPACE_TAB_EOL, offset - 1);
                if (previousNonBlank < startOfLine) {
                    // delete range between last non-blank and offset index
                    @NotNull OffsetInfo offsetInfo = preFormatTracker.getOffsetInfo(offset, true);
                    int offsetIndex = offsetInfo.endIndex;
                    int previousNonBlankIndex = baseSeq.lastIndexOfAnyNot(CharPredicate.SPACE_TAB_EOL, offsetIndex - 1);
                    return Range.of(previousNonBlankIndex + 1, offsetIndex);
                }
            }
        }
        return Range.NULL;
    }

    public BasedSequence wrapText() {
        if (getFirstWidth() <= 0) return baseSeq;
        if (trackedOffsets.isEmpty()) return wrapTextNotTracked();

        TrackedOffset[] offsets = new TrackedOffset[trackedOffsets.size()];
        int iMax = 0;
        for (TrackedOffset trackedOffset : trackedOffsets.keySet()) {
            offsets[iMax++] = trackedOffset;
        }

        Arrays.sort(offsets);

        // Adjust input text for wrapping by removing any continuation splice regions
        BasedSequence input = baseSeq;
        Range lastRange = Range.NULL;

        for (int i = iMax; i-- > 0; ) {
            TrackedOffset trackedOffset = offsets[i];
            if (lastRange.isEmpty() || !lastRange.contains(trackedOffset.getOffset())) {
                lastRange = getContinuationStartSplice(trackedOffset.getOffset(), trackedOffset.isAfterSpaceEdit(), trackedOffset.isAfterDelete());
                if (lastRange.isNotEmpty()) {
                    input = input.delete(lastRange.getStart(), lastRange.getEnd());
                }
            }
        }

        LeftAlignedWrapping wrapping = new LeftAlignedWrapping(input);
        BasedSequence wrapped = wrapping.wrapText();

        // FIX: apply after wrapping fixes
        if (restoreTrackedSpaces) {
            // NOTE: Restore trailing spaces at end of line if it has tracked offset on it
//                int endOffset = baseSeq.getEndOffset();
            int restoredAppendSpaces = 0;

            BasedOffsetTracker tracker = BasedOffsetTracker.create(wrapped);
            BasedOffsetTracker baseSeqTracker = BasedOffsetTracker.create(baseSeq);
            int baseSeqLastNonBlank = baseSeq.lastIndexOfAnyNot(CharPredicate.WHITESPACE) + 1;

            for (int i = iMax; i-- > 0; ) {
                TrackedOffset trackedOffset = offsets[i];
                OffsetInfo baseInfo = baseSeqTracker.getOffsetInfo(trackedOffset.getOffset(), true);
                int offset = baseInfo.endIndex;
                boolean noBeforeSpaces = false;
                int offsetSpaces = baseSeq.countTrailing(CharPredicate.SPACE, offset);
                int offsetSpacesAfter = baseSeq.countLeading(CharPredicate.SPACE, offset);
                boolean needSpace = baseSeq.countTrailing(CharPredicate.SPACE_TAB_EOL, offset) > 0;

                int endLine = baseSeq.endOfLine(offset);
                int startLine = baseSeq.startOfLine(offset);
                int firstNonBlank = baseSeq.indexOfAnyNot(CharPredicate.SPACE, startLine, endLine);

                OffsetInfo info = tracker.getOffsetInfo(trackedOffset.getOffset(), true);
                int index = info.endIndex;

                if (!trackedOffset.isAfterSpaceEdit() && (trackedOffset.isAfterInsert() || trackedOffset.isAfterDelete())) {
                    // need to keep it at the previous character but not when inserting as space
                    index = info.startIndex;
                    if (trackedOffset.isAfterDelete()) offsetSpacesAfter = 0;
                    offsetSpaces = 0;
                }

                int startLineWrapped = wrapped.startOfLine(index);
                int endLineWrapped = wrapped.endOfLine(index);
                int firstNonBlankWrapped = wrapped.indexOfAnyNot(CharPredicate.SPACE, startLineWrapped, endLineWrapped);

                // NOTE: if typing space before or on start of continuation line, then do not move tracked offset to previous line
                if (index <= firstNonBlankWrapped) {
                    int unwrappedOffset = wrapped.getIndexOffset(firstNonBlankWrapped);

                    if (unwrappedOffset >= 0) {
                        OffsetInfo offsetInfo = baseSeqTracker.getOffsetInfo(unwrappedOffset, true);
                        int baseSeqIndex = offsetInfo.endIndex - 1;  // adjust by -1 because the first nonblank in wrapped is the character after LS with LS having been removed.
                        boolean isLineSep = baseSeq.safeCharAt(baseSeqIndex) == SequenceUtils.LS;

                        if (!isLineSep && offset <= firstNonBlank && trackedOffset.isAfterSpaceEdit() && !trackedOffset.isAfterDelete()) {
                            offsetSpaces = 0;
                            offsetSpacesAfter = 0;
                        } else if (isLineSep || trackedOffset.isAfterDelete() || (trackedOffset.isAfterSpaceEdit() && offsetSpacesAfter > 0)) {
                            // tracked offset is followed by Line Separator, move the offset to end of previous line
                            index = wrapped.endOfLine(wrapped.startOfLine(index) - 1);

                            offset = baseSeqIndex;
                            endLine = baseSeq.startOfLine(offset);
                            startLine = baseSeq.startOfLine(offset);
                            firstNonBlank = baseSeq.indexOfAnyNot(CharPredicate.SPACE, startLine, endLine);
                            offsetSpaces = needSpace || isLineSep ? 1 : Math.min(1, offsetSpaces);
                            offsetSpacesAfter = 0;
                        }
                    }
                }

                int lastNonBlank = baseSeq.lastIndexOfAnyNot(CharPredicate.SPACE, endLine);
                int wrappedOffsetSpaces = wrapped.countTrailing(CharPredicate.SPACE, index);

                if (offset >= lastNonBlank) {
                    // add only what is missing
                    offsetSpaces = Math.max(0, offsetSpaces - wrappedOffsetSpaces);
                    offsetSpacesAfter = 0;
                } else if (offset > firstNonBlank) {
                    // spaces before caret, see if need to add max 1
                    int spacesBefore = wrapped.countTrailing(CharPredicate.SPACE, index);
                    offsetSpaces = Math.max(0, Math.min(1, offsetSpaces - spacesBefore));
                    int spacesAfter = wrapped.countLeading(CharPredicate.SPACE, index);
                    offsetSpacesAfter = Math.max(0, Math.min(1, offsetSpacesAfter - spacesAfter));
                } else {
                    offsetSpaces = 0;
                    offsetSpacesAfter = 0;
                }

                if (offset < baseSeqLastNonBlank) {
                    // insert in middle
                    if (offsetSpaces + offsetSpacesAfter > 0) {
                        wrapped = wrapped.insert(index, RepeatedSequence.ofSpaces(offsetSpaces + offsetSpacesAfter));
                        // need to adjust all following offsets by the amount inserted
                        for (int j = i + 1; j < iMax; j++) {
                            TrackedOffset trackedOffset1 = offsets[j];
                            int indexJ = trackedOffsets.get(trackedOffset1);
                            trackedOffsets.put(trackedOffset1, indexJ + offsetSpaces + offsetSpacesAfter);
                        }
                    }
                } else {
                    restoredAppendSpaces = Math.max(restoredAppendSpaces, offsetSpaces);
                }

                trackedOffsets.put(trackedOffset, index + offsetSpaces);
            }

            // append any trailing spaces
            if (restoredAppendSpaces > 0) {
                wrapped = wrapped.appendSpaces(restoredAppendSpaces);
            }
        } else {
            BasedOffsetTracker tracker = BasedOffsetTracker.create(wrapped);
            // Now we map the tracked offsets to indexes in the resulting text
            for (int i = iMax; i-- > 0; ) {
                TrackedOffset trackedOffset = offsets[i];
                OffsetInfo info = tracker.getOffsetInfo(trackedOffset.getOffset(), true);
                trackedOffsets.put(trackedOffset, info.endIndex);
            }
        }

        return wrapped;
    }

    public boolean addTrackedOffset(int offset) {
        return addTrackedOffset(offset, false, false, false);
    }

    @SuppressWarnings("UnusedReturnValue")
    public boolean addTrackedOffset(int offset, @Nullable Character c, boolean afterDelete) {
        return addTrackedOffset(offset, c != null && c == ' ', c != null && !afterDelete, afterDelete);
    }

    public boolean addTrackedOffset(int offset, boolean afterSpace, boolean afterInsert, boolean afterDelete) {
        if (trackedOffsets == EMPTY_OFFSET_MAP) trackedOffsets = new LinkedHashMap<>();
        assert offset >= 0 && offset <= baseSeq.getBaseSequence().length();
        trackedOffsets.put(new TrackedOffset(offset, afterSpace, afterInsert, afterDelete), null);
        return true;
    }

    public Map<TrackedOffset, Integer> getTrackedOffsets() {
        return trackedOffsets;
    }

    public int getTrackedOffsetIndex(int offset) {
        Integer index = trackedOffsets.get(new TrackedOffset(offset, false, false, false));
        return index == null ? offset : index;
    }

    public List<? extends SpecialLeadInHandler> getLeadInHandlers() {
        return leadInHandlers;
    }

    public void setLeadInHandlers(List<? extends SpecialLeadInHandler> leadInHandlers) {
        this.leadInHandlers = leadInHandlers;
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
        final SequenceBuilder result;
        final TextTokenizer tokenizer;
        int col = 0;
        int lineCount = 0;
        final int spaceWidth = charWidthProvider.spaceWidth();
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

        LeftAlignedWrapping(@NotNull BasedSequence baseSeq) {
            this.baseSeq = baseSeq;
            result = SequenceBuilder.emptyBuilder(baseSeq);
            tokenizer = new TextTokenizer(baseSeq);
        }

        void advance() {
            tokenizer.next();
        }

        void addToken(Token token) {
            addChars(baseSeq.subSequence(token.range));
        }

        void addChars(CharSequence charSequence) {
            result.append(charSequence);
            col += charWidthProvider.getStringWidth(charSequence);
        }

        void addSpaces(int count) {
            result.append(' ', count);
            col += charWidthProvider.spaceWidth() * count;
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
                    if (handler.escape(sequence, this::addChars)) return;
                }
            }
            addChars(sequence);
        }

        void processLeadInUnEscape(List<? extends SpecialLeadInHandler> handlers, BasedSequence sequence) {
            if (sequence.isNotEmpty() && unEscapeSpecialLeadInChars) {
                for (SpecialLeadInHandler handler : handlers) {
                    if (handler.unEscape(sequence, this::addChars)) return;
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

