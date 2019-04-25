package com.vladsch.flexmark.util.html;

import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.io.IOException;
import java.util.List;

/**
 * Used to collect line text for further processing
 * <p>
 * control output of new lines limiting them to terminate text but not create blank lines,
 * and control number of blank lines output, eliminate spaces before and after \n, other than indents
 * controlled by this class.
 * <p>
 * consecutive \n in the data are going go be collapsed to a single \n
 * <p>
 * tab is converted to a space if {@link #CONVERT_TABS} option is selected
 * <p>
 * spaces before and after \n are removed controlled by {@link #SUPPRESS_TRAILING_WHITESPACE} and {@link #ALLOW_LEADING_WHITESPACE}
 * <p>
 * use {@link #line()}, {@link #lineIf(boolean)}, {@link #blankLine()}, {@link #blankLineIf(boolean)}
 * and {@link #blankLine(int)} for getting these appended to result
 */
@SuppressWarnings({ "UnusedReturnValue", "SameParameterValue" })
public interface LineFormattingAppendable extends Appendable {
    int CONVERT_TABS = 0x0001;                  // expand tabs on column multiples of 4
    int COLLAPSE_WHITESPACE = 0x0002;           // collapse multiple tabs and spaces to single space
    int SUPPRESS_TRAILING_WHITESPACE = 0x0004;  // don't output trailing whitespace
    int PREFIX_AFTER_PENDING_EOL = 0; //0x0008;    // prefix takes effect after pending EOLs are output, there are no pending EOL
    int PASS_THROUGH = 0x0010;                  // just pass everything through to appendable with no formatting
    int ALLOW_LEADING_WHITESPACE = 0x0020;      // allow leading spaces on a line, else remove
    int ALLOW_LEADING_EOL = 0x0040;             // allow EOL at offset 0
    int PREFIX_PRE_FORMATTED = 0x0080;          // when prefixing lines, prefix pre-formatted lines
    int FORMAT_ALL = CONVERT_TABS | COLLAPSE_WHITESPACE | SUPPRESS_TRAILING_WHITESPACE;

    /**
     * Get current options
     *
     * @return option flags
     */
    int getOptions();

    /**
     * Set options on processing text
     *
     * @param options option flags
     * @return this
     */
    LineFormattingAppendable setOptions(int options);

    // these methods are monitored for content and formatting applied
    @Override
    LineFormattingAppendable append(CharSequence csq);
    @Override
    LineFormattingAppendable append(CharSequence csq, int start, int end);
    @Override
    LineFormattingAppendable append(char c);

    // same as calling append count times
    LineFormattingAppendable repeat(char c, int count);
    LineFormattingAppendable repeat(CharSequence csq, int count);
    LineFormattingAppendable repeat(CharSequence csq, int start, int end, int count);

    // the following do not apply formatting options
    /**
     * Append lines
     *
     * @param lineAppendable lines to append
     * @return this
     */
    default LineFormattingAppendable append(LineFormattingAppendable lineAppendable) {
        return append(lineAppendable, 0, Integer.MAX_VALUE);
    }

    /**
     * Append lines with given prefix
     *
     * @param lineAppendable lines to append
     * @param startLine      start line to append
     * @return this
     */
    default LineFormattingAppendable append(LineFormattingAppendable lineAppendable, int startLine) {
        return append(lineAppendable, startLine, Integer.MAX_VALUE);
    }

    /**
     * Append lines with given prefix
     *
     * @param lineAppendable lines to append
     * @param startLine      start line to append
     * @param endLine        end line to append
     * @return this
     */
    LineFormattingAppendable append(LineFormattingAppendable lineAppendable, int startLine, int endLine);

    /**
     * Test if given line is part of pre-formatted text
     *
     * @param line line
     * @return true if line is inside pre-formatted text
     */
    boolean isPreFormattedLine(int line);

    /**
     * Get the number of lines appended, does not include pending: EOLs
     *
     * @return number of lines appended
     */
    int getLineCount();

    /**
     * Add prefix to all lines
     *
     * @param prefix prefix to add
     * @return this
     */
    default LineFormattingAppendable prefixLines(CharSequence prefix) {
        return prefixLines(prefix, 0, getLineCount());
    }

    /**
     * Add prefix to all lines starting at given line
     *
     * @param prefix    prefix to add
     * @param startLine starting line offset
     * @return this
     */
    default LineFormattingAppendable prefixLines(CharSequence prefix, int startLine) {
        return prefixLines(prefix, startLine, getLineCount());
    }

