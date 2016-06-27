package com.vladsch.flexmark.ext.wikilink.internal;

import com.vladsch.flexmark.ext.wikilink.WikiLink;
import com.vladsch.flexmark.internal.util.BasedSequence;
import com.vladsch.flexmark.node.Document;
import com.vladsch.flexmark.node.Node;
import com.vladsch.flexmark.parser.LinkRefProcessor;
import com.vladsch.flexmark.parser.LinkRefProcessorFactory;

public class WikiLinkLinkRefProcessor implements LinkRefProcessor {
    final static boolean WANT_EXCLAMATION_PREFIX = false;
    final static int BRACKET_NESTING_LEVEL = 1;
    
    private final WikiLinkOptions options;

    public WikiLinkLinkRefProcessor(Document document) {
        this.options = new WikiLinkOptions(document);
    }

    @Override
    public boolean getWantExclamationPrefix() {
        return WANT_EXCLAMATION_PREFIX;
    }

    @Override
    public int getBracketNestingLevel() {
        return BRACKET_NESTING_LEVEL;
    }

    @Override
    public boolean isMatch(BasedSequence nodeChars) {
        return nodeChars.length() >= 4 && nodeChars.charAt(0) == '[' && nodeChars.charAt(1) == '[' && nodeChars.endCharAt(1) == ']' && nodeChars.endCharAt(2) == ']';
    }

    @Override
    public void adjustInlineText(Node node) {
        // nothing to do, our prefixes are stripped out of a link ref
    }

    @Override
    public Node createNode(BasedSequence nodeChars) {
        return new WikiLink(nodeChars, options.linkFirstSyntax);
    }
    
    public static class Factory implements LinkRefProcessorFactory {
        @Override
        public LinkRefProcessor create(Document document) {
            return new WikiLinkLinkRefProcessor(document);
        }

        @Override
        public boolean getWantExclamationPrefix() {
            return WANT_EXCLAMATION_PREFIX;
        }

        @Override
        public int getBracketNestingLevel() {
            return BRACKET_NESTING_LEVEL;
        }
    }
}
