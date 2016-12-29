package com.vladsch.flexmark.ext.wikilink.internal;

import com.vladsch.flexmark.ast.Document;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ext.wikilink.WikiImage;
import com.vladsch.flexmark.ext.wikilink.WikiLink;
import com.vladsch.flexmark.ext.wikilink.WikiLinkExtension;
import com.vladsch.flexmark.parser.LinkRefProcessor;
import com.vladsch.flexmark.parser.LinkRefProcessorFactory;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;

public class WikiLinkLinkRefProcessor implements LinkRefProcessor {
    static final int BRACKET_NESTING_LEVEL = 1;

    private final WikiLinkOptions options;

    public WikiLinkLinkRefProcessor(Document document) {
        this.options = new WikiLinkOptions(document);
    }

    @Override
    public boolean getWantExclamationPrefix() {
        return options.imageLinks;
    }

    @Override
    public int getBracketNestingLevel() {
        return BRACKET_NESTING_LEVEL;
    }

    @Override
    public boolean isMatch(BasedSequence nodeChars) {
        final int length = nodeChars.length();
        if (options.imageLinks) {
            if (length >= 5 && nodeChars.charAt(0) == '!') {
                return nodeChars.charAt(1) == '[' && nodeChars.charAt(2) == '[' && nodeChars.endCharAt(1) == ']' && nodeChars.endCharAt(2) == ']';
            } else if (length >= 4) {
                return nodeChars.charAt(0) == '[' && nodeChars.charAt(1) == '[' && nodeChars.endCharAt(1) == ']' && nodeChars.endCharAt(2) == ']';
            }
        } else if (length >= 4) {
            return nodeChars.charAt(0) == '[' && nodeChars.charAt(1) == '[' && nodeChars.endCharAt(1) == ']' && nodeChars.endCharAt(2) == ']';
        }
        return false;
    }

    @Override
    public void adjustInlineText(Node node) {
        // nothing to do, our prefixes are stripped out of a link ref
    }

    @Override
    public Node createNode(BasedSequence nodeChars) {
        return nodeChars.firstChar() == '!' ? new WikiImage(nodeChars, options.linkFirstSyntax) : new WikiLink(nodeChars, options.linkFirstSyntax);
    }

    public static class Factory implements LinkRefProcessorFactory {
        @Override
        public LinkRefProcessor create(Document document) {
            return new WikiLinkLinkRefProcessor(document);
        }

        @Override
        public boolean getWantExclamationPrefix(DataHolder options) {
            return WikiLinkExtension.IMAGE_LINKS.getFrom(options);
        }

        @Override
        public int getBracketNestingLevel(DataHolder options) {
            return BRACKET_NESTING_LEVEL;
        }
    }
}