    /**
     * Add prefix to selected lines
     *
     * @param prefix    prefix to add
     * @param startLine starting line offset
     * @param endLine   end line offset
     * @return this
     */
    default LineFormattingAppendable prefixLines(CharSequence prefix, int startLine, int endLine) {
        return prefixLines(prefix, false, startLine, endLine);
    }

    /**
     * Add prefix to selected lines either before the line prefix or after it
     *
     * @param prefix             prefix to add
     * @param addAfterLinePrefix if true add given prefix after the line prefix
     * @param startLine          starting line offset
     * @param endLine            end line offset
     * @return this
     */
    LineFormattingAppendable prefixLines(CharSequence prefix, boolean addAfterLinePrefix, int startLine, int endLine);

    /**
     * Get Lines without EOL
     *
     * @return list of lines
     */
    default List<CharSequence> getLines() {
        return getLines(0, Integer.MAX_VALUE);
    }

    /**
     * Get Lines without EOL
     *
     * @param startLine starting line offset
     * @return list of lines
     */
    default List<CharSequence> getLines(int startLine) {
        return getLines(startLine, Integer.MAX_VALUE);
    }

    /**
     * Get Lines without EOL
     *
     * @param startLine starting line offset
     * @param endLine   end line offset
     * @return list of lines
     */
    List<CharSequence> getLines(int startLine, int endLine);

    /**
     * Get Lines without prefixes or EOL
     *
     * @return list of lines
     */
    default List<CharSequence> getLineContents() {
        return getLineContents(0, Integer.MAX_VALUE);
    }

    /**
     * Get Line content of given line
     *
     * @return char sequence for the line
     */
    default CharSequence getLineContent(int lineIndex) {
        return getLineContents(lineIndex, lineIndex + 1).get(0);
    }

    /**
     * Get Line content of given line
     *
     * @return list of lines
     */
    default BasedSequence getLinePrefix(int lineIndex) {
        return getLinePrefixes(lineIndex, lineIndex + 1).get(0);
    }

    /**
     * Remove line range from result set
     *
     * @param startLine starting line offset
     * @param endLine   end line offset
     * @return this
     */
    LineFormattingAppendable removeLines(int startLine, int endLine);

    /**
     * Get Lines without prefixes or EOL
     *
     * @param startLine starting line offset
     * @return list of lines
     */
    default List<CharSequence> getLineContents(int startLine) {
        return getLineContents(startLine, Integer.MAX_VALUE);
    }

    /**
     * Get Lines without prefixes or EOL
     *
     * @param startLine starting line offset
     * @param endLine   end line offset
     * @return list of lines
     */
    List<CharSequence> getLineContents(int startLine, int endLine);

    /**
     * Get Line prefixes
     *
     * @return list of lines
     */
    default List<BasedSequence> getLinePrefixes() {
        return getLinePrefixes(0, Integer.MAX_VALUE);
    }

    /**
     * Get Line prefixes
     *
     * @param startLine starting line offset
     * @return list of lines
     */
    default List<BasedSequence> getLinePrefixes(int startLine) {
        return getLinePrefixes(startLine, Integer.MAX_VALUE);
    }

    /**
     * Get Line prefixes
     *
     * @param startLine starting line offset
     * @param endLine   end line offset
     * @return list of lines
     */
    List<BasedSequence> getLinePrefixes(int startLine, int endLine);

    /**
     * Get column offset after last append
     *
     * @return column offset after last append
     */
    int column();

    /**
     * Get text offset of all output lines (not including any text not included because it is not yet terminated by line() call)
     * NOTE: this includes prefixes
     *
     * @return offset of text as would be returned if all current lines were taken (without prefixes)
     */
    int offset();

    /**
     * Get column offset after last append
     * NOTE: this includes prefixes
     *
     * @return offset as would be returned by {@link #offset()} after line() call less 1 for EOL
     */
    int offsetWithPending();

    /**
     * Get text offset of all output lines (not including any text not included because it is not yet terminated by line() call)
     * NOTE: this does not include prefixes
     *
     * @return offset of text as would be returned if all current lines were taken (without prefixes)
     */
    int textOnlyOffset();

    /**
     * Get column offset after last append
     * NOTE: this does not include prefixes
     *
     * @return offset as would be returned by {@link #offset()} after line() call less 1 for EOL
     */
    int textOnlyOffsetWithPending();

    /**
     * Test if trailing text ends in space or tab
     *
     * @return true if ending in space or tab
     */
    boolean isPendingSpace();

    /**
     * Get trailing spaces or tabs of trailing text
     *
     * @return trailing spaces or tabs
     */
    int getPendingSpace();

