package com.vladsch.flexmark.util.html;

import com.vladsch.flexmark.util.Pair;
import com.vladsch.flexmark.util.Utils;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.Range;
import com.vladsch.flexmark.util.sequence.RepeatedSequence;
import com.vladsch.flexmark.util.sequence.SequenceUtils;
import com.vladsch.flexmark.util.sequence.builder.SequenceBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static com.vladsch.flexmark.util.Utils.*;

public class LineFormattingAppendableImpl implements LineFormattingAppendable {
    final private static char EOL = '\n';

    final private boolean myPassThrough;
    private int myOptions;

    // pre-formatted nesting level, while >0 all text is passed through as is and not monitored
    private int myPreFormattedNesting;
    private int myPreFormattedFirstLine;        // line which should be prefixed
    private int myPreFormattedFirstLineOffset;  // first line start of preformatted offset
    private int myPreFormattedLastLine;         // last line of preformatted text
    private int myPreFormattedLastLineOffset;   // first line end of preformatted offset

    // accumulated text and line information
    final private StringBuilder myAppendable;
    final private ArrayList<BasedSequence> myLines;     // line contents
    final private ArrayList<BasedSequence> myPrefixes;  // line prefixes
    private int myTextLength;                           // accumulated length of all offsets
    private int myPrefixLength;                         // accumulated length of all prefixes

    // indent level to use after the next \n and before text is appended
    private BasedSequence myPrefix;
    private BasedSequence myPrefixAfterEol;
    private BasedSequence myIndentPrefix;
    final private Stack<BasedSequence> myPrefixStack;
    final private Stack<Boolean> myIndentPrefixStack;
    final private SequenceBuilder myBuilder;

    // current line being accumulated
    private int myLineStart;            // start of line
    private boolean myAllWhitespace;    // all chars were whitespace
    private boolean myWasWhitespace;    // last char was whitespace
    private int myLineOnFirstText;      // issue EOL on first text
    final private ArrayList<Runnable> myIndentsOnFirstEol;    // issue indents on first eol

    public LineFormattingAppendableImpl(int formatOptions) {
        this(formatOptions, null);
    }

    public LineFormattingAppendableImpl(int formatOptions, @Nullable SequenceBuilder builder) {
        myBuilder = builder;
        myOptions = formatOptions;
        myPassThrough = haveOptions(PASS_THROUGH);
        myPreFormattedNesting = 0;
        myPreFormattedFirstLine = -1;
        myPreFormattedLastLine = -1;
        myLineStart = 0;
        myTextLength = 0;
        myPrefixLength = 0;
        myAllWhitespace = true;
        myWasWhitespace = false;
        myAppendable = new StringBuilder();
        myLines = new ArrayList<>();
        myPrefixes = new ArrayList<>();
        myPrefixStack = new Stack<>();
        myIndentPrefixStack = new Stack<>();
        myPrefix = BasedSequence.EMPTY;
        myPrefixAfterEol = BasedSequence.EMPTY;
        myIndentPrefix = BasedSequence.EMPTY;
        myLineOnFirstText = 0;
        myIndentsOnFirstEol = new ArrayList<>();
    }

    @Override
    public int getOptions() {
        return myOptions;
    }

    @Override
    public LineFormattingAppendable setOptions(int options) {
        myOptions = options;
        return this;
    }

    private boolean haveOptions(int options) {
        return (myOptions & options) != 0;
    }

    private boolean isConvertingTabs() {
        return haveOptions(CONVERT_TABS | COLLAPSE_WHITESPACE);
    }

    private boolean isSuppressingTrailingWhitespace() {
        return haveOptions(SUPPRESS_TRAILING_WHITESPACE);
    }

    private boolean isAllowLeadingWhitespace() {
        return haveOptions(ALLOW_LEADING_WHITESPACE);
    }

    private boolean isCollapseWhitespace() {
        return haveOptions(COLLAPSE_WHITESPACE);
    }

    @Override
    public CharSequence getIndentPrefix() {
        return myIndentPrefix;
    }

    @Override
    public LineFormattingAppendable setIndentPrefix(CharSequence prefix) {
        myIndentPrefix = prefix == null ? BasedSequence.NULL : BasedSequence.of(prefix).subSequence(0, prefix.length());
        return this;
    }

    @Override
    public CharSequence getPrefix() {
        return myPrefixAfterEol;
    }

