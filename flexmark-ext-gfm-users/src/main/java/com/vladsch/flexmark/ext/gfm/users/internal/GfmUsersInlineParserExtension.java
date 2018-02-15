package com.vladsch.flexmark.ext.gfm.users.internal;

import com.vladsch.flexmark.ext.gfm.users.GfmUser;
import com.vladsch.flexmark.parser.InlineParser;
import com.vladsch.flexmark.parser.InlineParserExtension;
import com.vladsch.flexmark.parser.InlineParserExtensionFactory;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.Set;
import java.util.regex.Pattern;

public class GfmUsersInlineParserExtension implements InlineParserExtension {
   public static final Pattern GITHUB_USER = Pattern.compile("^(@)([a-z\\d](?:[a-z\\d]|-(?=[a-z\\d])){0,38})(?=[ \\t]+|\\r|\\r\\n|\\n|$)", Pattern.CASE_INSENSITIVE);

    public GfmUsersInlineParserExtension(final InlineParser inlineParser) {

    }

    @Override
    public void finalizeDocument(final InlineParser inlineParser) {

    }

    @Override
    public void finalizeBlock(final InlineParser inlineParser) {

    }

    @Override
    public boolean parse(final InlineParser inlineParser) {
        BasedSequence[] matches = inlineParser.matchWithGroups(GITHUB_USER);
        if (matches != null) {
            BasedSequence input = inlineParser.getInput();
            inlineParser.flushTextNode();

            BasedSequence openMarker = matches[1];
            BasedSequence text = matches[2];

            GfmUser gitHubIssue = new GfmUser(openMarker, text);
            inlineParser.getBlock().appendChild(gitHubIssue);
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
            return "@";
        }

        @Override
        public Set<Class<? extends InlineParserExtensionFactory>> getBeforeDependents() {
            return null;
        }

        @Override
        public InlineParserExtension create(final InlineParser inlineParser) {
            return new GfmUsersInlineParserExtension(inlineParser);
        }

        @Override
        public boolean affectsGlobalScope() {
            return false;
        }
    }
}
