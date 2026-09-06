package com.vladsch.flexmark.ext.gfm.issues.internal;

import com.vladsch.flexmark.ext.gfm.issues.GfmIssue;
import com.vladsch.flexmark.parser.InlineParser;
import com.vladsch.flexmark.parser.InlineParserExtension;
import com.vladsch.flexmark.parser.InlineParserExtensionFactory;
import com.vladsch.flexmark.parser.LightInlineParser;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jspecify.annotations.Nullable;

import java.util.Set;
import java.util.regex.Pattern;

public class GfmIssuesInlineParserExtension implements InlineParserExtension {
    final public static Pattern GITHUB_ISSUE = Pattern.compile("^(#)(\\d+)\\b");

    public GfmIssuesInlineParserExtension(LightInlineParser inlineParser) {

    }

    @Override
    public void finalizeDocument(InlineParser inlineParser) {

    }

    @Override
    public void finalizeBlock(InlineParser inlineParser) {

    }

    @Override
    public boolean parse(LightInlineParser inlineParser) {
        BasedSequence[] matches = inlineParser.matchWithGroups(GITHUB_ISSUE);
        if (matches != null) {
            inlineParser.flushTextNode();

            BasedSequence openMarker = matches[1];
            BasedSequence text = matches[2];

            GfmIssue gfmIssue = new GfmIssue(openMarker, text);
            inlineParser.getBlock().appendChild(gfmIssue);
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

        @Override
        public CharSequence getCharacters() {
            return "#";
        }

        @Nullable
        @Override
        public Set<Class<?>> getBeforeDependents() {
            return null;
        }

        @Override
        public InlineParserExtension apply(LightInlineParser inlineParser) {
            return new GfmIssuesInlineParserExtension(inlineParser);
        }

        @Override
        public boolean affectsGlobalScope() {
            return false;
        }
    }
}
