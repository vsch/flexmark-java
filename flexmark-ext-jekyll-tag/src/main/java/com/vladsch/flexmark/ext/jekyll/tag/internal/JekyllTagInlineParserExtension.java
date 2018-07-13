package com.vladsch.flexmark.ext.jekyll.tag.internal;

import com.vladsch.flexmark.ext.jekyll.tag.JekyllTag;
import com.vladsch.flexmark.ext.jekyll.tag.JekyllTagExtension;
import com.vladsch.flexmark.parser.InlineParser;
import com.vladsch.flexmark.parser.InlineParserExtension;
import com.vladsch.flexmark.parser.InlineParserExtensionFactory;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;

public class JekyllTagInlineParserExtension implements InlineParserExtension {
    private final JekyllTagParsing parsing;
    private final boolean listIncludesOnly;

    public JekyllTagInlineParserExtension(final InlineParser inlineParser) {
        this.parsing = new JekyllTagParsing(inlineParser.getParsing());
        this.listIncludesOnly = JekyllTagExtension.LIST_INCLUDES_ONLY.getFrom(inlineParser.getDocument());
    }

    @Override
    public void finalizeDocument(final InlineParser inlineParser) {

    }

    @Override
    public void finalizeBlock(final InlineParser inlineParser) {
    }

    @Override
    public boolean parse(final InlineParser inlineParser) {
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
                    List<JekyllTag> tagList = JekyllTagExtension.TAG_LIST.getFrom(inlineParser.getDocument());
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
        @Override
        public Set<Class<? extends InlineParserExtensionFactory>> getAfterDependents() {
            return null;
        }

        @Override
        public CharSequence getCharacters() {
            return "{";
        }

        @Override
        public Set<Class<? extends InlineParserExtensionFactory>> getBeforeDependents() {
            return null;
        }

        @Override
        public InlineParserExtension create(final InlineParser inlineParser) {
            return new JekyllTagInlineParserExtension(inlineParser);
        }

        @Override
        public boolean affectsGlobalScope() {
            return false;
        }
    }
}
