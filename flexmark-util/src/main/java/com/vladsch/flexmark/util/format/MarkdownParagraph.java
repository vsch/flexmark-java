package com.vladsch.flexmark.util.format;

import com.vladsch.flexmark.util.sequence.*;
import com.vladsch.flexmark.util.sequence.builder.SequenceBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static com.vladsch.flexmark.util.format.TextAlignment.LEFT;

public class MarkdownParagraph {
    final private static char MARKDOWN_START_LINE_CHAR = SequenceUtils.LS;             // https://www.fileformat.info/info/unicode/char/2028/index.htm LINE_SEPARATOR this one is not preserved but will cause a line break if not already at beginning of line

    final @NotNull BasedSequence baseSeq;
    final @NotNull CharWidthProvider charWidthProvider;

    private int firstIndent = 0;
    private int indent = 0;
    private int firstWidthOffset = 0;
    int width = 0;
    private @NotNull TextAlignment alignment = LEFT;
    boolean keepHardBreaks = true;
    boolean keepLineBreaks = false;

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

    @NotNull
    public TextAlignment getAlignment() {
        return alignment;
    }

    public void setAlignment(@NotNull TextAlignment alignment) {
        this.alignment = alignment;
    }

    public boolean getKeepHardBreaks() {
        return keepHardBreaks;
    }

    public void setKeepHardBreaks(boolean keepHardBreaks) {
        this.keepHardBreaks = keepHardBreaks;
    }

    public boolean getKeepLineBreaks() {
        return keepLineBreaks;
    }

    public void setKeepLineBreaks(boolean keepLineBreaks) {
        this.keepLineBreaks = keepLineBreaks;
    }

    @NotNull
    public CharWidthProvider getCharWidthProvider() {
        return charWidthProvider;
    }

    void leftAlign(int width) {
        alignment = LEFT;
        setWidth(width);
    }

    void rightAlign(int width) {
        alignment = TextAlignment.RIGHT;
        setWidth(width);
    }

    void centerAlign(int width) {
        alignment = TextAlignment.CENTER;
        setWidth(width);
    }

    void justifyAlign(int width) {
        alignment = TextAlignment.JUSTIFIED;
        setWidth(width);
    }

    public interface LineBreakProcessor {
        void run(@Nullable Token token, CharSequence breakChars, boolean isLastLine);
    }

    public enum TextType {
        WORD,
        SPACE,
        BREAK,
        MARKDOWN_BREAK,
        MARKDOWN_START_LINE
    }

    @NotNull
    public BasedSequence computeResultSequence() {
        if (getFirstWidth() <= 0) return baseSeq;

        if (alignment == LEFT) {
            return computeLeftAlignedSequence();
        }

        String lineBreak = RichSequence.EOL;
        String hardBreak = "  \n";
        final int[] pos = { 0 };
        final int[] lineCount = { 0 };
        ArrayList<Token> lineWords = new ArrayList<>();
        SequenceBuilder result = SequenceBuilder.emptyBuilder(baseSeq);
        int spaceWidth = charWidthProvider.spaceWidth();
        final int[] lineIndent = { spaceWidth * getFirstIndent() };
        int nextIndent = spaceWidth * getIndent();
        final int[] lineWidth = { spaceWidth * getFirstWidth() };
        int nextWidth = (getWidth() <= 0) ? Integer.MAX_VALUE : spaceWidth * getWidth();
        final int[] wordsOnLine = { 0 };
        BasedSequence chars = baseSeq;
        TextTokenizer tokenizer = new TextTokenizer(chars);

        LineBreakProcessor doLineBreak = (spaceToken, breakChars, lastLine) -> {
            addLine(result, chars, lineWords, wordsOnLine[0], lineCount[0], (lineWidth[0] - pos[0] - lineIndent[0]) / spaceWidth, lastLine);

            if (spaceToken != null) {
                result.add(spaceToken.subSequence(chars)).add(breakChars);
            } else {
                result.add(breakChars);
            }

            lineWords.clear();

            pos[0] = 0;
            wordsOnLine[0] = 0;
            lineCount[0]++;
            lineIndent[0] = nextIndent;
            lineWidth[0] = nextWidth;
        };

        while (true) {
            final @Nullable Token token = tokenizer.getToken();
            if (token == null) break;

            switch (token.type) {
                case SPACE: {
                    if (pos[0] > 0) lineWords.add(token);
                    tokenizer.next();
                }
                break;

                case WORD: {
                    if (pos[0] == 0 || lineIndent[0] + pos[0] + charWidthProvider.getStringWidth(token.subSequence(chars)) + spaceWidth <= lineWidth[0]) {
                        // fits, add it;
                        if (pos[0] > 0) pos[0] += spaceWidth;
                        lineWords.add(token);
                        pos[0] += charWidthProvider.getStringWidth(token.subSequence(chars));
                        wordsOnLine[0]++;
                        tokenizer.next();
                    } else {
                        // need to insert a line break and repeat;
                        final Token lineBreakToken = lineWords.get(lineWords.size() - 1);
                        if (lineBreakToken.type == TextType.WORD) {
                            doLineBreak.run(null, lineBreak, false);
                        } else {
                            doLineBreak.run(lineBreakToken, lineBreak, false);
                        }
                    }
                }
                break;

                case MARKDOWN_BREAK: {
                    if (pos[0] > 0) {
                        if (keepHardBreaks) {
                            doLineBreak.run(token, hardBreak, true);
                        } else if (keepLineBreaks) {
                            lineWords.add(token);
                            doLineBreak.run(token, lineBreak, true);
                        }
                    }
                    tokenizer.next();
                }
                break;

                case BREAK: {
                    if (pos[0] > 0 && keepLineBreaks) {
                        doLineBreak.run(token, lineBreak, true);
                    }
                    tokenizer.next();
                }
                break;

                case MARKDOWN_START_LINE: {
                    if (wordsOnLine[0] > 0) {
                        doLineBreak.run(null, lineBreak, false);
                    }
                    tokenizer.next();
                }
                break;
            }
        }

        if (wordsOnLine[0] > 0) {
            addLine(result, chars, lineWords, wordsOnLine[0], lineCount[0], (lineWidth[0] - pos[0] - lineIndent[0]) / spaceWidth, true);
        }

        return result.toSequence();
    }

