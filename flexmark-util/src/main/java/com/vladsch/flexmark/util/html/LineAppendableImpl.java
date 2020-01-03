package com.vladsch.flexmark.util.html;

import com.vladsch.flexmark.util.Pair;
import com.vladsch.flexmark.util.collection.BitFieldSet;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.Range;
import com.vladsch.flexmark.util.sequence.RepeatedSequence;
import com.vladsch.flexmark.util.sequence.SequenceUtils;
import com.vladsch.flexmark.util.sequence.builder.ISequenceBuilder;
import com.vladsch.flexmark.util.sequence.builder.StringSequenceBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

import static com.vladsch.flexmark.util.Utils.maxLimit;
import static com.vladsch.flexmark.util.Utils.min;
import static com.vladsch.flexmark.util.Utils.minLimit;
import static com.vladsch.flexmark.util.sequence.SequenceUtils.countTrailingSpaceTab;
import static com.vladsch.flexmark.util.sequence.SequenceUtils.endsWithEOL;
import static com.vladsch.flexmark.util.sequence.SequenceUtils.isBlank;
import static com.vladsch.flexmark.util.sequence.SequenceUtils.trimEnd;
import static com.vladsch.flexmark.util.sequence.SequenceUtils.trimmedEOL;

public class LineAppendableImpl implements LineAppendable {
    final private static char EOL = '\n';

    final private boolean passThrough;              // pass through mode for all operations to appendable without examination
    final private BitFieldSet<Options> options;

    // pre-formatted nesting level, while >0 all text is passed through as is and not monitored
    private int preFormattedNesting;
    private int preFormattedFirstLine;        // line which should be prefixed
    private int preFormattedFirstLineOffset;  // first line start of preformatted offset
    private int preFormattedLastLine;         // last line of preformatted text
    private int preFormattedLastLineOffset;   // last line end of preformatted offset

    // accumulated text and line information
    private ISequenceBuilder<?, ?> appendable;
    final private ArrayList<LineInfo> linesInfo;     // line contents
    final private ArrayList<CharSequence> lines;     // line contents

    // indent level to use after the next \n and before text is appended
    private CharSequence prefix;                     // current prefix
    private CharSequence prefixAfterEol;             // next prefix after eol
    private CharSequence indentPrefix;               // indent prefix
    final private Stack<CharSequence> prefixStack;
    final private Stack<Boolean> indentPrefixStack;

    // current line being accumulated
    private boolean allWhitespace;                          // all chars were whitespace
    private boolean lastWasWhitespace;                      // last char was whitespace
    private int eolOnFirstText;                             // append EOLs on first text
    final private ArrayList<Runnable> indentsOnFirstEol;    // append indents on first eol
    final private Stack<Integer> optionStack = new Stack<>();

    public LineAppendableImpl(int formatOptions) {
        this(null, LineAppendable.toOptionSet(formatOptions));
    }

    public LineAppendableImpl(@Nullable Appendable builder, int formatOptions) {
        this(builder, LineAppendable.toOptionSet(formatOptions));
    }

    public LineAppendableImpl(@Nullable Appendable appendable, BitFieldSet<Options> formatOptions) {
        this.appendable = appendable instanceof ISequenceBuilder<?, ?> ? ((ISequenceBuilder<?, ?>) appendable).getBuilder()
                : appendable instanceof LineAppendable ? ((LineAppendable) appendable).getBuilder()
                : StringSequenceBuilder.emptyBuilder();

        options = formatOptions;
        passThrough = any(F_PASS_THROUGH);
        preFormattedNesting = 0;
        preFormattedFirstLine = -1;
        preFormattedLastLine = -1;
        allWhitespace = true;
        lastWasWhitespace = false;
        linesInfo = new ArrayList<>();
        lines = new ArrayList<>();
        prefixStack = new Stack<>();
        indentPrefixStack = new Stack<>();
        prefix = BasedSequence.EMPTY;
        prefixAfterEol = BasedSequence.EMPTY;
        indentPrefix = BasedSequence.EMPTY;
        eolOnFirstText = 0;
        indentsOnFirstEol = new ArrayList<>();
    }