    @Override
    public LineFormattingAppendable addPrefix(CharSequence prefix, boolean afterEol) {
        if (!myPassThrough) {
            if (afterEol) {
                myPrefixAfterEol = combinedPrefix(myPrefixAfterEol, prefix);
            } else {
                myPrefix = combinedPrefix(myPrefixAfterEol, prefix);
                myPrefixAfterEol = myPrefix;
            }
        }
        return this;
    }

    @Override
    public LineFormattingAppendable setPrefix(CharSequence prefix, boolean afterEol) {
        if (!myPassThrough) {
            if (afterEol) {
                myPrefixAfterEol = prefix == null ? BasedSequence.NULL : BasedSequence.of(prefix).subSequence(0, prefix.length());
            } else {
                myPrefix = prefix == null ? BasedSequence.NULL : BasedSequence.of(prefix).subSequence(0, prefix.length());
                myPrefixAfterEol = myPrefix;
            }
        }
        return this;
    }

    @Override
    public LineFormattingAppendable indent() {
        if (!myPassThrough) {
            line();
            rawIndent();
        }
        return this;
    }

    private void rawIndent() {
        myPrefixStack.push(myPrefixAfterEol);
        myPrefix = combinedPrefix(myPrefixAfterEol, myIndentPrefix);
        myPrefixAfterEol = myPrefix;
        myIndentPrefixStack.push(true);
    }

    private void rawUnIndent() {
        if (myPrefixStack.isEmpty()) throw new IllegalStateException("unIndent with an empty stack");
        if (!myIndentPrefixStack.peek()) throw new IllegalStateException("unIndent for element added by pushPrefix(), use popPrefix() instead");

        myPrefix = myPrefixStack.pop();
        myPrefixAfterEol = myPrefix;
        myIndentPrefixStack.pop();
    }

    @Override
    public LineFormattingAppendable unIndent() {
        if (!myPassThrough) {
            line();
            rawUnIndent();
        }
        return this;
    }

    @Override
    public LineFormattingAppendable unIndentNoEol() {
        if (!myPassThrough) {
            if (isLastEol()) {
                rawUnIndent();
            } else {
                BasedSequence prefix = myPrefix;
                rawUnIndent();
                myPrefixAfterEol = myPrefix;
                myPrefix = prefix;
            }
        }
        return this;
    }

    @Override
    public LineFormattingAppendable pushPrefix() {
        if (!myPassThrough) {
            myPrefixStack.push(myPrefixAfterEol);
            myIndentPrefixStack.push(false);
        }
        return this;
    }

    @Override
    public LineFormattingAppendable popPrefix(boolean afterEol) {
        if (!myPassThrough) {
            if (myPrefixStack.isEmpty()) throw new IllegalStateException("popPrefix with an empty stack");
            if (myIndentPrefixStack.peek()) throw new IllegalStateException("popPrefix for element added by indent(), use unIndent() instead");

            myPrefixAfterEol = myPrefixStack.pop();
            if (!afterEol) {
                myPrefix = myPrefixAfterEol;
            }
            myIndentPrefixStack.pop();
        }
        return this;
    }

    private boolean isTrailingBlankLine() {
        int i = myLines.size();
        if (i-- > 0) {
            BasedSequence line = myLines.get(i);
            return line.isBlank();
        }
        return myAppendable.length() == 0;
    }

    private int lastNonBlankLine() {
        int i = myLines.size();
        while (i-- > 0) {
            BasedSequence line = myLines.get(i);
            if (!line.isBlank()) break;
        }
        return i + 1;
    }

    private int trailingBlankLines() {
        return myLines.size() - lastNonBlankLine();
    }

    private boolean isLastEol() {
        return myAppendable.length() > 0 && myAppendable.charAt(myAppendable.length() - 1) == EOL;
    }

    private void addLineRange(Range range, BasedSequence prefix) {
        //if (range.getStart() > range.getEnd()) {
        //    int tmp = 0;
        //}
        assert range.getStart() <= range.getEnd();

        if (myBuilder != null) {
            myLines.add(range.isNull() ? BasedSequence.NULL : myBuilder.toSequence().subSequence(range.getStart(), range.getEnd()));
        } else {
            myLines.add(range.isNull() ? BasedSequence.NULL : range.basedSubSequence(myAppendable));
        }

        if (range.isEmpty() && !prefix.isEmpty()) {
            prefix = prefix.trimEnd();
            myPrefixes.add(prefix);
        } else {
            myPrefixes.add(prefix);
        }

        myTextLength += range.getSpan() + 1;
        myPrefixLength += prefix.length();
    }

