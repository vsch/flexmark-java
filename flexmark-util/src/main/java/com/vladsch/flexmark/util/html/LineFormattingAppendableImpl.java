package com.vladsch.flexmark.util.html;

import com.vladsch.flexmark.util.Pair;
import com.vladsch.flexmark.util.Utils;
import com.vladsch.flexmark.util.collection.BitFieldSet;
import com.vladsch.flexmark.util.format.TrackedOffset;
import com.vladsch.flexmark.util.format.TrackedOffsetList;
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
import java.util.function.BiConsumer;

import static com.vladsch.flexmark.util.Utils.maxLimit;
import static com.vladsch.flexmark.util.Utils.min;
import static com.vladsch.flexmark.util.Utils.minLimit;
import static com.vladsch.flexmark.util.sequence.SequenceUtils.isBlank;
import static com.vladsch.flexmark.util.sequence.SequenceUtils.trimEnd;

public class LineFormattingAppendableImpl implements LineAppendable {
    final private static char EOL = '\n';

    final private boolean passThrough;              // pass through mode for all operations to appendable without examination
    final private BitFieldSet<Options> options;

    // pre-formatted nesting level, while >0 all text is passed through as is and not monitored
    private int preFormattedNesting;
    private int preFormattedFirstLine;        // line which should be prefixed
    private int preFormattedFirstLineOffset;  // first line start of preformatted offset
    private int preFormattedLastLine;         // last line of preformatted text
    private int preFormattedLastLineOffset;   // first line end of preformatted offset

    // accumulated text and line information
    final private StringBuilder appendable;
    final private ArrayList<CharSequence> lines;     // line contents
    final private ArrayList<CharSequence> prefixes;  // line prefixes
    private int textLength;                           // accumulated length of all offsets
    private int prefixLength;                         // accumulated length of all prefixes

    // indent level to use after the next \n and before text is appended
    private CharSequence prefix;
    private CharSequence prefixAfterEol;
    private CharSequence indentPrefix;
    final private Stack<CharSequence> prefixStack;
    final private Stack<Boolean> indentPrefixStack;
    private @Nullable SequenceBuilder builder;              // this builder is used for line construction, fresh builder for each line so offsets are preserved
    private @Nullable TrackedOffsetList trackedOffsets = TrackedOffsetList.EMPTY_LIST;

    // current line being accumulated
    private int lineStart;                                  // start of line
    private boolean allWhitespace;                          // all chars were whitespace
    private boolean lastWasWhitespace;                      // last char was whitespace
    private int lineOnFirstText;                            // append EOL on first text
    final private ArrayList<Runnable> indentsOnFirstEol;    // append indents on first eol
    final private Stack<Integer> optionStack = new Stack<>();

    public LineFormattingAppendableImpl(Options... formatOptions) {
        this(null, LineAppendable.toOptionSet(formatOptions));
    }

    public LineFormattingAppendableImpl(BitFieldSet<Options> formatOptions) {
        this(null, formatOptions);
    }

    public LineFormattingAppendableImpl(int formatOptions) {
        this(null, LineAppendable.toOptionSet(formatOptions));
    }

    public LineFormattingAppendableImpl(@Nullable SequenceBuilder builder, int formatOptions) {
        this(builder, LineAppendable.toOptionSet(formatOptions));
    }

    public LineFormattingAppendableImpl(@Nullable SequenceBuilder builder, Options... formatOptions) {
        this(builder, LineAppendable.toOptionSet(formatOptions));
    }

