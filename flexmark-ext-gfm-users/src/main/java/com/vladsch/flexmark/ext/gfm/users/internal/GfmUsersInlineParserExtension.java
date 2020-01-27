package com.vladsch.flexmark.ext.gfm.users.internal;

import com.vladsch.flexmark.ext.gfm.users.GfmUser;
import com.vladsch.flexmark.parser.InlineParser;
import com.vladsch.flexmark.parser.InlineParserExtension;
import com.vladsch.flexmark.parser.InlineParserExtensionFactory;
import com.vladsch.flexmark.parser.LightInlineParser;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.regex.Pattern;

public class GfmUsersInlineParserExtension implements InlineParserExtension {
    final public static Pattern GITHUB_USER = Pattern.compile("^(@)([a-z\\d](?:[a-z\\d]|-(?=[a-z\\d])){0,38})\\b", Pattern.CASE_INSENSITIVE);

    public GfmUsersInlineParserExtension(LightInlineParser inlineParser) {

    }

    @Override
    public void finalizeDocument(@NotNull InlineParser inlineParser) {

    }

    @Override
    public void finalizeBlock(@NotNull InlineParser inlineParser) {

    }

    @Override
    public boolean parse(@NotNull LightInlineParser inlineParser) {
        int index = inlineParser.getIndex();
        boolean isPossible = index == 0;
        if (!isPossible) {
            char c = inlineParser.getInput().charAt(index - 1);
            if (!Character.isUnicodeIdentifierPart(c) && c != '-' && c != '.') {
                isPossible = true;
            }
        }
        if (isPossible) {
            BasedSequence[] matches = inlineParser.matchWithGroups(GITHUB_USER);
            if (matches != null) {
                inlineParser.flushTextNode();

                BasedSequence openMarker = matches[1];
                BasedSequence text = matches[2];

                GfmUser gitHubIssue = new GfmUser(openMarker, text);
                inlineParser.getBlock().appendChild(gitHubIssue);
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
            return "@";
        }

        @Nullable
        @Override
        public Set<Class<?>> getBeforeDependents() {
            return null;
        }

        @NotNull
        @Override
        public InlineParserExtension apply(@NotNull LightInlineParser lightInlineParser) {
            return new GfmUsersInlineParserExtension(lightInlineParser);
        }

        @Override
        public boolean affectsGlobalScope() {
            return false;
        }
    }
}
