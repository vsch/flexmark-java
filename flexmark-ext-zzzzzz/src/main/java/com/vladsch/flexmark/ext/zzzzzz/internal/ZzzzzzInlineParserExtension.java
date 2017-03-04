package com.vladsch.flexmark.ext.zzzzzz.internal;

import com.vladsch.flexmark.ext.zzzzzz.Zzzzzz;
import com.vladsch.flexmark.parser.InlineParser;
import com.vladsch.flexmark.parser.InlineParserExtension;
import com.vladsch.flexmark.parser.InlineParserExtensionFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ZzzzzzInlineParserExtension implements InlineParserExtension {
    private List<Zzzzzz> openZzzzzzs;

    public ZzzzzzInlineParserExtension(final InlineParser inlineParser) {
        this.openZzzzzzs = new ArrayList<Zzzzzz>();
    }

    @Override
    public void finalizeDocument(final InlineParser inlineParser) {

    }

    @Override
    public void finalizeBlock(final InlineParser inlineParser) {
        for (int j = openZzzzzzs.size(); j-- > 0; ) {
            inlineParser.moveNodes(openZzzzzzs.get(j), inlineParser.getBlock().getLastChild());
        }

        openZzzzzzs.clear();
    }

    @Override
    public boolean parse(final InlineParser inlineParser) {
        return false;
    }

    public static class Factory implements InlineParserExtensionFactory {
        @Override
        public Set<Class<? extends InlineParserExtensionFactory>> getAfterDependents() {
            return null;
        }

        @Override
        public CharSequence getCharacters() {
            return "";
        }

        @Override
        public Set<Class<? extends InlineParserExtensionFactory>> getBeforeDependents() {
            return null;
        }

        @Override
        public InlineParserExtension create(final InlineParser inlineParser) {
            return new ZzzzzzInlineParserExtension(inlineParser);
        }

        @Override
        public boolean affectsGlobalScope() {
            return false;
        }
    }
}
