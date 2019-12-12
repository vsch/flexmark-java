package com.vladsch.flexmark.util.html;

import com.vladsch.flexmark.util.Pair;
import com.vladsch.flexmark.util.Utils;
import com.vladsch.flexmark.util.collection.BitEnumSet;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.Range;
import com.vladsch.flexmark.util.sequence.RepeatedSequence;
import com.vladsch.flexmark.util.sequence.SequenceUtils;
import com.vladsch.flexmark.util.sequence.builder.SequenceBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.*;

import static com.vladsch.flexmark.util.Utils.*;

public class LineFormattingAppendableImpl implements LineFormattingAppendable {
    final private static char EOL = '\n';

    final private boolean passThrough;
    final private BitEnumSet<Options> options;

    // pre-formatted nesting level, while >0 all text is passed through as is and not monitored
    private int preFormattedNesting;
    private int preFormattedFirstLine;        // line which should be prefixed
    private int preFormattedFirstLineOffset;  // first line start of preformatted offset
    private int preFormattedLastLine;         // last line of preformatted text
    private int preFormattedLastLineOffset;   // first line end of preformatted offset

    // accumulated text and line information
    final private StringBuilder appendable;
    final private ArrayList<BasedSequence> lines;     // line contents
    final private ArrayList<BasedSequence> prefixes;  // line prefixes
    private int textLength;                           // accumulated length of all offsets
    private int prefixLength;                         // accumulated length of all prefixes

    // indent level to use after the next \n and before text is appended
    private BasedSequence prefix;
    private BasedSequence prefixAfterEol;
    private BasedSequence indentPrefix;
    final private Stack<BasedSequence> prefixStack;
    final private Stack<Boolean> indentPrefixStack;
    final private SequenceBuilder builder;

    // current line being accumulated
    private int lineStart;            // start of line
    private boolean allWhitespace;    // all chars were whitespace
    private boolean wasWhitespace;    // last char was whitespace
    private int lineOnFirstText;      // issue EOL on first text
    final private ArrayList<Runnable> indentsOnFirstEol;    // issue indents on first eol

    public LineFormattingAppendableImpl(Options... formatOptions) {
        this(null, LineFormattingAppendable.toOptionSet(formatOptions));
    }

    public LineFormattingAppendableImpl(BitEnumSet<Options> formatOptions) {
        this(null, formatOptions);
    }

    public LineFormattingAppendableImpl(int formatOptions) {
        this(null, LineFormattingAppendable.toOptionSet(formatOptions));
    }

    public LineFormattingAppendableImpl(@Nullable SequenceBuilder builder, int formatOptions) {
        this(builder, LineFormattingAppendable.toOptionSet(formatOptions));
    }

    public LineFormattingAppendableImpl(@Nullable SequenceBuilder builder, Options... formatOptions) {
        this(builder, LineFormattingAppendable.toOptionSet(formatOptions));
    }

    public LineFormattingAppendableImpl(@Nullable SequenceBuilder builder, BitEnumSet<Options> formatOptions) {
        this.builder = builder;
        options = formatOptions;
        passThrough = some(F_PASS_THROUGH);
        preFormattedNesting = 0;
        preFormattedFirstLine = -1;
        preFormattedLastLine = -1;
        lineStart = 0;
        textLength = 0;
        prefixLength = 0;
        allWhitespace = true;
        wasWhitespace = false;
        appendable = new StringBuilder();
        lines = new ArrayList<>();
        prefixes = new ArrayList<>();
        prefixStack = new Stack<>();
        indentPrefixStack = new Stack<>();
        prefix = BasedSequence.EMPTY;
        prefixAfterEol = BasedSequence.EMPTY;
        indentPrefix = BasedSequence.EMPTY;
        lineOnFirstText = 0;
        indentsOnFirstEol = new ArrayList<>();
    }

    @NotNull
    @Override
    public BitEnumSet<Options> getOptionSet() {
        return options;
    }

    @NotNull
    @Override
    public LineFormattingAppendable setOptions(int options) {
        this.options.replaceAll(options);
        return this;
    }

    private boolean some(int options) {
        return this.options.some(options);
    }

    private boolean isConvertingTabs() {
        return some(F_CONVERT_TABS | F_COLLAPSE_WHITESPACE);
    }

    private boolean isSuppressingTrailingWhitespace() {
        return some(F_SUPPRESS_TRAILING_WHITESPACE);
    }