    private void appendEol() {
        myAppendable.append(EOL);
        appendBuilder(SequenceUtils.EOL);

        int startOffset = myLineStart;
        myLineStart = myAppendable.length();
        int endOffset = myLineStart;
        addLineRange(Range.of(startOffset, endOffset - 1), myPrefix);
        myAllWhitespace = true;
        myWasWhitespace = false;
        myLineOnFirstText = 0;

        rawIndentsOnFirstEol();
    }

    private void rawIndentsOnFirstEol() {
        myPrefix = myPrefixAfterEol;

        while (!myIndentsOnFirstEol.isEmpty()) {
            Runnable runnable = myIndentsOnFirstEol.remove(myIndentsOnFirstEol.size() - 1);
            rawIndent();
            runnable.run();
        }
    }

    private void appendEol(int count) {
        while (count-- > 0) {
            appendEol();
        }
    }

    /**
     * Returns text range if EOL was appended
     * <p>
     * NOTE: if range == Range.NULL then no line would be added
     *
     * @return pair of line text range if EOL was added and prefix
     */
    private Pair<Range, BasedSequence> getRangePrefixAfterEol() {
        int startOffset = myLineStart;
        int endOffset = myAppendable.length() + 1;
        int currentLine = getLineCount();
        boolean needPrefix = haveOptions(PREFIX_PRE_FORMATTED) || (myPreFormattedNesting == 0 && myPreFormattedLastLine != currentLine || myPreFormattedFirstLine == currentLine);

        if (myPassThrough) {
            return new Pair<>(Range.of(startOffset, endOffset - 1), needPrefix ? myPrefix : BasedSequence.NULL);
        } else {
            if (myAllWhitespace && (myPreFormattedNesting == 0 && !(myPreFormattedFirstLine == currentLine || myPreFormattedLastLine == currentLine))) {
                if (haveOptions(ALLOW_LEADING_EOL) && myAppendable.length() == 0) {
                    return new Pair<>(Range.of(startOffset, endOffset - 1), needPrefix ? myPrefix : BasedSequence.NULL);
                } else {
                    return new Pair<>(Range.NULL, BasedSequence.NULL);
                }
            } else {
                // apply options other than convert tabs which is done at time of appending
                if (!haveOptions(ALLOW_LEADING_WHITESPACE) &&
                        (myPreFormattedNesting == 0 || myPreFormattedFirstLine == currentLine) &&
                        myPreFormattedNesting == 0 && myPreFormattedLastLine != currentLine
                ) {
                    if (myAllWhitespace) {
                        startOffset = endOffset - 1;
                    } else {
                        while (startOffset < endOffset && myAppendable.charAt(startOffset) == ' ') {
                            startOffset++;
                        }
                    }
                }

                if (haveOptions(SUPPRESS_TRAILING_WHITESPACE) && myPreFormattedNesting == 0) {
                    if (myAllWhitespace) {
                        startOffset = endOffset - 1;
                    } else {
                        while (startOffset < endOffset - 1 && myAppendable.charAt(endOffset - 2) == ' ') {
                            endOffset--;
                        }
                    }
                }

                if (myPreFormattedFirstLine == currentLine) {
                    if (startOffset > myPreFormattedFirstLineOffset) startOffset = myPreFormattedFirstLineOffset;
                }

                if (myPreFormattedLastLine == currentLine) {
                    if (endOffset < myPreFormattedLastLineOffset + 1) endOffset = myPreFormattedLastLineOffset + 1;
                }

                return new Pair<>(Range.of(startOffset, endOffset - 1), needPrefix ? myPrefix : BasedSequence.NULL);
            }
        }
    }

    /**
     * Returns text offset before EOL if EOL was issued
     *
     * @return would be offset after adding EOL - 1
     */
    private int offsetAfterEol(boolean includePrefixes) {
        Pair<Range, BasedSequence> rangePrefixAfterEol = getRangePrefixAfterEol();
        if (rangePrefixAfterEol.getFirst().isNull()) {
            return includePrefixes ? myTextLength + myPrefixLength : myTextLength;
        } else {
            Range range = rangePrefixAfterEol.getFirst();
            BasedSequence prefix = rangePrefixAfterEol.getSecond();

            if (range.isEmpty() && !prefix.isEmpty()) {
                prefix = prefix.trimEnd();
            }

            return includePrefixes ? myTextLength + rangePrefixAfterEol.getFirst().getSpan() + myPrefixLength + prefix.length()
                    : myTextLength + rangePrefixAfterEol.getFirst().getSpan();
        }
    }