    @NotNull
    @Override
    public BitFieldSet<Options> getOptionSet() {
        return options;
    }

    @NotNull
    @Override
    public LineAppendable setOptions(int flags) {
        options.setAll(flags);
        return this;
    }

    @Override
    public @NotNull LineAppendable pushOptions() {
        optionStack.push(options.toInt());
        return this;
    }

    @Override
    public @NotNull LineAppendable popOptions() {
        if (optionStack.isEmpty()) {
            throw new IllegalStateException("Option stack is empty");
        }
        Integer mask = optionStack.pop();
        options.setAll(mask);
        return this;
    }

    @Override
    public @NotNull LineAppendable changeOptions(int addFlags, int removeFlags) {
        if ((addFlags & removeFlags) != 0) {
            throw new IllegalStateException(String.format("Add flags:%d and remove flags:%d overlap:%d", addFlags, removeFlags, addFlags & removeFlags));
        }
        options.orMask(addFlags);
        options.andNotMask(removeFlags);
        return this;
    }

    private boolean any(int flags) {
        return options.any(flags);
    }

    private boolean isConvertingTabs() {
        return any(F_CONVERT_TABS | F_COLLAPSE_WHITESPACE);
    }

    private boolean isTrimTrailingWhitespace() {
        return any(F_TRIM_TRAILING_WHITESPACE);
    }

    private boolean isTrimLeadingWhitespace() {
        return any(F_TRIM_LEADING_WHITESPACE);
    }

    private boolean isCollapseWhitespace() {
        return any(F_COLLAPSE_WHITESPACE);
    }

    @NotNull
    @Override
    public BasedSequence getIndentPrefix() {
        return BasedSequence.of(indentPrefix);
    }

    @NotNull
    @Override
    public LineAppendable setIndentPrefix(@Nullable CharSequence prefix) {
        indentPrefix = prefix == null ? BasedSequence.NULL : prefix;
        return this;
    }

    @Override
    public @NotNull BasedSequence getPrefix() {
        return BasedSequence.of(prefixAfterEol);
    }

    @Override
    public @NotNull BasedSequence getBeforeEolPrefix() {
        return BasedSequence.of(prefix);
    }

    private CharSequence combinedPrefix(@Nullable CharSequence prefix, @Nullable CharSequence suffix) {
        if (prefix != null && prefix.length() > 0 && suffix != null && suffix.length() > 0) {
            return String.valueOf(prefix) + suffix;
        } else if (prefix != null && prefix.length() > 0) {
            return prefix;
        } else if (suffix != null && suffix.length() > 0) {
            return suffix;
        } else {
            return BasedSequence.NULL;
        }
    }

    @NotNull
    @Override
    public LineAppendable addPrefix(@NotNull CharSequence prefix, boolean afterEol) {
        if (!passThrough) {
            if (afterEol) {
                prefixAfterEol = combinedPrefix(prefixAfterEol, prefix);
            } else {
                this.prefix = combinedPrefix(prefixAfterEol, prefix);
                prefixAfterEol = this.prefix;
            }
        }
        return this;
    }

    public int getAfterEolPrefixDelta() {
        return prefixAfterEol.length() - prefix.length();
    }

    @NotNull
    @Override
    public LineAppendable setPrefix(@Nullable CharSequence prefix, boolean afterEol) {
        if (!passThrough) {
            if (afterEol) {
                prefixAfterEol = prefix == null ? BasedSequence.NULL : prefix;
            } else {
                this.prefix = prefix == null ? BasedSequence.NULL : prefix;
                prefixAfterEol = this.prefix;
            }
        }
        return this;
    }

    @NotNull
    @Override
    public LineAppendable indent() {
        if (!passThrough) {
            line();
            rawIndent();
        }
        return this;
    }

    private void rawIndent() {
        prefixStack.push(prefixAfterEol);
        prefix = combinedPrefix(prefixAfterEol, indentPrefix);
        prefixAfterEol = prefix;
        indentPrefixStack.push(true);
    }

    private void rawUnIndent() {
        if (prefixStack.isEmpty()) throw new IllegalStateException("unIndent with an empty stack");
        if (!indentPrefixStack.peek()) throw new IllegalStateException("unIndent for element added by pushPrefix(), use popPrefix() instead");

        prefix = prefixStack.pop();
        prefixAfterEol = prefix;
        indentPrefixStack.pop();
    }

