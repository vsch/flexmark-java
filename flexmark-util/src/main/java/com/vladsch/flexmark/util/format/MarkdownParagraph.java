package com.vladsch.flexmark.util.format;

import com.vladsch.flexmark.util.Pair;
import com.vladsch.flexmark.util.sequence.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MarkdownParagraph {
    final private static char MARKDOWN_START_LINE_CHAR = BasedSequence.LSEP;             // https://www.fileformat.info/info/unicode/char/2028/index.htm LINE_SEPARATOR this one is not preserved but will cause a line break if not already at beginning of line
    final private static BasedSequence MARKDOWN_START_LINE = BasedSequence.LINE_SEP;   // this one is not preserved but will cause a line break if not already at beginning of line

    final private @NotNull BasedSequence myReplacedChars;
    final private @NotNull CharWidthProvider myCharWidthProvider;

    private int myFirstIndent = 0;
    private int myIndent = 0;
    private int myFirstWidthOffset = 0;
    private int myWidth = 0;
    private @NotNull TextAlignment myAlignment = TextAlignment.LEFT;
    private boolean myKeepHardBreaks = true;
    private boolean myKeepLineBreaks = false;
    private @NotNull TrackerDirection myTrackerDirection = TrackerDirection.NONE;
    private int myMarkerOffset = -1;

    public MarkdownParagraph(CharSequence replacedChars) {
        this(BasedSequence.of(replacedChars), CharWidthProvider.NULL);
    }

    public MarkdownParagraph(BasedSequence replacedChars) {
        this(replacedChars, CharWidthProvider.NULL);
    }

    public MarkdownParagraph(@NotNull BasedSequence replacedChars, @NotNull CharWidthProvider charWidthProvider) {
        myReplacedChars = replacedChars;
        myCharWidthProvider = charWidthProvider;
    }

    @NotNull
    public TrackerDirection getTrackerDirection() {
        return myTrackerDirection;
    }

    public void setTrackerDirection(@NotNull TrackerDirection trackerDirection) {
        myTrackerDirection = trackerDirection;
    }

    public int getMarkerOffset() {
        return myMarkerOffset;
    }

    public void setMarkerOffset(int markerOffset) {
        myMarkerOffset = Math.min(-1, markerOffset);
    }

    @NotNull
    public BasedSequence getReplacedChars() {
        return myReplacedChars;
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
        myAlignment = TextAlignment.LEFT;
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

    protected List<Token> tokenizeSequence(CharSequence chars) {
        int pos = 0;
        final int maxPos = chars.length();
        int lastPos = 0;
        boolean inWord = false;
        final ArrayList<Token> tokenList = new ArrayList<Token>();
        int lastConsecutiveSpaces = 0;
        while (pos < maxPos) {
            char c = chars.charAt(pos);
            if (inWord) {
                if (c == ' ' || c == '\t' || c == '\n' || c == MARKDOWN_START_LINE_CHAR) {
                    inWord = false;
                    if (lastPos < pos) {
                        // have a word;
                        tokenList.add(Token.of(TextType.WORD, lastPos, pos));
                        lastPos = pos;
                    }
                } else {
                    pos++;
                }
            } else {
                // in white space;
                if (c != ' ' && c != '\t' && c != '\n' && c != MARKDOWN_START_LINE_CHAR) {
                    if (lastPos < pos) {
                        tokenList.add(Token.of(TextType.SPACE, lastPos, pos));
                        lastPos = pos;
                    }
                    inWord = true;
                    lastConsecutiveSpaces = 0;
                } else {
                    if (c == '\n') {
                        if (lastConsecutiveSpaces >= 2) {
                            tokenList.add(Token.of(TextType.MARKDOWN_BREAK, pos - lastConsecutiveSpaces, pos + 1));
                        } else {
                            tokenList.add(Token.of(TextType.BREAK, pos, pos + 1));
                        }
                        lastPos = pos + 1;
                    } else if (c == MARKDOWN_START_LINE_CHAR) {
                        tokenList.add(Token.of(TextType.MARKDOWN_START_LINE, pos, pos + 1));
                        lastPos = pos + 1;
                    }
                    if (c == ' ') lastConsecutiveSpaces++;
                    else lastConsecutiveSpaces = 0;
                    pos++;
                }
            }
        }
        if (lastPos < pos) {
            tokenList.add(Token.of((inWord) ? TextType.WORD : TextType.SPACE, lastPos, pos));
        }
        return tokenList;
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
        if (getFirstWidth() <= 0) return myReplacedChars;

        String lineBreak = RichSequence.EOL;
        String hardBreak = "  \n";
        final int[] pos = { 0 };
        final int[] lineCount = { 0 };
        ArrayList<Token> lineWords = new ArrayList<>();
        BasedSequenceBuilder result = new BasedSequenceBuilder(myReplacedChars, myReplacedChars.length());
        int spaceWidth = myCharWidthProvider.spaceWidth();
        final int[] lineIndent = { spaceWidth * getFirstIndent() };
        int nextIndent = spaceWidth * getIndent();
        final int[] lineWidth = { spaceWidth * getFirstWidth() };
        int nextWidth = (getWidth() <= 0) ? Integer.MAX_VALUE : spaceWidth * getWidth();
        final int[] wordsOnLine = { 0 };
        BasedSequence chars = myReplacedChars;//.cachedProxy;
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

    private void addLine(BasedSequenceBuilder result, BasedSequence charSequence, ArrayList<Token> lineWords, int wordsOnLine, int lineCount, int extraSpaces, boolean lastLine) {
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

        if (leadSpaces > 0) result.add(RepeatedSequence.repeatOf(' ', leadSpaces));

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
                    replaceSpaces(result, lastSpace.subSequence(charSequence), spcCount, myTrackerDirection, myMarkerOffset);
                    remSpaces -= spcSize;
                }
                result.add(word.subSequence(charSequence));
                lastSpace = null;
            } else {
                lastSpace = word;
            }
        }
    }

    @NotNull
    public Pair<Integer, Integer> distributeSpan(int total, boolean leftMajor) {
        int left;
        int right;

        if (leftMajor) {
            right = total >> 1;
            left = total - right;
        } else {
            left = total >> 1;
            right = total - left;
        }
        return Pair.of(left, right);
    }

    /**
     * Replace given sequence of spaces with another sequence of spaces of given length
     * while keeping marker (0 width characters) in resulting sequence so that they are not lost
     *
     * @param chars sequence of spaces
     * @param count number of desired spaces
     */
    public void replaceSpaces(@NotNull BasedSequenceBuilder result, @NotNull BasedSequence chars, int count, @NotNull TrackerDirection trackerDirection, int markerIndex) {
        assert count >= 0;

        if (chars.length() == 0) {
            result.add(RepeatedSequence.ofSpaces(count));
            return;
        }

        int spaces = 0;
        int iMax = chars.length();
        TrackerDirection direction = null;
        int markerPosition = -1;

        for (int i = 0; i < iMax; i++) {
            char c = chars.charAt(i);
            if (c == ' ') spaces++;
            else if (chars.getStartOffset() + i == markerIndex) {
                direction = trackerDirection;
                markerPosition = i;
            } else {
                throw new IllegalStateException("Not space or marker " + c);
            }
        }

        if (spaces == count) {
            result.add(chars);
        } else if (chars.length() == 0) {
            result.add(PrefixedSubSequence.prefixOf(RepeatedSequence.ofSpaces(count), chars));
        } else if (markerPosition == -1) {
            // no marker
            if (spaces < count) {
                // divide existing spaces in two parts and add padding in the middle
                int leftSpaces = spaces >> 1;
                result.add(chars.subSequence(0, leftSpaces));
                result.add(RepeatedSequence.ofSpaces(count - spaces));
                result.add(chars.subSequence(leftSpaces));
            } else {
                // divide kept spaces in two parts, keeping first and last spaces, leaving out the middle
                int leftSpaces = count >> 1;
                int rightSpaces = count - leftSpaces;
                result.add(chars.subSequence(0, leftSpaces));
                result.add(chars.endSequence(rightSpaces));
            }
        } else {
            // more convoluted:
            if (spaces < count) {
                int leftSpaces;
                switch (direction) {
                    case LEFT:
                        //   if leaning left then keep marker on left pad between it and right part
                        leftSpaces = markerIndex + 1;
                        break;
                    case RIGHT:
                        //   if leaning right then keep marker on right and pad between it and left part
                        leftSpaces = markerIndex;
                        break;

                    default:
                    case NONE:
                        //   if not leaning then divide around marker keeping marker in middle of padding
                        leftSpaces = markerIndex + 1;
                        break;
                }

                result.add(chars.subSequence(0, leftSpaces));
                result.add(RepeatedSequence.ofSpaces(count - spaces));
                result.add(chars.subSequence(leftSpaces));
            } else {
                int leftStart;
                int leftEnd;
                int rightStart;
                int rightEnd;
                int usedSpaces;
                switch (direction) {
                    case LEFT:
                        //   if leaning left then keep marker on left, keeping as many leading spaces as possible before the marker
                        leftEnd = markerIndex + 1;
                        leftStart = Math.max(0, markerIndex - count);
                        usedSpaces = leftEnd - leftStart - 1;
                        rightEnd = chars.length();
                        rightStart = Math.max(leftEnd, rightEnd - (count - usedSpaces));
                        break;

                    case RIGHT:
                        //   if leaning right then keep marker on right and keep as many right spaces as possible after the marker
                        rightStart = markerIndex;
                        rightEnd = Math.min(chars.length(), markerIndex + 1 + count);
                        usedSpaces = rightEnd - rightStart - 1;
                        leftStart = 0;
                        leftEnd = Math.max(rightStart, leftStart + Math.max(0, count - usedSpaces));
                        break;

                    default:
                    case NONE:
                        //   if not leaning then divide around marker keeping marker in middle of kept spaces
                        leftEnd = markerIndex + 1;
                        leftStart = Math.max(0, markerIndex - count);
                        usedSpaces = leftEnd - leftStart - 1;
                        rightEnd = chars.length();
                        rightStart = Math.max(leftEnd, rightEnd - (count - usedSpaces));
                        break;
                }

                result.add(chars.subSequence(leftStart, leftEnd));
                result.add(chars.subSequence(rightStart, rightEnd));
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
            return "token: $type $range";
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

    public static class TextTokenizer {
        final private CharSequence myChars;
        private int myMaxIndex;
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

//    protected fun computeLeftAlignedSequence(): SmartCharSequence {
//        if (firstWidth <= 0) return myReplacedChars//.cachedProxy
//
//        val lineBreak = SmartRepeatedCharSequence("\n")
//        var col = 0
//        var lineCount = 0
//        val result = ArrayList<SmartCharSequence>()
//        val spaceWidth = myCharWidthProvider.spaceWidth
//        var lineIndent = spaceWidth * firstIndent
//        val nextIndent = spaceWidth * indent
//        var lineWidth = spaceWidth * firstWidth
//        val nextWidth = if (myWidth.get() <= 0) Integer.MAX_VALUE else spaceWidth * myWidth.get()
//        var wordsOnLine = 0
//        var leadingIndent: Token? = null
//        var lastRange: Range? = null
//        var lastSpace: Token? = null
//
//        val chars = myReplacedChars//.cachedProxy
//        val tokenizer = TextTokenizer(chars)
//
//        fun advance() {
//            tokenizer.next()
//        }
//
//        fun commitLastRange() {
//            val range = lastRange
//            if (range != null) result.add(chars.subSequence(range.start, range.end))
//            lastRange = null
//        }
//
//        fun addToken(token: Token) {
//            val range = lastRange
//            if (range != null && range.isAdjacentBefore(token.range)) {
//                // combine them
//                lastRange = range.withEnd(token.range.end)
//            } else {
//                if (range != null) result.add(chars.subSequence(range.start, range.end))
//                lastRange = token.range
//            }
//            col += myCharWidthProvider.getStringWidth(token.subSequence(chars))
//        }
//
//        fun addChars(charSequence: SmartCharSequence) {
//            commitLastRange()
//            result.add(charSequence)
//            col += myCharWidthProvider.getStringWidth(charSequence)
//        }
//
//        fun addTokenSubRange(token: Token, count: Int) {
//            if (token.range.span == count) addToken(token)
//            else addChars(SmartRepeatedCharSequence(token.subSequence(chars), 0, count))
//        }
//
//        fun addSpaces(token: Token?, count: Int) {
//            if (token != null) {
//                addTokenSubRange(token, count)
//            } else {
//                addChars(SmartRepeatedCharSequence(' ', count))
//            }
//        }
//
//        fun afterLineBreak() {
//            col = 0
//            wordsOnLine = 0
//            lineCount++
//            lineIndent = nextIndent
//            lineWidth = nextWidth
//            lastSpace = null
//            leadingIndent = null
//        }
//
//        while (true) {
//            val token = tokenizer.token ?: break
//
//            when (token.type) {
//                TextType.SPACE -> {
//                    if (col == 0) leadingIndent = token
//                    else lastSpace = token
//                    advance()
//                }
//
//                TextType.WORD -> {
//                    if (col == 0 || col + myCharWidthProvider.getStringWidth(token.subSequence(chars)) + spaceWidth <= lineWidth) {
//                        // fits, add it
//                        if (col > 0) addSpaces(lastSpace, 1)
//                        else if (lineIndent > 0) addSpaces(leadingIndent, lineIndent)
//
//                        addToken(token)
//                        advance()
//                        wordsOnLine++
//                    } else {
//                        // need to insert a line break and repeat
//                        addChars(lineBreak)
//                        afterLineBreak()
//                    }
//                }
//
//                TextType.MARKDOWN_START_LINE -> {
//                    // start a new line if not already new
//                    if (col > 0) {
//                        addChars(lineBreak)
//                        afterLineBreak()
//                    }
//                    advance()
//                }
//
//                TextType.MARKDOWN_BREAK -> {
//                    // start a new line if not already new
//                    if (myKeepMarkdownHardBreaks.get()) {
//                        if (col > 0) {
//                            addToken(token)
//                            afterLineBreak()
//                        }
//                    } else {
//                        // treat as a space
//                        lastSpace = token
//                    }
//                    advance()
//                }
//
//                TextType.BREAK -> {
//                    if (col > 0 && myKeepLineBreaks.get()) {
//                        addToken(token)
//                        afterLineBreak()
//                    }
//                    advance()
//                }
//            }
//        }
//
//        commitLastRange()
//
//        return SmartCharSequenceBase.smart(result)//.cachedProxy
//    }
//
