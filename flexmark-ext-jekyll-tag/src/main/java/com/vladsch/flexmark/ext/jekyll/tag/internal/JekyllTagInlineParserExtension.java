package com.vladsch.flexmark.ext.jekyll.tag.internal;

import com.vladsch.flexmark.ext.jekyll.tag.JekyllTag;
import com.vladsch.flexmark.ext.jekyll.tag.JekyllTagExtension;
import com.vladsch.flexmark.parser.InlineParser;
import com.vladsch.flexmark.parser.InlineParserExtension;
import com.vladsch.flexmark.parser.InlineParserExtensionFactory;
import com.vladsch.flexmark.parser.LightInlineParser;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;

public class JekyllTagInlineParserExtension implements InlineParserExtension {
    final private JekyllTagParsing parsing;
    final private boolean listIncludesOnly;

    public JekyllTagInlineParserExtension(LightInlineParser lightInlineParser) {
        this.parsing = new JekyllTagParsing(lightInlineParser.getParsing());
        this.listIncludesOnly = JekyllTagExtension.LIST_INCLUDES_ONLY.get(lightInlineParser.getDocument());
    }

    @Override
    public void finalizeDocument(@NotNull InlineParser inlineParser) {

    }

    @Override
    public void finalizeBlock(@NotNull InlineParser inlineParser) {
    }

    @Override
    public boolean parse(@NotNull LightInlineParser inlineParser) {
        if (inlineParser.peek(1) == '%' && (inlineParser.peek(2) == ' ' || inlineParser.peek(2) == '\t')) {
            BasedSequence input = inlineParser.getInput();
            Matcher matcher = inlineParser.matcher(parsing.MACRO_TAG);
            if (matcher != null) {
                BasedSequence tag = input.subSequence(matcher.start(), matcher.end());
                BasedSequence tagName = input.subSequence(matcher.start(1), matcher.end(1));
                BasedSequence parameters = input.subSequence(matcher.end(1), matcher.end() - 2).trim();
                JekyllTag macro = new JekyllTag(tag.subSequence(0, 2), tagName, parameters, tag.endSequence(2));
                macro.setCharsFromContent();

                //noinspection EqualsBetweenInconvertibleTypes
                if (!listIncludesOnly || tagName.equals(JekyllTagBlockParser.INCLUDE_TAG)) {
                    List<JekyllTag> tagList = JekyllTagExtension.TAG_LIST.get(inlineParser.getDocument());
                    tagList.add(macro);
                }

                inlineParser.flushTextNode();
                inlineParser.getBlock().appendChild(macro);
                return true;
            }
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
            return "{";
        }

        @Nullable
        @Override
        public Set<Class<?>> getBeforeDependents() {
            return null;
        }

        @NotNull
        @Override
        public InlineParserExtension apply(@NotNull LightInlineParser lightInlineParser) {
            return new JekyllTagInlineParserExtension(lightInlineParser);
        }

        @Override
        public boolean affectsGlobalScope() {
            return false;
        }
    }
}
