package com.vladsch.flexmark.ext.attributes;

import com.vladsch.flexmark.util.misc.CharPredicate;
import org.jetbrains.annotations.NotNull;

import static com.vladsch.flexmark.util.sequence.SequenceUtils.containsAny;

public enum AttributeValueQuotes {
    AS_IS,
    NO_QUOTES_SINGLE_PREFERRED,
    NO_QUOTES_DOUBLE_PREFERRED,
    SINGLE_PREFERRED,
    DOUBLE_PREFERRED,
    SINGLE_QUOTES,
    DOUBLE_QUOTES,
    ;

    final static CharPredicate P_SPACES_OR_QUOTES = CharPredicate.anyOf(" \t\n'\"");
    final static CharPredicate P_SINGLE_QUOTES = CharPredicate.anyOf("'");
    final static CharPredicate P_DOUBLE_QUOTES = CharPredicate.anyOf("\"");

    @NotNull
    public String quotesFor(@NotNull CharSequence text, @NotNull CharSequence defaultQuotes) {
        switch (this) {
            case NO_QUOTES_SINGLE_PREFERRED:
                if (!containsAny(text, P_SPACES_OR_QUOTES)) {
                    return "";
                } else if (!containsAny(text, P_SINGLE_QUOTES) || containsAny(text, P_DOUBLE_QUOTES)) {
                    return "'";
                } else {
                    return "\"";
                }
            case NO_QUOTES_DOUBLE_PREFERRED:
                if (!containsAny(text, P_SPACES_OR_QUOTES)) {
                    return "";
                } else if (!containsAny(text, P_DOUBLE_QUOTES) || containsAny(text, P_SINGLE_QUOTES)) {
                    return "\"";
                } else {
                    return "'";
                }
            case SINGLE_PREFERRED:
                if (!containsAny(text, P_SINGLE_QUOTES) || containsAny(text, P_DOUBLE_QUOTES)) {
                    return "'";
                } else {
                    return "\"";
                }
            case DOUBLE_PREFERRED:
                if (!containsAny(text, P_DOUBLE_QUOTES) || containsAny(text, P_SINGLE_QUOTES)) {
                    return "\"";
                } else {
                    return "'";
                }
            case SINGLE_QUOTES:
                return "'";

            case DOUBLE_QUOTES:
                return "\"";

            case AS_IS:
            default:
                return defaultQuotes.toString();
        }
    }
}