    private void appendBuilder(CharSequence s) {
        appendBuilder(s, 0, s.length());
    }

    private void appendBuilder(CharSequence s, int start, int end) {
        if (myBuilder != null) {
            if (start == 0 && end == s.length()) {
                myBuilder.append(s);
            } else {
                myBuilder.append(s.subSequence(start, end));
            }
        }
    }

    private void appendBuilder(char c) {
        if (myBuilder != null) {
            myBuilder.append(c);
        }
    }

    private void appendBuilder(char c, int count) {
        if (myBuilder != null) {
            myBuilder.append(c, count);
        }
    }

    private void appendImpl(CharSequence s, int index) {
        char c = s.charAt(index);

        if (myPassThrough) {
            if (c == EOL) {
                appendEol();
            } else {
                if (myLineOnFirstText > 0) {
                    myLineOnFirstText = 0;
                    appendEol();
                }

                if (c != '\t' && c != ' ') myAllWhitespace = false;
                myAppendable.append(c);
                appendBuilder(s, index, index + 1);
            }
        } else {
            if (c == EOL) {
                Pair<Range, BasedSequence> rangePrefixAfterEol = getRangePrefixAfterEol();
                if (rangePrefixAfterEol.getFirst().isNull()) {
                    // nothing, add EOL and don't add line
                    // add the EOL so as not to mess up the text but do not add the line
                    myAppendable.append(c);
                    appendBuilder(s, index, index + 1);

                    myLineStart = myAppendable.length();
                    myAllWhitespace = true;
                    myWasWhitespace = false;
                    rawIndentsOnFirstEol();
                } else {
                    // add EOL and line
                    myAppendable.append(c);
                    appendBuilder(s, index, index + 1);

                    myLineStart = myAppendable.length();
                    addLineRange(rangePrefixAfterEol.getFirst(), rangePrefixAfterEol.getSecond());
                    myAllWhitespace = true;
                    myWasWhitespace = false;
                    rawIndentsOnFirstEol();
                }
            } else {
                if (myLineOnFirstText > 0) {
                    myLineOnFirstText = 0;
                    appendEol();
                }

                if (c == '\t') {
                    if (myPreFormattedNesting == 0 && haveOptions(COLLAPSE_WHITESPACE)) {
                        if (!myWasWhitespace) {
                            myAppendable.append(' ');
                            appendBuilder(' ');
                            myWasWhitespace = true;
                        }
                    } else {
                        if (haveOptions(CONVERT_TABS)) {
                            int column = myAppendable.length() - myLineStart;
                            int spaces = 4 - (column % 4);
                            myAppendable.append("    ", 0, spaces);
                            appendBuilder(' ', spaces);
                        } else {
                            myAppendable.append(c);
                            appendBuilder(s, index, index + 1);
                        }
                    }
                } else {
                    if (c == ' ') {
                        if (myPreFormattedNesting == 0 && haveOptions(COLLAPSE_WHITESPACE)) {
                            if (!myWasWhitespace) {
                                myAppendable.append(' ');
                                appendBuilder(' ');
                            }
                        } else {
                            myAppendable.append(' ');
                            appendBuilder(' ');
                        }
                        myWasWhitespace = true;
                    } else {
                        myAllWhitespace = false;
                        myWasWhitespace = false;
                        myAppendable.append(c);
                        appendBuilder(s, index, index + 1);
                    }
                }
            }
        }
    }

    private void appendImpl(CharSequence csq, int start, int end) {
        int i = start;
        while (i < end) {
            appendImpl(csq, i++);
        }
    }

    @Override
    public LineFormattingAppendable append(CharSequence csq) {
        appendImpl(csq, 0, csq.length());
        return this;
    }

    @Override
    public LineFormattingAppendable append(CharSequence csq, int start, int end) {
        appendImpl(csq, start, end);
        return this;
    }

    @Override
    public LineFormattingAppendable append(char c) {
        appendImpl(Character.toString(c), 0);
        return this;
    }