    @NotNull
    @Override
    public LineAppendable unIndent() {
        if (!passThrough) {
            line();
            rawUnIndent();
        }
        return this;
    }

    @NotNull
    @Override
    public LineAppendable unIndentNoEol() {
        if (!passThrough) {
            if (isLastEol()) {
                rawUnIndent();
            } else {
                CharSequence prefix = this.prefix;
                rawUnIndent();
                prefixAfterEol = this.prefix;
                this.prefix = prefix;
            }
        }
        return this;
    }

    @NotNull
    @Override
    public LineAppendable pushPrefix() {
        if (!passThrough) {
            prefixStack.push(prefixAfterEol);
            indentPrefixStack.push(false);
        }
        return this;
    }

    @NotNull
    @Override
    public LineAppendable popPrefix(boolean afterEol) {
        if (!passThrough) {
            if (prefixStack.isEmpty()) throw new IllegalStateException("popPrefix with an empty stack");
            if (indentPrefixStack.peek()) throw new IllegalStateException("popPrefix for element added by indent(), use unIndent() instead");

            prefixAfterEol = prefixStack.pop();
            if (!afterEol) {
                prefix = prefixAfterEol;
            }
            indentPrefixStack.pop();
        }
        return this;
    }

    @NotNull
    LineInfo getLastLineInfo() {
        return linesInfo.isEmpty() ? LineInfo.NULL : linesInfo.get(linesInfo.size() - 1);
    }

    private boolean isTrailingBlankLine() {
        return appendable.length() == 0 && getLastLineInfo().isBlankText();
    }

    private int lastNonBlankLine(int endLine) {
        if (endLine > lines.size() && appendable.length() > 0 && !allWhitespace) {
            // have dangling text
            return lines.size();
        }

        int i = Math.min(lines.size(), endLine);
        while (i-- > 0) {
            LineInfo lineInfo = linesInfo.get(i);
            if (!lineInfo.isBlankText()) break;
        }
        return i;
    }

    private int trailingBlankLines() {
        return lines.size() - lastNonBlankLine(lines.size()) - 1;
    }

    private boolean isLastEol() {
        return appendable.length() == 0 && getLastLineInfo().isNotNull();
    }

    private void addLineRange(Range textRange, CharSequence prefix) {
        assert textRange.getStart() <= textRange.getEnd();

        CharSequence sequence = appendable.toSequence();
        CharSequence text = textRange.isNull() ? BasedSequence.NULL : sequence.subSequence(textRange.getStart(), textRange.getEnd());
        CharSequence eol = trimmedEOL(sequence);

        if (textRange.isEmpty()) {
            prefix = SequenceUtils.trimEnd(prefix);
        }

        assert !endsWithEOL(text);

        CharSequence line = appendable.getBuilder().append(prefix).append(text).append(eol).toSequence();

        LineInfo.Preformatted preformatted;

        if (preFormattedNesting > 0) {
            preformatted = preFormattedFirstLine == lines.size() ? LineInfo.Preformatted.FIRST : LineInfo.Preformatted.BODY;
        } else {
            preformatted = preFormattedFirstLine == lines.size() ? LineInfo.Preformatted.LAST : LineInfo.Preformatted.NONE;
        }

        linesInfo.add(LineInfo.create(getLastLineInfo(), prefix.length(), text.length(), line.length(), isBlank(prefix), allWhitespace || text.length() == 0, preformatted));
        lines.add(line);

        resetBuilder();
    }

    private void resetBuilder() {
        appendable = appendable.getBuilder();
    }

    private void appendEol() {
        appendable.append(EOL);
        int endOffset = appendable.length();
        addLineRange(Range.of(0, endOffset - 1), prefix);
        allWhitespace = true;
        lastWasWhitespace = false;
        eolOnFirstText = 0;

        rawIndentsOnFirstEol();
    }

    private void rawIndentsOnFirstEol() {
        prefix = prefixAfterEol;

        while (!indentsOnFirstEol.isEmpty()) {
            Runnable runnable = indentsOnFirstEol.remove(indentsOnFirstEol.size() - 1);
            rawIndent();
            runnable.run();
        }
    }