    /**
     * Get number of EOLs at end of text (including pending text)
     *
     * @return number of eols at end of text
     */
    int getPendingEOL();

    /**
     * get the resulting text for all lines
     *
     * @param maxBlankLines maximum blank lines to allow at end, if -1 then no trailing EOL will be generated
     * @return resulting text
     */
    String toString(int maxBlankLines);

    /**
     * append lines to appendable with no trailing blank lines, if these are desired at the end of the output use {@link #appendTo(Appendable, int)}.
     *
     * @param out appendable to output the resulting lines
     * @return this
     * @throws IOException if thrown by appendable
     */
    default LineFormattingAppendable appendTo(Appendable out) throws IOException {
        return appendTo(out, 0, null, 0, Integer.MAX_VALUE);
    }

    /**
     * append lines to appendable with given maximum trailing blank lines
     *
     * @param out           appendable to output the resulting lines
     * @param maxBlankLines maximum blank lines to allow at end
     * @return this
     * @throws IOException if thrown by appendable
     */
    default LineFormattingAppendable appendTo(Appendable out, int maxBlankLines) throws IOException {
        return appendTo(out, maxBlankLines, null, 0, Integer.MAX_VALUE);
    }

    /**
     * append lines to appendable with given maximum trailing blank lines and given prefix to add to all lines
     *
     * @param out           appendable to output the resulting lines
     * @param maxBlankLines maximum blank lines to allow at end
     * @param prefix        prefix to add before each line
     * @return this
     * @throws IOException if thrown by appendable
     */
    default LineFormattingAppendable appendTo(Appendable out, int maxBlankLines, CharSequence prefix) throws IOException {
        return appendTo(out, maxBlankLines, prefix, 0, Integer.MAX_VALUE);
    }

    /**
     * append lines to appendable with given maximum trailing blank lines and given prefix to add to all lines
     *
     * @param out           appendable to output the resulting lines
     * @param maxBlankLines maximum blank lines to allow at end
     * @param prefix        prefix to add before each line
     * @param startLine     line from which to start output
     * @return this
     * @throws IOException if thrown by appendable
     */
    default LineFormattingAppendable appendTo(Appendable out, int maxBlankLines, CharSequence prefix, int startLine) throws IOException {
        return appendTo(out, maxBlankLines, prefix, startLine, Integer.MAX_VALUE);
    }

    /**
     * append lines to appendable with given maximum trailing blank lines and given prefix to add to all lines
     *
     * @param out           appendable to output the resulting lines
     * @param maxBlankLines maximum blank lines to allow at end, if -1 then no trailing EOL will be generated
     * @param prefix        prefix to add before each line
     * @param startLine     line from which to start output
     * @return this
     * @throws IOException if thrown by appendable
     */
    LineFormattingAppendable appendTo(Appendable out, int maxBlankLines, CharSequence prefix, int startLine, int endLine) throws IOException;

    /**
     * Add a new line, if there was any unterminated text appended
     *
     * @return this
     */
    LineFormattingAppendable line();

    /**
     * Add a new line, keep trailing spaces if there was any unterminated text appended
     *
     * @return this
     */
    default LineFormattingAppendable lineWithTrailingSpaces() {
        return lineWithTrailingSpaces(0);
    }

    /**
     * Add a new line, keep trailing spaces if there was any unterminated text appended
     *
     * @param count number of trailing spaces to add
     * @return this
     */
    LineFormattingAppendable lineWithTrailingSpaces(int count);

    /**
     * Add a new line or blank lines as needed.
     *
     * @return this
     */
    LineFormattingAppendable addLine();

    /**
     * Add a new line, if predicate is true and there was any unterminated text appended
     *
     * @param predicate if true then new line will be started
     * @return this
     */
    LineFormattingAppendable lineIf(boolean predicate);

    /**
     * Add a blank line, if there is not one already appended.
     *
     * @return this
     */
    LineFormattingAppendable blankLine();

    /**
     * Add a blank line, if predicate is true and there isn't already blank lines appended.
     *
     * @param predicate when true append blank line
     * @return this
     */
    LineFormattingAppendable blankLineIf(boolean predicate);

    /**
     * Add a blank lines, if there isn't already given number of blank lines appended.
     * Will append only enough blank lines to increase it to given level. If more are already in the wings then nothing is done.
     *
     * @param count number of blank lines to append
     * @return this
     */
    LineFormattingAppendable blankLine(int count);

    /**
     * @return true if in pre-formatted region
     */
    boolean isPreFormatted();

    /**
     * Open preformatted section and suspend content modification but add prefix to first line
     *
     * @return this
     */
    default LineFormattingAppendable openPreFormatted() {
        return openPreFormatted(true);
    }