    public LineFormattingAppendable repeat(char c, int count) {
        append(RepeatedSequence.repeatOf(c, count));
//        int i = count;
//        String s = Character.toString(c);
//        while (i-- > 0) appendImpl(s, 0);
        return this;
    }

    public LineFormattingAppendable repeat(CharSequence csq, int count) {
        append(RepeatedSequence.repeatOf(csq, count));
//        int i = count;
//        while (i-- > 0) append(csq);
        return this;
    }

    public LineFormattingAppendable repeat(CharSequence csq, int start, int end, int count) {
        append(RepeatedSequence.repeatOf(csq.subSequence(start, end), count));
//        int i = count;
//        while (i-- > 0) append(csq, start, end);
        return this;
    }

    private BasedSequence combinedPrefix(CharSequence prefix, CharSequence suffix) {
        if (prefix != null && prefix.length() > 0 && suffix != null && suffix.length() > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(prefix).append(suffix);
            CharSequence charSequence = sb.toString();
            return BasedSequence.of(charSequence).subSequence(0, charSequence.length());
        } else if (prefix != null && prefix.length() > 0) {
            return BasedSequence.of(prefix).subSequence(0, prefix.length());
        } else if (suffix != null && suffix.length() > 0) {
            return BasedSequence.of(suffix).subSequence(0, suffix.length());
        } else {
            return BasedSequence.NULL;
        }
    }

    @Override
    public boolean isPreFormattedLine(int line) {
        return getLinePrefix(line).isNull();
    }

    @Override
    public LineFormattingAppendable append(LineFormattingAppendable lineAppendable, int startLine, int endLine) {
        List<CharSequence> lines = lineAppendable.getLineContents(startLine, endLine);
        List<BasedSequence> prefixes = lineAppendable.getLinePrefixes(startLine, endLine);

        int iMax = lines.size();
        for (int i = 0; i < iMax; i++) {
            CharSequence line = lines.get(i);
            BasedSequence prefix = prefixes.get(i);

            int startOffset = myAppendable.length();

            myAppendable.append(line);
            appendBuilder(line);

            myAppendable.append(EOL);
            appendBuilder(EOL);

            int endOffset = myAppendable.length();

            BasedSequence combinedPrefix = haveOptions(PREFIX_PRE_FORMATTED) || !lineAppendable.isPreFormattedLine(startLine + i) ? combinedPrefix(myPrefix, prefix) : BasedSequence.NULL;
            addLineRange(Range.of(startOffset, endOffset - 1), combinedPrefix);
            myLineStart = endOffset;
        }
        return this;
    }

    @Override
    public LineFormattingAppendable prefixLines(CharSequence prefix, boolean addAfterLinePrefix, int startLine, int endLine) {
        int useStartLine = minLimit(startLine, 0);
        int useEndLine = maxLimit(endLine, getLineCount());

        if (prefix != null && prefix.length() > 0 && useStartLine < useEndLine) {
            // now need to add prefix to line contents
            CharSequence lastLinePrefix = BasedSequence.NULL;
            BasedSequence lastPrefix = addAfterLinePrefix ? combinedPrefix(lastLinePrefix, prefix) : combinedPrefix(prefix, lastLinePrefix);

            for (int i = useStartLine; i < useEndLine; i++) {
                BasedSequence linePrefix = myPrefixes.get(i);
                if (haveOptions(PREFIX_PRE_FORMATTED) || !isPreFormattedLine(i)) {
                    // need prefix
                    if (!linePrefix.equals(lastLinePrefix)) {
                        lastLinePrefix = linePrefix;
                        lastPrefix = addAfterLinePrefix ? combinedPrefix(lastLinePrefix, prefix) : combinedPrefix(prefix, lastLinePrefix);
                    }
                    myPrefixes.set(i, lastPrefix);
                } else {
                    myPrefixes.set(i, BasedSequence.NULL);
                }
            }
        }
        return this;
    }

    @Override
    public LineFormattingAppendable removeLines(int startLine, int endLine) {
        int useStartLine = minLimit(startLine, 0);
        int useEndLine = maxLimit(endLine, getLineCount());

        if (useStartLine < useEndLine) {
            int count = useEndLine - useStartLine;
            while (count-- > 0) {
                BasedSequence line = myLines.remove(startLine);
                BasedSequence prefix = myPrefixes.remove(startLine);
                myTextLength -= line.length() + 1;
                myPrefixLength -= prefix.length();
            }
        }
        return this;
    }

