package com.vladsch.flexmark.util.format;

import com.vladsch.flexmark.util.mappers.SpecialLeadInHandler;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.CharPredicate;
import com.vladsch.flexmark.util.sequence.Range;
import com.vladsch.flexmark.util.sequence.SequenceUtils;
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

    private int firstIndent = 0;
    private int indent = 0;
    private int firstWidthOffset = 0;
    int width = 0;
    boolean keepHardLineBreaks = true;
    boolean keepSoftLineBreaks = false;
    boolean unEscapeSpecialLeadInChars = true;
    boolean escapeSpecialLeadInChars = true;

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

        return wrapped;
    }

    public boolean addTrackedOffset(int offset) {
        return addTrackedOffset(offset, null, false);
    }

    public boolean addTrackedOffset(int offset, @Nullable Character c, boolean afterDelete) {
        return addTrackedOffset(offset, c != null && c == ' ', afterDelete);
    }

    public boolean addTrackedOffset(int offset, boolean afterSpace, boolean afterDelete) {
        if (trackedOffsets == EMPTY_OFFSET_MAP) trackedOffsets = new LinkedHashMap<>();
        assert offset >= 0 && offset <= baseSeq.getBaseSequence().length();
        trackedOffsets.put(new TrackedOffset(offset, afterSpace, afterDelete), null);
        return true;
    }

    public Map<TrackedOffset, Integer> getTrackedOffsets() {
        return trackedOffsets;
    }

    public List<? extends SpecialLeadInHandler> getLeadInHandlers() {
        return leadInHandlers;
    }

    public void setLeadInHandlers(List<? extends SpecialLeadInHandler> leadInHandlers) {
        this.leadInHandlers = leadInHandlers;
    }

    @NotNull
    public BasedSequence getChars() {
        return baseSeq;
    }

    public int getFirstIndent() {
        return firstIndent;
    }

    public void setFirstIndent(int firstIndent) {
        this.firstIndent = Math.max(0, firstIndent);
    }

    public int getIndent() {
        return indent;
    }

    public void setIndent(int indent) {
        this.indent = Math.max(0, indent);
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
        int lineIndent = spaceWidth * getFirstIndent();
        final int nextIndent = spaceWidth * getIndent();
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
            if (escapeSpecialLeadInChars) {
                for (SpecialLeadInHandler handler : handlers) {
                    if (handler.escape(sequence, this::addChars)) return;
                }
            }
            addChars(sequence);
        }

        void processLeadInUnEscape(List<? extends SpecialLeadInHandler> handlers, BasedSequence sequence) {
            if (unEscapeSpecialLeadInChars) {
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
                            } else if (lineIndent > 0) {
                                addSpaces(leadingIndent, lineIndent);
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

