package com.vladsch.flexmark.util.html;

import com.vladsch.flexmark.util.Utils;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.BasedSequenceImpl;
import com.vladsch.flexmark.util.sequence.Range;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
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
    private StringBuilder myAppendable;
    final private ArrayList<Range> myOffsets;          // line contents
    final private ArrayList<BasedSequence> myPrefixes;  // line prefixes

    // indent level to use after the next \n and before text is appended
    private BasedSequence myPrefix;
    private BasedSequence myIndentPrefix;
    final private Stack<BasedSequence> myPrefixStack;
    final private Stack<Boolean> myIndentPrefixStack;

    // current line being accumulated
    private int myLineStart;            // start of line
    private boolean myAllWhitespace;    // all chars were whitespace
    private boolean myWasWhitespace;    // last char was whitespace

    public LineFormattingAppendableImpl(final int formatOptions) {
        myOptions = formatOptions;
        myPassThrough = haveOptions(PASS_THROUGH);
        myPreFormattedNesting = 0;
        myPreFormattedFirstLine = -1;
        myPreFormattedLastLine = -1;
        myLineStart = 0;
        myAllWhitespace = true;
        myWasWhitespace = false;
        myAppendable = new StringBuilder();
        myOffsets = new ArrayList<>();
        myPrefixes = new ArrayList<>();
        myPrefixStack = new Stack<>();
        myIndentPrefixStack = new Stack<>();
        myPrefix = BasedSequence.NULL;
        myIndentPrefix = BasedSequence.NULL;
    }

    @Override
    public int getOptions() {
        return myOptions;
    }

    @Override
    public LineFormattingAppendable setOptions(final int options) {
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
    public LineFormattingAppendable setIndentPrefix(final CharSequence prefix) {
        myIndentPrefix = prefix == null ? BasedSequence.NULL : BasedSequenceImpl.of(prefix);
        return this;
    }

    @Override
    public CharSequence getPrefix() {
        return myPrefix;
    }

    @Override
    public LineFormattingAppendable addPrefix(final CharSequence prefix) {
        if (!myPassThrough) {
            myPrefix = combinedPrefix(myPrefix, prefix);
        }
        return this;
    }

    @Override
    public LineFormattingAppendable setPrefix(final CharSequence prefix) {
        if (!myPassThrough) {
            myPrefix = prefix == null ? BasedSequence.NULL : BasedSequenceImpl.of(prefix);
        }
        return this;
    }

    @Override
    public LineFormattingAppendable indent() {
        if (!myPassThrough) {
            line();
            myPrefixStack.push(myPrefix);
            myPrefix = combinedPrefix(myPrefix, myIndentPrefix);
            myIndentPrefixStack.push(true);
        }
        return this;
    }

    @Override
    public LineFormattingAppendable unIndent() {
        if (!myPassThrough) {
            line();
            if (myPrefixStack.isEmpty()) throw new IllegalStateException("unIndent with an empty stack");
            if (!myIndentPrefixStack.peek()) throw new IllegalStateException("unIndent for element added by pushPrefix(), use popPrefix() instead");

            myPrefix = myPrefixStack.pop();
            myIndentPrefixStack.pop();
        }
        return this;
    }

    @Override
    public LineFormattingAppendable pushPrefix() {
        if (!myPassThrough) {
            myPrefixStack.push(myPrefix);
            myIndentPrefixStack.push(false);
        }
        return this;
    }

    @Override
    public LineFormattingAppendable popPrefix() {
        if (!myPassThrough) {
            if (myPrefixStack.isEmpty()) throw new IllegalStateException("popPrefix with an empty stack");
            if (myIndentPrefixStack.peek()) throw new IllegalStateException("popPrefix for element added by indent(), use unIndent() instead");

            myPrefix = myPrefixStack.pop();
            myIndentPrefixStack.pop();
        }
        return this;
    }

    private boolean isTrailingBlankLine() {
        int i = myOffsets.size();
        if (i-- > 0) {
            Range range = myOffsets.get(i);
            return range.isEmpty() || range.subSequence(myAppendable).isBlank();
        }
        return false;
    }

    private int lastNonBlankLine() {
        int i = myOffsets.size();
        while (i-- > 0) {
            Range range = myOffsets.get(i);
            if (range.isEmpty()) continue;
            if (!range.subSequence(myAppendable).isBlank()) break;
        }
        return i + 1;
    }

    private int trailingBlankLines() {
        return myOffsets.size() - lastNonBlankLine();
    }

    private boolean isLastEol() {
        return myAppendable.length() > 0 && myAppendable.charAt(myAppendable.length() - 1) == EOL;
    }

    private void appendEol() {
        myAppendable.append(EOL);
        int startOffset = myLineStart;
        myLineStart = myAppendable.length();
        int endOffset = myLineStart;
        myOffsets.add(new Range(startOffset, endOffset - 1));
        myPrefixes.add(myPrefix);
        myAllWhitespace = true;
        myWasWhitespace = false;
    }

    private void appendEol(int count) {
        while (count-- > 0) {
            appendEol();
        }
    }

    private void appendImpl(final char c) {
        if (myPassThrough) {
            if (c == EOL) {
                appendEol();
                int startOffset = myLineStart;
                myLineStart = myAppendable.length();
                int endOffset = myLineStart;
                myOffsets.add(new Range(startOffset, endOffset - 1));
                myPrefixes.add(myPrefix);
                myAllWhitespace = true;
                myWasWhitespace = false;
            } else {
                if (c != '\t' && c != ' ') myAllWhitespace = false;
                myAppendable.append(c);
            }
        } else {
            if (c == EOL) {
                int currentLine = getLineCount();

                if (myAllWhitespace && myPreFormattedNesting == 0 && myPreFormattedFirstLine != currentLine && myPreFormattedLastLine != currentLine) {
                    // add the EOL so as not to mess up the text but do not add the line
                    myAppendable.append(c);
                    myLineStart = myAppendable.length();
                    myAllWhitespace = true;
                    myWasWhitespace = false;
                } else {
                    myAppendable.append(c);
                    int startOffset = myLineStart;
                    myLineStart = myAppendable.length();
                    int endOffset = myLineStart;

                    if (myAllWhitespace) {
                        startOffset = endOffset - 1;
                    } else {
                        // apply options other than convert tabs which is done at time of appending
                        if (myPreFormattedNesting == 0 || myPreFormattedFirstLine == currentLine) {
                            if (!haveOptions(ALLOW_LEADING_WHITESPACE)) {
                                while (startOffset < endOffset && myAppendable.charAt(startOffset) == ' ') {
                                    startOffset++;
                                }
                            }

                            if (haveOptions(SUPPRESS_TRAILING_WHITESPACE)) {
                                while (startOffset < endOffset - 1 && myAppendable.charAt(endOffset - 2) == ' ') {
                                    endOffset--;
                                }
                            }
                        }
                    }

                    if (myPreFormattedFirstLine == currentLine && startOffset > myPreFormattedFirstLineOffset) {
                        startOffset = myPreFormattedFirstLineOffset;
                    }

                    if (myPreFormattedLastLine == currentLine && endOffset < myPreFormattedLastLineOffset) {
                        endOffset = myPreFormattedLastLineOffset + 1;
                    }

                    myOffsets.add(new Range(startOffset, endOffset - 1));

                    if (myPreFormattedNesting == 0 && myPreFormattedLastLine != currentLine || myPreFormattedFirstLine == currentLine) {
                        myPrefixes.add(myPrefix);
                    } else {
                        myPrefixes.add(BasedSequence.NULL);
                    }

                    myAllWhitespace = true;
                    myWasWhitespace = false;
                }
            } else {
                if (c == '\t') {
                    if (myPreFormattedNesting == 0 && haveOptions(COLLAPSE_WHITESPACE)) {
                        if (!myWasWhitespace) {
                            myAppendable.append(' ');
                            myWasWhitespace = true;
                        }
                    } else {
                        if (haveOptions(CONVERT_TABS)) {
                            int column = myAppendable.length() - myLineStart;
                            int spaces = 4 - (column % 4);
                            myAppendable.append("    ", 0, spaces);
                        } else {
                            myAppendable.append(c);
                        }
                    }
                } else {
                    if (c == ' ') {
                        if (myPreFormattedNesting == 0 && haveOptions(COLLAPSE_WHITESPACE)) {
                            if (!myWasWhitespace) {
                                myAppendable.append(' ');
                            }
                        } else {
                            myAppendable.append(' ');
                        }
                        myWasWhitespace = true;
                    } else {
                        myAllWhitespace = false;
                        myWasWhitespace = false;
                        myAppendable.append(c);
                    }
                }
            }
        }
    }

    private void appendImpl(final CharSequence csq, final int start, final int end) {
        int i = start;
        while (i < end) {
            appendImpl(csq.charAt(i++));
        }
    }

    @Override
    public LineFormattingAppendable append(final CharSequence csq) {
        appendImpl(csq, 0, csq.length());
        return this;
    }

    @Override
    public LineFormattingAppendable append(final CharSequence csq, final int start, final int end) {
        appendImpl(csq, start, end);
        return this;
    }

    @Override
    public LineFormattingAppendable append(final char c) {
        appendImpl(c);
        return this;
    }

    public LineFormattingAppendable repeat(char c, int count) {
        int i = count;
        while (i-- > 0) append(c);
        return this;
    }

    public LineFormattingAppendable repeat(CharSequence csq, int count) {
        int i = count;
        while (i-- > 0) append(csq);
        return this;
    }

    public LineFormattingAppendable repeat(CharSequence csq, int start, int end, int count) {
        int i = count;
        while (i-- > 0) append(csq, start, end);
        return this;
    }

    @Override
    public LineFormattingAppendable append(final LineFormattingAppendable lines, final CharSequence prefix) {
        return append(lines.getLines(), prefix);
    }

    private BasedSequence combinedPrefix(CharSequence prefix, CharSequence suffix) {
        if (prefix != null && prefix.length() > 0 && suffix != null && suffix.length() > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(prefix).append(suffix);
            return BasedSequenceImpl.of(sb.toString());
        } else if (prefix != null && prefix.length() > 0) {
            return BasedSequenceImpl.of(prefix);
        } else if (suffix != null && suffix.length() > 0) {
            return BasedSequenceImpl.of(suffix);
        } else {
            return BasedSequence.NULL;
        }
    }

    @Override
    public LineFormattingAppendable append(final Collection<CharSequence> lines, final CharSequence prefix) {
        int startLine = getLineCount();

        for (CharSequence line : lines) {
            myAppendable.append(line);
            line();
        }

        int endLine = getLineCount();

        if (prefix != null && prefix.length() > 0 && startLine < endLine) {
            prefixLines(prefix, true, startLine, endLine);
        }

        return this;
    }

    @Override
    public LineFormattingAppendable prefixLines(final CharSequence prefix, boolean addAfterLinePrefix, final int startLine, final int endLine) {
        final int useStartLine = minLimit(startLine, 0);
        final int useEndLine = maxLimit(endLine, getLineCount());

        if (prefix != null && prefix.length() > 0 && useStartLine < useEndLine) {
            // now need to add prefix to line contents
            CharSequence lastLinePrefix = BasedSequence.NULL;
            BasedSequence lastPrefix = addAfterLinePrefix ? combinedPrefix(lastLinePrefix, prefix) : combinedPrefix(prefix, lastLinePrefix);

            for (int i = useStartLine; i < useEndLine; i++) {
                BasedSequence linePrefix = myPrefixes.get(i);
                if (!linePrefix.equals(lastLinePrefix)) {
                    lastLinePrefix = linePrefix;
                    lastPrefix = addAfterLinePrefix ? combinedPrefix(lastLinePrefix, prefix) : combinedPrefix(prefix, lastLinePrefix);
                }
                myPrefixes.set(i, lastPrefix);
            }
        }
        return this;
    }

    @Override
    public String toString(int maxBlankLines) {
        if (myPassThrough) {
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
    public List<CharSequence> getLineContents(final int startOffset, final int endOffset) {
        line();
        ArrayList<CharSequence> result = new ArrayList<>();
        int iMax = Utils.maxLimit(endOffset, myOffsets.size());
        CharSequence chars = myAppendable;

        for (int i = startOffset; i < iMax; i++) {
            Range range = myOffsets.get(i);
            result.add(chars.subSequence(range.getStart(), range.getEnd()));
        }
        return result;
    }

    @Override
    public List<CharSequence> getLinePrefixes(final int startOffset, final int endOffset) {
        line();
        ArrayList<CharSequence> result = new ArrayList<>();
        int iMax = Utils.maxLimit(endOffset, myOffsets.size());

        for (int i = startOffset; i < iMax; i++) {
            result.add(myPrefixes.get(i));
        }
        return result;
    }

    @Override
    public List<CharSequence> getLines(final int startOffset, final int endOffset) {
        line();
        StringBuilder sb = new StringBuilder();

        ArrayList<CharSequence> result = new ArrayList<>();

        int iMax = Utils.maxLimit(endOffset, myOffsets.size());
        CharSequence chars = sb;

        for (int i = startOffset; i < iMax; i++) {
            Range range = myOffsets.get(i);
            BasedSequence linePrefix = myPrefixes.get(i);

            int lineStart = sb.length();
            if (!linePrefix.isEmpty()) sb.append(linePrefix);
            sb.append(chars.subSequence(range.getStart(), range.getEnd()));

            result.add(sb.subSequence(lineStart, sb.length()));
        }
        return result;
    }

    @Override
    public LineFormattingAppendable appendTo(final Appendable out, final int maxBlankLines, CharSequence prefix, int startLine, int endLine) throws IOException {
        line();
        int removeBlankLines = minLimit(trailingBlankLines() - maxBlankLines, 0);

        int iMax = min(endLine, myOffsets.size() - removeBlankLines);
        CharSequence chars = myAppendable;
        int lastLineEOL = 0;

        for (int i = startLine; i < iMax; i++) {
            Range range = myOffsets.get(i);
            BasedSequence linePrefix = myPrefixes.get(i);
            if (!linePrefix.isEmpty()) out.append(linePrefix);
            if (prefix != null && prefix.length() > 0) out.append(prefix);
            out.append(chars, range.getStart(), range.getEnd()).append(EOL);
            lastLineEOL = range.getEnd();
        }
        return this;
    }

    @Override
    public LineFormattingAppendable line() {
        if (myLineStart < myAppendable.length()) appendImpl(EOL);
        return this;
    }

    @Override
    public LineFormattingAppendable addLine() {
        if (myLineStart < myAppendable.length()) appendImpl(EOL);
        else appendEol(EOL);
        return this;
    }

    @Override
    public LineFormattingAppendable lineIf(final boolean predicate) {
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
    public LineFormattingAppendable blankLineIf(final boolean predicate) {
        if (predicate) blankLine();
        return this;
    }

    @Override
    public LineFormattingAppendable blankLine(final int count) {
        line();
        int addBlankLines = count - trailingBlankLines();
        appendEol(addBlankLines);
        return this;
    }

    @Override
    public int getLineCount() {
        return myOffsets.size();
    }

    @Override
    public int column() {
        return myAppendable.length() - myLineStart;
    }

    @Override
    public boolean isPreFormatted() {
        return myPreFormattedNesting > 0;
    }

    @Override
    public LineFormattingAppendable openPreFormatted(boolean addPrefixToFirstLine) {
        if (myPreFormattedNesting == 0) {
            myPreFormattedFirstLine = myOffsets.size();
            myPreFormattedFirstLineOffset = myAppendable.length();
            myPreFormattedLastLine = -1;
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
            myPreFormattedLastLine = myOffsets.size();
            myPreFormattedLastLineOffset = myAppendable.length();
        }
        return this;
    }

    @Override
    public String toString() {
        return myAppendable.toString();
    }
}