    private boolean isAllowLeadingWhitespace() {
        return some(F_ALLOW_LEADING_WHITESPACE);
    }

    private boolean isCollapseWhitespace() {
        return some(F_COLLAPSE_WHITESPACE);
    }

    @NotNull
    @Override
    public CharSequence getIndentPrefix() {
        return indentPrefix;
    }

    @NotNull
    @Override
    public LineFormattingAppendable setIndentPrefix(@Nullable CharSequence prefix) {
        indentPrefix = prefix == null ? BasedSequence.NULL : BasedSequence.of(prefix);
        return this;
    }

    @Override
    public CharSequence getPrefix() {
        return prefixAfterEol;
    }

    @NotNull
    @Override
    public LineFormattingAppendable addPrefix(@NotNull CharSequence prefix, boolean afterEol) {
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

    @NotNull
    @Override
    public LineFormattingAppendable setPrefix(@Nullable CharSequence prefix, boolean afterEol) {
        if (!passThrough) {
            if (afterEol) {
                prefixAfterEol = prefix == null ? BasedSequence.NULL : BasedSequence.of(prefix);
            } else {
                this.prefix = prefix == null ? BasedSequence.NULL : BasedSequence.of(prefix);
                prefixAfterEol = this.prefix;
            }
        }
        return this;
    }

    @NotNull
    @Override
    public LineFormattingAppendable indent() {
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
    public LineFormattingAppendable unIndent() {
        if (!passThrough) {
            line();
            rawUnIndent();
        }
        return this;
    }

    @NotNull
    @Override
    public LineFormattingAppendable unIndentNoEol() {
        if (!passThrough) {
            if (isLastEol()) {
                rawUnIndent();
            } else {
                BasedSequence prefix = this.prefix;
                rawUnIndent();
                prefixAfterEol = this.prefix;
                this.prefix = prefix;
            }
        }
        return this;
    }

    @NotNull
    @Override
    public LineFormattingAppendable pushPrefix() {
        if (!passThrough) {
            prefixStack.push(prefixAfterEol);
            indentPrefixStack.push(false);
        }
        return this;
    }

    @NotNull
    @Override
    public LineFormattingAppendable popPrefix(boolean afterEol) {
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

    private boolean isTrailingBlankLine() {
        int i = lines.size();
        if (i-- > 0) {
            BasedSequence line = lines.get(i);
            return line.isBlank();
        }
        return appendable.length() == 0;
    }

    private int lastNonBlankLine() {
        int i = lines.size();
        while (i-- > 0) {
            BasedSequence line = lines.get(i);
            if (!line.isBlank()) break;
        }
        return i + 1;
    }

    private int trailingBlankLines() {
        return lines.size() - lastNonBlankLine();
    }

    private boolean isLastEol() {
        return appendable.length() > 0 && appendable.charAt(appendable.length() - 1) == EOL;
    }

    private void addLineRange(Range range, BasedSequence prefix) {
        //if (range.getStart() > range.getEnd()) {
        //    int tmp = 0;
        //}
        assert range.getStart() <= range.getEnd();

        if (builder != null) {
            lines.add(range.isNull() ? BasedSequence.NULL : builder.toSequence().subSequence(range.getStart(), range.getEnd()));
        } else {
            lines.add(range.isNull() ? BasedSequence.NULL : range.basedSubSequence(appendable));
        }

        if (range.isEmpty() && !prefix.isEmpty()) {
            prefix = prefix.trimEnd();
            prefixes.add(prefix);
        } else {
            prefixes.add(prefix);
        }

        textLength += range.getSpan() + 1;
        prefixLength += prefix.length();
    }

    private void appendEol() {
        appendable.append(EOL);
        appendBuilder(SequenceUtils.EOL);

        int startOffset = lineStart;
        lineStart = appendable.length();
        int endOffset = lineStart;
        addLineRange(Range.of(startOffset, endOffset - 1), prefix);
        allWhitespace = true;
        wasWhitespace = false;
        lineOnFirstText = 0;

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

    /**
     * Returns text range if EOL was appended
     * <p>
     * NOTE: if range == Range.NULL then no line would be added
     *
     * @return pair of line text range if EOL was added and prefix
     */
    private Pair<Range, BasedSequence> getRangePrefixAfterEol() {
        int startOffset = lineStart;
        int endOffset = appendable.length() + 1;
        int currentLine = getLineCount();
        boolean needPrefix = some(F_PREFIX_PRE_FORMATTED) || (preFormattedNesting == 0 && preFormattedLastLine != currentLine || preFormattedFirstLine == currentLine);

        if (passThrough) {
            return new Pair<>(Range.of(startOffset, endOffset - 1), needPrefix ? prefix : BasedSequence.NULL);
        } else {
            if (allWhitespace && (preFormattedNesting == 0 && !(preFormattedFirstLine == currentLine || preFormattedLastLine == currentLine))) {
                if (some(F_ALLOW_LEADING_EOL) && appendable.length() == 0) {
                    return new Pair<>(Range.of(startOffset, endOffset - 1), prefix);
                } else {
                    return new Pair<>(Range.NULL, BasedSequence.NULL);
                }
            } else {
                // apply options other than convert tabs which is done at time of appending
                if (!some(F_ALLOW_LEADING_WHITESPACE) &&
                        (preFormattedNesting == 0 || preFormattedFirstLine == currentLine) &&
                        preFormattedNesting == 0 && preFormattedLastLine != currentLine
                ) {
                    if (allWhitespace) {
                        startOffset = endOffset - 1;
                    } else {
                        while (startOffset < endOffset && appendable.charAt(startOffset) == ' ') {
                            startOffset++;
                        }
                    }
                }

                if (some(F_SUPPRESS_TRAILING_WHITESPACE) && preFormattedNesting == 0) {
                    if (allWhitespace) {
                        startOffset = endOffset - 1;
                    } else {
                        while (startOffset < endOffset - 1 && appendable.charAt(endOffset - 2) == ' ') {
                            endOffset--;
                        }
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
    private int offsetAfterEol(boolean includePrefixes) {
        Pair<Range, BasedSequence> rangePrefixAfterEol = getRangePrefixAfterEol();
        if (rangePrefixAfterEol.getFirst().isNull()) {
            return includePrefixes ? textLength + prefixLength : textLength;
        } else {
            Range range = rangePrefixAfterEol.getFirst();
            BasedSequence prefix = rangePrefixAfterEol.getSecond();

            if (range.isEmpty() && !prefix.isEmpty()) {
                prefix = prefix.trimEnd();
            }

            return includePrefixes ? textLength + rangePrefixAfterEol.getFirst().getSpan() + prefixLength + prefix.length()
                    : textLength + rangePrefixAfterEol.getFirst().getSpan();
        }
    }

    private void appendBuilder(CharSequence s) {
        appendBuilder(s, 0, s.length());
    }

    private void appendBuilder(CharSequence s, int start, int end) {
        if (builder != null) {
            if (start == 0 && end == s.length()) {
                builder.append(s);
            } else {
                builder.append(s.subSequence(start, end));
            }
        }
    }

    private void appendBuilder(char c) {
        if (builder != null) {
            builder.append(c);
        }
    }

    private void appendBuilder(char c, int count) {
        if (builder != null) {
            builder.append(c, count);
        }
    }

    private void appendImpl(CharSequence s, int index) {
        char c = s.charAt(index);

        if (passThrough) {
            if (c == EOL) {
                appendEol();
            } else {
                if (lineOnFirstText > 0) {
                    lineOnFirstText = 0;
                    appendEol();
                }

                if (c != '\t' && c != ' ') allWhitespace = false;
                appendable.append(c);
                appendBuilder(s, index, index + 1);
            }
        } else {
            if (c == EOL) {
                Pair<Range, BasedSequence> rangePrefixAfterEol = getRangePrefixAfterEol();
                if (rangePrefixAfterEol.getFirst().isNull()) {
                    // nothing, add EOL and don't add line
                    // add the EOL so as not to mess up the text but do not add the line
                    appendable.append(c);
                    appendBuilder(s, index, index + 1);

                    lineStart = appendable.length();
                    allWhitespace = true;
                    wasWhitespace = false;
                    rawIndentsOnFirstEol();
                } else {
                    // add EOL and line
                    appendable.append(c);
                    appendBuilder(s, index, index + 1);

                    lineStart = appendable.length();
                    addLineRange(rangePrefixAfterEol.getFirst(), rangePrefixAfterEol.getSecond());
                    allWhitespace = true;
                    wasWhitespace = false;
                    rawIndentsOnFirstEol();
                }
            } else {
                if (lineOnFirstText > 0) {
                    lineOnFirstText = 0;
                    appendEol();
                }

                if (c == '\t') {
                    if (preFormattedNesting == 0 && some(F_COLLAPSE_WHITESPACE)) {
                        if (!wasWhitespace) {
                            appendable.append(' ');
                            appendBuilder(' ');
                            wasWhitespace = true;
                        }
                    } else {
                        if (some(F_CONVERT_TABS)) {
                            int column = appendable.length() - lineStart;
                            int spaces = 4 - (column % 4);
                            appendable.append("    ", 0, spaces);
                            appendBuilder(' ', spaces);
                        } else {
                            appendable.append(c);
                            appendBuilder(s, index, index + 1);
                        }
                    }
                } else {
                    if (c == ' ') {
                        if (preFormattedNesting == 0 && some(F_COLLAPSE_WHITESPACE)) {
                            if (!wasWhitespace) {
                                appendable.append(' ');
                                appendBuilder(' ');
                            }
                        } else {
                            appendable.append(' ');
                            appendBuilder(' ');
                        }
                        wasWhitespace = true;
                    } else {
                        allWhitespace = false;
                        wasWhitespace = false;
                        appendable.append(c);
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

    @NotNull
    @Override
    public LineFormattingAppendable append(@NotNull CharSequence csq) {
        appendImpl(csq, 0, csq.length());
        return this;
    }

    @NotNull
    @Override
    public LineFormattingAppendable append(@NotNull CharSequence csq, int start, int end) {
        appendImpl(csq, start, end);
        return this;
    }

    @NotNull
    @Override
    public LineFormattingAppendable append(char c) {
        appendImpl(Character.toString(c), 0);
        return this;
    }

    @NotNull
    public LineFormattingAppendable repeat(char c, int count) {
        append(RepeatedSequence.repeatOf(c, count));
//        int i = count;
//        String s = Character.toString(c);
//        while (i-- > 0) appendImpl(s, 0);
        return this;
    }

    @NotNull
    public LineFormattingAppendable repeat(@NotNull CharSequence csq, int count) {
        append(RepeatedSequence.repeatOf(csq, count));
//        int i = count;
//        while (i-- > 0) append(csq);
        return this;
    }

    @NotNull
    public LineFormattingAppendable repeat(@NotNull CharSequence csq, int start, int end, int count) {
        append(RepeatedSequence.repeatOf(csq.subSequence(start, end), count));
//        int i = count;
//        while (i-- > 0) append(csq, start, end);
        return this;
    }

    private BasedSequence combinedPrefix(@Nullable CharSequence prefix, @Nullable CharSequence suffix) {
        if (prefix != null && prefix.length() > 0 && suffix != null && suffix.length() > 0) {
            CharSequence charSequence = String.valueOf(prefix) + suffix;
            return BasedSequence.of(charSequence);
        } else if (prefix != null && prefix.length() > 0) {
            return BasedSequence.of(prefix);
        } else if (suffix != null && suffix.length() > 0) {
            return BasedSequence.of(suffix);
        } else {
            return BasedSequence.NULL;
        }
    }

    @Override
    public boolean isPreFormattedLine(int line) {
        return getLinePrefix(line).isNull();
    }

    @NotNull
    @Override
    public LineFormattingAppendable append(@NotNull LineFormattingAppendable lineAppendable, int startLine, int endLine) {
        List<CharSequence> lines = lineAppendable.getLineContents(startLine, endLine);
        List<BasedSequence> prefixes = lineAppendable.getLinePrefixes(startLine, endLine);

        int iMax = lines.size();
        for (int i = 0; i < iMax; i++) {
            CharSequence line = lines.get(i);
            BasedSequence prefix = prefixes.get(i);

            int startOffset = appendable.length();

            appendable.append(line);
            appendBuilder(line);

            appendable.append(EOL);
            appendBuilder(EOL);

            int endOffset = appendable.length();

            BasedSequence combinedPrefix = some(F_PREFIX_PRE_FORMATTED) || !lineAppendable.isPreFormattedLine(startLine + i) ? combinedPrefix(this.prefix, prefix) : BasedSequence.NULL;
            addLineRange(Range.of(startOffset, endOffset - 1), combinedPrefix);
            lineStart = endOffset;
        }
        return this;
    }

    @NotNull
    @Override
    public LineFormattingAppendable prefixLines(@NotNull CharSequence prefix, boolean addAfterLinePrefix, int startLine, int endLine) {
        int useStartLine = minLimit(startLine, 0);
        int useEndLine = maxLimit(endLine, getLineCount());

        if (prefix.length() > 0 && useStartLine < useEndLine) {
            // now need to add prefix to line contents
            CharSequence lastLinePrefix = BasedSequence.NULL;
            BasedSequence lastPrefix = addAfterLinePrefix ? combinedPrefix(lastLinePrefix, prefix) : combinedPrefix(prefix, lastLinePrefix);

            for (int i = useStartLine; i < useEndLine; i++) {
                BasedSequence linePrefix = prefixes.get(i);
                if (some(F_PREFIX_PRE_FORMATTED) || !isPreFormattedLine(i)) {
                    // need prefix
                    if (!linePrefix.equals(lastLinePrefix)) {
                        lastLinePrefix = linePrefix;
                        lastPrefix = addAfterLinePrefix ? combinedPrefix(lastLinePrefix, prefix) : combinedPrefix(prefix, lastLinePrefix);
                    }
                    prefixes.set(i, lastPrefix);
                } else {
                    prefixes.set(i, BasedSequence.NULL);
                }
            }
        }
        return this;
    }

    @NotNull
    @Override
    public LineFormattingAppendable removeLines(int startLine, int endLine) {
        int useStartLine = minLimit(startLine, 0);
        int useEndLine = maxLimit(endLine, getLineCount());

        if (useStartLine < useEndLine) {
            int count = useEndLine - useStartLine;
            while (count-- > 0) {
                BasedSequence line = lines.remove(startLine);
                BasedSequence prefix = prefixes.remove(startLine);
                textLength -= line.length() + 1;
                prefixLength -= prefix.length();
            }
        }
        return this;
    }

    @Override
    public String toString(int maxBlankLines) {
        if (passThrough) {
            line();
            return appendable.toString();
        } else {
            StringBuilder out = new StringBuilder();
            try {
                appendTo(out, maxBlankLines);
            } catch (IOException ignored) {

            }
            return out.toString();
        }
    }

    @NotNull
    @Override
    public List<CharSequence> getLineContents(int startOffset, int endOffset) {
        line();
        ArrayList<CharSequence> result = new ArrayList<>();
        int iMax = Utils.maxLimit(endOffset, lines.size());

        for (int i = startOffset; i < iMax; i++) {
            BasedSequence line = lines.get(i);
            result.add(line);
        }
        return result;
    }

    @NotNull
    @Override
    public List<BasedSequence> getLinePrefixes(int startOffset, int endOffset) {
        line();
        ArrayList<BasedSequence> result = new ArrayList<>();
        int iMax = Utils.maxLimit(endOffset, lines.size());

        for (int i = startOffset; i < iMax; i++) {
            result.add(prefixes.get(i));
        }
        return result;
    }

    @NotNull
    @Override
    public List<CharSequence> getLines(int startOffset, int endOffset) {
        line();

        ArrayList<CharSequence> result = new ArrayList<>();
        int iMax = Utils.maxLimit(endOffset, lines.size());

        if (builder != null) {
            for (int i = startOffset; i < iMax; i++) {
                BasedSequence line = lines.get(i);
                BasedSequence linePrefix = prefixes.get(i);

                if (!linePrefix.isEmpty()) {
                    BasedSequence basedSequence = builder.subContext().append(linePrefix).append(line).toSequence();
                    result.add(basedSequence);
                } else {
                    result.add(line);
                }
            }
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = startOffset; i < iMax; i++) {
                BasedSequence line = lines.get(i);
                BasedSequence linePrefix = prefixes.get(i);

                int lineStart = sb.length();
                if (!linePrefix.isEmpty()) sb.append(linePrefix);
                sb.append(line);

                result.add(sb.subSequence(lineStart, sb.length()));
            }
        }

        return result;
    }

    @NotNull
    @Override
    public LineFormattingAppendable appendTo(@NotNull Appendable out, int maxBlankLines, CharSequence prefix, int startLine, int endLine) throws IOException {
        line();
        int removeBlankLines = minLimit(trailingBlankLines() - minLimit(maxBlankLines, 0), 0);

        int iMax = min(endLine, lines.size() - removeBlankLines);

        for (int i = startLine; i < iMax; i++) {
            BasedSequence line = lines.get(i);
            BasedSequence linePrefix = prefixes.get(i);
            if (!linePrefix.isEmpty()) out.append(linePrefix);
            if (prefix != null && prefix.length() > 0) out.append(prefix);
            out.append(line);

            if (maxBlankLines != -1 || i + 1 != iMax) {
                out.append(EOL);
            }
        }
        return this;
    }

    @NotNull
    @Override
    public LineFormattingAppendable line() {
        if (preFormattedNesting > 0 || lineStart < appendable.length() || some(F_ALLOW_LEADING_EOL)) {
            appendImpl(SequenceUtils.EOL, 0);
        } else {
            rawIndentsOnFirstEol();
        }
        return this;
    }

    @NotNull
    @Override
    public LineFormattingAppendable lineWithTrailingSpaces(int count) {
        if (preFormattedNesting > 0 || lineStart < appendable.length() || some(F_ALLOW_LEADING_EOL)) {
            int options = this.options.toInt();
            this.options.clear(F_SUPPRESS_TRAILING_WHITESPACE | F_COLLAPSE_WHITESPACE);
            if (count > 0) repeat(' ', count);
            appendImpl(SequenceUtils.EOL, 0);
            this.options.replaceAll(options);
        }
        return this;
    }

    @NotNull
    @Override
    public LineFormattingAppendable addLine() {
        appendImpl(SequenceUtils.EOL, 0);
        return this;
    }

    @NotNull
    @Override
    public LineFormattingAppendable lineIf(boolean predicate) {
        if (predicate) line();
        return this;
    }

    @NotNull
    @Override
    public LineFormattingAppendable blankLine() {
        line();
        if (!isTrailingBlankLine()) appendEol();
        return this;
    }

    @NotNull
    @Override
    public LineFormattingAppendable blankLineIf(boolean predicate) {
        if (predicate) blankLine();
        return this;
    }

    @NotNull
    @Override
    public LineFormattingAppendable blankLine(int count) {
        line();
        int addBlankLines = count - trailingBlankLines();
        appendEol(addBlankLines);
        return this;
    }

    @Override
    public int getLineCount() {
        return lines.size();
    }

    @Override
    public int column() {
        return appendable.length() - lineStart;
    }

    @Override
    public int offset() {
        return textLength + prefixLength;
    }

    @Override
    public int offsetWithPending() {
        return offsetAfterEol(true);
    }

    @Override
    public int textOnlyOffset() {
        return textLength;
    }

    @Override
    public int textOnlyOffsetWithPending() {
        return offsetAfterEol(false);
    }

    @Override
    public boolean isPendingSpace() {
        return appendable.length() > 0 && " \t".indexOf(appendable.charAt(appendable.length() - 1)) != -1;
    }

    @Override
    public int getPendingSpace() {
        int length = appendable.length();
        if (length == 0) return 0;
        int spaces = 0;
        while (length-- > 0 && " \t".indexOf(appendable.charAt(length)) != -1) {
            spaces++;
        }
        return spaces;
    }

    @Override
    public int getPendingEOL() {
        int withPending = textOnlyOffsetWithPending();

        if (withPending == textLength) {
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
    public LineFormattingAppendable openPreFormatted(boolean addPrefixToFirstLine) {
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
    public LineFormattingAppendable closePreFormatted() {
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
        return appendable.toString();
    }

    @Override
    public void toBuilder(@NotNull SequenceBuilder builder, int maxBlankLines) {
        if (this.builder == null) return;

        line();
        int removeBlankLines = minLimit(trailingBlankLines() - minLimit(maxBlankLines, 0), 0);

        int iMax = lines.size() - removeBlankLines;
        for (int i = 0; i < iMax; i++) {
            BasedSequence prefix = prefixes.get(i);
            BasedSequence line = lines.get(i);
            builder.append(prefix);
            builder.append(line);

            if (maxBlankLines != -1 || i + 1 != iMax) {
                builder.append(SequenceUtils.EOL);
            }
        }
    }

    @NotNull
    @Override
    public LineFormattingAppendable lineOnFirstText(boolean value) {
        if (value) lineOnFirstText++;
        else if (lineOnFirstText > 0) lineOnFirstText--;
        return this;
    }

    @NotNull
    @Override
    public LineFormattingAppendable removeIndentOnFirstEOL(@NotNull Runnable runnable) {
        indentsOnFirstEol.remove(runnable);
        return this;
    }

    @NotNull
    @Override
    public LineFormattingAppendable addIndentOnFirstEOL(@NotNull Runnable runnable) {
        indentsOnFirstEol.add(runnable);
        return this;
    }
}