    private void addLine(SequenceBuilder result, BasedSequence charSequence, ArrayList<Token> lineWords, int wordsOnLine, int lineCount, int extraSpaces, boolean lastLine) {
        int leadSpaces = 0;
        int addSpaces = 0;
        int remSpaces = 0;
        int distributeSpaces = Math.max(extraSpaces, 0);
        int indent = (lineCount > 0) ? this.indent : firstIndent;

        switch (alignment) {
            case LEFT:
                leadSpaces = indent;
                break;

            case RIGHT:
                leadSpaces = indent + distributeSpaces;
                break;

            case CENTER:
                leadSpaces = indent + distributeSpaces / 2;
                break;

            case JUSTIFIED:
                leadSpaces = indent;
                if (!lastLine && wordsOnLine > 1 && distributeSpaces > 0) {
                    addSpaces = distributeSpaces / (wordsOnLine - 1);
                    remSpaces = distributeSpaces - addSpaces * (wordsOnLine - 1);
                }
                break;
        }

        if (leadSpaces > 0) result.append(' ', leadSpaces);

        boolean firstWord = true;
        Token lastSpace = null;

        for (Token word : lineWords) {
            if (word.type == TextType.WORD) {
                if (firstWord) firstWord = false;
                else if (lastSpace == null) {
                    int spcSize = (remSpaces > 0) ? 1 : 0;
                    int spcCount = addSpaces + 1 + spcSize;
                    result.add(RepeatedSequence.repeatOf(' ', spcCount));
                    remSpaces -= spcSize;
                } else {
                    int spcSize = (remSpaces > 0) ? 1 : 0;
                    int spcCount = addSpaces + 1 + spcSize;
                    result.append(' ', spcCount);
                    remSpaces -= spcSize;
                }
                result.add(word.subSequence(charSequence));
                lastSpace = null;
            } else {
                lastSpace = word;
            }
        }
    }

    public static class Token {
        public final @NotNull TextType type;
        public final @NotNull Range range;

        private Token(@NotNull TextType type, @NotNull Range range) {
            this.type = type;
            this.range = range;
        }

        @Override
        public String toString() {
            return "token: " + type + " " + range;
        }

        public BasedSequence subSequence(BasedSequence charSequence) {
            return range.basedSubSequence(charSequence);
        }

        public CharSequence subSequence(CharSequence charSequence) {
            return range.charSubSequence(charSequence);
        }

        @NotNull
        public static Token of(@NotNull TextType type, @NotNull Range range) {
            return new Token(type, range);
        }

        @NotNull
        public static Token of(@NotNull TextType type, int start, int end) {
            return new Token(type, Range.of(start, end));
        }
    }

    static class State {
        final TextTokenizer textTokenizer;
        final int index;
        final int lastPos;
        final boolean isInWord;
        final int lastConsecutiveSpaces;
        final Token token;

