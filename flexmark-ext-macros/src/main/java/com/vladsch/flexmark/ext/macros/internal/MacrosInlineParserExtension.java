package com.vladsch.flexmark.ext.macros.internal;

import com.vladsch.flexmark.ext.macros.MacroReference;
import com.vladsch.flexmark.parser.InlineParser;
import com.vladsch.flexmark.parser.InlineParserExtension;
import com.vladsch.flexmark.parser.InlineParserExtensionFactory;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.Set;
import java.util.regex.Pattern;

public class MacrosInlineParserExtension implements InlineParserExtension {
    static Pattern MACRO_REFERENCE = Pattern.compile("<<<([\\w_-]+)>>>");
    static Pattern MACRO_REFERENCE_INTELLIJ = Pattern.compile("<<<([\u001f\\w_-]+)>>>");

    public MacrosInlineParserExtension(final InlineParser inlineParser) {

    }

    @Override
    public void finalizeDocument(final InlineParser inlineParser) {

    }

    @Override
    public void finalizeBlock(final InlineParser inlineParser) {

    }

    @Override
    public boolean parse(final InlineParser inlineParser) {
        final BasedSequence match = inlineParser.match(inlineParser.getParsing().intellijDummyIdentifier ? MACRO_REFERENCE_INTELLIJ : MACRO_REFERENCE);

        if (match != null) {
            BasedSequence name = match.midSequence(3, -3);
            MacroReference macro = new MacroReference(match.subSequence(0, 3), name, match.midSequence(-3));
            inlineParser.flushTextNode();
            inlineParser.getBlock().appendChild(macro);
            return true;
        }
        return false;
    }

    public static class Factory implements InlineParserExtensionFactory {
        @Override
        public Set<Class<? extends InlineParserExtensionFactory>> getAfterDependents() {
            return null;
        }

        @Override
        public CharSequence getCharacters() {
            return "<";
        }

        @Override
        public Set<Class<? extends InlineParserExtensionFactory>> getBeforeDependents() {
            return null;
        }

        @Override
        public InlineParserExtension apply(final InlineParser inlineParser) {
            return new MacrosInlineParserExtension(inlineParser);
        }

        @Override
        public boolean affectsGlobalScope() {
            return false;
        }
    }
}