    @Override
    public String toString(int maxBlankLines) {
        if (myPassThrough) {
            line();
            return myAppendable.toString();
        } else {
            StringBuilder out = new StringBuilder();
            try {
                appendTo(out, maxBlankLines);
            } catch (IOException ignored) {

            }
            return out.toString();
        }
    }

    @Override
    public List<CharSequence> getLineContents(int startOffset, int endOffset) {
        line();
        ArrayList<CharSequence> result = new ArrayList<>();
        int iMax = Utils.maxLimit(endOffset, myLines.size());

        for (int i = startOffset; i < iMax; i++) {
            BasedSequence line = myLines.get(i);
            result.add(line);
        }
        return result;
    }

    @Override
    public List<BasedSequence> getLinePrefixes(int startOffset, int endOffset) {
        line();
        ArrayList<BasedSequence> result = new ArrayList<>();
        int iMax = Utils.maxLimit(endOffset, myLines.size());

        for (int i = startOffset; i < iMax; i++) {
            result.add(myPrefixes.get(i));
        }
        return result;
    }

    @Override
    public List<CharSequence> getLines(int startOffset, int endOffset) {
        line();

        ArrayList<CharSequence> result = new ArrayList<>();
        int iMax = Utils.maxLimit(endOffset, myLines.size());

        if (myBuilder != null) {
            for (int i = startOffset; i < iMax; i++) {
                BasedSequence line = myLines.get(i);
                BasedSequence linePrefix = myPrefixes.get(i);

                if (!linePrefix.isEmpty()) {
                    BasedSequence basedSequence = myBuilder.subContext().append(linePrefix).append(line).toSequence();
                    result.add(basedSequence);
                } else {
                    result.add(line);
                }
            }
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = startOffset; i < iMax; i++) {
                BasedSequence line = myLines.get(i);
                BasedSequence linePrefix = myPrefixes.get(i);

                int lineStart = sb.length();
                if (!linePrefix.isEmpty()) sb.append(linePrefix);
                sb.append(line);

                result.add(sb.subSequence(lineStart, sb.length()));
            }
        }