        public State(TextTokenizer textTokenizer, int index, int lastPos, boolean inWord, int lastConsecutiveSpaces, Token token) {
            this.textTokenizer = textTokenizer;
            this.index = index;
            this.lastPos = lastPos;
            isInWord = inWord;
            this.lastConsecutiveSpaces = lastConsecutiveSpaces;
            this.token = token;
        }
    }

    public BasedSequence computeLeftAlignedSequence() {
        if (getFirstWidth() <= 0) return baseSeq;

        LeftAlignedWrapping wrapping = new LeftAlignedWrapping();
        return wrapping.doCompute();
    }

    class LeftAlignedWrapping {
        final String lineBreak = SequenceUtils.EOL;
        int col = 0;
        int lineCount = 0;
        final SequenceBuilder result = SequenceBuilder.emptyBuilder(baseSeq);
        final int spaceWidth = charWidthProvider.spaceWidth();
        int lineIndent = spaceWidth * getFirstIndent();
        final int nextIndent = spaceWidth * getIndent();
        int lineWidth = spaceWidth * getFirstWidth();
        final int nextWidth = width <= 0 ? Integer.MAX_VALUE : spaceWidth * width;
        int wordsOnLine = 0;
        BasedSequence leadingIndent = null;
        BasedSequence lastSpace = null;
        final BasedSequence chars = baseSeq;
        final TextTokenizer tokenizer = new TextTokenizer(chars);

        LeftAlignedWrapping() {

        }

        void advance() {
            tokenizer.next();
        }

        void addToken(Token token) {
            addChars(chars.subSequence(token.range));
        }

        void addChars(CharSequence charSequence) {
            result.add(charSequence);
            col += charWidthProvider.getStringWidth(charSequence);
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
                addChars(RepeatedSequence.ofSpaces(count));
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

        @NotNull
        BasedSequence doCompute() {
            while (true) {
                final Token token = tokenizer.getToken();
                if (token == null) break;
                switch (token.type) {
                    case SPACE: {
                        if (col == 0) leadingIndent = chars.subSequence(token.range);
                        else lastSpace = chars.subSequence(token.range);
                        advance();
                        break;
                    }

                    case WORD: {
                        if (col == 0 || col + charWidthProvider.getStringWidth(token.subSequence(chars)) + spaceWidth <= lineWidth) {
                            // fits, add it
                            if (col > 0) {
                                lastSpace = addSpaces(lastSpace, 1);
                            } else if (lineIndent > 0) {
                                addSpaces(leadingIndent, lineIndent);
                                leadingIndent = null;
                            }
                            addToken(token);
                            advance();
                            wordsOnLine++;
                        } else {
                            // need to insert a line break and repeat
                            addChars(lineBreak);
                            afterLineBreak();
                        }
                        break;
                    }

                    case MARKDOWN_START_LINE: {
                        // start a new line if not already new
                        if (col > 0) {
                            addChars(lineBreak);
                            afterLineBreak();
                        }

                        advance();
                        break;
                    }

                    case MARKDOWN_BREAK: {
                        // start a new line if not already new
                        if (keepHardBreaks) {
                            if (col > 0) {
                                addToken(token);
                                afterLineBreak();
                            }
                        } else {
                            // treat as a space
                            lastSpace = chars.subSequence(token.range);
                        }
                        advance();
                        break;
                    }

                    case BREAK: {
                        if (col > 0 && keepLineBreaks) {
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
        private int lastConsecutiveSpaces = 0;
        private @Nullable Token token = null;

        TextTokenizer(@NotNull CharSequence chars) {
            this.chars = chars;
            maxIndex = this.chars.length();
            reset();
        }

        @NotNull
        public State getState() {
            return new State(this, index, lastPos, isInWord, lastConsecutiveSpaces, token);
        }

        public void setState(@NotNull State value) {
            assert (this == value.textTokenizer);

            index = value.index;
            lastPos = value.lastPos;
            isInWord = value.isInWord;
            lastConsecutiveSpaces = value.lastConsecutiveSpaces;
            token = value.token;
        }

        public void reset() {
            index = 0;
            lastPos = 0;
            isInWord = false;
            token = null;
            lastConsecutiveSpaces = 0;
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
                        if (lastPos < index) {
                            // have a word
                            token = Token.of(TextType.WORD, lastPos, index);
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
                                lastPos = index + 1;
                                lastConsecutiveSpaces = 0;
                                index++;
                                break;
                            } else {
                                token = Token.of(TextType.BREAK, index, index + 1);
                                lastPos = index + 1;
                                lastConsecutiveSpaces = 0;
                                index++;
                                break;
                            }
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
                token = Token.of((isInWord) ? TextType.WORD : TextType.SPACE, lastPos, index);
                lastPos = index;
            }
        }
    }
}

