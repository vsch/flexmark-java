package com.vladsch.flexmark.ext.macros.internal;

import com.vladsch.flexmark.ext.macros.MacroReference;
import com.vladsch.flexmark.parser.InlineParser;
import com.vladsch.flexmark.parser.InlineParserExtension;
import com.vladsch.flexmark.parser.InlineParserExtensionFactory;
import com.vladsch.flexmark.parser.LightInlineParser;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.regex.Pattern;

public class MacrosInlineParserExtension implements InlineParserExtension {
    static Pattern MACRO_REFERENCE = Pattern.compile("<<<([\\w_-]+)>>>");
    static Pattern MACRO_REFERENCE_INTELLIJ = Pattern.compile("<<<([\u001f\\w_-]+)>>>");

    public MacrosInlineParserExtension(LightInlineParser inlineParser) {

    }

    @Override
    public void finalizeDocument(@NotNull InlineParser inlineParser) {

    }

    @Override
    public void finalizeBlock(@NotNull InlineParser inlineParser) {

    }

    @Override
    public boolean parse(@NotNull LightInlineParser inlineParser) {
        BasedSequence match = inlineParser.match(inlineParser.getParsing().intellijDummyIdentifier ? MACRO_REFERENCE_INTELLIJ : MACRO_REFERENCE);

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
        @Nullable
        @Override
        public Set<Class<?>> getAfterDependents() {
            return null;
        }

        @NotNull
        @Override
        public CharSequence getCharacters() {
            return "<";
        }

        @Nullable
        @Override
        public Set<Class<?>> getBeforeDependents() {
            return null;
        }

        @NotNull
        @Override
        public InlineParserExtension apply(@NotNull LightInlineParser lightInlineParser) {
            return new MacrosInlineParserExtension(lightInlineParser);
        }

        @Override
        public boolean affectsGlobalScope() {
            return false;
        }
    }
}