    private void appendEol(int count) {
        while (count-- > 0) {
            appendEol();
        }
    }

    private boolean isPrefixed(int currentLine) {
        return any(F_PREFIX_PRE_FORMATTED) || (preFormattedFirstLine == currentLine || preFormattedNesting == 0 && preFormattedLastLine != currentLine);
    }

    /**
     * Returns text range if EOL was appended
     * <p>
     * NOTE: if range == Range.NULL then no line would be added
     *
     * @return pair of line text range if EOL was added and prefix
     */
    private Pair<Range, CharSequence> getRangePrefixAfterEol() {
        int startOffset = 0;
        int endOffset = appendable.length() + 1;
        int currentLine = getLineCount();
        boolean needPrefix = isPrefixed(currentLine);

        if (passThrough) {
            return new Pair<>(Range.of(startOffset, endOffset - 1), needPrefix ? prefix : BasedSequence.NULL);
        } else {
            if (allWhitespace && (preFormattedNesting == 0 && !(preFormattedFirstLine == currentLine || preFormattedLastLine == currentLine))) {
                if (any(F_ALLOW_LEADING_EOL) || !lines.isEmpty()) {
                    return new Pair<>(Range.of(startOffset, endOffset - 1), prefix);
                } else {
                    return new Pair<>(Range.NULL, BasedSequence.NULL);
                }
            } else {
                // apply options other than convert tabs which is done at time of appending
                if (isTrimTrailingWhitespace() && preFormattedNesting == 0) {
                    if (allWhitespace) {
                        startOffset = endOffset - 1;
                    } else {
                        endOffset -= countTrailingSpaceTab(appendable.toSequence(), endOffset - 1);
                    }
                }

                if (preFormattedFirstLine == currentLine) {
                    if (startOffset > preFormattedFirstLineOffset) startOffset = preFormattedFirstLineOffset;
                }

                if (preFormattedLastLine == currentLine) {
                    if (endOffset < preFormattedLastLineOffset + 1) endOffset = preFormattedLastLineOffset + 1;
                }

                return new Pair<>(Range.of(startOffset, endOffset - 1), needPrefix ? prefix : BasedSequence.NULL);
            }
        }
    }

    /**
     * Returns text offset before EOL if EOL was issued
     *
     * @return would be offset after adding EOL - 1
     */
    private int offsetAfterEol() {
        Pair<Range, CharSequence> rangePrefixAfterEol = getRangePrefixAfterEol();
        LineInfo lastLineInfo = getLastLineInfo();

        if (rangePrefixAfterEol.getFirst().isNull()) {
            return lastLineInfo.sumLength;
        } else {
            Range range = rangePrefixAfterEol.getFirst();
            CharSequence prefix = rangePrefixAfterEol.getSecond();

            if (range.isEmpty() && prefix.length() != 0) {
                prefix = trimEnd(prefix);
            }

            return lastLineInfo.sumLength + rangePrefixAfterEol.getFirst().getSpan() + prefix.length();
        }
    }

    private void doEolOnFirstTest() {
        if (eolOnFirstText > 0) {
            eolOnFirstText = 0;
            appendEol();
        }
    }

