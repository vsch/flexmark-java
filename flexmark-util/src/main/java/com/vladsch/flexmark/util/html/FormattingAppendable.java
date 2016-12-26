package com.vladsch.flexmark.util.html;

import com.vladsch.flexmark.util.Ref;

import java.io.IOException;

/**
 * Used to intercept appended text and format it for indents,
 * control output of new lines limiting them to terminate text but not create blank lines,
 * and control number of blank lines output, eliminate spaces before and after \n, other than indents
 * controlled by this class.
 * <p>
 * consecutive \n in the data are going go be collapsed to a single \n and which will only be output
 * when text is appended after the \n, or on flush() call to output any pending \n's
 * <p>
 * tab is converted to a space
 * spaces before and after \n are removed since indentation is controlled by this class through
 * the {@link #indent()} and {@link #unIndent()} members.
 * optionally will collapse multiple spaces to one space
 * <p>
 * Indent is only output after a \n and before text. Blank lines and other \n's do not output an indent
 * use {@link #line()}, {@link #lineIf(boolean)}, {@link #blankLine()}, {@link #blankLineIf(boolean)}
 * and {@link #blankLine(int)} for getting these in the appended result
 * <p>
 * Also adds conditional new line and indent capability, allowing compact form if there is no "child" element
 * data output, and fully unfolded, indented form if there is "child" element text output.
 * <p>
 * For example output of HTML list items in compact form as &lt;li&gt;item text&lt;/li&gt; if it has no children
 * and as split over several lines, with child content indented, if it has children:
 * &lt;li&gt;item text
 * child text
 * &lt;/li&gt;
 */
public interface FormattingAppendable extends Appendable {
    // no IOExceptions are thrown, you can get the first IOException or null if did not have any
    IOException getIOException();

    // these methods are monitored for content and formatting applied
    @Override
    FormattingAppendable append(CharSequence csq);
    @Override
    FormattingAppendable append(CharSequence csq, int start, int end);
    @Override
    FormattingAppendable append(char c);

    /**
     * Get the underlying appendable
     *
     * @return appendable being used for accumulating output
     */
    Appendable getAppendable();

    int CONVERT_TABS = 0x0001;
    int COLLAPSE_WHITESPACE = 0x0002;
    int SUPPRESS_TRAILING_WHITESPACE = 0x0004;
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
    FormattingAppendable setOptions(int options);

    /**
     * Flush all pending new lines, no blank lines will be output, if these are desired at the end of the output.
     * <p>
     * All preFormatted and conditional regions should be exited prior to this call
     *
     * @return this
     */
    FormattingAppendable flush();

    /**
     * Flush all pending new lines and blank lines, if these are desired at the end of the output.
     * <p>
     * All preFormatted and conditional regions should be exited prior to this call
     *
     * @param maxBlankLines maximum blank lines to allow at end
     * @return this
     */
    FormattingAppendable flush(int maxBlankLines);

    /**
     * Get prefix appended after a new line character for every indent level
     *
     * @return char sequence of the current indent prefix used for each indent level
     */
    CharSequence getIndentPrefix();

    /**
     * Get the total indent prefix appended after a new line character,
     * {@link #getIndentPrefix()} repeated {@link #getIndent()} times.
     *
     * @return char sequence of the current indent prefix used for each indent level
     */
    CharSequence getTotalIndentPrefix();

    /**
     * Set prefix to append after a new line character for every indent level
     *
     * @param prefix prefix characters for new lines appended after this is set
     * @return this
     */
    FormattingAppendable setIndentPrefix(CharSequence prefix);

    /**
     * Get prefix being applied to all lines, even in pre-formatted sections
     *
     * @return char sequence of the current prefix
     */
    CharSequence getPrefix();

    /**
     * Set prefix to append after a new line character for every line before the indent prefix in normal
     * and after a new line in pre-formatted sections
     *
     * @param prefix prefix characters for new lines appended after this is set
     * @return this
     */
    FormattingAppendable setPrefix(CharSequence prefix);

    /**
     * Add a new line, if there was any unterminated text appended
     * <p>
     * Actual new line character is only appended if there is real data
     * appended and it did not contain a new line as the last character
     * @return this
     */
    FormattingAppendable line();

    /**
     * Add a new line, if predicate is true and there was any unterminated text appended
     * <p>
     * Actual new line character is only appended if and when text is appended
     * appended and it did not contain a new line as the last character
     *
     * @param predicate if true then new line will be started
     * @return this
     */
    FormattingAppendable lineIf(boolean predicate);

    /**
     * Add a new line, if there was any unterminated text appended
     * <p>
     * Actual new line character is only appended if there is real data
     * appended and it did not contain a new line as the last character
     *
     * @param lineRef predicate storage will be set to false if conditionalFormatter suppressed a new line
     * @return this
     */
    FormattingAppendable line(Ref<Boolean> lineRef);