    /**
     * Open preformatted section and suspend content modification
     *
     * @param addPrefixToFirstLine if true will add current prefix to first line
     * @return this
     */
    LineFormattingAppendable openPreFormatted(boolean addPrefixToFirstLine);

    /**
     * Close preformatted section and suspend content modification
     *
     * @return this
     */
    LineFormattingAppendable closePreFormatted();

    /**
     * Increase the indent level, will terminate the current line if there is unterminated text
     * <p>
     * NOTE: this is equivalent to pushPrefix(), addPrefix(getIndentPrefix()) but adds flag to
     * validate that {@link #unIndent()} is called only on prefixes added by indent()
     *
     * @return this
     */
    LineFormattingAppendable indent();

    /**
     * Decrease the indent level, min level is 0, will terminate the current line if there is unterminated text
     * <p>
     * NOTE: this is equivalent to popPrefix() but with validation
     * that it is called only on prefixes added by {@link #indent()}
     *
     * @return this
     */
    LineFormattingAppendable unIndent();

    /**
     * Decrease the indent level, if there is unterminated text then unindented prefix
     * is to be applied after the next EOL.
     * <p>
     * Will NOT terminate the current line if there is unterminated text
     * <p>
     * NOTE: should be used with {@link #addIndentOnFirstEOL(Runnable)} if callback is invoked
     *
     * @return this
     */
    LineFormattingAppendable unIndentNoEol();

    /**
     * Get prefix appended after a new line character for every indent level
     *
     * @return char sequence of the current indent prefix used for each indent level
     */
    CharSequence getIndentPrefix();

    /**
     * Set prefix to append after a new line character for every indent level
     *
     * @param prefix prefix characters for new lines appended after this is set
     * @return this
     */
    LineFormattingAppendable setIndentPrefix(CharSequence prefix);

    /**
     * Get prefix being applied to all lines, even in pre-formatted sections
     *
     * @return char sequence of the current prefix
     */
    CharSequence getPrefix();

    /**
     * Add to prefix appended after a new line character for every line
     * and after a new line in pre-formatted sections
     * <p>
     * This appends the sequence to current prefix
     *
     * @param prefix prefix characters to add to current prefix for new lines appended after this is set
     * @return this
     */
    default LineFormattingAppendable addPrefix(CharSequence prefix) {
        return addPrefix(prefix, getPendingEOL() == 0);
    }

    /**
     * Set prefix appended after a new line character for every line
     * and after a new line in pre-formatted sections
     * <p>
     * This appends the sequence to current prefix
     *
     * @param prefix prefix characters to add to current prefix for new lines appended after this is set
     * @return this
     */
    default LineFormattingAppendable setPrefix(CharSequence prefix) {
        return setPrefix(prefix, getPendingEOL() == 0);
    }

    /**
     * Add to prefix appended after a new line character for every line
     * and after a new line in pre-formatted sections
     * <p>
     * This appends the sequence to current prefix
     *
     * @param prefix   prefix characters to add to current prefix for new lines appended after this is set
     * @param afterEol if true prefix will take effect after EOL
     * @return this
     */
    LineFormattingAppendable addPrefix(CharSequence prefix, boolean afterEol);

    /**
     * Set prefix appended after a new line character for every line
     * and after a new line in pre-formatted sections
     * <p>
     * This appends the sequence to current prefix
     *
     * @param prefix   prefix characters to add to current prefix for new lines appended after this is set
     * @param afterEol if true prefix will take effect after EOL
     * @return this
     */
    LineFormattingAppendable setPrefix(CharSequence prefix, boolean afterEol);

    /**
     * Save the current prefix on the stack
     *
     * @return this
     */
    LineFormattingAppendable pushPrefix();

    /**
     * Pop a prefix from the stack and set the current prefix
     *
     * @return this
     */
    default LineFormattingAppendable popPrefix() {
        return popPrefix(false);
    }

    /**
     * Pop a prefix from the stack and set the current prefix
     *
     * @param afterEol if true prefix will take effect after EOL
     * @return this
     */
    LineFormattingAppendable popPrefix(boolean afterEol);

    default LineFormattingAppendable setLineOnFirstText() {
        return lineOnFirstText(true);
    }

    default LineFormattingAppendable clearLineOnFirstText() {
        return lineOnFirstText(false);
    }

    LineFormattingAppendable lineOnFirstText(boolean value);

    LineFormattingAppendable addIndentOnFirstEOL(Runnable runnable);
    LineFormattingAppendable removeIndentOnFirstEOL(Runnable runnable);
}