    private void appendImpl(CharSequence s, int index) {
        char c = s.charAt(index);

        if (passThrough) {
            if (c == EOL) {
                appendEol();
            } else {
                if (eolOnFirstText > 0) {
                    eolOnFirstText = 0;
                    appendEol();
                }

                if (c != '\t' && c != ' ') allWhitespace = false;
                appendable.append(c);
            }
        } else {
            if (c == EOL) {
                Pair<Range, CharSequence> rangePrefixAfterEol = getRangePrefixAfterEol();
                Range textRange = rangePrefixAfterEol.getFirst();
                if (textRange.isNull()) {
                    // nothing to add, just add EOL
                    resetBuilder();
                } else {
                    // add EOL and line
                    appendable.append(c);
                    addLineRange(textRange, rangePrefixAfterEol.getSecond());
                }

                allWhitespace = true;
                lastWasWhitespace = false;
                rawIndentsOnFirstEol();
            } else {
                doEolOnFirstTest();

                if (c == '\t') {
                    if (preFormattedNesting == 0 && any(F_COLLAPSE_WHITESPACE)) {
                        if (!lastWasWhitespace) {
                            appendable.append(' ');
                            lastWasWhitespace = true;
                        }
                    } else {
                        if (any(F_CONVERT_TABS)) {
                            int column = appendable.length();
                            int spaces = 4 - (column % 4);
                            appendable.append(' ', spaces);
                        } else {
                            appendable.append(s, index, index + 1);
                        }
                    }
                } else {
                    if (c == ' ') {
                        if (preFormattedNesting == 0) {
                            if (!any(F_TRIM_LEADING_WHITESPACE) || (appendable.length() != 0 && !allWhitespace)) {
                                if (any(F_COLLAPSE_WHITESPACE)) {
                                    if (!lastWasWhitespace) {
                                        appendable.append(' ');
                                    }
                                } else {
                                    appendable.append(' ');
                                }
                            }
                        } else {
                            appendable.append(' ');
                        }
                        lastWasWhitespace = true;
                    } else {
                        allWhitespace = false;
                        lastWasWhitespace = false;
                        appendable.append(s, index, index + 1);
                    }
                }
            }
        }
    }

    private void appendImpl(CharSequence csq, int start, int end) {
        int startIndex = -1;

        int i = start;

        while (i < end) {
            appendImpl(csq, i++);
        }
    }

    @NotNull
    @Override
    public LineAppendable append(@NotNull CharSequence csq) {
        appendImpl(csq, 0, csq.length());
        return this;
    }

    @NotNull
    @Override
    public ISequenceBuilder<?, ?> getBuilder() {
        return appendable.getBuilder();
    }

    @NotNull
    @Override
    public LineAppendable append(@NotNull CharSequence csq, int start, int end) {
        appendImpl(csq, start, end);
        return this;
    }

    @NotNull
    @Override
    public LineAppendable append(char c) {
        appendImpl(Character.toString(c), 0);
        return this;
    }

    @NotNull
    public LineAppendable append(char c, int count) {
        append(RepeatedSequence.repeatOf(c, count));
        return this;
    }

    @NotNull
    public LineAppendable repeat(@NotNull CharSequence csq, int count) {
        append(RepeatedSequence.repeatOf(csq, count));
        return this;
    }

    @NotNull
    public LineAppendable repeat(@NotNull CharSequence csq, int start, int end, int count) {
        append(RepeatedSequence.repeatOf(csq.subSequence(start, end), count));
        return this;
    }

    @NotNull
    @Override
    public LineAppendable line() {
        if (preFormattedNesting > 0 || appendable.length() != 0) {
            appendImpl(SequenceUtils.EOL, 0);
        } else {
            rawIndentsOnFirstEol();
        }
        return this;
    }

    @NotNull
    @Override
    public LineAppendable lineWithTrailingSpaces(int count) {
        if (preFormattedNesting > 0 || appendable.length() != 0) {
            int options = this.options.toInt();
            this.options.andNotMask(F_TRIM_TRAILING_WHITESPACE | F_COLLAPSE_WHITESPACE);
            if (count > 0) append(' ', count);
            appendImpl(SequenceUtils.EOL, 0);
            this.options.setAll(options);
        }
        return this;
    }

    @NotNull
    @Override
    public LineAppendable lineIf(boolean predicate) {
        if (predicate) line();
        return this;
    }

    @NotNull
    @Override
    public LineAppendable blankLine() {
        line();
        if (!lines.isEmpty() && !isTrailingBlankLine() || lines.isEmpty() && any(F_ALLOW_LEADING_EOL)) appendEol();
        return this;
    }

    @NotNull
    @Override
    public LineAppendable blankLineIf(boolean predicate) {
        if (predicate) blankLine();
        return this;
    }

    @NotNull
    @Override
    public LineAppendable blankLine(int count) {
        line();
        if ((any(F_ALLOW_LEADING_EOL) || !lines.isEmpty())) {
            int addBlankLines = count - trailingBlankLines();
            appendEol(addBlankLines);
        }
        return this;
    }

