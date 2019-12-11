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

    final @NotNull BasedSequence myChars;
    final @NotNull CharWidthProvider myCharWidthProvider;

    private int myFirstIndent = 0;
    private int myIndent = 0;
    private int myFirstWidthOffset = 0;
    int myWidth = 0;
    private @NotNull TextAlignment myAlignment = LEFT;
    boolean myKeepHardBreaks = true;
    boolean myKeepLineBreaks = false;

    public MarkdownParagraph(CharSequence chars) {
        this(BasedSequence.of(chars), CharWidthProvider.NULL);
    }

    public MarkdownParagraph(BasedSequence chars) {
        this(chars, CharWidthProvider.NULL);
    }

    public MarkdownParagraph(@NotNull BasedSequence chars, @NotNull CharWidthProvider charWidthProvider) {
        myChars = chars;
        myCharWidthProvider = charWidthProvider;
    }

    @NotNull
    public BasedSequence getChars() {
        return myChars;
    }

    public int getFirstIndent() {
        return myFirstIndent;
    }

    public void setFirstIndent(int firstIndent) {
        myFirstIndent = Math.max(0, firstIndent);
    }

    public int getIndent() {
        return myIndent;
    }

    public void setIndent(int indent) {
        myIndent = Math.max(0, indent);
    }

    public int getFirstWidth() {
        return (myWidth == 0) ? 0 : Math.max(0, myWidth + myFirstWidthOffset);
    }

    public int getFirstWidthOffset() {
        return myFirstWidthOffset;
    }

    public void setFirstWidthOffset(int firstWidthOffset) {
        myFirstWidthOffset = firstWidthOffset;
    }

    public int getWidth() {
        return myWidth;
    }

    public void setWidth(int width) {
        myWidth = Math.max(0, width);
    }

    @NotNull
    public TextAlignment getAlignment() {
        return myAlignment;
    }

    public void setAlignment(@NotNull TextAlignment alignment) {
        myAlignment = alignment;
    }

    public boolean getKeepHardBreaks() {
        return myKeepHardBreaks;
    }

    public void setKeepHardBreaks(boolean keepHardBreaks) {
        myKeepHardBreaks = keepHardBreaks;
    }

    public boolean getKeepLineBreaks() {
        return myKeepLineBreaks;
    }

    public void setKeepLineBreaks(boolean keepLineBreaks) {
        myKeepLineBreaks = keepLineBreaks;
    }

    @NotNull
    public CharWidthProvider getCharWidthProvider() {
        return myCharWidthProvider;
    }

    void leftAlign(int width) {
        myAlignment = LEFT;
        setWidth(width);
    }

    void rightAlign(int width) {
        myAlignment = TextAlignment.RIGHT;
        setWidth(width);
    }

    void centerAlign(int width) {
        myAlignment = TextAlignment.CENTER;
        setWidth(width);
    }

    void justifyAlign(int width) {
        myAlignment = TextAlignment.JUSTIFIED;
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
        if (getFirstWidth() <= 0) return myChars;

        if (myAlignment == LEFT) {
            return computeLeftAlignedSequence();
        }

        String lineBreak = RichSequence.EOL;
        String hardBreak = "  \n";
        final int[] pos = { 0 };
        final int[] lineCount = { 0 };
        ArrayList<Token> lineWords = new ArrayList<>();
        SequenceBuilder result = SequenceBuilder.emptyBuilder(myChars);
        int spaceWidth = myCharWidthProvider.spaceWidth();
        final int[] lineIndent = { spaceWidth * getFirstIndent() };
        int nextIndent = spaceWidth * getIndent();
        final int[] lineWidth = { spaceWidth * getFirstWidth() };
        int nextWidth = (getWidth() <= 0) ? Integer.MAX_VALUE : spaceWidth * getWidth();
        final int[] wordsOnLine = { 0 };
        BasedSequence chars = myChars;
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
                    if (pos[0] == 0 || lineIndent[0] + pos[0] + myCharWidthProvider.getStringWidth(token.subSequence(chars)) + spaceWidth <= lineWidth[0]) {
                        // fits, add it;
                        if (pos[0] > 0) pos[0] += spaceWidth;
                        lineWords.add(token);
                        pos[0] += myCharWidthProvider.getStringWidth(token.subSequence(chars));
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
                        if (myKeepHardBreaks) {
                            doLineBreak.run(token, hardBreak, true);
                        } else if (myKeepLineBreaks) {
                            lineWords.add(token);
                            doLineBreak.run(token, lineBreak, true);
                        }
                    }
                    tokenizer.next();
                }
                break;

                case BREAK: {
                    if (pos[0] > 0 && myKeepLineBreaks) {
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
        int indent = (lineCount > 0) ? myIndent : myFirstIndent;

        switch (myAlignment) {
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
        final TextTokenizer myTextTokenizer;
        final int myIndex;
        final int myLastPos;
        final boolean myInWord;
        final int myLastConsecutiveSpaces;
        final Token myToken;

        public State(TextTokenizer textTokenizer, int index, int lastPos, boolean inWord, int lastConsecutiveSpaces, Token token) {
            myTextTokenizer = textTokenizer;
            myIndex = index;
            myLastPos = lastPos;
            myInWord = inWord;
            myLastConsecutiveSpaces = lastConsecutiveSpaces;
            myToken = token;
        }
    }

    public BasedSequence computeLeftAlignedSequence() {
        if (getFirstWidth() <= 0) return myChars;

        LeftAlignedWrapping wrapping = new LeftAlignedWrapping();
        return wrapping.doCompute();
    }

    class LeftAlignedWrapping {
        final String lineBreak = SequenceUtils.EOL;
        int col = 0;
        int lineCount = 0;
        final SequenceBuilder result = SequenceBuilder.emptyBuilder(myChars);
        final int spaceWidth = myCharWidthProvider.spaceWidth();
        int lineIndent = spaceWidth * getFirstIndent();
        final int nextIndent = spaceWidth * getIndent();
        int lineWidth = spaceWidth * getFirstWidth();
        final int nextWidth = myWidth <= 0 ? Integer.MAX_VALUE : spaceWidth * myWidth;
        int wordsOnLine = 0;
        BasedSequence leadingIndent = null;
        BasedSequence lastSpace = null;
        final BasedSequence chars = myChars;
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
            col += myCharWidthProvider.getStringWidth(charSequence);
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
                        if (col == 0 || col + myCharWidthProvider.getStringWidth(token.subSequence(chars)) + spaceWidth <= lineWidth) {
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
                        if (myKeepHardBreaks) {
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
                        if (col > 0 && myKeepLineBreaks) {
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
        final private CharSequence myChars;
        private final int myMaxIndex;
        private int myIndex = 0;
        private int myLastPos = 0;
        private boolean myInWord = false;
        private int myLastConsecutiveSpaces = 0;
        private @Nullable Token myToken = null;

        TextTokenizer(@NotNull CharSequence chars) {
            myChars = chars;
            myMaxIndex = myChars.length();
            reset();
        }

        @NotNull
        public State getState() {
            return new State(this, myIndex, myLastPos, myInWord, myLastConsecutiveSpaces, myToken);
        }

        public void setState(@NotNull State value) {
            assert (this == value.myTextTokenizer);

            myIndex = value.myIndex;
            myLastPos = value.myLastPos;
            myInWord = value.myInWord;
            myLastConsecutiveSpaces = value.myLastConsecutiveSpaces;
            myToken = value.myToken;
        }

        public void reset() {
            myIndex = 0;
            myLastPos = 0;
            myInWord = false;
            myToken = null;
            myLastConsecutiveSpaces = 0;
            next();
        }

        @Nullable
        Token getToken() {
            return myToken;
        }

        @NotNull
        public List<Token> asList() {
            ArrayList<Token> tokens = new ArrayList<Token>();
            reset();

            while (myToken != null) {
                tokens.add(myToken);
                next();
            }

            return tokens;
        }

        void next() {
            myToken = null;
            while (myIndex < myMaxIndex) {
                char c = myChars.charAt(myIndex);
                if (myInWord) {
                    if (c == ' ' || c == '\t' || c == '\n' || c == MARKDOWN_START_LINE_CHAR) {
                        myInWord = false;
                        if (myLastPos < myIndex) {
                            // have a word
                            myToken = Token.of(TextType.WORD, myLastPos, myIndex);
                            myLastPos = myIndex;
                            break;
                        }
                    } else {
                        myIndex++;
                    }
                } else {
                    // in white space
                    if (c != ' ' && c != '\t' && c != '\n' && c != MARKDOWN_START_LINE_CHAR) {
                        if (myLastPos < myIndex) {
                            myToken = Token.of(TextType.SPACE, myLastPos, myIndex);
                            myLastPos = myIndex;
                            myInWord = true;
                            myLastConsecutiveSpaces = 0;
                            break;
                        } else {
                            myInWord = true;
                            myLastConsecutiveSpaces = 0;
                        }
                    } else {
                        if (c == '\n') {
                            if (myLastConsecutiveSpaces >= 2) {
                                myToken = Token.of(TextType.MARKDOWN_BREAK, myIndex - myLastConsecutiveSpaces, myIndex + 1);
                                myLastPos = myIndex + 1;
                                myLastConsecutiveSpaces = 0;
                                myIndex++;
                                break;
                            } else {
                                myToken = Token.of(TextType.BREAK, myIndex, myIndex + 1);
                                myLastPos = myIndex + 1;
                                myLastConsecutiveSpaces = 0;
                                myIndex++;
                                break;
                            }
                        } else if (c == MARKDOWN_START_LINE_CHAR) {
                            myToken = Token.of(TextType.MARKDOWN_START_LINE, myIndex, myIndex + 1);
                            myLastPos = myIndex + 1;
                            myLastConsecutiveSpaces = 0;
                            myIndex++;
                            break;
                        } else {
                            if (c == ' ') myLastConsecutiveSpaces++;
                            else myLastConsecutiveSpaces = 0;
                            myIndex++;
                        }
                    }
                }
            }

            if (myLastPos < myIndex) {
                myToken = Token.of((myInWord) ? TextType.WORD : TextType.SPACE, myLastPos, myIndex);
                myLastPos = myIndex;
            }
        }
    }
}

