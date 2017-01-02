package com.vladsch.flexmark.ext.wikilink.internal;

import com.vladsch.flexmark.ast.Document;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ast.util.TextCollectingVisitor;
import com.vladsch.flexmark.ext.wikilink.WikiImage;
import com.vladsch.flexmark.ext.wikilink.WikiLink;
import com.vladsch.flexmark.ext.wikilink.WikiLinkExtension;
import com.vladsch.flexmark.ext.wikilink.WikiNode;
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
    public BasedSequence adjustInlineText(Document document, Node node) {
        // here we remove the page ref from child text and only leave the text part
        assert (node instanceof WikiNode);
        final WikiNode wikiNode = (WikiNode) node;
        return wikiNode.getText().ifNull(wikiNode.getLink());
    }

    @Override
    public boolean allowDelimiters(final BasedSequence chars, final Document document, final Node node) {
        assert (node instanceof WikiNode);
        final WikiNode wikiNode = (WikiNode) node;
        return node instanceof WikiLink && WikiLinkExtension.ALLOW_INLINES.getFrom(document) && wikiNode.getText().ifNull(wikiNode.getLink()).containsAllOf(chars);
    }

    @Override
    public void updateNodeElements(final Document document, final Node node) {
        assert (node instanceof WikiNode);
        final WikiNode wikiNode = (WikiNode) node;
        if (node instanceof WikiLink && WikiLinkExtension.ALLOW_INLINES.getFrom(document)) {
            // need to update link and pageRef with plain text versions
            if (wikiNode.getText().isNull()) {
                BasedSequence link = new TextCollectingVisitor().collectAndGetSequence(node);
                wikiNode.setLink(link, WikiLinkExtension.ALLOW_ANCHORS.getFrom(document), WikiLinkExtension.ALLOW_ANCHOR_ESCAPE.getFrom(document));
            }
        }
    }

    @Override
    public Node createNode(BasedSequence nodeChars) {
        return nodeChars.firstChar() == '!' ? new WikiImage(nodeChars, options.linkFirstSyntax, options.allowPipeEscape)
                : new WikiLink(nodeChars, options.linkFirstSyntax, options.allowAnchors, options.allowPipeEscape, options.allowAnchorEscape);
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