    @NotNull
    @Override
    public LineAppendable lineOnFirstText(boolean value) {
        if (value) eolOnFirstText++;
        else if (eolOnFirstText > 0) eolOnFirstText--;
        return this;
    }

    @NotNull
    @Override
    public LineAppendable removeIndentOnFirstEOL(@NotNull Runnable listener) {
        indentsOnFirstEol.remove(listener);
        return this;
    }

    @NotNull
    @Override
    public LineAppendable addIndentOnFirstEOL(@NotNull Runnable listener) {
        indentsOnFirstEol.add(listener);
        return this;
    }

    @Override
    public int getLineCount() {
        return lines.size();
    }

    @Override
    public int column() {
        return appendable.length();
    }

    @NotNull
    @Override
    public LineInfo getLineInfo(int lineIndex) {
        return linesInfo.get(lineIndex);
    }

    @Override
    public @NotNull BasedSequence getLine(int lineIndex) {
        return BasedSequence.of(lines.get(lineIndex));
    }

    @Override
    public int offset() {
        return getLastLineInfo().sumLength;
    }

    @Override
    public int offsetWithPending() {
        return offsetAfterEol();
    }

    @Override
    public boolean isPendingSpace() {
        return appendable.length() > 0 && lastWasWhitespace;
    }

    @Override
    public int getPendingSpace() {
        if (lastWasWhitespace && appendable.length() != 0) {
            return SequenceUtils.countTrailingSpaceTab(appendable.toSequence());
        }
        return 0;
    }

    @Override
    public int getPendingEOL() {
        if (appendable.length() == 0) {
            // use count of blank lines+1
            return trailingBlankLines() + 1;
        } else {
            return 0;
        }
    }

    @Override
    public boolean isPreFormatted() {
        return preFormattedNesting > 0;
    }

    @NotNull
    @Override
    public LineAppendable openPreFormatted(boolean addPrefixToFirstLine) {
        if (preFormattedNesting == 0) {
            if (preFormattedFirstLine != lines.size()) {
                preFormattedFirstLine = lines.size();
                preFormattedFirstLineOffset = appendable.length();
            }
        }

        ++preFormattedNesting;
        return this;
    }

    @NotNull
    @Override
    public LineAppendable closePreFormatted() {
        if (preFormattedNesting <= 0) throw new IllegalStateException("closePreFormatted called with nesting == 0");
        --preFormattedNesting;

        if (preFormattedNesting == 0 && !isLastEol()) {
            // this will be the last line of preformatted text
            preFormattedLastLine = lines.size();
            preFormattedLastLineOffset = appendable.length();
        }
        return this;
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        try {
            appendToNoLine(out, true, Integer.MAX_VALUE, Integer.MAX_VALUE, 0, Integer.MAX_VALUE);
            out.append(appendable);
        } catch (IOException ignored) {

        }
        return out.toString();
    }

    @NotNull
    @Override
    public String toString(boolean withPrefixes, int maxBlankLines, int maxTrailingBlankLines) {
        StringBuilder out = new StringBuilder();
        try {
            appendTo(out, withPrefixes, maxBlankLines, maxTrailingBlankLines, 0, Integer.MAX_VALUE);
        } catch (IOException ignored) {

        }
        return out.toString();
    }

    @NotNull
    @Override
    public CharSequence toSequence(boolean withPrefixes, int maxBlankLines, int maxTrailingBlankLines) {
        ISequenceBuilder<?, ?> out = getBuilder();
        try {
            appendTo(out, withPrefixes, maxBlankLines, maxTrailingBlankLines, 0, Integer.MAX_VALUE);
        } catch (IOException ignored) {

        }
        return out.toSequence();
    }

    @Override
    public <T extends Appendable> T appendTo(@NotNull T out, boolean withPrefixes, int maxBlankLines, int maxTrailingBlankLines, int startLine, int endLine) throws IOException {
        line();
        return appendToNoLine(out, withPrefixes, maxBlankLines, maxTrailingBlankLines, startLine, endLine);
    }

