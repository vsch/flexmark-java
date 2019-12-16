package com.vladsch.flexmark.parser;

import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface SpecialLeadInHandler extends BiConsumer<BasedSequence, Consumer<CharSequence>> {
    /**
     * Escape/UnEscape special lead-in characters which start a block element if first non-whitespace on the line
     *
     * The leadIn sequence is always followed by a space or EOL so if lead in does not require a space to start a block element
     * then test if it starts with the special sequence, otherwise test if it equals the special sequence
     *
     * @param leadIn  char sequence appearing as first non-whitespace on a line
     * @param consumer consumer of char sequences to be called for the leadIn if it is changed by this handler
     */
    void accept(BasedSequence leadIn, Consumer<CharSequence> consumer);
}
