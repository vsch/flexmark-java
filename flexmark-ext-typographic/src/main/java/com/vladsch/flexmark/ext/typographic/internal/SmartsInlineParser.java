package com.vladsch.flexmark.ext.typographic.internal;

import com.vladsch.flexmark.ext.typographic.TypographicSmarts;
import com.vladsch.flexmark.parser.InlineParser;
import com.vladsch.flexmark.parser.InlineParserExtension;
import com.vladsch.flexmark.parser.InlineParserExtensionFactory;
import com.vladsch.flexmark.parser.LightInlineParser;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.Set;

public class SmartsInlineParser implements InlineParserExtension {
    private final SmartsParsing parsing;

    public SmartsInlineParser(LightInlineParser inlineParser) {
        this.parsing = new SmartsParsing(inlineParser.getParsing());
    }

    @Override
    public void finalizeDocument(InlineParser inlineParser) {

    }

    @Override
    public void finalizeBlock(InlineParser inlineParser) {

    }

    @Override
    public boolean parse(LightInlineParser inlineParser) {
        // hard coding implementation because pattern matching can be very slow for large files
        BasedSequence input = inlineParser.getInput();
        int iMax = input.length();
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
//        BasedSequence match = inlineParser.match(parsing.SMARTS);
//        if (match != null) {
//            inlineParser.flushTextNode();
//
//            String typographicSmarts;
//
//            if (match.matches(parsing.ELIPSIS)) typographicSmarts = "&hellip;";
//            else if (match.matches(parsing.ELIPSIS_SPACED)) typographicSmarts = "&hellip;";
//            else if (match.matches(parsing.EN_DASH)) typographicSmarts = "&ndash;";
//            else if (match.matches(parsing.EM_DASH)) typographicSmarts = "&mdash;";
//            else return false;
//
//            TypographicSmarts smarts = new TypographicSmarts(match, typographicSmarts);
//            inlineParser.getBlock().appendChild(smarts);
//            return true;
//        }
        return false;
    }

    public static class Factory implements InlineParserExtensionFactory {
        @Override
        public Set<Class<? extends InlineParserExtensionFactory>> getAfterDependents() {
            return null;
        }

        @Override
        public CharSequence getCharacters() {
            return ".-";
        }

        @Override
        public Set<Class<? extends InlineParserExtensionFactory>> getBeforeDependents() {
            return null;
        }

        @Override
        public InlineParserExtension apply(LightInlineParser lightInlineParser) {
            return new SmartsInlineParser(lightInlineParser);
        }

        @Override
        public boolean affectsGlobalScope() {
            return false;
        }
    }
}