    public LineFormattingAppendableImpl(@Nullable SequenceBuilder builder, BitFieldSet<Options> formatOptions) {
        this.builder = builder == null ? null : builder.getBuilder();
        options = formatOptions;
        passThrough = any(F_PASS_THROUGH);
        preFormattedNesting = 0;
        preFormattedFirstLine = -1;
        preFormattedLastLine = -1;
        lineStart = 0;
        textLength = 0;
        prefixLength = 0;
        allWhitespace = true;
        lastWasWhitespace = false;
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
    public BitFieldSet<Options> getOptionSet() {
        return options;
    }

    @NotNull
    @Override
    public LineAppendable setOptions(int flags) {
        options.replaceAll(flags);
        return this;
    }

    @Override
    public @NotNull LineAppendable pushOptions() {
//        System.out.println(String.format("%6x: Pushing options %s", hashCode(), Long.toBinaryString(options.toInt())));
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
//        System.out.println(String.format("%6x: Popped options %s, %s", hashCode(), Long.toBinaryString(options.toInt()), Long.toBinaryString(mask)));
        return this;
    }

    @Override
    public @NotNull LineAppendable changeOptions(int addFlags, int removeFlags) {
        if ((addFlags & removeFlags) != 0) {
            throw new IllegalStateException(String.format("Add flags:%d and remove flags:%d overlap:%d", addFlags, removeFlags, addFlags & removeFlags));
        }
        options.orMask(addFlags);
        options.andNotMask(removeFlags);
//        System.out.println(String.format("%6x: Changed options %s, added: %s, removed:%s ", hashCode(), Long.toBinaryString(options.toInt()), Long.toBinaryString(addFlags), Long.toBinaryString(removeFlags)));
        return this;
    }

    private boolean any(int flags) {
        return options.any(flags);
    }

    private boolean isConvertingTabs() {
        return any(F_CONVERT_TABS | F_COLLAPSE_WHITESPACE);
    }

    private boolean isResolvingTrackedOffsets() {
        return !any(F_NO_TRACKED_OFFSETS);
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
    public CharSequence getIndentPrefix() {
        return indentPrefix;
    }

    @NotNull
    @Override
    public LineAppendable setIndentPrefix(@Nullable CharSequence prefix) {
        indentPrefix = prefix == null ? BasedSequence.NULL : prefix;
        return this;
    }

    @Override
    public CharSequence getPrefix() {
        return prefixAfterEol;
    }

    @Override
    public CharSequence getBeforeEolPrefix() {
        return prefix;
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
        return SequenceUtils.equals(prefixAfterEol, prefix) ? 0 : prefixAfterEol.length() - prefix.length();
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

    private boolean isTrailingBlankLine() {
        int i = lines.size();
        if (i-- > 0) {
            CharSequence line = lines.get(i);
            return isBlank(line);
        }
        return appendable.length() == 0;
    }

    private int lastNonBlankLine() {
        int i = lines.size();
        while (i-- > 0) {
            CharSequence line = lines.get(i);
            if (!isBlank(line)) break;
        }
        return i + 1;
    }

    private int trailingBlankLines() {
        return lines.size() - lastNonBlankLine();
    }

    private boolean isLastEol() {
        return appendable.length() > 0 && appendable.charAt(appendable.length() - 1) == EOL;
    }

    private void addLineRange(Range range, CharSequence prefix) {
        //if (range.getStart() > range.getEnd()) {
        //    int tmp = 0;
        //}
        assert range.getStart() <= range.getEnd();

        if (builder != null) {
            BasedSequence line = builder.toSequence();
            resetBuilder();

            assert !line.endsWithEOL():"Eol on line";
            if (line.safeCharAt(line.length() - 1) == '\n') {
                int tmp = 0;
            }
            lines.add(line);

            if (line.isBlank() && prefix.length() != 0) {
                prefix = SequenceUtils.trimEnd(prefix);
            }
            prefixes.add(prefix);
            textLength += line.length() + 1;
            prefixLength += prefix.length();
        } else {
            BasedSequence line = range.isNull() ? BasedSequence.NULL : range.basedSubSequence(appendable);
            assert !line.endsWithEOL():"Eol on line";
            if (line.safeCharAt(line.length() - 1) == '\n') {
                int tmp = 0;
            }
            lines.add(line);
            if (range.isEmpty() && prefix.length() != 0) {
                prefix = SequenceUtils.trimEnd(prefix);
            }

            prefixes.add(prefix);
            textLength += range.getSpan() + 1;
        }

        prefixLength += prefix.length();
    }

    private void resetBuilder() {
        if (builder != null && builder.isNotEmpty()) {
            // start a new line
            builder = builder.getBuilder();
        }
    }

    private void appendEol() {
        appendable.append(EOL);

        int startOffset = lineStart;
        lineStart = appendable.length();
        int endOffset = lineStart;
        addLineRange(Range.of(startOffset, endOffset - 1), prefix);
        allWhitespace = true;
        lastWasWhitespace = false;
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
    private Pair<Range, CharSequence> getRangePrefixAfterEol() {
        int startOffset = lineStart;
        int endOffset = appendable.length() + 1;
        int currentLine = getLineCount();
        boolean needPrefix = any(F_PREFIX_PRE_FORMATTED) || (preFormattedNesting == 0 && preFormattedLastLine != currentLine || preFormattedFirstLine == currentLine);

        if (passThrough) {
            return new Pair<>(Range.of(startOffset, endOffset - 1), needPrefix ? prefix : BasedSequence.NULL);
        } else {
            if (allWhitespace && (preFormattedNesting == 0 && !(preFormattedFirstLine == currentLine || preFormattedLastLine == currentLine))) {
                if (any(F_ALLOW_LEADING_EOL) && appendable.length() == 0) {
                    return new Pair<>(Range.of(startOffset, endOffset - 1), prefix);
                } else {
                    return new Pair<>(Range.NULL, BasedSequence.NULL);
                }
            } else {
                // apply options other than convert tabs which is done at time of appending
//                if (isTrimLeadingWhitespace() &&
//                        (preFormattedNesting == 0 || preFormattedFirstLine == currentLine) &&
//                        preFormattedNesting == 0 && preFormattedLastLine != currentLine
//                ) {
//                    if (allWhitespace) {
//                        startOffset = endOffset - 1;
//                    } else {
//                        while (startOffset < endOffset && appendable.charAt(startOffset) == ' ') {
//                            startOffset++;
//                        }
//                    }
//                }

                if (isTrimTrailingWhitespace() && preFormattedNesting == 0) {
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
        Pair<Range, CharSequence> rangePrefixAfterEol = getRangePrefixAfterEol();
        if (rangePrefixAfterEol.getFirst().isNull()) {
            return includePrefixes ? textLength + prefixLength : textLength;
        } else {
            Range range = rangePrefixAfterEol.getFirst();
            CharSequence prefix = rangePrefixAfterEol.getSecond();

            if (range.isEmpty() && prefix.length() != 0) {
                prefix = trimEnd(prefix);
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
            }
        } else {
            if (c == EOL) {
                Pair<Range, CharSequence> rangePrefixAfterEol = getRangePrefixAfterEol();
                if (rangePrefixAfterEol.getFirst().isNull()) {
                    // nothing, add EOL and don't add line
                    // add the EOL so as not to mess up the text but do not add the line
                    appendable.append(c);
                    resetBuilder();

                    lineStart = appendable.length();
                    allWhitespace = true;
                    lastWasWhitespace = false;
                    rawIndentsOnFirstEol();
                } else {
                    // add EOL and line
                    appendable.append(c);
                    lineStart = appendable.length();
                    addLineRange(rangePrefixAfterEol.getFirst(), rangePrefixAfterEol.getSecond());
                    allWhitespace = true;
                    lastWasWhitespace = false;
                    rawIndentsOnFirstEol();
                }
            } else {
                if (lineOnFirstText > 0) {
                    lineOnFirstText = 0;
                    appendEol();
                }

                if (c == '\t') {
                    if (preFormattedNesting == 0 && any(F_COLLAPSE_WHITESPACE)) {
                        if (!lastWasWhitespace) {
                            appendable.append(' ');
                            appendBuilder(' ');
                            lastWasWhitespace = true;
                        }
                    } else {
                        if (any(F_CONVERT_TABS)) {
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
                        if (preFormattedNesting == 0) {
                            if (!any(F_TRIM_LEADING_WHITESPACE) || (lineStart != appendable.length() && !allWhitespace)) {
                                if (any(F_COLLAPSE_WHITESPACE)) {
                                    if (!lastWasWhitespace) {
                                        appendable.append(' ');
                                        appendBuilder(' ');
                                    }
                                } else {
                                    appendable.append(' ');
                                    appendBuilder(' ');
                                }
                            }
                        } else {
                            appendable.append(' ');
                            appendBuilder(' ');
                        }
                        lastWasWhitespace = true;
                    } else {
                        allWhitespace = false;
                        lastWasWhitespace = false;
                        appendable.append(c);
                        appendBuilder(s, index, index + 1);
                    }
                }
            }
        }
    }

    private void appendImpl(CharSequence csq, int start, int end) {
        int startIndex = -1;

        int i = start;

        if (false && builder != null && csq instanceof BasedSequence) {
            startIndex = offsetAfterEol(true);
        }

        while (i < end) {
            appendImpl(csq, i++);
        }

        if (false && isResolvingTrackedOffsets() && builder != null && csq instanceof BasedSequence) {
            if (trackedOffsets != null) {
                if (this.trackedOffsets.isEmpty()) {
                    // NOTE: not initialized since tracked offsets are added to builder after creating appendable
                    if (!builder.getTrackedOffsets().isEmpty()) {
                        this.trackedOffsets = builder.getTrackedOffsets();
                    } else {
                        this.trackedOffsets = null;
                        return;
                    }
                }

                int endIndex = -1;
                BasedSequence subSequence = ((BasedSequence) csq).subSequence(start, end);
                int startOffset = subSequence.getStartOffset();
                int endOffset = subSequence.getEndOffset();

                List<TrackedOffset> trackedOffsets = this.trackedOffsets.getTrackedOffsets(startOffset, Math.max(startOffset, endOffset));
                if (!trackedOffsets.isEmpty()) {
                    for (TrackedOffset trackedOffset : trackedOffsets) {
                        if (!trackedOffset.isResolved()) {
                            if (endIndex == -1) endIndex = offsetAfterEol(true);
                            int index = trackedOffset.getOffset() - startOffset;
                            trackedOffset.setIndex(Math.min(endIndex, index + startIndex));
                            System.out.println(String.format("Resolved %d to %d, startIndex: %d, endIndex: %d, in '%s'", trackedOffset.getOffset(), trackedOffset.getIndex(), startIndex, endIndex, SequenceUtils.toVisibleWhitespaceString(subSequence)));
                        } else {
                            System.out.println(String.format("Resolved offset %d to %d at '%s'", trackedOffset.getOffset(), trackedOffset.getIndex(), SequenceUtils.toVisibleWhitespaceString(subSequence)));
                        }
                    }
                }
            }
        }
    }

    @NotNull
    @Override
    public LineAppendable append(@NotNull CharSequence csq) {
        appendImpl(csq, 0, csq.length());
        return this;
    }

    @Override
    public @Nullable SequenceBuilder getBuilder() {
        return builder;
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

    @Override
    public boolean isPreFormattedLine(int line) {
        return getLinePrefix(line) == BasedSequence.NULL;
    }

    @NotNull
    @Override
    public LineAppendable append(@NotNull LineAppendable lineAppendable, int startLine, int endLine) {
        CharSequence[] lines = lineAppendable.getLinesContent(startLine, endLine);
        CharSequence[] prefixes = lineAppendable.getLinesPrefix(startLine, endLine);

        int iMax = lines.length;
        for (int i = 0; i < iMax; i++) {
            CharSequence line = lines[i];
            CharSequence prefix = prefixes[i];

            int startOffset = appendable.length();

            appendable.append(line);
            appendBuilder(line);

            appendable.append(EOL);
            // NOTE: builder lines do not have EOL added
//            appendBuilder(EOL);

            int endOffset = appendable.length();

            CharSequence combinedPrefix = any(F_PREFIX_PRE_FORMATTED) || !lineAppendable.isPreFormattedLine(startLine + i) ? combinedPrefix(this.prefix, prefix) : BasedSequence.NULL;
            addLineRange(Range.of(startOffset, endOffset - 1), combinedPrefix);
            lineStart = endOffset;
        }
        return this;
    }

    @NotNull
    @Override
    public LineAppendable prefixLinesWith(@NotNull CharSequence prefix, boolean addAfterLinePrefix, int startLine, int endLine) {
        int useStartLine = minLimit(startLine, 0);
        int useEndLine = maxLimit(endLine, getLineCount());

        if (prefix.length() > 0 && useStartLine < useEndLine) {
            // now need to add prefix to line contents
            CharSequence lastLinePrefix = BasedSequence.NULL;
            CharSequence lastPrefix = addAfterLinePrefix ? combinedPrefix(lastLinePrefix, prefix) : combinedPrefix(prefix, lastLinePrefix);

            for (int i = useStartLine; i < useEndLine; i++) {
                CharSequence linePrefix = prefixes.get(i);
                if (any(F_PREFIX_PRE_FORMATTED) || !isPreFormattedLine(i)) {
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
    public LineAppendable removeLines(int startLine, int endLine) {
        int useStartLine = minLimit(startLine, 0);
        int useEndLine = maxLimit(endLine, getLineCount());

        if (useStartLine < useEndLine) {
            int count = useEndLine - useStartLine;
            while (count-- > 0) {
                CharSequence line = lines.remove(startLine);
                CharSequence prefix = prefixes.remove(startLine);
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
    public CharSequence[] getLinesContent(int startOffset, int endOffset) {
        line();

        int iMax = Utils.maxLimit(endOffset, lines.size());
        CharSequence[] result = new CharSequence[Math.max(0, iMax - startOffset)];

        for (int i = startOffset; i < iMax; i++) {
            CharSequence line = lines.get(i);
            result[i - startOffset] = line;
        }
        return result;
    }

    @Override
    public void setLinePrefixIndex(int lineIndex, int prefixEndIndex) {

    }

    @Override
    public void setLinePrefixIndex(int lineIndex, @NotNull CharSequence prefix, @NotNull CharSequence content) {

    }

    @NotNull
    @Override
    public int[] getLinesPrefixIndex(int startLine, int endLine) {
        line();

        int iMax = maxLimit(endLine, lines.size());
        int[] result = new int[Math.max(0, iMax - startLine)];

        for (int i = startLine; i < iMax; i++) {
            CharSequence prefix = prefixes.get(i);
            result[i - startLine] = prefix.length();
        }
        return result;
    }

    @NotNull
    @Override
    public CharSequence[] getLinesPrefix(int startOffset, int endOffset) {
        line();

        int iMax = Utils.maxLimit(endOffset, lines.size());
        CharSequence[] result = new CharSequence[Math.max(0, iMax - startOffset)];

        for (int i = startOffset; i < iMax; i++) {
            result[i - startOffset] = prefixes.get(i);
        }
        return result;
    }

    @NotNull
    @Override
    public CharSequence[] getLines(int startOffset, int endOffset) {
        line();

        int iMax = Utils.maxLimit(endOffset, lines.size());
        CharSequence[] result = new CharSequence[Math.max(0, iMax - startOffset)];

        if (builder != null) {
            for (int i = startOffset; i < iMax; i++) {
                CharSequence line = lines.get(i);
                CharSequence linePrefix = prefixes.get(i);

                if (linePrefix.length() != 0) {
                    CharSequence basedSequence = builder.getBuilder().append(linePrefix).append(line).toSequence();
                    result[i - startOffset] = basedSequence;
                } else {
                    result[i - startOffset] = line;
                }
            }
        } else {
            for (int i = startOffset; i < iMax; i++) {
                CharSequence line = lines.get(i);
                CharSequence linePrefix = prefixes.get(i);

                if (linePrefix.length() != 0) {
                    result[i - startOffset] = linePrefix.toString() + line;
                } else {
                    result[i - startOffset] = line;
                }
            }
        }

        return result;
    }

    @NotNull
    @Override
    public LineAppendable appendTo(@NotNull Appendable out, int maxBlankLines, CharSequence prefix, int startLine, int endLine) throws IOException {
        line();
        int removeBlankLines = minLimit(trailingBlankLines() - minLimit(maxBlankLines, 0), 0);

        int iMax = min(endLine, lines.size() - removeBlankLines);

        for (int i = startLine; i < iMax; i++) {
            CharSequence line = lines.get(i);
            CharSequence linePrefix = prefixes.get(i);
            if (linePrefix.length() != 0) out.append(linePrefix);
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
    public LineAppendable line() {
        if (preFormattedNesting > 0 || lineStart < appendable.length() || any(F_ALLOW_LEADING_EOL)) {
            appendImpl(SequenceUtils.EOL, 0);
        } else {
            rawIndentsOnFirstEol();
        }
        return this;
    }

    @NotNull
    @Override
    public LineAppendable lineWithTrailingSpaces(int count) {
        if (preFormattedNesting > 0 || lineStart < appendable.length() || any(F_ALLOW_LEADING_EOL)) {
            int options = this.options.toInt();
            this.options.andNotMask(F_TRIM_TRAILING_WHITESPACE | F_COLLAPSE_WHITESPACE);
            if (count > 0) append(' ', count);
            appendImpl(SequenceUtils.EOL, 0);
            this.options.replaceAll(options);
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
        if (!isTrailingBlankLine()) appendEol();
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
        return appendable.toString();
    }

    @Override
    public void toBuilder(@NotNull SequenceBuilder builder, int maxBlankLines) {
        line();
        int removeBlankLines = minLimit(trailingBlankLines() - minLimit(maxBlankLines, 0), 0);

        int iMax = lines.size() - removeBlankLines;
        for (int i = 0; i < iMax; i++) {
            CharSequence prefix = prefixes.get(i);
            CharSequence line = lines.get(i);
            builder.append(prefix);
            builder.append(line);

            if (maxBlankLines != -1 || i + 1 != iMax) {
                builder.append(SequenceUtils.EOL);
            }
        }
    }

    @Override
    public void forAllLines(@NotNull SequenceBuilder builder, int maxBlankLines, @NotNull BiConsumer<BasedSequence, Integer> consumer) {
        line();
        int removeBlankLines = minLimit(trailingBlankLines() - minLimit(maxBlankLines, 0), 0);

        int iMax = lines.size() - removeBlankLines;
        for (int i = 0; i < iMax; i++) {
            CharSequence prefix = prefixes.get(i);
            CharSequence line = lines.get(i);
            SequenceBuilder subBuilder = builder.getBuilder();
            subBuilder.append(prefix);
            subBuilder.append(line);

            if (maxBlankLines != -1 || i + 1 != iMax) {
                subBuilder.append(SequenceUtils.EOL);
            }

            consumer.accept(subBuilder.toSequence(), i);
        }
    }

    @NotNull
    @Override
    public LineAppendable lineOnFirstText(boolean value) {
        if (value) lineOnFirstText++;
        else if (lineOnFirstText > 0) lineOnFirstText--;
        return this;
    }

    @NotNull
    @Override
    public LineAppendable removeIndentOnFirstEOL(@NotNull Runnable runnable) {
        indentsOnFirstEol.remove(runnable);
        return this;
    }

    @NotNull
    @Override
    public LineAppendable addIndentOnFirstEOL(@NotNull Runnable runnable) {
        indentsOnFirstEol.add(runnable);
        return this;
    }
}
