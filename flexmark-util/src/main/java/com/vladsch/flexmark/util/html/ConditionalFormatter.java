package com.vladsch.flexmark.util.html;

public interface ConditionalFormatter {
    /**
     * Text, line or indent was appended
     *
     * @param firstAppend true if this is the first append after opening the conditional region
     * @param onIndent true if indent was invoked before text appended
     * @param onLine   true if line as invoked  before text appended
     * @param onText   true if text appended
     */
    void apply(final boolean firstAppend, boolean onIndent, boolean onLine, boolean onText);
}