    public <T extends Appendable> T appendToNoLine(@NotNull T out, boolean withPrefixes, int maxBlankLines, int maxTrailingBlankLines, int startLine, int endLine) throws IOException {
        boolean tailEOL = maxTrailingBlankLines >= 0;
        maxBlankLines = Math.max(0, maxBlankLines);
        maxTrailingBlankLines = Math.max(0, maxTrailingBlankLines);

        int iMax = min(lines.size(), endLine);
        int lastNonBlankLine = lastNonBlankLine(endLine);
        int consecutiveBlankLines = 0;

        for (int i = startLine; i < iMax; i++) {
            LineInfo info = linesInfo.get(i);
            CharSequence line = lines.get(i);

            if (info.isBlankText() && !info.isPreformatted()) {
                if (i > lastNonBlankLine) {
                    // NOTE: these are tail blank lines
                    if (consecutiveBlankLines < maxTrailingBlankLines) {
                        consecutiveBlankLines++;
                        if (withPrefixes) out.append(trimEnd(info.getPrefix(line)));
                        if (tailEOL || consecutiveBlankLines != maxTrailingBlankLines) {
                            out.append(EOL);
                        }
                    }
                } else {
                    if (consecutiveBlankLines < maxBlankLines) {
                        consecutiveBlankLines++;
                        if (withPrefixes) out.append(trimEnd(info.getPrefix(line)));
                        out.append(EOL);
                    }
                }
            } else {
                consecutiveBlankLines = 0;
                if (tailEOL || i < lastNonBlankLine || info.isPreformatted() && info.getPreformatted() != LineInfo.Preformatted.LAST) {
                    if (withPrefixes) out.append(line);
                    else out.append(info.getText(line));
                } else {
                    if (withPrefixes) out.append(info.getTextNoEOL(line));
                    else out.append(info.getText(line));
                }
            }
        }
        return out;
    }

    @NotNull
    @Override
    public LineAppendable append(@NotNull LineAppendable lineAppendable, int startLine, int endLine) {
        lineAppendable.line();

        int iMax = lineAppendable.getLineCount();
        for (int i = 0; i < iMax; i++) {
            CharSequence line = lineAppendable.getLine(i);
            LineInfo lineInfo = lineAppendable.getLineInfo(i);
            CharSequence text = lineInfo.getTextNoEOL(line);
            CharSequence prefix = lineInfo.getPrefix(line);

            appendable.append(text);
            appendable.append(EOL);
            allWhitespace = lineInfo.isBlankText();

            int endOffset = appendable.length();
            CharSequence combinedPrefix = any(F_PREFIX_PRE_FORMATTED) || !lineInfo.isPreformatted() || lineInfo.getPreformatted() == LineInfo.Preformatted.FIRST ? combinedPrefix(this.prefix, prefix) : BasedSequence.NULL;
            addLineRange(Range.of(0, endOffset - 1), combinedPrefix);
        }
        return this;
    }

    /**
     * Remove lines and return index from which line info must be recomputed
     *
     * @param startLine start line index to remove
     * @param endLine   end line index to remove
     *
     * @return index from which line info must be recomputed
     */
    private int removeLinesRaw(int startLine, int endLine) {
        int useStartLine = minLimit(startLine, 0);
        int useEndLine = maxLimit(endLine, getLineCount());

        if (useStartLine < useEndLine) {
            linesInfo.subList(useStartLine, useEndLine).clear();
            lines.subList(useStartLine, useEndLine).clear();

            // recompute lineInfo for lines at or after the delete lines
            return useStartLine;
        }
        return lines.size();
    }

    private void recomputeLineInfo(int startLine) {
        // recompute lineInfo for lines at or after the delete lines
        int iMax = lines.size();
        startLine = Math.max(0, startLine);

        if (startLine < iMax) {
            LineInfo lastInfo = startLine - 1 >= 0 ? linesInfo.get(startLine - 1) : LineInfo.NULL;
            for (int i = startLine; i < iMax; i++) {
                LineInfo info = linesInfo.get(i);
                lastInfo = LineInfo.create(lastInfo, info);
                linesInfo.set(i, lastInfo);

                if (!lastInfo.needAggregateUpdate(info)) break;
            }
        }
    }