        return result;
    }

    @Override
    public LineFormattingAppendable appendTo(Appendable out, int maxBlankLines, CharSequence prefix, int startLine, int endLine) throws IOException {
        line();
        int removeBlankLines = minLimit(trailingBlankLines() - minLimit(maxBlankLines, 0), 0);

        int iMax = min(endLine, myLines.size() - removeBlankLines);

        for (int i = startLine; i < iMax; i++) {
            BasedSequence line = myLines.get(i);
            BasedSequence linePrefix = myPrefixes.get(i);
            if (!linePrefix.isEmpty()) out.append(linePrefix);
            if (prefix != null && prefix.length() > 0) out.append(prefix);
            out.append(line);

            if (maxBlankLines != -1 || i + 1 != iMax) {
                out.append(EOL);
            }
        }
        return this;
    }

    @Override
    public LineFormattingAppendable line() {
        if (myPreFormattedNesting > 0 || myLineStart < myAppendable.length() || haveOptions(ALLOW_LEADING_EOL)) {
            //// see if really have something that will be kept
            //BasedSequence pendingText = BasedSequenceImpl.of(myAppendable.subSequence(myLineStart, myAppendable.length()));
            //
            //if (!haveOptions(ALLOW_LEADING_WHITESPACE)) {
            //   pendingText = pendingText.trimEnd();
            //}
            //
            //if (haveOptions(SUPPRESS_TRAILING_WHITESPACE)) {
            //   pendingText = pendingText.trimStart();
            //}
            //
            //if (!pendingText.isEmpty()) {
            //    appendImpl(EOL);
            //}
            appendImpl(SequenceUtils.EOL, 0);
        } else {
            rawIndentsOnFirstEol();
        }
        return this;
    }

    @Override
    public LineFormattingAppendable lineWithTrailingSpaces(int count) {
        if (myPreFormattedNesting > 0 || myLineStart < myAppendable.length() || haveOptions(ALLOW_LEADING_EOL)) {
            int options = myOptions;
            myOptions &= ~(SUPPRESS_TRAILING_WHITESPACE | COLLAPSE_WHITESPACE);
            if (count > 0) repeat(' ', count);
            appendImpl(SequenceUtils.EOL, 0);
            myOptions = options;
        }
        return this;
    }

    @Override
    public LineFormattingAppendable addLine() {
        appendImpl(SequenceUtils.EOL, 0);
        return this;
    }

    @Override
    public LineFormattingAppendable lineIf(boolean predicate) {
        if (predicate) line();
        return this;
    }

    @Override
    public LineFormattingAppendable blankLine() {
        line();
        if (!isTrailingBlankLine()) appendEol();
        return this;
    }

    @Override
    public LineFormattingAppendable blankLineIf(boolean predicate) {
        if (predicate) blankLine();
        return this;
    }

    @Override
    public LineFormattingAppendable blankLine(int count) {
        line();
        int addBlankLines = count - trailingBlankLines();
        appendEol(addBlankLines);
        return this;
    }

    @Override
    public int getLineCount() {
        return myLines.size();
    }

    @Override
    public int column() {
        return myAppendable.length() - myLineStart;
    }

    @Override
    public int offset() {
        return myTextLength + myPrefixLength;
    }

    @Override
    public int offsetWithPending() {
        return offsetAfterEol(true);
    }

    @Override
    public int textOnlyOffset() {
        return myTextLength;
    }

    @Override
    public int textOnlyOffsetWithPending() {
        return offsetAfterEol(false);
    }

    @Override
    public boolean isPendingSpace() {
        return myAppendable.length() > 0 && " \t".indexOf(myAppendable.charAt(myAppendable.length() - 1)) != -1;
    }

    @Override
    public int getPendingSpace() {
        int length = myAppendable.length();
        if (length == 0) return 0;
        int spaces = 0;
        while (length-- > 0 && " \t".indexOf(myAppendable.charAt(length)) != -1) {
            spaces++;
        }
        return spaces;
    }

    @Override
    public int getPendingEOL() {
        int withPending = textOnlyOffsetWithPending();

        if (withPending == myTextLength) {
            // use count of blank lines+1
            return trailingBlankLines() + 1;
        } else {
            return 0;
        }
    }

    @Override
    public boolean isPreFormatted() {
        return myPreFormattedNesting > 0;
    }

    @Override
    public LineFormattingAppendable openPreFormatted(boolean addPrefixToFirstLine) {
        if (myPreFormattedNesting == 0) {
            if (myPreFormattedFirstLine != myLines.size()) {
                myPreFormattedFirstLine = myLines.size();
                myPreFormattedFirstLineOffset = myAppendable.length();
            }
        }

        ++myPreFormattedNesting;
        return this;
    }

    @Override
    public LineFormattingAppendable closePreFormatted() {
        if (myPreFormattedNesting <= 0) throw new IllegalStateException("closePreFormatted called with nesting == 0");
        --myPreFormattedNesting;

        if (myPreFormattedNesting == 0 && !isLastEol()) {
            // this will be the last line of preformatted text
            myPreFormattedLastLine = myLines.size();
            myPreFormattedLastLineOffset = myAppendable.length();
        }
        return this;
    }

    @Override
    public String toString() {
        return myAppendable.toString();
    }

    @Override
    public void toBuilder(@NotNull SequenceBuilder builder, int maxBlankLines) {
        if (myBuilder == null) return;

        line();
        int removeBlankLines = minLimit(trailingBlankLines() - minLimit(maxBlankLines, 0), 0);

        int iMax = myLines.size() - removeBlankLines;
        for (int i = 0; i < iMax; i++) {
            BasedSequence prefix = myPrefixes.get(i);
            BasedSequence line = myLines.get(i);
            builder.append(prefix);
            builder.append(line);

            if (maxBlankLines != -1 || i + 1 != iMax) {
                builder.append(SequenceUtils.EOL);
            }
        }
    }

    @Override
    public LineFormattingAppendable lineOnFirstText(boolean value) {
        if (value) myLineOnFirstText++;
        else if (myLineOnFirstText > 0) myLineOnFirstText--;
        return this;
    }

    @Override
    public LineFormattingAppendable removeIndentOnFirstEOL(Runnable runnable) {
        myIndentsOnFirstEol.remove(runnable);
        return this;
    }

    @Override
    public LineFormattingAppendable addIndentOnFirstEOL(Runnable runnable) {
        myIndentsOnFirstEol.add(runnable);
        return this;
    }
}
