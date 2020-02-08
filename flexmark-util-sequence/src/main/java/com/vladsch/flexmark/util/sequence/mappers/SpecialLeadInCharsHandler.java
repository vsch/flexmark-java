package com.vladsch.flexmark.util.sequence.mappers;

import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.misc.CharPredicate;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class SpecialLeadInCharsHandler implements SpecialLeadInHandler {
    final CharPredicate predicate;

    protected SpecialLeadInCharsHandler(CharPredicate predicate) {
        this.predicate = predicate;
    }

    /**
     * Escape special lead-in characters which start a block element if first non-whitespace on the line
     * <p>
     * The leadIn sequence is always followed by a space or EOL so if lead in does not require a space to start a block element
     * then test if it starts with the special sequence, otherwise test if it equals the special sequence
     *
     * @param sequence char sequence appearing as first non-whitespace on a line
     * @param options  options
     * @param consumer consumer of char sequences to be called for the leadIn if it is changed by this handler
     * @return true if sequence was a lead in for the handler
     */
    @Override
    public boolean escape(@NotNull BasedSequence sequence, @Nullable DataHolder options, @NotNull Consumer<CharSequence> consumer) {
        if (sequence.length() == 1 && predicate.test(sequence.charAt(0))) {
            consumer.accept("\\");
            consumer.accept(sequence);
            return true;
        }
        return false;
    }

    /**
     * UnEscape special lead-in characters which start a block element if first non-whitespace on the line
     * <p>
     * The leadIn sequence is always followed by a space or EOL so if lead in does not require a space to start a block element
     * then test if it starts with the special sequence, otherwise test if it equals the special sequence
     *
     * @param sequence char sequence appearing as first non-whitespace on a line
     * @param options  options
     * @param consumer consumer of char sequences to be called for the leadIn if it is changed by this handler
     * @return true if sequence was a lead in for the handler
     */
    @Override
    public boolean unEscape(@NotNull BasedSequence sequence, @Nullable DataHolder options, @NotNull Consumer<CharSequence> consumer) {
        if (sequence.length() == 2 && sequence.charAt(0) == '\\' && predicate.test(sequence.charAt(1))) {
            consumer.accept(sequence.subSequence(1));
            return true;
        }
        return false;
    }

    @NotNull
    public static SpecialLeadInCharsHandler create(char leadInChar) {
        return new SpecialLeadInCharsHandler(CharPredicate.anyOf(leadInChar));
    }

    @NotNull
    public static SpecialLeadInCharsHandler create(@NotNull CharSequence leadInChar) {
        return new SpecialLeadInCharsHandler(CharPredicate.anyOf(leadInChar));
    }
}