    @NotNull
    @Override
    public LineAppendable removeLines(int startLine, int endLine) {
        int useStartLine = removeLinesRaw(startLine, endLine);
        recomputeLineInfo(useStartLine);
        return this;
    }

    @Override
    public LineAppendable removeExtraBlankLines(int maxBlankLines, int maxTrailingBlankLines, int startLine, int endLine) {
        line();

        maxBlankLines = Math.max(0, maxBlankLines);
        maxTrailingBlankLines = Math.max(0, maxTrailingBlankLines);

        int iMax = min(endLine, lines.size());

        int consecutiveBlankLines = 0;
        int maxConsecutiveBlankLines = maxTrailingBlankLines;
        int minRemovedLine = lines.size();

        for (int i = iMax; i-- > 0; ) {
            LineInfo info = linesInfo.get(i);
            CharSequence line = lines.get(i);

            if (info.isBlankText() && !info.isPreformatted()) {
                if (consecutiveBlankLines >= maxConsecutiveBlankLines) {
                    // remove the last blank line to stay consistent with what would be done when appendingTo
                    linesInfo.remove(i + consecutiveBlankLines);
                    lines.remove(i + consecutiveBlankLines);
                    minRemovedLine = i + consecutiveBlankLines;
                } else {
                    consecutiveBlankLines++;
                }
            } else {
                consecutiveBlankLines = 0;
                maxConsecutiveBlankLines = maxBlankLines;
            }
        }

        recomputeLineInfo(minRemovedLine);
        return this;
    }

    @Override
    public void setPrefixLength(int lineIndex, int prefixLength) {
        CharSequence line = lines.get(lineIndex);
        LineInfo info = linesInfo.get(lineIndex);

        if (prefixLength < 0 || prefixLength >= line.length() - 1)
            throw new IllegalArgumentException(String.format("prefixLength %d is out of valid range [0, %d) for the line", prefixLength, line.length() - 1));

        if (prefixLength != info.prefixLength) {
            CharSequence prefix = line.subSequence(0, prefixLength);
            LineInfo newInfo = LineInfo.create(
                    lineIndex == 0 ? LineInfo.NULL : linesInfo.get(lineIndex - 1),
                    prefix.length(),
                    info.prefixLength + info.textLength - prefixLength,
                    info.length,
                    isBlank(prefix),
                    isBlank(line.subSequence(prefixLength, info.getTextEnd())),
                    info.getPreformatted()
            );

            linesInfo.set(lineIndex, newInfo);
            this.recomputeLineInfo(lineIndex + 1);
        }
    }

    @Override
    public void setLine(int lineIndex, @NotNull CharSequence prefix, @NotNull CharSequence content) {
        LineInfo info = linesInfo.get(lineIndex);
        CharSequence text = content;
        CharSequence eol = trimmedEOL(content);

        if (eol == null) eol = SequenceUtils.EOL;
        else text = text.subSequence(0, text.length() - eol.length());

        if (text.length() == 0) {
            prefix = SequenceUtils.trimEnd(prefix);
        }

        CharSequence line = appendable.getBuilder().append(prefix).append(text).append(eol).toSequence();

        LineInfo.Preformatted preformatted = info.getPreformatted();

        LineInfo newInfo = LineInfo.create(
                lineIndex == 0 ? LineInfo.NULL : linesInfo.get(lineIndex - 1),
                prefix.length(),
                text.length(),
                line.length(),
                isBlank(prefix),
                isBlank(text),
                preformatted
        );

        linesInfo.set(lineIndex, newInfo);
        lines.set(lineIndex, line);
        this.recomputeLineInfo(lineIndex + 1);
    }

    @Override
    public void forAllLines(int maxTrailingBlankLines, int startLine, int endLine, @NotNull LineProcessor processor) {
        line();
        int removeBlankLines = minLimit(trailingBlankLines() - minLimit(maxTrailingBlankLines, 0), 0);

        int iMax = lines.size() - removeBlankLines;
        startLine = Math.max(0, startLine);
        endLine = Math.max(startLine, Math.min(iMax, endLine));

        for (int i = startLine; i < endLine; i++) {
            if (!processor.processLine(BasedSequence.of(lines.get(i)), linesInfo.get(i))) break;
        }
    }
}
