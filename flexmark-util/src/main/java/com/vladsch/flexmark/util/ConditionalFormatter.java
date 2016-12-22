package com.vladsch.flexmark.util;

public interface ConditionalFormatter {
    /**
     * Text, line or indent was appended
     *
     * @param onIndent true if indent was invoked before text appended
     * @param onLine   true if line as invoked  before text appended
     * @param onText   true if text appended
     */
    void apply(boolean onIndent, boolean onLine, boolean onText);
}