    /**
     * Add a new line, if lineRef is true, if false means that the new line in {@link #line(Ref)} was suppressed
     * by conditionalFormatter and this new line will be suppressed too.
     * <p>
     * Actual new line character is only appended if and when text is appended
     * appended and it did not contain a new line as the last character
     *
     * @param lineRef predicate storage for conditionalFormatter suppressed new line condition
     * @return this
     */
    FormattingAppendable lineIf(Ref<Boolean> lineRef);

    /**
     * Add a blank line, if there is not one already appended.
     * <p>
     * Actual blank line characters are only appended if there is real data
     * appended and it did not contain a blank line as the last part of its text
     *
     * @return this
     */
    FormattingAppendable blankLine();

    /**
     * Add a blank line, if predicate is true and there isn't already blank lines appended.
     * <p>
     * Actual blank line characters are only appended if and when there text is appended
     * appended and it did not contain a blank line as the last part of its text
     *
     * @param predicate when true append blank line
     * @return this
     */
    FormattingAppendable blankLineIf(final boolean predicate);

    /**
     * Add a blank lines, if there isn't already given number of blank lines appended.
     * Will append only enough blank lines to increase it to given level. If more are already in the wings then nothing is done.
     * <p>
     * On real data being appended, will append enough new lines to ensure that there are given
     * number of blank lines between previous append and the next one.
     * <p>
     * Actual blank line characters are only appended if there is real data
     * appended and it did not contain a blank line as the last part of its text
     *
     * @param count number of blank lines to append
     * @return this
     */
    FormattingAppendable blankLine(int count);

    /**
     * Get the current indent level + indent offset
     *
     * @return indent level
     */
    int getIndent();

    /**
     * Set the current indent level. Not for indenting use. This does not do any calls or validation.
     * Intended use is to set initial indent
     *
     * @param indentOffset minimum indent that will be used
     * @return this
     */
    FormattingAppendable setIndentOffset(int indentOffset);

    /**
     * Increase the indent level, will terminate the current line if there is unterminated text
     *
     * @return this
     */
    FormattingAppendable indent();

    /**
     * Notify of the fact that after some appending some text, there will be an indent() call so that
     * conditional formatting can kick in before the text is appended
     *
     * @return this
     */
    FormattingAppendable willIndent();

    /**
     * Decrease the indent level, min level is 0, will terminate the current line if there is unterminated text
     *
     * @return this
     */
    FormattingAppendable unIndent();

    /**
     * Get the modification count. Incremented every time there is actual text appended
     *
     * @return modification count
     */
    int getModCount();

    /**
     * Get the number of lines appended, does not include pending: EOLs
     *
     * @return number of lines appended
     */
    int getLineCount();

    /**
     * Get total number of characters appended before the last append, but right after pending: EOLs, spaces or indents were added
     *
     * @return character offset
     */
    int getOffsetBefore();

    /**
     * Get total number of characters appended after the last append, does not include any pending: EOLs, spaces nor indents
     *
     * @return character offset
     */
    int getOffsetAfter();

    /**
     * Open a pre-formatted section. No monitoring of text is done, all text is output as is while nesting count &gt;0
     *
     * @param keepIndent       if true, if there is pending indent it will be appended before entering pre-format mode
     * @return this
     */
    FormattingAppendable openPreFormatted(final boolean keepIndent);

    /**
     * Close a pre-formatted section. No monitoring of text is done, all text is output as is while nesting count &gt;0
     *
     * @return this
     */
    FormattingAppendable closePreFormatted();

    /**
     * @return true if in pre-formatted region
     */
    boolean isPreFormatted();

    /**
     * Open a conditional formatting region.
     * <p>
     * After opening if and when text is appended the {@link ConditionalFormatter#apply(boolean, boolean, boolean, boolean)}
     * will be invoked before appending the text with parameters specifying whether indent or line were
     * invoked before appending text. The onText is always true for opening conditional formatter or the apply() method
     * would not be called
     * <p>
     * the firstAppend will be set if this is the first call, if onIndent is false on first call then the apply() method
     * will be invoked again if any indent() is performed before the conditional is closed.
     *
     * @param openFormatter to invoke before text is appended
     * @return this
     */
    FormattingAppendable openConditional(ConditionalFormatter openFormatter);

    /**
     * Close a conditional formatting region.
     * <p>
     * {@link ConditionalFormatter#apply(boolean, boolean, boolean, boolean)}
     * will be invoked with parameters specifying whether indent, line or text was appended when {@link #openConditional(ConditionalFormatter)}
     * invoked before appending text. If onText is false then openFormatter passed to {@link #openConditional(ConditionalFormatter)} was
     * not invoked.
     *
     * @param closeFormatter to invoke with parameters informing with what parameters the openFormatter was invoked, if at all
     * @return this
     */
    FormattingAppendable closeConditional(ConditionalFormatter closeFormatter);
}
