package com.vladsch.flexmark.ext.gitlab.internal;

import com.vladsch.flexmark.ext.gitlab.GitLabInlineMath;
import com.vladsch.flexmark.parser.InlineParser;
import com.vladsch.flexmark.parser.LightInlineParser;
import com.vladsch.flexmark.parser.InlineParserExtension;
import com.vladsch.flexmark.parser.InlineParserExtensionFactory;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GitLabInlineMathParser implements InlineParserExtension {
    Pattern MATH_PATTERN = Pattern.compile("\\$`((?:.|\n)*?)`\\$");
    private final GitLabOptions options;

    public GitLabInlineMathParser(final LightInlineParser inlineParser) {
        options = new GitLabOptions(inlineParser.getDocument());
    }

    @Override
    public void finalizeDocument(final InlineParser inlineParser) {

    }

    @Override
    public void finalizeBlock(final InlineParser inlineParser) {

    }

    @Override
    public boolean parse(final LightInlineParser inlineParser) {
        if (inlineParser.peek(1) == '`') {
            BasedSequence input = inlineParser.getInput();
            Matcher matcher = inlineParser.matcher(MATH_PATTERN);
            if (matcher != null) {
                inlineParser.flushTextNode();

                BasedSequence mathOpen = input.subSequence(matcher.start(), matcher.start(1));
                BasedSequence mathClosed = input.subSequence(matcher.end(1), matcher.end());
                GitLabInlineMath inlineMath = new GitLabInlineMath(mathOpen, mathOpen.baseSubSequence(mathOpen.getEndOffset(), mathClosed.getStartOffset()), mathClosed);
                inlineParser.getBlock().appendChild(inlineMath);
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
            return "$";
        }

        @Override
        public Set<Class<? extends InlineParserExtensionFactory>> getBeforeDependents() {
            return null;
        }

        @Override
        public InlineParserExtension apply(final LightInlineParser lightInlineParser) {
            return new GitLabInlineMathParser(lightInlineParser);
        }

        @Override
        public boolean affectsGlobalScope() {
            return false;
        }
    }
}
