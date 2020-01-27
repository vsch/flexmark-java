package com.vladsch.flexmark.ext.typographic.internal;

import com.vladsch.flexmark.ext.typographic.TypographicSmarts;
import com.vladsch.flexmark.parser.InlineParser;
import com.vladsch.flexmark.parser.InlineParserExtension;
import com.vladsch.flexmark.parser.InlineParserExtensionFactory;
import com.vladsch.flexmark.parser.LightInlineParser;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class SmartsInlineParser implements InlineParserExtension {
    final private SmartsParsing parsing;

    public SmartsInlineParser(LightInlineParser inlineParser) {
        this.parsing = new SmartsParsing(inlineParser.getParsing());
    }

    @Override
    public void finalizeDocument(@NotNull InlineParser inlineParser) {

    }

    @Override
    public void finalizeBlock(@NotNull InlineParser inlineParser) {

    }

    @Override
    public boolean parse(@NotNull LightInlineParser inlineParser) {
        // hard coding implementation because pattern matching can be very slow for large files
        BasedSequence input = inlineParser.getInput();
        String typographicSmarts = null;
        BasedSequence match = null;

        int i = inlineParser.getIndex();
        char c = input.charAt(i);

        if (c == '.') {
            if (input.matchChars(parsing.ELIPSIS, i)) {
                match = input.subSequence(i, i + parsing.ELIPSIS.length());
                typographicSmarts = "&hellip;";
            } else if (input.matchChars(parsing.ELIPSIS_SPACED, i)) {
                match = input.subSequence(i, i + parsing.ELIPSIS_SPACED.length());
                typographicSmarts = "&hellip;";
            }
        } else if (c == '-') {
            if (input.matchChars(parsing.EM_DASH, i)) {
                match = input.subSequence(i, i + parsing.EM_DASH.length());
                typographicSmarts = "&mdash;";
            } else if (input.matchChars(parsing.EN_DASH, i)) {
                match = input.subSequence(i, i + parsing.EN_DASH.length());
                typographicSmarts = "&ndash;";
            }
        }

        if (match != null) {
            inlineParser.flushTextNode();
            inlineParser.setIndex(i + match.length());
            TypographicSmarts smarts = new TypographicSmarts(match, typographicSmarts);
            inlineParser.getBlock().appendChild(smarts);
            return true;
        }
        return false;
    }

    public static class Factory implements InlineParserExtensionFactory {
        @Nullable
        @Override
        public Set<Class<?>> getAfterDependents() {
            return null;
        }

        @NotNull
        @Override
        public CharSequence getCharacters() {
            return ".-";
        }

        @Nullable
        @Override
        public Set<Class<?>> getBeforeDependents() {
            return null;
        }

        @NotNull
        @Override
        public InlineParserExtension apply(@NotNull LightInlineParser lightInlineParser) {
            return new SmartsInlineParser(lightInlineParser);
        }

        @Override
        public boolean affectsGlobalScope() {
            return false;
        }
    }
}
